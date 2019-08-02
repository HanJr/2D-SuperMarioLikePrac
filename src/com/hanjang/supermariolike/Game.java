package com.hanjang.supermariolike;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.hanjang.framework.ObjectID;

public class Game extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera cam;
	private BufferedImage level = null;
	public static int WIDTH;
	public static int HEIGHT;
	
	
	public void init() {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		cam = new Camera(0, 0);
		handler = new Handler(cam);
		//handler.createLevel();
		//handler.addObject(new Player(100,100,ObjectID.Player));
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level.png"); // if it loads the image correctly, there shouldn't be error. 프로젝트 프로퍼티에서 빌드패스를 res 폴더로 했기 때문에 저 path가 가능한 것 같다.
		mapBuild(level);
		handler.addObject(new Player(100,100,ObjectID.Player));
		addKeyListener(new KeyInput(handler));
		requestFocus();
	}
	
	public void run() {
		init();
		
		double targetNumOfUpdate = 60.0;
		double ns = 1000000000 / targetNumOfUpdate;
		long lastUpdate = System.nanoTime();
		double delta = 0;
		int update = 0;
		int fps = 0;
		long timer = System.currentTimeMillis();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastUpdate) / ns;
			lastUpdate = now;
			
			if(delta >= 1) {
				tick();
				delta--;
				update++;
			}
			
			render();
			fps++;
			
			if(System.currentTimeMillis() - timer  >= 1000) {
				timer += 1000;
				System.out.println("Update: " + update + " FPS: " + fps);
				update = 0;
				fps = 0;
			}
		}
	}

	public void mapBuild(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int pixel = image.getRGB(x,y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if(red == 255 && green == 255 && blue == 255)
					handler.addObject(new Block(x * 32, y * 32, ObjectID.Block));
			}
		}
	}
	
	private void tick() {
		handler.tick();
	}
	
	//나는 지금까지 렌더라는 것은 무조건 Graphics g 가 요구되는 것으로 착각하였으나, 이것은 요구되는 것이 아니다.
	private void render() {
		// TODO Auto-generated method stub
		BufferStrategy bs = this.getBufferStrategy();
		
		/*내가 했던 방법, NullPointerException이 나온다. 
		if(bs == null) {
			createBufferStrategy(3);
		}
		*/ 
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,Game.WIDTH, Game.HEIGHT);
		
		
		g2d.translate(cam.getX(), cam.getY()); // 보통 프로그래밍에서 시작은 0,0이 시작이 된다. 그러나, translate method은 이렇게 고정된 origination을 바꿀 수 있게 해준다. 
											   // 즉 g2d,translate(Game.WIDHT/2, Game.HEIGHT/2)가 사용되면, bg drawing의 기준점은 더이상 0,0이 아니라 화면의 중심이 된다. 
											   // It is additive, https://coderanch.com/t/333375/java/Graphics-translate
		handler.render(g);	
		
		g2d.translate(-cam.getX(), -cam.getY()); //사실상 이것이 없어도, 똑같이 작동한다. https://www.youtube.com/watch?v=vdcOIwkB6dA&list=PLWms45O3n--54U-22GDqKMRGlXROOZtMx&index=10 (6:25의 현상은)
		
		g.dispose();
		bs.show();
	}

	public synchronized void start() {
		// TODO Auto-generated method stub
		
		if(isRunning)
			return;
		
		isRunning = true;
		//After this line, I totally forgot about thread.
		thread = new Thread(this);
		thread.start();
		
	}
	
	public synchronized void stop() {
		// TODO Auto-generated method stub
		if(!isRunning)
			return;
		
		isRunning = false;
		
		//Runnable 자체는 interface로 close 할 수 없다. 그래서 thread variable을 private으로 만들었다. 
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(1);
		
	}	
	
	public static void main(String[] args) {
		new Window(800, 600, "Super Mario Like", new Game()); //this 를 new Game()대신에 패스하려고 했는데, this는 static method에서 불가하다고 에러 메시지가 나왔다.
	}
}

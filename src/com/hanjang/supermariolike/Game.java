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
		level = loader.loadImage("/level.png"); // if it loads the image correctly, there shouldn't be error. ������Ʈ ������Ƽ���� �����н��� res ������ �߱� ������ �� path�� ������ �� ����.
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
	
	//���� ���ݱ��� ������� ���� ������ Graphics g �� �䱸�Ǵ� ������ �����Ͽ�����, �̰��� �䱸�Ǵ� ���� �ƴϴ�.
	private void render() {
		// TODO Auto-generated method stub
		BufferStrategy bs = this.getBufferStrategy();
		
		/*���� �ߴ� ���, NullPointerException�� ���´�. 
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
		
		
		g2d.translate(cam.getX(), cam.getY()); // ���� ���α׷��ֿ��� ������ 0,0�� ������ �ȴ�. �׷���, translate method�� �̷��� ������ origination�� �ٲ� �� �ְ� ���ش�. 
											   // �� g2d,translate(Game.WIDHT/2, Game.HEIGHT/2)�� ���Ǹ�, bg drawing�� �������� ���̻� 0,0�� �ƴ϶� ȭ���� �߽��� �ȴ�. 
											   // It is additive, https://coderanch.com/t/333375/java/Graphics-translate
		handler.render(g);	
		
		g2d.translate(-cam.getX(), -cam.getY()); //��ǻ� �̰��� ���, �Ȱ��� �۵��Ѵ�. https://www.youtube.com/watch?v=vdcOIwkB6dA&list=PLWms45O3n--54U-22GDqKMRGlXROOZtMx&index=10 (6:25�� ������)
		
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
		
		//Runnable ��ü�� interface�� close �� �� ����. �׷��� thread variable�� private���� �������. 
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(1);
		
	}	
	
	public static void main(String[] args) {
		new Window(800, 600, "Super Mario Like", new Game()); //this �� new Game()��ſ� �н��Ϸ��� �ߴµ�, this�� static method���� �Ұ��ϴٰ� ���� �޽����� ���Դ�.
	}
}

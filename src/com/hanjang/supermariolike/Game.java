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
	private Camera cam;
	private int level = 1; //���� BufferedImage����
	private BufferedImage cloud = null;
	private String[] levels = {"/level.png", "/level2.png"};
	public static int WIDTH;
	public static int HEIGHT;
	public static Texture tex;
	public static Handler handler;
	private BufferedImageLoader loader;
	
	public void init() {
		WIDTH = this.getWidth();//how does the canvas get the width and height?
		HEIGHT = this.getHeight();//Canvas has getWidth and getHeight method inherited from Component class
		cam = new Camera(0, 0);
		handler = new Handler(cam);
		tex = new Texture();
		//handler.createLevel();
		//handler.addObject(new Player(100,100,ObjectID.Player));
		loader = new BufferedImageLoader();
		//level = loader.loadImage(levels[0]); // if it loads the image correctly, there shouldn't be error. ������Ʈ ������Ƽ���� �����н��� res ������ �߱� ������ �� path�� ������ �� ����.
		cloud = loader.loadImage("/cloud.png");
		mapBuild(loader.loadImage(levels[level - 1]));
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
				int pixel = image.getRGB(x,y); //�̹����� ����� ������ �ȼ����� analyze�Ѵ�.
				
				//Java���� int�� 32bit�̴�. 255 = 00000000 00000000 00000000 11111111
				//���⼭ ù��° byte�� ������ ������ 3byte�� red / green / blue�� ����(�������)
				
				// the hex literal 0xFF is an equal int(255)
				
				//pixel�� ����� ���� 16��Ʈ ���������� �ű����μ�, ���尡 ����Ǵ� ���Ǹ� interpret�� �� �ְԵǾ���.
				//16��Ʈ�� ���������� �Ű��� bit ���� 1111 1111 �� &(1 + 1 = 1)�� ������, ���忡 ���� ����� ���� ���� ���� �� �ִ�.
				int red = (pixel >> 16) & 0xff; 
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				//rgb�� ��� 255�̸� white��
				if(red == 255 && green == 255 && blue == 255)
					handler.addObject(new Block(x * 32, y * 32, ObjectID.Block));
				if(red == 255 && green == 0 && blue == 0)
					handler.addObject(new Flag(x * 32, y * 32, ObjectID.Flag));
			}
		}
	}
	
	public static Texture getInstance() {
		return tex;
	}
	
	private void tick() {
		handler.tick();
		if(Player.LEVEL_COMPLETED) {
			level++;
			mapBuild(loader.loadImage(levels[level - 1]));
			handler.addObject(new Player(100,100,ObjectID.Player));
			Player.LEVEL_COMPLETED = false;
		}
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
		
		g.setColor(new Color(77, 181, 219));
		g.fillRect(0,0,Game.WIDTH, Game.HEIGHT);
		
		
		g2d.translate(cam.getX(), cam.getY()); // ���� ���α׷��ֿ��� ������ 0,0�� ������ �ȴ�. �׷���, translate method�� �̷��� ������ origination�� �ٲ� �� �ְ� ���ش�. 
											   // �� g2d,translate(Game.WIDHT/2, Game.HEIGHT/2)�� ���Ǹ�, bg drawing�� �������� ���̻� 0,0�� �ƴ϶� ȭ���� �߽��� �ȴ�. 
											   // It is additive, https://coderanch.com/t/333375/java/Graphics-translate

		
			//���� �����ϴ� ���� �ִ� �� ����. Ŭ������ x��ǥ�� �״�� 0���� ��ο찡 ������ �ȴ�. ��ǥ������ ī�޶��� ��ġ������ ������ �ʴ´�, ���밪�� ���� �ִ�. 
			//ī�޶� ���� -x ��ǥ���� �����Ѵٰ� ���� ���� ���� �� �ϴ�. �׷��� ������ ī�޶�� ��������, object���� �ڱ���� �ڸ��� ����Ѵ�. 
			for(int xx = 0; xx < getWidth() * 2.5; xx += getWidth() / 2) {
				g.drawImage(cloud, xx, 40, null);
			}
				handler.render(g);	
			
		//g2d.translate(-cam.getX(), -cam.getY()); //��ǻ� �̰��� ���, �Ȱ��� �۵��Ѵ�. https://www.youtube.com/watch?v=vdcOIwkB6dA&list=PLWms45O3n--54U-22GDqKMRGlXROOZtMx&index=10 (6:25�� ������)
		
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

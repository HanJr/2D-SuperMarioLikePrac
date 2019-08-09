package com.hanjang.supermariolike;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] image;
	private BufferedImage currentImage;
	
	private int speed;
	private int speedCount = 0;
	
	private int currentIndex = 0;
	private int numOfFrame;
	
	//...�󸶳� ���� arg�� ���� �� �𸦶� ����Ѵ�. �� �׳� ����Ʈ�� ��̸� �ѱ��� �ʴ°�?
	public Animation(int speed, BufferedImage... args) {
		this.speed = speed;
		image = new BufferedImage[args.length];
		
		for(int i = 0; i < args.length; i++) {
			image[i] = args[i];
		}
		
		numOfFrame = args.length;
		currentImage = image[currentIndex];
	}
	
	public void runAnimation() {
		speedCount++;
		
		if(speedCount > speed) {
			speedCount = 0;
			nextFrame();
		}
	}
	
	public void nextFrame() {
		currentIndex++;
		
		if(currentIndex >= numOfFrame)
			currentIndex = 0;
		
		currentImage = image[currentIndex];
	}
	
	public void drawImage(Graphics g, int x, int y) {
		g.drawImage(currentImage, x, y, null);
	}
	
}

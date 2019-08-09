package com.hanjang.supermariolike;

import java.awt.image.BufferedImage;

public class Texture {
	BufferedImage[] objects = new BufferedImage[4]; 
	
	public Texture() {
		BufferedImageLoader loader = new BufferedImageLoader();
		SpriteSheet ss = new SpriteSheet(loader.loadImage("/block spritesheet.png"));
		objects[0] = ss.grabImage(1,1,32,32); //block
		objects[1] = ss.grabImage(2,1,32,32); //player 1
		objects[2] = ss.grabImage(3,1,32,32); //player 2
		objects[3] = ss.grabImage(4,1,32,32); //player 3
	}
	
}

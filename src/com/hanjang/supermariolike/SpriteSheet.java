package com.hanjang.supermariolike;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage grabImage(int x, int y, int w, int h) {
		return image.getSubimage((x * w) - w , (y * h) - h, w, h);
	}
}

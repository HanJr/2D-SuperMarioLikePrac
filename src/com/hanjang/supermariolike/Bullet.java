package com.hanjang.supermariolike;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.hanjang.framework.GameObject;
import com.hanjang.framework.ObjectID;

public class Bullet extends GameObject{
	
	public Bullet(float x, float y, ObjectID objectID, int velX) {
		super(x, y, objectID);
		// TODO Auto-generated constructor stub
		this.setVelX(velX);
	}

	@Override
	public void tick(LinkedList<GameObject> objectList) {
		// TODO Auto-generated method stub
		x += velX;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int) x, (int) y, 16, 16);
	}
	
}

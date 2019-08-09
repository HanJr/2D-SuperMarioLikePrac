package com.hanjang.supermariolike;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.hanjang.framework.GameObject;
import com.hanjang.framework.ObjectID;

public class Flag extends GameObject{

	public ObjectID objectID;
	
	public Flag(float x, float y, ObjectID objectID) {
		super(x, y, objectID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(LinkedList<GameObject> objectList) {
		return;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int)y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}

package com.hanjang.supermariolike;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.hanjang.framework.GameObject;
import com.hanjang.framework.ObjectID;

public class Block extends GameObject{

	public Block(float x, float y, ObjectID objectID) {
		super(x, y, objectID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(LinkedList<GameObject> objectList) {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect((int)x, (int)y, 32, 32);
	}

	//나는 boolean을 하려고 했는데 intersect가 아니기 때문에 필요가 없다는 것을 깨달았다. 어떻게하면 현재 이 블락 오브젝트의 크기의 밸류를 모두 한번에 넘길수 있을까 생각해보았다.
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}

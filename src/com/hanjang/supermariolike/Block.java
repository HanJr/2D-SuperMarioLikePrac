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

	//���� boolean�� �Ϸ��� �ߴµ� intersect�� �ƴϱ� ������ �ʿ䰡 ���ٴ� ���� ���޾Ҵ�. ����ϸ� ���� �� ��� ������Ʈ�� ũ���� ����� ��� �ѹ��� �ѱ�� ������ �����غ��Ҵ�.
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}

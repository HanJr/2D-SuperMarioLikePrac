package com.hanjang.supermariolike;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.hanjang.framework.GameObject;
import com.hanjang.framework.ObjectID;

public class Player extends GameObject{

	private int width = 32;
	private int height = 64;
	private float gravity = 0.5f;//�߷��� 0.5�μ� �ѹ��� velY�� 0���� ������ ���ϱ� ������, ������� ������ �����ϴ� 
	private float MAX_SPEED = 10;
	
	public Player(float x, float y, ObjectID objectID) {
		super(x, y, objectID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(LinkedList<GameObject> objectList) {
		x += velX; //super class�� field�� �׳� ��밡���ϴ�. private�� �ƴ϶�, protected�� ���Ǿ �׷� �� ����. 
		y += velY; //super class�� field�� �׳� ��밡���ϴ�. private�� �ƴ϶�, protected�� ���Ǿ �׷� �� ����. 
		
		if(jumping || falling)
			velY += gravity; // ���������� ������ �������°� �ڵ�Ǿ��ִ�.
		
		if(velY > MAX_SPEED)
			velY = MAX_SPEED;
		
		collision(objectList);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect((int)x, (int)y, width, height);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.WHITE);
		
		g2d.draw(getBounds());
		g2d.draw(getBoundsTop());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
	}

	public void collision(LinkedList<GameObject> objectList) {
		for(int i = 0; i < objectList.size(); i++) {
			if(objectList.get(i).getObjectID() == ObjectID.Block) {
				if(getBounds().intersects(objectList.get(i).getBounds())){
					y = objectList.get(i).getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				}
				else if(getBoundsTop().intersects(objectList.get(i).getBounds())){
					y = objectList.get(i).getY() + 32;
					velY = 0;
				}
				else if(getBoundsRight().intersects(objectList.get(i).getBounds())){
					x = objectList.get(i).getX() - 32;
				}
				else if(getBoundsLeft().intersects(objectList.get(i).getBounds())){
					x = objectList.get(i).getX() + 32;
				}
				else {
					falling = true;
				}
			}
		}
	}
	
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x + width / 4, (int)y + (height / 2), width / 2, height / 2);
	}
	public Rectangle getBoundsTop() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x + width / 4, (int)y, width / 2, height / 2);
	}
	public Rectangle getBoundsRight() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x + 3 * (width / 4) , (int)y + 4, width / 4, height - 8);
	}
	public Rectangle getBoundsLeft() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x, (int)y + 4, width / 4, height - 8);
	}


}

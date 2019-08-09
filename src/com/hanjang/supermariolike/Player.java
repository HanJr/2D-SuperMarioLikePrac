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
	private int height = 32;
	private float gravity = 0.5f;//중력이 0.5로서 한번에 velY를 0으로 만들지 못하기 때문에, 어느정도 점프가 가능하다 
	private float MAX_SPEED = 10;
	private Animation animation = new Animation(1, Game.tex.objects[1], Game.tex.objects[2],Game.tex.objects[3]);
	public static boolean LEVEL_COMPLETED = false;
	
	public Player(float x, float y, ObjectID objectID) {
		super(x, y, objectID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(LinkedList<GameObject> objectList) {

		if(velX != 0)
			animation.runAnimation();
		x += velX; //super class의 field를 그냥 사용가능하다. private이 아니라, protected가 사용되어서 그런 것 같다. 
		y += velY; //super class의 field를 그냥 사용가능하다. private이 아니라, protected가 사용되어서 그런 것 같다. 
		
		if(jumping || falling)
			velY += gravity; // 떨어질수록 빠르게 떨어지는게 코드되어있다.
		
		if(velY > MAX_SPEED)
			velY = MAX_SPEED;
		
		collision(objectList);
	}
	
	@Override
	public void render(Graphics g) {
		if(velX == 0)
			g.drawImage(Game.tex.objects[1], (int) x, (int) y, null);
		else
			animation.drawImage(g, (int) x, (int) y); 
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
			else if(objectList.get(i).getObjectID() == ObjectID.Flag) {
				boolean touching = false;
				
				if(getBounds().intersects(objectList.get(i).getBounds())){
					touching = true;
				}
				else if(getBoundsTop().intersects(objectList.get(i).getBounds())){
					touching = true;
				}
				else if(getBoundsRight().intersects(objectList.get(i).getBounds())){
					touching = true;
				}
				else if(getBoundsLeft().intersects(objectList.get(i).getBounds())){
					touching = true;
				}
				
				if(touching) {
					LEVEL_COMPLETED = true;
					Game.handler.getObjectList().clear();
				}
				
				touching = false;
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

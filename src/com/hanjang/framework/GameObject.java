package com.hanjang.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class GameObject {

	protected float x;//protected는 무엇인가?
	protected float y;
	protected float velX, velY = 0;
	protected boolean falling = true;
	protected boolean jumping = false;
	protected ObjectID objectID;//enum 클래스는 어떻게 사용 할 수 있을까?
	protected int isFacing = 1; // 1 is right
	
	public GameObject(float x, float y, ObjectID objectID) {
		this.x = x;
		this.y = y;
		this.objectID = objectID;
	}
	
	public abstract void tick(LinkedList<GameObject> objectList);
	
	public abstract void render(Graphics g);
	
	//abstract method에는 {}이 필요 없다는 것을 까먹었었다. 
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void setIsFacing(int isFacing) {
		this.isFacing = isFacing;
	}

	public int getIsFacing() {
		return isFacing;
	}
	
	
	public ObjectID getObjectID() {
		return objectID;
	}
	
	
	public abstract Rectangle getBounds();
	
}

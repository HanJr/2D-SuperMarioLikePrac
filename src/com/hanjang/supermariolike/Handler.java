package com.hanjang.supermariolike;

import java.awt.Graphics;
import java.util.LinkedList;

import com.hanjang.framework.GameObject;
import com.hanjang.framework.ObjectID;

public class Handler {
	private LinkedList<GameObject> objectList = new LinkedList<GameObject>();//왜 여기서 initiate을 해야하나?
	private GameObject tempObj;
	private Camera cam;
	
	public Handler(Camera cam) {
		this.cam = cam;//why should we pass cam as an argument? it is not a GameObject. 
	}
	
	public void tick() {
		for(int i = 0; i < objectList.size(); i++) {
			tempObj = objectList.get(i); 
			tempObj.tick(objectList);
			if(tempObj.getObjectID() == ObjectID.Player)
				cam.tick(tempObj);
		}		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < objectList.size(); i++) {
			tempObj = objectList.get(i);
			tempObj.render(g);
		}	
	}
	
	public void addObject(GameObject object) {
		objectList.add(object);
	}
	
	public void removeObject(GameObject object) {
		objectList.remove(object);
	}
/*	
	public void createLevel() {
		for(int x = 0; x < Game.WIDTH + 32; x += 32) {
			addObject(new Block(x, Game.HEIGHT - 32, ObjectID.Block));
		}
		
		for(int x = 128 ; x < Game.WIDTH - 128 ; x += 32) {
			addObject(new Block(x, Game.HEIGHT - 160, ObjectID.Block));
		}
		
		for(int y = 0 ; y < Game.HEIGHT - 32 ; y += 32) {
			addObject(new Block(0, y, ObjectID.Block));
		}
	}
*/	
	public LinkedList<GameObject> getObjectList() {
		return objectList;
	}
}

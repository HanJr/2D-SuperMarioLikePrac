package com.hanjang.supermariolike;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.hanjang.framework.ObjectID;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		//I think it is more efficient to not add the Player instance in the handler
		for(int i = 0; i < handler.getObjectList().size(); i++) {
			if(handler.getObjectList().get(i).getObjectID() == ObjectID.Player) {
				if(key == KeyEvent.VK_D) {
					handler.getObjectList().get(i).setVelX(5);
					handler.getObjectList().get(i).setIsFacing(1);
				}
				if(key == KeyEvent.VK_A) {
					handler.getObjectList().get(i).setVelX(-5);
					handler.getObjectList().get(i).setIsFacing(-1);
				}
				if(key == KeyEvent.VK_W && !handler.getObjectList().get(i).isJumping()) {
					handler.getObjectList().get(i).setVelY(-10);
					handler.getObjectList().get(i).setJumping(true);
				}
				if(key == KeyEvent.VK_SPACE) {
					handler.addObject(new Bullet(handler.getObjectList().get(i).getX(), handler.getObjectList().get(i).getY(), ObjectID.Bullet, handler.getObjectList().get(i).getIsFacing() * 5));
					System.out.println("FACING: " + handler.getObjectList().get(i).getIsFacing());
				}
			}
				
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.getObjectList().size(); i++) {
			if(handler.getObjectList().get(i).getObjectID() == ObjectID.Player) {
				if(key == KeyEvent.VK_D)
					handler.getObjectList().get(i).setVelX(0);
				if(key == KeyEvent.VK_A)
					handler.getObjectList().get(i).setVelX(0);
			}
				
		}	
	}
}

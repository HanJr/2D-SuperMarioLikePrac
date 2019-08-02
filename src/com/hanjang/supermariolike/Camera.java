package com.hanjang.supermariolike;

import com.hanjang.framework.GameObject;

public class Camera {

	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject player) {
		//x--;  �� x�� �۾�������, Game class�� g2d.translate(cam.getX(), cam.getY())�� ī�޶� ���� ������ ���� ��ó�� ���ϱ�? 
		// ī�޶�� ��ǻ� �����̶�� ����ȴ�. ī�޶� �����̶�� �����Ͽ�, ����ϸ� ȭ���� ������ �����ϴ°Ͱ��� ���� �� ������? �װ��� ī�޶� ������ ��繰ü���� ���ݾ� �ڷ� �����̸� �Ǵ� ���̴�.
		//�� ī�޶� �̵��ϴ� ���� �ƴ϶�, ī�޶� ������ ��� ��ü�� �ڷ� ���������μ� �������ϰ� ����� ���̶�� �� �� �ִ�.
		float xTarg = -player.getX() + Game.WIDTH / 2; 
		x += (xTarg - x) * 0.05f; //tweening algorithm(Linear interpolation), how? https://gamedev.stackexchange.com/questions/138756/smooth-camera-movement-java
	}

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
	
}

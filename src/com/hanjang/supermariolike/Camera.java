package com.hanjang.supermariolike;

import com.hanjang.framework.GameObject;

public class Camera {

	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject player) {
		//x--;  왜 x가 작아질수록, Game class의 g2d.translate(cam.getX(), cam.getY())는 카메라가 점점 앞으로 가는 것처럼 보일까? 
		// 카메라는 사실상 고정이라고 보면된다. 카메라가 고정이라는 전제하에, 어떻게하면 화면이 앞으로 진행하는것같이 보일 수 있을까? 그것은 카메라를 제외한 모든물체들이 조금씩 뒤로 움직이면 되는 것이다.
		//즉 카메라가 이동하는 것이 아니라, 카메라를 제외한 모든 물체를 뒤로 움직임으로서 착각을하게 만드는 것이라고 볼 수 있다.
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

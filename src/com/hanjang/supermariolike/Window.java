package com.hanjang.supermariolike;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	
	private int width;
	private int height;
	private String title;
	private Game game;
	
	public Window(int width, int height, String title, Game game) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.game = game;
	
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(width, height));
		frame.setTitle(title);
		frame.add(game);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		game.start();
		
		
	}
	
	
	
	/*내가 저질렀던 실수, static method에서는 static이 아닌 private variable을 사용할 수 없었다. 체크해보자. 
	public static void main(String[] args) {
		
		game.setPreferredSize(new Dimension(width, height, null));
		
		JFrame frame = new JFrame(TITLE);
		frame.add(game); //game이 canvas이기 때문에, 그렇다면 JFrame자체에는 width와 height이 사용 가능한가?
	}
	*/
}

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
	
	
	
	/*���� �������� �Ǽ�, static method������ static�� �ƴ� private variable�� ����� �� ������. üũ�غ���. 
	public static void main(String[] args) {
		
		game.setPreferredSize(new Dimension(width, height, null));
		
		JFrame frame = new JFrame(TITLE);
		frame.add(game); //game�� canvas�̱� ������, �׷��ٸ� JFrame��ü���� width�� height�� ��� �����Ѱ�?
	}
	*/
}

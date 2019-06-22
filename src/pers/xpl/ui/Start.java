/*
 * auther:Xie Peiliang
 * create date:2019.4.4
 * describe:项目启动
 */
package pers.xpl.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Start {
	public static void main(String[] args) {
		EventQueue.invokeLater(() ->
		{
			//MyFrame frame = new MyFrame();
			Login frame = new Login();

			frame.setLocationRelativeTo(null);            //居中
			frame.setTitle("回转库库存管理及入出库调度");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
		});
	}
}

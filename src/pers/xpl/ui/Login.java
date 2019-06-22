package pers.xpl.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.xpl.Global;
import pers.xpl.config.JavaConfig;
import pers.xpl.util.FunctionsOfMysql;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JRadioButton;

/*
 * 登录界面
 */
public class Login extends JFrame implements ActionListener{
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton button;
	private ButtonGroup buttonGroup;

	public Login() {
		setSize(540, 260);
		getContentPane().setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label = new JLabel("回转库库存管理及入出度调度系统");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("华文琥珀", Font.PLAIN, 29));

		JLabel label_2 = new JLabel("账号：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel label_3 = new JLabel("密码：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 13));

		textField = new JTextField();
		textField.setColumns(10);

		passwordField = new JPasswordField();

		button = new JButton("登录");
		button.setFont(new Font("楷体", Font.BOLD, 13));
		button.addActionListener(this);
		buttonGroup = new ButtonGroup();

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(47)
								.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(42))
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(179)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(label_2)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(textField, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(label_3)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(button)
														.addComponent(passwordField))))
								.addContainerGap(205, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(34)
								.addComponent(label)
								.addGap(30)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(label_2)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(label_3)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(26)
								.addComponent(button)
								.addContainerGap(45, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button) {
			if(textField.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入正确的账号！");
				return;
			}
			if(passwordField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入密码！");
				return;
			}


			ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
			FunctionsOfMysql functionsOfMysql = (FunctionsOfMysql) context.getBean("functionsOfMysql");
			int flag = 0;
			try {
				flag = functionsOfMysql.check_login(textField.getText(), passwordField.getText());
			}catch (SQLException ec){
				ec.printStackTrace();
			}
			if(flag == 1){
				JOptionPane.showMessageDialog(null,"登陆成功！");
				MyFrame myFrame = new MyFrame();
				myFrame.setLocationRelativeTo(null);
				myFrame.setTitle("回转库库存管理及入出库调度");
				myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.dispose();
				myFrame.setVisible(true);
				Global.username = textField.getText();
			}
			else if(flag == 0){
				JOptionPane.showMessageDialog(null, "密码错误!");
			}
			else if(flag == -1){
				JOptionPane.showMessageDialog(null, "账号不存在！");
			}
//			else if(flag == -2){
//				JOptionPane.showMessageDialog(null, "账号类型错误!");
//			}
		}
	}
}

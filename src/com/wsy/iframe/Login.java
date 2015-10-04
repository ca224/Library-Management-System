package com.wsy.iframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.wsy.Library;
import com.wsy.dao.Dao;
import com.wsy.model.Operater;
import com.wsy.util.CreatecdIcon;
import com.wsy.util.MyDocument;

public class Login extends JApplet {

	private class ResetAction implements ActionListener {
		public void actionPerformed(final ActionEvent e){
			Username.setText("");
			Password.setText("");			
		}
	}
	
	private class TypeAction implements ActionListener {
		public void actionPerformed(final ActionEvent e){
			if(Usertype == "ADMIN"){
			Label.setText("CardID");
			Username.setText("");
			Password.setText("");
			Password.setEditable(false);
			Usertype = "READER";
			} else {
				Label.setText("Username");
				Username.setText("");
				Password.setText("");
				Password.setEditable(true);
				Usertype = "ADMIN";
			}
		}
	}
	
	class LoginAction implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			User = Dao.check(Username.getText(), Password.getText(), Usertype);
			if (User.getName() != null){
				try {
					Frame = new Library();
					Frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Login Failed");
				Username.setText("");
				Password.setText("");
			}
		}
	}
	
	private JPasswordField Password;
	private JTextField Username;
	private JLabel Label;
	private JButton Login;
	private JButton Reset;
	private JButton Type;
	private JPanel Panel_1;
	private JPanel Panel;
	private static Operater User;
	public static Library Frame;
	private String Usertype = "ADMIN";
	
	public void init(){
		final BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(10);
		getContentPane().setLayout(borderLayout);
		setBounds(100, 100, 300, 194);

		Panel = new JPanel();
		Panel.setLayout(new BorderLayout());
		Panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(Panel);

		final JPanel panel_2 = new JPanel();
		final GridLayout gridLayout = new GridLayout(0, 2);
		gridLayout.setHgap(5);
		gridLayout.setVgap(20);
		panel_2.setLayout(gridLayout);
		Panel.add(panel_2);

		Label = new JLabel();
		Label.setHorizontalAlignment(SwingConstants.CENTER);
		Label.setPreferredSize(new Dimension(0, 0));
		Label.setMinimumSize(new Dimension(0, 0));
		panel_2.add(Label);
		Label.setText("Username");

		Username = new JTextField(20);
		Username.setPreferredSize(new Dimension(0, 0));
		panel_2.add(Username);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_1);
		label_1.setText("Password");

		Password = new JPasswordField(20);
		Password.setDocument(new MyDocument(6));
		Password.setEchoChar('*');
		Password.addKeyListener(new KeyAdapter() {
		
		public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == 10)
					Login.doClick();
			}
		});
		panel_2.add(Password);

		Panel_1 = new JPanel();
		Panel.add(Panel_1, BorderLayout.SOUTH);

		Login=new JButton();
		Login.addActionListener(new LoginAction());
		Type=new JButton();
		Type.addActionListener(new TypeAction());
		Type.setText("Change User Type");
		
		
		Login.setText("Login");
		Panel_1.add(Login);
		Reset=new JButton();
		Reset.addActionListener(new ResetAction());
		
		Reset.setText("Reset");
		Panel_1.add(Reset);
		Panel_1.add(Type);

		final JLabel imgLabel = new JLabel();
		ImageIcon loginIcon=CreatecdIcon.add("login.jpg");
		imgLabel.setIcon(loginIcon);
		imgLabel.setOpaque(true);
		imgLabel.setBackground(Color.WHITE);
		Panel.add(imgLabel, BorderLayout.NORTH);
		this.add(Panel);
		setVisible(true);
	}
	public static Operater getUser() {
		return User;
	}
	public static void setUser(Operater user) {
		User = user;
	}

}

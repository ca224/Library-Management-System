package com.wsy.iframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.wsy.dao.Dao;
import com.wsy.util.CreatecdIcon;

public class RAdd extends JInternalFrame {
	
	private JTextField Readername;
	private JTextField Readerid;
	private JTextField Readeradd;
	private JTextField Readertype;	

	/**
	 * Create the frame
	 */
	public RAdd() {
		super();
		setTitle("Reader Add");
		setIconifiable(true);
		setClosable(true);
		setBounds(30, 30, 500, 350);

		final JLabel logoLabel = new JLabel();
		ImageIcon readerAddIcon=CreatecdIcon.add("readerAdd.jpg");
		logoLabel.setIcon(readerAddIcon);
		logoLabel.setOpaque(true);
		logoLabel.setBackground(Color.CYAN);
		logoLabel.setPreferredSize(new Dimension(400, 60));
		getContentPane().add(logoLabel, BorderLayout.NORTH);

		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		getContentPane().add(panel);

		final JPanel readerpanel = new JPanel();
		final GridLayout gridLayout = new GridLayout(0, 4);
		readerpanel.setBorder(new EmptyBorder(50, 10, 50, 10));
		gridLayout.setVgap(50);
		gridLayout.setHgap(10);
		readerpanel.setLayout(gridLayout);
		readerpanel.setPreferredSize(new Dimension(450, 200));
		panel.add(readerpanel);
		
		final JLabel label_1 = new JLabel();
		label_1.setText("CARDID");
		readerpanel.add(label_1);

		Readerid = new JTextField();
		Readerid.setText(String.valueOf(Dao.countId("READERID")+1));
		Readerid.setEditable(false);
		readerpanel.add(Readerid);
		
		final JLabel label_2 = new JLabel();
		label_2.setText("TYPE");
		readerpanel.add(label_2);

		Readertype = new JTextField();
		readerpanel.add(Readertype);

		final JLabel label_3 = new JLabel();
		label_3.setText("NAME");
		readerpanel.add(label_3);

		Readername = new JTextField();
		readerpanel.add(Readername);

		final JLabel label_4 = new JLabel();
		label_4.setText("ADDRESS");
		readerpanel.add(label_4);

		Readeradd = new JTextField();
		readerpanel.add(Readeradd);

		final JPanel buttonpanel = new JPanel();
		buttonpanel.setPreferredSize(new Dimension(450, 100));
		panel.add(buttonpanel);

		final JButton save = new JButton();
		buttonpanel.add(save);
		save.setText("Save");
		save.addActionListener(new SaveActionListener());
		

		final JButton close = new JButton();
		buttonpanel.add(close);
		close.setText("Close");
		close.addActionListener(new CloseActionListener());
		setVisible(true);
	}

	class CloseActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			doDefaultCloseAction();
		}
	}
	
	class SaveActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Readername.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "!READER NAME CAN'T BE NULL");
				return;
			}
			String Rid = String.valueOf(Dao.countId("READERID")+1);
			int i=Dao.InsertReader(Rid,Readertype.getText().trim(), Readername.getText().trim(), Readeradd.getText().trim());
			System.out.println(i);
			if(i==1){
				JOptionPane.showMessageDialog(null, "Database Operation Successed");
				doDefaultCloseAction();
			} else {
				JOptionPane.showMessageDialog(null, "Database Operation Failed");
			}
		}
	}

}

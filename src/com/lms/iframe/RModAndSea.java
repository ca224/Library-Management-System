package com.lms.iframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.lms.dao.Dao;
import com.lms.model.Reader;
import com.lms.util.CreatecdIcon;

public class RModAndSea extends JInternalFrame {

	private JPanel Panel_1;
	private JScrollPane Scrollpane;
	private JTable Table;
	private JTextField Readername;
	private JTextField Readertype;
	private JTextField Readeradd;
	private JTextField Readerid;
	private int Selrow = -1;
	private List Resultlist;
	
	private String[] Columnname={ "READERID", "RTYPE", "RNAME", "ADDRESS"};

	private String Rid;
	private Object[][] Results;
	
	/**
	 * Create the frame
	 */
	private Object[][] getReaderStates(List list){
		Results=new Object[list.size()][Columnname.length];
		for(int i=0;i<list.size();i++){
			Reader reader=(Reader)list.get(i);
			Results[i][0]=reader.getRid();
			Results[i][1]=reader.getRtype();
			Results[i][2]=reader.getName();
			Results[i][3]=reader.getRadd();
			if(reader.getRadd() == null){
				Results[i][3]="";
			}
			if(reader.getRtype() == null){
				Results[i][1]="";
			}
		}
		return Results;
	         		
	}
	public RModAndSea() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("Reader Modi And Del");
		setBounds(30, 30, 500, 350);

		final JLabel logoLabel = new JLabel();
		ImageIcon readerModiAndDelIcon=CreatecdIcon.add("readerModiAndDel.jpg");
		logoLabel.setIcon(readerModiAndDelIcon);
		logoLabel.setBackground(Color.WHITE);
		logoLabel.setOpaque(true);
		logoLabel.setPreferredSize(new Dimension(400, 80));
		getContentPane().add(logoLabel, BorderLayout.NORTH);

		Panel_1 = new JPanel();
		Panel_1.setLayout(new BorderLayout());
		getContentPane().add(Panel_1, BorderLayout.SOUTH);

		Scrollpane = new JScrollPane();
		getContentPane().add(Scrollpane, BorderLayout.CENTER);

		
		final DefaultTableModel model=new DefaultTableModel();
		
		Table = new JTable();
		Scrollpane.setViewportView(Table);
		Table.addMouseListener(new TableListener());

		final JPanel readerpanel = new JPanel();
		readerpanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		final GridLayout gridLayout = new GridLayout(0, 4);
		gridLayout.setVgap(9);
		readerpanel.setLayout(gridLayout);
		Panel_1.add(readerpanel, BorderLayout.NORTH);
		
		final JLabel label_4 = new JLabel();
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setText("CARDID");
		readerpanel.add(label_4);

		Readerid = new JTextField();
		readerpanel.add(Readerid);
		Readerid.setEditable(false);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setText("TYPE");
		readerpanel.add(label_1);

		Readertype = new JTextField();
		readerpanel.add(Readertype);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setText("NAME");
		readerpanel.add(label_2);
		
		Readername = new JTextField();
		readerpanel.add(Readername);

		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setText("ADDRESS");
		readerpanel.add(label_3);

		Readeradd = new JTextField();
		readerpanel.add(Readeradd);

		final JPanel buttonpanel = new JPanel();
		buttonpanel.setMaximumSize(new Dimension(0, 0));
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(4);
		buttonpanel.setLayout(flowLayout);
		Panel_1.add(buttonpanel,BorderLayout.SOUTH);
		
		final JButton buttonDel = new JButton();
		buttonpanel.add(buttonDel);
		buttonDel.setText("Delete");
		buttonDel.addActionListener(new DelButtonListener());

		final JButton button = new JButton();
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonpanel.add(button);
		button.setText("Modify");
		button.addActionListener(new ModiButtonListener());
		
		final JButton buttonsea = new JButton();
		buttonpanel.add(buttonsea);
		buttonsea.setText("Search");
		buttonsea.addActionListener(new SeaButtonListener());
		
		final JButton buttonclose = new JButton();
		buttonpanel.add(buttonclose);
		buttonclose.setText("Close");
		buttonclose.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				doDefaultCloseAction();
			}
		});

		setVisible(true);
	}
	
	class TableListener extends MouseAdapter {
		public void mouseClicked(final MouseEvent e) {
			Selrow = Table.getSelectedRow();
			Rid = Table.getValueAt(Selrow, 0).toString().trim();
			Readertype.setText(Table.getValueAt(Selrow, 1).toString().trim());			
			Readername.setText(Table.getValueAt(Selrow, 2).toString().trim());
			Readeradd.setText(Table.getValueAt(Selrow, 3).toString().trim());		
		}
	}

	private final class DelButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			int j;
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			String message = "Delete READERID: " + Rid + " ?";
			int i = JOptionPane.showConfirmDialog(null, message,
					"Casacade Delete!", JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
				j = Dao.del("READER",Rid,"");
				if (j == 1) {
					JOptionPane.showMessageDialog(null,
							"Database Operation Successed!");
					doDefaultCloseAction();
				} else {
					JOptionPane.showMessageDialog(null,
							"Database Operation Failed!");
					doDefaultCloseAction();
				}
			}
		}
	}
	class ModiButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			if (Readername.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "!READER NAME CAN'T BE NULL");
				return;
			}
			String message = "Update RID: " + Rid + " ?";
			int i = JOptionPane.NO_OPTION,j = 0;
			i = JOptionPane.showConfirmDialog(null, message, "Update Data!",
					JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
				j = Dao.UpdateReader(Rid, Readertype.getText().trim(), Readername.getText().trim(), Readeradd.getText().trim());
			} else {
				return;
			}
			if (j == 1) {
				JOptionPane.showMessageDialog(null,
						"Database Operation Successed!");
				doDefaultCloseAction();
			} else {
				JOptionPane.showMessageDialog(null,
						"Database Operation Failed!");
				doDefaultCloseAction();
			}
		}
	}
	
	class SeaButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			String Rid, Rtype, Rname, Add;
			if (Readerid.getText().toString().equals("")) {
				Rid = "";
			} else {
				Rid = " AND READERID ='" + Readerid.getText().trim() + "'";
			}
			if (Readername.getText().toString().equals("")) {
				Rname = "";
			} else {
				Rname = " AND RNAME ='" + Readername.getText().trim() + "'";
			}
			if (Readertype.getText().toString().equals("")) {
				Rtype = "";
			} else {
				Rtype = " AND RTYPE ='" + Readertype.getText().trim() + "'";
			}
			if (Readeradd.getText().toString().equals("")) {
				Add = "";
			} else {
				Add = " AND ADDRESS LIKE '%" + Readeradd.getText().trim() + "%'";
			}
			Resultlist = Dao.searchReader(Rid, Rtype, Rname, Add);
			Results = getReaderStates(Resultlist);
			Scrollpane.setVisible(false);
			Table = new JTable(Results, Columnname);
			Table.addMouseListener(new TableListener());
			Scrollpane.setViewportView(Table);
			Scrollpane.setVisible(true);
		}
	}
}

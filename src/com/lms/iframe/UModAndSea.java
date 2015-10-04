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
import com.lms.model.Publisher;
import com.lms.util.CreatecdIcon;

public class UModAndSea extends JInternalFrame {

	private JPanel Panel_1;
	private JScrollPane Scrollpane;
	private JTable Table;
	private JTextField Name;
	private JTextField Add;
	private JTextField Id;
	private int Selrow = -1;
	private List Resultlist;
	
	private String[] Columnname={ "ID", "NAME", "ADDRESS"};

	private String ID;
	private Object[][] Results;
	private String Type;

	private Object[][] getUtilStates(List list){
		Results=new Object[list.size()][Columnname.length];
		for(int i=0;i<list.size();i++){
			Publisher reader=(Publisher)list.get(i);
			Results[i][0]=reader.getId();
			Results[i][1]=reader.getName();
			Results[i][2]=reader.getAdd();
			if(reader.getAdd() == null){
				Results[i][2]="";
			}
		}
		return Results;
	         		
	}
	public UModAndSea(String type) {
		super();
		this.Type = type;
		setIconifiable(true);
		setClosable(true);
		setTitle("Utility Management");
		setBounds(30, 30, 500, 350);

		final JLabel logoLabel = new JLabel();
		ImageIcon readerModiAndDelIcon=CreatecdIcon.add("utility.jpg");
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
		label_4.setText("ID:");
		readerpanel.add(label_4);

		Id = new JTextField();
		readerpanel.add(Id);
		Id.setEditable(false);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setText("Name:");
		readerpanel.add(label_2);
		
		Name = new JTextField();
		readerpanel.add(Name);

		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setText("ADDRESS:");
		readerpanel.add(label_3);

		Add = new JTextField();
		readerpanel.add(Add);

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
			ID = Table.getValueAt(Selrow, 0).toString().trim();
			Id.setText(Table.getValueAt(Selrow, 0).toString().trim());
			Name.setText(Table.getValueAt(Selrow, 1).toString().trim());
			Add.setText(Table.getValueAt(Selrow, 2).toString().trim());		
		}
	}

	private final class DelButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			int j;
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			String message = "Delete ID: " + ID + " ?";
			int i = JOptionPane.showConfirmDialog(null, message,
					"Casacade Delete!", JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
				if (Type == "PUBLISHER") {
					j = Dao.del("PUBLISHER", ID, "");
				} else {
					j = Dao.del("BRANCH", ID, "");
				}
				if (j == 1) {
					JOptionPane.showMessageDialog(null,
							"Database Operation Successed!");
					reNewTable();
				} else {
					JOptionPane.showMessageDialog(null,
							"Database Operation Failed!");
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
			String message = "Update ID: " + ID + " ?";
			int i = JOptionPane.NO_OPTION,j = 0;
			i = JOptionPane.showConfirmDialog(null, message, "Update Data!",
					JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
				if(Type == "PUBLISHER"){
				j = Dao.updPub(ID, Name.getText().trim(), Add.getText().trim());
				} else {
				j = Dao.updBranch(ID, Name.getText().trim(), Add.getText().trim());	
				}
			} else {
				return;
			}
			if (j == 1) {
				JOptionPane.showMessageDialog(null,
						"Database Operation Successed!");
				reNewTable();
			} else {
				JOptionPane.showMessageDialog(null,
						"Database Operation Failed!");
			}
		}
	}
	
	class SeaButtonListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			String id, name, add;
			if (Id.getText().toString().equals("")) {
				id = "";
			} else {
				if(Type == "PUBLISHER"){
				id = " AND PUBLISHERID ='" + Id.getText().trim() + "'";
				} else {
				id = " AND LIBID ='" + Id.getText().trim() + "'";	
				}
			}
			if (Name.getText().toString().equals("")) {
				name = "";
			} else {
				if(Type == "PUBLISHER"){				
				name = " AND PUBNAME ='" + Name.getText().trim() + "'";
				} else {
					name = " AND LNAME ='" + Name.getText().trim() + "'";					
				}
			}
			if (Add.getText().toString().equals("")) {
				add = "";
			} else {
				if( Type == "PUBLISHER"){
				add = " AND ADDRESS LIKE '%" + Add.getText().trim() + "%'";
				} else {
					add = " AND LLOCATION LIKE '%" + Add.getText().trim() + "%'";					
				}
			}
			if( Type == "PUBLISHER"){
			Resultlist = Dao.searchPub(id, name, add);
			} else {
				Resultlist = Dao.searchBranch(id, name, add);				
			}
			Results = getUtilStates(Resultlist);
			Scrollpane.setVisible(false);
			Table = new JTable(Results, Columnname);
			Table.addMouseListener(new TableListener());
			Scrollpane.setViewportView(Table);
			Scrollpane.setVisible(true);
		}
	}
	
	public void reNewTable(){
		Scrollpane.setVisible(false);
		if(Type=="PUBLISHER"){
		Resultlist = Dao.searchPub("","","");
		} else {
			Resultlist = Dao.searchBranch("","","");			
		}
		Results = getUtilStates(Resultlist);
		Table = new JTable(Results, Columnname);
		Table.addMouseListener(new TableListener());
		Scrollpane.setViewportView(Table);
		Scrollpane.setVisible(true);
	}
}

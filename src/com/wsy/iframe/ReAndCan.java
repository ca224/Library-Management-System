package com.wsy.iframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import com.wsy.dao.Dao;
import com.wsy.model.Borrow;
import com.wsy.model.Branch;
import com.wsy.model.Copy;
import com.wsy.model.Reserve;
import com.wsy.util.CreatecdIcon;

public class ReAndCan extends JInternalFrame {

	private JTable Table;
	private JPanel Mainpanel;
	private JPanel Buttonpanel;
	private JScrollPane Scrollpane;
	private JButton Rebutton;
	private JButton Canbutton;

	private JComboBox<?> Typefield;
	private List Resultlist;
	private Object[][] Results;

	String[] Columnres = { "RESNUMBER", "DOCID", "TITLE", "COPYNO", "LNAME",
			"DTIME" };
	String[] Columnbor = { "BORNUMBER", "DOCID", "TITLE", "COPYNO", "LNAME",
			"BDTIME","RDTIME" };
	String[] Columnname;

	private String Type = "RESERVE";
	private String Docid;
	private String Cno;
	private String Bno;
	private String Rno;
	private String Bid;
	private int Selrow = -1;
	private String Rid = Login.getUser().getPassword();

	private Object[][] getPersonalStates(List list) {

		Object[][] results;
		switch (Type) {
		case "RESERVE":
			Columnname = Columnres;
			results = new Object[list.size()][Columnres.length];
			for (int i = 0; i < list.size(); i++) {
				Reserve res = (Reserve) list.get(i);
				results[i][0] = res.getRno();
				results[i][1] = res.getId();
				results[i][2] = res.getTitle();
				results[i][3] = res.getNo();
				results[i][4] = res.getLname();
				results[i][5] = res.getDtime();
			}
			return results;
		default:
			Columnname = Columnbor;
			results = new Object[list.size()][Columnbor.length];
			for (int i = 0; i < list.size(); i++) {
				Borrow bor = (Borrow) list.get(i);
				results[i][0] = bor.getBno();
				results[i][1] = bor.getId();
				results[i][2] = bor.getTitle();
				results[i][3] = bor.getNo();
				results[i][4] = bor.getLname();
				results[i][5] = bor.getBtime();
				results[i][6] = bor.getRtime();
			}
			return results;
		}
	}

	public ReAndCan() {
		super();
		final BorderLayout borderLayout = new BorderLayout();
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(borderLayout);
		setTitle("ReAndCanIFrame");
		setBounds(30, 30, 500, 350);

		Buttonpanel = new JPanel();
		Buttonpanel.setBorder(new LineBorder(SystemColor.activeCaptionBorder,
				1, false));
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(2);
		flowLayout.setHgap(2);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		Buttonpanel.setLayout(flowLayout);

		Rebutton = new JButton();
		Rebutton.addActionListener(new reDocActionListener());
		Rebutton.setText("Return");
		Rebutton.setEnabled(false);
		Buttonpanel.add(Rebutton);

		Canbutton = new JButton();
		Canbutton.addActionListener(new canDocActionListener());
		Canbutton.setText("Cancel");
		Buttonpanel.add(Canbutton);

		final JLabel headLogo = new JLabel();
		ImageIcon bookModiAndDelIcon = CreatecdIcon.add("retbookcanc.jpg");
		headLogo.setIcon(bookModiAndDelIcon);
		headLogo.setOpaque(true);
		headLogo.setBackground(Color.WHITE);
		headLogo.setPreferredSize(new Dimension(400, 80));
		headLogo.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 1,
				false));
		getContentPane().add(headLogo, BorderLayout.NORTH);

		Scrollpane = new JScrollPane();
		getContentPane().add(Scrollpane, BorderLayout.CENTER);

		final JPanel personalpanel = new JPanel();
		final GridLayout gridLayout1 = new GridLayout(0, 4);
		personalpanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		gridLayout1.setVgap(5);
		gridLayout1.setHgap(5);
		personalpanel.setLayout(gridLayout1);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("Type:");
		personalpanel.add(label);

		Typefield = new JComboBox<>();
		String[] array = new String[] { "RESERVE", "BORROW", "HISTORY" };
		Typefield.setModel(new DefaultComboBoxModel(array));
		Typefield.setEditable(false);
		Typefield.addItemListener(new RecordTypeItemListener());
		personalpanel.add(Typefield);

		Mainpanel = new JPanel();
		BorderLayout borderLayout2 = new BorderLayout();
		Mainpanel.setLayout(borderLayout2);
		Mainpanel.add(personalpanel, BorderLayout.CENTER);
		Mainpanel.add(Buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(Mainpanel, BorderLayout.SOUTH);

		reNewTable();
		setVisible(true);
	}

	class canDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			String message = "Cancel RNO: " + Rno + " ?";
			int i = JOptionPane.NO_OPTION, j = 0, k = 0;
			i = JOptionPane.showConfirmDialog(null, message, "Cancel Reserve!",
					JOptionPane.YES_NO_OPTION);
			if (i != JOptionPane.YES_OPTION) {
				return;
			}
			j = Dao.del("RESERVES", Rno,"");
			String position = ((Reserve) Resultlist.get(Selrow)).getLoc();
			k = Dao.updCopy(Docid, Cno, Bid, position, "AVA");
			if (j == 1 && k == 1) {
				JOptionPane.showMessageDialog(null,
						"Database Operation Successed!");
				reNewTable();
			} else {
				JOptionPane.showMessageDialog(null,
						"Database Operation Failed!");
			}
		}
	}

	class reDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			String message = "Return DOCID: " + Docid + "COPYNO: " + Cno + " ?";
			int i = JOptionPane.NO_OPTION, j = 0, k = 0;
			i = JOptionPane.showConfirmDialog(null, message,
					"Return Document!", JOptionPane.YES_NO_OPTION);
			if (i != JOptionPane.YES_OPTION) {
				return;
			}
			j = Dao.updBor(Bno);
			String position = ((Borrow) Resultlist.get(Selrow)).getLoc();
			k = Dao.updCopy(Docid, Cno, Bid, position, "AVA");
			if (j == 1 && k == 1) {
				JOptionPane.showMessageDialog(null,
						"Database Operation Successed!");
				reNewTable();
			} else {
				JOptionPane.showMessageDialog(null,
						"Database Operation Failed!");
			}
		}
	}

	class TableListener extends MouseAdapter {
		public void mouseClicked(final MouseEvent e) {
			Selrow = Table.getSelectedRow();
			Docid = Table.getValueAt(Selrow, 1).toString().trim();
			Cno = Table.getValueAt(Selrow, 3).toString().trim();
			Bid = ((Copy) Resultlist.get(Selrow)).getLid();
			switch (Type) {
			case "RESERVE":
				Rno = Table.getValueAt(Selrow, 0).toString().trim();
				break;
			default:
				Bno = Table.getValueAt(Selrow, 0).toString().trim();
				break;
			}
		}
	}

	class RecordTypeItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Type = Typefield.getSelectedItem().toString();
			if (e.getStateChange() == ItemEvent.SELECTED) {
				switch (Type) {
				case "RESERVE":
					Rebutton.setEnabled(false);
					Canbutton.setEnabled(true);
					break;
				case "HISTORY":
					Rebutton.setEnabled(false);
					Canbutton.setEnabled(false);
					break;
				default:
					Rebutton.setEnabled(true);
					Canbutton.setEnabled(false);
					break;
				}
				reNewTable();
			}
		}
	}

	public void reNewTable() {
		Scrollpane.setVisible(false);
		switch (Type) {
		case "RESERVE":
			Resultlist = Dao.searchRes("", " AND R.READERID='" + Rid + "'", "",
					"", "", "");
			break;
		case "BORROW":
			Resultlist = Dao.searchBor("", " AND BO.READERID='" + Rid + "'",
					"", "", "", ""," AND BO.RDTIME IS NULL");
			break;
		default:
			Resultlist = Dao.searchBor("", " AND BO.READERID='" + Rid + "'",
					"", "", "", ""," AND BO.RDTIME IS NOT NULL");
			break;
		}
		Results = getPersonalStates(Resultlist);
		Table = new JTable(Results, Columnname);
		Table.addMouseListener(new TableListener());
		Scrollpane.setViewportView(Table);
		Scrollpane.setVisible(true);
	}
}

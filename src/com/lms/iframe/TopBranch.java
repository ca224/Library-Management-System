package com.lms.iframe;

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
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.lms.dao.Dao;
import com.lms.model.Borrow;
import com.lms.model.Branch;
import com.lms.util.CreatecdIcon;

public class TopBranch extends JInternalFrame {

	private JTable Table;
	private JPanel Mainpanel;
	private JPanel Buttonpanel;
	private JScrollPane Scrollpane;

	DefaultComboBoxModel<Branch> Branchmodel;
	private JComboBox<?> Branchfield;
	private List Resultlist;
	private Object[][] Results;

	String[] Columnname = { "DOCID", "TITLE","BORROWED" };

	private String Bid = "1";

	private Object[][] getPersonalStates(List list) {

		Object[][] results;
			results = new Object[list.size()][Columnname.length];
			for (int i = 0; i < list.size(); i++) {
				Borrow bor = (Borrow) list.get(i);
				results[i][0] = bor.getId();
				results[i][1] = bor.getTitle();
				results[i][2] = bor.getNo();
			}
			return results;
	}

	public TopBranch() {
		super();
		final BorderLayout borderLayout = new BorderLayout();
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(borderLayout);
		setTitle("TOP OF BRANCH");
		setBounds(30, 30, 500, 350);

		Buttonpanel = new JPanel();
		Buttonpanel.setBorder(new LineBorder(SystemColor.activeCaptionBorder,
				1, false));
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(2);
		flowLayout.setHgap(2);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		Buttonpanel.setLayout(flowLayout);

		final JButton closebutton = new JButton();
		closebutton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				doDefaultCloseAction();
			}
		});
		closebutton.setText("Close");
		Buttonpanel.add(closebutton);

		final JLabel headLogo = new JLabel();
		ImageIcon bookModiAndDelIcon = CreatecdIcon.add("top10.jpg");
		headLogo.setIcon(bookModiAndDelIcon);
		headLogo.setOpaque(true);
		headLogo.setBackground(Color.WHITE);
		headLogo.setPreferredSize(new Dimension(400, 80));
		headLogo.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 1,
				false));
		getContentPane().add(headLogo, BorderLayout.NORTH);

		Scrollpane = new JScrollPane();
		getContentPane().add(Scrollpane, BorderLayout.CENTER);

		final JPanel panel = new JPanel();
		final GridLayout gridLayout1 = new GridLayout(0, 4);
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		gridLayout1.setVgap(5);
		gridLayout1.setHgap(5);
		panel.setLayout(gridLayout1);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("Branch:");
		panel.add(label);

		Branchfield = new JComboBox<>();
		Branchmodel = (DefaultComboBoxModel<Branch>) Branchfield.getModel();
		Branchfield.setEditable(true);
		List<?> list_branch = Dao.searchBranch("","");
		for (int i = 0; i < list_branch.size(); i++) {
			Branch branch = (Branch) list_branch.get(i);
			Branchmodel.addElement(branch);
		}
		Branchfield.addItemListener(new BranchItemListener());
		panel.add(Branchfield);

		Mainpanel = new JPanel();
		BorderLayout borderLayout2 = new BorderLayout();
		Mainpanel.setLayout(borderLayout2);
		Mainpanel.add(panel, BorderLayout.CENTER);
		Mainpanel.add(Buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(Mainpanel, BorderLayout.SOUTH);

		reNewTable();
		setVisible(true);
	}

	class BranchItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Bid = ((Branch)Branchfield.getSelectedItem()).getId();
			if (e.getStateChange() == ItemEvent.SELECTED) {
				reNewTable();
			}
		}
	}

	public void reNewTable() {
		Scrollpane.setVisible(false);
		Resultlist = Dao.searchTopBor(Bid);
		Results = getPersonalStates(Resultlist);
		Table = new JTable(Results, Columnname);
		Scrollpane.setViewportView(Table);
		Scrollpane.setVisible(true);
	}
}

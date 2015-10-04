package com.lms.iframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
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

import com.lms.dao.Dao;
import com.lms.model.Branch;
import com.lms.model.Copy;
import com.lms.model.Reserve;
import com.lms.util.CreatecdIcon;

public class CManagment extends JInternalFrame {

	private JTable Table;

	private JPanel Mainpanel;
	private JPanel Buttonpanel;
	private JScrollPane Scrollpane;

	private JComboBox<Branch> Branchfield;
	private JTextField Position;
	private JComboBox<?> Statusfield;	

	private List Resultlist;
	private Object[][] Results;

	DefaultComboBoxModel<Branch> Branchmodel;
	String[] Columncopy = { "DOC_ID", "TITLE", "COPY_NO", "BRANCH", "POSITION",
			"STATUS" };

	private String Docid;
	private String Cno;
	private int Selrow = -1;
	private String Rid = Login.getUser().getPassword();

	private Object[][] getCopyStates(List list) {

		Object[][] results;
		results = new Object[list.size()][Columncopy.length];
		for (int i = 0; i < list.size(); i++) {
			Copy copy = (Copy) list.get(i);
			results[i][0] = copy.getId();
			results[i][1] = copy.getTitle();
			results[i][2] = copy.getNo();
			results[i][3] = copy.getLname();
			results[i][4] = copy.getLoc();
			results[i][5] = copy.getS();
		}
		return results;
	}

	public CManagment(String docid) {
		super();
		this.Docid = docid;
		final BorderLayout borderLayout = new BorderLayout();
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(borderLayout);
		setTitle("CopyIFrame");
		setBounds(30, 30, 700, 450);

		Buttonpanel = new JPanel();
		Buttonpanel.setBorder(new LineBorder(SystemColor.activeCaptionBorder,
				1, false));
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(2);
		flowLayout.setHgap(2);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		Buttonpanel.setLayout(flowLayout);

		final JButton delbutton = new JButton();
		delbutton.addActionListener(new delDocActionListener());
		delbutton.setText("Delete");
		Buttonpanel.add(delbutton);

		final JButton modbutton = new JButton();
		modbutton.addActionListener(new modDocActionListener());
		modbutton.setText("Modify");
		Buttonpanel.add(modbutton);

		final JButton addbutton = new JButton();
		addbutton.addActionListener(new addDocActionListener());
		addbutton.setText("Add New Copy");
		Buttonpanel.add(addbutton);
		
		if(Login.getUser().getType() == "READER"){
		
		delbutton.setEnabled(false);
		modbutton.setEnabled(false);
		addbutton.setEnabled(false);
		
		final JButton borbutton = new JButton();
		borbutton.addActionListener(new borDocActionListener());
		borbutton.setText("Borrow");
		Buttonpanel.add(borbutton);
		
		final JButton resbutton = new JButton();
		resbutton.addActionListener(new resDocActionListener());
		resbutton.setText("Reserve");
		Buttonpanel.add(resbutton);
		}

		final JLabel headLogo = new JLabel();
		ImageIcon bookModiAndDelIcon = CreatecdIcon.add("bookmodify.jpg");
		headLogo.setIcon(bookModiAndDelIcon);
		headLogo.setOpaque(true);
		headLogo.setBackground(Color.WHITE);
		headLogo.setPreferredSize(new Dimension(400, 80));
		headLogo.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 1,
				false));
		getContentPane().add(headLogo, BorderLayout.NORTH);

		Scrollpane = new JScrollPane();
		// ScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(Scrollpane, BorderLayout.CENTER);

		final JPanel copypanel = new JPanel();
		final GridLayout gridLayout1 = new GridLayout(0, 4);
		copypanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		gridLayout1.setVgap(5);
		gridLayout1.setHgap(5);
		copypanel.setLayout(gridLayout1);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("BRANCH");
		copypanel.add(label);

		Branchfield = new JComboBox<Branch>();
		Branchmodel = (DefaultComboBoxModel<Branch>) Branchfield.getModel();
		Branchfield.setEditable(true);
		List<?> list_branch = Dao.searchBranch("","");
		for (int i = 0; i < list_branch.size(); i++) {
			Branch branch = (Branch) list_branch.get(i);
			Branchmodel.addElement(branch);
		}
		copypanel.add(Branchfield);

		final JLabel label1 = new JLabel();
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setText("POSITION");
		copypanel.add(label1);

		Position = new JTextField();
		copypanel.add(Position);
		
		final JLabel label2 = new JLabel();
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setText("AVAILABILITY");
		copypanel.add(label2);

		Statusfield = new JComboBox();
		String[] array = new String[] { "AVA", "NOTAVA",
				"BOR", "RES" };
		Statusfield.setModel(new DefaultComboBoxModel(array));
		Statusfield.setEditable(false);
		copypanel.add(Statusfield);

		Mainpanel = new JPanel();
		BorderLayout borderLayout2 = new BorderLayout();
		Mainpanel.setLayout(borderLayout2);
		Mainpanel.add(copypanel, BorderLayout.CENTER);
		Mainpanel.add(Buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(Mainpanel, BorderLayout.SOUTH);

		reNewTable();
		if(Login.getUser().getType() == "READER"){
			Statusfield.setEnabled(false);
			Position.setEnabled(false);
			Branchfield.setEnabled(false);
		}
		setVisible(true);
	}

	class modDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			String message = "Update DOCID: " + Docid + "COPYNO: " + Cno + " ?";
			int i = JOptionPane.NO_OPTION, j = 0;
			String bid;
			i = JOptionPane.showConfirmDialog(null, message, "Update Data!",
					JOptionPane.YES_NO_OPTION);
			if (i != JOptionPane.YES_OPTION) {
				return;
			}
			if (Branchfield.getSelectedItem() instanceof Branch) {
				bid = ((Branch) Branchfield.getSelectedItem()).getId();
			} else {
				bid = String.valueOf(Dao.countId("LIBID") + 1);
				Dao.insertBranch(bid, Branchfield.getSelectedItem().toString(),
						"");
			}
			j = Dao.updCopy(Docid, Cno, bid, Position.getText().trim(), Statusfield.getSelectedItem().toString());
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

	class delDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			String message = "Delete DOCID: " + Docid + "COPYNO: " + Cno + " ?";
			int i = JOptionPane.showConfirmDialog(null, message,
					"Casacade Delete!", JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
				int j = Dao.del("COPY",Cno,Docid);
				if (j == 1) {
					JOptionPane.showMessageDialog(null,
							"Database Operation Successed!");
					reNewTable();
				} else {
					JOptionPane.showMessageDialog(null,
							"Database Operation Failed!");
				}
			} else {
				return;
			}
		}
	}
	
	class borDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;			
			}
			int j1 = 0, j2 = 0;
			int Bno = Dao.countId("BORNUMBER")+1;
			String message = "Borrow DOCID: " + Docid + "COPYNO: " + Cno + " ?";
			int i = JOptionPane.showConfirmDialog(null, message,
					"Borrow Document", JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
			switch(Statusfield.getSelectedItem().toString()){
			case "AVA":
				break;
			case "RES":
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				java.util.Date date=new java.util.Date();  
				String dtime=sdf.format(date);
				List list = Dao.searchRes("", " AND R.READERID ='"+Rid+"'", " AND R.DOCID ='"+Docid+"'", 
						" AND R.COPYNO='"+Cno+"'", " AND R.LIBID ='"+((Branch)Branchfield.getSelectedItem()).getId()+"'", dtime);
				if(list.isEmpty()){
					JOptionPane.showMessageDialog(null,
							"Not Available");
					return;
				}
				Dao.del("RESERVES",((Reserve) list.get(0)).getRno(),"");
				break;
			default:
				JOptionPane.showMessageDialog(null,"Not Available");
				return;
			}
			j1 = Dao.insertBor(String.valueOf(Bno), Rid, Docid, Cno,((Branch)Branchfield.getSelectedItem()).getId());
			j2 = Dao.updCopy(Docid, Cno,((Branch)Branchfield.getSelectedItem()).getId(),Position.getText().trim(),"BOR");
			if (j1 == 1 && j2 == 1) {
				JOptionPane.showMessageDialog(null,"Database Operation Successed! YOUR BORROW NUMBER IS: "+Bno);
				reNewTable();
			} else {
				JOptionPane.showMessageDialog(null,
						"Database Operation Failed!");
			}
			}
		}
	}
	
	class resDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
				String message = "Reserve DOCID: " + Docid + "COPYNO: " + Cno + " ?";
				int i = JOptionPane.showConfirmDialog(null, message,
						"Reserve Document", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					if (Statusfield.getSelectedItem().toString() != "AVA"){
						JOptionPane.showMessageDialog(null,
								"Not Available");
						return;
					}
					int Rno = Dao.countId("RESNUMBER")+1;
					int j1 = Dao.insertRes(String.valueOf(Rno), Rid, Docid, Cno,((Branch)Branchfield.getSelectedItem()).getId());
					int j2 = Dao.updCopy(Docid, Cno,((Branch)Branchfield.getSelectedItem()).getId(),Position.getText().trim(),"RES");
					if (j1 == 1 && j2 == 1) {
						JOptionPane.showMessageDialog(null,
								"Database Operation Successed! YOUR RESERVE NUMBER IS: "+Rno);
						reNewTable();
					} else {
						JOptionPane.showMessageDialog(null,
								"Database Operation Failed!");
					}
				} else {
					return;
				}
		}
	}

	class addDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			Cno = String.valueOf(Dao.countId("COPYNO",Docid) + 1);
			String message = "Add DOCID: " + Docid + "COPYNO: " + Cno + " ?";
			int i = JOptionPane.NO_OPTION, j = 0;
			String bid;
			i = JOptionPane.showConfirmDialog(null, message, "Add Data!",
					JOptionPane.YES_NO_OPTION);
			if (i != JOptionPane.YES_OPTION) {
				return;
			}
			if (Branchfield.getSelectedItem() instanceof Branch) {
				bid = ((Branch) Branchfield.getSelectedItem()).getId();
			} else {
				bid = String.valueOf(Dao.countId("LIBID") + 1);
				Dao.insertBranch(bid, Branchfield.getSelectedItem().toString(),
						"");
			}

			j = Dao.insertCopy(Docid, Cno, bid, Position.getText().trim());
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

	class TableListener extends MouseAdapter {
		public void mouseClicked(final MouseEvent e) {
			Selrow = Table.getSelectedRow();
			Docid = Table.getValueAt(Selrow, 0).toString().trim();
			Cno = Table.getValueAt(Selrow, 2).toString().trim();
			Branch branch = new Branch();
			branch.setName(Table.getValueAt(Selrow, 3).toString().trim());
			branch.setId(((Copy) Resultlist.get(Selrow)).getLid());
			Branchfield.setSelectedItem(branch);
			Position.setText(Table.getValueAt(Selrow, 4).toString().trim());
			String status = Table.getValueAt(Selrow, 5).toString().trim();
			Statusfield.setSelectedItem(status);
		}
	}
	
	public void reNewTable(){
		Scrollpane.setVisible(false);
		Resultlist = Dao.searchCopy(Docid,"","");
		Results = getCopyStates(Resultlist);
		Table = new JTable(Results, Columncopy);
		Table.addMouseListener(new TableListener());
		Scrollpane.setViewportView(Table);
		Scrollpane.setVisible(true);
	}
		}
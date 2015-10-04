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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.lms.Library;
import com.lms.dao.Dao;
import com.lms.model.Author;
import com.lms.model.Book;
import com.lms.model.Document;
import com.lms.model.Journal;
import com.lms.model.Proceeding;
import com.lms.model.Publisher;
import com.lms.util.CreatecdIcon;

public class DModAndSea extends JInternalFrame {

	private JTable Table;
	private JPanel Subpanel;
	private JPanel Mainpanel;
	private JPanel Buttonpanel;
	private JScrollPane Scrollpane;
	private JComboBox<Publisher> Publisherfield;
	private JComboBox<Author> Authorfield;
	private JComboBox<Author> Chiefeditor;
	private JTextField Docname;
	private JTextField Pubdate;
	private JTextField Condate;
	private JTextField ISBN;
	private JTextField Jvolume;
	private JTextField Volume;
	private JTextField Scope;
	private JTextField Conloc;
	private JTextField Coneditor;
	private JTextArea Guesteditor;
	private JComboBox<?> Doctype;
	private String[] Columnname;
	private List Resultlist;
	private Object[][] Results;

	DefaultComboBoxModel<Publisher> Publishermodel;
	DefaultComboBoxModel<Author> Authormodel;
	DefaultComboBoxModel<Author> Chiefeditormodel;

	private String Doctypecopy = "BOOK";
	private String Docid;
	private String Issid;
	private int Selrow = -1;
	SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	private Object[][] getDocStates(List list) {

		String[] columnbook = { "DOC_ID", "TITLE", "AUTHOR", "PDATE", "PUBLISHER",
				"ISBN" };
		String[] columnjournalvol = { "DOC_ID", "TITLE", "VOLUME", "EDITOR",
				"PDATE", "PUBLISHER" };
		String[] columnjournaliss = { "DOC_ID", "TITLE", "VOLUME", "ISSUE_NO",
				"SCOPE", "EDITOR", "PDATE", "PUBLISHER" };
		String[] columnproceeding = { "DOC_ID", "TITLE", "CHAIRMAN", "DATE",
				"LOCATION", "PDATE", "PUBLISHER" };

		Object[][] results;
		switch (Doctypecopy) {
		case "JOURNAL_VOLUME":
			results = new Object[list.size()][columnjournalvol.length];
			for (int i = 0; i < list.size(); i++) {
				Journal jou = (Journal) list.get(i);
				results[i][0] = jou.getId();
				results[i][1] = jou.getName();
				results[i][2] = jou.getJvolume();
				results[i][3] = jou.getEName();
				results[i][4] = jou.getDate().substring(0, 10);
				results[i][5] = jou.getPName();
			}
			Columnname = columnjournalvol;
			return results;
		case "JOURNAL_ISSUE":
			results = new Object[list.size()][columnjournaliss.length];
			for (int i = 0; i < list.size(); i++) {
				Journal jou = (Journal) list.get(i);
				results[i][0] = jou.getId();
				results[i][1] = jou.getName();
				results[i][2] = jou.getJvolume();
				results[i][3] = jou.getIss();
				results[i][4] = jou.getScope();
				results[i][5] = jou.getIEname();
				results[i][6] = jou.getDate().substring(0, 10);
				results[i][7] = jou.getPName();
			}
			Columnname = columnjournaliss;
			return results;
		case "PROCEEDING":
			results = new Object[list.size()][columnproceeding.length];
			for (int i = 0; i < list.size(); i++) {
				Proceeding pro = (Proceeding) list.get(i);
				results[i][0] = pro.getId();
				results[i][1] = pro.getName();
				results[i][2] = pro.getCName();
				if(pro.getCName() == null){
					Results[i][2]="";
				}
				results[i][3] = pro.getCdate().substring(0, 10);
				results[i][4] = pro.getLoc();
				if(pro.getLoc() == null){
					Results[i][4]="";
				}
				results[i][5] = pro.getDate().substring(0, 10);
				results[i][6] = pro.getPName();
			}
			Columnname = columnproceeding;
			return results;
		default:
			results = new Object[list.size()][columnbook.length];
			for (int i = 0; i < list.size(); i++) {
				Book book = (Book) list.get(i);
				results[i][0] = book.getId();
				results[i][1] = book.getName();
				results[i][2] = book.getAName();
				results[i][3] = book.getDate().substring(0, 10);
				results[i][4] = book.getPName();
				results[i][5] = book.getISBN();
				if(book.getISBN() == null){
					Results[i][5]="";
				}
			}
			Columnname = columnbook;
			return results;
		}
	}

	public DModAndSea() {
		super();
		final BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		setIconifiable(true);
		setClosable(true);
		setTitle("DocSeachIFrame");
		setBounds(30, 30, 700, 450);

		Buttonpanel = new JPanel();
		Buttonpanel.setBorder(new LineBorder(SystemColor.activeCaptionBorder,
				1, false));
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(2);
		flowLayout.setHgap(2);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		Buttonpanel.setLayout(flowLayout);
		
		final JButton copybutton = new JButton();
		copybutton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e){
				if (Selrow == -1) {
					JOptionPane.showMessageDialog(null, "Select Item First");
					return;
				}
				CManagment copyframe = new CManagment(Docid);
				Library.addIFame(copyframe);
				try {
					DModAndSea.this.setIcon(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		copybutton.setText("Check Copy");
		Buttonpanel.add(copybutton);

		final JButton delbutton = new JButton();
		delbutton.addActionListener(new delDocActionListener());
		delbutton.setText("Delete");
		Buttonpanel.add(delbutton);

		final JButton modbutton = new JButton();
		modbutton.addActionListener(new modDocActionListener());
		modbutton.setText("Modify");
		Buttonpanel.add(modbutton);
		
		final JButton resbutton = new JButton();
		resbutton.addActionListener(new resDocActionListener());
		resbutton.setText("Reset");
		Buttonpanel.add(resbutton);

		final JButton searchbutton = new JButton();
		searchbutton.addActionListener(new seaDocActionListener());
		searchbutton.setText("Search");
		Buttonpanel.add(searchbutton);

		final JButton closebutton = new JButton();
		closebutton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				doDefaultCloseAction();
			}
		});
		closebutton.setText("Close");
		Buttonpanel.add(closebutton);

		final JLabel headLogo = new JLabel();
		ImageIcon bookModiAndDelIcon;
		if(Login.getUser().getType() == "ADMIN"){
		bookModiAndDelIcon = CreatecdIcon.add("bookmodify.jpg");
		} else {
			bookModiAndDelIcon = CreatecdIcon.add("searchdoc.jpg");			
		}
		headLogo.setIcon(bookModiAndDelIcon);
		headLogo.setOpaque(true);
		headLogo.setBackground(Color.WHITE);
		headLogo.setPreferredSize(new Dimension(400, 80));
		headLogo.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 1,
				false));
		getContentPane().add(headLogo, BorderLayout.NORTH);

		Scrollpane = new JScrollPane();
		getContentPane().add(Scrollpane, BorderLayout.CENTER);

		final JPanel suppanel = new JPanel();
		final GridLayout gridLayout1 = new GridLayout(0, 4);
		suppanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		gridLayout1.setVgap(5);
		gridLayout1.setHgap(5);
		suppanel.setLayout(gridLayout1);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("TYPE");
		suppanel.add(label);

		Doctype = new JComboBox<Object>();
		String[] array = new String[] { "BOOK", "JOURNAL_VOLUME",
				"JOURNAL_ISSUE", "PROCEEDING" };
		Doctype.setModel(new DefaultComboBoxModel(array));
		Doctype.addItemListener(new DocTypeItemListener());
		suppanel.add(Doctype);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setText("DOCUMENT NAME");
		suppanel.add(label_2);

		Docname = new JTextField();
		suppanel.add(Docname);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setText("<html>PUBLISH DATE<br>YYYY-MM-DD");
		suppanel.add(label_1);

		Pubdate = new JTextField();
		suppanel.add(Pubdate);

		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setText("PUBLISHER");
		suppanel.add(label_3);

		Publisherfield = new JComboBox<Publisher>();
		Publishermodel = (DefaultComboBoxModel<Publisher>) Publisherfield
				.getModel();
		Publisherfield.setEditable(true);
		List<?> list_pub = Dao.searchPub("","");
		for (int i = 0; i < list_pub.size(); i++) {
			Publisher pub = (Publisher) list_pub.get(i);
			Publishermodel.addElement(pub);
		}
		Publisher pub = new Publisher();
		pub.setName("");
		Publisherfield.setSelectedItem(pub);
		suppanel.add(Publisherfield);

		Subpanel = new JPanel();
		Subpanel = panelChange("BOOK");

		Mainpanel = new JPanel();
		BorderLayout borderLayout2 = new BorderLayout();
		Mainpanel.setLayout(borderLayout2);
		Mainpanel.add(suppanel, BorderLayout.NORTH);
		Mainpanel.add(Subpanel, BorderLayout.CENTER);
		Mainpanel.add(Buttonpanel, BorderLayout.SOUTH);
		getContentPane().add(Mainpanel, BorderLayout.SOUTH);
		setVisible(true);
		if(Login.getUser().getType() == "READER"){
			delbutton.setEnabled(false);
			modbutton.setEnabled(false);
		}
	}

	class modDocActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Selrow == -1) {
				JOptionPane.showMessageDialog(null, "Select Item First");
				return;
			}
			String message = "Update DOCID: " + Docid + " ?";
			int i = JOptionPane.NO_OPTION,j = 0;
			String pid;
			i = JOptionPane.showConfirmDialog(null, message, "Update Data!",
					JOptionPane.YES_NO_OPTION);
			if (i != JOptionPane.YES_OPTION) {
				return;
			}
			if (Docname.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "!DOCUMENT NAME CAN'T BE NULL");
				return;
			}
			try {
				Date date = sdf.parse(Pubdate.getText().trim());
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "!DATE FORMAT INCORRECT");
				return;
			}
				if (Doctypecopy == "JOURNAL_ISSUE") {
					if (Guesteditor.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(null, "!GUEST EDITOR NAME CAN'T BE NULL");
						return;
					}
					j = Dao.updJou_iss(Docid, Issid, Scope.getText().trim(),Table.getValueAt(Selrow, 5).toString()
							.trim(),Guesteditor.getText().trim());
				} else {
					if (Publisherfield.getSelectedItem().toString().length() == 0) {
						JOptionPane.showMessageDialog(null, "!PUBLISHER CAN'T BE NULL");
						return;
					}
					if (Publisherfield.getSelectedItem() instanceof Publisher) {
						pid = ((Publisher) Publisherfield.getSelectedItem())
								.getId();
					} else {
						pid = String.valueOf(Dao.countId("PUBLISHERID") + 1);
						Dao.insertPub(pid, Publisherfield.getSelectedItem()
								.toString(), "");
					}
					Dao.updDoc(Docid, Docname.getText().trim(), Pubdate
							.getText().trim(), pid);
				}
					switch (Doctypecopy) {
					case "BOOK":
						if (Authorfield.getSelectedItem().toString().length() == 0) {
							JOptionPane.showMessageDialog(null, "!AUTHOR CAN'T BE NULL");
							return;
						}
						String aid;
						String authorcopy = Authorfield.getSelectedItem().toString();
						if (Authorfield.getSelectedItem() instanceof Author) {
							aid = ((Author) Authorfield.getSelectedItem()).getId();
							Dao.del("WRITES",((Book)Resultlist.get(Selrow)).getAid(),Docid);
							Dao.insertWri(aid, Docid);
						} else {
							Dao.del("WRITES",((Book)Resultlist.get(Selrow)).getAid(),Docid);
							aid = String.valueOf(Dao.countId("AUTHORID") + 1);
							Dao.insertAut(aid, authorcopy);
							Dao.insertWri(aid, Docid);
						}
						j = Dao.updBook(Docid, ISBN.getText().trim());						
						break;
					case "JOURNAL_VOLUME":
						if (Jvolume.getText().trim().length() == 0) {
							JOptionPane.showMessageDialog(null, "!JOURNAL VOLUME CAN'T BE NULL");
							return;
						}

						if (Chiefeditor.getSelectedItem().toString().length() == 0) {
							JOptionPane.showMessageDialog(null, "!CHIEF EDITOR CAN'T BE NULL");
							return;
						}
						String cid;
						String ceditorcopy = Chiefeditor.getSelectedItem().toString();
						if (Chiefeditor.getSelectedItem() instanceof Author) {
							cid = ((Author) Chiefeditor.getSelectedItem()).getId();
						} else {
							cid = String.valueOf(Dao.countId("EDITOR_ID") + 1);
							Dao.insertChi(cid, ceditorcopy);
						}
						j = Dao.updJou(Docid, Jvolume.getText().trim(), cid);						
						break;
					case "PROCEEDING":
						j = Dao.updPro(Docid, Condate.getText().trim(), Conloc.getText().trim(), Coneditor.getText().trim());	
						break;
					}
			if (j == 1) {
				JOptionPane.showMessageDialog(null,
						"Database Operation Successed!");
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
				String message = "Delete DOCID: " + Docid + " ?";
				if (Doctypecopy == "JOURNAL_ISSUE") {
					// message = "Delete DOCID: " + jou.getId() + " TITLE: "+
					// jou.getName()+" VOLUME: "+jou.getJvolume()+" ISSUE.NO: "+
					// jou.getIss()+" SCOPE: "+jou.getScope()+" EDITOR: "+jou.getIEname();
					int i = JOptionPane.showConfirmDialog(null, message,
							"Casacade Delete!", JOptionPane.YES_NO_OPTION);
					if (i == JOptionPane.YES_OPTION) {
						int j = Dao.delJou_iss(Docid, Issid);
						if (j == 1) {
							JOptionPane.showMessageDialog(null,
									"Database Operation Successed!");
						} else {
							JOptionPane.showMessageDialog(null,
									"Database Operation Failed!");
						}
					}
				} else {
					switch (Doctypecopy) {
					case "BOOK":
						// message = "Delete DOCID: " + book.getId() +
						// " TITLE: "+
						// book.getName()+" AUTHOR: "+book.getAName()+" PUBDATE: "+
						// book.getDate()+" PUBNAME "+book.getPName()+" ISBN: "+book.getISBN();
						break;
					case "JOURNAL_VOLUME":
						// message = "Delete DOCID: " + jou.getId() +
						// " TITLE: "+
						// jou.getName()+" VOLUME: "+jou.getJvolume()+" EDITOR: "+
						// jou.getEName()+" PUBDATE: "+jou.getDate()+" PUBNAME: "+jou.getPName();
						break;
					case "PROCEEDING":
						// message = "Delete DOCID: " + pro.getId() +
						// " TITLE: "+
						// pro.getName()+" CHAIR: "+pro.getCName()+" CONDATE: "+
						// pro.getCdate()+" LOCATION: "+pro.getLoc()+" PUBNAME: "+pro.getPName()+" PUBDATE: "+pro.getDate();
						break;
					}
					int i = JOptionPane.showConfirmDialog(null, message,
							"Casacade Delete!", JOptionPane.YES_NO_OPTION);
					if (i == JOptionPane.YES_OPTION) {
						int j = Dao.del("DOCUMENT",Docid,"");
						if (j == 1) {
							JOptionPane.showMessageDialog(null,
									"Database Operation Successed!");
						} else {
							JOptionPane.showMessageDialog(null,
									"Database Operation Failed!");
						}
					} else {
						return;
					}
				}
			}
		}
		
		private class resDocActionListener implements ActionListener {
			public void actionPerformed(final ActionEvent e){
				Publisher pub = new Publisher();
				pub.setName("");
				Publisherfield.setSelectedItem(pub);
				Docname.setText("");
				Pubdate.setText("");
				Author aut = new Author();
				aut.setName("");
				Authorfield.setSelectedItem(aut);
				ISBN.setText("");
				switch(Doctypecopy){
				case "JOURNAL_VOLUME":
				Chiefeditor.setSelectedItem(aut);
				Jvolume.setText("");
				break;
				case "JOURNAL_ISSUE":		
				Volume.setText("");
				Scope.setText("");
				Guesteditor.setText("");
				break;
				case "PROCEEDING":
				Conloc.setText("");
				Coneditor.setText("");
				Condate.setText("");
			}
		}
		}

		class seaDocActionListener implements ActionListener {
			public void actionPerformed(final ActionEvent e) {
				String docname, pdate, publishername, isbn, author, jvolume, ceditor, volume, scope, geditor, cdate, cloc, coneditor;
				if (Docname.getText().toString().equals("")) {
					docname = "";
				} else {
					docname = " AND D.TITLE ='" + Docname.getText() + "'";
				}
				if (Pubdate.getText().trim().equals("")) {
					pdate = "";
				} else {
					try {
						Date date = sdf.parse(Pubdate.getText().trim());
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(null, "!DATE FORMAT INCORRECT");
						return;
					}
					pdate = " AND D.PDATE =TO_DATE('" + Pubdate.getText().trim() + "','yyyy-MM-dd') ";
				}
				if (Publisherfield.getSelectedItem().toString().equals("")) {
					publishername = "";
				} else {
					publishername = " AND P.PUBNAME ='"
							+ Publisherfield.getSelectedItem().toString() + "'";
				}
				switch (Doctypecopy) {
				case "BOOK":
					if (ISBN.getText().trim().equals("")) {
						isbn = "";
					} else {
						isbn = " AND B.ISBN='" + ISBN.getText().trim() + "'";
					}
					if (Authorfield.getSelectedItem().toString().equals("")) {
						author = "";
					} else {
						author = " AND A.ANAME='"
								+ Authorfield.getSelectedItem().toString()
								+ "'";
					}
					Resultlist = Dao.searchBook(docname, pdate, publishername,
							isbn, author);
					Results = getDocStates(Resultlist);
					break;
				case "JOURNAL_VOLUME":
					if (Jvolume.getText().trim().equals("")) {
						jvolume = "";
					} else {
						jvolume = " AND J.JVOLUME='" + Jvolume.getText().trim()
								+ "'";
					}
					if (Chiefeditor.getSelectedItem().toString().equals("")) {
						ceditor = "";
					} else {
						ceditor = " AND C.ENAME='"
								+ Chiefeditor.getSelectedItem().toString()
								+ "'";
					}
					Resultlist = Dao.searchJou(docname, pdate, publishername,
							jvolume, ceditor);
					Results = getDocStates(Resultlist);
					break;
				case "JOURNAL_ISSUE":
					if (Volume.getText().trim().equals("")) {
						volume = "";
					} else {
						volume = " AND J.JVOLUME='" + Volume.getText().trim()
								+ "'";
					}
					if (Scope.getText().trim().equals("")) {
						scope = "";
					} else {
						scope = " AND JO.SCOPE='" + Scope.getText().trim()
								+ "'";
					}
					if (Guesteditor.getText().trim().equals("")) {
						geditor = "";
					} else {
						geditor = " AND I.IENAME='"
								+ Guesteditor.getText().trim() + "'";
					}
					Resultlist = Dao.searchJou_iss(docname, pdate,
							publishername, volume, scope, geditor);
					Results = getDocStates(Resultlist);
					break;
				case "PROCEEDING":
					if (Condate.getText().trim().equals("")) {
						cdate = "";
					} else {
						cdate = " AND PR.CDATE=TO_DATE('" + Condate.getText().trim()
								+ "','yyyy-MM-dd') ";
					}
					if (Conloc.getText().trim().equals("")) {
						cloc = "";
					} else {
						cloc = " AND PR.CLOCATION='" + Conloc.getText().trim()
								+ "'";
					}
					if (Coneditor.getText().trim().equals("")) {
						coneditor = "";
					} else {
						coneditor = " AND PR.CEDITOR='"
								+ Coneditor.getText().trim() + "'";
					}
					Resultlist = Dao.searchPro(docname, pdate, publishername,
							cdate, cloc, coneditor);
					Results = getDocStates(Resultlist);
					break;
				}
				Scrollpane.setVisible(false);
				Table = new JTable(Results, Columnname);
				Table.addMouseListener(new TableListener());
				Scrollpane.setViewportView(Table);
				Scrollpane.setVisible(true);
			}
		}

		public JPanel panelChange(String n) {
			GridLayout gridLayout = new GridLayout();
			switch (n) {
			case "BOOK":
				// book panel
				JPanel Bookpanel = new JPanel();
				Bookpanel.setBorder(new EmptyBorder(0, 5, 5, 5));
				gridLayout = new GridLayout(0, 4);
				gridLayout.setVgap(5);
				gridLayout.setHgap(5);
				Bookpanel.setLayout(gridLayout);

				final JLabel label_b_1 = new JLabel();
				label_b_1.setHorizontalAlignment(SwingConstants.CENTER);
				label_b_1.setText("ISBN");
				Bookpanel.add(label_b_1);

				ISBN = new JTextField();
				Bookpanel.add(ISBN);

				final JLabel label_b_2 = new JLabel();
				label_b_2.setHorizontalAlignment(SwingConstants.CENTER);
				label_b_2.setText("AUTHOR");
				Bookpanel.add(label_b_2);

				Authorfield = new JComboBox<Author>();
				Authormodel = (DefaultComboBoxModel<Author>) Authorfield
						.getModel();
				Authorfield.setEditable(true);

				// Read Publisher Data From Database
				List<?> list_aut = Dao.searchAut();
				for (int i = 0; i < list_aut.size(); i++) {
					Author author = (Author) list_aut.get(i);
					Authormodel.addElement(author);
				}
				Author aut = new Author();
				aut.setName("");
				Authorfield.setSelectedItem(aut);
				Bookpanel.add(Authorfield);
				return Bookpanel;
			case "JOURNAL_VOLUME":
				// journal_volume panel
				JPanel Journalpanel1 = new JPanel();
				Journalpanel1.setBorder(new EmptyBorder(0, 5, 5, 5));
				gridLayout = new GridLayout(0, 4);
				gridLayout.setVgap(5);
				gridLayout.setHgap(5);
				Journalpanel1.setLayout(gridLayout);
				getContentPane().add(Journalpanel1);

				final JLabel label_j_1 = new JLabel();
				label_j_1.setHorizontalAlignment(SwingConstants.CENTER);
				label_j_1.setText("JOUNAL VOLUME");
				Journalpanel1.add(label_j_1);

				Jvolume = new JTextField();
				Journalpanel1.add(Jvolume);

				final JLabel label_j_2 = new JLabel();
				label_j_2.setHorizontalAlignment(SwingConstants.CENTER);
				label_j_2.setText("CHIEF EDITOR");
				Journalpanel1.add(label_j_2);

				Chiefeditor = new JComboBox<Author>();
				Chiefeditormodel = (DefaultComboBoxModel<Author>) Chiefeditor
						.getModel();
				Chiefeditor.setEditable(true);

				// Read Chief Editor Data From Database
				List<?> list_chi = Dao.searchChi();
				for (int i = 0; i < list_chi.size(); i++) {
					Author author = (Author) list_chi.get(i);
					Chiefeditormodel.addElement(author);
				}
				Author aut2 = new Author();
				aut2.setName("");
				Chiefeditor.setSelectedItem(aut2);
				Journalpanel1.add(Chiefeditor);
				return Journalpanel1;
			case "JOURNAL_ISSUE":
				// journal_issue panel
				JPanel Journalpanel2 = new JPanel();
				Journalpanel2.setBorder(new EmptyBorder(0, 5, 0, 5));
				gridLayout = new GridLayout(0, 6);
				gridLayout.setVgap(2);
				gridLayout.setHgap(2);
				Journalpanel2.setLayout(gridLayout);
				getContentPane().add(Journalpanel2);
				
				final JLabel label_j_3 = new JLabel();
				label_j_3.setHorizontalAlignment(SwingConstants.CENTER);
				label_j_3.setText("JOUNAL VOLUME");
				Journalpanel2.add(label_j_3);

				Volume = new JTextField();
				Journalpanel2.add(Volume);

				final JLabel label_j_4 = new JLabel();
				label_j_4.setHorizontalAlignment(SwingConstants.CENTER);
				label_j_4.setText("SCOPE");
				Journalpanel2.add(label_j_4);

				Scope = new JTextField();
				Journalpanel2.add(Scope);

				final JLabel label_j_5 = new JLabel();
				label_j_5.setHorizontalAlignment(SwingConstants.LEFT);
				label_j_5.setText("<html>GUEST EDITOR<br>(NAME,NAME,...)");
				Journalpanel2.add(label_j_5);

				final JScrollPane jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				{
					Guesteditor = new JTextArea();
					getContentPane().add(Guesteditor);
					Guesteditor.setText("");
					jScrollPane1.setViewportView(Guesteditor);
					Guesteditor.setLineWrap(true);
				}
				Journalpanel2.add(jScrollPane1);
				return Journalpanel2;
			case "PROCEEDING":
				// proceeding panel
				JPanel Proceedingpanel = new JPanel();
				Proceedingpanel.setBorder(new EmptyBorder(0, 2, 0, 2));
				gridLayout = new GridLayout(0, 6);
				gridLayout.setVgap(2);
				gridLayout.setHgap(2);
				Proceedingpanel.setLayout(gridLayout);
				getContentPane().add(Proceedingpanel);

				final JLabel label_p_1 = new JLabel();
				label_p_1.setHorizontalAlignment(SwingConstants.CENTER);
				label_p_1.setText("<html>CONFERENCE DATE<br>YYYY-MM-DD");
				Proceedingpanel.add(label_p_1);

				Condate = new JTextField();
				Proceedingpanel.add(Condate);

				final JLabel label_p_2 = new JLabel();
				label_p_2.setHorizontalAlignment(SwingConstants.CENTER);
				label_p_2.setText("LOCATION");
				Proceedingpanel.add(label_p_2);

				Conloc = new JTextField();
				Proceedingpanel.add(Conloc);

				final JLabel label_p_3 = new JLabel();
				label_p_3.setHorizontalAlignment(SwingConstants.CENTER);
				label_p_3.setText("CHAIRMAN");
				Proceedingpanel.add(label_p_3);

				Coneditor = new JTextField();
				Proceedingpanel.add(Coneditor);
				return Proceedingpanel;
			default:
				return null;
			}
		}

		class DocTypeItemListener implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				Doctypecopy = Doctype.getSelectedItem().toString();
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Mainpanel.setVisible(false);
					Mainpanel.remove(Subpanel);
					Subpanel = panelChange(Doctypecopy);
					Mainpanel.add(Subpanel, BorderLayout.CENTER);
					Mainpanel.setVisible(true);
				}
			}
		}

		class TableListener extends MouseAdapter {
			public void mouseClicked(final MouseEvent e) {
				Selrow = Table.getSelectedRow();
				Docid = Table.getValueAt(Selrow, 0).toString().trim();
				Publisher pub = new Publisher();
				Author aut = new Author();
				Docname.setText(Table.getValueAt(Selrow, 1).toString().trim());
				switch (Doctypecopy) {
				case "BOOK":
					pub.setName(Table.getValueAt(Selrow, 4).toString().trim());
					pub.setId(((Document)Resultlist.get(Selrow)).getPublisher());
					Publisherfield.setSelectedItem(pub);
					Pubdate.setText(Table.getValueAt(Selrow, 3).toString()
							.trim());
					ISBN.setText(Table.getValueAt(Selrow, 5).toString().trim());
					aut.setName(Table.getValueAt(Selrow, 2).toString().trim());
					aut.setId(((Book)Resultlist.get(Selrow)).getAid());
					Authorfield.setSelectedItem(aut);
					return;
				case "JOURNAL_VOLUME":
					Pubdate.setText(Table.getValueAt(Selrow, 4).toString()
							.trim());
					pub.setName(Table.getValueAt(Selrow, 5).toString().trim());
					Publisherfield.setSelectedItem(pub);
					Jvolume.setText(Table.getValueAt(Selrow, 2).toString()
							.trim());
					aut.setName(Table.getValueAt(Selrow, 3).toString().trim());
					aut.setId(((Journal)Resultlist.get(Selrow)).getEid());
					Chiefeditor.setSelectedItem(aut);
					return;
				case "JOURNAL_ISSUE":
					Pubdate.setText(Table.getValueAt(Selrow, 6).toString()
							.trim());
					pub.setName(Table.getValueAt(Selrow, 7).toString().trim());
					Publisherfield.setSelectedItem(pub);
					Issid = (Table.getValueAt(Selrow, 3).toString().trim());
					Scope.setText(Table.getValueAt(Selrow, 4).toString().trim());
					Guesteditor.setText(Table.getValueAt(Selrow, 5).toString()
							.trim());
					return;
				case "PROCEEDING":
					Pubdate.setText(Table.getValueAt(Selrow, 5).toString()
							.trim());
					pub.setName(Table.getValueAt(Selrow, 6).toString().trim());
					Publisherfield.setSelectedItem(pub);
					Condate.setText(Table.getValueAt(Selrow, 3).toString()
							.trim());
					Conloc.setText(Table.getValueAt(Selrow, 4).toString()
							.trim());
					Coneditor.setText(Table.getValueAt(Selrow, 2).toString()
							.trim());
					return;
				}
			}
		}
	}
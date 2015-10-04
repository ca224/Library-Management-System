package com.lms.iframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.lms.dao.Dao;
import com.lms.model.Author;
import com.lms.model.Journal;
import com.lms.model.Publisher;
import com.lms.util.CreatecdIcon;

public class DAdd extends JInternalFrame {

	private JPanel Mainpanel;
	private JPanel Buttonpanel;
	private JPanel Backpanel;
	private JComboBox<Publisher> Publisherfield;
	private JComboBox<Author> Authorfield;
	private JComboBox<Author> Chiefeditor;
	private JTextField Docname;
	private JTextField Volume;
	private JTextField Pubdate;
	private JTextField Condate;
	private JTextField ISBN;
	private JTextField Jvolume;
	private JTextField Scope;
	private JTextField Conloc;
	private JTextField Coneditor;
	private JTextArea Guesteditor;
	private JComboBox<?> Doctype;
	private JButton Buttonnext;
	private JButton Buttonclose;
	private JButton Buttonback;
	private JButton Buttonfinish;
	private GridLayout gridLayout;

	private String Doctypecopy;

	DefaultComboBoxModel<Publisher> Publishermodel;
	DefaultComboBoxModel<Author> Authormodel;
	DefaultComboBoxModel<Author> Chiefeditormodel;
	
	SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	public DAdd() {
		super();
		final BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		setIconifiable(true);
		setClosable(true);
		setTitle("BookAddIFrame");
		setBounds(30, 30, 400, 260);

		Mainpanel = new JPanel();
		Mainpanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		GridLayout gridLayout = new GridLayout(0, 4);
		gridLayout.setVgap(50);
		gridLayout.setHgap(5);
		Mainpanel.setLayout(gridLayout);
		getContentPane().add(Mainpanel);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("DOCUMENT TYPE");
		Mainpanel.add(label);

		Doctype = new JComboBox<Object>();
		String[] array = new String[] { "BOOK", "JOURNAL_VOLUME",
				"JOURNAL_ISSUE", "PROCEEDING" };
		Doctype.setModel(new DefaultComboBoxModel(array));
		Mainpanel.add(Doctype);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setText("DOCUMENT NAME");
		Mainpanel.add(label_1);

		Docname = new JTextField();
		Mainpanel.add(Docname);

		final JLabel label_2_1 = new JLabel();
		label_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_2_1.setText("PUBLISHER");
		Mainpanel.add(label_2_1);

		Publisherfield = new JComboBox<Publisher>();
		Publishermodel = (DefaultComboBoxModel<Publisher>) Publisherfield
				.getModel();
		Publisherfield.setEditable(true);

		// Read Publisher Data From Database
		List<?> list_pub = Dao.searchPub("","");
		for (int i = 0; i < list_pub.size(); i++) {
			Publisher publisher = (Publisher) list_pub.get(i);
			Publishermodel.addElement(publisher);
		}
		Mainpanel.add(Publisherfield);

		final JLabel label_1_1 = new JLabel();
		label_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1_1.setText("<html>PDATE<br>YYYY-MM-DD");
		Mainpanel.add(label_1_1);

		Pubdate = new JTextField();
		Mainpanel.add(Pubdate);

		// button panel
		Buttonpanel = new JPanel();
		Buttonpanel.setBorder(new LineBorder(SystemColor.activeCaptionBorder,
				1, false));
		getContentPane().add(Buttonpanel, BorderLayout.SOUTH);
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(2);
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		Buttonpanel.setLayout(flowLayout);

		Buttonnext = new JButton();
		Buttonnext.addActionListener(new NextActionListener());
		Buttonnext.setText("Next");
		Buttonpanel.add(Buttonnext);

		Buttonclose = new JButton();
		Buttonclose.addActionListener(new CloseActionListener());
		Buttonclose.setText("Cancel");
		Buttonpanel.add(Buttonclose);

		Buttonback = new JButton();
		Buttonback.addActionListener(new BackActionListener());
		Buttonback.setText("Back");

		Buttonfinish = new JButton();
		Buttonfinish.addActionListener(new FinishActionListener());
		Buttonfinish.setText("Finish");

		final JLabel label_5 = new JLabel();
		ImageIcon bookAddIcon = CreatecdIcon.add("bookAdd.jpg");
		label_5.setIcon(bookAddIcon);
		label_5.setPreferredSize(new Dimension(400, 80));
		label_5.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 1,
				false));
		getContentPane().add(label_5, BorderLayout.NORTH);
		label_5.setText("");

		setVisible(true);
	}

	class CloseActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			doDefaultCloseAction();
		}
	}

	class FinishActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {

			String docnamecopy = Docname.getText().trim();
			String publishercopy = Publisherfield.getSelectedItem().toString();
			String pubdatescopy = Pubdate.getText().trim();			
			
			String pid;
			String did="";
			
			if (Publisherfield.getSelectedItem() instanceof Publisher) {
				pid = ((Publisher) Publisherfield.getSelectedItem()).getId();
			} else {
				pid = String.valueOf(Dao.countId("PUBLISHERID") + 1);
				Dao.insertPub(pid, publishercopy, "");
			}

			String type;
			if (Doctypecopy == "JOURNAL_ISSUE"
					|| Doctypecopy == "JOURNAL_VOLUME") {
				type = "JOURNAL";
			} else {
				type = Doctypecopy;
			}

			if (Doctypecopy != "JOURNAL_ISSUE") {
				did = String.valueOf(Dao.countId("DOCID") + 1);
				Dao.insertDoc(did, docnamecopy, pubdatescopy, pid, type);
			}
			int j = 0;
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
				} else {
					aid = String.valueOf(Dao.countId("AUTHORID") + 1);
					Dao.insertAut(aid, authorcopy);
				}
				j = Dao.insertBook(did, ISBN.getText().trim());
				Dao.insertWri(aid, did);
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
				String chiefcopy = Chiefeditor.getSelectedItem().toString();
				if (Chiefeditor.getSelectedItem() instanceof Author) {
					cid = ((Author) Chiefeditor.getSelectedItem()).getId();
				} else {
					cid = String.valueOf(Dao.countId("EDITOR_ID") + 1);
					Dao.insertChi(cid, chiefcopy);
				}
				j = Dao.insertJvolume(did,Jvolume.getText().trim(), cid);
				break;
			case "JOURNAL_ISSUE":
				if (Guesteditor.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "!GUEST EDITOR NAME CAN'T BE NULL");
					return;
				}				
				if (Scope.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "!JOURNAL VOLUME CAN'T BE NULL");
					return;
				}
				String docname = " AND D.TITLE='"+Docname.getText().trim()+"'";
				String jvolume = " AND J.JVOLUME='"+Volume.getText().trim()+"'";				
				List list =Dao.searchJou(docname, "", "", jvolume, "");
				if(list.isEmpty()){
					JOptionPane.showMessageDialog(null, "CAN'T FIND THIS JOURNAL VOLUME! PLEASE ADD THIS JOURNAL VOLUME FIRST");
					return;
				} else {
					did = ((Journal)list.get(0)).getId();
				}

				String iid = String.valueOf(Dao.countId("ISSUE_NO",did) + 1);
				System.out.println(did + " " + iid);
				j = Dao.insertJissue(did, iid, Scope.getText().trim());
				String[] part;
				part = Guesteditor.getText().trim().split(",");
				for (int i2 = 0; i2 < part.length; i2++) {
					String guesteditor = part[i2];
					Dao.insertGeditor(did, iid, guesteditor);
				}
				break;
			case "PROCEEDING":
				try {
					Date date = sdf.parse(Condate.getText().trim());
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "!DATE FORMAT INCORRECT");
					return;
				}
				j = Dao.insertPro(did, Condate.getText().trim(), Conloc
						.getText().trim(), Coneditor.getText().trim());
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

	class BackActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			setVisible(false);
			getContentPane().remove(Mainpanel);
			Mainpanel = Backpanel;
			getContentPane().add(Mainpanel);
			setVisible(true);
			Buttonpanel.removeAll();
			Buttonpanel.add(Buttonnext);
			Buttonpanel.add(Buttonclose);
		}
	}

	class NextActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			if (Docname.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "!DOCUMENT NAME CAN'T BE NULL");
				return;
			}

			if (Publisherfield.getSelectedItem().toString().length() == 0) {
				JOptionPane.showMessageDialog(null, "!PUBLISHER CAN'T BE NULL");
				return;
			}			
			try {
				Date date = sdf.parse(Pubdate.getText().trim());
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "!DATE FORMAT INCORRECT");
				return;
			}

			Doctypecopy = (String) Doctype.getSelectedItem();

			setVisible(false);
			getContentPane().remove(Mainpanel);
			Backpanel = Mainpanel;
			Mainpanel = panelChange(Doctypecopy);
			getContentPane().add(Mainpanel);
			setVisible(true);
			Buttonpanel.removeAll();
			Buttonpanel.add(Buttonback);
			Buttonpanel.add(Buttonfinish);
			Buttonpanel.add(Buttonclose);
		}
	}

	public JPanel panelChange(String n) {
		switch (n) {
		case "BOOK":
			// book panel
			JPanel Bookpanel = new JPanel();
			Bookpanel.setBorder(new EmptyBorder(5, 10, 5, 10));
			gridLayout = new GridLayout(0, 2);
			gridLayout.setVgap(15);
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
			Authormodel = (DefaultComboBoxModel<Author>) Authorfield.getModel();
			Authorfield.setEditable(true);

			// Read Author Data From Database
			List<?> list_aut = Dao.searchAut();
			for (int i = 0; i < list_aut.size(); i++) {
				Author author = (Author) list_aut.get(i);
				Authormodel.addElement(author);
			}
			Bookpanel.add(Authorfield);
			return Bookpanel;
		case "JOURNAL_VOLUME":
			// journal_volume panel
			JPanel Journalpanel1 = new JPanel();
			Journalpanel1.setBorder(new EmptyBorder(5, 10, 5, 10));
			gridLayout = new GridLayout(0, 2);
			gridLayout.setVgap(15);
			gridLayout.setHgap(5);
			Journalpanel1.setLayout(gridLayout);
			getContentPane().add(Journalpanel1);

			final JLabel label_j_1 = new JLabel();
			label_j_1.setHorizontalAlignment(SwingConstants.CENTER);
			label_j_1.setText("VOLUME");
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

			Journalpanel1.add(Chiefeditor);
			return Journalpanel1;
		case "JOURNAL_ISSUE":
			// journal_issue panel
			JPanel Journalpanel2 = new JPanel();
			Journalpanel2.setBorder(new EmptyBorder(5, 10, 5, 10));
			gridLayout = new GridLayout(0, 2);
			gridLayout.setVgap(5);
			gridLayout.setHgap(5);
			Journalpanel2.setLayout(gridLayout);
			getContentPane().add(Journalpanel2);

			final JLabel label_j = new JLabel();
			label_j.setHorizontalAlignment(SwingConstants.CENTER);
			label_j.setText("VOLUME");
			Journalpanel2.add(label_j);

			Volume = new JTextField();
			Journalpanel2.add(Volume);

			final JLabel label_j_3 = new JLabel();
			label_j_3.setHorizontalAlignment(SwingConstants.CENTER);
			label_j_3.setText("SCOPE");
			Journalpanel2.add(label_j_3);

			Scope = new JTextField();
			Journalpanel2.add(Scope);

			final JLabel label_j_4 = new JLabel();
			label_j_4.setHorizontalAlignment(SwingConstants.CENTER);
			label_j_4.setText("<html>GUEST EDITOR<br>(NAME,NAME,...)");
			Journalpanel2.add(label_j_4);

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
			Proceedingpanel.setBorder(new EmptyBorder(5, 10, 5, 10));
			gridLayout = new GridLayout(0, 2);
			gridLayout.setVgap(5);
			gridLayout.setHgap(5);
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
			return Mainpanel;
		}
	}
}

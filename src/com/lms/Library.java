package com.lms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import com.lms.iframe.Login;
import com.lms.util.CreatecdIcon;;

public class Library extends JFrame {
	
	private static JDesktopPane DESKTOP_PANE = new JDesktopPane();

	public static void addIFame(JInternalFrame iframe) {
		DESKTOP_PANE.add(iframe);
	}
	
	public Library() {
		super();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setSize(800, 600);
		setTitle("Library Management System");
		if(Login.getUser().getType() == "ADMIN"){
		JMenuBar menuBar = createMenu();
		setJMenuBar(menuBar);
		}
		JToolBar toolBar = createToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		final JLabel label = new JLabel();
		label.setBounds(0, 0, 0, 0);
		label.setIcon(null);

		DESKTOP_PANE = new JDesktopPane();
		DESKTOP_PANE.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				Dimension size = e.getComponent().getSize();
				label.setSize(e.getComponent().getSize());
				label.setText("<html><img width=" + size.width + " height="
						+ size.height + " src='"
						+ this.getClass().getResource("/backImg2.jpg")
						+ "'></html>");
			}
		});
		DESKTOP_PANE.add(label,new Integer(Integer.MIN_VALUE));
		getContentPane().add(DESKTOP_PANE);
	}

	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		if(Login.getUser().getType() == "ADMIN"){		
		JButton DocAddButton=new JButton(MenuActions.DOC_ADD);
		ImageIcon icon=new ImageIcon(Library.class.getResource("/bookAddtb.jpg"));
		DocAddButton.setIcon(icon);
		DocAddButton.setHideActionText(true);	
		toolBar.add(DocAddButton);
		
		JButton DocModiAndDelButton=new JButton(MenuActions.DOC_MODIFY);
		ImageIcon bookmodiicon=CreatecdIcon.add("bookModiAndDeltb.jpg");
		DocModiAndDelButton.setIcon(bookmodiicon);
		DocModiAndDelButton.setHideActionText(true);
		toolBar.add(DocModiAndDelButton);
		}
		
		if(Login.getUser().getType() == "READER"){
			
			JButton DocModiAndDelButton=new JButton(MenuActions.DOC_SEARCH);
			ImageIcon bookmodiicon=CreatecdIcon.add("bookModiAndDeltb.jpg");
			DocModiAndDelButton.setIcon(bookmodiicon);
			DocModiAndDelButton.setHideActionText(true);
			toolBar.add(DocModiAndDelButton);
			
		JButton ReturnCancel=new JButton(MenuActions.RETURN_CANCEL);
		ImageIcon returncancelicon=CreatecdIcon.add("newbookChecktb.jpg");
		ReturnCancel.setIcon(returncancelicon);
		ReturnCancel.setHideActionText(true);
		toolBar.add(ReturnCancel);
		}
		
		if(Login.getUser().getType() == "ADMIN"){	
		JButton readerAddButton=new JButton(MenuActions.READER_ADD);
		ImageIcon readerAddicon=CreatecdIcon.add("readerAddtb.jpg");
		readerAddButton.setIcon(readerAddicon);
		readerAddButton.setHideActionText(true);
		toolBar.add(readerAddButton);
		}
		
		if(Login.getUser().getType() == "ADMIN"){
		JButton readerModiAndDelButton=new JButton(MenuActions.READER_MODIFY);
		ImageIcon readerModiAndDelicon=CreatecdIcon.add("readerModiAndDeltb.jpg");
		readerModiAndDelButton.setIcon(readerModiAndDelicon);
		readerModiAndDelButton.setHideActionText(true);
		toolBar.add(readerModiAndDelButton);
		}
		
		JButton ExitButton=new JButton(MenuActions.EXIT);
		ImageIcon Exiticon=CreatecdIcon.add("exittb.jpg");
		ExitButton.setIcon(Exiticon);
		ExitButton.setHideActionText(true);
		toolBar.add(ExitButton);
		return toolBar;
	}
	
	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		 
		JMenu baseMenu = new JMenu(" INFORMATION MANAGEMENT ");
		{
			JMenu DocmanagementMenu = new JMenu(" DOCUMENT MANAGEMENT ");
			DocmanagementMenu.add(MenuActions.DOC_ADD);
			DocmanagementMenu.add(MenuActions.DOC_MODIFY);		
			
			JMenu readerManagerMItem = new JMenu(" READER MANAGEMENT ");
			readerManagerMItem.add(MenuActions.READER_ADD);
			readerManagerMItem.add(MenuActions.READER_MODIFY);
			
			JMenu utilManagerMItem = new JMenu(" UTILITY MANAGEMENT ");
			utilManagerMItem.add(MenuActions.PUBLISHER);
			utilManagerMItem.add(MenuActions.BRANCH);

			baseMenu.add(readerManagerMItem);
			baseMenu.addSeparator();
			baseMenu.add(DocmanagementMenu);
			baseMenu.addSeparator();
			baseMenu.add(utilManagerMItem);
			baseMenu.addSeparator();			
			baseMenu.add(MenuActions.EXIT);
		}

		JMenu topMenu = new JMenu(" TOP ");
		{
		topMenu.add(MenuActions.TOP_BOOK);
		topMenu.add(MenuActions.TOP_READER);
		}

		menuBar.add(baseMenu);
		menuBar.add(topMenu);
		return menuBar;
	}
}

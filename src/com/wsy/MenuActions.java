package com.wsy;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import com.wsy.iframe.DAdd;
import com.wsy.iframe.DModAndSea;
import com.wsy.iframe.Login;
import com.wsy.iframe.RAdd;
import com.wsy.iframe.RModAndSea;
import com.wsy.iframe.ReAndCan;
import com.wsy.iframe.TopBranch;
import com.wsy.iframe.TopReader;
import com.wsy.iframe.UModAndSea;

public class MenuActions {
	
	private static Map<String, JInternalFrame> frames;
	public static ReturnCancelAciton RETURN_CANCEL;
	public static ReaderModiAction READER_MODIFY;
	public static ReaderAddAction READER_ADD;
	public static DocModiAction1 DOC_MODIFY;
	public static DocModiAction2 DOC_SEARCH;	
	public static DocAddAction DOC_ADD;
	public static ExitAction EXIT;
	public static PubAction PUBLISHER;
	public static BranchAction BRANCH;
	public static topBranchAction TOP_BOOK;
	public static topReaderAction TOP_READER;

	static {
		frames = new HashMap<String, JInternalFrame>();
		READER_MODIFY = new ReaderModiAction();
		READER_ADD = new ReaderAddAction();
		RETURN_CANCEL = new ReturnCancelAciton();
		DOC_MODIFY = new DocModiAction1();
		DOC_SEARCH = new DocModiAction2();
		DOC_ADD = new DocAddAction();
		EXIT = new ExitAction();
		PUBLISHER = new PubAction();
		BRANCH = new BranchAction();
		TOP_BOOK = new topBranchAction();
		TOP_READER = new topReaderAction();	
	}
	
	private static class PubAction extends AbstractAction {
		PubAction() {
			super("Publisher Management", null);
			putValue(Action.LONG_DESCRIPTION, "Publisher Management");
			putValue(Action.SHORT_DESCRIPTION, "Publisher Management");
		}
		public void actionPerformed(ActionEvent e) {
			
			if (!frames.containsKey("Publisher Management")||frames.get("Publisher Management").isClosed()) {
				UModAndSea iframe=new UModAndSea("PUBLISHER");
				frames.put("Publisher Management", iframe);
				Library.addIFame(frames.get("Publisher Management"));
			}
		}
	}
	
	private static class BranchAction extends AbstractAction {
		BranchAction() {
			super("Branch Management", null);
			putValue(Action.LONG_DESCRIPTION, "Branch Management");
			putValue(Action.SHORT_DESCRIPTION, "Branch Management");
		}
		public void actionPerformed(ActionEvent e) {
			
			if (!frames.containsKey("Branch Management")||frames.get("Branch Management").isClosed()) {
				UModAndSea iframe=new UModAndSea("BRANCH");
				frames.put("Branch Management", iframe);
				Library.addIFame(frames.get("Branch Management"));
			}
		}
	}
	
	private static class ReaderModiAction extends AbstractAction {
		ReaderModiAction() {
			super("Reader Modify", null);
			putValue(Action.LONG_DESCRIPTION, "Modify or Delete Reader Information");
			putValue(Action.SHORT_DESCRIPTION, "Reader Modify");
		}
		public void actionPerformed(ActionEvent e) {
			
			if (!frames.containsKey("Reader Modify")||frames.get("Reader Modify").isClosed()) {
				RModAndSea iframe=new RModAndSea();
				frames.put("Reader Modify", iframe);
				Library.addIFame(frames.get("Reader Modify"));
			}
		}
	}
	
	private static class ReturnCancelAciton extends AbstractAction {
		ReturnCancelAciton() {
			super("Return and Cancel", null);
			putValue(Action.LONG_DESCRIPTION, "Return Document Or Cancel Reserve");
			putValue(Action.SHORT_DESCRIPTION, "Return Document Or Cancel Reserve");
		}
		public void actionPerformed(ActionEvent e) {
			
			if (!frames.containsKey("Return and Cancel")||frames.get("Return and Cancel").isClosed()) {
				ReAndCan iframe=new ReAndCan();
				frames.put("Return and Cancel", iframe);
				Library.addIFame(frames.get("Return and Cancel"));
			}
		}
	}
	
	private static class topBranchAction extends AbstractAction {
		topBranchAction() {
			super("TOP BOOK", null);
			putValue(Action.LONG_DESCRIPTION, "TOP OF BRANCH");
			putValue(Action.SHORT_DESCRIPTION, "TOP BORROWED BOOK");
		}
		public void actionPerformed(ActionEvent e) {
			
			if (!frames.containsKey("TOP BOOK")||frames.get("TOP BOOK").isClosed()) {
				TopBranch iframe=new TopBranch();
				frames.put("TOP BOOK", iframe);
				Library.addIFame(frames.get("TOP BOOK"));
			}
		}
	}
	
	private static class topReaderAction extends AbstractAction {
		topReaderAction() {
			super("TOP READER", null);
			putValue(Action.LONG_DESCRIPTION, "TOP OF READER");
			putValue(Action.SHORT_DESCRIPTION, "READER WHO BORROW THE MOST");
		}
		public void actionPerformed(ActionEvent e) {
			
			if (!frames.containsKey("TOP READER")||frames.get("TOP READER").isClosed()) {
				TopReader iframe=new TopReader();
				frames.put("TOP READER", iframe);
				Library.addIFame(frames.get("TOP READER"));
			}
		}
	}


	private static class ReaderAddAction extends AbstractAction {
		ReaderAddAction() {
			super("Add Reader", null);
			putValue(Action.LONG_DESCRIPTION, "Add New Reader Information");
			putValue(Action.SHORT_DESCRIPTION, "Add New Reader Information");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("Add Reader")||frames.get("Add Reader").isClosed()) {
				RAdd iframe=new RAdd();
				frames.put("Add Reader", iframe);
				Library.addIFame(frames.get("Add Reader"));
			}
		}
	}

	private static class DocModiAction1 extends AbstractAction {
		DocModiAction1() {
			super("Doc Modify", null);
			putValue(Action.LONG_DESCRIPTION, "Doc Information Management");
			putValue(Action.SHORT_DESCRIPTION, "Doc Information Management");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("Doc Modify")||frames.get("Doc Modify").isClosed()) {
				DModAndSea iframe=new DModAndSea();
				frames.put("Doc Modify", iframe);
				Library.addIFame(frames.get("Doc Modify"));
			}
		}
	}
	
	private static class DocModiAction2 extends AbstractAction {
		DocModiAction2() {
			super("Doc Search", null);
			putValue(Action.LONG_DESCRIPTION, "Document Search, Borrow, Reserve");
			putValue(Action.SHORT_DESCRIPTION, "Document Search, Borrow, Reserve");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("Doc Search")||frames.get("Doc Search").isClosed()) {
				DModAndSea iframe=new DModAndSea();
				frames.put("Doc Search", iframe);
				Library.addIFame(frames.get("Doc Search"));
			}
		}
	}
	
	private static class DocAddAction extends AbstractAction {
		DocAddAction() {

			super("Add Document", null);
			putValue(Action.LONG_DESCRIPTION, "Add New Document");
			putValue(Action.SHORT_DESCRIPTION, "Add New Document");
		}
		public void actionPerformed(ActionEvent e) {
			if (!frames.containsKey("Add Document")||frames.get("Add Document").isClosed()) {
				DAdd iframe = new DAdd();
				frames.put("Add Document", iframe);
				Library.addIFame(frames.get("Add Document"));
			}
		}
	}
	private static class ExitAction extends AbstractAction {
		public ExitAction() {
			super("Log out", null);
			putValue(Action.LONG_DESCRIPTION, "Log out");
			putValue(Action.SHORT_DESCRIPTION, "Log out");
		}
		public void actionPerformed(final ActionEvent e) {
			Login.Frame.setVisible(false);
			frames.clear();
		}
	}

	private MenuActions() {
		super();
	}

}

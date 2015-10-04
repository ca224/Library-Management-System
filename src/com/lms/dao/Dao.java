package com.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lms.model.Author;
import com.lms.model.Book;
import com.lms.model.Borrow;
import com.lms.model.Branch;
import com.lms.model.Copy;
import com.lms.model.Journal;
import com.lms.model.Operater;
import com.lms.model.Proceeding;
import com.lms.model.Publisher;
import com.lms.model.Reader;
import com.lms.model.Reserve;

import java.util.Date;

public class Dao {
	protected static String dbUrl = "jdbc:oracle:thin:@prophet.njit.edu:1521:course";
	protected static String dbUser = "QZ78";
	protected static String dbPwd = "oB6hhwQN";
	private static Connection conn = null;
	
	private Dao() {
		try {
			if (conn == null) {
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			}
			else
				return;
		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}
	private static ResultSet executeQuery(String sql) {
		try {
			if(conn==null)new Dao();
			return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}
	private static int executeUpdate(String sql) {
		
		try {
			if(conn==null)
				new Dao();
			return conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());			
			return -1;
		} finally {
		}
	}
	
	public static void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn = null;
		}
	}

	public static Operater check(String name, String password, String type) {
		int i = 0;
		Operater operater=new Operater();
		String sql;
		switch(type){
		case "READER":
		sql = "select *  from READER where READERID='" + name
			+ "'";
		break;
		default:
		sql = "select *  from administrator where aname='" + name
				+ "' and apwd='" + password + "'";
		}
		ResultSet rs = Dao.executeQuery(sql);
		try {
			switch(type){
			case "READER":
			while (rs.next()) {
				String names = rs.getString(1);
				operater.setName(rs.getString("RNAME"));
				operater.setPassword(String.valueOf(rs.getInt("READERID")));
				operater.setType("READER");
				if (names != null) {
					i = 1;
				}
			}
			break;
			default:
				while (rs.next()) {
					String names = rs.getString(1);
					operater.setName(rs.getString("aname"));
					operater.setPassword(rs.getString("apwd"));
					operater.setType("ADMIN");
					if (names != null) {
						i = 1;
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return operater;
	}

	public static int insertDoc(String DOCID,String TITLE,String PDATE,String PUBLISHERID, String DOCTYPE){
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(PDATE);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date sqlbdtime = new java.sql.Date(date.getTime());
		try{
			String sql="insert into DOCUMENT(DOCID,TITLE,PDATE,PUBLISHERID,DOCTYPE) values('"+DOCID+"','"+TITLE+"',TO_DATE('"+PDATE+"','yyyy-MM-dd'),'"+PUBLISHERID+"','"+DOCTYPE+"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int countId(String id) {
		int countid = 0;
		String group = "";
		String tablename = "DOCUMENT";
		switch(id){
		case "AUTHORID":
		tablename = "AUTHOR";
		break;
		case "EDITOR_ID":
		tablename = "CHIEF_EDITOR";
		break;
		case "PUBLISHERID":
		tablename = "PUBLISHER";
		break;
		case "LIBID":
		tablename = "BRANCH";
		break;
		case "READERID":
		tablename = "READER";
		break;
		case "RESNUMBER":
		tablename = "RESERVES";
		break;
		case "BORNUMBER":
		tablename = "BORROWS";
		break;
		}
		String sql = "select max(" + id + ") from " + tablename + group;
		//System.out.println(sql);
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				countid = rs.getInt("max(" + id + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return countid;
	}
	
	public static int countId(String id, String sid) {
		int countid = 0;
		String sidstring = "";
		String tablename = "";
		switch(id){
		case "ISSUE_NO":
		tablename = "JOURNAL_ISSUE";
		sidstring = " where DOCID='"+sid+"'";
		break;
		case "COPYNO":
		tablename = "COPY";
		sidstring = " where DOCID='"+sid+"'";
		break;
		}
		String sql = "select max(" + id + ") from " + tablename + sidstring;
		//System.out.println(sql);
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				countid = rs.getInt("max(" + id + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return countid;
	}

	public static int insertPub(String PUBLISHERID, String PUBNAME, String ADDRESS){
			int i=0;
			try{
				String sql="insert into PUBLISHER(PUBLISHERID,PUBNAME,ADDRESS) values('"+PUBLISHERID+"','"+PUBNAME+"','"+ADDRESS+"')";
				//System.out.println(sql);
				i=Dao.executeUpdate(sql);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			Dao.close();
			return i;
	}
	
	public static int insertAut(String AUTHORID, String ANAME) {
		int i=0;
		try{
			String sql="insert into AUTHOR(AUTHORID,ANAME) values('"+AUTHORID+"','"+ANAME+"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int insertWri(String AUTHORID, String DOCID) {
		int i=0;
		try{
			String sql="insert into WRITES(AUTHORID,DOCID) values('"+AUTHORID+"','"+ DOCID +"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int insertBook(String DOCID, String ISBN) {
		int i=0;
		try{
			String sql="insert into BOOK(DOCID,ISBN) values('"+DOCID+"','"+ ISBN +"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int insertPro(String DOCID, String CDATE, String CLOCATION, String CEDITOR) {
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(CDATE);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date sqlbdtime = new java.sql.Date(date.getTime());
		try{
			String sql="insert into PROCEEDINGS(DOCID,CDATE,CLOCATION,CEDITOR) values('"+DOCID+"',TO_DATE('"+ CDATE +"','yyyy-MM-dd'),'"+CLOCATION+"','"+ CEDITOR +"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int insertChi(String EDITOR_ID, String ENAME) {
		int i=0;
		try{
			String sql="insert into CHIEF_EDITOR(EDITOR_ID,ENAME) values('"+EDITOR_ID+"','"+ ENAME +"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int insertJvolume(String DOCID, String JVOLUME, String EDITOR_ID) {
		int i=0;
		try{
			String sql="insert into JOURNAL_VOLUME(DOCID,JVOLUME,EDITOR_ID) values('"+DOCID+"','"+ JVOLUME +"','"+EDITOR_ID +"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	public static int insertJissue(String DOCID, String ISSUE_NO, String SCOPE) {
		int i=0;
		try{
			String sql="insert into JOURNAL_ISSUE(DOCID,ISSUE_NO,SCOPE) values('"+DOCID+"','"+ ISSUE_NO +"','"+SCOPE +"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	public static int insertGeditor(String DOCID, String ISSUE_NO, String IENAME) {
		int i=0;
		try{
			String sql="insert into INV_EDITOR(DOCID,ISSUE_NO,IENAME) values('"+DOCID+"','"+ ISSUE_NO +"','"+IENAME +"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static List<?> searchChi() {
		List list=new ArrayList();
		String sql = "select * from CHIEF_EDITOR";
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Author author=new Author();
				author.setId(String.valueOf(rs.getInt("EDITOR_ID")));
				author.setName(rs.getString("ENAME"));
				list.add(author);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
		
	public static List<?> searchAut() {
		List list=new ArrayList();
		String sql = "select * from AUTHOR";
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Author author=new Author();
				author.setId(String.valueOf(rs.getInt("AUTHORID")));
				author.setName(rs.getString("ANAME"));
				list.add(author);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
	
	public static List searchPub(String pubname, String address) {
		List list=new ArrayList();
		String sql = "select * from PUBLISHER WHERE PUBLISHERID>0 "+ pubname+address;
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Publisher publisher=new Publisher();
				publisher.setId(String.valueOf(rs.getInt("PUBLISHERID")));
				publisher.setName(rs.getString("PUBNAME"));
				publisher.setAdd(rs.getString("ADDRESS"));
				list.add(publisher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
	
	public static List searchJou (String docname, String pdate, String publishername, String jvolume, String ceditor) {
		List list=new ArrayList();
		String sql;
		ResultSet rs;
			sql = "select *  from JOURNAL_VOLUME J, DOCUMENT D, PUBLISHER P, CHIEF_EDITOR C "
					+ "where D.DOCID = J.DOCID AND D.PUBLISHERID = P.PUBLISHERID AND J.EDITOR_ID = C.EDITOR_ID " 
					+ docname  + pdate + publishername + jvolume + ceditor;
			//System.out.println(sql);
			rs = Dao.executeQuery(sql);
			try {
				while (rs.next()) {
					Journal jou=new Journal();					
					jou.setId(String.valueOf(rs.getInt("DOCID")));
					jou.setName(rs.getString("TITLE"));
					jou.setJvolume(String.valueOf(rs.getInt("JVOLUME")));
					jou.setPName(rs.getString("PUBNAME"));
					jou.setDate(rs.getString("PDATE"));
					jou.setEName(rs.getString("ENAME"));
					jou.setPublisher(String.valueOf(rs.getInt("PUBLISHERID")));
					jou.setEid(String.valueOf(rs.getInt("EDITOR_ID")));
					list.add(jou);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dao.close();
			return list;
	}
	public static List searchJou_iss (String docname, String pdate, String publishername, String volume, String scope, String geditor) {
		List list=new ArrayList();
		String sql;
		ResultSet rs;
			sql = "select *  from JOURNAL_VOLUME J, DOCUMENT D, JOURNAL_ISSUE JO, INV_EDITOR I, PUBLISHER P "
					+ "where D.PUBLISHERID = P.PUBLISHERID AND D.DOCID = J.DOCID AND J.DOCID = JO.DOCID AND JO.ISSUE_NO = I.ISSUE_NO AND I.DOCID = JO.DOCID "
					+ docname + pdate + publishername + volume + scope + geditor;
			//System.out.println(sql);
			rs = Dao.executeQuery(sql);
			try {
				while (rs.next()) {
					Journal jou=new Journal();					
					jou.setId(String.valueOf(rs.getInt("DOCID")));
					jou.setName(rs.getString("TITLE"));
					jou.setJvolume(String.valueOf(rs.getInt("JVOLUME")));
					jou.setScope(rs.getString("SCOPE"));
					jou.setIEname(rs.getString("IENAME"));
					jou.setIss(String.valueOf(rs.getInt("ISSUE_NO")));
					jou.setPName(rs.getString("PUBNAME"));
					jou.setDate(rs.getString("PDATE"));
					list.add(jou);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dao.close();
			return list;
	}
	
	public static List searchPro (String docname, String pdate, String publishername, String cdate, String cloc, String coneditor) {
		List list=new ArrayList();
		String sql;
		ResultSet rs;
			sql = "select *  from PROCEEDINGS PR,DOCUMENT D, PUBLISHER P WHERE PR.DOCID = D.DOCID AND D.PUBLISHERID = P.PUBLISHERID "
					+ docname + pdate + publishername + cdate + cloc + coneditor;
			//System.out.println(sql);
			rs = Dao.executeQuery(sql);
			try {
				while (rs.next()) {
					Proceeding pro=new Proceeding();					
					pro.setId(String.valueOf(rs.getInt("DOCID")));
					pro.setName(rs.getString("TITLE"));
					pro.setCName(rs.getString("CEDITOR"));
					pro.setCdate(rs.getString("CDATE"));
					pro.setLoc(rs.getString("CLOCATION"));
					pro.setDate(rs.getString("PDATE"));
					pro.setPName(rs.getString("PUBNAME"));
					pro.setPublisher(String.valueOf(rs.getInt("PUBLISHERID")));
					list.add(pro);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dao.close();
			return list;
	}
	
	public static List searchBook (String docname, String pdate, String publishername, String isbn, String author) {
		List list=new ArrayList();
		String sql;
		ResultSet rs;
			sql = "select *  from BOOK B, DOCUMENT D, WRITES W, AUTHOR A, PUBLISHER P WHERE B.DOCID = D.DOCID AND W.DOCID = B.DOCID AND"
					+ " A.AUTHORID = W.AUTHORID AND D.PUBLISHERID = P.PUBLISHERID " + docname + pdate + publishername + author + isbn;
			//System.out.println(sql);
			rs = Dao.executeQuery(sql);
			try {
				while (rs.next()) {
					Book book=new Book();					
					book.setId(String.valueOf(rs.getInt("DOCID")));
					book.setName(rs.getString("TITLE"));
					book.setAName(rs.getString("ANAME"));
					book.setPName(rs.getString("PUBNAME"));
					book.setDate(rs.getString("PDATE"));
					book.setISBN(rs.getString("ISBN"));
					book.setPublisher(String.valueOf(rs.getInt("PUBLISHERID")));
					book.setAid(String.valueOf(rs.getInt("AUTHORID")));
					list.add(book);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dao.close();
			return list;
		}

	public static int delJou_iss(String DOCID, String ISSUE_NO) {
		int i=0;
		try{
			String sql="delete from JOURNAL_ISSUE where DOCID='"+DOCID+"' AND ISSUE_NO='"+ISSUE_NO+"'";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		Dao.close();
		return i;
	}
	
	public static int updJou_iss(String DOCID, String ISSUE_NO, String SCOPE, String OIENAME,String IENAME) {
		int i=0;
		try{
			String sql="update JOURNAL_ISSUE set SCOPE='"+SCOPE+"' where DOCID='"+DOCID+"' AND ISSUE_NO='"+ISSUE_NO+"'";
			Dao.executeUpdate(sql);
			String sql1_5="delete from INV_EDITOR WHERE DOCID='"+DOCID+"' AND ISSUE_NO='"+ISSUE_NO+"' AND IENAME='"+OIENAME+"'";
			Dao.executeUpdate(sql1_5);
			String sql2="insert into INV_EDITOR(DOCID,ISSUE_NO,IENAME) VALUES('"+DOCID+"','"+ISSUE_NO+"','"+IENAME+"')";
			i=Dao.executeUpdate(sql2);	
			} catch(Exception e) {
				e.printStackTrace();
			}
		Dao.close();
		return i;
	}
	
	public static int updDoc(String DOCID, String docname, String pdate, String publisherid) {
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(pdate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date sqlbdtime = new java.sql.Date(date.getTime());
		try{
			String sql="update DOCUMENT set TITLE='"+docname+"' ,PDATE=TO_DATE('"+pdate+"','yyyy-MM-dd') ,PUBLISHERID='"+publisherid+"' where DOCID='"+DOCID+"'";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		Dao.close();
		return i;
	}
	
	public static int updBook(String DOCID, String ISBN) {
			int i=0;
			try{
				String sql="update BOOK set ISBN='"+ISBN+"'where DOCID='"+DOCID+"'";
				//System.out.println(sql);
				i=Dao.executeUpdate(sql);
				} catch(Exception e) {
					e.printStackTrace();
				}
			Dao.close();
			return i;
	}
	public static int updJou(String DOCID, String JVOLUME, String CID) {
		int i=0;
		try{
			String sql="update JOURNAL_VOLUME set JVOLUME='"+JVOLUME+"' ,EDITOR_ID='"+CID+"' where DOCID='"+DOCID+"'";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		Dao.close();
		return i;
	}
	
	public static int updPro(String DOCID, String CDATE, String CLOCATION, String CEDITOR) {
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(CDATE);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date sqlbdtime = new java.sql.Date(date.getTime());
		try{
			String sql="update PROCEEDINGS set CDATE= TO_DATE('"+CDATE+"','yyyy-MM-dd') ,CLOCATION='"+CLOCATION+"' ,CEDITOR='"+CEDITOR+"' where DOCID='"+DOCID+"'";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		Dao.close();
		return i;
	}
	
	public static int InsertReader(String Rid,String Rtype,String Rname,String Radd){
		int i=0;
		try{
			String sql="insert into READER(READERID,RTYPE,RNAME,ADDRESS) values('"+Rid+"','"+Rtype+"','"+Rname+"','"+Radd+"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		Dao.close();
		return i;
	}
	
	public static int UpdateReader(String Rid,String RTYPE,String RNAME, String ADDRESS){
		int i=0;
		try{
			String sql="update READER set RTYPE='"+RTYPE+"',RNAME='"+RNAME+"',ADDRESS='" + ADDRESS+ "' where READERID='"+Rid+"'";
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		Dao.close();
		return i;
	}
	
	public static List searchReader (String Rid, String Rtype, String Rname, String Add) {
		List list=new ArrayList();
		String sql;
		ResultSet rs;
			sql = "select *  from READER where READERID>0 "
					+ Rid + Rtype + Rname + Add;
			//System.out.println(sql);
			rs = Dao.executeQuery(sql);
			try {
				while (rs.next()) {
					Reader reader=new Reader();					
					reader.setRid(String.valueOf(rs.getInt("READERID")));
					reader.setName(rs.getString("RNAME"));
					reader.setRtype(rs.getString("RTYPE"));
					reader.setRadd(rs.getString("ADDRESS"));
					list.add(reader);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Dao.close();
			return list;
	}
	public static int insertBranch(String bid, String lname, String location) {
		int i=0;
		try{
			String sql="insert into BRANCH(LIBID,LNAME,LLOCATION) values('"+bid+"','"+lname+"','"+location+"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int updCopy(String did, String Cno, String bid, String position, String status) {
		int i=0;
		try{
			String sql="update COPY set LIBID='"+bid+"',POSITION='"+position+"',STATUS='"+status+"' where DOCID='"+did+"' AND COPYNO='" + Cno+"'";
			System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	public static int insertCopy(String docid, String cno, String bid,String position) {
		int i=0;
		try{
			String sql="insert into COPY(DOCID,COPYNO,LIBID,POSITION) values('"+docid+"','"+cno+"','"+bid+"','"+position+"')";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	public static List<?> searchBranch(String lname, String llocation) {
		List list=new ArrayList();
		String sql = "select * from BRANCH WHERE LIBID>0 "+ lname+llocation;
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Branch branch=new Branch();
				branch.setId(String.valueOf(rs.getInt("LIBID")));
				branch.setName(rs.getString("LNAME"));
				branch.setLoc(rs.getString("LLOCATION"));
				list.add(branch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
	public static List searchCopy(String did, String lname, String position) {
		List list=new ArrayList();
		String sql = "select * from COPY C,BRANCH BR, DOCUMENT D WHERE C.DOCID>0 "+ lname+position+
				" AND C.LIBID=BR.LIBID AND D.DOCID=C.DOCID AND D.DOCID='"+did+"'";
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Copy copy=new Copy();
				copy.setId(String.valueOf(rs.getInt("DOCID")));
				copy.setTitle(rs.getString("TITLE"));
				copy.setNo(String.valueOf(rs.getInt("COPYNO")));
				copy.setLname(rs.getString("LNAME"));
				copy.setLoc(rs.getString("POSITION"));
				copy.setLid(String.valueOf(rs.getInt("LIBID")));
				copy.setS(rs.getString("STATUS"));				
				list.add(copy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}

	public static int insertBor(String bno, String rid, String docid,String cno, String bid) {
		int i=0;
		java.util.Date utilDate = new Date();
		java.sql.Date sqlbdtime = new java.sql.Date(utilDate.getTime());		
		try{
			String sql="insert into BORROWS(BORNUMBER,READERID,DOCID,COPYNO,LIBID,BDTIME,RDTIME) values('"+bno+"','"+rid+"','"+docid+"','"+cno+"','"+bid+"',TO_DATE('"+sqlbdtime+"','yyyy-MM-dd'),'')";	
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static int insertRes(String rno, String rid, String docid,String cno, String bid) {
		int i=0;
		java.util.Date utilDate = new Date();
		java.sql.Date sqldtime = new java.sql.Date(utilDate.getTime());		
		try{
			String sql="insert into RESERVES(RESNUMBER,READERID,DOCID,COPYNO,LIBID,DTIME) values('"+rno+"','"+rid+"','"+docid+"','"+cno+"','"+bid+"',TO_DATE('"+sqldtime+"','yyyy-MM-dd'))";	
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	
	public static List searchRes(String rno, String rid, String docid,
			String cno, String bid, String dtime) {
		List list=new ArrayList();
		String dtimestring = "";
		if (dtime != "") {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd");
				java.util.Date date = sdf.parse(dtime);
				java.sql.Date sqldtime = new java.sql.Date(date.getTime());
				dtimestring = " AND R.DTIME = TO_DATE('" + sqldtime
						+ "','yyyy-MM-dd')";
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		String sql = "select * from RESERVES R, DOCUMENT D, COPY C, BRANCH B where R.RESNUMBER>0 AND R.DOCID=C.DOCID AND "
				+ "R.COPYNO=C.COPYNO AND R.LIBID=C.LIBID AND C.LIBID=B.LIBID AND C.DOCID=D.DOCID" + rno
					+ rid + docid + cno + bid + dtimestring;
		//System.out.println(sql);
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Reserve res=new Reserve();
				res.setRno(String.valueOf(rs.getInt("RESNUMBER")));
				res.setId(String.valueOf(rs.getInt("DOCID")));
				res.setTitle(rs.getString("TITLE"));
				res.setNo(String.valueOf(rs.getInt("COPYNO")));
				res.setLname(rs.getString("LNAME"));
				res.setDtime(rs.getString("DTIME"));
				res.setLoc(rs.getString("POSITION"));
				res.setLid(rs.getString("LIBID"));				
				list.add(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
	
	public static int updBor(String bno) {
		int i=0;
		java.util.Date utilDate = new Date();
		java.sql.Date sqlrtime = new java.sql.Date(utilDate.getTime());		
		try{
			String sql="update BORROWS set RDTIME= TO_DATE('"+sqlrtime+"','yyyy-MM-dd') where BORNUMBER='"+bno+"'";	
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		Dao.close();
		return i;
	}
	public static List searchBor(String bno, String rid, String docid,
			String cno, String bid, String bdtime, String rdtime) {
		List list=new ArrayList();
		String bdtimestring = "";
		if (bdtime != "") {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd");
				java.util.Date date = sdf.parse(bdtime);
				java.sql.Date sqlbdtime = new java.sql.Date(date.getTime());
				bdtimestring = " AND BO.BDTIME = TO_DATE('" + sqlbdtime
						+ "','yyyy-MM-dd')";
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		String sql = "select * from BORROWS BO, DOCUMENT D, COPY C, BRANCH B where BO.BORNUMBER>0 AND BO.DOCID=C.DOCID AND "
				+ "BO.COPYNO=C.COPYNO AND BO.LIBID=C.LIBID AND C.LIBID=B.LIBID AND C.DOCID=D.DOCID" + bno
					+ rid + docid + cno + bid + bdtimestring + rdtime;
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Borrow bor=new Borrow();
				bor.setBno(String.valueOf(rs.getInt("BORNUMBER")));
				bor.setId(String.valueOf(rs.getInt("DOCID")));
				bor.setTitle(rs.getString("TITLE"));
				bor.setNo(String.valueOf(rs.getInt("COPYNO")));
				bor.setLname(rs.getString("LNAME"));
				bor.setBtime(rs.getString("BDTIME"));
				bor.setRtime(rs.getString("RDTIME"));
				bor.setLoc(rs.getString("POSITION"));
				bor.setLid(rs.getString("LIBID"));
				list.add(bor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
	
	public static int del(String table, String id, String sid) {
		int n = 0;
		String idname = "";
		String second = "";
		String end = "";
		switch(table){
		case "DOCUMENT":
		idname = "DOCID";
		break;
		case "WRITES":
		idname = "AUTHORID";
		second = " AND DOCID='";
		end = "'";
		break;
		case "AUTHOR":
		idname = "AUTHORID";
		break;
		case "CHIEF_EDITOR":
		idname = "EDITOR_ID";
		break;
		case "PUBLISHERID":
		idname = "PUBLISHER";
		break;
		case "BRANCH":
		idname = "LIBID";
		break;
		case "RESERVES":
		idname = "RESNUMBER";
		break;
		case "READER":
		idname = "READERID";
		break;
		case "JOURNAL_ISSUE":
		idname = "ISSUE_NO";
		second = " AND DOCID='";
		end = "'";
		break;
		case "COPY":
		idname = "COPYNO";
		second = " AND DOCID='";
		end = "'";
		break;
		case "BORROWS":
		idname = "BORNUMBER";
		break;
		}
		String sql = "delete from " + table + " where "+idname+"='"+id+"' "+second+sid+end;
		System.out.println(sql);
		n = Dao.executeUpdate(sql);
		Dao.close();
		return n;
	}
	
	public static int updPub(String pid, String pname, String add) {
		int i=0;
			String sql="update PUBLISHER set PUBNAME= '"+pname+"', ADDRESS='"+add+"' where PUBLISHERID='"+pid+"'";	
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		Dao.close();
		return i;
	}
	
	public static int updBranch(String bid, String lname, String add) {
		int i=0;
		String sql="update BRANCH set LNAME= '"+lname+"', LLOCATION='"+add+"' where LIBID='"+bid+"'";
			//System.out.println(sql);
			i=Dao.executeUpdate(sql);
		Dao.close();
		return i;
	}
	public static List searchPub(String id, String name,String add) {
		List list=new ArrayList();		
		String sql = "select * from PUBLISHER where PUBLISHERID>0 "+id+name+add;
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Publisher pub=new Publisher();
				pub.setId(String.valueOf(rs.getInt("PUBLISHERID")));
				pub.setName(rs.getString("PUBNAME"));
				pub.setAdd(rs.getString("ADDRESS"));			
				list.add(pub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
	
	public static List searchBranch(String id, String name,String add) {
		List list=new ArrayList();
		
		String sql = "select * from BRANCH where LIBID>0 "+id+name+add;
		ResultSet rs = Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				Publisher pub=new Publisher();
				pub.setId(String.valueOf(rs.getInt("LIBID")));
				pub.setName(rs.getString("LNAME"));
				pub.setAdd(rs.getString("LLOCATION"));			
				list.add(pub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.close();
		return list;
	}
	public static List searchTopBor(String lid) {
		List list=new ArrayList();
		String sql = "CREATE VIEW TMP(DOCID,COUNT) AS select DOCID, COUNT(BORNUMBER) from BORROWS where LIBID='"+lid+"' GROUP BY DOCID";
		String sql3 = "SELECT * FROM DOCUMENT D, TMP TM WHERE D.DOCID=TM.DOCID ORDER BY COUNT DESC ";
		Dao.executeQuery(sql);
		ResultSet rs = Dao.executeQuery(sql3);
		try {
			while (rs.next()) {
				Borrow bor=new Borrow();
				bor.setId(String.valueOf(rs.getInt("DOCID")));
				bor.setTitle(rs.getString("TITLE"));
				bor.setNo(String.valueOf(rs.getInt("COUNT")));
				list.add(bor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.executeQuery("DROP VIEW TMP");	
		Dao.close();
		return list;
	}
	
	public static List searchTopBor2(String lid) {
		List list=new ArrayList();
		String sql = "CREATE VIEW TMP(READERID,BORNUMBER) AS select R.READERID, BO.BORNUMBER from BORROWS BO, READER R where BO.BORNUMBER>0 AND BO.READERID=R.READERID" + lid;
		String sql2 = "CREATE VIEW TMP2(READERID,COUNT) AS SELECT READERID,COUNT(BORNUMBER) FROM TMP GROUP BY READERID";
		String sql3 = "SELECT * FROM READER R, TMP2 TM WHERE R.READERID=TM.READERID ORDER BY COUNT DESC";
		Dao.executeQuery(sql);
		Dao.executeQuery(sql2);	
		ResultSet rs = Dao.executeQuery(sql3);
		try {
			while (rs.next()) {
				Reader reader=new Reader();
				reader.setRid(String.valueOf(rs.getInt("READERID")));
				reader.setName(rs.getString("RNAME"));
				reader.setRtype(rs.getString("RTYPE"));
				reader.setRadd(rs.getString("COUNT"));
				list.add(reader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Dao.executeQuery("DROP VIEW TMP");
		Dao.executeQuery("DROP VIEW TMP2");		
		Dao.close();
		return list;
	}
}
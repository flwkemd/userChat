package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ChatDAO {
	
	DataSource dataSource;
	Connection conn;
	
	public ChatDAO() {
		try{
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			conn = dataSource.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChatDTO> getChatListById(String fromId, String toId, String chatId){
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String SQL = "SELECT * FROM CHAT WHERE ((fromId = ? AND toId =? ) OR (fromId = ? AND toId=?)) AND chatId > ? ORDER BY chatTime";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fromId);
			pstmt.setString(2, toId);
			pstmt.setString(3, toId);
			pstmt.setString(4, fromId);
			pstmt.setInt(5, Integer.parseInt(chatId));
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatId(rs.getInt("chatId"));
				chat.setFromId(rs.getString("fromId").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToId(rs.getString("fromId").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("fromId").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				Timestamp chatTime = rs.getTimestamp("chatTime");
				chatList.add(chat);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!= null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		 return chatList; //리스트 반환
	}
	
	public ArrayList<ChatDTO> getChatListByRecent(String fromId, String toId, String chatId, int number){
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String SQL = "SELECT * FROM CHAT WHERE ((fromId = ? AND toId =? ) OR (fromId = ? AND toId=?)) AND chatId > (SELECT MAX(chatId) - ? FROM CHAT) ORDER BY chatTime";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fromId);
			pstmt.setString(2, toId);
			pstmt.setString(3, toId);
			pstmt.setString(4, fromId);
			pstmt.setInt(5, number);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatId(rs.getInt("chatId"));
				chat.setFromId(rs.getString("fromId").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToId(rs.getString("fromId").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("fromId").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				Timestamp chatTime = rs.getTimestamp("chatTime");
				chatList.add(chat);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!= null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList; //리스트 반환
	}
	
	public int submit(String fromId, String toId, String chatContent){
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String SQL = "INSERT INTO CHAT VALUES (CHAT_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fromId);
			pstmt.setString(2, toId);
			pstmt.setString(3, chatContent);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!= null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류
	}
	
	
}

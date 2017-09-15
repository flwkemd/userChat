package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String fromId = request.getParameter("fromId");
		String toId = request.getParameter("toId");
		String listType = request.getParameter("listType");
		if(fromId == null || fromId.equals("") || toId == null || toId.equals("") || listType == null || listType.equals("")) 
			response.getWriter().write("");
		else if(listType.equals("ten")) 
			response.getWriter().write(getTen(URLDecoder.decode(fromId, "utf-8"), URLDecoder.decode(toId, "utf-8")));
		else {
			try {
				response.getWriter().write(getId(URLDecoder.decode(fromId, "utf-8"), URLDecoder.decode(toId, "utf-8"), listType));
			}catch(Exception e) {
				response.getWriter().write("");
			}
		}
	}

	public String getTen(String fromId, String toId) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromId, toId, 10);
		if(chatList.size() == 0 ) return "";
		for(int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromId()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToId()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime()+ "\"}]");
			
			if(i != chatList.size() -1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatId() + "\"}");
		return result.toString();
	}

	public String getId(String fromId, String toId, String chatId) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListById(fromId, toId, chatId);
		if(chatList.size() == 0 ) return "";
		for(int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromId()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToId()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent()+ "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime()+ "\"}]");
			
			if(i != chatList.size() -1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatId() + "\"}");
		return result.toString();
	}
	
}

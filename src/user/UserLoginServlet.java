package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("userPassword");
		if(userId == null || userId.equals("") || userPassword == null || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력해주세요.");
			response.sendRedirect("login.jsp");
			return;
		}
		int result = new UserDAO().login(userId, userPassword);
		if(result == 1) {
			request.getSession().setAttribute("userId", userId);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
			response.sendRedirect("index.jsp");
		}else if(result == 2) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호를 다시 확인하세요.");
			response.sendRedirect("login.jsp");
		}else if(result == 0) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "아이디가 존재하지 않습니다.");
			response.sendRedirect("login.jsp");
		}else if(result == -1) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			response.sendRedirect("login.jsp");
		}
	}

}

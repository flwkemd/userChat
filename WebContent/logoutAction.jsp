<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>JSP 실시간 채팅</title>
</head>
<body>
	<%
		session.invalidate();
	%>
	<script type="text/javascript">
		location.href= 'index.jsp';
	</script>
	
</body>
</html>
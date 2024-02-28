<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고,이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다 라고 설정함 -->
	<!-- 현재 이 JSP 페이지를 저장할 때는 UTF-8 방식으로 인코딩 한다 -->
	<!-- 모든 JSP 페이지 상단에 무조건 아래 설정이 들어간다.         -->
	
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@include file="/WEB-INF/views/common.jsp" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<body>
	<center>
		서버에 문제가 발생했습니다. 관리자에게 문의 바랍니다.<br><br>
		<span style="cursor:pointer"
		 onclick="location.replace('/loginForm.do')">로그인 화면으로....</span>
	</center>
</body>
</html>
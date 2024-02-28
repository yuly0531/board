

<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고,이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다 라고 설정함 -->
	<!-- 현재 이 JSP 페이지를 저장할때는 UTF-8 방식으로 인코딩 한다 -->
	<!-- 모든 JSP 페이지 상단에 무조건 아래 설정이 들어간다. -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!-- 아래 코딩 이후에 나오는 c코어 태그는 수입된 라이브러리(JSTL)에서 
			정한 규칙에 의해 해석하라.  
			이 코딩이 없으면 C코어태그는 아무런 의미가 없다.
			
			-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<!-- style.css 파일 내용 수입하기           -->
<!-- JQuery 라이브러리 수입하기             -->
<!-- common.js 파일안의 자스 코딩 수입하기  -->
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<link href="/css/style3.css?<%=Math.random()%>" rel="stylesheet">
<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/common.js?<%=Math.random()%>"></script>

<div onclick="location.replace('/loginForm.do')" style='cursor:pointer' align="right">[로그아웃]</div>












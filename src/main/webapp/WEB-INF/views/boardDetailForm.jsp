

<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고,이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다 라고 설정함 -->
	<!-- 현재 이 JSP 페이지를 저장할때는 UTF-8 방식으로 인코딩 한다 -->
	<!-- 모든 JSP 페이지 상단에 무조건 아래 설정이 들어간다. -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- JSP기술의 한 종류인 Include Directive 를 이용하여 common.jsp 파일내의 소스를 삽입하기 -->
<%@include file="/WEB-INF/views/common.jsp" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>


<script>
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//웹브라우저가 body 태그 안을 모두 읽어들인 후 init() 함수 호출하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	$(function(){ init(); })
	
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//웹브라우저가 body 태그 안을 모두 읽어들인 후 실행할  init() 함수 선언하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function init(){
		
	}
	
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//게시판 수정/삭제 화면으로 이동하는 함수 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function goBoardListForm(){
		//*********************************************
		// name=boardListForm 을 가진 form 태그의 action 값의 URL 주소로 서버에 접속하라
		// form 태그 내부의 입력양식들의 입력된 데이터도 서버로 전송된다.
		//*********************************************
		document.boardListForm.submit();
	}
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 게시판 수정/삭제 화면으로 이동하는 함수 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function goBoardUpDelForm(){
		//*********************************************
		// name=boardUpDelForm 을 가진 form 태그의 action 값의 URL 주소로 서버에 접속하라
		// form 태그 내부의 입력양식들의 입력된 데이터도 서버로 전송된다.
		// 현재는 hidden 태그에 입려독된 게시판 번호가 전송된다.
		//*********************************************
		document.boardUpDelForm.submit();
	}
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 게시판 댓글쓰기 화면으로 이동하는 함수 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function goBoardRegForm(){ 
		//*********************************************
		// name=boardRegForm 을 가진 form 태그의 action 값의 URL 주소로 서버에 접속하라
		// form 태그 내부의 입력양식들의 입력된 데이터도 서버로 전송된다.
		// 현재는 hidden 태그에 입려독된 게시판 번호가 전송된다.
		//*********************************************
		document.boardRegForm.submit();
	}
</script>

</head>
<body>
<center>
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->	
	<!-- 
		HttpServletRequest객체에 
		키값"boardDTO"로 저장된 데이터가 null 아니면 
		BoardController 객체안에 "/boardUpDelForm.do" 주소를 맞이하는 메소드 내부에
		mav.addObject(   "boardDTO" , boardDTO     ); 이 코드에서 저장된 놈을 말한다.
		ModelAndView객체에 addObject 메소드로 저장된 놈은
		HttpServletRequest객체에 저장된다.-->
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!-- empty는 EL문법이다. 
			empty오른쪽의 데이터가 null이거나 길이가 없는 데이터라면 true를 리턴한다 -->
	
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<form name="boardDetailForm">
		<!-- <table border="1" cellpadding="7" cellspacing="0" 
							style="border-collapse:collapse" align="center" >  -->
							 <table class="tableB" > 
			<caption>[게시판 상세글 보기]</caption>
			<tr>
				<th>이 름</th>
				<td width="300px"> 
					<!--------------------------------------------------->
					<!--EL 문법으로 HttpServletRequest 객체에 키값 "boardDTO" 로 -->
					<!--저장된 객체의 멤버변수 writer 의 저장값 표현하기          -->
					<!--------------------------------------------------->
					${requestScope.boardDTO.writer} 
				</td>
			</tr>
			<tr>
				<th>제 목</th>
				<td> 
					<!--------------------------------------------------->
					<!--EL 문법으로 HttpServletRequest 객체에 키값 "boardDTO" 로 -->
					<!--저장된 객체의 멤버변수 subject 의 저장값 표현하기          --> 
					<!--------------------------------------------------->
					${requestScope.boardDTO.subject} 
				</td> 
			</tr>
			<tr>
				<th>조회수</th>
				<td> 
					<!--------------------------------------------------->
					<!--EL 문법으로 HttpServletRequest 객체에 키값 "boardDTO" 로 -->
					<!--저장된 객체의 멤버변수 readcount 의 저장값 표현하기          -->
					<!--------------------------------------------------->
					${requestScope.boardDTO.readcount} 
				</td> 
			</tr>
			<tr>
				<th>이메일</th>
				<td> 
					<!--------------------------------------------------->
					<!--EL 문법으로 HttpServletRequest 객체에 키값 "boardDTO" 로 -->
					<!--저장된 객체의 멤버변수 email 의 저장값 표현하기          -->
					<!--------------------------------------------------->
					${requestScope.boardDTO.email} 
				</td> 
			</tr>
			<tr>
				<th>내 용</th>
				<td> 
					<textarea name="content" class="content" rows="13" cols="32" 
            maxlength="500" style="border:none; resize: none; outline: none;" readonly>${requestScope.boardDTO.content}</textarea>
				</td>
			</tr>
			<tr>
				<th>이미지</th>
				<td> 
					<c:if test="${!empty requestScope.boardDTO.img_name}">
						<img src="/img/${requestScope.boardDTO.img_name}" height="400px">
					</c:if>
				</td>
			</tr>
			
		</table>
			<div style="height:5px;"></div>
			<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
			<!-- 파명 "mom_b_no"에 대응하는 파값을 입력 양식에에 삽입하기   
			<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
			<input type="hidden" name="mom_b_no" value="${param.mom_b_no}">
			<!-------------------------------------------------------->	
			<input type="button" value="답글쓰기" onClick="goBoardRegForm();">
			<input type="button" value="수정/삭제하기" onClick="goBoardUpDelForm();">
			<span style="cursor:pointer" onClick="goBoardListForm();">[목록보기]</span>
			<!-------------------------------------------------------->	
		
		<div style="height:15px;"></div>
	</form>
	
	
</center>		
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<!-- WAS에 "/boardList.do"  URL 주소로 접속하기 위한 form 태그 선언-->
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<form name="boardListForm" method="post" action="/boardList.do"> 
		<input type="hidden" name="search" value="${requestScope.boardDTO.b_no}">
		</form>
		
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<!-- WAS에 "/boardUpDelForm.do"  URL 주소로 접속하기 위한 form 태그 선언-->
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<form name="boardUpDelForm" method="post" action="/boardUpDelForm.do"> 
			<!----------------------------------------------------->
			<!-- WAS에 "/boardUpDelForm.do"  URL 주소로 접속할 때 가져갈 데이터 저장 목적의 hidden 태그 선언-->
			<!-- 현재 이 hidden 태그에는 수정삭제 할 게시판의 고유번호가 저장되어 있다.-->
			<!----------------------------------------------------->
			<input type="hidden" name="b_no" value="${requestScope.boardDTO.b_no}">
		</form>
		
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<!-- WAS에 "/boardRegForm.do"  URL 주소로 접속하기 위한 form 태그 선언-->
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<form name="boardRegForm" method="post" action="/boardRegForm.do"> 
			<!----------------------------------------------------->
			<!-- WAS에 "/boardRegForm.do"  URL 주소로 접속할 때 가져갈 데이터 저장 목적의 hidden 태그 선언-->
			<!-- 현재 이 hidden 태그에는 댓글쓰기 화면에서 필요한 엄마 글 게시판의 고유번호가 저장되어 있다.-->
			<!----------------------------------------------------->
			<input type="hidden" name="mom_b_no" value="${requestScope.boardDTO.b_no}">
		</form>
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->	
	<!-- 
		HttpServletRequest객체에 
		키값"boardDTO"로 저장된 데이터가 null 이면
		BoardController 객체안에 "/boardUpDelForm.do" 주소를 맞이하는 메소드 내부에
		mav.addObject(   "boardDTO" , boardDTO     ); 이 코드에서 저장된 놈을 말한다.
		ModelAndView객체에 addObject 메소드로 저장된 놈은
		HttpServletRequest객체에 저장된다.-->
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!-- empty는 EL문법이다. 
			empty오른쪽의 데이터가 null이거나 길이가 없는 데이터라면 true를 리턴한다 -->
	
</body>
</html>

<!-- 
	${requestScope.boardDTO.writer}  <br>
	${requestScope.boardDTO.subject}  <br>
	${requestScope.boardDTO.email}  <br>
	${requestScope.boardDTO.content}  <br>
	${requestScope.boardDTO.writer}  <br>
	${requestScope.boardDTO.writer}  <br>
 -->











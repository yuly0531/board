
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
<title>글쓰기</title>

<script>
$(function(){init();});

function init(){
	
	var formObj = $("[name='boardRegForm']");
	formObj.find("[name='writer']").val("땨오");
	formObj.find("[name='subject']").val("뺘오");
	formObj.find("[name='email']").val("xx@naver.com")
	formObj.find("[name='content']").val("호엥")
	formObj.find("[name='pwd']").val("1111")
	
	/**/
	<%
	/*
	out.print("var formObj = $('[name=boardRegForm]');");
	out.print("formObj.find('[name=writer]').val('킹킹이');");
	out.print("formObj.find('[name=subject]').val('제목');");
	out.print("formObj.find('[name=email]').val('xx@naver.com');");
	out.print("formObj.find('[name=content]').val('호엥');");
	out.print("formObj.find('[name=pwd]').val('1111');");
	*/
	%>
}

	//----------------------------------------
	// 목록보기 버튼을 클릭하면 호출되는 함수
	//----------------------------------------
	function goBoardListForm(){
		// name=boardListForm  을 가지 form 태그의 
		// action 값의 URL 주소로 WAS에 접속하기
		document.boardListForm.submit();
	}

//저장버튼 누르면 호출되는 함수
function checkBoardRegForm(){
	var formObj = $("[name='boardRegForm']");
	
	
	inputAfterBlankDel(formObj.find("[name='writer']"));
	inputAfterBlankDel(formObj.find("[name='email']"));
	inputAfterBlankDel(formObj.find("[name='subject']"));
	inputAfterBlankDel(formObj.find("[name='content']"));

	
	
	//*******************************************
	// 게시판 입력 양식의 유효성 체크하기
	//*******************************************
	// 등록,입력할때 암호는 친절해야하지만 그 외의 로그인할때, 수정할때 암호는 불친절 해야한다  
	
	/*var img_name = formObj.find("[name='img']").val();
	
	if( !checkSubject(formObj.find("[name='subject']"))  ) { return; }
	if( !checkEmail(formObj.find("[name='email']"))  )     { return; }
	if(!checkPwd(formObj.find("[name='pwd']")) )          	{return;}
	if( !checkContent(formObj.find("[name='content']"))  ) { return; }
	if( !checkWriter(formObj.find("[name='writer']"))  )   { return; }
	//if(!checkImg(formObj.find("[name='img']"))){alert() }
	
	*/
		
	
	
	//*******************************************
	// [등록여부]를 물어보기
	//*******************************************
	if(confirm("글을 올리시겠습니까?")==false) { return; }
		//confirm은 내장함수다
	
		
	//WAS로 boardRegProc.do URL주소로 접속하고
	// 입력 실행결과 정보가 담긴 JSON을 받고
	// 이 JSON 저장된 데이터에 따라 실행구문을 달리 실행하기
	
	ajax("/boardRegProc.do"
			//-------------------------------
			// WAS 로 접속하는 방법 설정. get 또는 post
			//-------------------------------
			,"post"
			//-------------------------------
			// 입력 양식을 끌어안고 있는 form 태그 관리 JQuery 객체 메위주
			//-------------------------------
			,formObj
			//-------------------------------
			// WAS 와 통신이 성공했을 호출되는 익명함수 설정.
			// 현재 익명함수의 매개 변수로 JSON 이 들어온다.
			// JSON 안에 입력 실행 결과 정보 담겨 있다.
			//-------------------------------
			,function(responseJson){
				//---------------------------------
				// WAS가 응답해준 JSON 에서 
				// 경고 문구 꺼내서 변수 errorMsg 에 저장하기
				// 입력된 행의 개수 꺼내서 변수 boardRegCnt 에 저장하기
				//---------------------------------
				var errorMsg    = responseJson["errorMsg"];
				var boardRegCnt = responseJson["boardRegCnt"]; 
				//---------------------------------
				// 변수 errorMsg 안에 경고 문구가 없고 
				// 변수 boardRegCnt  입력 성공 행의 개수가 1이면, 
				// 즉 입력이 성공했으면
				//---------------------------------
				if( errorMsg=="" && boardRegCnt==1 ){
					alert("${empty param.mom_b_no?'새글':'댓글'}쓰기 성공!");
					goBoardListForm();
				}
				//된 행의 개수가 -11이면 즉 업로드된 파일의 크기가 너무 큰거면
				else if(boardRegCnt==-11){
					alert("파일의 크기는 1000kb 이하여야 합니다.");
				}
				//수정된 행의 개수가 -12이면 즉 업로드된 파일의 확장자가 이미지파일이 아니면
				else if(boardRegCnt==-12){
					alert("파일의 확장자는 jpg, png, gif 파일만 업로드 가능합니다.");
				}
				//수정된 행의 개수가 -21이면 즉 유효성 체크시 에러가 발생했으면
				else if(boardRegCnt==-21){
					alert(errorMsg);
				}
				else{
					alert( errorMsg + ". ${empty param.mom_b_no?'새글':'댓글'}쓰기 실패!");
				}
				
			}
	);
}
	
</script>

</head>
<body>
	<center>
	<form name="boardRegForm">
		<table class="tableB" >
							<!-- 만약에 파라미터명 mom_b_no 의 파라미터값이 비었으면
							새글쓰기 표현하고 아니면 댓글쓰기 표현하기 -->
				<caption>${empty param.mom_b_no?"[새글쓰기]":"[댓글쓰기]"}</caption>
				<!-- ================================================================
							EL문법에서 달러{empty 데이터1?데이터2:데이터3} ?
					====================================================================
				EL의 삼항연산자 표현방식이다 데이터1이 null이나 길이가없는 문자데이터면 
				데이터2로 표현하고 아니면 데이터3으로 표현한다
				
					================================================================
							EL문법에서 달러{!empty 데이터1?데이터2:데이터3} ?
					====================================================================
							위와 반대다 empty는 null처리 함수네! nvl2랑 같다!
					
					================================================================
								달러{와} 안의 param.mom_b_no?
					====================================================================
					
								파라미터값을 표현하는 문법이다.
								형식은 param.파라미터명이다.
								URL주소로 접속시 가져온 파라미터값중에 파라미터명 mom_b_no의
								파라미터값을 표현한다.
								만약 새글쓰러 들어왔다면 파라미터명 mom_b_no의 파라미터값은 없을것이고,
								만약 답글쓰러 들어왔다면 파라미터명 mom_b_no의 파라미터값은 있다
								파라미터명 mom_b_no의 파라미터값은 부모글의 고유번호다
					
					
								파라미터명은 입력양식의 name값.
								파라미터값은 입력양식의 value값
				-->
				<tr>
					<th>이 름</th>
					<td  width="300px">
					<!-------------------------------------------------------->
					<input type="text" name="writer" class="writer" size="10" maxlength="15" >
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>제 목</th>
					<td>
					<!--------------------------------------------------------> 
					<input type="text" name="subject" class="subject" size="40" maxlength="30" 
							>
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
					<!-------------------------------------------------------->
					<input type="text" name="email" class="email" size="40" maxlength="50" 
							>
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>내 용</th>
					<td>
					<!-------------------------------------------------------->
					<textarea name="content"  class="content" rows="13" cols="40"  
						maxlength="500"></textarea>
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
					<!-------------------------------------------------------->
					<input type="password" name="pwd" class="pwd"  size="8"  maxlength="4" >
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>이미지</th>
					<td>
					<!-------------------------------------------------------->
					<input type="file" name="img" >
					<!-------------------------------------------------------->
					</td>
				</tr>
		</table>
			<!-------------------------------------------------------->	
			<c:if test="${!empty param.mom_b_no}">
				<input type="hidden" name="mom_b_no" value="${param.mom_b_no}">
			</c:if>
			<!-------------------------------------------------------->	
			<div style="height:5px;"></div>
			<!-------------------------------------------------------->	
			<input type="button" value="저장" onClick="checkBoardRegForm();">
			<input type="reset" value="다시작성">
			<span style="cursor:pointer" onClick="goBoardListForm();">[목록보기]</span>
			<!-------------------------------------------------------->	
		</form>
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!-- WAS에 "/boardList.do"  URL 주소로 접속하기 위한 form 태그 선언-->
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<form name="boardListForm" method="post" action="/boardList.do">
	</form>
</body>
</html>








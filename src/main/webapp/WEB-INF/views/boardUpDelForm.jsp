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
	function goBoardListForm(){
		document.boardListForm.method="post";
		document.boardListForm.action="/boardList.do";
		document.boardListForm.submit();
	}

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 수정 버튼 클릭 시 호출되는  함수 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function checkBoardUpForm(){

			var formObj = $("[name='boardUpDelForm']");
			
			//*******************************************
			// 게시판 입력 양식의 유효성 체크하기
			//*******************************************
			// 등록,입력할때 암호는 친절해야하지만 그 외의 로그인할때, 수정할때 암호는 불친절 해야한다  
			//if( !checkPwd(formObj.find("[name='pwd']"))  ) { return; }
			/*
			if( !checkSubject(formObj.find("[name='subject']"))  ) { return; }
			if( !checkEmail(formObj.find("[name='email']"))  )     { return; }
			
			if(checkVal(formObj.find("[name='pwd']")
						,"암호입력바람"
							,new RegExp(/[^ ]+/)
						//해당 유효성체크 패턴식은 공백이 아닌 문자가 앞이든, 중간이든, 마지막이든 즉 하나이상만 있으면 내버려두겠다..
						// 공백이 아닌 문자가 하나이상있으면
			)==false){return;}
			
			if( !checkContent(formObj.find("[name='content']"))  ) { return; }
			if( !checkWriter(formObj.find("[name='writer']"))  )   { return; }
			*/
			//*******************************************
			// [수정 여부]를 물어보기
			//*******************************************
			if(formObj.find("[name='isdel']").is(":checked")){
				
				if(confirm("사진을 삭제하시겠습니까??")==false) { return; }
				
			}
			
			
			if(confirm("정말 수정하시겠습니까?")==false) { return; }
				//confirm은 내장함수다
				
			// WAS로 "/boardUpProc.do" URL주소로 접속하고 얻은
			// 수정 실행결과 정보가 담긴 [JSON객체]를 받고 
			// 이 [JSON객체]에 저장된 데이터에 따라 실행구문을 달리 실행하기
			
			ajax(
					// WAS로 접속할 주소 설정
					"/boardUpProc.do"		
					// WAS로 접속하는 방법 설정
					,"post"
					// 입력양식을 끌어안은 form태그관리 Jquery객체 메위주
					,formObj
					// WAS와 통신이 성공했을때 호출되는, 응답메세지로 보낼 익명함수
					// 매개변수로 JSON 객체가 들어올거임
					// JSON안에 수정, 실행결과 정보가 담겨있다.
					,function(responseJson){
						// WAS가 응답해준 JSON에서 
						// 경고문구 꺼내서 변수 errorMsg에 저장하기
						// 수정된 행의 개수를 꺼내서 변수 boardUpCnt에 저장하기
						var errorMsg = responseJson["errorMsg"];
						var boardUpCnt = responseJson["boardUpCnt"];
						
						
						// 경고문구가 저장된 errorMsg가 비어있지 않으면,
						// 경고문구를 보이고 함수 중단
						if(errorMsg!=""){
							alert(errorMsg); return;
						}
						
						// 수정된 행의 개수가 1이면
						if(boardUpCnt==1){
							alert("수정이 성공됐습니다.");
							// name=boardListForm 을 가진 form태그에 있는
							// action 속성값에 있는 URL주소로 WAS에 접속하기
							// 이때 접속방식은 form태그에 있는 method의 속성값을 따른다
							// 즉 게시물 목록화면으로 이동
							document.boardListForm.submit();
						}
						// 수정된 행의개수가 없다면 0이라면,(수정할 게시물이 삭제된 상황이면)
						else if(boardUpCnt==0){
							alert("삭제된 게시물입니다");
						}
						// 수정된 행의 개수가 -1이면(즉 암호가 틀린상황이면)
						else if(boardUpCnt==-1){
							alert("암호가 틀립니다. 재입력 바랍니다.");
						}
						//수정된 행의 개수가 -11이면 즉 업로드된 파일의 크기가 너무 큰거면
						else if(boardUpCnt==-11){
							alert("파일의 크기는 1000kb 이하여야 합니다.");
						}
						//수정된 행의 개수가 -12이면 즉 업로드된 파일의 확장자가 이미지파일이 아니면
						else if(boardUpCnt==-12){
							alert("파일의 확장자는 jpg, png, gif 파일만 업로드 가능합니다.");
						}
						//수정된 행의 개수가 -21이면 즉 유효성 체크시 에러가 발생했으면
						else if(boardRegCnt==-21){
							alert(errorMsg);
						}
						
						else{
							alert("수정실패 관리자에게 문의바람.");
						}
					}
			);
	}
			//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
			// 삭제 버튼 클릭 시 호출되는  함수 선언
			//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
			function checkBoardDelForm(){
				var formObj=$("[name='boardUpDelForm']")
				
				if( !checkVal(
						formObj.find("[name='pwd']")
						,"암호입력바랍니다"
						,/[^ ]+/)
						){return;}
				//삭제여부 물어보기
				if(confirm("정말 삭제하시겠습니까?")==false){return;}
				
				// WAS로 "/boardDelProc.do" URL주소로 접속하고
				// 삭제 실행 행의 개수를 받고
				// 삭제 실행행의 개수따라 실행구문을 달리 실행하기
				ajax(
						//WAS로 접속할 주소 설정
					"/boardDelProc.do"	
					// WAS로 접속할 방법
					,"post"
					// 입력양식을 끌어안고있는 form태그관리 JQuery객체 메위주
					,formObj
					//WAS와 통신이 성공했을때 호출되는 익명함수 설정
					// 현재 익명함수의 매개변수로 삭제실행 행의 개수가 들어온다
					,function(boardDelCnt){
						// 매개변수 boardDelCnt가 1이 저장돼 있으면
						// 즉 삭제가 성공했으면
						if(boardDelCnt==1){
							alert("삭제성공");
							document.boardListForm.submit();
							
							//매개변수 boardDelCnt에 2가 저장돼 있으면
							// 즉 자식글이 있어 제목,컨텐츠만 비우기가 성공한다면
						}else if(boardDelCnt==2){
							alert("답글이 있어 제목과 컨텐츠만 비움");
							document.boardListForm.submit();
							
							// 매개변수 boardDelCnt 에 0 이 저장돼 있으면
							// 즉 삭제된 게시물이면
						}else if(boardDelCnt==0){
							alert("이미 삭제된 게시글입니다.")
							
								// 매개변수 boardDelCnt 에 -1 이 저장돼 있으면
							// 즉 삭제된 게시물이면
						}else if(boardDelCnt==-1){
							alert("암호가 틀립니다 재입력 바랍니다.")
							formObj.find("[name='pwd']").val("");
							formObj.find("[name='pwd']").focus();
						}
						else{alert("관리자에게 문의바랍니다.")}
							
					}
				);
				
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
	<!--<c:if test="${!empty requestScope.boardDTO}">-->
	<form name="boardUpDelForm">
		<table class=tableB > 
				<caption> [수정/삭제]</caption>
				<tr>
					<th>이 름</th>
					<td  width="300px">
					<!-------------------------------------------------------->
					<input type="text" name="writer" class="writer" size="10" maxlength="15" 
									value="${requestScope.boardDTO.writer}">
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>제 목</th>
					<td>
					<!--------------------------------------------------------> 
					<input type="text" name="subject" class="subject" size="40" maxlength="30" 
							value="${requestScope.boardDTO.subject}">
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
					<!-------------------------------------------------------->
					<input type="text" name="email" class="email" size="40" maxlength="50" 
							value="${requestScope.boardDTO.email}">
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>내 용</th>
					<td>
					<!-------------------------------------------------------->
					<textarea name="content"  class="content" rows="13" cols="40"  
						maxlength="500" >${requestScope.boardDTO.content}</textarea>
					<!-------------------------------------------------------->
					</td>
				</tr>
				
				<tr>
					<th>비밀번호</th>
					<td>
					<!-------------------------------------------------------->
					<input type="password" name="pwd" class="pwd"  size="8"  maxlength="4">
					<!-------------------------------------------------------->
					</td>
				</tr>
				<tr>
					<th>이미지</th>
					<td>
					<!--
						 img 태그는 입력양식태그가 아닌 이미지의 파라미터명과 파라미터값을 갖고온다 
						 이미지를 출력하기 위한 태그임.
						입력양식태그는 input 태그와 select 태그와 textarea태그밖에 없다
																									-->
					<c:if test="${!empty requestScope.boardDTO.img_name}">
					<img src="/img/${requestScope.boardDTO.img_name}" height="200px">
					<input type="checkbox" name="isdel" value="del">이미지 삭제
					<input type="hidden" name="img_name" value="${requestScope.boardDTO.img_name}">
					</c:if>
					<input type="file" name="img" >
					</td>
				</tr>
		</table>
			<!-------------------------------------------------------->	
			<input type="hidden" name="b_no" value="${requestScope.boardDTO.b_no}">
			<!-------------------------------------------------------->	
			<div style="height:5px;"></div>
			<!-------------------------------------------------------->	
			<input type="button" value="수정" onClick="checkBoardUpForm();">    
			<input type="button" value="삭제" onClick="checkBoardDelForm();">  
			<span style="cursor:pointer" onClick="goBoardListForm();">[목록보기]</span>
			<!-------------------------------------------------------->	
	</form>
	<!--</c:if>-->
	
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!-- WAS에 "/boardList.do"  URL 주소로 접속하기 위한 form 태그 선언-->
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<form name="boardListForm" method="post" action="/boardList.do">
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
	<!--<c:if test="${empty requestScope.boardDTO}">
		<script>
		alert("삭제된 게시물입니다.")
		goBoardListForm();
		</script>
	</c:if>-->
	
		
</body>
</html>









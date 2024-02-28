<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<!--JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!--
	현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고,이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다 라고 설정함 
	현재 이 JSP 페이지를 저장할때는 UTF-8 방식으로 인코딩 한다 
	모든 JSP 페이지 상단에 무조건 아래 설정이 들어간다. 
	-->
<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- JSP기술의 한 종류인 Include Directive 를 이용하여 common.jsp 파일내의 소스를 삽입하기 -->
<%@include file="/WEB-INF/views/common.jsp" %>

<!DOCTYPE html>
<html>
<head>
<title>게시판 검색</title>
<meta charset="UTF-8">

<script>
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// body 태그 안의 모든 내용을 읽어들인 이후 init 함수() 호출하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	$(function(){ init(); });

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// body 태그 안의 모든 내용을 읽어들인 이후 실행할 자스 코딩 설정하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function init(){

	}

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// [페이지 번호]를 클릭하면 호출되는 함수 pageNoClick 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function pageNoClick(
		clickPageNo    // 클릭한 번호가 들어 오는 매개변수
	){
		//---------------------------------------------
		// 변수 formObj 선언하고 
		// name='boardSearchForm' 를 가진 form 태그 관리 JQuery 객체를 저장하기
		//---------------------------------------------
		var formObj = $("[name='boardSearchForm']");
		//---------------------------------------------
		// name='selectPageNo' 를 가진 태그의 value 값에 
		// 매개변수로 들어오는 [클릭한 페이지 번호]를 저장하기
		// 즉 <input type="hidden" name="selectPageNo" value="1"> 태그에
		// value 값에 [클릭한 페이지 번호]를 저장하기
		//---------------------------------------------
		formObj.find("[name='selectPageNo']").val(clickPageNo);
		//---------------------------------------------
		// search 함수 호출하기
		//---------------------------------------------
		search();
	}

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 헤더를 클릭하면 호출되는 함수 선언하기 
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function sortThClick(
		ascDesc    // 정렬의미 문자가 들어오는 매개변수
	){
			//-----------------------------------------------
			// name='sort' 가진 태그에 정렬의미 문자 저장하기
			//-----------------------------------------------
		 	$("[name='boardSearchForm'] [name='sort']").val(ascDesc);
			//---------------------------------------------
			// search 함수 호출하기
			//---------------------------------------------
			search();
	}
	
	
	
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 게시판 검색 하는 함수 search() 선언.
	// [검색] 버튼 클릭 시 호출되는 함수이다.
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function search(){
		//*******************************************************
		// WAS 와 비동기 방식으로 "/boardList.do" URL 주소로 접속하고 얻은 
		// HTML 문서에서 필요한 데이터를 캐치해서 현 화면에 반영하기
		//*******************************************************
		ajax(
				//------------------------------------------
				// WAS 에 접속할 떄 사용할 URL 주소 지정
				//------------------------------------------
				"/boardList.do"
				//------------------------------------------
				// WAS 에 전송할 파라미터값 을 보내는 방법 지정
				//------------------------------------------
				,"post"
				//------------------------------------------
				// 파라미터값을 내포한 form 태그 관리하는  JQuery 객체 메위주
				//------------------------------------------
				,$("[name='boardSearchForm']")
				//----------------------------------------------------------
				// WAS 와 통신한 후 WAS의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정.
				// 익명 함수의 매개변수에는 WAS 가 보내온 HTML 문자열이 들어온다
				//----------------------------------------------------------
				,function(responseHtml){
					//---------------------------------------------
					// 매개변수로 들어오는 HTML 문자열을 관리하는 JQuery 객체 생성하여 변수 obj에 저장하기
					//---------------------------------------------
					var obj = $(responseHtml);
					//---------------------------------------------
					// JQuery 객체가 관리하는 HTML 태그 문자열에서 
					//	class='searchResultCnt' 가진 태그 내부 문자열을 얻어 변수 searchResultCnt 에 저장하기
					//---------------------------------------------
					var searchResultCnt = obj.find(".searchResultCnt").html();
					//---------------------------------------------
					// JQuery 객체가 관리하는 HTML 태그 문자열에서 
					//	class='searchResult' 가진 태그 내부 문자열을 얻어 변수 searchResult 에 저장하기
					//---------------------------------------------
					var searchResult = obj.find(".searchResult").html();
					//---------------------------------------------
					// JQuery 객체가 관리하는 HTML 태그 문자열에서 
					//	class='pageNos' 가진 태그 내부 문자열을 얻어 변수 pageNos 에 저장하기
					//---------------------------------------------
					var pageNos = obj.find(".pageNos").html();

					//---------------------------------------------
					// class='searchResultCnt' 를 가진 태그 내부에
					// JQuery 객체가 관리하는 태그 문자열에서 class='searchResultCnt' 가진 태그 내부 데이터를 덮어쓰기
					//--------------------------------------------- 
					$(".searchResultCnt").html( searchResultCnt );
					//---------------------------------------------
					// class='searchResult' 를 가진 태그 내부에
					// JQuery 객체가 관리하는 태그 문자열에서 class='searchResult' 가진 태그 내부 데이터를 덮어쓰기
					//---------------------------------------------
					$(".searchResult").html( searchResult );
					//---------------------------------------------
					// class='pageNos' 를 가진 태그 내부에
					// JQuery 객체가 관리하는 태그 문자열에서 class='pageNos' 가진 태그 내부 데이터를 덮어쓰기
					//---------------------------------------------
					$(".pageNos").html( pageNos );
				}

		);
	}

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// [게시판 상세 화면]으로 이동하는 함수 goBoardDetailForm() 선언.
	// 게시판 행을 버튼 클릭 시 호출되는 함수이다.
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	function goBoardDetailForm(b_no){
		
		$("[name='boardDetailForm'] [name='b_no']").val(b_no);
		
		document.boardDetailForm.submit();
	}
	
	
</script>


</head>
<body>
	<center>

	<form name="boardSearchForm">
		
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<!--- 게시판 검색 조건 관련 태그 선언하기.-->
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<table border="1" cellpadding="5" cellspacing="0" 
							style="border-collapse:collapse" align="center" >
			<tr>
				<th>키워드</td>
				<td>
					<!------------------------------------------->
					<!--키워드 검색 단어를 입력하는 [입력 양식 태그] 선언하기 -->
					<!------------------------------------------->
					<input type="text" name="keyword1">
					<select name="orand">
						<option value="or">or
						<option value="and">and
					</select>
					<input type="text" name="keyword2">
					<!------------------------------------------->
					<input type="checkbox" name="date" value="+0">오늘
					<input type="checkbox" name="date" value="-1">어제
				</td>
			</tr>
		</table>
		<div style="height:10px"></div>
		<!------------------------------------------->
		<!-- 클릭한 즉 선택한 페이지 번호가 저장되는 [입력 양식 태그] 선언하기 -->
		<!------------------------------------------->
		<input type="hidden" name="selectPageNo" value="1">
		
		<!------------------------------------------->
		<!-- 정렬 기준 데이터가 저장되는 [입력 양식 태그] 선언하기 -->
		<!------------------------------------------->
		<input type="hidden" name="sort">
		
		<!------------------------------------------->
		<input type="button" value="검색" onclick="search()">&nbsp;&nbsp;&nbsp;
		<span style="cursor:pointer" onclick="location.replace('/boardRegForm.do')">[새글쓰기]</span>

		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<!--- 게시판 검색 결과물 개수, 게시판 총 개수 출력하기.-->
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<span class="searchResultCnt">
			[검색 개수] : ${requestScope.boardMap.boardListCnt} / ${requestScope.boardMap.boardListCntAll}
						<!--
							--------------------------------------
							${requestScope.boardMap.boardListCnt}
							--------------------------------------
								HttpServletRequest 객체에 "boardMap"라는 키값으로 저장된 놈이 HashMap객체 인데
								이 HashMap객체안의  "boardListCnt" 라는 키갑으로 저장된 놈을 EL 문법으로 표현하기.
								<주의>MedelAndView 객체에 저장된 놈은 HttpServletRequest 객체에 저장된다.
							--------------------------------------
							${requestScope.boardMap.boardListCntAll}
							--------------------------------------
								HttpServletRequest 객체에 "boardMap"라는 키값으로 저장된 놈이 HashMap객체 인데
								이 HashMap객체안의  "boardListCntAll" 라는 키갑으로 저장된 놈을 EL 문법으로 표현하기.
								<주의>MedelAndView 객체에 저장된 놈은 HttpServletRequest 객체에 저장된다.
						-->
		</span>
		<!------------------------------------------->
		<!-- 보이는 행의 개수를 선택하는 [입력 양식 태그] 선언하기 -->
		<!------------------------------------------->
		<select name="rowCntPerPage" onChange="search()">
			<option value="10">10
			<option value="15">15
			<option value="20">20
		</select>행보기

			<div style="height:10px"></div>

		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<!--- 게시판 검색 결과물 출력하기  -->
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<div class="searchResult">
			 <table class="tableB" > 
			 <!--<table border="0" cellpadding="7" cellspacing="0" 
								style="border-collapse:collapse" align="center" >
				<tr bgColor="lightgray">-->
					<th>번호
					<th>제목
					<th>작성자
					<th>조회수
					
					<!--============================================================= -->
					<!-- 만약 HttpSession 객체에 키값 "mid" 에 'xyz' 가 저장되어 있지 않으면 -->
					<!-- 만약 'xyz' 가 아닌 아이디로 로그인 했으면 -->
					<!--============================================================= -->
					<c:if test="${sessionScope.mid!='xyz'}">
						<th>등록일
					</c:if>
					
					<!--============================================================= -->
					<!-- 만약 HttpSession 객체에 키값 "mid" 에 'xyz' 가 저장되어 있으면 -->
					<!-- 만약 'xyz' 라는 아이디로 로그인 했으면 -->
					<!--============================================================= -->
					<c:if test="${sessionScope.mid=='xyz'}">
						<!--============================================================= -->
						<!-- 만약 파명 "sort" 의 파값이 비었으면 -->
						<!-- 즉 정렬 의지가 없으면               -->
						<!--============================================================= -->
						<c:if test="${param.sort!='reg_date asc' and param.sort!='reg_date desc'}">
							<th onClick="sortThClick('reg_date asc')"  style="cursor:pointer;">등록일
						</c:if>
						
						<!--============================================================= -->
						<!-- 만약 파명 "sort" 의 파값이 'reg_date asc' 면 -->
						<!-- 즉 정렬 의지가 'reg_date asc' 면             -->
						<!--============================================================= -->
						<c:if test="${param.sort=='reg_date asc'}">
							<th onClick="sortThClick('reg_date desc')"  style="cursor:pointer;">등록일▲
						</c:if>
						
						<!--============================================================= -->
						<!-- 만약 파명 "sort" 의 파값이 'reg_date desc' 면 -->
						<!-- 즉 정렬 의지가 'reg_date desc' 면             -->
						<!--============================================================= -->
						<c:if test="${param.sort=='reg_date desc'}">
							<th onclick="sortThClick('')" style="cursor:pointer">등록일▼
						</c:if>
					</c:if>
					
					
				</tr>
				
				
				

				<!--*******************************************************************
				----------------------------------------------------------------------------------
				[EL 문법]과 [커스텀 태그]의 일종인 [반복문 C코어 태그]를 사용하여  게시판 검색 결과물 출력하기.
				----------------------------------------------------------------------------------
					HttpServletRequest 객체에   "boardMap" 라는 키값으로 저장된  놈을 꺼내면 HashMap 객체인데  
					이 HashMap 객체에서 "boardList" 키값으로 저장된 놈을 꺼내면 List<Map> 객체인데             
					이 ArrayList  객체 안의 저장된 HashMap 객체를 하나씩 꺼내서 지역변수   board 담고       
					반복문 안으로 들어간다.                                                                  
					반복문 안에서는 지역변수 board 에 저장된 HashMap 객체에 저장된 데이터를 꺼낼 때   
					달러{지역변수명.키값} 로 꺼낸다.                                                      
					varStatus="vs" 에서
						vs 는 지역변수이고 이 안에 반복문 관리 객체가 저장되어있다.   
						반복문 관리 객체의 index 라는 속성변수에는 0 부터 시작하는 인덱스번호가 저장되어있다.
						index 라는 속성변수 안의 데이터를 표현할 때는 달러{vs.index} 로 표현한다.
				----------------------------------------------------------------------------------
				 [반복문 C코어 태그] 내부의 속성 설명
				----------------------------------------------------------------------------------
						------------------------------------------------------------
						items="달러{EL로표현되는ArrayList객체또는배열객체}"
						------------------------------------------------------------
						var="반복문안에서사용되는지역변수"      
								-> EL로 표현되는 ArrayList객체 또는 배열객체 안의 i번째 데이터가 저장된다.
						------------------------------------------------------------                                                    
						varStatus="vs" 
								vs 는 지역변수이고 이 안에 반복문 관리 객체가 저장되어있다.   
								반복문 관리 객체의 index 라는 속성변수에는 0 부터 시작하는 인덱스번호가 저장되어있다.
								index 라는 속성변수 안의 데이터를 표현할 때는 달러{vs.index} 로 표현한다.
								반복문 관리 객체의 count 라는 속성변수에는 1 부터 시작하는 순서번호가 저장되어있다.
								count 라는 속성변수 안의 데이터를 표현할 때는 달러{vs.count} 로 표현한다.
						------------------------------------------------------------
						begin="시작번호"  end="끝번호"
						------------------------------------------------------------ 
							시작번호 부터 시작해서 1씩 증가 하면서 끝번호 까지 반복문이 돈다.
							이 번호들은 var 오른쪽 "반복문안에서사용되는지역변수"  에 저장되어
							반복문 안으로 들어간다.
						------------------------------------------------------------ 
				*******************************************************************-->
				<c:forEach var="board" items="${requestScope.boardMap.boardList}" varStatus="vs">
				
					<tr  style="cursor:pointer;" onClick="goBoardDetailForm(${board.b_no})">
					
						<td>${requestScope.boardMap.begin_serialNo_desc-vs.index}</td> 
						
						<td>
							<!------------------------------------------------------------->
							<!-- 들여쓰기 레벨 단계 번호 만큼  &nbsp;&nbsp; 를 표현하기-->
							<!------------------------------------------------------------->
							<c:forEach var="no" begin="1" end="${board.print_level}">
								&nbsp;&nbsp;
							</c:forEach>
							<!------------------------------------------------------------->
							<!-- 만약에 들여쓰기 레벨 단계 번호가 0보다 크면 문자 ㄴ 표현하기-->
							<!------------------------------------------------------------->
							<c:if test="${board.print_level>0}">
								ㄴ
							</c:if>
							${board.subject}
						</td> 
						
						
						<td>${board.writer}</td> 
						<td>${board.readcount}</td> 
						<td>${board.reg_date}</td> 
					</tr>
				</c:forEach>
				

				<%
					/*
					Map<String,Object> boardMap = (Map<String,Object>)request.getAttribute("boardMap");
					List<Map<String,String>>  boardList = (List<Map<String,String>>)boardMap.get("boardList");
					int begin_serialNo_desc = (int)boardMap.get("begin_serialNo_desc");

					for(int i=0 ; i<boardList.size() ; i++){
						Map<String,String> board = boardList.get(i);
						out.print("<tr> ");
						out.print("<td>"+(begin_serialNo_desc-i)+"</td> ");
						out.print("<td>"+board.get("subject")+"</td> ");
						out.print("<td>"+board.get("writer")+"</td> ");
						out.print("<td>"+board.get("readcount")+"</td> ");
						out.print("<td>"+board.get("reg_date")+"</td> ");
					}
					*/
				%>


			</table> 
		</div>
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
			<div style="height:10px"></div>



		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<!--- 게시판 페이징 번호 출력하기.      -->
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
		<div class="pageNos"> 
			<!--------------------------------------------->
			<!-- [처음] [이전] 출력하기 -->
			<!--------------------------------------------->
			<span style="cursor:pointer" onClick="pageNoClick(1)">[처음]</span>
			<span style="cursor:pointer" onClick="pageNoClick(${requestScope.boardMap.selectPageNo}-1)">[이전]</span>&nbsp;&nbsp;
		
			<!--------------------------------------------->
			<!--  [반복문 C코어 태그]를 사용하여 페이지 번호 출력하기 -->
			<!--------------------------------------------->
			<c:forEach var="pageNo" begin="${requestScope.boardMap.begin_pageNo}" end="${requestScope.boardMap.end_pageNo}">
				<!--------------------------------------------->
				<!--  만약에 [선택한 페이지 번호]와 [화면에 출력할 페이지 번호]가 같으면  -->
				<!--------------------------------------------->
				<c:if test="${requestScope.boardMap.selectPageNo==pageNo}">
					${pageNo}
				</c:if>
				<!--------------------------------------------->
				<!--  만약에 [선택한 페이지 번호]와 [화면에 출력할 페이지 번호]가 다르면  -->
				<!--------------------------------------------->
				<c:if test="${requestScope.boardMap.selectPageNo!=pageNo}">
					<span style="cursor:pointer" onClick="pageNoClick(${pageNo})">[${pageNo}]</span>
				</c:if>
			</c:forEach>&nbsp;&nbsp;

			<!--------------------------------------------->
			<!-- [다음] [마지막] 출력하기 -->
			<!--------------------------------------------->
			<span style="cursor:pointer" onClick="pageNoClick(${requestScope.boardMap.selectPageNo}+1)">[다음]</span>
			<span style="cursor:pointer" onClick="pageNoClick(${requestScope.boardMap.last_pageNo})">[마지막]</span>
		</div>
		<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->



	</form><br>



	</center>

	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<!--- 게시판 [상세보기 화면] 이동을 위한 [form 태그] 선언하기.-->
	<!--mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm-->
	<form name="boardDetailForm" action="/boardDetailForm.do" post="post">
		
		<input type="hidden" name="b_no">
	</form>
	

</body>
</html>


	













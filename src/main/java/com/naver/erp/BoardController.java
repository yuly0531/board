package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable.BindRestriction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
// BoardController 클래스 선언하기
//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
	/*
	스프링에서 관용적으로   Controller 라는 단어가 들어간 클래스는  
	URL 주소 접속 시 대응해서 호출되는 메소드를 소유하고 있다.
	클래스 이름 앞에는 @Controller  라는 어노테이션이 붙는다.
	클래스 내부의 URL 주소 접속 시 호출되는 메소드명 앞에는 
	 	@RequestMapping  라는 어노테이션이 붙는다.
	  ------------------------------------------------------------
	  @Controller 어노테이션이 붙은 클래스 특징
	  ------------------------------------------------------------
	  
		(1) 스프링프레임워크가 알아서 객체를 생성하고 관리한다.
		(2) URL 주소 접속 시 대응해서 호출되는 메소드를 소유하고 있다.
		
	  ------------------------------------------------------------
	*/
@Controller
public class BoardController {

	@Autowired
	private BoardDAO boardDAO;  

	@Autowired
	private BoardService boardService; 
	
	
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// URL 주소 /boardList.do 로 접근하면 호출되는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@RequestMapping( value="/boardList.do")
	public ModelAndView boardList( 
			//----------------------------------------------------------------
			// 웹브라우저가 가져온 파값을 저장할 DTO 객체를 매개변수로 선언
			//----------------------------------------------------------------
				// [파라미터명]과 [BoardSearchDTO 객체]의 [멤버변수명]이 같으면
				// setter 메소드가 작동되어 [파라미터값]이 [멤버변수명]에 저장된다.
				// 만약 [파라미터명] 중에 [멤버변수명]과 같은 놈이 없다면
				// setter 메소드가 작동되어 않아서 [멤버변수명]에 저장되지 않는다.
			BoardSearchDTO 	boardSearchDTO
			//--------------------------------------
			// HttpSession 객체의 메위주를 저장하는 매개변수 session 선언하기
			//--------------------------------------
			,HttpSession session
	){

		//--------------------------------------------------------------
		// HttpSession 객체에서 "mid" 라는 키값으로 저장된 데이터 꺼내서 변수 mid 에 저장하기
		// 즉 로그인이 성공했을 때 HttpSession 객체에 저장한  로그인 아이디를 꺼내서  변수 mid 에 저장하기.
		//--------------------------------------------------------------
//		String mid = (String)session.getAttribute("mid");
	
//		//--------------------------------------------------------------
//		// 만약 mid 변수 안에 null 이 있으면
//		// 즉 로그인이 실패 했을 때
//		//--------------------------------------------------------------
//		if( mid==null ) {
//			//-------------------------------------------------------------
//			// [ModelAndView 객체] 생성하기
//			// [ModelAndView 객체]의 setViewName 메소드 호출하여  
//			//					[호출할 JSP 페이지명]을 문자로 저장하기
//			// [ModelAndView 객체] 리턴하기
//			//-------------------------------------------------------------
//			ModelAndView mav = new ModelAndView();
//			mav.setViewName( "loginFail.jsp" );
//			return  mav;
//		}
		
		
		//-------------------------------------------------------------
		// getBoardSearchResultMap 메소드를 호출하여
			// 게시판 검색 결과 관련 각종 정보들 저장한 Map<String,Object> 객체 구하고
			// 이 객체의 메위주를 변수 boardMap 에 저장하기
			// 이 객체안의 모든 정보는 boardList.jsp 페이지에서 사용될 예정이다.
		//-------------------------------------------------------------
		Map<String,Object> boardMap = getBoardSearchResultMap( boardSearchDTO );
		//-------------------------------------------------------------
		// [ModelAndView 객체] 생성하기
		// [ModelAndView 객체]의 setViewName 메소드 호출하여  
		//					[호출할 JSP 페이지명]을 문자로 저장하기
		// [ModelAndView 객체]에 변수 boardMap 에 저장된 객체를 저장하기
		//-------------------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName( "boardList.jsp" );
		mav.addObject(   "boardMap" , boardMap     );
		//-------------------------------------------------------------
		// [ModelAndView 객체] 리턴하기
		//-------------------------------------------------------------
		return  mav;
	}
	


	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 게시판 검색 관련 각종 정보들 구해서, Map<String,Object> 객체에 담아 리턴하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	public Map<String,Object> getBoardSearchResultMap( 
			// 웹브라우저가 가져온 파값을 저장할 DTO 객체를 매개변수로 선언
			BoardSearchDTO 	boardSearchDTO
		
	){
		//-------------------------------------------------------------
		// 검색 화면에 필요한 각종 데이터를 저장할 Map<String,Object> 객체  생성.
		//-------------------------------------------------------------
		// 게시판 검색 결과 목록(=n행m열)을 저장할 List<Map<String,String>> 객체 메위주 저장할 변수 boardList 선언하기.
		// 게시판 검색 결과 개수 저장할 변수 boardListCnt 선언하기.
		// 게시판 모든 개수 저장할 변수 boardListCntAll 선언하기.
		// 페이징 처리 관련 데이터 저장한 Map<String,Integer> 객체 저장할 변수 pagingMap 선언하기.
		//-------------------------------------------------------------
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		List<Map<String,String>> boardList;
		int boardListCnt;
		int boardListCntAll;
		Map<String,Integer> pagingMap;
		//-------------------------------------------------------------
		// 게시판 모든 개수 구해서 변수 boardListCntAll 에 저장하기
		// 게시판 검색 결과 개수 구해서 변수 boardListCnt 에 저장하기
		// 페이징 처리 관련 데이터가 저장된 Map<String,Integer> 객체를 구해서 변수 pagingMap에 저장하기
		//-------------------------------------------------------------
		boardListCntAll =  this.boardDAO.getBoardListCntAll(   );
		boardListCnt =  this.boardDAO.getBoardListCnt(  boardSearchDTO );
		pagingMap = Util.getPagingMap(
				boardSearchDTO.getSelectPageNo()       // 선택한 페이지 번호
				, boardSearchDTO.getRowCntPerPage()    // 한 화면에 보여지는 행의 개수
				, boardListCnt                          // 검색된 게시판의 총개수
		);
		//-------------------------------------------------------------
		// BoardSearchDTO 객체에 
		//		선택 페이지 번호, 페이지 당 보일 행 개수
		//		테이블에서 검색 시 사용할 시작 행 번호
		//		테이블에서 검색 시 사용할 끝 행 번호
		// 저장하기.
		// BoardSearchDTO 객체에 저장된 데이터들은 SQL 구문에서 사용할 데이터이다.
		// SQL 구문은 mapper_board.xml 에 들어 있다.
		//-------------------------------------------------------------
		boardSearchDTO.setSelectPageNo(  (int)pagingMap.get("selectPageNo")  );  // 보정된 선택 페이지 번호 저장하기
		boardSearchDTO.setRowCntPerPage( (int)pagingMap.get("rowCntPerPage") );  // 페이지 당 보일 행 개수 저장하기
		boardSearchDTO.setBegin_rowNo(   (int)pagingMap.get("begin_rowNo")   );  // 테이블에서 검색 시 시작 행 번호 저장하기
		boardSearchDTO.setEnd_rowNo(     (int)pagingMap.get("end_rowNo")     );  // 테이블에서 검색 시 끝 행 번호 저장하기
		//-------------------------------------------------------------
		// 게시판 검색 결과 목록 저장한 List<Map<String,String>> 객체 메위주 boardList 에 저장하기
		// BoardDAO 인터페이스를 구현한 객체의 getBoardList 메소드를 호출하여
		// 게시판 검색 결과물은 저장한 List<Map<String,String>> 객체를 얻어
		// 변수 boardList 에 저장하기
		//-------------------------------------------------------------
		boardList       =  this.boardDAO.getBoardList( boardSearchDTO  );

		//-------------------------------------------------------------
		// Map<String,Object> 객체에 위에서 구한 모든 데이터를 저장하기
		//-------------------------------------------------------------
		resultMap.put(  "boardList"       , boardList        );
		resultMap.put(  "boardListCnt"    , boardListCnt     );
		resultMap.put(  "boardListCntAll" , boardListCntAll  );
		resultMap.put(  "boardSearchDTO"  , boardSearchDTO   );
		//--------------
		resultMap.put(  "begin_pageNo"          , pagingMap.get("begin_pageNo")        );
		resultMap.put(  "end_pageNo"            , pagingMap.get("end_pageNo")          );
		resultMap.put(  "selectPageNo"          , pagingMap.get("selectPageNo")        );
		resultMap.put(  "last_pageNo"           , pagingMap.get("last_pageNo")         );
		resultMap.put(  "begin_serialNo_asc"    , pagingMap.get("begin_serialNo_asc")  );
		resultMap.put(  "begin_serialNo_desc"   , pagingMap.get("begin_serialNo_desc") );

		//--------------------------------------------------
		// [Map<String,Object> 객체] 리턴하기
		//--------------------------------------------------
		
		return  resultMap;
	}


	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// /boardDetailForm.do 접속 시 호출되는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@RequestMapping( value="/boardDetailForm.do" )
	public ModelAndView boardDetailForm( 	
			//---------------------------------------
			// "b_no" 라는 파라미터명의 파라미터값이 저장되는 매개변수 b_no 선언
			// 상세보기 할 게시판 고유 번호가 들어오는 매개변수 선언
			//---------------------------------------
			@RequestParam(value="b_no") int b_no	
	){
		//*******************************************************
		// 상세보기 화면에서 필요한 [1개의 게시판 글]을 가져오기
		//*******************************************************	
		BoardDTO boardDTO = this.boardService.getBoard(b_no,true);  
		
			
		
		//*******************************************************
		// ModelAndView 객체 생성하여 DB 연동 결과물(=1개의 게시판 글), 호출할 JSP 페이지 저장하기
		//*******************************************************	
		ModelAndView mav = new ModelAndView( );
		
		//*******************************************************
		// 만약에 변수 boardDTO가 null이 아니면 즉 게시글이 존재하면
		//*******************************************************
		if(boardDTO!=null) {
			// ModelAndView 객체에 호출할 JSP페이지 "boardDetailForm.jsp"로 지정하기
			// ModelAndView 객체에 DB연동 결과물 저장하기
			mav.setViewName( "boardDetailForm.jsp");
			mav.addObject("boardDTO", boardDTO);
		}
		else {
			//*******************************************************
			// 만약에 변수 boardDTO가 null이면 즉 게시글이 삭제됐으면 호출할 JSP페이지 "boardEmpty.jsp"로 지정하기
			//*******************************************************
			mav.setViewName("boardEmpty.jsp");
		}
		
		
		//*******************************************************
		// [ModelAndView 객체] 리턴하기
		//*******************************************************
		return mav;
		
	}
	
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// /boardUpDelForm.do 접속 시 호출되는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@RequestMapping(value="/boardUpDelForm.do" )
	public ModelAndView boardUpDelForm( 
			//---------------------------------------
			// "b_no" 라는 파라미터명의 파라미터값이 저장되는 매개변수 b_no 선언
			// 상세보기 할 게시판 고유 번호가 들어오는 매개변수 선언
			//---------------------------------------
			@RequestParam(value="b_no") int b_no
			
	) {

		//*******************************************************
		// 수정/삭제 화면에서 필요한 [1개의 게시판 글]을 기져오기
		//*******************************************************	
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);  
		
		//*******************************************************
		// [ModelAndView 객체] 생성하기
		// [ModelAndView 객체]에 [호출 JSP 페이지명]을 저장하기
		//*******************************************************
		ModelAndView mav = new ModelAndView( );
		
		//*******************************************************
		// 만약에 변수 boardDTO가 null이 아니면 즉 게시글이 존재하면 호출할 JSP페이지 "boardUpDelForm.jsp"로 지정하기
		//*******************************************************
		if(boardDTO!=null) {
			mav.setViewName("boardUpDelForm.jsp");	
			mav.addObject("boardDTO", boardDTO);
		}
		else {
			//*******************************************************
			// 만약에 변수 boardDTO가 null이면 즉 게시글이 삭제됐으면 호출할 JSP페이지 "boardEmpty.jsp"로 지정하기
			//*******************************************************
			mav.setViewName("boardEmpty.jsp");
		}

		//*******************************************************
		// [ModelAndView 객체] 리턴하기
		//*******************************************************
		return mav;
	}
	
	
	@RequestMapping(value="/boardUpProc.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	
	// JSON은 자바스크립트의 개념이다. 허나 자바에선 JSON을 사용할수 없으니 JSON과 유사한 HashMap객체를 사용한다.
	@ResponseBody
	public Map<String,String> boardUpProc(
			
			// 파라미터값이 저장된 [BoardDTO 객체]가 들어올 매개변수 선언
			//------------------------------------------------------------------
			// [파라미터명]과 [BoardDTO 객체]의 멤버변수명이 같으면
			// setter 메소드가 작동되어 [파라미터값]이 [멤버변수]에 저장된다
			@Valid
			BoardDTO boardDTO
			,BindingResult bindingResult
			){
		
		
		// 게시판 수정 결과물을 저장할 HashMap객체 생성하기
		// 게시판 수정행의 개수를 저장할 변수 boardUpCnt 선언
		// 경고메세지 저장변수 errorMsg선언 
		Map<String,String> responseMap = new HashMap<String,String>();
		int boardUpCnt=0;
		String errorMsg="";
		try {
			
				errorMsg=Util.getErrorMsgFromBindingResult(bindingResult);
			
			
			//만약 errorMsg가 에러문자가 있으면 즉 유효성 체크시 에러가 발생 했으면
			if(errorMsg!=null&&errorMsg.length()>0) {
				boardUpCnt=-21;
			}else {	
			// [BoardServiceImpl객체]의 updateBoard메소드 호출로
			// 게시판글 수정하고 [수정적용행의 개수] 얻기
			boardUpCnt=this.boardService.updateBoard(boardDTO);
			}
		}
		catch(Exception ex) {
			errorMsg="게시판 수정이 실패했습니다. 관리자에게 문의 바랍니다.";
			boardUpCnt=-9;
			
		}
		//HashMap 객체에 경고메세지 게시판 수정행의 개수 저장하기
		responseMap.put("errorMsg", errorMsg);
		responseMap.put("boardUpCnt", boardUpCnt+"");
		return responseMap;
		
	}

	@RequestMapping(value="/boardDelProc.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	
	// JSON은 자바스크립트의 개념이다. 허나 자바에선 JSON을 사용할수 없으니 JSON과 유사한 HashMap객체를 사용한다.
	@ResponseBody
	public int boardDelProc(
			
			// 파라미터값이 저장된 [BoardDTO 객체]가 들어올 매개변수 선언
			//------------------------------------------------------------------
			// [파라미터명]과 [BoardDTO 객체]의 멤버변수명이 같으면
			// setter 메소드가 작동되어 [파라미터값]이 [멤버변수]에 저장된다
			BoardDTO boardDTO){
		
		// 게시판 수정 결과물을 저장할 HashMap객체 생성하기
		// 게시판 수정행의 개수를 저장할 변수 boardUpCnt 선언
		int deleteBoardcnt=this.boardService.deleteBoard(boardDTO);
		return deleteBoardcnt;
	}
	
	@RequestMapping( value="/boardRegForm.do" )
	public ModelAndView boardNewForm(){
				
				
				//*******************************************************
				// [ModelAndView 객체] 생성하기
				// [ModelAndView 객체]에 [호출 JSP 페이지명]을 저장하기
				//*******************************************************
				ModelAndView mav = new ModelAndView( );
				mav.setViewName("boardRegForm.jsp");
		

				//*******************************************************
				// [ModelAndView 객체] 리턴하기
				//*******************************************************
				return mav;
		
	}
	@RequestMapping(value="/boardRegProc.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	
	// JSON은 자바스크립트의 개념이다. 허나 자바에선 JSON을 사용할수 없으니 JSON과 유사한 HashMap객체를 사용한다.
	@ResponseBody
	public Map<String,String> boardRegProc(
			
			// 파라미터값이 저장된 [BoardDTO 객체]가 들어올 매개변수 선언
			//------------------------------------------------------------------
			// [파라미터명]과 [BoardDTO 객체]의 멤버변수명이 같으면
			// setter 메소드가 작동되어 [파라미터값]이 [멤버변수]에 저장된다
			
			
			//ㅡㅡㅡㅡㅡㅡㅡㅡ
			// @Valid ?
			//ㅡㅡㅡㅡㅡㅡㅡㅡ
			// 매개변수로 들어오는 DTO객체 앞에 붙어 아래 기능을 수행한다.
			// DTO객체안의 멤버변수에 붙은 [유효성 체크 어노테이션]으로 유효성 체크하고
			// 유효성 체크시 발생하는 경고메시지 문자는 [BindingResult 객체]에 저장한다
			
			@Valid
			
			BoardDTO boardDTO
			
			// 동료매개변수 중 @Valid가 붙은 DTO객체에서
			// 유효성 체크시 발생하는 에러메세지를 관리하는
			// Error객체들을 관리하는 BindingResult 객체가 들어오는
			// 매개변수 bindingResult 선언
			,BindingResult bindingResult
			){
		
		//게시판 입력결과물을 저장할 HashMap객체를 생성한다.
		//경고메세지 저장 변수도 선언
		// 게시판 입력행의 개수를 저장할 변수 boardRegCnt선언하기
		
		Map<String,String> responseMap = new HashMap<String,String>();
		int boardRegCnt=0;
		String errorMsg="";
		
		
		//예외가 발생할 가능성이 있는 코드는 try{} 영역에 삽입하기
		try {
			
			// Util 클래스의 getErrorMsgFromBindingResult 메소드를 호출하여
			// 유효성 체크시 발생한 에러 메세지를 얻어서 변수 errorMsg에 저장하기
			errorMsg=Util.getErrorMsgFromBindingResult(bindingResult);
			
			
			//만약 errorMsg가 에러문자가 있으면 즉 유효성 체크시 에러가 발생 했으면
			if(errorMsg!=null&&errorMsg.length()>0) {
				boardRegCnt=-21;
				
				//만약에 변수 errorMsg에 에러문자가 없으면
				// 즉 유효성 체크시 에러발생 안 했으면
			}else {
				
				//boardServiceImpl객체의 insertBoard 메소드호출로
				//게시판글 입력하고 입력적용행의 개수 얻기
				boardRegCnt=this.boardService.insertBoard(boardDTO);
				
			}
			
		}
		// try영역에서 예외 발생시 실행할 catch구문 선언
		// catch구문의 매개변수에는 예외처리 관리객체가 들어온다.
		catch(Exception ex) {
			errorMsg="게시판 입력이 실패했습니다. 관리자에게 문의 바랍니다.";
			boardRegCnt=-1;
			
		}
		//HashMap 객체에 경고메세지 게시판 수정행의 개수 저장하기
		responseMap.put("errorMsg", errorMsg);
		responseMap.put("boardRegCnt", boardRegCnt+"");
		return responseMap;
		
	}


	
	
//	
//	현재 이 컨트롤러 클래스 내의 @RequestMapping 이 붙은 메소드 호출시
//	예외 발생하면 호출되는 메소드 선언
	@ExceptionHandler(Exception.class)
	public String handleException(
			HttpServletRequest request
			) {
		//호출할 "error.jsp" 페이지를 문자열로 리턴
		return "error.jsp";
	}
	
	
	/*
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// URL 주소  /boardListProc.do 로 접근하면 호출되는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
		// 메소드 앞에 
		// @RequestMapping(~,~,produces="application/json;charset=UTF-8") 하고
		// @ResponseBody  가 붙으면 리턴하는 데이터가 웹브라우저에게 전송된다.
	@RequestMapping( 
			value="/boardListProc.do" 
			,method=RequestMethod.POST
			,produces="application/json;charset=UTF-8"
	)
	@ResponseBody
	public Map<String,Object> boardListProc( 
			BoardSearchDTO 	boardSearchDTO
	){

		System.out.println( boardSearchDTO.getKeyword1() );
		Map<String,Object> map = new HashMap<String,Object>();
		

		List<Map<String,String>> boardList =  this.boardDAO.getBoardList2( boardSearchDTO  );
		map.put(  "boardList" , boardList );
		map.put(  "boardListCnt" , boardList.size());
		
		

		//--------------------------------------------------
		// [ModelAndView 객체] 리턴하기
		//--------------------------------------------------
		return  map;
	}
 	*/
}








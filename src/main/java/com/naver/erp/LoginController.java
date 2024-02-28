package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
// LoginController 클래스 선언하기
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
	*/
@Controller
public class LoginController {

	

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// LoginDAO 인터페이스 구현한 클래스를 찾아서 객체화 해서  
	// 멤버변수 loginDAO 에 객체의 메위주를 저장.
	// 즉 현재 LoginDAOImp 객체의 메위주가 저장되었음
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@Autowired
	private LoginDAO loginDAO;   
	
	
	
	
	
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//웹브라우저가 /loginForm.do   URL 주소로 접근하면 호출되는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
		//--------------------------------------------------
		// URL 주소에 대응하여 호출되려면 메소드 앞에 
		// @RequestMapping( value="포트번호이후주소")  이라는 어노테이션이 붙어야한다.
		//--------------------------------------------------
		// <주의> @RequestMapping 이 붙은 메소드의 이름은 개발자 맘대로이다. 
		//        될수 있는 대로 URL 주소의 의도를 살리는 메소드 이름을 주는 것이 좋다.
		//--------------------------------------------------
	@RequestMapping( value="/loginForm.do")
	public ModelAndView loginForm(
			//--------------------------------------
			// HttpSession 객체의 메위주를 저장하는 매개변수 session 선언하기
			//--------------------------------------
			HttpSession session
	){

		//--------------------------------------------------
		// HttpSession 객체에 키값 "mid" 에 붙어 저장된 데이터 지우기
		// 즉 예전에 로그인 성공했을 때 HttpSession 객체에 저장한 로그인 아이디를 삭제한다.
		// 즉 로그아웃을 시킨것이다.
		//--------------------------------------------------
		session.removeAttribute("mid");
		
		//--------------------------------------------------
		// [ModelAndView 객체] 생성하기
		//--------------------------------------------------
		ModelAndView mav = new ModelAndView();
		
		
		
		//--------------------------------------------------
		// [ModelAndView 객체]의 setViewName 메소드 호출하여  
		// [호출할 JSP 페이지명]을 문자로 저장하기
		//--------------------------------------------------
		// [호출할 JSP 페이지명] 앞에 붙는 위치 경로는 
		// application.properties   에서 
		// spring.mvc.view.prefix=/WEB-INF/views/     에 설정한다.
		//---------------------------------------------------------------
		// [호출할 JSP 페이지명] 뒤에 붙는 확장자는 
		// application.properties   에서 
		// spring.mvc.view.suffix=.jsp     에 설정한다. 근데 이거는 여기서 생략했다.
		//---------------------------------------------------------------
		// <참고>기본적으로 저장 경로에서 webapp 폴더까지는 자동으로 찾아간다
		//---------------------------------------------------------------
		// 만일 properties에서 경로 설정을 해두지 않았다면 밑의 코딩은
		// mav.setViewName( "/WEB-INF/views/loginForm.jsp" );
		//---------------------------------------------------------------
		// 이렇게 되어야 한다
		mav.setViewName( "loginForm.jsp" );
		
		
		//--------------------------------------------------
		// [ModelAndView 객체] 리턴하기
		// [ModelAndView 객체를 리턴한 후 스프링은 ModelAndView 객체에 저장된 JSP페이지를 찾아 호출한다]
		//--------------------------------------------------
		return mav;
	}

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// URL 주소  /loginProc.do 로 접근하면 호출되는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
		// 메소드 앞에 
		// @RequestMapping(~,~,produces="application/json;charset=UTF-8") 하고
		// @ResponseBody  가 붙으면 리턴하는 데이터가 웹브라우저에게 전송된다.
	@RequestMapping( 
			value="/loginProc.do" 
			// 이 URL주소로 들어오면
			,method=RequestMethod.POST
			//POST방식을 허락
			,produces="application/json;charset=UTF-8"
			//JSP페이지로 로그인하는게 아니라 존재개수를 리턴
			//ModelAndView 를 사용하면 JSP를 리턴하지만 얘는 존재개수를 리턴하는거임
	)
	@ResponseBody
	public int loginProc( 
			//--------------------------------------
			// "mid" 라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 mid 에 저장하고 들어온다.
			// 즉 웹브라우저에서 입력한 유저의 아이디가 매개변수로 저장돼 들어온다
			// "mid"라는 키값에 해당하는 value값을 갖고온다
			// 즉 <input type="text" name="mid"> 태그에 입력된 값이 들어온다
			//--------------------------------------
			@RequestParam( value="mid" ) String mid
			//--------------------------------------
			// "pwd" 라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 pwd 에 저장하고 들어온다.
			// 즉 웹브라우저에서 입력한 유저의 비밀번호가 매개변수로 저장돼 들어온다
			// "pwd"라는 키값에 해당하는 value값을 갖고온다
			// 즉 <input type="text" name="pwd"> 태그에 입력된 값이 들어온다
			//--------------------------------------
			,@RequestParam( value="pwd" ) String pwd
			//--------------------------------------
			// "autoLogin" 라는 파라미터명에 해당하는 파라미터값을 
			//		꺼내서 매개변수 autoLogin 에 저장하고 들어온다.
			//		즉 웹브라우저의 유저가 체크한 데이터가 아래 매개변수로 들어온다
			//		즉 <input type="checkbox" name="autoLogin" value="yes" class="autoLogin">태그가 체크될때 yes값이 들어온다
			//---------------------------------------------------------------------------------------
			//	required=false 필수로 요구되지 않도록 설정해준것이다. 즉  null값을 허용한다는 말이다.
			//		만약 이 코딩을 하지않으면 체크박스는 반드시 체크해야된다. 
			//   파라미터값으로 받아오는 값에 null값을 허용하려면 해당코딩은 필수다.
			//---------------------------------------------------------------------------------------
			//	required=true	코딩을 안하면 자동으로 입력되는 값이 이거다.
			//	요구하다		반드시 입력을 해야된다.
			,@RequestParam( value="autoLogin", required=false  ) String autoLogin
					/*
					--------------------------------------------------------
					매개변수 왼쪽에 붙는 @RequestParam 어노테이션 형식
					--------------------------------------------------------
						-----------------------------------------------------------------
						@RequestParam( value="xxx1", required=false또는true  ) String xxx2
						-----------------------------------------------------------------
							"xxx1" 이라는 파라미터명에 대응하는 파라미터값을 
													매개변수 xxx2 에 저장하고 들어와!
							required=false
								파라미터값이  없더라도 괜찮아.. 없으면 디폴트 값을 매개변수에 넣어줄께!
							required=true 
								파라미터값이  없으면 안돼...필수로 들어와..안들어오면 에러발생!
								required=true 도 없고 required=false 없으면 required=true 가 있다고 봐야함!
					*/
			//--------------------------------------
			// HttpSession 객체의 메위주를 저장하는 매개변수 session 선언하기
			//--------------------------------------
			,HttpSession session
			//--------------------------------------
			// [HtppServletResponse 객체]가 들어올 매개변수 선언
			//--------------------------------------
			,HttpServletResponse response
	){
		//----------------------------------------------------
		// HashMap 객체 생성하고 객체의 메위주를 map 에 저장하기
		// 이 HashMap 객체에는 매개변수로 들어온 아이디와 암호를 저장할 예정이다.
		// HashMap 객체에 매개변수로 들어온 [로그인 아이디] 저장하기
		// HashMap 객체에 매개변수로 들어온 [암호] 저장하기
		//----------------------------------------------------
		Map<String,String> map = new HashMap<String,String>();
		map.put("mid", mid);
		map.put("pwd", pwd);

		//----------------------------------------------------
		// [로그인 아이디]와 [암호]의 DB 존재 개수를 저장할 변수 loginIdCnt 선언하고
		// LoginDAOImpl 객체의 getLoginIdCnt 메소드를 호출하여 얻은 데이터 저장하기
		// 즉 로그인 [아이디]와 [암호]의 DB 존재 개수를 구해서 변수 loginIdCnt 에 저장한다.
		//----------------------------------------------------
		int loginIdCnt = this.loginDAO.getLoginIdCnt(map);

		//----------------------------------------------------
		// 만약 loginIdCnt 변수안의 데이터가 1이면, 즉 아이디와 암호가 DB 에 있으면
		//----------------------------------------------------
		if(loginIdCnt==1){
			//-------------------------------
			// HttpSession 객체에 로그인 아이디 저장하기
			//-------------------------------
				//-------------------------------
				// HttpSession 객체에 로그인 아이디를 저장하면 재 접속했을때 다시 꺼낼수 있다.
				// <참고>HttpSession 객체는 접속한 이후에도 제거되지 않고 지정된 기간동안 살아있는 객체이다.
				// <참고>HttpServletRequest,HttpServletResponse 객체는 접속때 생성되고 응답이후 삭제되는 객체이다
				//-------------------------------
			session.setAttribute( "mid", mid );

			//--------------------------------------
			// 매개변수 autoLogin 에 null 이 저장되어 있으면(=[아이디,암호 자동 입력]의사 없을 경우 )
			//--------------------------------------
			if(autoLogin==null){
				// Cookie 객체를 생성하고 쿠키명-쿠키값을 ["mid"-null]로 하기
				Cookie cookie1 = new Cookie("mid",null);
				// Cookie 객체에 저장된 쿠키의 수명은 0으로 하기
				cookie1.setMaxAge(0);

				// Cookie 객체를 생성하고 쿠키명-쿠키값을 ["pwd"-null]로 하기
				Cookie cookie2 = new Cookie("pwd",null);
				// Cookie 객체의 수명은 0으로 하기
				cookie2.setMaxAge(0);
				
				// Cookie 객체가 소유한 쿠키를 응답메시지에 저장하기. 
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}
			//--------------------------------------
			// 매개변수 autoLogin 에 "yes" 이 저장되어 있으면(=[아이디,암호 자동 입력]의사 있을 경우 )
			//--------------------------------------
			else {
				//--------------------------------------------------
				// 클라이언트가 보낸 아이디,암호를 [응답 메시지]에 쿠키명-쿠키값으로 저장하기
				//--------------------------------------------------
				// Cookie 객체를 생성하고 쿠키명-쿠키값을 ["mid"-"입력아이디"]로 하기
				Cookie cookie1 = new Cookie("mid",mid);
				// Cookie 객체에 저장된 쿠키의 수명은 60*60*24으로 하기
				cookie1.setMaxAge(60*60*24);

				// Cookie 객체를 생성하고 쿠키명-쿠키값을 ["pwd"-"입력암호"]로 하기
				Cookie cookie2 = new Cookie("pwd",pwd);
				// Cookie 객체에 저장된 쿠키의 수명은 60*60*24으로 하기
				cookie2.setMaxAge(60*60*24);

				// Cookie 객체가 소유한 쿠키를 응답메시지에 저장하기. 
				// 응답메시지에 저장되는 쿠키는 결국 웹브라우저 즉 클라이언트쪽에 저장된다.
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}
		}




		//----------------------------------------------------
		// [로그인 아이디]와 [암호]의 DB 존재 개수를 리턴하기
		//----------------------------------------------------
		return loginIdCnt;
	}
	
}


//http://naver.me/FuEXWkZz





// http://localhost:8081/loginForm.do
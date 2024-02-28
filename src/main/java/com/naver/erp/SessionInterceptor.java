package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;


//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
// URL접속시 @Controller가 붙은 클래스의
// @RequestMapping이 붙은 메소드 호출전 또는 후에
// 실행될 메소드를 소유할 SessionInterceptor클래스 선언

//------------------------------------------------------------------
// @Controller가 붙은 클래스의
// @RequestMapping이 붙은 메소드 호출전 또는 후에
// 실행될 클래스의 메소드가 될 자격 요건

//<1> 스프링이 제공하는 HandlerInterceptor 인터페이스를 구현한다

//<2> @RequestMapping 이 붙은 메소드를 호출하기 전에 실행할 코딩은
//		 HandlerInterceptor 인터페이스의
// 			preHandle 메소드를 재정의 하면서 삽입한다

//<3> @RequestMapping이 붙은 메소드 호출후에 실행할 코딩은
//		HandlerInterceptor 인터페이스의 postHandle 메소드를 재정의 하면서 삽입한다
public class SessionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(
	HttpServletRequest request
	,HttpServletResponse response
	,Object handler
			)
	throws Exception{
		// HttpSession 객체 메위주 얻기 이 객체가 로그인 정보를 가지고 있다
		// httpservletRequest 객체의 getSession 메소드 호출하면
		// HttpSession 객체의 메위주를 얻을수 있다.
		 HttpSession session = request.getSession();
		
		// HttpSession객체에서
		 // 키값이 mid로 저장된 데이터 꺼내기
		 // 즉 로그인 정보 꺼내기
		 String mid=(String)session.getAttribute("mid");
		 
		 // 만약 mid변수안에 null이 저장돼 있으면
		 // 즉 만약 로그인에 성공한 적이 없으면
		 if(mid == null) {
			 // 웹 브라우저가 loginForm.do로 재접속하라고 설정하기
			 response.sendRedirect("/loginForm.do");
			 // false 값을 리턴하기
			 // false 값을 리턴하면 @RequestMapping이 붙은 메소드는 호출되지 않는다
			 
			 return false;
		 }
		// 만약 mid변수안에 null이 저장돼 있으면
				 // 즉 만약 로그인에 성공한 적이 있으면
		 else {
			// true 값을 리턴하기
			 // true 값을 리턴하면 @RequestMapping이 붙은 메소드가 호출된다
			 return true;
		 }
		
	}
	
}




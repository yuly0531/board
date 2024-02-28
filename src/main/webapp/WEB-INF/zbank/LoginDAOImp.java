package com.naver.erp;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
// [LoginDAOImpl 클래스] 선언.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 관용적으로 DAO 단어가 들어간 클래스는 직접 DB 연동을 하는 클래스이다.
	// 직접 DB 연동을 하는 클래스 앞에는 @Repository 라는 어노테이션을 붙인다.
	// 직접 DB 연동을 하는 클래스 보통 인테페이스를 구현하여 만든다.
	// 여기서는 LoginDAO 인테페이스를 구현하여 선언하였다.

@Repository
public class LoginDAOImp implements LoginDAO{

	//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
	// SqlSessionTemplate 객체를 생성해 멤버변수 sqlSession 에 저장
	//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
		// @Autowired 어노테이션을 붙이면 속성변수 자료형에 맞는 
		// SqlSessionTemplate 객체를 생성한 후 
		// 객체의 메위주를 속성변수 저장한다.
		//--------------------------------
		// SqlSessionTemplate 객체의 기능
		//--------------------------------
			// DB 연동 시 사용하는 객체이다.
			// xml 파일에 있는 SQL 구문을 읽어서 DB 에 SQL 명령을 내린 후
			// 그 결과값을 받아오는 객체이다. 그렇게 해주는 메소드가 존재한다.
	@Autowired
	private SqlSessionTemplate sqlSession;

	//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
	// 로그인 아이디와 암호 존재의 개수를 리턴하는 메소드 선언
	// 이 메소드는 현재 LoginController 객체 안의 메소드에서 호출하고 있다.
	//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
	@Override
	public int getLoginIdCnt( 
			// 로그인 아이디와 암호가 저장된 HashMap 객체가 들어오는 매개변수 선언
			Map<String,String> idPwd 
	) {
		//--------------------------------------------------
		// SqlSessionTemplate 객체의 [selectOne 메소드] 호출로
		// xml 파일에 있는 select 구문을 호출하여 
		// [로그인 아이디,암호 존재 개수]를 얻기
		//--------------------------------------------------
			//-----------------------
			// selectOne 메소드 특징
			//-----------------------
					// selec 구문을 DB 에 날리고  [1행m열] 의 결과를 리턴하는 메소드이다.
					// 만약 selectOne 메소드의 결과가  2행 이상이면 에러가 터진다.
					//-----------------------------------------------
				    // selectOne 메소드 실행 결과가 1행1열 일 경우
					//-----------------------------------------------
							// int 또는 String 또는 double 로 리턴된다.
					//-----------------------------------------------
				    // selectOne 메소드 실행 결과가 1행 2열 이상 경우
					//-----------------------------------------------
							//자바의 [HashMap 객체] 또는 [DTO 객체]에 저장해서 리턴된다.
		int loginIdCnt = this.sqlSession.selectOne(
				//-----------------------------------------------
				// 실행할 [SQL 구문의 위치]를 문자열로 지정하기.
				//-----------------------------------------------
				"com.naver.erp.LoginDAO.getLoginIdCnt" 
					// com.naver.erp.LoginDAO  => xml 파일안의 mapper 태그 안의 namespace 값을 의미한다.
					//                            com.naver.erp.LoginDAO 는 실제 존재하는 인터페이스의 경로이어야한다.
				    // getLoginIdCnt           => 그 mapper 태그 안의 <~ id="getLoginIdCnt"> 이 태그 내부의 SQL문을 말한다.
					//                            이  <~ id="getLoginIdCnt"> 에  getLoginIdCnt 는 인터페이스의 메소드 이름하고
					//                            일치해야한다.
				//-----------------------------------------------
				// 실행할 SQL구문에서 사용할 데이터 지정하기. 
				// 밑의 idPwd 변수에는 아이디와 암호가 저장된 HashMap 객체가 들어 있다.
				//-----------------------------------------------
				, idPwd
		);
		//-----------------------------------------------
		// [로그인 아이디,암호 존재 개수]를 리턴하기
		//-----------------------------------------------
		return loginIdCnt;
	}
}

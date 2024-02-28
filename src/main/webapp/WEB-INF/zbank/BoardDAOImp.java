package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BoardDAOImp implements BoardDAO{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<Map<String,String>> getBoardList( ){
		
		
		List<Map<String,String>> boardList = this.sqlSession.selectList(
				"com.naver.erp.BoardDAO.getBoardList"
		);
		//System.out.println( "---------------------" + boardList.size() );
		//System.out.println( "---------------------" + (String)(boardList.get(0).get("subject")) );
		return boardList;
		
	}

}









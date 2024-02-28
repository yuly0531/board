package com.naver.erp;

import java.util.Map;


//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//[BoardService 인터페이스] 선언.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm

public interface BoardService {
	
	//****************************************************
	// [1개 게시판 글] 리턴하는 메소드 선언
	//****************************************************
	BoardDTO getBoard( int b_no , boolean isBoardDetailForm );
	
	// 1개 게시판 수정 실행하고 수정 적용행의 개수를 리턴하는 메소드 선언
	int updateBoard(BoardDTO boardDTO) throws Exception;
	// 1개 게시판 삭제 실행하고 삭제적용행의 개수를 리턴하는 메소드선언
	int deleteBoard(BoardDTO boardDTO);
	// 1개의 글을 입력 실행하고 입력적용행의 개수를 리턴하는 메소드선언
	int insertBoard(BoardDTO boardDTO) throws Exception;
	
}

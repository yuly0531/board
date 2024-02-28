package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDAO {
	//--------------------------------------------
	// 게시판 총 개수 리턴하는 메소드 선언.
	//--------------------------------------------
	int  getBoardListCntAll(  );
	//--------------------------------------------
	// 게시판 검색 결과 개수 리턴하는 메소드 선언.
	//--------------------------------------------
	int  getBoardListCnt( BoardSearchDTO boardSearchDTO );
	//--------------------------------------------
	// 게시판 검색 결과(n행m열) 리턴하는 메소드 선언.
	//--------------------------------------------
	List<Map<String,String>> getBoardList( BoardSearchDTO boardSearchDTO );

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 조회수를 1 증가하고 적용된 행의 개수를 얻는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	int updateReadcount(int b_no);	

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// [1개의 게시판 정보]를 리턴하는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	BoardDTO getBoard(int b_no);
	
	//게시판의 존재개수를 리턴하는 메소드 선언
	int getBoardCnt(int b_no);
	
	// 수정/삭제할 게시판의 비밀번호 존재개수를 리턴하는 메소드 선언
	int getBoardPwdCnt(BoardDTO boardDTO);
	
	// 게시판 수정 명령후 수정적용행의 개수를 리턴하는 메소드 선언
	int updateBoard(BoardDTO boardDTO);
	
	// 게시판 삭제 명령후 삭제적용 행의 개수를 리턴하는 메소드 선언
	int deleteBoard(BoardDTO boardDTO);
	
	// 자식글(후손글)갯수를 리턴하는 메소드선언
	int getBoardChildrenCnt(BoardDTO boardDTO);
	
	// 삭제예정인 글의 제목과 내용을 비우는 메소드 선언
	// 자식이 있는글의 메인글은 삭제하지 말고
	// 제목과 내용을 "삭제글입니다"라고 수정한 후 수정적용행의 개수를 얻는 메소드다.
	int updateBoardEmpty(BoardDTO boardDTO);
	
	// 게시판 입력명령후 입력적용행의 개수를 리턴하는 메소드 선언
	int insertBoard(BoardDTO boardDTO);
	
	// 부모글의 출력순서번호를 증가시키는 메소드 선언
	int upPrintNo(BoardDTO boardDTO);
	
	
}

package com.naver.erp;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//[BoardService 인터페이스] 선언.
// @Service 과 @Transactional 가 붙은 클래스는
// 모든 DB연동에 트랜잭션이 걸린다. 
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
@Service
@Transactional
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO  boardDAO;

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//  [1개 게시판 글] 검색 해 리턴하는 메소드 선언
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@Override
	public BoardDTO getBoard(int b_no , boolean isBoardDetailForm ) {
		if( isBoardDetailForm ) {
			//------------------------------------------
			// [BoardDAO 인터페이스를 구현한 객체]의 updateReadcount 메소드를 호출하여
			// [조회수 증가]하고 수정한 행의 개수를 얻는다
			//------------------------------------------
			int updateCount = this.boardDAO.updateReadcount(b_no);
		}
		//------------------------------------------
		// [BoardDAO 인터페이스를 구현한 객체]의  getBoard 메소드를 호출하여
		// [1개 게시판 글]을 얻는다
		//------------------------------------------
		BoardDTO board = this.boardDAO.getBoard(b_no);
		//------------------------------------------
		// [1개 게시판 글]이 저장된 BoardDTO 객체 리턴하기
		//------------------------------------------
		return board;
	}
	
	// 1개 게시판 수정 실행하고 수정 적용행의 개수를 리턴하는 메소드 선언
	@Override
	public int updateBoard(BoardDTO boardDTO) throws Exception {
		
		
		
		//게시판 수정행의 개수 저장변수 boardUpCnt선언
		// BoardDTO 객체에 저장된 MultiPartFile 객체를 꺼내서 변수 img에 저장
		// MultiPartFile 객체가 업로드된 파일을 관리하고 있다면
		// 변수 isFile에 true 저장하고 아니면 false 저장하기
		// 업로드된 파일에 부여할 새 이름을 구해서 변수 newFileName에 저장하기
		int boardUpCnt=0;	
		MultipartFile multi = boardDTO.getImg();
		boolean isFile = multi!=null && multi.isEmpty()==false;
		String newFileName = Util.getNewFileName(multi);
		
		String isdel = boardDTO.getIsdel();
		String img_name = boardDTO.getImg_name();
		
		// 파일 삭제 명령어
		//	File file = new File(~+"예전파일명").delete();
		
		
		
		//업로드 파일의 크기와 확장자 검사하기
		// Util 클래스의 checkUploadFile메소드를 호출하여
		// 리턴되는 데이터가 -11이면 크기가 큰것,
		// 					 -12면 확장자가 맞지 않은것
		
		int checkUploadFile=Util.checkUploadFileForBoard(
			multi					//업로드되는 파일을 관리하는 객체
			);
		if(checkUploadFile<0) {
			return checkUploadFile;
		}
	
		// 수정할 게시판의 존재개수얻기
		// 만약 수정할 게시판의 개수가 0개면, (이미 삭제됐으면) 0 리턴하기
			int boardCnt= this.boardDAO.getBoardCnt(boardDTO.getB_no());
			if(boardCnt==0) {return 0;}
			
			
			
			// 암호 존재개수 얻기
			// 만약 암호의 존재개수가 0개면 (암호가 틀렸으면) -1 리턴하기
			int boardPwdCnt=this.boardDAO.getBoardPwdCnt(boardDTO);
			if(boardPwdCnt==0) {return -1;}
			
			
			
			// 업로드된 파일이 있다면
			if(isFile) {
				
				//boardDTO 객체안의 멤버변수 img_name에 새로운 파일명을 저장하기
				boardDTO.setImg_name(newFileName);
						
				
			}//-----------------------------------------------------------------------
			//기존파일이 있다는 조건하에 새로운파일을 선택하거나,
			//			아니면 새로운파일을 선택하지 않고 기존파일을 삭제만 하거나.
			//-----------------------------------------------------------------------
			// 업로드된 파일이 없다면
			else {
				// 변수 isdel이 null이 아니라면 삭제의도가 있다면
				if(isdel!=null) {
					//boardDTO객체의 멤버변수  img_name에 null저장하기
					boardDTO.setImg_name(null);
				}
				
			}
			
			
			// 수정실행하고 수정적용행의 개수 얻기
			boardUpCnt=this.boardDAO.updateBoard(boardDTO);
			
			
			//업로드된 파일이 있다면
			if(isFile) {
				
				// 새 파일을 만들고 이 파일을 관리하는 File 객체를 생성하고
				// file객체의 메위주를 변수 file에 저장하기
				File file = new File(Util.uploadDirForBoard()+newFileName);
						
				//	MultipartFile 객체의 transferTo 메소드 호출하여
				// MultipartFile 객체가 관리하는 업로드 파일을 위에서 만든 새파일에 덮어쓰기
				multi.transferTo(file);
				
			}
			
			// 만약 기존에 파일이 있으면서 기존파일이 길이가있는 문자가 있을때 
			if(img_name!=null && img_name.length()>0) {
				
				// 또 만약에 파일삭제에 체크를 했으면서 파일삭제변수안에 데이터가 있거나 또는 변수 isFile이 true가 들어있다면
				if(isdel!=null&&isdel.length()>0 || isFile) {
					
					// 기존파일 삭제
					File file = new File(Util.uploadDirForBoard()+img_name);
					file.delete();
				}
				
			}
			// 변수 boardUpCnt안의 데이터를 리턴
			
			return boardUpCnt;
		
	
//			
//			// 수정실행하고 수정적용행의 개수 리턴
//			return updateBoardCnt;
		
	}
	// 1개 게시판 삭제후 삭제적용행의 개수를 리턴하는 메소드 선언
	@Override
	public int deleteBoard(BoardDTO boardDTO) {
		int deleteBoardCnt=0;
		
		
		String img_name = boardDTO.getImg_name();
		
		// 삭제예정인 게시판의 존재개수를 얻어서 변수 boardCnt에 저장 
		int boardCnt= this.boardDAO.getBoardCnt(boardDTO.getB_no());
		if(boardCnt==0) {return 0;}
		
	// 암호 존재개수 얻기
	// 만약 암호의 존재개수가 0개면 (암호가 틀렸으면) -1 리턴하기
		int boardPwdCnt=this.boardDAO.getBoardPwdCnt(boardDTO);
		if(boardPwdCnt==0) {return -1;}
		
		//삭제예정인 게시글의 자식글 존재개수 얻어서 변수 boardChildrenCnt에 저장하기
		// 만약 자식글의 존재개수가 1개이상이면(자식글이 있으면) 2 리턴
		int boardChildrenCnt=this.boardDAO.getBoardChildrenCnt(boardDTO);
		if(boardChildrenCnt>0) {
			// 삭제 예정인 게시판의 글 제목 내용을 비우기
			deleteBoardCnt=this.boardDAO.updateBoardEmpty(boardDTO);
			
			// 변수 img_name에 해당하는 파일 삭제하기
			Util.delboardfile(img_name);
			
			p.p(img_name);
			// 2 리턴하기
			return 2;
		}
		//=======================================================================
		//게시판 글을 삭제하고 삭제행의 개수를 변수 deleteBoardCnt에 저장하기
		// 여기까지 왔다면, 
		//	삭제되지도 않았고, 자식도 없다 암호도 맞아 떨어진다.
		deleteBoardCnt=this.boardDAO.deleteBoard(boardDTO);
		
		Util.delboardfile(img_name);
			
			
			
		
		// 변수 deleteBoardCnt안의 데이터를 리턴하기
		return deleteBoardCnt;
	}
	
	
	
	
	
	// 1개 게시판 입력후 입력적용행의 개수를 리턴하는 메소드 선언
	@Override
	public int insertBoard(BoardDTO boardDTO) throws Exception {
		
		//게시판 입력행의 개수 저장변수 boardRegCnt선언
		// 출력순서번호 1증가 적용행 개수저장변수 선언
		// BoardDTO 객체에 저장된 multipartFile 객체를 꺼내서 변수 img에 저장하기
		// multipartFile 객체가 업로드된 파일을 관리하고 있다면
		// 변수 isFile에 true 저장하고 아니면 false 저장
		
		// 업로드된 파일의 새 이름을 구해서 변수 newFileName에 저장하기
		int boardRegCnt=0;
		int upPrintNoCnt=0;
		
		
		
		MultipartFile multi = boardDTO.getImg();
		boolean isFile = multi!=null && multi.isEmpty()==false;
		String newFileName = Util.getNewFileName(multi);
		
		
		
				//업로드 파일의 크기와 확장자 검사하기
				// Util 클래스의 checkUploadFile메소드를 호출하여
				// 리턴되는 데이터가 -11이면 크기가 큰것,
				// 					 -12면 확장자가 맞지 않은것
				
				int checkUploadFile=Util.checkUploadFileForBoard(
					multi					//업로드되는 파일을 관리하는 객체
					);
				if(checkUploadFile<0) {
					
					return checkUploadFile;
				}
		
		
		
		// 만약 BoardDTO객체안의 부모글의 글번호가 있으면 댓글쓰기 이므로
		// 부모글 이후의 게시판글에 대해 출력순서번호를 1 증가시키기
		
		if(boardDTO.getMom_b_no()>0) {
			upPrintNoCnt=this.boardDAO.upPrintNo(boardDTO);
		}
//		
//		boardDTO 객체안에 img_name이라는 멤버변수에 새로운 파일명 저장하기
		boardDTO.setImg_name(newFileName);
		
		//boardDAOImpl객체의 insertBoard 메소드를 호출하여 
		//게시판 글 입력후 입력적용행의 개수 얻기
		boardRegCnt=this.boardDAO.insertBoard(boardDTO);
		
		//업로드 파일을 WAS내부에 저장하기
		
		// 만약에 매개변수로 들어온 boardDTO객체의 멤버변수 Img가 null이 아니면
		// 즉 파일업로드가 되었다면 
		if(isFile) {
			
			// 새 파일을 만들고 이 파일을 관리하는 File 객체를 생성하고
			// file객체의 메위주를 변수 file에 저장하기
			File file = new File(Util.uploadDirForBoard()+newFileName);
					
			//	MultipartFile 객체의 transferTo 메소드 호출하여
			// MultipartFile 객체가 관리하는 업로드 파일을 위에서 만든 새파일에 덮어쓰기
			
			multi.transferTo(file);
		}
		
		return boardRegCnt;
	}
	
}










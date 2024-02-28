package com.naver.erp;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
// BoardDTO 클래스 선언
// 1개의 게시판 글 정보가 저장되는 클래스이다.
//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
public class BoardDTO {
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 파명 "b_no" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "subject" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "content" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "writer" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "email" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "pwd" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "readcount" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "reg_date" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "group_no" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "print_no" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "print_level" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	// 파명 "mom_b_no" 에 대응하는 파값이 저장되는 멤버변수 선언하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	private int b_no;
	
	// 멤버변수 subject에 저장된 파라미터값의
	// 유효성 체크실행 어노테이션 선언하기
	// 유효성체크 실행결과 메세지는 BindingResult 객체가 관리한다
	@NotEmpty(message = "제목은 필수 입력입니다.")
	@NotNull(message = "제목은 필수 입력입니다.")
	@NotBlank(message = "제목을 입력해 주세요.")
	@Size(min = 2,max = 30, message = "제목은 2~30자 까지 입력가능합니다")
	@Pattern(regexp = "^[^><]{2,30}$", message = "제목은 2~30자 까지 입력가능하며 >또는 < 기호가 들어갈수 없습니다. 재입력요망")
	private String subject;
	
	
		// 멤버변수 content에 저장된 파라미터값의
		// 유효성 체크실행 어노테이션 선언하기
		// 유효성체크 실행결과 메세지는 BindingResult 객체가 관리한다
	@NotEmpty(message = "내용은 필수 입력입니다.")
	@NotNull(message = "내용은 필수 입력입니다.")
	@NotBlank(message = "내용을 입력해 주세요.")
	@Pattern(regexp = "^[^><]{2,500}$", message = "내용은 2~500자 까지 입력가능합니다.")
	private String content;
	
	@NotEmpty(message = "작성자항목은 필수 입력입니다.")
	@NotNull(message = "작성자항목은 필수 입력입니다.")
	@NotBlank(message = "작성자를 입력해주세요.")
	@Pattern(regexp = "^[가-힣a-zA-Z]{2,15}$", message = "작성자는 2~15자 한글,영소대문자로만 구성돼야 합니다..")
	private String writer;
	
	@NotEmpty(message = "이메일은 필수 입력입니다.")
	@NotNull(message = "이메일은 필수 입력입니다.")
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email()
	private String email;
	
	@NotEmpty(message = "암호는 필수 입력입니다.")
	@NotNull(message = "암호는 필수 입력입니다.")
	@NotBlank(message = "암호를 입력해주세요.")
	@Pattern(regexp = "^[0-9a-z]{4}$", message = "암호는 영소문 또는 숫자로 구성된 4자리 입력해야 합니다.")
	private String pwd;
	
	
	//		어노테이션이란?
	
	// 클래스앞에, 메소드앞에, 멤버변수앞에 붙어서 
	// 특정 기능을 부여하는 표기법을 어노테이션이라 한다.
	

	private int readcount;
	private String reg_date;
	private int group_no;
	private int print_no;
	private int print_level;
	

	private int mom_b_no;
	
	// 파일 업로드 관련 멤버변수들 선언
	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	// 파일 업로드된 파일을 관리하는 MultipartFile객체 저장 매개변수 img선언
	// 업로드된 파일의 새이름을 저장할 img_name 멤버변수 선언
	private String img_name;
	private MultipartFile img;
	
	private String isdel;

	
	public int getB_no() {
		return b_no;
	}

	public void setB_no(int b_no) {
		this.b_no = b_no;
	}

	public String getSubject() {
		
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public int getGroup_no() {
		return group_no;
	}

	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}

	public int getPrint_no() {
		return print_no;
	}

	public void setPrint_no(int print_no) {
		this.print_no = print_no;
	}

	public int getPrint_level() {
		return print_level;
	}

	public void setPrint_level(int print_level) {
		this.print_level = print_level;
	}

	public int getMom_b_no() {
		return mom_b_no;
	}

	public void setMom_b_no(int mom_b_no) {
		this.mom_b_no = mom_b_no;
	}

	public String getImg_name() {
		return img_name;
	}

	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	
	
}

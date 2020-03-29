package com.yosep.user.repository;

import java.util.List;

import com.yosep.user.entity.User;

public interface UserDAO {
	// 01. 회원 목록
	public List<User> userList();
	
	// 02. 회원 생성
	public int createUser(User user);
	
	// 03. 회원 상세 보기
	public User viewUser(String userId);
	
	// 04. 회원 탈퇴
	public void deleteUser(String userId);
	
	// 05. 회원 수정
	public void updateUser(User user);
	
	// 06. 비밀번호 일치여부
	public boolean checkPassword(String userId, String userPassword);
	
	// 07. 회원 로그인 체크
	public boolean loginCheck(User user);
	
	// 08. 아이디 중복여부 체크
	public User sameIdCheck(String userId);
	
	// 09. 대시보드용 회원정보 가져오기
}

package com.yosep.user.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yosep.user.entity.User;

@Repository
public class UserDAOImpl implements UserDAO{
	static private final String NAMESPACE = "com.yosep.user.mapper.userMapper";
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<User> userList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createUser(User user) {
		// TODO Auto-generated method stub
		return sqlSession.insert(NAMESPACE + ".createUser",user);
	}

	@Override
	public User viewUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkPassword(String userId, String userPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loginCheck(User user) {
		// TODO Auto-generated method stub
		String userId = sqlSession.selectOne(NAMESPACE + ".loginCheck",user);
		
		return (userId == null) ? false : true;
	}

	@Override
	public User sameIdCheck(String userId) {
		// TODO Auto-generated method stub
		User user = null;
		user = sqlSession.selectOne(NAMESPACE + ".checkSameId",userId);
		
		return user;
	}

}

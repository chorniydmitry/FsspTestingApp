package ru.fssprus.r82.service;

import java.util.List;

import ru.fssprus.r82.dao.UserDao;
import ru.fssprus.r82.dao.impl.UserDatabaseDao;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.utils.AppConstants;

/**
 * @author Chernyj Dmitry
 *
 */
public class UserService {
	// TODO: autowired
	private UserDao userDao = new UserDatabaseDao();

	public List<User> getBySurname(int startPos, int endPos, String surname) {
		return userDao.getBySurname(startPos, endPos, surname);
		
	}
	
	public List<User> getBySurname(String surname) {
		return userDao.getBySurname(-1, -1, surname);
		
	}
	
	public List<User> getByNameSurnameSecondName(int startPos, int endPos, String name, String surname, String secondName) {
		return userDao.getByNameSurnameSecondName(startPos, endPos, name, surname, secondName);
	}
	
	public List<User> getByNameSurnameSecondName(String name, String surname, String secondName) {
		return userDao.getByNameSurnameSecondName(AppConstants.NO_SQL_LIMIT_START_SPECIFIED, 
				AppConstants.NO_SQL_LIMIT_START_SPECIFIED, name, surname, secondName);
	}
	
	public User getByNameSurnameSecondNameSingle(String name, String surname, String secondName) {
		return userDao.getByNameSurnameSecondNameSingle(AppConstants.NO_SQL_LIMIT_START_SPECIFIED, 
				AppConstants.NO_SQL_LIMIT_START_SPECIFIED, name, surname, secondName);
	}
	
	public void add(User user) {
		userDao.add(user);
	}

}

package ru.fssprus.r82.service;

import ru.fssprus.r82.dao.PasswordDao;
import ru.fssprus.r82.dao.impl.PasswordDatabaseDao;

public class PasswordService {
	//TODO: autowired
	private PasswordDao passwordDao = new PasswordDatabaseDao();
	
	public boolean checkPassword(String sectionName, String passwordToCheck) {
		return passwordDao.checkBySection(sectionName, passwordToCheck);
	}
	
	public void update(String sectionName, String newPassword) {
		passwordDao.update(sectionName, newPassword);
	}
	
}

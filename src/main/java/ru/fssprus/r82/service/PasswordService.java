package ru.fssprus.r82.service;

import java.util.List;

import ru.fssprus.r82.dao.PasswordDao;
import ru.fssprus.r82.dao.impl.PasswordDatabaseDao;
import ru.fssprus.r82.entity.Password;

public class PasswordService {
	//TODO: autowired
	private PasswordDao passwordDao = new PasswordDatabaseDao();
	
	public int passwordIsSet(String sectionName) {
		return passwordDao.getCountByName(sectionName);
	}
	
	public boolean checkPassword(String sectionName, String passwordToCheck) {
		return passwordDao.checkBySection(sectionName, passwordToCheck);
	}
	
	public void update(String sectionName, String newPassword) {
		passwordDao.update(sectionName, newPassword);
	}
	
	public void save(Password password) {
		passwordDao.add(password);
	}
	
	public List<Password> getAll() {
		return passwordDao.getAll();
	}
	
}

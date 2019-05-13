package ru.fssprus.r82.service;

import ru.fssprus.r82.dao.TestDao;
import ru.fssprus.r82.dao.impl.TestDatabaseDao;
import ru.fssprus.r82.entity.Test;

public class TestService {
	// TODO: autowired
	private TestDao testDao = new TestDatabaseDao();
	
	public void add(Test test) {
		testDao.add(test);
	}
	
}

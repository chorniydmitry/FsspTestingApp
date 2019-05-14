package ru.fssprus.r82.service;

import java.util.List;

import ru.fssprus.r82.dao.TestDao;
import ru.fssprus.r82.dao.impl.TestDatabaseDao;
import ru.fssprus.r82.entity.Test;

public class TestService {
	// TODO: autowired
	private TestDao testDao = new TestDatabaseDao();
	
	public void add(Test test) {
		testDao.add(test);
	}
	
	public List<Test> getAll(int fromId, int toId) {
		return testDao.getAll(fromId, toId);
	}
	
}

package ru.fssprus.r82.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ru.fssprus.r82.dao.TestDao;
import ru.fssprus.r82.dao.impl.TestDatabaseDao;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.entity.User;

/**
 * @author Chernyj Dmitry
 *
 */
public class TestService {
	// TODO: autowired
	private TestDao testDao = new TestDatabaseDao();
	
	public void add(Test test) {
		testDao.add(test);
	}
	
	public List<Test> getAll(int fromId, int toId) {
		return testDao.getAll(fromId, toId);
	}
	
	public int countAll() {
		return testDao.getAmountOfItems();
	}
	
	
	public void delete(Test test) {
		testDao.remove(test);
	}

	public List<Test> getByUserSpecifiactionLevelAndDate(Set<User> users, Set<Specification> specs, QuestionLevel level,
			Date dateMore, Date dateLess, String result, int scoreMore, int scoreLess) {
		return testDao.getByUserSpecifiactionLevelAndDate(-1, -1, users, specs, level, dateMore, dateLess, result, scoreMore, scoreLess);
		
	}

	public List<Test> getByUserSpecifiactionLevelAndDate(int startX, int endX, Set<User> users,
			Set<Specification> specs, QuestionLevel level, Date dateMore, Date dateLess, String result, int scoreMore,
			int scoreLess) {
		return testDao.getByUserSpecifiactionLevelAndDate(startX, endX, users, specs, level, dateMore, dateLess, result, scoreMore, scoreLess);
	}
	
	public int countByUserSpecifiactionLevelAndDate(Set<User> users,
			Set<Specification> specs, QuestionLevel level, Date dateMore, Date dateLess, String result, int scoreMore,
			int scoreLess) {
		return testDao.countByUserSpecifiactionLevelAndDate(users, specs, level, dateMore, dateLess, result, scoreMore, scoreLess);
		
	}
	
}

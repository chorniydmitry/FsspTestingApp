package ru.fssprus.r82.service;

import java.util.List;
import java.util.Set;

import ru.fssprus.r82.dao.QuestionDao;
import ru.fssprus.r82.dao.impl.QuestionDatabaseDao;
import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;

public class QuestionService {
	// TODO: autowired
	private QuestionDao questionDao = new QuestionDatabaseDao();

	public List<Question> getByName(int startPos, int endPos, String name) {
		return questionDao.getByTitle(startPos, endPos, name);
	}

	public List<Question> getByAnswer(int startPos, int endPos, Answer answer) {
		return questionDao.getByAnswer(startPos, endPos, answer);
	}

	public List<Question> getBySpecification(int startPos, int endPos, Specification spec) {
		return questionDao.getBySpecification(startPos, endPos, spec);
	}

	public List<Question> getByIDsList(Set<Long> list) {
		return questionDao.getByIds(list);
	}
	
	public int getItemsCountBySpecification(Specification spec) {
		return questionDao.countItemsBySpecification(spec);
	}
	
	public int getAmountOfItems() {
		return questionDao.getAmountOfItems();
	}
	
	public Question getById(Long id) {
		return questionDao.getById(id);
	}

	public List<Question> getAllBySpecification(Specification specification) {
		return questionDao.getAllBySpecification(specification);
		
	}
}

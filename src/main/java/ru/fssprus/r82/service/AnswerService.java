package ru.fssprus.r82.service;

/**
 * @author Chernyj Dmitry
 *
 */
import java.util.List;
import java.util.Set;

import ru.fssprus.r82.dao.AnswerDao;
import ru.fssprus.r82.dao.impl.AnswerDatabaseDao;
import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;

public class AnswerService{
	//TODO: autowired
	private AnswerDao answerDao = new AnswerDatabaseDao();
	
	public List<Answer> getByName(int startPos, int endPos, String name) {
		return answerDao.getByTitle(startPos, endPos, name);
	}
	
	public List<Answer> getAllByQuestion(int startPos, int endPos, Question question) {
		return answerDao.getByQuestion(startPos, endPos, question);
	}	
	
	public List<Answer> getAllByQuestion(Question question) {
		return answerDao.getByQuestion(-1, -1, question);
	}
	
	public List<Answer> getCorrectByQuestion(int startPos, int endPos, Question question) {
		return answerDao.getCorrectByQuestion(startPos, endPos, question);
	}
	
	public List<Answer> getCorrectByQuestion(Question question) {
		return answerDao.getCorrectByQuestion(-1, -1, question);
	}
	
	public List<Answer> getCorrectByQuestionSet(int startPos, int endPos, Set<Question> questions) {
		return answerDao.getCorrectByQuestionSet(startPos, endPos, questions);
	}
	
	public void save(Answer ans) {
		answerDao.add(ans);
	}

	public void delete(Answer ans) {
		answerDao.remove(ans);
		
	}
}

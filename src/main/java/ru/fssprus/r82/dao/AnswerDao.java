package ru.fssprus.r82.dao;

import java.util.List;
import java.util.Set;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;

public interface AnswerDao extends ItemDao<Answer> {
	public List<Answer> getByTitle(int startPos, int endPos, String title);
	
	public List<Answer> getByQuestion(int startPos, int endPos, Question question);
	
	public List<Answer> getCorrectByQuestion(int startPos, int endPos, Question question);
	
	public List<Answer> getBySpecification(int startPos, int endPos, Specification spec);

	List<Answer> getCorrectByQuestionSet(int startPos, int endPos, Set<Question> questionList);
	
}

package ru.fssprus.r82.dao;

/**
 * @author Chernyj Dmitry
 *
 */
import java.util.List;
import java.util.Set;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;

public interface AnswerDao extends ItemDao<Answer> {
	public List<Answer> getByTitle(int startPos, int endPos, String title);
	
	public List<Answer> getByQuestion(int startPos, int endPos, Question question);
	
	public List<Answer> getCorrectByQuestion(int startPos, int endPos, Question question);
	
	public List<Answer> getCorrectByQuestionSet(int startPos, int endPos, Set<Question> questionList);
	
}

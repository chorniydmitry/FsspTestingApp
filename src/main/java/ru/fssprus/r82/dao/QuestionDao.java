package ru.fssprus.r82.dao;

import java.util.List;
import java.util.Set;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;

public interface QuestionDao extends ItemDao<Question> {
	
	public List<Question> getByTitle(int startPos, int endPos, String title);
	
	public List<Question> getByAnswer(int startPos, int endPos, Answer answer);
	
	public List<Question> getBySpecification(int startPos, int endPos, Specification spec);
	
	public List<Question> getAllBySpecification(Specification spec);
	
	public List<Question> getByIds(Set<Long> ids);
	
	public int countItemsBySpecification(Specification spec);
	
	public int getAmountOfItems();


}

package ru.fssprus.r82.dao;

import java.util.List;
import java.util.Set;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;

/**
 * @author Chernyj Dmitry
 *
 */
public interface QuestionDao extends ItemDao<Question> {
	
	public List<Question> getByTitle(int startPos, int endPos, String title);
	
	public List<Question> getByAnswer(int startPos, int endPos, Answer answer);
	
	public List<Question> getBySpecification(int startPos, int endPos, Specification spec);
	
	public List<Question> getByIds(Set<Long> ids);
	
	public List<Question> getByNameAndSpecification(String name, Specification spec);
	
	public int countItemsBySpecification(Specification spec);
	
	public int getAmountOfItems();

	public List<Question> getByNameSpecificationAndLevel(String name, Set<Specification> specs,
			Set<QuestionLevel> lvls);

	public List<Question> getBySpecificationAndLevel(int startPos, int endPos, Specification spec, QuestionLevel level);

	public int countBySpecificationAndLevel(Specification spec, QuestionLevel level);
	
	public List<Question> getByNameSpecListLvlListAndID(int startPos, int endPos, String name, Set<Specification> specs,
			Set<QuestionLevel> lvls, Long id);
	
	public int countByNameSpecListLvlListAndID(String name, Set<Specification> specs,
			Set<QuestionLevel> lvls, Long id);
	
	

}

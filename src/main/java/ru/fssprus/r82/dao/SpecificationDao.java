package ru.fssprus.r82.dao;

import java.util.List;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;

/**
 * @author Chernyj Dmitry
 *
 */
public interface SpecificationDao extends ItemDao<Specification> {
	
	public List<Specification> getByTitle(int startPos, int endPos, String title);
	
	public List<Specification> getByQuestion(int startPos, int endPos, Question questions);
	
}

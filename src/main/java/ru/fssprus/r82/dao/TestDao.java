package ru.fssprus.r82.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.entity.User;

/**
 * @author Chernyj Dmitry
 *
 */
public interface TestDao extends ItemDao<Test> {

	int getAmountOfItems();
	
	List<Test> getByUserSpecifiactionLevelAndDate(int startPos, int endPos, Set<User> user, Set<Specification> specification,
			QuestionLevel level, Date dateMore, Date dateLess, String result, int scoreMore, int scoreLess);

	int countByUserSpecifiactionLevelAndDate(Set<User> users, Set<Specification> specs, QuestionLevel level,
			Date dateMore, Date dateLess, String result, int scoreMore, int scoreLess);
	
}
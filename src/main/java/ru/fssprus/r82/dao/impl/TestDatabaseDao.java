package ru.fssprus.r82.dao.impl;

/**
 * @author Chernyj Dmitry
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.fssprus.r82.dao.TestDao;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.utils.HibernateUtil;

public class TestDatabaseDao extends AbstractHibernateDao<Test> implements TestDao {

	@Override
	public int getAmountOfItems() {
		int returnValue = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();
			Root<Test> root = criteriaQuery.from(Test.class);

			criteriaQuery.select(builder.count(root));

			TypedQuery<Object> q = session.createQuery(criteriaQuery);

			returnValue = Integer.parseInt(q.getSingleResult().toString());

			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	@Override
	public List<Test> getByUserSpecifiactionLevelAndDate(int startPos, int endPos, Set<User> users,
			Set<Specification> specifications, QuestionLevel level, Date dateMore, Date dateLess, String result,
			int scoreMore, int scoreLess) {
		List<Test> testList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Test> criteriaQuery = builder.createQuery(Test.class);

			Root<Test> root = criteriaQuery.from(Test.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (users != null && users.size() > 0)
				predicates.add(root.join("user").in(users));

			if (specifications != null && specifications.size() > 0)
				predicates.add(root.join("specification").in(specifications));

			if (level != null) {
				Predicate p = builder.conjunction();
				p = builder.and(p, builder.equal(root.get("level"), level));
				predicates.add(p);

			}

			if (dateMore != null)
				predicates.add(builder.greaterThanOrEqualTo(root.get("date"), dateMore));

			if (dateLess != null)
				predicates.add(builder.lessThanOrEqualTo(root.get("date"), dateLess));

			if (result != null && !result.isEmpty())
				predicates.add(builder.like(root.get("result"), "%" + result + "%"));

			if (scoreMore != 0)
				predicates.add(builder.greaterThanOrEqualTo(root.get("score"), scoreMore));

			if (scoreLess != 0)
				predicates.add(builder.lessThanOrEqualTo(root.get("score"), scoreLess));

			criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));

			Query<Test> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1) || !(startPos == -1)) {

				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			testList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return testList;
	}

	@Override
	public int countByUserSpecifiactionLevelAndDate(Set<User> users, Set<Specification> specs, QuestionLevel level,
			Date dateMore, Date dateLess, String result, int scoreMore, int scoreLess) {

		int returnValue = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();

			Root<Test> root = criteriaQuery.from(Test.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (users != null && users.size() > 0)
				predicates.add(root.join("user").in(users));

			if (specs != null && specs.size() > 0)
				predicates.add(root.join("specification").in(specs));

			if (level != null) {
				Predicate p = builder.conjunction();
				p = builder.and(p, builder.equal(root.get("level"), level));
				predicates.add(p);

			}

			if (dateMore != null)
				predicates.add(builder.greaterThanOrEqualTo(root.get("date"), dateMore));

			if (dateLess != null)
				predicates.add(builder.lessThanOrEqualTo(root.get("date"), dateLess));

			if (result != null && !result.isEmpty())
				predicates.add(builder.like(root.get("result"), "%" + result + "%"));

			if (scoreMore != 0)
				predicates.add(builder.greaterThanOrEqualTo(root.get("score"), scoreMore));

			if (scoreLess != 0)
				predicates.add(builder.lessThanOrEqualTo(root.get("score"), scoreLess));

			criteriaQuery.select(builder.count(root));
			criteriaQuery.where(predicates.toArray(new Predicate[] {}));

			TypedQuery<Object> t = session.createQuery(criteriaQuery);

			returnValue = Integer.parseInt(t.getSingleResult().toString());

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}

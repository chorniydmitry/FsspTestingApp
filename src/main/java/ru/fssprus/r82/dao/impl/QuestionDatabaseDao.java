package ru.fssprus.r82.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.fssprus.r82.dao.QuestionDao;
import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.utils.HibernateUtil;

/**
 * @author Chernyj Dmitry
 *
 */
public class QuestionDatabaseDao extends AbstractHibernateDao<Question> implements QuestionDao {

	public QuestionDatabaseDao() {
		super();
	}

	@Override
	public List<Question> getByIds(Set<Long> ids) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.select(root).where(builder.in(root.get("id")));

			Expression<String> parentExpression = root.get("id");
			Predicate parentPredicate = parentExpression.in(ids);
			criteriaQuery.where(parentPredicate);

			Query<Question> query = session.createQuery(criteriaQuery);

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public List<Question> getByTitle(int startPos, int endPos, String title) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.select(root).where(builder.like(root.get("title"), "%" + title + "%"));

			Query<Question> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public List<Question> getByAnswer(int startPos, int endPos, Answer answer) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.select(root).where(builder.equal(root.get("answer"), answer));

			Query<Question> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public List<Question> getBySpecification(int startPos, int endPos, Specification spec) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.where(root.join("specification").in(spec));

			Query<Question> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public List<Question> getBySpecificationAndLevel(int startPos, int endPos, Specification spec,
			QuestionLevel level) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.where(root.join("specification").in(spec), root.join("levels").in(level));

			Query<Question> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public int countItemsBySpecification(Specification spec) {
		int returnValue = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();
			Root<Question> root = criteriaQuery.from(Question.class);

			criteriaQuery.select(builder.count(root));
			criteriaQuery.where(root.join("specification").in(spec));

			TypedQuery<Object> q = session.createQuery(criteriaQuery);

			returnValue = Integer.parseInt(q.getSingleResult().toString());

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	@Override
	public int getAmountOfItems() {
		int returnValue = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();
			Root<Question> root = criteriaQuery.from(Question.class);

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
	public List<Question> getByNameAndSpecification(String name, Specification spec) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.select(root).where((builder.like(root.get("title"), "%" + name + "%")),
					(root.join("specification").in(spec)));

			Query<Question> query = session.createQuery(criteriaQuery);

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return questionList;
	}

	@Override
	public List<Question> getByNameSpecificationAndLevel(String name, Set<Specification> specs,
			Set<QuestionLevel> lvls) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.select(root).where((builder.like(root.get("title"), "%" + name + "%")),
					(root.join("specification").in(specs)), (root.join("levels").in(lvls)));

			Query<Question> query = session.createQuery(criteriaQuery);

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return questionList;
	}

	@Override
	public List<Question> getByNameSpecListLvlListAndID(int startPos, int endPos, String name, Set<Specification> specs,
			Set<QuestionLevel> lvls, Long id) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (id != 0) {
				predicates.add(builder.equal(root.get("id"), id));
			}

			if (!name.isEmpty()) {
				predicates.add(builder.like(root.get("title"), "%" + name + "%"));
			}
			if (specs != null) {
				predicates.add(root.join("specification").in(specs));
			}

			if (lvls != null) {
				predicates.add(root.join("levels").in(lvls));
			}

			criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));

			Query<Question> query = session.createQuery(criteriaQuery);
			
			if (!(endPos == -1) || !(startPos == -1)) {
				
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			questionList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return questionList;
	}
	
	@Override
	public int countBySpecificationAndLevel(Specification spec, QuestionLevel level) {
		int returnValue = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();
			Root<Question> root = criteriaQuery.from(Question.class);

			criteriaQuery.select(builder.count(root));
			criteriaQuery.where(root.join("specification").in(spec), root.join("levels").in(level));

			TypedQuery<Object> q = session.createQuery(criteriaQuery);

			returnValue = Integer.parseInt(q.getSingleResult().toString());

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	@Override
	public int countByNameSpecListLvlListAndID(String name, Set<Specification> specs, Set<QuestionLevel> lvls, Long id) {
		int returnValue = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();

			Root<Question> root = criteriaQuery.from(Question.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (id != 0 && id != null) {
				predicates.add(builder.equal(root.get("id"), id));
			}

			if (!name.isEmpty()) {
				predicates.add(builder.like(root.get("title"), "%" + name + "%"));
			}
			if (specs != null) {
				predicates.add(root.join("specification").in(specs));
			}

			if (lvls != null) {
				predicates.add(root.join("levels").in(lvls));
			}
			
			criteriaQuery.select(builder.count(root));
			criteriaQuery.where(predicates.toArray(new Predicate[] {}));

			TypedQuery<Object> q = session.createQuery(criteriaQuery);
			
			returnValue = Integer.parseInt(q.getSingleResult().toString());

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return returnValue;

	}

//  Получить список вопросов по списку id и спецификации
//	select q.title, s.name
//	from question as q, specification as s 
//	inner join question_specification as qs on qs.specification_id = s.id AND qs.question_id  = q.id
//	where q.id in(3,5,7,9) and s.id = 1
}

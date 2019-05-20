package ru.fssprus.r82.dao.impl;

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

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}
	

	@Override
	public List<Question> getAllByTitle(String title) {
		return getByTitle(-1, -1, title);
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

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public List<Question> getAllByAnswer(Answer answer) {
		return getByAnswer(-1, -1, answer);
	}

	@Override
	public List<Question> getBySpecification(int startPos, int endPos, Specification spec) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.where(root.join("specifications").in(spec));

			Query<Question> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			questionList = query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}
	
	@Override
	public List<Question> getBySpecificationAndLevel(int startPos, int endPos, Specification spec, QuestionLevel level) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.where(
					root.join("specifications").in(spec),
					root.join("levels").in(level));

			Query<Question> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			questionList = query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return questionList;
	}

	@Override
	public List<Question> getAllBySpecification(Specification spec) {
		return getBySpecification(-1, -1, spec);
	}

	@Override
	public List<Question> getAllBySpecificationAndLevel(Specification spec, QuestionLevel level) {
		return getBySpecificationAndLevel(-1, -1, spec, level);
	}

	@Override
	public int countItemsBySpecification(Specification spec) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();
			Root<Question> root = criteriaQuery.from(Question.class);

			criteriaQuery.select(builder.count(root));
			criteriaQuery.where(root.join("specifications").in(spec));

			TypedQuery<Object> q = session.createQuery(criteriaQuery);
			return Integer.parseInt(q.getSingleResult().toString());

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int getAmountOfItems() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();
			Root<Question> root = criteriaQuery.from(Question.class);

			criteriaQuery.select(builder.count(root));

			TypedQuery<Object> q = session.createQuery(criteriaQuery);
			return Integer.parseInt(q.getSingleResult().toString());

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public List<Question> getByNameAndSpecification(String name, Specification spec) {
		List<Question> questionList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);

			Root<Question> root = criteriaQuery.from(Question.class);
			criteriaQuery.select(root).where((builder.like(root.get("title"), "%" + name + "%")),
					(root.join("specifications").in(spec)));

			// criteriaQuery.where(root.join("specifications").in(spec));

			Query<Question> query = session.createQuery(criteriaQuery);

			questionList = query.getResultList();

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
					(root.join("specifications").in(specs)), (root.join("levels").in(lvls)));

			// criteriaQuery.where(root.join("specifications").in(spec));

			Query<Question> query = session.createQuery(criteriaQuery);

			questionList = query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return questionList;
	}

//  Получить список вопросов по списку id и спецификации
//	select q.title, s.name
//	from question as q, specification as s 
//	inner join question_specification as qs on qs.specification_id = s.id AND qs.question_id  = q.id
//	where q.id in(3,5,7,9) and s.id = 1
}
package ru.fssprus.r82.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.fssprus.r82.dao.AnswerDao;
import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.utils.HibernateUtil;

/**
 * @author Chernyj Dmitry
 *
 */
public class AnswerDatabaseDao extends AbstractHibernateDao<Answer> implements AnswerDao {

	@Override
	public List<Answer> getByTitle(int startPos, int endPos, String title) {
		List<Answer> answerList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Answer> criteriaQuery = builder.createQuery(Answer.class);

			Root<Answer> root = criteriaQuery.from(Answer.class);
			criteriaQuery.select(root).where(builder.like(root.get("title"), "%" + title + "%"));

			Query<Answer> query = session.createQuery(criteriaQuery);
			query.setFirstResult(startPos);
			query.setMaxResults(endPos);

			answerList = query.getResultList();
			
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return answerList;
	}

	@Override
	public List<Answer> getByQuestion(int startPos, int endPos, Question question) {
		List<Answer> answerList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Answer> criteriaQuery = builder.createQuery(Answer.class);

			Root<Answer> root = criteriaQuery.from(Answer.class);
			criteriaQuery.select(root).where(builder.equal(root.get("question"), question));

			Query<Answer> query = session.createQuery(criteriaQuery);
			
			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}
			
			answerList = query.getResultList();
			
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return answerList;
	}

	@Override
	public List<Answer> getCorrectByQuestion(int startPos, int endPos, Question question) {
		List<Answer> answerList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Answer> criteriaQuery = builder.createQuery(Answer.class);

			Root<Answer> root = criteriaQuery.from(Answer.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get("isCorrect"),  true), builder.equal(root.get("question"), question)));

			Query<Answer> query = session.createQuery(criteriaQuery);
			
			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			answerList = query.getResultList();
			
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return answerList;
	}
	
	@Override
	public List<Answer> getCorrectByQuestionSet(int startPos, int endPos, Set<Question> questionList) {
		List<Answer> answerList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Answer> criteriaQuery = builder.createQuery(Answer.class);

			Root<Answer> root = criteriaQuery.from(Answer.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get("isCorrect"),  true), root.get("question").in(questionList)));
			
			Query<Answer> query = session.createQuery(criteriaQuery);
			query.setFirstResult(startPos);
			query.setMaxResults(endPos);

			answerList = query.getResultList();
			
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return answerList;
	}
}

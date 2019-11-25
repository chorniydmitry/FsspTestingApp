package ru.fssprus.r82.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.fssprus.r82.dao.SpecificationDao;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.utils.HibernateUtil;

/**
 * @author Chernyj Dmitry
 *
 */
public class SpecifiactionDatabaseDao extends AbstractHibernateDao<Specification> implements SpecificationDao {

	public SpecifiactionDatabaseDao() {
		super();
	}

	@Override
	public List<Specification> getByTitle(int startPos, int endPos, String title) {
		List<Specification> specificationList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Specification> criteriaQuery = builder.createQuery(Specification.class);

			Root<Specification> root = criteriaQuery.from(Specification.class);
			criteriaQuery.select(root).where(builder.like(root.get("name"), "%" + title + "%"));

			Query<Specification> query = session.createQuery(criteriaQuery);
			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			specificationList = query.getResultList();
			
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return specificationList;
	}

	@Override
	public List<Specification> getByQuestion(int startPos, int endPos, Question question) {
		List<Specification> specList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Specification> criteriaQuery = builder.createQuery(Specification.class);

			Root<Specification> root = criteriaQuery.from(Specification.class);
			criteriaQuery.where(root.join("questionList").in(question));

			Query<Specification> query = session.createQuery(criteriaQuery);
			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			specList = query.getResultList();
			
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return specList;
	}

}

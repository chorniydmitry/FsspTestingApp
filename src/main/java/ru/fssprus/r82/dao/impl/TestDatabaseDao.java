package ru.fssprus.r82.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.fssprus.r82.dao.TestDao;
import ru.fssprus.r82.entity.Question;
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
	

}

package ru.fssprus.r82.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import ru.fssprus.r82.entity.Model;
import ru.fssprus.r82.utils.HibernateUtil;

/**
 * @author Chernyj Dmitry
 *
 */
public abstract class AbstractHibernateDao<T extends Model> {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	private Class<T> clazz;
	
	
	@SuppressWarnings("unchecked")
	public AbstractHibernateDao() {
		final ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		this.clazz = (Class<T>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
	}
	
	public List<T> getAll() {
		List<T> resultList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);

			Root<T> root = criteriaQuery.from(clazz);
			criteriaQuery.select(root);

			Query<T> query = session.createQuery(criteriaQuery);

			resultList = query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	public List<T> getAll(int limit) {
		List<T> resultList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);

			Root<T> root = criteriaQuery.from(clazz);
			criteriaQuery.select(root);

			Query<T> query = session.createQuery(criteriaQuery);
			query.setMaxResults(limit);

			resultList = query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	public List<T> getAll(int start, int end) {
		List<T> resultList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);

			Root<T> root = criteriaQuery.from(clazz);
			criteriaQuery.select(root);

			Query<T> query = session.createQuery(criteriaQuery);
			query.setFirstResult(start);
			query.setMaxResults(end);

			resultList = query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public T getById(Long Id) {
		List<T> resultList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);

			Root<T> root = criteriaQuery.from(clazz);
			criteriaQuery.select(root).where(builder.equal(root.get("id"), Id));

			Query<T> query = session.createQuery(criteriaQuery);

			resultList = query.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return resultList.get(0);
	}

	public void add(T model) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(model);
			transaction.commit();
		} catch (HibernateException e) {
	         if (transaction != null)
	             transaction.rollback();
			e.printStackTrace();
		}
	}

	public void delete(T model) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.delete(model);
			transaction.commit();
		} catch (HibernateException e) {
	         if (transaction != null)
	             transaction.rollback();
			e.printStackTrace();
		}
	}
	
	public void update(T model) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(model);
			transaction.commit();
		} catch (HibernateException e) {
	         if (transaction != null)
	             transaction.rollback();
			e.printStackTrace();
		}
	}
	
	public void remove(Long id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
	        T model = session.get(clazz, id);
	        
	        if(model != null)
	           session.delete(model);
			
	        transaction.commit();
		} catch (HibernateException e) {
			 if (transaction != null)
	             transaction.rollback();
			e.printStackTrace();
		}

	}
	
	public void remove(T model) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
	        if(model != null)
	           session.delete(model);
			
	        transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
	             transaction.rollback();
			e.printStackTrace();
		}
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}


}
package ru.fssprus.r82.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.fssprus.r82.dao.UserDao;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.utils.HibernateUtil;

/**
 * @author Chernyj Dmitry
 *
 */
public class UserDatabaseDao extends AbstractHibernateDao<User> implements UserDao {

	@Override
	public List<User> getBySurname(int startPos, int endPos, String surname) {
		List<User> userList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);

			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.select(root).where(builder.like(root.get("surname"), "%" + surname + "%"));

			Query<User> query = session.createQuery(criteriaQuery);
			
			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}
			
			userList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return userList;
	}

	@Override
	public List<User> getByNameSurnameSecondName(int startPos, int endPos, String name, String surname,
			String secondname) {
		List<User> userList = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);

			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.select(root).where(builder.like(root.get("surname"), "%" + surname + "%"),
					builder.like(root.get("name"), "%" + name + "%"),
					builder.like(root.get("secondName"), "%" + secondname + "%"));

			Query<User> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}

			userList = query.getResultList();

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return userList;
	}

	@Override
	public User getByNameSurnameSecondNameSingle(int startPos, int endPos, String name, String surname,
			String secondname) {
		User user = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);

			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.select(root).where(builder.like(root.get("surname"), "%" + surname + "%"),
					builder.like(root.get("name"), "%" + name + "%"),
					builder.like(root.get("secondName"), "%" + secondname + "%"));

			Query<User> query = session.createQuery(criteriaQuery);

			if (!(endPos == -1 || startPos == -1)) {
				query.setFirstResult(startPos);
				query.setMaxResults(endPos);
			}
			try {
				user = query.getSingleResult();
			} catch (Exception e) {
				System.err.println("Пользователя с таким ФИО не существует!");
			}
			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return user;
	}
}

package ru.fssprus.r82.dao.impl;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.fssprus.r82.dao.PasswordDao;
import ru.fssprus.r82.entity.Password;
import ru.fssprus.r82.utils.HibernateUtil;

/**
 * @author Chernyj Dmitry
 *
 */
public class PasswordDatabaseDao extends AbstractHibernateDao<Password> implements PasswordDao {

	@Override
	public Password getBySection(String sectionName) {
		Password pass = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Password> criteriaQuery = builder.createQuery(Password.class);

			Root<Password> root = criteriaQuery.from(Password.class);
			criteriaQuery.select(root).where(builder.like(root.get("sectionName"), "%" + sectionName + "%"));

			Query<Password> query = session.createQuery(criteriaQuery);

			pass = query.getResultList().get(0);


			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return pass;
	}

	@Override
	public boolean checkBySection(String section, String passToCheckMD5) {
		boolean returnValue = false;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Password> criteriaQuery = builder.createQuery(Password.class);

			Root<Password> root = criteriaQuery.from(Password.class);
			criteriaQuery.select(root).where(builder.like(root.get("sectionName"), "%" + section + "%"));

			Query<Password> query = session.createQuery(criteriaQuery);

			Password passwordMD5 = query.getResultList().get(0);

			String passFromDBMD5 = passwordMD5.getPasswordMD5();

			if (passFromDBMD5.equals(passToCheckMD5))
				returnValue = true;

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public void update(String section, String newPassword) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Password> criteriaQuery = builder.createQuery(Password.class);

			Root<Password> root = criteriaQuery.from(Password.class);
			criteriaQuery.select(root).where(builder.like(root.get("sectionName"), "%" + section + "%"));

			Query<Password> query = session.createQuery(criteriaQuery);

			Password passwordMD5 = query.getResultList().get(0);

			passwordMD5.setPasswordMD5(newPassword);

			super.update(passwordMD5);

			session.close();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getCountByName(String sectionName) {
		int returnValue = 0;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = builder.createQuery();
			Root<Password> root = criteriaQuery.from(Password.class);

			criteriaQuery.select(builder.count(root));
			criteriaQuery.where(builder.like(root.get("sectionName"), "%" + sectionName + "%"));

			TypedQuery<Object> q = session.createQuery(criteriaQuery);

			returnValue = Integer.parseInt(q.getSingleResult().toString());

			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return returnValue;
	}
}

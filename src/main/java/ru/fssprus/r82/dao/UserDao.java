package ru.fssprus.r82.dao;

import java.util.List;

import ru.fssprus.r82.entity.User;

/**
 * @author Chernyj Dmitry
 *
 */
public interface UserDao extends ItemDao<User>{

	List<User> getBySurname(int startPos, int endPos, String surname);
	List<User> getByNameSurnameSecondName(int startPos, int endPos, String name, String surname, String secondName);
	User getByNameSurnameSecondNameSingle(int startPos, int endPos, String name, String surname, String secondname);

}

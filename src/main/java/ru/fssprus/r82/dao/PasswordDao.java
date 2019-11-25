package ru.fssprus.r82.dao;

import ru.fssprus.r82.entity.Password;

/**
 * @author Chernyj Dmitry
 *
 */
public interface PasswordDao extends ItemDao<Password>{

	public boolean checkBySection(String section, String passToCheckMD5);

	public void update(String sectionName, String newPassword);
	
	public int getCountByName(String sectionName);
	
	public Password getBySection(String sectionName);
}

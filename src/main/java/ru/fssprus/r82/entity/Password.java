package ru.fssprus.r82.entity;

/**
 * @author Chernyj Dmitry
 *
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "password")
public class Password extends Model {
	@Column(name = "section", length = 255, unique = true)
	private String sectionName;
	
	@Column(name = "passMD5", length = 2048)
	private String passwordMD5;

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getPasswordMD5() {
		return passwordMD5;
	}

	public void setPasswordMD5(String passwordMD5) {
		this.passwordMD5 = passwordMD5;
	}
}

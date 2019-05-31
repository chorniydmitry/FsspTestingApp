package ru.fssprus.r82.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="password")
public class Password extends Model {
	@Column(name="section", length=255, unique=true)
	String sectionName;
	@Column(name="passMD5", length=2048)
	String passwordMD5;
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

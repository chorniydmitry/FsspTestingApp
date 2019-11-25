package ru.fssprus.r82.entity;

/**
 * @author Chernyj Dmitry
 *
 */
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="usr")
public class User extends Model {
	@NotNull
	@Size(min=2, max=30)
	@Pattern(regexp = "[А-Я][а-я]*")
	@Column(name="name")
	private String name;
	
	@NotNull
	@Size(min=2, max=30)
	@Pattern(regexp = "[А-Я][а-я]*")
	@Column(name="surname")
	private String surname;
	
	@NotNull
	@Size(min=2, max=30)
	@Pattern(regexp = "[А-Я][а-я]*")
	@Column(name="second_name")
	private String secondName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private Set<Test> testsDone;

	public User() {
		super();
	}
	
	public User(String name, String surname, String secondName) {
		super();
		this.name = name;
		this.surname = surname;
		this.secondName = secondName;
	}

	public User(Long id) {
		super(id);
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Set<Test> getTestsDone() {
		return testsDone;
	}

	public void setTestsDone(Set<Test> testsDone) {
		this.testsDone = testsDone;
	}
}

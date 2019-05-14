package ru.fssprus.r82.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="specification")
public class Specification extends Model{
	@Column(name="name", length=2048)
	private String name;
	
	@ManyToMany(mappedBy = "specifications", fetch = FetchType.EAGER)
	private Set<Question> questionList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="specification")
	private Set<Test> testList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(Set<Question> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "Specification [name=" + name + "]";
	}
	
	
}

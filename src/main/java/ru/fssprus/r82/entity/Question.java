package ru.fssprus.r82.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="question")
public class Question extends Model{
	@Column(name="title", length=2048)
	private String title;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="question")
	private Set<Answer> answers;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="question_specification", 
	joinColumns=@JoinColumn(name="question_id"),
	inverseJoinColumns=@JoinColumn(name="specification_id"))
	private Set<Specification> specifications;
	
	
    @ElementCollection(targetClass = QuestionLevel.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "question_level", joinColumns = @JoinColumn(name = "question_id", unique=false))
    @Enumerated(EnumType.STRING)
	private Set<QuestionLevel> levels;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Set<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}

	public Set<QuestionLevel> getLevels() {
		return levels;
	}

	public void setLevels(Set<QuestionLevel> levels) {
		this.levels = levels;
	}
	
}

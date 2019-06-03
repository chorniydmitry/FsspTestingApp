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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="question")
public class Question extends Model {
	@Column(name="title", length=2048)
	private String title;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="question", fetch = FetchType.EAGER)
	private Set<Answer> answers;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="specification_id") 
	private Specification specification;
	
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

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public Set<QuestionLevel> getLevels() {
		return levels;
	}

	public void setLevels(Set<QuestionLevel> levels) {
		this.levels = levels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((levels == null) ? 0 : levels.hashCode());
		result = prime * result + ((specification == null) ? 0 : specification.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (levels == null) {
			if (other.levels != null)
				return false;
		} else if (!levels.equals(other.levels))
			return false;
		if (specification == null) {
			if (other.specification != null)
				return false;
		} else if (!specification.equals(other.specification))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [title=" + title + ", answers=" + answers + ", specification=" + specification + ", levels="
				+ levels + "]";
	}
	
}

package ru.fssprus.r82.entity;

/**
 * @author Chernyj Dmitry
 *
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import ru.fssprus.r82.utils.AppConstants;

@Entity
@Table(name="question")
public class Question extends Model {
	@NotNull
    @ManyToOne
    @JoinColumn(name="specification_id") 
    private Specification specification;
	
	@NotNull
	@Size(min=AppConstants.QUESTION_TEXT_MIN_LENGTH)
	@Column(name="title", length=2048)
	private String title;
	
	@Size(min=AppConstants.MIN_ANSWERS_AMOUNT, max=AppConstants.MAX_ANSWERS_AMOUNT)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="question", fetch = FetchType.EAGER)
	private Set<Answer> answers;
	
	@NotNull
    @ElementCollection(targetClass = QuestionLevel.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "question_level", joinColumns = @JoinColumn(name = "question_id", unique=false))
    @Enumerated(EnumType.STRING)
	@Size(min=1)
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

	public Set<QuestionLevel> getLevels() {
		return levels;
	}

	public void setLevels(Set<QuestionLevel> levels) {
		this.levels = levels;
	}
	
	public Specification getSpecification() {
		return specification;
	}
	
	public void setSpecification(Specification specification) {
		this.specification = specification;
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

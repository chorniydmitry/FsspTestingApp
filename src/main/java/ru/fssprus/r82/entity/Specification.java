package ru.fssprus.r82.entity;

/**
 * @author Chernyj Dmitry
 *
 */
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "specification")
public class Specification extends Model {
	@OneToMany(cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy="specification")
	private Set<Question> questionList;

	@NotNull
	@Size(min=5)
	@Column(name = "name", length = 2048, unique = true, nullable = false, updatable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specification", fetch = FetchType.EAGER)
	private Set<Test> testList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy =
	// "specifications")

	public void setQuestionList(Set<Question> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "Specification [name=" + name + ", testList=" + testList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((testList == null) ? 0 : testList.hashCode());
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
		Specification other = (Specification) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (testList == null) {
			if (other.testList != null)
				return false;
		} else if (!testList.equals(other.testList))
			return false;
		return true;
	}

	public Set<Question> getQuestionList() {
		return questionList;
	}

}

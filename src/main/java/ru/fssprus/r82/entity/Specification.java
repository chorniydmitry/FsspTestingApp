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
	
	@Column(name="name", length=2048, unique = true, nullable = false, updatable = false)
	private String name;
	
//	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
//			mappedBy = "specifications")
//	private Set<Question> questionList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="specification", fetch=FetchType.EAGER)
	private Set<Test> testList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "specifications")

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
	
	
	
	
	
	
}
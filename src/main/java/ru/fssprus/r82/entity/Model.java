package ru.fssprus.r82.entity;

/**
 * @author Chernyj Dmitry
 *
 */
import javax.persistence.*;

@MappedSuperclass
public class Model {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	public Model() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Model(Long id) {
		this.id = id;
	}
	
}
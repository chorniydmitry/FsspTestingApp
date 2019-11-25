package ru.fssprus.r82.entity;

/**
 * @author Chernyj Dmitry
 *
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class Answer extends Model {
	
	@Column(name="title", length=2048)
	private String title;
	
	@Column(name="is_correct")
	private Boolean isCorrect;
	
	@ManyToOne
	@JoinColumn(name="question_id", nullable=false)
	private Question question;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Answer [title=" + title + ", isCorrect=" + isCorrect + "]";
	}
	
	
	
	

}

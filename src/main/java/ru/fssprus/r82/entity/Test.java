package ru.fssprus.r82.entity;

/**
 * @author Chernyj Dmitry
 *
 */
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Table(name="test")
public class Test extends Model {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Past
	@Column(name="date")
	private Date date;
	
	@Min(20)
	@Column(name="total_questions")
	private int totalQuestions;
	
	@Min(0)
	@Column(name="correct_answers")
	private int correctAnswers;
	
	@Min(0)
	@Column(name="score")
	private int score;
	
	@Column(name="result")
	private String result;
	
	@Min(0)
	@Column(name="testing_time")
	private int testingTime;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private QuestionLevel level;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="specification_id")
	private Specification specification;
	
	public Test() {
		super();
	}

	public Test(Long id) {
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTestingTime() {
		return testingTime;
	}

	public void setTestingTime(int testingTime) {
		this.testingTime = testingTime;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public QuestionLevel getLevel() {
		return level;
	}

	public void setLevel(QuestionLevel level) {
		this.level = level;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}

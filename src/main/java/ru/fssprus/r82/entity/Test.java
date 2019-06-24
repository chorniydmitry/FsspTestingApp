package ru.fssprus.r82.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Past;

@Entity
@Table(name="test")
public class Test extends Model {
	@Valid
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Past
	@Column(name="date")
	private Date date;
	
	@Column(name="total_questions")
	private int totalQuestions;
	
	@Column(name="correct_answers")
	private int correctAnswers;
	
	@Column(name="score")
	private int score;
	
	@Column(name="result")
	private String result;
	
	@Column(name="testing_time")
	private int testingTime;
	
	@Valid
	@Column(name="level")
	private String level;
	
	@Valid
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
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

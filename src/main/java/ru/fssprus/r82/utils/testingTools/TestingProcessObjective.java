package ru.fssprus.r82.utils.testingTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.swing.ulils.MessageBox;

public class TestingProcessObjective {
	private User testedUser;
	private List<Question> questionsToAskList;
	private Map<Question, List<Answer>> questionsAndAnswersGiven;
	boolean isQuizzFinished = false;

	public TestingProcessObjective(User user, List<Question> questionsToAsk) {
		this.testedUser = user;
		this.questionsToAskList = questionsToAsk;
		
		initQuestionsAndAnswersGiven();
	}
	
	private void initQuestionsAndAnswersGiven() {
		questionsAndAnswersGiven = new HashMap<>();
		
		questionsToAskList.forEach(question -> 
			questionsAndAnswersGiven.put(question, new ArrayList<>()));
	}

	public void makeAnswer(Question question, List<Answer> answersGiven) {
		if(!checkQuestion(question)) {
			MessageBox.showWrongQuestionToAnswerErrorMessage(null);
			return;
		}
		if((!checkAnswers(question, answersGiven)) && answersGiven.size() > 0) {
			MessageBox.showWrongAnswerListForQuestionErrorMessage(null);
			return;
		}

		questionsAndAnswersGiven.put(question, answersGiven);
	}
	
	public void finishTest() {
		isQuizzFinished = true;
	}

	private boolean checkQuestion(Question q) {
		return (questionsToAskList.contains(q)) ? true : false;
	}

	private boolean checkAnswers(Question q, List<Answer> answers) {
		int answersFound = 0;

		List<Answer> answersToCompare = new ArrayList<>(q.getAnswers());

		for (Answer answer : answers)
			answersFound = answersToCompare.contains(answer) ? (answersFound + 1) : answersFound;
		
			
		return answersFound == answers.size();
	}

	public User getTestedUser() {
		return testedUser;
	}

	public void setTestedUser(User testedUser) {
		this.testedUser = testedUser;
	}

	public List<Question> getQuestionsToAskList() {
		return questionsToAskList;
	}

	public void setQuestionsToAskList(List<Question> questionsToAskList) {
		this.questionsToAskList = questionsToAskList;
	}

	public Map<Question, List<Answer>> getQuestionsAndAnswersGiven() {
		return questionsAndAnswersGiven;
	}

	public void setQuestionsAndAnswersGiven(Map<Question, List<Answer>> questionsAndAnswersGiven) {
		this.questionsAndAnswersGiven = questionsAndAnswersGiven;
	}

	public boolean isQuizzFinished() {
		return isQuizzFinished;
	}

	public void setQuizzFinished(boolean isQuizzFinished) {
		this.isQuizzFinished = isQuizzFinished;
	}
}

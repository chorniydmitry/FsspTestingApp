package ru.fssprus.r82.utils.testingTools;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.service.AnswerService;
import ru.fssprus.r82.utils.MarkCounter;

public class TestingProcessAnaliser {
	
	private TestingProcessObjective testingProcess;
	private int correctAmount;
	private int questionAmount;
	
	private Map<Question, List<Answer>> userAnswers;
	
	public TestingProcessAnaliser(TestingProcessObjective testingProcess) {
		this.testingProcess = testingProcess;
		initVariables();
		
	}
	
	private void initVariables() {
		userAnswers = testingProcess.getQuestionsAndAnswersGiven();
		correctAmount = 0;
		questionAmount = testingProcess.getQuestionsToAskList().size();
	}
	
	public void analize() {
		for(Entry<Question, List<Answer>> entry: userAnswers.entrySet()) {
			Question questionToCheck = entry.getKey();
			List<Answer> correctAnswers = getCorrectAnswers(questionToCheck);
			List<Answer> givenAnswers = entry.getValue();
			
			correctAmount = ((compareAnswers(correctAnswers, givenAnswers)) ? correctAmount + 1 : correctAmount);
					
		}
	}
	
	// Защитываются только полные совпадения!
	private boolean compareAnswers(List<Answer> correctAnswers, List<Answer> givenAnswers) {
		if(correctAnswers.size() != givenAnswers.size())
			return false;
		
		for (Answer answer : givenAnswers) {
			if(!answer.getIsCorrect())
				return false;
		}
		
		return true;
	}

	private List<Answer> getCorrectAnswers(Question question) {
		return new AnswerService().getCorrectByQuestion(question);
	}
	

	
	public String getMarkOneToFive() {
		return String.valueOf(MarkCounter.countInOneToFive(questionAmount, correctAmount));
	}

	public String getMarkLetter() {
		return String.valueOf(MarkCounter.countMarkInECTS(questionAmount, correctAmount));
	}

	public String getMarkText() {
		return String.valueOf(MarkCounter.countInWords(questionAmount, correctAmount));
	}

	public String getMarkPercent() {
		return String.valueOf(MarkCounter.countMarkInPercent(questionAmount, correctAmount));
	}

	public Color getMarkColor() {
		return MarkCounter.countInColors(questionAmount, correctAmount);
	}

	public int getCorrectAnswersAmount() {
		return correctAmount;
	}

}

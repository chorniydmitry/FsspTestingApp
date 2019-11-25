package ru.fssprus.r82.utils.testingTools;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H1;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Li;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Ul;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.service.AnswerService;
import ru.fssprus.r82.utils.MarkCounter;

/**
 * @author Chernyj Dmitry
 *
 */
public class TestingProcessAnaliser {

	private static final String WRONGS_HTML_ANS_LIST_BOLD_TEXT = "Список вопросов:";
	private static final String WRONGS_HTML_QUESTION_BOLD_TEXT = "Вопрос:";
	private static final String WRONGS_HTML_ANSWER_CHOSEN_BOLD_TEXT = "Выбраны ответы:";
	private static final String WRONGS_HTML_CORRECT_ANSWER_BOLD_TEXT = "Верные ответы:";
	private static final String WRONGS_HTML_NOTHING_SELECTED_TEXT = "Ничего не выбрано";
	private static final String HTML_STYLE_BOLD = "font-weight: bold";
	private static final String DELIMETER_DASHES_TEXT = "-------------------------------"
			+ "---------------------------------------------------------------------";

	private TestingProcess testingProcess;
	private int correctAmount;
	private int questionAmount;

	private Map<Question, List<Answer>> userAnswers;
	private Map<Question, List<Answer>> wrongAnswers;

	public TestingProcessAnaliser(TestingProcess testingProcess) {
		this.testingProcess = testingProcess;
		initVariables();

	}
	
	private void initVariables() {
		wrongAnswers = new HashMap<>();
		userAnswers = testingProcess.getQuestionsAndAnswersGiven();
		correctAmount = 0;
		questionAmount = testingProcess.getQuestionsAndAnswersGiven().keySet().size();
	}

	public void analize() {
		for (Entry<Question, List<Answer>> entry : userAnswers.entrySet()) {
			Question questionToCheck = entry.getKey();
			List<Answer> correctAnswers = getCorrectAnswers(questionToCheck);
			List<Answer> givenAnswers = entry.getValue();

			if (compareAnswers(correctAnswers, givenAnswers)) {
				correctAmount++;
			} else {
				wrongAnswers.put(questionToCheck, givenAnswers);
			}
		}
	}

	public int getTotalAmount() {
		return questionAmount;
	}

	// Защитываются только полные совпадения!
	private boolean compareAnswers(List<Answer> correctAnswers, List<Answer> givenAnswers) {
		if (correctAnswers.size() != givenAnswers.size())
			return false;

		for (Answer answer : givenAnswers) {
			if (!answer.getIsCorrect())
				return false;
		}

		return true;
	}

	private List<Answer> getCorrectAnswers(Question question) {
		return new AnswerService().getCorrectByQuestion(question);
	}

	public String printWrongs() {
		Html html = new Html();
		html.appendChild(new H1().appendText(WRONGS_HTML_ANS_LIST_BOLD_TEXT));
		html.appendChild(new Br());

		for (Entry<Question, List<Answer>> entry : wrongAnswers.entrySet()) {
			
			html.appendChild(new P().appendText(WRONGS_HTML_QUESTION_BOLD_TEXT).setStyle(HTML_STYLE_BOLD));
			
			html.appendText(entry.getKey().getTitle());
			
			html.appendChild(new P().appendText(printChosenWrongAnswers(entry.getValue())));
			html.appendChild(new P().appendText(printCorrectAnswers(entry.getKey())));
			
			html.appendChild(new P().appendText(DELIMETER_DASHES_TEXT));

		}
		return html.write();
	}

	private String printCorrectAnswers(Question question) {
		Ul ul = new Ul().appendText(WRONGS_HTML_CORRECT_ANSWER_BOLD_TEXT);

		for (Answer ans : question.getAnswers()) {
			if (ans.getIsCorrect())
				ul.appendChild(new Li().appendText(ans.getTitle()));
		}
		return ul.write();
	}

	private String printChosenWrongAnswers(List<Answer> answers) {
		Ul ul = new Ul().appendText(WRONGS_HTML_ANSWER_CHOSEN_BOLD_TEXT);

		if (!(answers == null) && answers.size() > 0)
			for (Answer ans : answers)
				ul.appendChild(new Li().appendText(ans.getTitle()));

		else
			ul.appendChild(new Li().appendText(WRONGS_HTML_NOTHING_SELECTED_TEXT));
		return ul.write();
	}

	public int getMarkOneToFive() {
		return MarkCounter.countInOneToFive(questionAmount, correctAmount);
	}

	public String getMarkLetter() {
		return String.valueOf(MarkCounter.countMarkInECTS(questionAmount, correctAmount));
	}

	public String getMarkText() {
		return String.valueOf(MarkCounter.countInWords(questionAmount, correctAmount));
	}

	public int getMarkPercent() {
		return MarkCounter.countMarkInPercent(questionAmount, correctAmount);
	}

	public Color getMarkColor() {
		return MarkCounter.countInColors(questionAmount, correctAmount);
	}

	public int getCorrectAnswersAmount() {
		return correctAmount;
	}

	public int getWrongsAmount() {
		return questionAmount - correctAmount;
	}

	public TestingProcess getTestingProcess() {
		return testingProcess;
	}

}

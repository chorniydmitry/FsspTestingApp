package ru.fssprus.r82.utils.testingTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.Utils;

/**
 * @author Chernyj Dmitry
 *
 */
public class TestProcessBuilder {
	private List<Specification> specifications;
	private QuestionLevel questionLevel;
	private List<Question> questions;
	private List<Integer> amountQuestionsForSpecs;
	private User user;

	public TestProcessBuilder(List<Specification> specifications, QuestionLevel questionLevel, User user) {
		this.specifications = specifications;
		this.questionLevel = questionLevel;
		this.user = user;
	}

	private void countAmountsOfQuestionsForSpecifications() {
		int amountOfQuestions = 0;
		int commonPercent = 0;

		switch (questionLevel) {
		case Базовый:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("base.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("base.common.percent"));
			break;
		case Стандартный:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("standart.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("standart.common.percent"));
			break;
		case Продвинутый:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("advanced.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("advanced.common.percent"));
			break;
		case Резерв:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("reserve.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("reserve.common.percent"));
			break;
		}

		amountQuestionsForSpecs = initAmountsOfQuestionsForSpecList(amountOfQuestions, commonPercent);
	}

	private List<Integer> initAmountsOfQuestionsForSpecList(int amountOfQuestions, int commonPercent) {
		int commonQuestsAmount = Utils.countCommonQuestsAmount(amountOfQuestions, commonPercent);

		int specQuestsAmount = Utils.countSpecQuestsAmount(amountOfQuestions, commonQuestsAmount);

		List<Integer> amountQuestionsForSpecs = new ArrayList<Integer>();
		
		amountQuestionsForSpecs.add(specQuestsAmount);
		amountQuestionsForSpecs.add(commonQuestsAmount);
		
		return amountQuestionsForSpecs;
	}

	private void loadQuestions() {
		QuestionService questService = new QuestionService();

		if (questions == null)
			questions = new ArrayList<Question>();

		for (int i = 0; i < specifications.size(); i++) {
			List<Question> allQuestionsList = questService.getAllBySpecificationAndLevel(specifications.get(i),
					questionLevel);

			Random rnd = new Random();
			Set<Integer> randomIndexesSet = new HashSet<Integer>();

			do {
				randomIndexesSet.add(rnd.nextInt(allQuestionsList.size()));

			} while (randomIndexesSet.size() < amountQuestionsForSpecs.get(i));

			randomIndexesSet.forEach((n) -> questions.add(allQuestionsList.get(n)));

			Collections.shuffle(questions);
			
			
		}
	}

	public TestingProcess buildTest() {
		countAmountsOfQuestionsForSpecifications();
		loadQuestions();

		return new TestingProcess(user, questions, questionLevel, specifications.get(0));

	}

}

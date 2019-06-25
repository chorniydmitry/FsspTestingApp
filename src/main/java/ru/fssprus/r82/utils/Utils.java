package ru.fssprus.r82.utils;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.swing.ulils.MessageBox;

public class Utils {

	public static int countMinimumCommonQuestionsForLevel(String level) {
		try {
		int amount = Integer.parseInt(ApplicationConfiguration.getItem(level + ".num"));
		int commons = Integer.parseInt(ApplicationConfiguration.getItem(level + ".common.percent"));
		
		return (int) (amount * (commons / 100.0));
		
		} catch(Exception e) {
			MessageBox.showAppConfigFileNotFoundOrCorrupted(null);
		}
		return 0;
		
	}
	
	public static int countTestDialogTaQuestionHeight(int height) {
		return (2 * height / 3) - 60;
	}

	public static int countTestDialogPnlAnswersHeight(int height) {
		return height / 3;
	}
	
	public static int countCommonQuestsAmount(int amountOfQuestions, int commonPercent) {
		return (int) Math.round((double) (amountOfQuestions * (commonPercent / 100.0)));
	}
	
	public static int countSpecQuestsAmount(int amountOfQuestions, int commonQuestsAmount) { 
		return amountOfQuestions - commonQuestsAmount;
	}

	public static int countMinimumCommonQuestionsForLevel(int selectedLevel) {
		return countMinimumCommonQuestionsForLevel(QuestionLevel.values()[selectedLevel]);
	}
	
	public static int countMinimumCommonQuestionsForLevel(QuestionLevel qlevel) {
		String level = null;
		switch(qlevel) {
		case Базовый:
			level = "base";
			break;
		case Стандартный:
			level = "standart";
			break;
		case Продвинутый:
			level = "advanced";
			break;
		case Резерв:
			level = "reserve";
			break;
		}
		return countMinimumCommonQuestionsForLevel(level);
	}
	
	public static boolean isNumeric(String strNum) {
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}
}

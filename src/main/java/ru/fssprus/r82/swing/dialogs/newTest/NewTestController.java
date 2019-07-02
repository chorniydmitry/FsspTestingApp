package ru.fssprus.r82.swing.dialogs.newTest;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.TestingProcess;
import ru.fssprus.r82.utils.Utils;

public class NewTestController extends CommonController<NewTestDialog> {

	private static final String COMMON_TEXT = "Общие";

	public NewTestController(NewTestDialog dialog) {
		super(dialog);
		loadSpecificationList();
	}

	@Override
	protected void setListeners() {
		dialog.getBtnBegin().addActionListener(listener -> doBegin());
		dialog.getBtnCancel().addActionListener(listener -> doCancel());
		dialog.getCbSpecification().addActionListener(listener -> doCheckSpecAndLevels());
	}

	private void doBegin() {
		dialog.resetUserInputComponents();

		if (!validateFields())
			return;

		int selectedLevel = dialog.getSelectedLevelIndex();

		List<Specification> specs = configureSpecsList(selectedLevel);

		TestingProcess tp = initNewTestingProcess(specs, selectedLevel);
		fillUserInfoForTestingProcess(tp);

		DialogBuilder.showTestDialog(specs, tp, selectedLevel);
		
		dialog.dispose();
	}

	private void fillUserInfoForTestingProcess(TestingProcess testingProcess) {
		String userName = dialog.getTfName().getText();
		String userSurname = dialog.getTfSurname().getText();
		String userSecondName = dialog.getTfSecondName().getText();

		testingProcess.fillUserInfo(userName, userSurname, userSecondName);
	}

	private List<Specification> configureSpecsList(int selectedLevel) {
		Specification commonSpec = getCommonSpec(selectedLevel);
		Specification selectedSpec = getUserSelectedSpec(selectedLevel);

		List<Specification> specs = new ArrayList<>();
		specs.add(selectedSpec);
		specs.add(commonSpec);

		return specs;
	}
	
	private Specification getUserSelectedSpec(int selectedLevel) {
		SpecificationService specService = new SpecificationService();
		
		String userSelectedSpecText = String.valueOf(dialog.getCbSpecification().getSelectedItem());
		
		Specification selectedSpec = specService.getUniqueByName(userSelectedSpecText);
		
		return selectedSpec;
	}

	private Specification getCommonSpec(int selectedLevel) {
		SpecificationService specService = new SpecificationService();
		QuestionService questionService = new QuestionService();
		
		Specification commonSpec = specService.getUniqueByName(COMMON_TEXT);

		int amountOfCommons = questionService.getCountBySpecificationAndLevel(commonSpec,
				QuestionLevel.values()[selectedLevel]);
		int minimumCommons = Utils.countMinimumCommonQuestionsForLevel(selectedLevel);

		if ((commonSpec == null) || (amountOfCommons < minimumCommons)) {
			MessageBox.showNotEnoughCommonQuestionError(dialog);
			return null;
		}
		
		return commonSpec;
	}

	private void doCancel() {
		dialog.dispose();
	}

	private void doCheckSpecAndLevels() {
		dialog.getRbLevels().forEach((n) -> n.setEnabled(true));

		QuestionService qService = new QuestionService();
		SpecificationService sService = new SpecificationService();
		Specification specification = sService
				.getUniqueByName(String.valueOf(dialog.getCbSpecification().getSelectedItem()));

		for (int i = 0; i < QuestionLevel.values().length; i++) {
			int qPerLvl = qService.countBySpecificationAndLevel(specification, QuestionLevel.values()[i]);

			if ((qPerLvl == 0) || (qPerLvl < AppConstants.MINIMUM_QUESTIONS_TO_INIT_TEST))
				dialog.getRbLevels().get(i).setEnabled(false);
		}
	}

	private boolean validateFields() {

		boolean validationPassed = true;

		if (dialog.getTfName().getText().isEmpty()) {
			dialog.getTfName().setBackground(Color.RED);
			validationPassed = false;
		}
		if (dialog.getTfSurname().getText().isEmpty()) {
			dialog.getTfSurname().setBackground(Color.RED);
			validationPassed = false;
		}
		if (dialog.getTfSecondName().getText().isEmpty()) {
			dialog.getTfSecondName().setBackground(Color.RED);
			validationPassed = false;
		}
		Object cbValue = dialog.getCbSpecification().getSelectedItem();

		if (cbValue == null) {
			dialog.getCbSpecification().setBackground(Color.RED);
			validationPassed = false;
		}

		if (dialog.getSelectedLevelIndex() == AppConstants.NO_INDEX_SELECTED) {
			for (JRadioButton lvls : dialog.getRbLevels())
				lvls.setBackground(Color.RED);
			validationPassed = false;
		}

		return validationPassed;

	}

	private void loadSpecificationList() {
		SpecificationService service = new SpecificationService();
		List<Specification> specList = service.getAll();

		dialog.getCbSpecification().addItem(null);

		for (Specification spec : specList) {
			if (spec.getName().toUpperCase().equals(COMMON_TEXT.toUpperCase()))
				continue;
			dialog.getCbSpecification().addItem(spec.getName());
		}
	}

	private TestingProcess initNewTestingProcess(List<Specification> specs, int level) {
		int amountOfQuestions = 0;
		int commonPercent = 0;

		switch (level) {
		case 0:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("base.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("base.common.percent"));
			break;
		case 1:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("standart.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("standart.common.percent"));
			break;
		case 2:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("advanced.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("advanced.common.percent"));
			break;
		case 3:
			amountOfQuestions = Integer.parseInt(ApplicationConfiguration.getItem("reserve.num"));
			commonPercent = Integer.parseInt(ApplicationConfiguration.getItem("reserve.common.percent"));
			break;
		}

		List<Integer> amountQuestionsForSpecs = initAmountsOfQuestionsForSpecList(amountOfQuestions, commonPercent);
		TestingProcess testingProcess = new TestingProcess(specs, amountQuestionsForSpecs, getSelectedLevel());
		return testingProcess;
	}

	private List<Integer> initAmountsOfQuestionsForSpecList(int amountOfQuestions, int commonPercent) {
		int commonQuestsAmount = Utils.countCommonQuestsAmount(amountOfQuestions, commonPercent);

		int specQuestsAmount = Utils.countSpecQuestsAmount(amountOfQuestions, commonQuestsAmount);

		List<Integer> amountQuestionsForSpecs = new ArrayList<Integer>();
		amountQuestionsForSpecs.add(specQuestsAmount);
		amountQuestionsForSpecs.add(commonQuestsAmount);
		return amountQuestionsForSpecs;
	}

	private String getSelectedLevel() {

		ArrayList<JRadioButton> rbs = dialog.getRbLevels();
		for (int i = 0; i < rbs.size(); i++) {
			if (rbs.get(i).isSelected())
				return rbs.get(i).getText();
		}
		return null;
	}

}

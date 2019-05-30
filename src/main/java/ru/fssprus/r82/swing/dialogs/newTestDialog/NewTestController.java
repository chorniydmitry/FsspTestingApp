package ru.fssprus.r82.swing.dialogs.newTestDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.testDialog.TestController;
import ru.fssprus.r82.swing.dialogs.testDialog.TestDialog;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.TestingProcess;

public class NewTestController extends CommonController<NewTestDialog> {


	public NewTestController(NewTestDialog dialog) {
		super(dialog);
		loadSpecificationList();
	}

	@Override
	protected void setListeners() {
		dialog.getBtnBegin().addActionListener(this);
		dialog.getBtnCancel().addActionListener(this);
		
		dialog.getCbSpecification().addActionListener(this);
		
	}
	
	private void loadSpecificationList() {
		SpecificationService service = new SpecificationService();
		List<Specification> specList = service.getAll();

		dialog.getCbSpecification().addItem(null);

		for (Specification spec : specList) {
			if(spec.getName().toUpperCase().equals("ОБЩИЕ"))
				continue;
			dialog.getCbSpecification().addItem(spec.getName());
		}
	}
	
	private void doCheckSpecAndLevels() {
		dialog.getRbLevels().forEach((n) -> n.setEnabled(true));
		
		QuestionService qService = new QuestionService();
		SpecificationService sService = new SpecificationService();
		Specification specification = sService.getUniqueByName(String.valueOf(dialog.getCbSpecification().
				getSelectedItem()));
		
		for (int i = 0; i < QuestionLevel.values().length; i++) {
			int qPerLvl = qService.countBySpecificationAndLevel(specification, QuestionLevel.values()[i]);
			
			if(qPerLvl == 0 || qPerLvl < AppConstants.BASE_QUESTS) 
				dialog.getRbLevels().get(i).setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == dialog.getCbSpecification())
			doCheckSpecAndLevels();
		if (e.getSource() == dialog.getBtnBegin())
			doBegin();
		if (e.getSource() == dialog.getBtnCancel())
			doCancel();
	}

	private void doCancel() {
		dialog.dispose();
	}

	private void doBegin() {
		dialog.resetUserInputComponents();
		
		if (!validateFields())
			return;
		
		SpecificationService service = new SpecificationService();

		String userName = dialog.getTfName().getText();
		String userSurname = dialog.getTfSurname().getText();
		String userSecondName = dialog.getTfSecondName().getText();
		String userSelectedSpec = String.valueOf(dialog.getCbSpecification().getSelectedItem());
		int selectedLevel = dialog.getSelectedLevelIndex();
		
		List<Specification> specs = service.getByName(0, 1, userSelectedSpec);
		specs.add(service.getByName(0, 1, "Общие").get(0));
		TestingProcess tp = initNewTestingProcess(specs, selectedLevel);
		fillUserInfo(tp, userName, userSurname, userSecondName);

		new TestController(new TestDialog(1000, 800), specs, tp, selectedLevel);
		dialog.dispose();

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

		if (dialog.getSelectedLevelIndex() == -1) {
			for (JRadioButton lvls : dialog.getRbLevels())
				lvls.setBackground(Color.RED);
			validationPassed = false;
		}

		return validationPassed;

	}

	private void fillUserInfo(TestingProcess testingProcess, String userName, String userSurname,
			String userSecondName) {
		testingProcess.fillUserInfo(userName, userSurname, userSecondName);
	}
	

	private TestingProcess initNewTestingProcess(List<Specification> specs, int level) {

		int amountOfQuestions = 0;
		int commonPercent = 0;

		switch (level) {
		case 0:
			amountOfQuestions = 
			AppConstants.BASE_QUESTS;
			commonPercent = AppConstants.BASE_COMMONS;
			break;
		case 1:
			amountOfQuestions = AppConstants.STANDART_QUESTS;
			commonPercent = AppConstants.STANDART_COMMONS;
			break;
		case 2:
			amountOfQuestions = AppConstants.ADVANCED_QUESTS;
			commonPercent = AppConstants.ADVANCED_COMMONS;
			break;
		case 3:
			amountOfQuestions = AppConstants.RESERVE_QUESTS;
			commonPercent = AppConstants.RESERVE_COMMONS;
			break;
		}
		
		int commonQuestsAmount = (int)Math.round((double)(amountOfQuestions * (commonPercent / 100.0)));
		
		int specQuestsAmount = amountOfQuestions - commonQuestsAmount;
		

		List<Integer> amountQuestionsForSpecs = new ArrayList<Integer>();
		amountQuestionsForSpecs.add(specQuestsAmount);
		amountQuestionsForSpecs.add(commonQuestsAmount);

		TestingProcess testingProcess = new TestingProcess(specs, amountQuestionsForSpecs, getSelectedLevel());

		return testingProcess;
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

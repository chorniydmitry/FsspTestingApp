package ru.fssprus.r82.swing.main.newTestDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.main.testingPanel.ss.TestController;
import ru.fssprus.r82.swing.main.testingPanel.ss.TestDialog;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.TestingProcess;

public class NewTestController implements ActionListener {

	private NewTestDialog view;

	public NewTestController(NewTestDialog view) {
		setView(view);
		loadSpecificationList();
		addActionListeners();
	}

	private void addActionListeners() {
		view.getBtnBegin().addActionListener(this);
		view.getBtnCancel().addActionListener(this);
		
		view.getCbSpecification().addActionListener(this);
	}

	private void loadSpecificationList() {
		SpecificationService service = new SpecificationService();
		List<Specification> specList = service.getAll();

		view.getCbSpecification().addItem(null);

		for (Specification spec : specList) {
			if(spec.getName().toUpperCase().equals("ОБЩИЕ"))
				continue;
			view.getCbSpecification().addItem(spec.getName());
		}
	}
	private void doCheckSpecAndLevels() {
		
		view.getRbLevels().forEach((n) -> n.setEnabled(true));
		
		QuestionService qService = new QuestionService();
		SpecificationService sService = new SpecificationService();
		Specification specification = sService.getUniqueByName(String.valueOf(view.getCbSpecification().
				getSelectedItem()));
		
		for (int i = 0; i < QuestionLevel.values().length; i++) {
			int qPerLvl = qService.countBySpecificationAndLevel(specification, QuestionLevel.values()[i]);
			if(qPerLvl == 0 || qPerLvl < AppConstants.BASE_QUESTS) 
				view.getRbLevels().get(i).setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.getCbSpecification()) {
			doCheckSpecAndLevels();
		}
		if (e.getSource() == view.getBtnBegin())
			doBegin();
		if (e.getSource() == view.getBtnCancel())
			doCancel();
	}

	private void doCancel() {
		view.dispose();
	}

	private void doBegin() {
		view.resetUserInputComponents();
		
		if (!validateFields())
			return;
		
		SpecificationService service = new SpecificationService();

		String userName = view.getTfName().getText();
		String userSurname = view.getTfSurname().getText();
		String userSecondName = view.getTfSecondName().getText();
		String userSelectedSpec = String.valueOf(view.getCbSpecification().getSelectedItem());
		int selectedLevel = view.getSelectedLevelIndex();
		
		List<Specification> specs = service.getByName(0, 1, userSelectedSpec);
		specs.add(service.getByName(0, 1, "Общие").get(0));
		TestingProcess tp = initNewTestingProcess(specs, selectedLevel);
		fillUserInfo(tp, userName, userSurname, userSecondName);

		new TestController(new TestDialog(1000, 800), specs, tp, selectedLevel);
		view.dispose();

	}

	private boolean validateFields() {
		boolean validationPassed = true;

		if (view.getTfName().getText().isEmpty()) {
			view.getTfName().setBackground(Color.RED);
			validationPassed = false;
		}
		if (view.getTfSurname().getText().isEmpty()) {
			view.getTfSurname().setBackground(Color.RED);
			validationPassed = false;
		}
		if (view.getTfSecondName().getText().isEmpty()) {
			view.getTfSecondName().setBackground(Color.RED);
			validationPassed = false;
		}
		Object cbValue = view.getCbSpecification().getSelectedItem();

		if (cbValue == null) {
			view.getCbSpecification().setBackground(Color.RED);
			validationPassed = false;
		}

		if (view.getSelectedLevelIndex() == -1) {
			for (JRadioButton lvls : view.getRbLevels())
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
		ArrayList<JRadioButton> rbs = view.getRbLevels();
		for (int i = 0; i < rbs.size(); i++) {
			if (rbs.get(i).isSelected()) 
				return rbs.get(i).getText();
		}
		return null;
	}

	public NewTestDialog getView() {
		return view;
	}

	public void setView(NewTestDialog view) {
		this.view = view;
	}
}

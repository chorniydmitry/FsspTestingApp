package ru.fssprus.r82.swing.dialogs.newTest;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JRadioButton;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.testingTools.TestProcessBuilder;

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

		QuestionLevel selectedql = QuestionLevel.values()[dialog.getSelectedLevelIndex()];
		
		List<Specification> specs = configureSpecsList();
		
		TestProcessBuilder tpBuilder = new TestProcessBuilder(specs, selectedql, getUser());

		DialogBuilder.showTestDialog(tpBuilder.buildTest());
		
		dialog.dispose();
	}
	
	private User getUser() {
		return new User(dialog.getTfName().getText(), dialog.getTfSurname().getText(), dialog.getTfSecondName().getText());
	}

	private List<Specification> configureSpecsList() {
		
		String selectedSpecName = String.valueOf(dialog.getCbSpecification().getSelectedItem());
		
		Specification selectedSpec = new SpecificationService().getUniqueByName(selectedSpecName);
		Specification commonSpec = new SpecificationService().getUniqueByName(COMMON_TEXT);
		
		List<Specification> specs = new ArrayList<>();
		specs.add(selectedSpec);
		specs.add(commonSpec);

		return specs;
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
		
		ValidatorFactory valFact = Validation.buildDefaultValidatorFactory();
		Validator validator = valFact.getValidator();
		
		Set<ConstraintViolation<User>> nameCons = validator.validateValue(User.class, "name", dialog.getTfName().getText());
		Set<ConstraintViolation<User>> snCons = validator.validateValue(User.class, "surname", dialog.getTfSurname().getText());
		Set<ConstraintViolation<User>> fnCons = validator.validateValue(User.class, "secondName", dialog.getTfSecondName().getText());
		
		if (nameCons.size() > 0) {
			dialog.getTfName().setBackground(Color.RED);
			validationPassed = false;
		}
		if (snCons.size() > 0) {
			dialog.getTfSurname().setBackground(Color.RED);
			validationPassed = false;
		}
		if (fnCons.size() > 0) {
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
		
		valFact.close();

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

}

package ru.fssprus.r82.swing.dialogs.statistics;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.entity.User;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.service.TestService;
import ru.fssprus.r82.service.UserService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.table.TablePanelController;
import ru.fssprus.r82.swing.table.UpdatableController;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.MarkCounter;
import ru.fssprus.r82.utils.TimeUtils;
import ru.fssprus.r82.utils.Utils;

public class StatisticsController extends CommonController<StatisticsDialog> implements UpdatableController {
	private static final int ENTRIES_FOR_PAGE = AppConstants.TABLE_ROWS_LIMIT;
	private static final String FROM_TEXT = " из ";
	private static final String COMMON_TEXT = "Общие";
	private int currentPage;
	private int totalPages;

	private List<Test> testsOnScreenList;

	public StatisticsController(StatisticsDialog dialog) {
		super(dialog);
		initCbs();
		dialog.getTabPanel().getBtnAdd().setEnabled(false);

		TablePanelController tablePanelController = new TablePanelController(dialog.getTabPanel());
		tablePanelController.setSubscriber(this);

		updateTable();
	}

	@Override
	protected void setListeners() {
		dialog.getBtnClearFilters().addActionListener(listener -> doClearFiltersAction());
		dialog.getBtnFilter().addActionListener(listener -> updateTable());

		dialog.getTfDateMore().addFocusListener(new DateTextFieldsFocusListener());
		dialog.getTfDateMore().addActionListener(listener -> updateAndFormatDateFields(dialog.getTfDateMore()));

		dialog.getTfDateLess().addFocusListener(new DateTextFieldsFocusListener());
		dialog.getTfDateLess().addActionListener(listener -> updateAndFormatDateFields(dialog.getTfDateLess()));

	}
	
	private void initCbs() {
		initCbLevels();
		initCbMarks();
		initCbSpecs();
	}
	
	private void initCbLevels() {
		dialog.getCbLevel().addItem(null);
		for(QuestionLevel item: QuestionLevel.values())
			dialog.getCbLevel().addItem(item);
	}
	
	private void initCbMarks() {
		dialog.getCbMarks().addItem(null);
		for(String mark: MarkCounter.getAllMarksWords())
			dialog.getCbMarks().addItem(mark);
	}
	
	private void initCbSpecs() {
			SpecificationService service = new SpecificationService();
			List<Specification> specList = service.getAll();

			dialog.getCbSpecs().addItem(null);

			for (Specification spec : specList) {
				if (spec.getName().toUpperCase().equals(COMMON_TEXT.toUpperCase()))
					continue;
				dialog.getCbSpecs().addItem(spec.getName());
			}
	}

	private void doFilter() {
		TestService testService = new TestService();
		UserService userService = new UserService();
		SpecificationService specService = new SpecificationService();

		Set<User> users = null;
		if (!dialog.getTfSurNamLast().getText().isEmpty())
			users = new HashSet<>(userService.getBySurname(dialog.getTfSurNamLast().getText()));

		Set<Specification> specs = null;
		if (dialog.getCbSpecs().getSelectedIndex() != 0)
			specs = new HashSet<>(specService.getByName(dialog.getCbSpecs().getSelectedItem().toString()));

		QuestionLevel level = null;

		if ((dialog.getCbLevel().getSelectedItem() != null)) {
			level = (QuestionLevel.valueOf(dialog.getCbLevel().getSelectedItem().toString()));
		}
		
		Date dateMore = TimeUtils.getDate(dialog.getTfDateMore().getText());

		Date dateLess = TimeUtils.getDate(dialog.getTfDateLess().getText());

		String result = dialog.getCbMarks().getSelectedItem().toString();

		String scoreMoreText = dialog.getTfScoreMore().getText();
		String scoreLessText = dialog.getTfScoreLess().getText();

		int scoreMore = Utils.isNumeric(scoreMoreText) ? Integer.parseInt(scoreMoreText) : 0;
		int scoreLess = Utils.isNumeric(scoreLessText) ? Integer.parseInt(scoreLessText) : 0;

		int start = currentPage * ENTRIES_FOR_PAGE;
		int max = ENTRIES_FOR_PAGE;

		testsOnScreenList = testService.getByUserSpecifiactionLevelAndDate(start, max, users, specs, level, dateMore,
				dateLess, result, scoreMore, scoreLess);
	}

	private void updateAndFormatDateFields(Object tf) {
		JTextField tfToUpdate = (JTextField) tf;
		String dateMore = tfToUpdate.getText();

		if (!dateMore.isEmpty())
			dateMore = TimeUtils.convertToStandart(dateMore);

		if (dateMore != null && TimeUtils.getDate(dateMore) != null)
			tfToUpdate.setText(dateMore);
		else
			tfToUpdate.setText("");
	}

	private void doClearFiltersAction() {

		dialog.getTfSurNamLast().setText(null);
		dialog.getCbSpecs().setSelectedIndex(0);
		dialog.getCbLevel().setSelectedIndex(0);
		dialog.getCbMarks().setSelectedIndex(0);
		dialog.getTfDateLess().setText(null);
		dialog.getTfDateMore().setText(null);
		dialog.getTfScoreLess().setText(null);
		dialog.getTfScoreMore().setText(null);

		updateTable();
	}

	public void convertAndAddToTable(List<Test> tests) {
		for (int i = 0; i < tests.size(); i++) {
			Test test = tests.get(i);

			String userName = test.getUser().getSurname() + " " + test.getUser().getName() + " "
					+ test.getUser().getSecondName();
			String spec = test.getSpecification().getName();
			QuestionLevel lvl = test.getLevel();

			Date testDate = test.getDate();

			String date = AppConstants.STATDIALOG_TABLE_DATE_FORMAT.format(testDate);

			int testSecs = test.getTestingTime();

			String testTime = String.valueOf(TimeUtils.stringTimes(testSecs));

			String corrects = String.valueOf(test.getCorrectAnswers());
			String percent = String.valueOf(test.getScore());
			String result = test.getResult();

			Object[] row = { i + 1, userName, spec, lvl, date, testTime, corrects, percent, result };

			dialog.getTableModel().setRow(row, i);
			dialog.getTableModel().setRowColor(i,
					MarkCounter.countInColors(test.getTotalQuestions(), test.getCorrectAnswers()));

			dialog.getTableModel().update();
		}
	}

	private void updateTable() {
		dialog.getTableModel().clearTable();

		doFilter();
		
		int totalEntries = testsOnScreenList.size();

		dialog.getTabPanel().getTfPage().setText(String.valueOf(currentPage + 1));
		dialog.getTabPanel().getLblPagesTotal().setText(FROM_TEXT + countTotalPages(totalEntries));

		
		convertAndAddToTable(testsOnScreenList);

	}

	private int countTotalPages(int amountOfQuestions) {
		this.totalPages = amountOfQuestions / ENTRIES_FOR_PAGE + 1;
		return totalPages;
	}

	@Override
	public void edit(int index) {
	}

	@Override
	public void nextPage() {
		if (currentPage + 1 < totalPages)
			currentPage++;
		updateTable();
	}

	@Override
	public void previousPage() {
		if (currentPage > 0)
			currentPage--;
		updateTable();
	}

	@Override
	public void delete(int index) {
		if (MessageBox.showConfirmQuestionDelete(dialog)) {
			TestService service = new TestService();
			service.delete(testsOnScreenList.get(index));
		}
		updateTable();

	}

	@Override
	public void goToPage(int page) {
		if (page <= totalPages && page > 0)
			currentPage = page - 1;

		updateTable();
	}

	public class DateTextFieldsFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			updateAndFormatDateFields(e.getSource());
		}

	}

}

package ru.fssprus.r82.swing.dialogs.statisticsDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.service.TestService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.table.TablePanelController;
import ru.fssprus.r82.swing.table.UpdatableController;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.MarkCounter;
import ru.fssprus.r82.utils.TimeConverter;

public class StatisticsController extends CommonController<StatisticsDialog> implements UpdatableController {
	private static final int ENTRIES_FOR_PAGE = AppConstants.TABLE_ROWS_LIMIT;
	private int currentPage;
	private int totalPages;

	private List<Test> testsOnScreenList;
	public StatisticsController(StatisticsDialog dialog) {
		super(dialog);
		dialog.getTabPanel().getBtnAdd().setEnabled(false);
		
		TablePanelController tablePanelController = new TablePanelController(dialog.getTabPanel());
		tablePanelController.setSubscriber(this);

		updateTable();
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
	}

	public void convertAndAddToTable(List<Test> tests) {
		for (int i = 0; i < tests.size(); i++) {
			Test test = tests.get(i);

			String userName = test.getUser().getSurname() + " " + test.getUser().getName() + " "
					+ test.getUser().getSecondName();
			String spec = test.getSpecification().getName();
			String lvl = test.getLevel();

			Date testDate = test.getDate();

			String pattern = "dd MMMMM yyyy HH:mm";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("ru", "RU"));

			String date = simpleDateFormat.format(testDate);

			int testSecs = test.getTestingTime();

			String testTime = String.valueOf(TimeConverter.stringTimes(testSecs));

			String corrects = String.valueOf(test.getCorrectAnswers());
			String percent = String.valueOf(test.getScore());
			String result = test.getResult();

			Object[] row = { i + 1, userName, spec, lvl, date, testTime, corrects, percent, result };
			
			dialog.getTableModel().setRow(row, i);
			dialog.getTableModel().setRowColor(i, MarkCounter.countInColors(test.getTotalQuestions(), test.getCorrectAnswers()));

			dialog.getTableModel().update();
		}
	}

	private void updateTable() {
		TestService testService = new TestService();

		int totalEntries = testService.countAll();

		int start = currentPage * ENTRIES_FOR_PAGE;
		int max = ENTRIES_FOR_PAGE;

		testsOnScreenList = testService.getAll(start, max);
		
		dialog.getTabPanel().getTfPage().setText(String.valueOf(currentPage + 1));
		dialog.getTabPanel().getLblPagesTotal().setText(" из " + countTotalPages(totalEntries));

		dialog.getTableModel().clearTable();
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

}

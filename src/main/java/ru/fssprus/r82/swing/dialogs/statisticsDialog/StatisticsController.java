package ru.fssprus.r82.swing.dialogs.statisticsDialog;

import java.awt.event.ActionEvent;
import java.util.List;

import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.service.TestService;
import ru.fssprus.r82.swing.dialogs.CommonController;

public class StatisticsController extends CommonController<StatisticsDialog> {
	
	public StatisticsController(StatisticsDialog dialog) {
		super(dialog);
		loadTestsFromDB();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		
	}
	
	private void loadTestsFromDB() {
		TestService testService = new TestService();
		List<Test> tests = testService.getAll(0, 25);
		dialog.getTabStat().addTests(tests);
		//statDialog.getTabStat().
	}


}

package ru.fssprus.r82.swing.main.statisticsDialog;

import java.util.List;

import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.service.TestService;

public class StatisticsController {
	private StatisticsDialog statDialog;
	
	public StatisticsController(StatisticsDialog statDialog) {
		this.statDialog = statDialog;
		loadTestsFromDB();
	}
	
	private void loadTestsFromDB() {
		TestService testService = new TestService();
		List<Test> tests = testService.getAll(0, 25);
		statDialog.getTabStat().addTests(tests);
		//statDialog.getTabStat().
	}
}

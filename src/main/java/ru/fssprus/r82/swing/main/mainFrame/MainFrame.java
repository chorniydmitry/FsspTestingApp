package ru.fssprus.r82.swing.main.mainFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.utils.AppConstants;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -354084726011189758L;
	
	private static final String BTN_TESTING_CAPTION = "Тестирование";
	private static final String BTN_ADMIN_CAPTION = "Администрирование";
	private static final String BTN_STATISTICS_CAPTION = "Статистика";
	private static final String BTN_EXIT_CAPTION = "Выход";

	private JButton btnTest = new JButton(BTN_TESTING_CAPTION);
	private JButton btnAdmin = new JButton(BTN_ADMIN_CAPTION);
	private JButton btnStatistics = new JButton(BTN_STATISTICS_CAPTION);
	private JButton btnExit = new JButton(BTN_EXIT_CAPTION);

	private Dimension dimButtonSize = new Dimension(
			AppConstants.MAINFRAME_BTN_WIDTHS,
			AppConstants.MAINFRAME_BTN_HEIGHTS);

	public MainFrame() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new FlowLayout());

		setButtonsSizes();

		addComponents();

		addListeners();

		setVisible(true);
	}

	private void addComponents() {
		add(btnTest);
		add(btnStatistics);
		add(btnAdmin);
		add(btnExit);
	}

	private void setButtonsSizes() {
		btnTest.setPreferredSize(dimButtonSize);
		btnStatistics.setPreferredSize(dimButtonSize);
		btnAdmin.setPreferredSize(dimButtonSize);
		btnExit.setPreferredSize(dimButtonSize);
	}
	
	private void initNewTestDialog() {
		DialogBuilder.showNewTestDialog();
	}
	
	private void initAdminDialog() {
		DialogBuilder.showAdminDialog();
	}
	
	private void initStatisticsDialog() {
		DialogBuilder.showStatisticsDialog();
	}

	private void addListeners() {
		btnTest.addActionListener(listener -> initNewTestDialog());
		btnAdmin.addActionListener(listener -> initAdminDialog());
		btnStatistics.addActionListener(listener -> initStatisticsDialog());

		btnExit.addActionListener(listener-> System.exit(0));
	}
}

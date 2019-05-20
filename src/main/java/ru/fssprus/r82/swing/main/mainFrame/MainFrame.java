package ru.fssprus.r82.swing.main.mainFrame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import ru.fssprus.r82.swing.main.newTestDialog.NewTestController;
import ru.fssprus.r82.swing.main.newTestDialog.NewTestDialog;
import ru.fssprus.r82.swing.main.settingsDialog.SettingsController;
import ru.fssprus.r82.swing.main.settingsDialog.SettingsDialog;
import ru.fssprus.r82.swing.main.statisticsDialog.StatisticsController;
import ru.fssprus.r82.swing.main.statisticsDialog.StatisticsDialog;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -354084726011189758L;
	private JButton btnTest = new JButton("Тестирование");
	private JButton btnSetting = new JButton("Настройки");
	private JButton btnStatistics = new JButton("Статистика");
	private JButton btnExit = new JButton("Выход");
	
	public MainFrame() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new FlowLayout());
		
		add(btnTest);
		add(btnSetting);
		add(btnStatistics);
		add(btnExit);
		
		addListeners();
		
		setVisible(true);
	}
	
	private void addListeners() {
		btnTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewTestDialog newTestDialog = new NewTestDialog(500, 300);
				new NewTestController(newTestDialog);
			}
		});
		
		btnSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsDialog sd = new SettingsDialog(800, 600);
				new SettingsController(sd);
			}
		});
		
		btnStatistics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StatisticsController(new StatisticsDialog(900, 600));
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
	}
}

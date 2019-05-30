package ru.fssprus.r82.swing.main.mainFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import ru.fssprus.r82.swing.dialogs.adminDialog.AdminController;
import ru.fssprus.r82.swing.dialogs.adminDialog.AdminDialog;
import ru.fssprus.r82.swing.dialogs.newTestDialog.NewTestController;
import ru.fssprus.r82.swing.dialogs.newTestDialog.NewTestDialog;
import ru.fssprus.r82.swing.dialogs.statisticsDialog.StatisticsController;
import ru.fssprus.r82.swing.dialogs.statisticsDialog.StatisticsDialog;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -354084726011189758L;
	private JButton btnTest = new JButton("Тестирование");
	//private JButton btnSetting = new JButton("Настройки");
	//private JButton btnQuestionsList = new JButton("Список вопросов");
	private JButton btnAdmin = new JButton("Администрирование");
	private JButton btnStatistics = new JButton("Статистика");
	private JButton btnExit = new JButton("Выход");
	
	private Dimension dimButtonSize = new Dimension(175, 75);
	public MainFrame() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new FlowLayout());
		
		btnTest.setPreferredSize(dimButtonSize);
		btnStatistics.setPreferredSize(dimButtonSize);
		btnAdmin.setPreferredSize(dimButtonSize);
		btnExit.setPreferredSize(dimButtonSize);
		
		add(btnTest);
		add(btnStatistics);
		add(btnAdmin);
		add(btnExit);
		
		addListeners();
		
		setVisible(true);
	}
	
	private void addListeners() {
		btnTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewTestDialog newTestDialog = new NewTestDialog(600, 300);
				new NewTestController(newTestDialog);
			}
		});
		
		btnAdmin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDialog adminDialog = new AdminDialog(300,200);
				new AdminController(adminDialog);
				
			}
		});
		
		btnStatistics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StatisticsController(new StatisticsDialog(1024, 600));
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

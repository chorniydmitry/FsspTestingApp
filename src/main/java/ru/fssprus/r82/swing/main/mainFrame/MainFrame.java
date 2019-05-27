package ru.fssprus.r82.swing.main.mainFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import ru.fssprus.r82.service.PasswordService;
import ru.fssprus.r82.swing.main.MessageBox;
import ru.fssprus.r82.swing.main.newTestDialog.NewTestController;
import ru.fssprus.r82.swing.main.newTestDialog.NewTestDialog;
import ru.fssprus.r82.swing.main.questionListDialog.QuestionListController;
import ru.fssprus.r82.swing.main.questionListDialog.QuestionListDialog;
import ru.fssprus.r82.swing.main.settingsDialog.SettingsController;
import ru.fssprus.r82.swing.main.settingsDialog.SettingsDialog;
import ru.fssprus.r82.swing.main.statisticsDialog.StatisticsController;
import ru.fssprus.r82.swing.main.statisticsDialog.StatisticsDialog;
import ru.fssprus.r82.utils.CryptWithMD5;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -354084726011189758L;
	private JButton btnTest = new JButton("Тестирование");
	private JButton btnSetting = new JButton("Настройки");
	private JButton btnQuestionsList = new JButton("Список вопросов");
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
		btnSetting.setPreferredSize(dimButtonSize);
		btnQuestionsList.setPreferredSize(dimButtonSize);
		btnExit.setPreferredSize(dimButtonSize);
		
		add(btnTest);
		add(btnStatistics);
		add(btnSetting);
		add(btnQuestionsList);
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
		
		btnSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PasswordService passService = new PasswordService();
				
				String inputedPass = MessageBox.showInputPasswordDialog(MainFrame.this);
				
				boolean accessAllowed = passService.checkPassword("SETTINGS", 
						CryptWithMD5.cryptWithMD5(inputedPass));
				
				if(accessAllowed) {
					SettingsDialog sd = new SettingsDialog(800, 600);
					new SettingsController(sd);
				}
			}
		});
		
		btnStatistics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StatisticsController(new StatisticsDialog(1024, 600));
			}
		});
		
		btnQuestionsList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new QuestionListController(new QuestionListDialog(1024,768));
				
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

package ru.fssprus.r82.swing.main.resultingDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.fssprus.r82.swing.main.wrongAnswersDialog.WrongAnswersController;
import ru.fssprus.r82.swing.main.wrongAnswersDialog.WrongAnswersDialog;
import ru.fssprus.r82.utils.TestingProcess;

public class ResultingController implements ActionListener{
	private ResultingDialog resDialog;
	private TestingProcess testingProcess;
	
	public ResultingController(ResultingDialog resDialog, TestingProcess testingProcess) {
		this.resDialog = resDialog;
		this.testingProcess = testingProcess;
		setListeners();
	}
	
	private void setListeners() {
		resDialog.getBtnShowWrongs().addActionListener(this);
		resDialog.getBtnClose().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resDialog.getBtnShowWrongs())
			doShowWrongs();
		if(e.getSource() == resDialog.getBtnClose())
			doClose();
		
	}
	
	private void doShowWrongs() {
		WrongAnswersController waController = 
				new WrongAnswersController(new WrongAnswersDialog(800, 600,"Результаты теста"));
		
		waController.setText(testingProcess.showWrongs());
		waController.startCountdown();
		
		resDialog.dispose();
	}
	
	private void doClose() {
		resDialog.dispose();
		
	}

}

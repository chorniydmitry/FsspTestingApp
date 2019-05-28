package ru.fssprus.r82.swing.main.adminDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.fssprus.r82.service.PasswordService;
import ru.fssprus.r82.swing.main.MessageBox;
import ru.fssprus.r82.swing.main.passwordManageDialog.PasswordManageController;
import ru.fssprus.r82.swing.main.passwordManageDialog.PasswordManageDialog;
import ru.fssprus.r82.swing.main.questionListDialog.QuestionListController;
import ru.fssprus.r82.swing.main.questionListDialog.QuestionListDialog;
import ru.fssprus.r82.swing.main.settingsDialog.SettingsController;
import ru.fssprus.r82.swing.main.settingsDialog.SettingsDialog;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.CryptWithMD5;

public class AdminController implements ActionListener {
	private AdminDialog adminDialog;

	public AdminController(AdminDialog adminDialog) {
		this.adminDialog = adminDialog;
		adminDialog.init();
		setListeners();
	}

	private void setListeners() {
		adminDialog.getBtnPasswords().addActionListener(this);
		adminDialog.getBtnQuestionEdit().addActionListener(this);
		adminDialog.getBtnQuestionLoad().addActionListener(this);
		adminDialog.getBtnSettings().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adminDialog.getBtnPasswords())
			doOpenManagePasswordsDialog();
		if (e.getSource() == adminDialog.getBtnQuestionEdit())
			doOpenQuestionEditDialog();
		if (e.getSource() == adminDialog.getBtnQuestionLoad())
			doOpenQuestionLoagingDialog();
		if (e.getSource() == adminDialog.getBtnSettings())
			doOpenSettingsDialog();

	}
	
	private boolean checkIfPasswordIsSet(String section) {
		PasswordService service = new PasswordService();
		if(service.passwordIsSet(section) > 0)
			return true;
		return false;
	}

	private void doOpenSettingsDialog() {
		if(checkIfPasswordIsSet(AppConstants.SETTINGS_SECTION)) {
			PasswordService passService = new PasswordService();
			
			String inputedPass = MessageBox.showInputPasswordDialog(adminDialog);
			
			boolean accessAllowed = passService.checkPassword(AppConstants.SETTINGS_SECTION, 
					CryptWithMD5.cryptWithMD5(inputedPass));
			
			if(accessAllowed) {
				SettingsDialog sd = new SettingsDialog(800, 600);
				new SettingsController(sd);
			}
		} else {
			SettingsDialog sd = new SettingsDialog(800, 600);
			new SettingsController(sd);
		}
		
	}

	private void doOpenQuestionLoagingDialog() {
		if(checkIfPasswordIsSet(AppConstants.QUESTION_LOAD_SECTION)) {
			PasswordService passService = new PasswordService();
			
			String inputedPass = MessageBox.showInputPasswordDialog(adminDialog);
			
			boolean accessAllowed = passService.checkPassword(AppConstants.QUESTION_LOAD_SECTION, 
					CryptWithMD5.cryptWithMD5(inputedPass));
			
			if(accessAllowed) {
				new QuestionListController(new QuestionListDialog(1024,768));
			}
		} else {
			new QuestionListController(new QuestionListDialog(1024,768));
		}
		
	}

	private void doOpenQuestionEditDialog() {
		if(checkIfPasswordIsSet(AppConstants.QUESTION_EDIT_SECTION)) {
			PasswordService passService = new PasswordService();
			
			String inputedPass = MessageBox.showInputPasswordDialog(adminDialog);
			
			boolean accessAllowed = passService.checkPassword(AppConstants.QUESTION_EDIT_SECTION, 
					CryptWithMD5.cryptWithMD5(inputedPass));
			
			if(accessAllowed) {
				new QuestionListController(new QuestionListDialog(1024,768));
			}
		} else {
			new QuestionListController(new QuestionListDialog(1024,768));
		}
		
	}

	private void doOpenManagePasswordsDialog() {
		if(checkIfPasswordIsSet(AppConstants.MANAGE_PASSWORDS_SECTION)) {
			PasswordService passService = new PasswordService();
			
			String inputedPass = MessageBox.showInputPasswordDialog(adminDialog);
			
			boolean accessAllowed = passService.checkPassword(AppConstants.MANAGE_PASSWORDS_SECTION, 
					CryptWithMD5.cryptWithMD5(inputedPass));
			
			if(accessAllowed) {
				new PasswordManageController(new PasswordManageDialog(1024,768));
			}
		} else {
			new PasswordManageController(new PasswordManageDialog(1024,768));
		}
		
	}
}

package ru.fssprus.r82.swing.dialogs.adminDialog;

import java.awt.event.ActionEvent;

import ru.fssprus.r82.service.PasswordService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.addingSetDialog.AddingQuestionSetController;
import ru.fssprus.r82.swing.dialogs.addingSetDialog.AddingQuestionSetDialog;
import ru.fssprus.r82.swing.dialogs.passwordManageDialog.PasswordManageController;
import ru.fssprus.r82.swing.dialogs.passwordManageDialog.PasswordManageDialog;
import ru.fssprus.r82.swing.dialogs.questionListDialog.QuestionListController;
import ru.fssprus.r82.swing.dialogs.questionListDialog.QuestionListDialog;
import ru.fssprus.r82.swing.dialogs.settingsDialog.SettingsController;
import ru.fssprus.r82.swing.dialogs.settingsDialog.SettingsDialog;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.CryptWithMD5;

public class AdminController extends CommonController<AdminDialog> {

	public AdminController(AdminDialog dialog) {
		super(dialog);
	}
	
	@Override
	protected void setListeners() {
		dialog.getBtnPasswords().addActionListener(this);
		dialog.getBtnQuestionEdit().addActionListener(this);
		dialog.getBtnQuestionLoad().addActionListener(this);
		dialog.getBtnSettings().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dialog.getBtnPasswords())
			doOpenManagePasswordsDialog();
		if (e.getSource() == dialog.getBtnQuestionEdit())
			doOpenQuestionEditDialog();
		if (e.getSource() == dialog.getBtnQuestionLoad())
			doOpenQuestionLoagingDialog();
		if (e.getSource() == dialog.getBtnSettings())
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
			
			String inputedPass = MessageBox.showInputPasswordDialog(dialog);
			
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
			
			String inputedPass = MessageBox.showInputPasswordDialog(dialog);
			
			boolean accessAllowed = passService.checkPassword(AppConstants.QUESTION_LOAD_SECTION, 
					CryptWithMD5.cryptWithMD5(inputedPass));
			
			if(accessAllowed) {
				new AddingQuestionSetController(new AddingQuestionSetDialog(1024,200));
			}
		} else {
			new AddingQuestionSetController(new AddingQuestionSetDialog(1024,200));
		}
		
	}

	private void doOpenQuestionEditDialog() {
		if(checkIfPasswordIsSet(AppConstants.QUESTION_EDIT_SECTION)) {
			PasswordService passService = new PasswordService();
			
			String inputedPass = MessageBox.showInputPasswordDialog(dialog);
			
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
			
			String inputedPass = MessageBox.showInputPasswordDialog(dialog);
			
			boolean accessAllowed = passService.checkPassword(AppConstants.MANAGE_PASSWORDS_SECTION, 
					CryptWithMD5.cryptWithMD5(inputedPass));
			
			if(accessAllowed) {
				new PasswordManageController(new PasswordManageDialog(600,300));
			}
		} else {
			new PasswordManageController(new PasswordManageDialog(600,300));
		}
		
	}
}

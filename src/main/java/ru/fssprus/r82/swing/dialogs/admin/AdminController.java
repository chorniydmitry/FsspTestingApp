package ru.fssprus.r82.swing.dialogs.admin;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.addingSet.LoadingQuestionSetController;
import ru.fssprus.r82.swing.dialogs.addingSet.LoadingQuestionSetDialog;
import ru.fssprus.r82.swing.dialogs.config.ConfigController;
import ru.fssprus.r82.swing.dialogs.config.ConfigDialog;
import ru.fssprus.r82.swing.dialogs.passwordManage.PasswordManageController;
import ru.fssprus.r82.swing.dialogs.passwordManage.PasswordManageDialog;
import ru.fssprus.r82.swing.dialogs.questionList.QuestionListController;
import ru.fssprus.r82.swing.dialogs.questionList.QuestionListDialog;
import ru.fssprus.r82.utils.AppConstants;

public class AdminController extends CommonController<AdminDialog> {

	public AdminController(AdminDialog dialog) {
		super(dialog);
	}

	@Override
	protected void setListeners() {
		dialog.getBtnPasswords().addActionListener(listener -> doOpenManagePasswordsDialog());
		dialog.getBtnQuestionEdit().addActionListener(listener -> doOpenQuestionEditDialog());
		dialog.getBtnQuestionLoad().addActionListener(listener -> doOpenQuestionLoagingDialog());
		dialog.getBtnSettings().addActionListener(listener -> doOpenSettingsDialog());
	}

	private void doOpenSettingsDialog() {
		new ConfigController(new ConfigDialog(
				AppConstants.DIALOG_CONFIG_WIDTH, 
				AppConstants.DIALOG_CONFIG_HEIGHT));

	}

	private void doOpenQuestionLoagingDialog() {
		new LoadingQuestionSetController(new LoadingQuestionSetDialog(
				AppConstants.DIALOG_LOADING_QUESTION_SET_WIDTH, 
				AppConstants.DIALOG_LOADING_QUESTION_SET_HEIGHT));
	}

	private void doOpenQuestionEditDialog() {
		new QuestionListController(new QuestionListDialog(
				AppConstants.DIALOG_QUESTUIN_EDIT_WIDTH, 
				AppConstants.DIALOG_QUESTUIN_EDIT_HEIGHT));

	}

	private void doOpenManagePasswordsDialog() {
		new PasswordManageController(new PasswordManageDialog(
				AppConstants.DIALOG_MANAGE_PASSWORDS_WIDTH, 
				AppConstants.DIALOG_MANAGE_PASSWORDS_HEIGHT));

	}
}

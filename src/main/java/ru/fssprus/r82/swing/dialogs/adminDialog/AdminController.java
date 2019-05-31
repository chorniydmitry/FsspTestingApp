package ru.fssprus.r82.swing.dialogs.adminDialog;

import java.awt.event.ActionEvent;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.addingSetDialog.LoadingQuestionSetController;
import ru.fssprus.r82.swing.dialogs.addingSetDialog.LoadingQuestionSetDialog;
import ru.fssprus.r82.swing.dialogs.passwordManageDialog.PasswordManageController;
import ru.fssprus.r82.swing.dialogs.passwordManageDialog.PasswordManageDialog;
import ru.fssprus.r82.swing.dialogs.questionListDialog.QuestionListController;
import ru.fssprus.r82.swing.dialogs.questionListDialog.QuestionListDialog;
import ru.fssprus.r82.swing.dialogs.settingsDialog.ConfigController;
import ru.fssprus.r82.swing.dialogs.settingsDialog.ConfigDialog;

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

	private void doOpenSettingsDialog() {
		new ConfigController(new ConfigDialog(800, 600));

	}

	private void doOpenQuestionLoagingDialog() {
		new LoadingQuestionSetController(new LoadingQuestionSetDialog(600, 200));
	}

	private void doOpenQuestionEditDialog() {
		new QuestionListController(new QuestionListDialog(1024, 768));

	}

	private void doOpenManagePasswordsDialog() {
		new PasswordManageController(new PasswordManageDialog(600, 300));

	}
}

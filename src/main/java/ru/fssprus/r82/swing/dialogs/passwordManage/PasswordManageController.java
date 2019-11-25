package ru.fssprus.r82.swing.dialogs.passwordManage;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.fssprus.r82.entity.Password;
import ru.fssprus.r82.service.PasswordService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.CryptWithMD5;

/**
 * @author Chernyj Dmitry
 *
 */
public class PasswordManageController extends CommonController<PasswordManageDialog> implements DocumentListener {

	public PasswordManageController(PasswordManageDialog dialog) {
		super(dialog);
		loadSetPasswords();
	}

	private void loadSetPasswords() {
		PasswordService passService = new PasswordService();
		List<Password> passwordsSet = passService.getAll();

		for (Password pass : passwordsSet) {
			for (int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++) {
				if (pass.getSectionName().equals(dialog.getPfList().get(i).getName())) {
					dialog.getPfList().get(i).setText(AppConstants.PASSWORD_IS_SET_DEFAULT_MASK);
					dialog.getBtnList().get(i).setEnabled(false);
				}
			}
		}
	}
	
	private void doAction(ActionEvent e) {
		for (int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++) {
			if (e.getSource() == dialog.getBtnList().get(i)) {
				String section = dialog.getBtnList().get(i).getName();
				int passLength = dialog.getPfList().get(i).getPassword().length;
				String passwordSetMD5 = CryptWithMD5.cryptWithMD5(dialog.getPfList().get(i).getPassword());

				PasswordService passService = new PasswordService();

				if (passService.passwordIsSet(section) > 0) {
					if (passLength == 0) {
						passService.delete(passService.getBySection(section));
					} else {
						passService.update(section, passwordSetMD5);
					}
				} else {
					setNewPassword(section, passwordSetMD5, passService);
				}
				dialog.getBtnList().get(i).setEnabled(false);
			}
		}
	}

	private void setNewPassword(String section, String passwordSetMD5, PasswordService passService) {
		Password pass = new Password();
		pass.setSectionName(section);
		pass.setPasswordMD5(passwordSetMD5);
		passService.save(pass);
	}


	@Override
	protected void setListeners() {
		for (int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++) {
			dialog.getBtnList().get(i).addActionListener(listener -> doAction(listener));
			dialog.getPfList().get(i).getDocument().addDocumentListener(this);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		for (int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++)
			if (e.getDocument() == dialog.getPfList().get(i).getDocument())
				dialog.getBtnList().get(i).setEnabled(true);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		for (int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++)
			if (e.getDocument() == dialog.getPfList().get(i).getDocument())
				dialog.getBtnList().get(i).setEnabled(true);

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		for (int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++)
			if (e.getDocument() == dialog.getPfList().get(i).getDocument())
				dialog.getBtnList().get(i).setEnabled(true);
	}

}

package ru.fssprus.r82.swing.dialogs.passwordManageDialog;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.fssprus.r82.entity.Password;
import ru.fssprus.r82.service.PasswordService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.CryptWithMD5;

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
					dialog.getPfList().get(i).setText("*****");
					dialog.getBtnList().get(i).setEnabled(false);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
					Password pass = new Password();
					pass.setSectionName(section);
					pass.setPasswordMD5(passwordSetMD5);
					passService.save(pass);
				}
				dialog.getBtnList().get(i).setEnabled(false);
			}
		}
	}

	@Override
	protected void setListeners() {
		for (int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++) {
			dialog.getBtnList().get(i).addActionListener(this);
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

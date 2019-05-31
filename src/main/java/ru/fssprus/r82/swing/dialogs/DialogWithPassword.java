package ru.fssprus.r82.swing.dialogs;

import ru.fssprus.r82.service.PasswordService;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.CryptWithMD5;

public abstract class DialogWithPassword extends CommonDialog {
	private static final long serialVersionUID = -3351598510558332393L;

	public DialogWithPassword(int width, int height) {
		super(width, height);
		if(checkIfPasswordIsSet(getSection())) {
			if(checkaccess(getSection())) {
				accesGained = true;
			}
		} else {
			accesGained = true;
		}
	}
	
	private boolean checkaccess(String section) {
		PasswordService passService = new PasswordService();

		String inputedPass = MessageBox.showInputPasswordDialog(this);

		boolean accessAllowed = passService.checkPassword(getSection(),
				CryptWithMD5.cryptWithMD5(inputedPass));

		return accessAllowed;

	}

	private boolean checkIfPasswordIsSet(String section) {
		PasswordService service = new PasswordService();
		if (service.passwordIsSet(section) > 0)
			return true;
		return false;
	}

}
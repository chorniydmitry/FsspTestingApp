package ru.fssprus.r82.swing.dialogs.passwordManage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.utils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;

/**
 * @author Chernyj Dmitry
 *
 */
public class PasswordManageDialog extends DialogWithPassword {
	private static final long serialVersionUID = -4879337467270588965L;
	
	private static final String SECTION = AppConstants.MANAGE_PASSWORDS_DIALOG;
	private static final String TITLE = AppConstants.MANAGE_PASSWORDS_TEXT;
	private static final String ICON = AppConstants.MANAGE_PASSWORDS_ICON;
	
	private static final String CAPT_CHANGE = "Сменить";

	private JLabel lblInfo = new JLabel(AppConstants.DIALOG_PASSOWRD_MANAGE_ABOUT_INFO);
	
	private ArrayList<JLabel> lblList = new ArrayList<>();
	private ArrayList<JPasswordField> pfList = new ArrayList<>();
	private ArrayList<JGreenButton> btnList = new ArrayList<>();

	public PasswordManageDialog(int width, int height, JFrame parent) {
		super(width, height, parent);
	}
	
	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}
	
	@Override
	public void init() {
		initAndFillComponentsList();
		super.init();
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}
	
	@Override
	protected String getTitleText() {
		return TITLE;
	}
	
	private void initAndFillComponentsList() {
		for(int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++) {
			JLabel lbl = new JLabel(AppConstants.DIALOG_WITH_PASSWORDS_TEXT_ARR[i]);
			lblList.add(lbl);
			
			JPasswordField pf = new JPasswordField();
			pf.setName(AppConstants.DIALOG_WITH_PASSWORDS_ARR[i]);
			pfList.add(pf);
			
			JGreenButton btn = new JGreenButton(CAPT_CHANGE);
			btn.setName(AppConstants.DIALOG_WITH_PASSWORDS_ARR[i]);
			btn.setEnabled(false);
			btnList.add(btn);
		}
	}
	

	@Override
	protected void layoutDialog() {
		JPanel contentPanel = getContentPanel();
		contentPanel.setLayout(new GridBagLayout());

		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
		// insets(top, left, botom, right), ipadx, ipady

		contentPanel.add(lblInfo, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

		for(int i = 0; i < AppConstants.SECTIONS_AMOUNT; i++) {
			contentPanel.add(lblList.get(i), new GridBagConstraints(0, (i + 1), 1, 1, 0.2, 1, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(2, 10, 10, 2), 0, 0));

			contentPanel.add(pfList.get(i), new GridBagConstraints(1, (i + 1), 1, 1, 0.5, 1, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

			contentPanel.add(btnList.get(i), new GridBagConstraints(2, (i + 1), 1, 1, 0.2, 1, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(2, 10, 10, 2), 0, 0));
		}

	}

	public ArrayList<JGreenButton> getBtnList() {
		return btnList;
	}

	public void setBtnList(ArrayList<JGreenButton> btnList) {
		this.btnList = btnList;
	}

	public ArrayList<JPasswordField> getPfList() {
		return pfList;
	}

	public void setPfList(ArrayList<JPasswordField> pfList) {
		this.pfList = pfList;
	}

}

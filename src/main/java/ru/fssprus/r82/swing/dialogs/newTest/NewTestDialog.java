package ru.fssprus.r82.swing.dialogs.newTest;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.utils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;

/**
 * @author Chernyj Dmitry
 *
 */
public class NewTestDialog extends DialogWithPassword {
	private static final long serialVersionUID = 1342455118946206792L;
	private static final String SECTION = AppConstants.NEW_TEST_DIALOG;
	private static final String TITLE = AppConstants.NEW_TEST_TEXT;
	private static final String ICON = AppConstants.NEW_TEST_ICON;
	
	private static final String LBL_INFO_TEXT = "ИНФОРМАЦИЯ";
	private static final String LBL_NAME_TEXT = "Ваше имя:";
	private static final String LBL_SURNAME_TEXT = "Ваша фамилия:";
	private static final String LBL_SECONDNAME_TEXT = "Ваше отчество:";
	private static final String LBL_SPECIFICATION_TEXT = "Специализация:";
	private static final String BTN_BEGIN_CAPTION = "Начать";
	private static final String BTN_CANCEL_CAPTION = "Отмена";
	
	private JLabel lblInfo = new JLabel(LBL_INFO_TEXT);
	private JLabel lblName = new JLabel(LBL_NAME_TEXT);
	private JLabel lblSurname = new JLabel(LBL_SURNAME_TEXT);
	private JLabel lblSecondName = new JLabel(LBL_SECONDNAME_TEXT);
	private JLabel lblSpecification = new JLabel(LBL_SPECIFICATION_TEXT);
	
	private JTextField tfName = new JTextField();
	private JTextField tfSurname = new JTextField();
	private JTextField tfSecondName = new JTextField();
	private JComboBox<String> cbSpecification = new JComboBox<String>();
	
	private JPanel pnlLevels = new JPanel();
	
	private ArrayList<JRadioButton> rbLevels = new ArrayList<JRadioButton>();
	private ButtonGroup bgLevels = new ButtonGroup();
	
	private JButton btnBegin = new JGreenButton(BTN_BEGIN_CAPTION);
	private JButton btnCancel = new JGreenButton(BTN_CANCEL_CAPTION);
	
	public NewTestDialog(int width, int height, JFrame parent) {
		super(width, height, parent);
		fillRadioButtons();
		initPanelLevels();
	}
	
	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}
	
	@Override
	protected void layoutDialog() {
		JPanel contentPanel = getContentPanel();
		contentPanel.setLayout(new GridBagLayout());;
		//gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets, ipadx, ipady
		
		// 1st row
		contentPanel.add(lblInfo, new GridBagConstraints(0, 0, 4, 1, 4, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		
		// 2nd row
		contentPanel.add(lblName, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		
		contentPanel.add(tfName, new GridBagConstraints(1, 2, 4, 1, 4, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

		// 3rd row
		contentPanel.add(lblSurname, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		
		contentPanel.add(tfSurname, new GridBagConstraints(1, 1, 4, 1, 4, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		
		// 4th row
		contentPanel.add(lblSecondName, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		
		contentPanel.add(tfSecondName, new GridBagConstraints(1, 3, 4, 1, 4, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		
		// 5th row
		contentPanel.add(lblSpecification, new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		
		contentPanel.add(cbSpecification, new GridBagConstraints(1, 4, 4, 1, 4, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		
		// 6th row
		contentPanel.add(pnlLevels, new GridBagConstraints(0, 5, 4, 1, 4, 1, GridBagConstraints.NORTHEAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		
		// 7 th row 
		contentPanel.add(btnCancel, new GridBagConstraints(0, 6, 2, 1, 2, 1, GridBagConstraints.NORTHEAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		
		contentPanel.add(btnBegin, new GridBagConstraints(2, 6, 2, 1, 2, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}
	
	@Override
	protected String getTitleText() {
		return TITLE;
	}
	
	private void initPanelLevels() {
		pnlLevels.setLayout(new FlowLayout());
		rbLevels.forEach((n) -> pnlLevels.add(n));
	}
	
	private void fillRadioButtons() {
		JRadioButton rb1 = new JRadioButton(QuestionLevel.Базовый.toString());
		JRadioButton rb2 = new JRadioButton(QuestionLevel.Стандартный.toString());
		JRadioButton rb3 = new JRadioButton(QuestionLevel.Продвинутый.toString());
		JRadioButton rb4 = new JRadioButton(QuestionLevel.Резерв.toString());
		
		rbLevels.add(rb1);
		rbLevels.add(rb2);
		rbLevels.add(rb3);
		rbLevels.add(rb4);
		
		bgLevels.add(rb1);
		bgLevels.add(rb2);
		bgLevels.add(rb3);
		bgLevels.add(rb4);
		
		getRbLevels().forEach((n) -> n.setEnabled(false));
	}
	
	public void resetUserInputComponents() {
		tfName.setBackground(Color.WHITE);
		tfSurname.setBackground(Color.WHITE);
		tfSecondName.setBackground(Color.WHITE);
		
		for (JRadioButton lvls : getRbLevels()) {
			lvls.setBackground(Color.WHITE);
		}
	}
	
	public int getSelectedLevelIndex() {
		int returnValue = -1;
		for(int i = 0; i < rbLevels.size(); i++) 
			if(rbLevels.get(i).isSelected()) returnValue = i;
		return returnValue;
	}


	public JTextField getTfName() {
		return tfName;
	}


	public void setTfName(JTextField tfName) {
		this.tfName = tfName;
	}


	public JTextField getTfSurname() {
		return tfSurname;
	}


	public void setTfSurname(JTextField tfSurname) {
		this.tfSurname = tfSurname;
	}


	public JTextField getTfSecondName() {
		return tfSecondName;
	}


	public void setTfSecondName(JTextField tfSecondName) {
		this.tfSecondName = tfSecondName;
	}


	public JComboBox<String> getCbSpecification() {
		return cbSpecification;
	}

	public void setCbSpecification(JComboBox<String> cbSpecification) {
		this.cbSpecification = cbSpecification;
	}

	public JButton getBtnBegin() {
		return btnBegin;
	}


	public void setBtnBegin(JButton btnBegin) {
		this.btnBegin = btnBegin;
	}


	public JButton getBtnCancel() {
		return btnCancel;
	}


	public void setBtnCancel(JButton btnCancel) {
		this.btnCancel = btnCancel;
	}
	
	public ArrayList<JRadioButton> getRbLevels() {
		return rbLevels;
	}

	public void setRbLevels(ArrayList<JRadioButton> rbLevels) {
		this.rbLevels = rbLevels;
	}

	public ButtonGroup getBgLevels() {
		return bgLevels;
	}

	public void setBgLevels(ButtonGroup bgLevels) {
		this.bgLevels = bgLevels;
	}

}


package ru.fssprus.r82.swing.dialogs.addingSet;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import g.cope.swing.autocomplete.jcombobox.StringSearchable;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.utils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;

public class LoadingQuestionSetDialog extends DialogWithPassword {
	private static final long serialVersionUID = -4114441914928348354L;
	
	private static final String SECTION = AppConstants.QUESTION_LOAD_DIALOG;
	private static final String TITLE = AppConstants.QUESTION_LOAD_TEXT;
	private static final String ICON = AppConstants.QUESTION_LOAD_ICON;
	
	private static final String BTN_OPEN_CAPTION = "Открыть файл";
	private static final String LBL_SPEC_NAME_CAPTION = "Специализация:";
	private static final String LBL_QLEVEL_CAPTION = "Уровень сложности:";
	private static final String BTN_LOAD_CAPTION = "Добавить";
	
	private JLabel lblMsg = new JLabel(AppConstants.DIALOG_LOADING_QUEST_SET_ABOUT_INFO);
	
	private JButton btnOpenTextFile = new JGreenButton(BTN_OPEN_CAPTION);
	private JTextField tfFilePath = new JTextField();
	private JLabel lblSpecName = new JLabel(LBL_SPEC_NAME_CAPTION);
	private AutocompleteJComboBox accbSpecName = new AutocompleteJComboBox(null);

	private JLabel lblQuestLevel = new JLabel(LBL_QLEVEL_CAPTION);
	private JComboBox<Object> cbQuestLevel = new JComboBox<>(QuestionLevel.values());
	private JButton btnLoadQuestionsSet = new JGreenButton(BTN_LOAD_CAPTION);

	public LoadingQuestionSetDialog(int width, int height, JFrame parent) {
		super(width, height, parent);
	}
	
	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}
	
	@Override
	public void init() {
		initTfSpecNames();
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
	
	@Override
	protected void layoutDialog() {
		JPanel contentPanel = getContentPanel();

		contentPanel.setLayout(new GridBagLayout());

		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
		// insets(top, left, botom, right), ipadx, ipady

		// 1st row
		contentPanel.add(lblMsg, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(15, 5, 15, 5), 0, 0));
		
		// 2nd row
		contentPanel.add(btnOpenTextFile, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		contentPanel.add(tfFilePath, new GridBagConstraints(1, 1, 3, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));

		// 3rd row
		contentPanel.add(lblSpecName, new GridBagConstraints(0, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

		contentPanel.add(lblQuestLevel, new GridBagConstraints(2, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

		// 4th row
		contentPanel.add(accbSpecName, new GridBagConstraints(0, 3, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

		contentPanel.add(cbQuestLevel, new GridBagConstraints(2, 3, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

		// 5th row
		contentPanel.add(btnLoadQuestionsSet, new GridBagConstraints(0, 4, GridBagConstraints.REMAINDER, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

	}
	
	private void initTfSpecNames() {
		SpecificationService specService = new SpecificationService();
		
		ArrayList<String> keywords = new ArrayList<String>();
		specService.getAll().forEach((n) -> keywords.add(n.getName()));
		
		StringSearchable searchable = new StringSearchable(keywords);
		setAccbSpecName(new AutocompleteJComboBox(searchable));
		getAccbSpecName().addItem(null);
		getAccbSpecName().setPrototypeDisplayValue(AppConstants.ACCP_SPEC_NAMES_PROTOTYPE_DISPLAY_VALUE);
		keywords.forEach((n)-> getAccbSpecName().addItem(n));
	}
	
	public JButton getBtnLoadQuestionsSet() {
		return btnLoadQuestionsSet;
	}

	public void setBtnLoadQuestionsSet(JButton btnLoadQuestionsSet) {
		this.btnLoadQuestionsSet = btnLoadQuestionsSet;
	}
	
	public JComboBox<Object> getCbQuestLevel() {
		return cbQuestLevel;
	}

	public void setCbQuestLevel(JComboBox<Object> cbQuestLevel) {
		this.cbQuestLevel = cbQuestLevel;
	}
	
	public AutocompleteJComboBox getAccbSpecName() {
		return accbSpecName;
	}

	public void setAccbSpecName(AutocompleteJComboBox accbSpecName) {
		this.accbSpecName = accbSpecName;
	}

	public JButton getBtnOpenTextFile() {
		return btnOpenTextFile;
	}

	public void setBtnOpenTextFile(JButton btnOpenTextFile) {
		this.btnOpenTextFile = btnOpenTextFile;
	}

	public JTextField getTfFilePath() {
		return tfFilePath;
	}

	public void setTfFilePath(JTextField tfFilePath) {
		this.tfFilePath = tfFilePath;
	}
}

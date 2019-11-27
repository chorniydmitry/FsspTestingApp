package ru.fssprus.r82.swing.dialogs.questionList;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import g.cope.swing.autocomplete.jcombobox.StringSearchable;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.table.CommonTable;
import ru.fssprus.r82.swing.table.CommonTableModel;
import ru.fssprus.r82.swing.table.TablePanel;
import ru.fssprus.r82.swing.utils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;

/**
 * @author Chernyj Dmitry
 *
 */
public class QuestionListDialog extends DialogWithPassword {
	private static final long serialVersionUID = -8319908967500731744L;
	private static final String SECTION = AppConstants.QUESTION_EDIT_DIALOG;
	private static final String TITLE = AppConstants.QUESTION_EDIT_TEXT;
	private static final String ICON = AppConstants.QUESTION_EDIT_ICON;
	
	private static final String ANSWER_TEXT = "Ответ ";
	private static final String FILTER_PANEL_TITLE = "Панель фильтрации";
	
	public static final String LBL_ID_CAPTION_RU = "ID";
	public static final String LBL_QUESTION_NAME_CAPTION_RU = "Текст вопроса";
	public static final String LBL_SPECIFICATION_CAPTION_RU = "Специализация";
	public static final String LBL_LEVELS_CAPTION_RU = "Сложность";
	public static final String BTN_FILTER_CAPTION_RU = "Фильтр";
	public static final String BTN_RESET_CAPTION_RU = "Сбросить";
	public static final String BTN_DISCARD_Q_EDIT_CAPTION_RU = "Сбросить";
	public static final String BTN_SAVE_CAPTION_RU = "Сохранить";
	public static final String BTN_EDIT_CAPTION_RU = "Редактировать";

	private JPanel pnlFilter = new JPanel();

	private JLabel lblId = new JLabel(LBL_ID_CAPTION_RU);
	private JLabel lblQuestionName = new JLabel(LBL_QUESTION_NAME_CAPTION_RU);
	private JLabel lblSpecs = new JLabel(LBL_SPECIFICATION_CAPTION_RU);
	private JLabel lblLevels = new JLabel(LBL_LEVELS_CAPTION_RU);

	private JTextField tfId = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JTextField tfQuestionName = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JTextField tfSpecs = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JTextField tfLevels = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	
	private JGreenButton btnFilter = new JGreenButton(BTN_FILTER_CAPTION_RU);
	private JGreenButton btnClearFilters = new JGreenButton(BTN_RESET_CAPTION_RU);

	private JPanel pnlQuestionEdit = new JPanel();
	private JTextArea taQuestion = new JTextArea();

	private ArrayList<JTextField> tfAnsList = new ArrayList<>();
	private ArrayList<JLabel> lblAnsList = new ArrayList<>();
	private ArrayList<JCheckBox> cbAnsList = new ArrayList<>();

	private ArrayList<JCheckBox> cbLevelsList = new ArrayList<>();

	private JButton btnDiscardQuestionEditChanges = new JGreenButton(BTN_DISCARD_Q_EDIT_CAPTION_RU);
	private JButton btnSaveQuestion = new JGreenButton(BTN_SAVE_CAPTION_RU);
	private JButton btnEditQuestion = new JGreenButton(BTN_EDIT_CAPTION_RU);

	private JLabel lblSpecName = new JLabel(LBL_SPECIFICATION_CAPTION_RU);
	private AutocompleteJComboBox accbSpecNames = new AutocompleteJComboBox(null);
	
	private TablePanel tablePanel;

	public QuestionListDialog(int width, int height, JFrame parent) {
		super(width, height, parent);
		initTable();
		
		getRootPane().setDefaultButton(btnFilter);
	}
	
	private void initTable() {
		int[] widths = AppConstants.QLDIALOG_TABLE_COL_WIDTHS_ARR;
		String[] names = AppConstants.QLDIALOG_TABLE_COL_CAPTIONS_ARR;
		tablePanel = new TablePanel(widths, names);
	}
	
	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}
	
	@Override
	protected void layoutDialog() {
		JPanel contentPanel = getContentPanel();
		
		contentPanel.setLayout(new BorderLayout());
		
		contentPanel.add(pnlFilter, BorderLayout.NORTH);
		contentPanel.add(tablePanel, BorderLayout.CENTER);
		contentPanel.add(pnlQuestionEdit, BorderLayout.SOUTH);
		
		layoutPanelFilter();
		
		layoutPanelQuestionEdit();
	}

	@Override
	public void init() {
		initTable();
		initTfSpecNames();
		super.init();
	}
	
	private void initTfSpecNames() {
		SpecificationService specService = new SpecificationService();
		
		ArrayList<String> keywords = new ArrayList<String>();
		specService.getAll().forEach((n) -> keywords.add(n.getName()));
		
		StringSearchable searchable = new StringSearchable(keywords);
		setAccbSpecNames(new AutocompleteJComboBox(searchable));
		getAccbSpecNames().addItem(null);
		getAccbSpecNames().setPrototypeDisplayValue(AppConstants.ACCP_SPEC_NAMES_PROTOTYPE_DISPLAY_VALUE);
		keywords.forEach((n)-> getAccbSpecNames().addItem(n));
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}
	
	@Override
	protected String getTitleText() {
		return TITLE;
	}

	private void layoutPanelQuestionEdit() {
		pnlQuestionEdit.setLayout(new GridBagLayout());

		for (QuestionLevel qlevel : QuestionLevel.values()) {
			cbLevelsList.add(new JCheckBox(qlevel.toString()));
		}

		for (int i = 0; i < AppConstants.MAX_ANSWERS_AMOUNT; i++) {
			lblAnsList.add(new JLabel(ANSWER_TEXT + (i + 1)));
			tfAnsList.add(new JTextField());
			cbAnsList.add(new JCheckBox());
		}

		taQuestion.setWrapStyleWord(true);
		taQuestion.setLineWrap(true);
		// taQuestion.setPreferredSize(new Dimension(this.getWidth(), 80));
		JScrollPane scrollPane = new JScrollPane(taQuestion);

		pnlQuestionEdit.setBorder(BorderFactory.createTitledBorder(AppConstants.QLDIALOG_PNL_QEDIT_BORDER_TITLE_RU));

		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
		// insets(top, left, botom, right), ipadx, ipady

		// 1st .. cbLevelsList.size() row
		pnlQuestionEdit.add(scrollPane, new GridBagConstraints(0, 0, 2, cbLevelsList.size(), 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		for (int i = 0; i < cbLevelsList.size(); i++) {
			pnlQuestionEdit.add(cbLevelsList.get(i), new GridBagConstraints(2, i, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		}

		pnlQuestionEdit.add(scrollPane, new GridBagConstraints(0, 0, 2, cbLevelsList.size(), 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		//
		pnlQuestionEdit.add(lblSpecName, new GridBagConstraints(0, cbLevelsList.size(), 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlQuestionEdit.add(accbSpecNames, new GridBagConstraints(1, cbLevelsList.size(), 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// cbLevelsList.size()+1 row
		for (int i = 0; i < AppConstants.MAX_ANSWERS_AMOUNT; i++) {
			pnlQuestionEdit.add(lblAnsList.get(i), new GridBagConstraints(0, cbLevelsList.size() + 1 + i, 1, 1, 0, 0,
					GridBagConstraints.WEST, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0));

			pnlQuestionEdit.add(tfAnsList.get(i), new GridBagConstraints(1, cbLevelsList.size() + 1 + i, 1, 1, 1, 0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

			pnlQuestionEdit.add(cbAnsList.get(i), new GridBagConstraints(2, cbLevelsList.size() + 1 + i, 1, 1, 0, 0,
					GridBagConstraints.WEST, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0));
		}

		// last row
		pnlQuestionEdit.add(btnDiscardQuestionEditChanges, new GridBagConstraints(0, cbLevelsList.size() + 2 + AppConstants.MAX_ANSWERS_AMOUNT, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlQuestionEdit.add(btnSaveQuestion, new GridBagConstraints(1, cbLevelsList.size() + 2 + AppConstants.MAX_ANSWERS_AMOUNT, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		pnlQuestionEdit.add(btnEditQuestion, new GridBagConstraints(2, cbLevelsList.size() + 2 + AppConstants.MAX_ANSWERS_AMOUNT, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		pnlQuestionEdit.setVisible(true);

	}

	private void layoutPanelFilter() {
		pnlFilter.setLayout(new GridBagLayout());

		pnlFilter.setBorder(BorderFactory.createTitledBorder(FILTER_PANEL_TITLE));

		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
		// insets(top, left, botom, right), ipadx, ipady

		// 1st row
		pnlFilter.add(lblId, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.add(tfId, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.add(lblQuestionName, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.add(tfQuestionName, new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.add(btnFilter, new GridBagConstraints(4, 0, 1, GridBagConstraints.REMAINDER, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		pnlFilter.add(btnClearFilters, new GridBagConstraints(5, 0, 1, GridBagConstraints.REMAINDER, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// 2nd row
		pnlFilter.add(lblSpecs, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.add(tfSpecs, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.add(lblLevels, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.add(tfLevels, new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlFilter.setVisible(true);
	}
	

	public JButton getBtnFilter() {
		return btnFilter;
	}

	public void setBtnFilter(JGreenButton btnFilter) {
		this.btnFilter = btnFilter;
	}

	public JTextArea getTaQuestion() {
		return taQuestion;
	}

	public void setTaQuestion(JTextArea taQuestion) {
		this.taQuestion = taQuestion;
	}

	public ArrayList<JTextField> getTfAnsList() {
		return tfAnsList;
	}

	public void setTfAnsList(ArrayList<JTextField> tfAnsList) {
		this.tfAnsList = tfAnsList;
	}

	public ArrayList<JCheckBox> getCbAnsList() {
		return cbAnsList;
	}

	public void setCbAnsList(ArrayList<JCheckBox> cbAnsList) {
		this.cbAnsList = cbAnsList;
	}

	public JButton getBtnDiscardQuestionEditChanges() {
		return btnDiscardQuestionEditChanges;
	}

	public void setBtnDiscardQuestionEditChanges(JButton btnDiscardQuestionEditChanges) {
		this.btnDiscardQuestionEditChanges = btnDiscardQuestionEditChanges;
	}

	public JButton getBtnSaveQuestion() {
		return btnSaveQuestion;
	}

	public void setBtnSave(JButton btnSaveQuestion) {
		this.btnSaveQuestion = btnSaveQuestion;
	}

	public ArrayList<JCheckBox> getCbLevelsList() {
		return cbLevelsList;
	}

	public void setCbLevelsList(ArrayList<JCheckBox> cbLevelsList) {
		this.cbLevelsList = cbLevelsList;
	}

	public AutocompleteJComboBox getAccbSpecNames() {
		return accbSpecNames;
	}

	public void setAccbSpecNames(AutocompleteJComboBox accbSpecNames) {
		this.accbSpecNames = accbSpecNames;
	}

	public JTextField getTfId() {
		return tfId;
	}

	public void setTfId(JTextField tfId) {
		this.tfId = tfId;
	}

	public JTextField getTfQuestionName() {
		return tfQuestionName;
	}

	public void setTfQuestionName(JTextField tfQuestionName) {
		this.tfQuestionName = tfQuestionName;
	}

	public JTextField getTfSpecs() {
		return tfSpecs;
	}

	public void setTfSpecs(JTextField tfSpecs) {
		this.tfSpecs = tfSpecs;
	}

	public JTextField getTfLevels() {
		return tfLevels;
	}

	public void setTfLevels(JTextField tfLevels) {
		this.tfLevels = tfLevels;
	}

	public JButton getBtnClearFilters() {
		return btnClearFilters;
	}

	public void setBtnClearFilters(JGreenButton btnClearFilters) {
		this.btnClearFilters = btnClearFilters;
	}

	public JButton getBtnEditQuestion() {
		return btnEditQuestion;
	}

	public void setBtnEditQuestion(JButton btnEditQuestion) {
		this.btnEditQuestion = btnEditQuestion;
	}

	public TablePanel getTabPanel() {
		return tablePanel;
	}

	public void setTabPanel(TablePanel tabPanel) {
		this.tablePanel = tabPanel;
	}
	
	public CommonTable getTable() {
		return tablePanel.getCommonTable();
	}
	
	public CommonTableModel getTableModel() {
		return getTable().getTabModel();
	}
	
	
}

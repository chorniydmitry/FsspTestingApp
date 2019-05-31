package ru.fssprus.r82.swing.dialogs.questionListDialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.utils.AppConstants;

public class QuestionListDialog extends DialogWithPassword {
	private static final long serialVersionUID = -8319908967500731744L;
	private static final String SECTION = AppConstants.QUESTION_EDIT_SECTION;
	public static final int MAX_ANSWERS = 5;

	private QuestionListTable tabQuestList = new QuestionListTable();
	private JScrollPane scrollPane = new JScrollPane(tabQuestList);
	private JPanel pnlBottom = new JPanel();
	private JPanel pnlFilter = new JPanel();

	private JLabel lblId = new JLabel("ID");
	private JLabel lblQuestionName = new JLabel("Формулировка");
	private JLabel lblSpecs = new JLabel("Cпецификации");
	private JLabel lblLevels = new JLabel("Уровни");

	private JTextField tfId = new JTextField(25);
	private JTextField tfQuestionName = new JTextField(25);
	private JTextField tfSpecs = new JTextField(25);
	private JTextField tfLevels = new JTextField(25);
	
	private JButton btnAdd = new JButton("+");
	private JButton btnRemove = new JButton("-");
	private JPanel pnlAddRemove = new JPanel(new FlowLayout(FlowLayout.LEFT));

	private JButton btnFilter = new JButton("Фильтр");
	private JButton btnClearFilters = new JButton("Сбросить");

	private JButton btnNextPage = new JButton(">");
	private JButton btnPreviousPage = new JButton("<");
	private JLabel lblPageInfo = new JLabel();

	private JPanel pnlQuestionEdit = new JPanel();
	private JTextArea taQuestion = new JTextArea();

	private ArrayList<JTextField> tfAnsList = new ArrayList<JTextField>();
	private ArrayList<JLabel> lblAnsList = new ArrayList<JLabel>();
	private ArrayList<JCheckBox> cbAnsList = new ArrayList<JCheckBox>();

	private ArrayList<JCheckBox> cbLevelsList = new ArrayList<JCheckBox>();

	private JButton btnDiscardQuestionEditChanges = new JButton("Сбросить");
	private JButton btnSaveQuestion = new JButton("Cохранить");
	private JButton btnEditQuestion = new JButton("Редактировать");

	private JLabel lblSpecName = new JLabel("Специализация: ");
	private AutocompleteJComboBox accbSpecNames = new AutocompleteJComboBox(null);

	public QuestionListDialog(int width, int height) {
		super(width, height);
	}
	
	@Override
	protected void layoutDialog() {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		layoutPanelFilter();
		
		layoutPanelAddRemove();

		layoutPanelBottom();
		
		layoutPanelQuestionEdit();
		
		add(pnlFilter);
		add(pnlAddRemove);
		add(scrollPane);
		add(pnlBottom);
		add(pnlQuestionEdit);
		
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}

	private void layoutPanelQuestionEdit() {
		pnlQuestionEdit.setLayout(new GridBagLayout());

		for (QuestionLevel qlevel : QuestionLevel.values()) {
			cbLevelsList.add(new JCheckBox(qlevel.toString()));
		}

		for (int i = 0; i < MAX_ANSWERS; i++) {
			lblAnsList.add(new JLabel("Ответ " + (i + 1)));
			tfAnsList.add(new JTextField());
			cbAnsList.add(new JCheckBox());
		}

		taQuestion.setWrapStyleWord(true);
		taQuestion.setLineWrap(true);
		// taQuestion.setPreferredSize(new Dimension(this.getWidth(), 80));
		JScrollPane scrollPane = new JScrollPane(taQuestion);

		pnlQuestionEdit.setBorder(BorderFactory.createTitledBorder("Просмотр и редактирование вопроса"));

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
		for (int i = 0; i < MAX_ANSWERS; i++) {
			pnlQuestionEdit.add(lblAnsList.get(i), new GridBagConstraints(0, cbLevelsList.size() + 1 + i, 1, 1, 0, 0,
					GridBagConstraints.WEST, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0));

			pnlQuestionEdit.add(tfAnsList.get(i), new GridBagConstraints(1, cbLevelsList.size() + 1 + i, 1, 1, 1, 0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

			pnlQuestionEdit.add(cbAnsList.get(i), new GridBagConstraints(2, cbLevelsList.size() + 1 + i, 1, 1, 0, 0,
					GridBagConstraints.WEST, GridBagConstraints.CENTER, new Insets(0, 0, 0, 0), 0, 0));
		}

		// last row
		pnlQuestionEdit.add(btnDiscardQuestionEditChanges, new GridBagConstraints(0, cbLevelsList.size() + 2 + MAX_ANSWERS, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlQuestionEdit.add(btnSaveQuestion, new GridBagConstraints(1, cbLevelsList.size() + 2 + MAX_ANSWERS, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		pnlQuestionEdit.add(btnEditQuestion, new GridBagConstraints(2, cbLevelsList.size() + 2 + MAX_ANSWERS, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		pnlQuestionEdit.setVisible(true);

	}

	private void layoutPanelFilter() {
		pnlFilter.setLayout(new GridBagLayout());

		pnlFilter.setBorder(BorderFactory.createTitledBorder("Панель фильтрации"));

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
	
	private void layoutPanelAddRemove() {
		Dimension dim = new Dimension(45,25);
		btnAdd.setPreferredSize(dim);
		btnRemove.setPreferredSize(dim);
		
		pnlAddRemove.add(btnAdd);
		pnlAddRemove.add(btnRemove);
	}

	private void layoutPanelBottom() {
		pnlBottom.add(btnPreviousPage);
		pnlBottom.add(lblPageInfo);
		pnlBottom.add(btnNextPage);

		pnlBottom.setVisible(true);
	}

	public QuestionListTable getTabQuestList() {
		return tabQuestList;
	}

	public JButton getBtnFilter() {
		return btnFilter;
	}

	public void setBtnFilter(JButton btnFilter) {
		this.btnFilter = btnFilter;
	}

	public JButton getBtnNextPage() {
		return btnNextPage;
	}

	public void setBtnNextPage(JButton btnNextPage) {
		this.btnNextPage = btnNextPage;
	}

	public JButton getBtnPreviousPage() {
		return btnPreviousPage;
	}

	public void setBtnPreviousPage(JButton btnPreviousPage) {
		this.btnPreviousPage = btnPreviousPage;
	}

	public JLabel getLblPageInfo() {
		return lblPageInfo;
	}

	public void setLblPageInfo(JLabel lblPageInfo) {
		this.lblPageInfo = lblPageInfo;
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

	public void setBtnClearFilters(JButton btnClearFilters) {
		this.btnClearFilters = btnClearFilters;
	}

	public JButton getBtnEditQuestion() {
		return btnEditQuestion;
	}

	public void setBtnEditQuestion(JButton btnEditQuestion) {
		this.btnEditQuestion = btnEditQuestion;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(JButton btnAdd) {
		this.btnAdd = btnAdd;
	}

	public JButton getBtnRemove() {
		return btnRemove;
	}

	public void setBtnRemove(JButton btnRemove) {
		this.btnRemove = btnRemove;
	}
	
	
	
	
}

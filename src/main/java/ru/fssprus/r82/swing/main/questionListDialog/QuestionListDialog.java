package ru.fssprus.r82.swing.main.questionListDialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import ru.fssprus.r82.entity.QuestionLevel;

public class QuestionListDialog extends JDialog {
	private static final long serialVersionUID = -8319908967500731744L;
	public static final int MAX_ANSWERS = 5;

	private QuestionListTable tabQuestList = new QuestionListTable();
	private JPanel pnlBottom = new JPanel();
	private JPanel pnlTop = new JPanel();

	private JLabel lblId = new JLabel("ID");
	private JLabel lblQuestionName = new JLabel("Формулировка");
	private JLabel lblSpecs = new JLabel("Cпецификации");
	private JLabel lblLevels = new JLabel("Уровни");

	private JTextField tfId = new JTextField(25);
	private JTextField tfQuestionName = new JTextField(25);
	private JTextField tfSpecs = new JTextField(25);
	private JTextField tfLevels = new JTextField(25);

	private JButton btnFilter = new JButton("Фильтр");
	private JButton btnReset = new JButton("Сбросить");

	private JButton btnNextPage = new JButton(">");
	private JButton btnPreviousPage = new JButton("<");
	private JLabel lblPageInfo = new JLabel();

	private JPanel pnlQuestionEdit = new JPanel();
	private JTextArea taQuestion = new JTextArea();

	private ArrayList<JTextField> tfAnsList = new ArrayList<JTextField>();
	private ArrayList<JLabel> lblAnsList = new ArrayList<JLabel>();
	private ArrayList<JCheckBox> cbAnsList = new ArrayList<JCheckBox>();

	private ArrayList<JCheckBox> cbLevelsList = new ArrayList<JCheckBox>();

	private JButton btnDiscard = new JButton("Сбросить");
	private JButton btnSave = new JButton("Cохранить");

	private JLabel lblSpecName = new JLabel("Специализация: ");
	private AutocompleteJComboBox accbSpecNames = new AutocompleteJComboBox(null);

	public QuestionListDialog(int width, int height) {
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		setVisible(true);
	}

	public void init() {
		initPanelTop();
		add(pnlTop);

		JScrollPane scrollPane = new JScrollPane(tabQuestList);
		add(scrollPane);

		initPanelBottom();
		add(pnlBottom);

		initPanelQuestionEdit();
		add(pnlQuestionEdit);

		repaint();
		revalidate();
	}

	private void initPanelQuestionEdit() {
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
		pnlQuestionEdit.add(btnDiscard, new GridBagConstraints(0, cbLevelsList.size() + 2 + MAX_ANSWERS, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlQuestionEdit.add(btnSave, new GridBagConstraints(1, cbLevelsList.size() + 2 + MAX_ANSWERS, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlQuestionEdit.setVisible(true);

	}

	private void initPanelTop() {
		pnlTop.setLayout(new GridBagLayout());

		pnlTop.setBorder(BorderFactory.createTitledBorder("Панель фильтрации"));

		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
		// insets(top, left, botom, right), ipadx, ipady

		// 1st row
		pnlTop.add(lblId, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.add(tfId, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.add(lblQuestionName, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.add(tfQuestionName, new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.add(btnFilter, new GridBagConstraints(4, 0, 1, GridBagConstraints.REMAINDER, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		pnlTop.add(btnReset, new GridBagConstraints(5, 0, 1, GridBagConstraints.REMAINDER, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// 2nd row
		pnlTop.add(lblSpecs, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.add(tfSpecs, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.add(lblLevels, new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.add(tfLevels, new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		pnlTop.setVisible(true);
	}

	private void initPanelBottom() {
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

	public JButton getBtnDiscard() {
		return btnDiscard;
	}

	public void setBtnDiscard(JButton btnDiscard) {
		this.btnDiscard = btnDiscard;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
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
	
	

}

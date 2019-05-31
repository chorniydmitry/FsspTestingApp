package ru.fssprus.r82.swing.dialogs.addingSetDialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import g.cope.swing.autocomplete.jcombobox.StringSearchable;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.utils.AppConstants;

public class LoadingQuestionSetDialog extends DialogWithPassword {
	private static final long serialVersionUID = -4114441914928348354L;
	private static final String SECTION = AppConstants.QUESTION_LOAD_SECTION;
	
	private static final String MSG = "<html><p width = 580>Данная форма предназначена"
			+ "для загрузки в базу данных вопросов, выгруженных из базы данных "
			+ "приложения ФССП тест в фотматах XLSX или ODS</p><html>";

	private JLabel lblMsg = new JLabel(MSG);
	
	private JButton btnOpenTextFile = new JButton("Открыть файл");
	private JTextField tfFilePath = new JTextField();
	private JLabel lblSpecName = new JLabel("Специализация:");
	private AutocompleteJComboBox accbSpecName = new AutocompleteJComboBox(null);

	private JLabel lblQuestLevel = new JLabel("Уровень сложности:");
	private JComboBox<Object> cbQuestLevel = new JComboBox<Object>(QuestionLevel.values());
	private JButton btnLoadQuestionsSet = new JButton("Добавить");

	public LoadingQuestionSetDialog(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
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
	protected void layoutDialog() {

		setLayout(new GridBagLayout());

		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
		// insets(top, left, botom, right), ipadx, ipady

		// 1st row
		add(lblMsg, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(15, 5, 15, 5), 0, 0));
		
		// 2nd row
		add(btnOpenTextFile, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(tfFilePath, new GridBagConstraints(1, 1, 3, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(7, 5, 5, 5), 0, 0));

		// 3rd row
		add(lblSpecName, new GridBagConstraints(0, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

		add(lblQuestLevel, new GridBagConstraints(2, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

		// 4th row
		add(accbSpecName, new GridBagConstraints(0, 3, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

		add(cbQuestLevel, new GridBagConstraints(2, 3, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

		// 5th row
		add(btnLoadQuestionsSet, new GridBagConstraints(0, 4, GridBagConstraints.REMAINDER, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

	}
	
	private void initTfSpecNames() {
		SpecificationService specService = new SpecificationService();
		
		ArrayList<String> keywords = new ArrayList<String>();
		specService.getAll().forEach((n) -> keywords.add(n.getName()));
		
		StringSearchable searchable = new StringSearchable(keywords);
		setAccbSpecName(new AutocompleteJComboBox(searchable));
		getAccbSpecName().addItem(null);
		getAccbSpecName().setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
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

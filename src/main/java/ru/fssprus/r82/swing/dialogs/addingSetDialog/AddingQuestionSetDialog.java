package ru.fssprus.r82.swing.dialogs.addingSetDialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import g.cope.swing.autocomplete.jcombobox.StringSearchable;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonDialog;

public class AddingQuestionSetDialog extends CommonDialog {
	private static final long serialVersionUID = -4114441914928348354L;

	private JButton btnOpenTextFile = new JButton("Открыть файл");
	private JTextField tfFilePath = new JTextField();
	private JLabel lblSpecName = new JLabel("Специализация:");
	private AutocompleteJComboBox accbSpecName = new AutocompleteJComboBox(null);

	private JLabel lblQuestLevel = new JLabel("Уровень сложности:");
	private JComboBox<Object> cbQuestLevel = new JComboBox<Object>(QuestionLevel.values());
	private JButton btnLoadQuestionsSet = new JButton("Добавить");

	public AddingQuestionSetDialog(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() {
		initTfSpecNames();
		super.init();
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
	
	@Override
	protected void layoutDialog() {

		setLayout(new GridBagLayout());

		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
		// insets(top, left, botom, right), ipadx, ipady

		// 1st row
		add(btnOpenTextFile, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		add(tfFilePath, new GridBagConstraints(1, 0, 3, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(7, 5, 5, 5), 0, 0));

		// 2nd row
		add(lblSpecName, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

		add(lblQuestLevel, new GridBagConstraints(2, 1, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

		// 3rd row
		add(accbSpecName, new GridBagConstraints(0, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

		add(cbQuestLevel, new GridBagConstraints(2, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

		// 4th row
		add(btnLoadQuestionsSet, new GridBagConstraints(0, 3, GridBagConstraints.REMAINDER, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

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

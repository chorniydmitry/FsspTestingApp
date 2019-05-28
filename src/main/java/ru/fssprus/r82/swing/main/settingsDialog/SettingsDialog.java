package ru.fssprus.r82.swing.main.settingsDialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import g.cope.swing.autocomplete.jcombobox.StringSearchable;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.utils.AppConstants;

public class SettingsDialog extends JDialog {
	private static final long serialVersionUID = -3585887095900374897L;
	
	private JLabel lblBaseNum = new JLabel("Количество:");
	private JLabel lblBaseCommons = new JLabel("Процент Общих:");
	private JLabel lblBaseTime = new JLabel("Время теста(секунд):");
	
	private JPanel pnlBase = new JPanel();
	
	private JLabel lblStandartNum = new JLabel("Количество:");
	private JLabel lblStandartCommons = new JLabel("Процент Общих:");
	private JLabel lblStandartTime = new JLabel("Время теста(секунд):");
	
	private JPanel pnlStandart = new JPanel();
	
	private JLabel lblAdvancedNum = new JLabel("Количество:");
	private JLabel lblAdvancedCommons = new JLabel("Процент Общих:");
	private JLabel lblAdvancedTime = new JLabel("Время теста(секунд):");
	
	private JPanel pnlAdvanced = new JPanel();
	
	private JLabel lblReserveNum = new JLabel("Количество:");
	private JLabel lblReserveCommons = new JLabel("Процент Общих:");
	private JLabel lblReserveTime = new JLabel("Время теста(секунд):");
	
	private JPanel pnlReserve = new JPanel();
	
	private JTextField tfBaseNum = new JTextField(String.valueOf(AppConstants.BASE_QUESTS));
	private JTextField tfBaseCommons = new JTextField(String.valueOf(AppConstants.BASE_COMMONS));
	private JTextField tfBaseTime = new JTextField(String.valueOf(AppConstants.BASE_TIME));
	
	private JTextField tfStandartNum = new JTextField(String.valueOf(AppConstants.STANDART_QUESTS));
	private JTextField tfStandartCommons = new JTextField(String.valueOf(AppConstants.STANDART_COMMONS));
	private JTextField tfStandartTime = new JTextField(String.valueOf(AppConstants.STANDART_TIME));
	
	private JTextField tfAdvancedNum = new JTextField(String.valueOf(AppConstants.ADVANCED_QUESTS));
	private JTextField tfAdvancedCommons = new JTextField(String.valueOf(AppConstants.ADVANCED_COMMONS));
	private JTextField tfAdvancedTime = new JTextField(String.valueOf(AppConstants.ADVANCED_TIME));
	
	private JTextField tfReserveNum = new JTextField(String.valueOf(AppConstants.RESERVE_QUESTS));
	private JTextField tfReserveCommons = new JTextField(String.valueOf(AppConstants.RESERVE_COMMONS));
	private JTextField tfReserveTime = new JTextField(String.valueOf(AppConstants.RESERVE_TIME));
	
	private JButton btnSave = new JButton("Cохранить изменения");
	
	private JButton btnOpenTextFile = new JButton("Открыть файл");
	private JTextField tfFilePath = new JTextField();
	private JPanel pnlAddingSet = new JPanel();
	private JLabel lblSpecName = new JLabel("Специализация:");
	private AutocompleteJComboBox accbSpecName = new AutocompleteJComboBox(null);

	private JLabel lblQuestLevel = new JLabel("Уровень сложности:");
	private JComboBox<Object> cbQuestLevel = new JComboBox<Object>(QuestionLevel.values());
	private JButton btnLoadQuestionsSet = new JButton("Добавить");
	
	private List<JTextField> tfsList = new ArrayList<JTextField>();
	
	public SettingsDialog(int width, int height) {
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);

	}
	
	public void init() {
		initComponents();
		
		initGroupPanels();
		layoutGroupPanels();
		setTfsNames();
		fillTfsList();
		layoutDialog();
		
		setResizable(true);
		setVisible(true);
	}
	
	private void setTfsNames() {
		tfBaseNum.setName("base.num");
		tfBaseTime.setName("base.time");
		tfBaseCommons.setName("base.common.percent");
		
		tfStandartNum.setName("standart.num");
		tfStandartTime.setName("standart.time");
		tfStandartCommons.setName("standart.common.percent");
		
		tfAdvancedNum.setName("advanced.num");
		tfAdvancedTime.setName("advanced.time");
		tfAdvancedCommons.setName("advanced.common.percent");
		
		tfReserveNum.setName("reserve.num");
		tfReserveTime.setName("reserve.time");
		tfReserveCommons.setName("reserve.common.percent");
	}
	
	private void fillTfsList() {
		tfsList.add(tfBaseNum);
		tfsList.add(tfBaseCommons);
		tfsList.add(tfBaseTime);
		
		tfsList.add(tfStandartNum);
		tfsList.add(tfStandartCommons);
		tfsList.add(tfStandartTime);
		
		tfsList.add(tfAdvancedNum);
		tfsList.add(tfAdvancedCommons);
		tfsList.add(tfAdvancedTime);

		tfsList.add(tfReserveNum);
		tfsList.add(tfReserveCommons);
		tfsList.add(tfReserveTime);
	}
	
	private void initComponents() {
		btnSave.setEnabled(false);
	}
	
	private void layoutDialog() {
		setLayout(new GridBagLayout());
		
		//gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets(top, left, botom, right), ipadx, ipady
		
		// 1st row
		add(pnlBase, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		add(pnlStandart, new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		// 2nd row
		add(pnlAdvanced, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		add(pnlReserve, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		// 3rd row
		add(btnSave, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 20, 0, 20), 0, 0));
		
		// 4th row
		add(pnlAddingSet, new GridBagConstraints(0, 3, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
	}
	
	private void initGroupPanels() {
		pnlBase.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Базовый.toString()));
		pnlStandart.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Стандартный.toString()));
		pnlAdvanced.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Продвинутый.toString()));
		pnlReserve.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Резерв.toString()));
		
		pnlAddingSet.setBorder(BorderFactory.createTitledBorder("Загрузка набора вопросов из файла"));
	}
	
	private void layoutGroupPanels() {
		doGrigBagGroupPanels(pnlBase, lblBaseNum, tfBaseNum, lblBaseCommons, tfBaseCommons, lblBaseTime, tfBaseTime);
		doGrigBagGroupPanels(pnlStandart, lblStandartNum, tfStandartNum, lblStandartCommons, tfStandartCommons, lblStandartTime, tfStandartTime);
		doGrigBagGroupPanels(pnlAdvanced, lblAdvancedNum, tfAdvancedNum, lblAdvancedCommons, tfAdvancedCommons, lblAdvancedTime, tfAdvancedTime);
		doGrigBagGroupPanels(pnlReserve, lblReserveNum, tfReserveNum, lblReserveCommons, tfReserveCommons, lblReserveTime, tfReserveTime);
		doGridBagAddSetPanel();
	}
	
	private void doGridBagAddSetPanel() {
		pnlAddingSet.setLayout(new GridBagLayout());
		
		//gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets(top, left, botom, right), ipadx, ipady
		
		// 1st row
		pnlAddingSet.add(btnOpenTextFile, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		pnlAddingSet.add(tfFilePath, new GridBagConstraints(1, 0, 3, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(7, 5, 5, 5), 0, 0));
		
		// 2nd row
		pnlAddingSet.add(lblSpecName, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
		
		pnlAddingSet.add(lblQuestLevel, new GridBagConstraints(2, 1, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
		
		// 3rd row
		pnlAddingSet.add(accbSpecName, new GridBagConstraints(0, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
		
		pnlAddingSet.add(cbQuestLevel, new GridBagConstraints(2, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
		
		// 4th row
		pnlAddingSet.add(btnLoadQuestionsSet, new GridBagConstraints(0, 3, GridBagConstraints.REMAINDER, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
	}

	private void doGrigBagGroupPanels(JPanel panel, JLabel lbl1, JTextField tf1, JLabel lbl2, JTextField tf2,
			 JLabel lbl3, JTextField tf3) {
		panel.setLayout(new GridBagLayout());
		
		//gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets(top, left, botom, right), ipadx, ipady
		
		// 1st row
		panel.add(lbl1, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		
		panel.add(tf1, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		// 2nd row
		panel.add(lbl2, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		
		panel.add(tf2, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		// 3rd row
		panel.add(lbl3, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		
		panel.add(tf3, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
	}

	public JTextField getTfBaseNum() {
		return tfBaseNum;
	}

	public void setTfBaseNum(JTextField tfBaseNum) {
		this.tfBaseNum = tfBaseNum;
	}

	public JTextField getTfBaseCommons() {
		return tfBaseCommons;
	}

	public void setTfBaseCommons(JTextField tfBaseCommons) {
		this.tfBaseCommons = tfBaseCommons;
	}

	public JTextField getTfBaseTime() {
		return tfBaseTime;
	}

	public void setTfBaseTime(JTextField tfBaseTime) {
		this.tfBaseTime = tfBaseTime;
	}

	public JTextField getTfStandartNum() {
		return tfStandartNum;
	}

	public void setTfStandartNum(JTextField tfStandartNum) {
		this.tfStandartNum = tfStandartNum;
	}

	public JTextField getTfStandartCommons() {
		return tfStandartCommons;
	}

	public void setTfStandartCommons(JTextField tfStandartCommons) {
		this.tfStandartCommons = tfStandartCommons;
	}

	public JTextField getTfStandartTime() {
		return tfStandartTime;
	}

	public void setTfStandartTime(JTextField tfStandartTime) {
		this.tfStandartTime = tfStandartTime;
	}

	public JTextField getTfAdvancedNum() {
		return tfAdvancedNum;
	}

	public void setTfAdvancedNum(JTextField tfAdvancedNum) {
		this.tfAdvancedNum = tfAdvancedNum;
	}

	public JTextField getTfAdvancedCommons() {
		return tfAdvancedCommons;
	}

	public void setTfAdvancedCommons(JTextField tfAdvancedCommons) {
		this.tfAdvancedCommons = tfAdvancedCommons;
	}

	public JTextField getTfAdvancedTime() {
		return tfAdvancedTime;
	}

	public void setTfAdvancedTime(JTextField tfAdvancedTime) {
		this.tfAdvancedTime = tfAdvancedTime;
	}

	public JTextField getTfReserveNum() {
		return tfReserveNum;
	}

	public void setTfReserveNum(JTextField tfReserveNum) {
		this.tfReserveNum = tfReserveNum;
	}

	public JTextField getTfReserveCommons() {
		return tfReserveCommons;
	}

	public void setTfReserveCommons(JTextField tfReserveCommons) {
		this.tfReserveCommons = tfReserveCommons;
	}

	public JTextField getTfReserveTime() {
		return tfReserveTime;
	}

	public void setTfReserveTime(JTextField tfReserveTime) {
		this.tfReserveTime = tfReserveTime;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}

	public JButton getBtnLoadQuestionsSet() {
		return btnLoadQuestionsSet;
	}

	public void setBtnLoadQuestionsSet(JButton btnLoadQuestionsSet) {
		this.btnLoadQuestionsSet = btnLoadQuestionsSet;
	}

	public List<JTextField> getTfsList() {
		return tfsList;
	}

	public void setTfsList(List<JTextField> tfsList) {
		this.tfsList = tfsList;
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

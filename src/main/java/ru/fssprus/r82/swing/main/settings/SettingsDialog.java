package ru.fssprus.r82.swing.main.settings;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.fssprus.r82.utils.TestConstants;

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
	
	private JTextField tfBaseNum = new JTextField(String.valueOf(TestConstants.BASE_QUESTS));
	private JTextField tfBaseCommons = new JTextField(String.valueOf(TestConstants.BASE_COMMONS));
	private JTextField tfBaseTime = new JTextField(String.valueOf(TestConstants.BASE_TIME));
	
	private JTextField tfStandartNum = new JTextField(String.valueOf(TestConstants.STANDART_QUESTS));
	private JTextField tfStandartCommons = new JTextField(String.valueOf(TestConstants.STANDART_COMMONS));
	private JTextField tfStandartTime = new JTextField(String.valueOf(TestConstants.STANDART_TIME));
	
	private JTextField tfAdvancedNum = new JTextField(String.valueOf(TestConstants.ADVANCED_QUESTS));
	private JTextField tfAdvancedCommons = new JTextField(String.valueOf(TestConstants.ADVANCED_COMMONS));
	private JTextField tfAdvancedTime = new JTextField(String.valueOf(TestConstants.ADVANCED_TIME));
	
	private JTextField tfReserveNum = new JTextField(String.valueOf(TestConstants.RESERVE_QUESTS));
	private JTextField tfReserveCommons = new JTextField(String.valueOf(TestConstants.RESERVE_COMMONS));
	private JTextField tfReserveTime = new JTextField(String.valueOf(TestConstants.RESERVE_TIME));
	
	private JButton btnSave = new JButton("Cохранить изменения");
	
	private JLabel lblLoadQuestionsSet = new JLabel("Загрузить набор вопросов из файла:");
	private JButton btnLoadQuestionsSet = new JButton("+");
	
	private List<JTextField> tfsList = new ArrayList<JTextField>();
	
	public SettingsDialog(int width, int height) {
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		
		initComponents();
		
		initGroupPanels();
		layoutGroupPanels();
		setTfsNames();
		fillTfsList();
		layoutDialog();
		
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
		
		add(pnlReserve, new GridBagConstraints(1, 1, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		// 3rd row
		add(btnSave, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		// 4th row
		add(lblLoadQuestionsSet, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		add(btnLoadQuestionsSet, new GridBagConstraints(1, 3, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
	}
	
	private void initGroupPanels() {
		pnlBase.setBorder(BorderFactory.createTitledBorder("Базовый"));
		pnlStandart.setBorder(BorderFactory.createTitledBorder("Стандартный"));
		pnlAdvanced.setBorder(BorderFactory.createTitledBorder("Продвинутый"));
		pnlReserve.setBorder(BorderFactory.createTitledBorder("Резерв"));
	}
	
	private void layoutGroupPanels() {
		doGrigBagGroupPanels(pnlBase, lblBaseNum, tfBaseNum, lblBaseCommons, tfBaseCommons, lblBaseTime, tfBaseTime);
		doGrigBagGroupPanels(pnlStandart, lblStandartNum, tfStandartNum, lblStandartCommons, tfStandartCommons, lblStandartTime, tfStandartTime);
		doGrigBagGroupPanels(pnlAdvanced, lblAdvancedNum, tfAdvancedNum, lblAdvancedCommons, tfAdvancedCommons, lblAdvancedTime, tfAdvancedTime);
		doGrigBagGroupPanels(pnlReserve, lblReserveNum, tfReserveNum, lblReserveCommons, tfReserveCommons, lblReserveTime, tfReserveTime);
	}
	
	private void doGrigBagGroupPanels(JPanel panel, JLabel lbl1, JTextField tf1, JLabel lbl2, JTextField tf2,
			 JLabel lbl3, JTextField tf3) {
		panel.setLayout(new GridBagLayout());
		
		//gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets(top, left, botom, right), ipadx, ipady
		
		// 1st row
		panel.add(lbl1, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 0));
		
		panel.add(tf1, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 0));
		// 2nd row
		panel.add(lbl2, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 0));
		
		panel.add(tf2, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 0));
		// 3rd row
		panel.add(lbl3, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 0));
		
		panel.add(tf3, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 0));
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
	
}

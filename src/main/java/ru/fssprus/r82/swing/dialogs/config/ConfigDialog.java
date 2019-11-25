package ru.fssprus.r82.swing.dialogs.config;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.utils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.ApplicationConfiguration;

public class ConfigDialog extends DialogWithPassword {
	private static final long serialVersionUID = -3585887095900374897L;
	private static final String SECTION = AppConstants.CONFIG_DIALOG;
	private static final String TITLE = AppConstants.CONFIG_TEXT;
	private static final String ICON = AppConstants.CONFIG_ICON;
	
	private static final String BTN_SAVE_CAPTION = "Cохранить изменения";
	
	private static final String CAPT_AMOUNT = "Количество:";
	private static final String CAPT_COMMON_PERC = "Процент Общих:";
	private static final String CAPT_TIME_OF_TEST = "Время теста(секунд):";
	
	private JLabel lblBaseNum = new JLabel(CAPT_AMOUNT);
	private JLabel lblBaseCommons = new JLabel(CAPT_COMMON_PERC);
	private JLabel lblBaseTime = new JLabel(CAPT_TIME_OF_TEST);
	
	private JPanel pnlBase = new JPanel();
	
	private JLabel lblStandartNum = new JLabel(CAPT_AMOUNT);
	private JLabel lblStandartCommons = new JLabel(CAPT_COMMON_PERC);
	private JLabel lblStandartTime = new JLabel(CAPT_TIME_OF_TEST);
	
	private JPanel pnlStandart = new JPanel();
	
	private JLabel lblAdvancedNum = new JLabel(CAPT_AMOUNT);
	private JLabel lblAdvancedCommons = new JLabel(CAPT_COMMON_PERC);
	private JLabel lblAdvancedTime = new JLabel(CAPT_TIME_OF_TEST);
	
	private JPanel pnlAdvanced = new JPanel();
	
	private JLabel lblReserveNum = new JLabel(CAPT_AMOUNT);
	private JLabel lblReserveCommons = new JLabel(CAPT_COMMON_PERC);
	private JLabel lblReserveTime = new JLabel(CAPT_TIME_OF_TEST);
	
	private JPanel pnlReserve = new JPanel();
	
	private JTextField tfBaseNum = new JTextField();
	private JTextField tfBaseCommons = new JTextField();
	private JTextField tfBaseTime = new JTextField();
	
	private JTextField tfStandartNum = new JTextField();
	private JTextField tfStandartCommons = new JTextField();
	private JTextField tfStandartTime = new JTextField();
	
	private JTextField tfAdvancedNum = new JTextField();
	private JTextField tfAdvancedCommons = new JTextField();
	private JTextField tfAdvancedTime = new JTextField();
	
	private JTextField tfReserveNum = new JTextField();
	private JTextField tfReserveCommons = new JTextField();
	private JTextField tfReserveTime = new JTextField();
	
	private JButton btnSave = new JGreenButton(BTN_SAVE_CAPTION);
	
	private List<JTextField> tfsList = new ArrayList<JTextField>();
	
	public ConfigDialog(int width, int height, JFrame parent) {
		super(width, height, parent);
	}
	
	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}
	
	@Override
	public void init() {
		initComponents();
		
		setGroupPanelBorders();
		layoutGroupPanels();
		setTfsNames();
		fillTfsList();
		layoutDialog();
		
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}
	
	@Override
	protected String getTitleText() {
		return TITLE;
	}
	
	private void loadTFsText() {
		 tfBaseNum.setText(String.valueOf(ApplicationConfiguration.getItem("base.num")));
		 tfBaseCommons.setText(String.valueOf(ApplicationConfiguration.getItem("base.common.percent")));
		 tfBaseTime.setText(String.valueOf(ApplicationConfiguration.getItem("base.time")));
		
		 tfStandartNum.setText(String.valueOf(ApplicationConfiguration.getItem("standart.num")));
		 tfStandartCommons.setText(String.valueOf(ApplicationConfiguration.getItem("standart.common.percent")));
		 tfStandartTime.setText(String.valueOf(ApplicationConfiguration.getItem("standart.time")));
		
		 tfAdvancedNum.setText(String.valueOf(ApplicationConfiguration.getItem("advanced.num")));
		 tfAdvancedCommons.setText(String.valueOf(ApplicationConfiguration.getItem("advanced.common.percent")));
		 tfAdvancedTime.setText(String.valueOf(ApplicationConfiguration.getItem("advanced.time")));
		
		 tfReserveNum.setText(String.valueOf(ApplicationConfiguration.getItem("reserve.num")));
		 tfReserveCommons.setText(String.valueOf(ApplicationConfiguration.getItem("reserve.common.percent")));
		 tfReserveTime.setText(String.valueOf(ApplicationConfiguration.getItem("reserve.time")));
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
	
	public void fillTfsList() {
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
		loadTFsText();
		btnSave.setEnabled(false);
	}
	
	@Override
	protected void layoutDialog() {
		JPanel contentPanel = getContentPanel();
		contentPanel.setLayout(new GridBagLayout());
		
		//gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets(top, left, botom, right), ipadx, ipady
		
		// 1st row
		contentPanel.add(pnlBase, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		contentPanel.add(pnlStandart, new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		// 2nd row
		contentPanel.add(pnlAdvanced, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		contentPanel.add(pnlReserve, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		// 3rd row
		contentPanel.add(btnSave, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 20, 0, 20), 0, 0));
		
	}
	
	private void setGroupPanelBorders() {
		pnlBase.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Базовый.toString()));
		pnlStandart.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Стандартный.toString()));
		pnlAdvanced.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Продвинутый.toString()));
		pnlReserve.setBorder(BorderFactory.createTitledBorder(QuestionLevel.Резерв.toString()));
		
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

	public List<JTextField> getTfsList() {
		return tfsList;
	}

	public void setTfsList(List<JTextField> tfsList) {
		this.tfsList = tfsList;
	}
}
package ru.fssprus.r82.swing.dialogs.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ru.fssprus.r82.swing.dialogs.CommonDialog;
import ru.fssprus.r82.swing.ulils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.Utils;

public class TestDialog extends CommonDialog {
	private static final long serialVersionUID = 1355222097401941564L;
	private static final String SECTION = AppConstants.TEST_DIALOG;
	private static final String TITLE = AppConstants.TEST_TEXT;
	private static final String ICON = AppConstants.TEST_ICON;

	private static final String BTN_TO_NEXT_CAPTION = "К следующему";
	private static final String BTN_FINISH_CAPTION = "Закончить тест";
	private static final String BTN_NEXT_CAPTION = ">";
	private static final String BTN_PREVIOUS_CAPTION = "<";

	private static final int AMT_RAD_BUTTONS = 5;

	private JPanel pnlQuizzControll = new JPanel();

	private JPanel pnlQuestAndAnswers = new JPanel();
	private JPanel pnlAnswers = new JPanel();
	private JButton btnNextUnanswered = new JButton(BTN_TO_NEXT_CAPTION);
	private JButton btnFinish = new JButton(BTN_FINISH_CAPTION);

	private JLabel lblQuestionInfo = new JLabel();
	private JTextArea taQuestionText = new JTextArea();
	private ArrayList<JCheckBox> cbAnswers = new ArrayList<JCheckBox>(AMT_RAD_BUTTONS);

	private JPanel pnlDown = new JPanel();
	private JButton btnNext = new JGreenButton(BTN_NEXT_CAPTION);
	private JButton btnPrevious = new JGreenButton(BTN_PREVIOUS_CAPTION);
	private JLabel lblTimeLeftSec = new JLabel();

	private boolean isPaused = false;

	public TestDialog(int width, int height) {
		super(width, height);
	}

	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}

	@Override
	public void init() {
		fillBgAnswers();
		getRootPane().setDefaultButton(btnNext);
		requestFocus();
		super.init();
	}

	@Override
	protected void layoutDialog() {
		setFonts();

		layoutPanelQuizzControll();
		layoutPanelQuestAndAnswers();
		layoutPanelDown();

		addComponents();

		setVisible(true);
	}

	private void addComponents() {
		JPanel contentPanel = getContentPanel();

		contentPanel.add(pnlQuizzControll, BorderLayout.NORTH);

		contentPanel.add(pnlQuestAndAnswers, BorderLayout.CENTER);

		contentPanel.add(pnlDown, BorderLayout.SOUTH);
	}

	@Override
	protected String getSection() {
		return SECTION;
	}

	@Override
	protected String getTitleText() {
		return TITLE;
	}

	private void layoutPanelQuizzControll() {
		pnlQuizzControll.add(btnFinish);
		pnlQuizzControll.add(btnNextUnanswered);
	}

	private void layoutPanelDown() {
		btnPrevious.setEnabled(false);
		pnlDown.add(btnPrevious);
		pnlDown.add(lblTimeLeftSec);
		pnlDown.add(btnNext);
	}

	private void layoutPanelQuestAndAnswers() {
		initTaQuestionText();
		initPanelAnswers();
		initLblQuestionInfo();

		pnlQuestAndAnswers.setLayout(new BoxLayout(pnlQuestAndAnswers, BoxLayout.Y_AXIS));

		pnlQuestAndAnswers.add(Box.createRigidArea(new Dimension(AppConstants.TESTDIALOG_PNLQNA_RIGID_AREA_WIDTH,
				AppConstants.TESTDIALOG_PNLQNA_RIGID_AREA_HEIGHT)));

		pnlQuestAndAnswers.add(lblQuestionInfo);

		pnlQuestAndAnswers.add(taQuestionText);

		pnlQuestAndAnswers.add(pnlAnswers);
	}

	private void initTaQuestionText() {
		taQuestionText.setEnabled(false);
		taQuestionText.setWrapStyleWord(true);
		taQuestionText.setLineWrap(true);
		taQuestionText.setDisabledTextColor(Color.BLACK);

		taQuestionText.setPreferredSize(
				new Dimension(this.getWidth(), Utils.countTestDialogTaQuestionHeight(this.getHeight())));

		taQuestionText.setBackground(pnlQuestAndAnswers.getBackground());

		taQuestionText.setAlignmentX(JComponent.LEFT_ALIGNMENT);

	}

	private void initPanelAnswers() {
		pnlAnswers.setBackground(Color.YELLOW);

		pnlAnswers.setAlignmentX(JComponent.LEFT_ALIGNMENT);

		pnlAnswers.setPreferredSize(
				new Dimension(this.getWidth(), Utils.countTestDialogPnlAnswersHeight(this.getHeight())));

		pnlAnswers.setLayout(new GridBagLayout());

		for (int i = 0; i < AMT_RAD_BUTTONS; i++) {
			pnlAnswers.add(cbAnswers.get(i), new GridBagConstraints(0, i, GridBagConstraints.REMAINDER, 1, 0, 0,
					GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
		}
	}

	private void initLblQuestionInfo() {

		lblQuestionInfo
				.setPreferredSize(new Dimension(this.getWidth(), AppConstants.TESTDIALOG_LBL_QUESTION_INFO_HEIGHT));

		lblQuestionInfo.setAlignmentX(JComponent.LEFT_ALIGNMENT);
	}

	private void setFonts() {
		Font fontHeader = AppConstants.TESTDIALOG_HEADER_FONT;
		Font fontQuestion = AppConstants.TESTDIALOG_QUESTION_FONT;
		Font fontItems = AppConstants.TESTDIALOG_ITEMS_FONT;

		lblQuestionInfo.setFont(fontHeader);
		taQuestionText.setFont(fontQuestion);
		for (int i = 0; i < AMT_RAD_BUTTONS; i++) {
			cbAnswers.get(i).setFont(fontItems);
			cbAnswers.get(i).setForeground(Color.BLACK);
		}

	}

	private void fillBgAnswers() {
		for (int i = 0; i < AMT_RAD_BUTTONS; i++) {
			JCheckBox jrb = new JCheckBox();
			cbAnswers.add(jrb);
		}
	}

	public JLabel getLblQuestionInfo() {
		return lblQuestionInfo;
	}

	public void setLblQuestionInfo(JTextArea taQuestionText) {
		this.taQuestionText = taQuestionText;
	}

	public JTextArea getLblQuestionText() {
		return taQuestionText;
	}

	public void setLblQuestionText(JTextArea taQuestionText) {
		this.taQuestionText = taQuestionText;
	}

	public ArrayList<JCheckBox> getCbAnswers() {
		return cbAnswers;
	}

	public void setRbAnswers(ArrayList<JCheckBox> cbAnswers) {
		this.cbAnswers = cbAnswers;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}

	public JButton getBtnPrevious() {
		return btnPrevious;
	}

	public void setBtnPrevious(JButton btnPrevious) {
		this.btnPrevious = btnPrevious;
	}

	public JLabel getLblTimeLeftSec() {
		return lblTimeLeftSec;
	}

	public void setLblTimeLeftSec(JLabel lblTimeLeftSec) {
		this.lblTimeLeftSec = lblTimeLeftSec;
	}

	public JButton getBtnNextUnanswered() {
		return btnNextUnanswered;
	}

	public void setBtnNextUnanswered(JButton btnNextUnanswered) {
		this.btnNextUnanswered = btnNextUnanswered;
	}

	public JButton getBtnFinish() {
		return btnFinish;
	}

	public void setBtnFinish(JButton btnFinish) {
		this.btnFinish = btnFinish;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

}
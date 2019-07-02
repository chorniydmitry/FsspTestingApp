package ru.fssprus.r82.swing.dialogs.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import ru.fssprus.r82.swing.dialogs.CommonDialog;
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
	private ArrayList<JRadioButton> rbAnswers = new ArrayList<JRadioButton>(AMT_RAD_BUTTONS);
	private ButtonGroup bgAnswers = new ButtonGroup();

	private JPanel pnlDown = new JPanel();
	private JButton btnNext = new JButton(BTN_NEXT_CAPTION);
	private JButton btnPrevious = new JButton(BTN_PREVIOUS_CAPTION);
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
		layoutPanelQuizzControll();
		layoutPanelDown();

		setFonts();
		layoutPanelQuestAndAnswers();
		setLayout(new BorderLayout());

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

	private void initPanelAnswers() {
		pnlAnswers.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		pnlAnswers.setPreferredSize(new Dimension(
				this.getWidth(), 
				Utils.countTestDialogPnlAnswersHeight(this.getHeight())));
		
		pnlAnswers.setLayout(new BoxLayout(pnlAnswers, BoxLayout.Y_AXIS));

		for (int i = 0; i < AMT_RAD_BUTTONS; i++) {
			pnlAnswers.add(rbAnswers.get(i));
			pnlAnswers.add(Box.createRigidArea(new Dimension(AppConstants.TESTDIALOG_PNL_ANSWERS_RIGID_AREA_WIDTH,
					AppConstants.TESTDIALOG_PNL_ANSWERS_RIGID_AREA_HEIGHT)));
		}
	}
	
	private void initTaQuestionText() {
		taQuestionText.setEnabled(false);
		taQuestionText.setWrapStyleWord(true);
		taQuestionText.setLineWrap(true);
		taQuestionText.setDisabledTextColor(Color.BLACK);
		
		taQuestionText.setPreferredSize(new Dimension(
				this.getWidth(), 
				Utils.countTestDialogTaQuestionHeight(this.getHeight())));
		
		taQuestionText.setBackground(pnlQuestAndAnswers.getBackground());
		
		taQuestionText.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
	}
	
	private void initLblQuestionInfo() {
		lblQuestionInfo.setPreferredSize(new Dimension(
				this.getWidth(), 
				AppConstants.TESTDIALOG_LBL_QUESTION_INFO_HEIGHT));
		
		lblQuestionInfo.setAlignmentX(JComponent.LEFT_ALIGNMENT);
	}

	private void layoutPanelQuestAndAnswers() {
		initTaQuestionText();
		initPanelAnswers();
		initLblQuestionInfo();

		pnlQuestAndAnswers.setLayout(new BoxLayout(pnlQuestAndAnswers, BoxLayout.Y_AXIS));

		pnlQuestAndAnswers.add(Box.createRigidArea(new Dimension(
				AppConstants.TESTDIALOG_PNLQNA_RIGID_AREA_WIDTH, 
				AppConstants.TESTDIALOG_PNLQNA_RIGID_AREA_HEIGHT)));

		
		pnlQuestAndAnswers.add(lblQuestionInfo);
		
		pnlQuestAndAnswers.add(taQuestionText);
		
		pnlQuestAndAnswers.add(pnlAnswers);
	}

	public void hideInterface(boolean hide) {
		if (hide) {
			setPaused(hide);
			taQuestionText.setBackground(Color.BLACK);
			pnlAnswers.setBackground(Color.BLACK);
			rbAnswers.forEach((n) -> n.setBackground(Color.BLACK));
		} else {
			setPaused(hide);
			taQuestionText.setBackground(pnlQuestAndAnswers.getBackground());
			pnlAnswers.setBackground(pnlQuestAndAnswers.getBackground());
			rbAnswers.forEach((n) -> n.setBackground(pnlQuestAndAnswers.getBackground()));
		}
		revalidate();
	}

	private void setFonts() {
		Font fontHeader = AppConstants.TESTDIALOG_HEADER_FONT;
		Font fontQuestion = AppConstants.TESTDIALOG_QUESTION_FONT;
		Font fontItems = AppConstants.TESTDIALOG_ITEMS_FONT;

		lblQuestionInfo.setFont(fontHeader);
		taQuestionText.setFont(fontQuestion);
		for (int i = 0; i < AMT_RAD_BUTTONS; i++) {
			rbAnswers.get(i).setFont(fontItems);
			rbAnswers.get(i).setForeground(Color.BLACK);
		}

	}

	private void fillBgAnswers() {
		for (int i = 0; i < AMT_RAD_BUTTONS; i++) {
			JRadioButton jrb = new JRadioButton();
			rbAnswers.add(jrb);
			bgAnswers.add(jrb);
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

	public ArrayList<JRadioButton> getRbAnswers() {
		return rbAnswers;
	}

	public void setRbAnswers(ArrayList<JRadioButton> rbAnswers) {
		this.rbAnswers = rbAnswers;
	}

	public ButtonGroup getBgAnswers() {
		return bgAnswers;
	}

	public void setBgAnswers(ButtonGroup bgAnswers) {
		this.bgAnswers = bgAnswers;
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
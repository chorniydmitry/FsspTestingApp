package ru.fssprus.r82.swing.dialogs.testDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import ru.fssprus.r82.swing.dialogs.CommonDialog;
import ru.fssprus.r82.utils.AppConstants;

public class TestDialog extends CommonDialog {
	private static final long serialVersionUID = 1355222097401941564L;
	private static final String SECTION = AppConstants.TEST_SECTION;
	
	private static final int AMT_RAD_BUTTONS = 5;
	
	private JPanel pnlTop = new JPanel();
	
	private JPanel pnlQuestAndAnswers = new JPanel();
	private JPanel pnlAnswers = new JPanel();
	private JButton btnNextUnanswered = new JButton("К следующему");
	private JButton btnPause = new JButton("Пауза");
	private JButton btnFinish = new JButton("Закончить тест");
	
	private JLabel lblQuestionInfo = new JLabel();
	private JTextArea taQuestionText = new JTextArea();
	private ArrayList<JRadioButton> rbAnswers = new ArrayList<JRadioButton>(AMT_RAD_BUTTONS);
	private ButtonGroup bgAnswers = new ButtonGroup();
	
	private JPanel pnlDown = new JPanel();
	private JButton btnNext =  new JButton(">");
	private JButton btnPrevious = new JButton("<");
	private JLabel lblTimeLeftSec = new JLabel();
	
	private boolean isPaused = false;
	
	public TestDialog(int width, int height) {
		super(width, height);
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
		layoutPanelTop();
		layoutPanelDown();
		
		setFonts();
		layoutPanelAnswers();
		layoutPanelQuestAndAnswers();
		setLayout(new BorderLayout());
		
		add(pnlTop, BorderLayout.NORTH);
		
		add(pnlQuestAndAnswers, BorderLayout.CENTER);
		
		add(pnlDown, BorderLayout.SOUTH);
		
		setVisible(true);
		
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}
	
	private void layoutPanelTop() {
		pnlTop.add(btnFinish);
		pnlTop.add(btnPause);
		pnlTop.add(btnNextUnanswered);
	}

	private void layoutPanelDown() {
		btnPrevious.setEnabled(false);
		pnlDown.add(btnPrevious);
		pnlDown.add(lblTimeLeftSec);
		pnlDown.add(btnNext);
	}

	private void layoutPanelAnswers() {
		pnlAnswers.setLayout(new BoxLayout(pnlAnswers, BoxLayout.Y_AXIS));
	
		for (int i = 0; i < AMT_RAD_BUTTONS; i++) {
			pnlAnswers.add(rbAnswers.get(i));
			pnlAnswers.add(Box.createRigidArea(new Dimension(0,20)));
		}
	}

	private void layoutPanelQuestAndAnswers() {
		taQuestionText.setEnabled(false);
		taQuestionText.setWrapStyleWord(true);
		taQuestionText.setLineWrap(true);
		taQuestionText.setDisabledTextColor(Color.BLACK);
		
		JPanel pnl = pnlQuestAndAnswers;
		
		lblQuestionInfo.setPreferredSize(new Dimension(this.getWidth(), 60));
		taQuestionText.setPreferredSize(new Dimension(this.getWidth(), ((2 * this.getHeight() / 3)-60)));
		pnlAnswers.setPreferredSize(new Dimension(this.getWidth(), (this.getHeight()/3)));
		
		taQuestionText.setBackground(pnl.getBackground());
		
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		
		pnl.add(Box.createRigidArea(new Dimension(50,50)));
		
		lblQuestionInfo.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		pnl.add(lblQuestionInfo);
		taQuestionText.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		pnl.add(taQuestionText);
		pnlAnswers.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		pnl.add(pnlAnswers);
	}

	public void hideInterface(boolean hide) {
		if(hide) {
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
		Font fontHeader = new Font("Courier New", Font.BOLD, 28);
		Font fontQuestion = new Font("Courier New", Font.PLAIN, 20);
		Font fontItems = new Font("Courier New", Font.PLAIN, 16);
		
		lblQuestionInfo.setFont(fontHeader);
		taQuestionText.setFont(fontQuestion);
		for(int i = 0; i < AMT_RAD_BUTTONS; i++) {
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

	public JButton getBtnPause() {
		return btnPause;
	}

	public void setBtnPause(JButton btnPause) {
		this.btnPause = btnPause;
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
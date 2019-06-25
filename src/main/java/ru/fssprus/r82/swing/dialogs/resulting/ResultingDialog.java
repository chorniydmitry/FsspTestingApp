package ru.fssprus.r82.swing.dialogs.resulting;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;

import ru.fssprus.r82.swing.dialogs.CommonDialog;
import ru.fssprus.r82.utils.AppConstants;

public class ResultingDialog extends CommonDialog {
	private static final long serialVersionUID = -8599425813178121954L;
	private static final String SECTION = "RESULTS";

	private static final String LBL_MARK_PERCENT = "В процентах: ";
	private static final String LBL_MARK_ONE_TO_FIVE_MARK = "Оценка: ";
	private static final String LBL_CORRECTS_TOTAL = " из ";
	private static final String LBL_CORRECTS_TOTAL_CORRECTS = "Всего верных ответов: ";
	private static final String LBL_MSG_RESULT_CAPTION = "Ваш результат:";
	private static final String BTN_SHOW_ERRORS_CAPTION = "Показать ошибки";
	private static final String BTN_CLOSE_CAPTION = "Завершить";
	
	private JLabel lblMessageResult = new JLabel(LBL_MSG_RESULT_CAPTION);
	private JLabel lblMarkPercent = new JLabel();
	private JLabel lblMarkOneToFive = new JLabel();
	private JLabel lblMarkText = new JLabel();
	private JLabel lblMarkLetter = new JLabel();
	private JLabel lblCorrectAnswers = new JLabel();

	private JButton btnShowWrongs = new JButton(BTN_SHOW_ERRORS_CAPTION);
	private JButton btnClose = new JButton(BTN_CLOSE_CAPTION);

	private Color markColor = Color.BLACK;

	public ResultingDialog(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void init() {
		initFonts();
		super.init();
	}
	
	@Override
	protected void layoutDialog() {
		this.setLayout(new GridBagLayout());
		// gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets,
		// ipadx, ipady
		
		// 1st row
		this.add(lblMessageResult, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		// 2nd row
		this.add(lblCorrectAnswers, new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// 3rd row
		this.add(lblMarkPercent, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// 4th row
		this.add(lblMarkOneToFive, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		
		this.add(lblMarkLetter, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// 5th row
		this.add(lblMarkText, new GridBagConstraints(0, 4, 2, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		// 6th row
		this.add(btnShowWrongs, new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		this.add(btnClose, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}

	private void initFonts() {
		Font f = AppConstants.RESULTDIALOG_TEXT_FONT;
		lblMessageResult.setFont(f);
		lblMarkPercent.setFont(f);
		lblMarkOneToFive.setFont(f);
		lblMarkLetter.setFont(f);
		lblMarkText.setFont(f);
		lblCorrectAnswers.setFont(f);
		lblMarkText.setForeground(markColor);
	}

	public void setCaptions(int correctAnswers, int totalQuestions, String markPercent, String markOneToFive,
			String markText, String markLetter) {
		lblCorrectAnswers.setText(LBL_CORRECTS_TOTAL_CORRECTS + correctAnswers + LBL_CORRECTS_TOTAL + totalQuestions);
		lblMarkPercent.setText(LBL_MARK_PERCENT + markPercent + '%');
		lblMarkOneToFive.setText(LBL_MARK_ONE_TO_FIVE_MARK + markOneToFive);
		lblMarkText.setText(markText);
		lblMarkLetter.setText('(' + markLetter +  ')');
	}

	public Color getMarkColor() {
		return markColor;
	}

	public void setMarkColor(Color markColor) {
		lblMarkText.setForeground(markColor);
		this.markColor = markColor;
	}

	public JButton getBtnShowWrongs() {
		return btnShowWrongs;
	}

	public void setBtnShowWrongs(JButton btnShowWrongs) {
		this.btnShowWrongs = btnShowWrongs;
	}

	public JButton getBtnClose() {
		return btnClose;
	}

	public void setBtnClose(JButton btnClose) {
		this.btnClose = btnClose;
	}
}

package ru.fssprus.r82.swing.main.resultingDialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ResultingDialog extends JDialog {
	private static final long serialVersionUID = -8599425813178121954L;

	private JLabel lblMessageResult = new JLabel("Ваш результат:");
	private JLabel lblMarkPercent = new JLabel();
	private JLabel lblMarkOneToFive = new JLabel();
	private JLabel lblMarkText = new JLabel();
	private JLabel lblMarkLetter = new JLabel();
	private JLabel lblCorrectAnswers = new JLabel();

	private JButton btnShowWrongs = new JButton("Показать ошибки");
	private JButton btnClose = new JButton("Завершить");

	private Color markColor = new Color(0x000000);

	public ResultingDialog(int width, int height) {
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);

		initFonts();
		layoutComponents();

		setVisible(true);
	}

	private void initFonts() {
		Font f = new Font("Courier New", Font.BOLD, 18);
		lblMessageResult.setFont(f);
		lblMarkPercent.setFont(f);
		lblMarkOneToFive.setFont(f);
		lblMarkLetter.setFont(f);
		lblMarkText.setFont(f);
		lblCorrectAnswers.setFont(f);
		lblMarkText.setForeground(markColor);
	}

	private void layoutComponents() {
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

	public void setCaptions(int correctAnswers, int totalQuestions, String markPercent, String markOneToFive,
			String markText, String markLetter) {
		lblCorrectAnswers.setText("Всего верных ответов: " + correctAnswers + " из " + totalQuestions);
		lblMarkPercent.setText("В процентах: " + markPercent + "%");
		lblMarkOneToFive.setText("Оценка: " + markOneToFive);
		lblMarkText.setText(markText);
		lblMarkLetter.setText("(" + markLetter +  ")");
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

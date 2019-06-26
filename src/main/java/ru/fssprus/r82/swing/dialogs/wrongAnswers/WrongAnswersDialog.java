package ru.fssprus.r82.swing.dialogs.wrongAnswers;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import ru.fssprus.r82.swing.dialogs.CommonDialog;
import ru.fssprus.r82.utils.AppConstants;

public class WrongAnswersDialog extends CommonDialog {
	private static final long serialVersionUID = 3594882748640500638L;
	private static final String SECTION = "WRONG_ANS";
	
	private static final String BTN_CLOSE_CAPTION = "Закрыть";
	
	private JEditorPane taWrongs = new JEditorPane();
	private JLabel lblTimeLeftSec = new JLabel();
	private JButton btnClose = new JButton(BTN_CLOSE_CAPTION);
	
	public WrongAnswersDialog(int width, int height) {
		super(width, height);
	}
	
	@Override
	protected void layoutDialog() {
		taWrongs.setContentType(AppConstants.CONTENT_TYPE_HTML);
		JScrollPane scroller = new JScrollPane(taWrongs,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setPreferredSize(new Dimension(
				this.getWidth(), 
				this.getHeight() - AppConstants.WADIALOG_TAWRONGS_HEIGHT_PADDING));
		
		addComponents(scroller);
		
	}

	private void addComponents(JScrollPane scroller) {
		this.add(lblTimeLeftSec, BorderLayout.NORTH);
		this.add(scroller, BorderLayout.CENTER);
		this.add(btnClose, BorderLayout.SOUTH);
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}

	public JEditorPane getTaWrongs() {
		return taWrongs;
	}

	public void setTaWrongs(JEditorPane taWrongs) {
		this.taWrongs = taWrongs;
	}

	public JLabel getLblTimeLeftSec() {
		return lblTimeLeftSec;
	}

	public void setLblTimeLeftSec(JLabel lblTimeLeftSec) {
		this.lblTimeLeftSec = lblTimeLeftSec;
	}

	public JButton getBtnClose() {
		return btnClose;
	}

	public void setBtnClose(JButton btnClose) {
		this.btnClose = btnClose;
	}
}

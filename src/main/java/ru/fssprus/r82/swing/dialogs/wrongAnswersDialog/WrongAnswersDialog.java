package ru.fssprus.r82.swing.dialogs.wrongAnswersDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import ru.fssprus.r82.swing.dialogs.CommonDialog;

public class WrongAnswersDialog extends CommonDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3594882748640500638L;
	private JEditorPane taWrongs = new JEditorPane();
	private JLabel lblTimeLeftSec = new JLabel();
	private JButton btnClose = new JButton("Закрыть");
	
	public WrongAnswersDialog(int width, int height) {
		super(width, height);
	}
	
	@Override
	protected void layoutDialog() {
		taWrongs.setContentType("text/html");
		JScrollPane scroller = new JScrollPane(taWrongs,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-50));
		this.add(lblTimeLeftSec, BorderLayout.NORTH);
		this.add(scroller, BorderLayout.CENTER);
		this.add(btnClose, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
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

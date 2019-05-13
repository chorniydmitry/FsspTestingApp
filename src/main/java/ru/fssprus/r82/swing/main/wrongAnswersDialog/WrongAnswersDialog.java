package ru.fssprus.r82.swing.main.wrongAnswersDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class WrongAnswersDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3594882748640500638L;
	private JEditorPane taWrongs = new JEditorPane();
	private JLabel lblTimeLeftSec = new JLabel();
	private JButton btnClose = new JButton("Закрыть");
	
	public WrongAnswersDialog(int width, int height, String title) {
		this.setSize(new Dimension(width, height));
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		initDialog();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initDialog() {
		taWrongs.setContentType("text/html");
		JScrollPane scroller = new JScrollPane(taWrongs,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-50));
		this.add(lblTimeLeftSec, BorderLayout.NORTH);
		this.add(scroller, BorderLayout.CENTER);
		this.add(btnClose, BorderLayout.SOUTH);
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

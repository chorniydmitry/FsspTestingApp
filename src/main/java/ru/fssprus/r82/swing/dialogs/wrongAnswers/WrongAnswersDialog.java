package ru.fssprus.r82.swing.dialogs.wrongAnswers;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ru.fssprus.r82.swing.dialogs.CommonDialog;
import ru.fssprus.r82.swing.utils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;

/**
 * @author Chernyj Dmitry
 *
 */
public class WrongAnswersDialog extends CommonDialog {
	private static final long serialVersionUID = 3594882748640500638L;
	private static final String SECTION = AppConstants.WRONG_ANSWERS_DIALOG;
	private static final String TITLE = AppConstants.WRONG_ANSWERS_TEXT;
	private static final String ICON = AppConstants.WRONG_ANSWERS_ICON;
	
	private static final String BTN_CLOSE_CAPTION = "Закрыть";
	
	private JEditorPane taWrongs = new JEditorPane();
	private JLabel lblTimeLeftSec = new JLabel();
	private JButton btnClose = new JGreenButton(BTN_CLOSE_CAPTION);
	
	public WrongAnswersDialog(int width, int height, JFrame parent) {
		super(width, height, parent);
	}
	
	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}
	
	@Override
	protected void layoutDialog() {
		taWrongs.setContentType(AppConstants.CONTENT_TYPE_HTML);
		taWrongs.setEditable(false);
		JScrollPane scroller = new JScrollPane(taWrongs,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setPreferredSize(new Dimension(
				this.getWidth(), 
				this.getHeight() - AppConstants.WADIALOG_TAWRONGS_HEIGHT_PADDING
				- AppConstants.TOP_PANEL_HEIGHT));
		
		addComponents(scroller);
		
	}

	private void addComponents(JScrollPane scroller) {
		JPanel contentPanel = getContentPanel();
		contentPanel.add(lblTimeLeftSec, BorderLayout.NORTH);
		contentPanel.add(scroller, BorderLayout.CENTER);
		contentPanel.add(btnClose, BorderLayout.SOUTH);
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	protected String getSection() {
		return SECTION;
	}
	
	@Override
	protected String getTitleText() {
		return TITLE;
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

	public JButton getBtnBottomClose() {
		return btnClose;
	}

	public void setBtnClose(JButton btnClose) {
		this.btnClose = btnClose;
	}
}

package ru.fssprus.r82.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.fssprus.r82.utils.AppConstants;

public abstract class CommonDialog extends JDialog {
	private static final long serialVersionUID = -933522897356606777L;
	protected boolean accesGained = false;
	private JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel pnlContent = new JPanel();
	private JLabel lblEmblem = new JLabel();
	private JLabel lblTitle = new JLabel();

	public CommonDialog(int width, int height) {
		setSize(new Dimension(width, height));
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlContent, BorderLayout.CENTER);
		
	}
	
	private void initTopPanel() {
		pnlTop.setPreferredSize(new Dimension(this.getWidth(), AppConstants.TOP_PANEL_HEIGHT));
		pnlTop.setBackground(AppConstants.FSSP_COLOR);
		
		pnlTop.add(lblEmblem);
		pnlTop.add(lblTitle);
	}
	
	protected void layoutPanelTop(String text, ImageIcon emblem) {
		initLblTitle();
		initTopPanel();
		lblTitle.setText(text);
		lblEmblem.setIcon(emblem);
	}
	
	private void initLblTitle() {
		lblTitle.setForeground(AppConstants.TOP_PANELS_TEXT_FONT_COLOR);
		lblTitle.setFont(AppConstants.TOP_PANELS_TEXT_FONT);
	}
	
	
	protected JPanel getContentPanel() {
		return pnlContent;
	}

	protected void loadDialog() {
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);

		setVisible(true);
	}

	public void init() {
		loadDialog();
		layoutDialog();
	}

	protected abstract void layoutDialog();
	protected abstract String getSection();
	protected abstract String getTitleText();
	protected abstract void layoutPanelTop();

	protected boolean isAccessGained() {
		return accesGained;
	}

}

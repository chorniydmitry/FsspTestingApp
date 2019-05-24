package ru.fssprus.r82.swing.main.questionListDialog;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class QuestionListDialog extends JDialog{
	private static final long serialVersionUID = -8319908967500731744L;
	private QuestionListTable tabQuestList = new QuestionListTable();
	private JPanel pnlBottom = new JPanel();
	private JPanel pnlTop = new JPanel();

	private JLabel lblQuestionName = new JLabel("Формулировка");
	private JLabel lblSpecs = new JLabel("Cпецификации");
	private JLabel lblLevels = new JLabel("Уровни");
	
	private JTextField tfQuestionName = new JTextField(25);
	private JTextField tfSpecs = new JTextField(25);
	private JTextField tfLevels = new JTextField(25);
	
	private JButton btnFilter = new JButton("Фильтр");
	
	private JButton btnNextPage = new JButton(">");
	private JButton btnPreviousPage = new JButton("<");
	private JLabel lblPageInfo = new JLabel();
	

	public QuestionListDialog(int width, int height) {
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		init();
		
		setVisible(true);
	}
	
	private void init() {
		initPanelTop();
		add(pnlTop);
		
		JScrollPane scrollPane = new JScrollPane(tabQuestList);		
		add(scrollPane);
		
		initPanelBottom();
		add(pnlBottom);
		
		repaint();
		revalidate();
	}
	
	private void initPanelTop() {
		
		pnlTop.add(lblQuestionName);
		pnlTop.add(tfQuestionName);
		pnlTop.add(lblSpecs);
		pnlTop.add(tfSpecs);
		pnlTop.add(lblLevels);
		pnlTop.add(tfLevels);
		pnlTop.add(btnFilter);
		
		pnlTop.setBorder(BorderFactory.createTitledBorder("Панель фильтрации"));
		
		pnlTop.setPreferredSize(new Dimension(getWidth(), 100));
		pnlTop.setVisible(true);
	}
	
	private void initPanelBottom() {
		pnlBottom.add(btnPreviousPage);
		pnlBottom.add(lblPageInfo);
		pnlBottom.add(btnNextPage);
		
		pnlBottom.setVisible(true);
	}
	
	public QuestionListTable getTabQuestList() {
		return tabQuestList;
	}

	public JButton getBtnFilter() {
		return btnFilter;
	}

	public void setBtnFilter(JButton btnFilter) {
		this.btnFilter = btnFilter;
	}

	public JButton getBtnNextPage() {
		return btnNextPage;
	}

	public void setBtnNextPage(JButton btnNextPage) {
		this.btnNextPage = btnNextPage;
	}

	public JButton getBtnPreviousPage() {
		return btnPreviousPage;
	}

	public void setBtnPreviousPage(JButton btnPreviousPage) {
		this.btnPreviousPage = btnPreviousPage;
	}

	public JLabel getLblPageInfo() {
		return lblPageInfo;
	}

	public void setLblPageInfo(JLabel lblPageInfo) {
		this.lblPageInfo = lblPageInfo;
	}

}

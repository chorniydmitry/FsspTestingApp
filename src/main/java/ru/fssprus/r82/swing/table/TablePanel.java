package ru.fssprus.r82.swing.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ru.fssprus.r82.swing.utils.JGreenButton;

/**
 * @author Chernyj Dmitry
 *
 */
public class TablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final String BTN_ADD_CAPTION = "Добавить";
	private static final String BTN_REMOVE_CAPTION = "Удалить";
	private static final String BTN_NEXT_CAPTION = ">";
	private static final String BTN_PREVIOUS_CAPTION = "<";
	private static final String LBL_PAGE_CAPTION = "Страница: ";

	private static final String TF_PAGE_DEF_TEXT = "1";
	private static final String LBL_PAGES_TOTAL_DEF_TEXT = " из ";
	
	private static final int TF_PAGE_LENGTH = 3;
	
	private JButton btnAdd;
	private JButton btnDelete;
	private JPanel pnlTop;
	
	private JButton btnNext;
	private JButton btnPrevious;
	private JLabel lblPage;
	private JTextField tfPage;
	private JLabel lblPagesTotal;
	private JPanel pnlBottom;
	
	private CommonTable commonTable;
	private JScrollPane scrollPane;
	
	public TablePanel(int[] widths, String[] names) {
		initTable(widths, names);
		initPanel();
		layoutPanel();
		
	}
	
	private void initTable(int[] widths, String[] names) {
		commonTable = new CommonTable(widths, names);
	}
	
	private void initPanel() {
		initComponents();
	}
	
	private void initComponents() {
		btnAdd = new JGreenButton(BTN_ADD_CAPTION);
		btnDelete = new JGreenButton(BTN_REMOVE_CAPTION);
		pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

		scrollPane = new JScrollPane(commonTable);
		
		btnNext = new JGreenButton(BTN_NEXT_CAPTION);
		lblPage = new JLabel(LBL_PAGE_CAPTION);
		tfPage = new JTextField(TF_PAGE_LENGTH);
		tfPage.setText(TF_PAGE_DEF_TEXT);
		lblPagesTotal = new JLabel(LBL_PAGES_TOTAL_DEF_TEXT);
		btnPrevious = new JGreenButton(BTN_PREVIOUS_CAPTION);
		pnlBottom = new JPanel();
			
	}
	
	private void layoutPanel() {
		layoutComponents();
		
		this.setLayout(new BorderLayout());
		this.add(pnlTop, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pnlBottom, BorderLayout.SOUTH);
		
	}
	
	private void layoutComponents() {
		pnlTop.add(btnAdd);
		pnlTop.add(btnDelete);
		
		pnlBottom.add(btnPrevious);
		pnlBottom.add(lblPage);
		pnlBottom.add(tfPage);
		pnlBottom.add(lblPagesTotal);
		pnlBottom.add(btnNext);
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(JButton btnAdd) {
		this.btnAdd = btnAdd;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
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

	public JTextField getTfPage() {
		return tfPage;
	}

	public void setTfPage(JTextField tfPage) {
		this.tfPage = tfPage;
	}

	public JLabel getLblPagesTotal() {
		return lblPagesTotal;
	}

	public void setLblPagesTotal(JLabel lblPagesTotal) {
		this.lblPagesTotal = lblPagesTotal;
	}

	public CommonTable getCommonTable() {
		return commonTable;
	}

	public void setCommonTable(CommonTable commonTable) {
		this.commonTable = commonTable;
	}

}

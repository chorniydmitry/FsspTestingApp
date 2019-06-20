package ru.fssprus.r82.swing.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TablePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
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
		btnAdd = new JButton("+");
		btnDelete = new JButton("-");
		pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

		scrollPane = new JScrollPane(commonTable);
		
		btnNext = new JButton(">");
		lblPage = new JLabel("Страница:");
		tfPage = new JTextField(3);
		tfPage.setText("1");
		lblPagesTotal = new JLabel("из 1");
		btnPrevious = new JButton("<");
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

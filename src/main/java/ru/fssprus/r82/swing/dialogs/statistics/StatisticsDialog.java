package ru.fssprus.r82.swing.dialogs.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.table.CommonTable;
import ru.fssprus.r82.swing.table.CommonTableModel;
import ru.fssprus.r82.swing.table.TablePanel;
import ru.fssprus.r82.swing.ulils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;

public class StatisticsDialog extends DialogWithPassword {
	private static final long serialVersionUID = -1487357130550152798L;
	private static final String SECTION = AppConstants.STATISTICS_DIALOG;
	private static final String TITLE = AppConstants.STATISTICS_TEXT;
	private static final String ICON = AppConstants.STATISTICS_ICON;

	private static final String LBL_FIO_CAPTION = "ФИО:";
	private static final String LBL_SPEC_CAPTION = "Специализация:";
	private static final String LBL_LVL_CAPTION = "Уровень:";
	private static final String LBL_MARK_CAPTION = "Результат:";

	private static final String BTN_FILTER_CAPTION = "Фильтр";
	private static final String LBL_CLEAR_CAPTION = "Сброс";

	private static final int PNL_FILTER_HEIGHT = 55;

	private TablePanel tablePanel;

	private JPanel pnlFilter = new JPanel();

	private JLabel lblSurNamLast = new JLabel(LBL_FIO_CAPTION);
	private JLabel lblSpecification = new JLabel(LBL_SPEC_CAPTION);
	private JLabel lblLevel = new JLabel(LBL_LVL_CAPTION);
	private JLabel lblMark = new JLabel(LBL_MARK_CAPTION);

	private JTextField tfSurNamLast = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JTextField tfSpecification = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JTextField tfLevel = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JTextField tfMark = new JTextField(AppConstants.QLDIALOG_TF_SIZE);

	private JGreenButton btnFilter = new JGreenButton(BTN_FILTER_CAPTION);
	private JGreenButton btnClearFilters = new JGreenButton(LBL_CLEAR_CAPTION);

	public StatisticsDialog(int width, int heigth, JFrame parent) {
		super(width, heigth, parent);
		initTablePanel();
		layoutFilterPanel();
	}

	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}

	private void layoutFilterPanel() {
		pnlFilter.setLayout(new GridBagLayout());

		pnlFilter.add(lblSurNamLast, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(tfSurNamLast, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(lblSpecification, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(tfSpecification, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(btnFilter, new GridBagConstraints(4, 0, 1, 2, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(btnClearFilters, new GridBagConstraints(5, 0, 1, 2, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(lblLevel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(tfLevel, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(lblMark, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(tfMark, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 2), 0, 0));

	}

	private void initTablePanel() {
		int[] widths = AppConstants.STATDIALOG_TABLE_COL_WIDTHS_ARR;
		String[] names = AppConstants.STATDIALOG_TABLE_COL_CAPTIONS_ARR;
		tablePanel = new TablePanel(widths, names);
	}

	public TablePanel getTabPanel() {
		return tablePanel;
	}

	public CommonTable getTable() {
		return tablePanel.getCommonTable();
	}

	public CommonTableModel getTableModel() {
		return getTable().getTabModel();
	}

	@Override
	protected void layoutDialog() {
		tablePanel.setPreferredSize(
				new Dimension(this.getWidth(), this.getHeight() - AppConstants.TOP_PANEL_HEIGHT - PNL_FILTER_HEIGHT));
		getContentPanel().add(pnlFilter, BorderLayout.NORTH);
		getContentPanel().add(tablePanel, BorderLayout.CENTER);
	}

	@Override
	protected String getSection() {
		return SECTION;
	}

	@Override
	protected String getTitleText() {
		return TITLE;
	}

	public JTextField getTfSurNamLast() {
		return tfSurNamLast;
	}

	public void setTfSurNamLast(JTextField tfSurNamLast) {
		this.tfSurNamLast = tfSurNamLast;
	}

	public JTextField getTfSpecification() {
		return tfSpecification;
	}

	public void setTfSpecification(JTextField tfSpecification) {
		this.tfSpecification = tfSpecification;
	}

	public JTextField getTfLevel() {
		return tfLevel;
	}

	public void setTfLevel(JTextField tfLevel) {
		this.tfLevel = tfLevel;
	}

	public JTextField getTfMark() {
		return tfMark;
	}

	public void setTfMark(JTextField tfMark) {
		this.tfMark = tfMark;
	}

	public JGreenButton getBtnFilter() {
		return btnFilter;
	}

	public void setBtnFilter(JGreenButton btnFilter) {
		this.btnFilter = btnFilter;
	}

	public JGreenButton getBtnClearFilters() {
		return btnClearFilters;
	}

	public void setBtnClearFilters(JGreenButton btnClearFilters) {
		this.btnClearFilters = btnClearFilters;
	}
}

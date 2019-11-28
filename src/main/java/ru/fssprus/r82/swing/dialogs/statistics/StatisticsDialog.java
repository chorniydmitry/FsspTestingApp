package ru.fssprus.r82.swing.dialogs.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cherny.jdatepicker.JDatePicker;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.table.CommonTable;
import ru.fssprus.r82.swing.table.CommonTableModel;
import ru.fssprus.r82.swing.table.TablePanel;
import ru.fssprus.r82.swing.utils.JGreenButton;
import ru.fssprus.r82.utils.AppConstants;

/**
 * @author Chernyj Dmitry
 *
 */
public class StatisticsDialog extends DialogWithPassword {
	private static final long serialVersionUID = -1487357130550152798L;
	private static final String SECTION = AppConstants.STATISTICS_DIALOG;
	private static final String TITLE = AppConstants.STATISTICS_TEXT;
	private static final String ICON = AppConstants.STATISTICS_ICON;

	private static final String LBL_FIO_CAPTION = "Фамилия:";
	private static final String LBL_SPEC_CAPTION = "Специализация:";
	private static final String LBL_LVL_CAPTION = "Уровень:";
	private static final String LBL_MARK_CAPTION = "Результат:";
	private static final String LBL_DATE_CAPTION_LESS = "Дата МЕНЬШЕ:";
	private static final String LBL_DATE_CAPTION_MORE = "Дата БОЛЬШЕ:";
	private static final String LBL_SCORE_CAPTION_LESS = "% МЕНЬШЕ";
	private static final String LBL_SCORE_CAPTION_MORE = "% БОЛЬШЕ";

	private static final String BTN_FILTER_CAPTION = "Фильтр";
	private static final String BTN_CLEAR_CAPTION = "Сброс";
	private static final String BTN_PRINT_CAPTION = "Печать";
	private static final String BTN_EXPORT_CAPTION = "Экспорт таблицы";
	
	private static final int PNL_FILTER_HEIGHT = 155;

	private TablePanel tablePanel;

	private JPanel pnlFilter = new JPanel();
	private JPanel pnlFilterButtons = new JPanel();

	private JLabel lblSurNamLast = new JLabel(LBL_FIO_CAPTION);
	private JLabel lblSpecification = new JLabel(LBL_SPEC_CAPTION);
	private JLabel lblLevel = new JLabel(LBL_LVL_CAPTION);
	private JLabel lblMark = new JLabel(LBL_MARK_CAPTION);
	private JLabel lblDateLess = new JLabel(LBL_DATE_CAPTION_LESS);
	private JLabel lblDateMore = new JLabel(LBL_DATE_CAPTION_MORE);
	private JLabel lblScoreLess = new JLabel(LBL_SCORE_CAPTION_LESS);
	private JLabel lblScoreMore = new JLabel(LBL_SCORE_CAPTION_MORE);

	private JTextField tfSurNamLast = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JDatePicker dpDateLess = new JDatePicker();
	private JDatePicker dpDateMore = new JDatePicker();
	private JTextField tfScoreLess = new JTextField(AppConstants.QLDIALOG_TF_SIZE);
	private JTextField tfScoreMore = new JTextField(AppConstants.QLDIALOG_TF_SIZE);

	private JComboBox<QuestionLevel> cbLevels = new JComboBox<>();
	private JComboBox<String> cbMarks = new JComboBox<>();
	private JComboBox<String> cbSpecs = new JComboBox<>();
	
	private JGreenButton btnFilter = new JGreenButton(BTN_FILTER_CAPTION);
	private JGreenButton btnClearFilters = new JGreenButton(BTN_CLEAR_CAPTION);
	private JGreenButton btnPrint = new JGreenButton(BTN_PRINT_CAPTION);
	private JGreenButton btnExportSheet = new JGreenButton(BTN_EXPORT_CAPTION);

	public StatisticsDialog(int width, int heigth, JFrame parent) {
		super(width, heigth, parent);
		initTablePanel();
		layoutFilterPanel();
		layoutFilterPanelButtons();
		
		getRootPane().setDefaultButton(btnFilter);
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

		pnlFilter.add(cbSpecs, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		//
		
		pnlFilter.add(lblScoreLess, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
		
		pnlFilter.add(tfScoreLess, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(lblMark, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(cbMarks, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 2), 0, 0));
		
		//
		
		pnlFilter.add(lblScoreMore, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
		
		pnlFilter.add(tfScoreMore, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
		
		pnlFilter.add(lblLevel, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

		pnlFilter.add(cbLevels, new GridBagConstraints(3, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
		
		//
		
		pnlFilter.add(lblDateMore, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
		
		pnlFilter.add(dpDateMore, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
		
		pnlFilter.add(lblDateLess, new GridBagConstraints(2, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
		
		pnlFilter.add(dpDateLess, new GridBagConstraints(3, 3, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
		
		//
		
		pnlFilter.add(pnlFilterButtons, new GridBagConstraints(0, 4, GridBagConstraints.REMAINDER, 1, 0, 0, GridBagConstraints.NORTH, 
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));

	}
	
	private void layoutFilterPanelButtons() {
		pnlFilterButtons.add(btnClearFilters);
		pnlFilterButtons.add(btnFilter);
		pnlFilterButtons.add(btnPrint);
		pnlFilterButtons.add(btnExportSheet);
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

	public JComboBox<String> getCbSpecs() {
		return cbSpecs;
	}

	public void setCbSpecs(JComboBox<String> cbSpecs) {
		this.cbSpecs = cbSpecs;
	}

	public JComboBox<QuestionLevel> getCbLevel() {
		return cbLevels;
	}

	public void setCbLevel(JComboBox<QuestionLevel> tfLevel) {
		this.cbLevels = tfLevel;
	}

	public JComboBox<String> getCbMarks() {
		return cbMarks;
	}

	public void setTfMark(JComboBox<String> tfMarks) {
		this.cbMarks = tfMarks;
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

	public JDatePicker getDpDateLess() {
		return dpDateLess;
	}

	public void setDpDateLess(JDatePicker dpDateLess) {
		this.dpDateLess = dpDateLess;
	}

	public JDatePicker getDpDateMore() {
		return dpDateMore;
	}

	public void setDpDateMore(JDatePicker dpDateMore) {
		this.dpDateMore = dpDateMore;
	}

	public JTextField getTfScoreLess() {
		return tfScoreLess;
	}

	public void setTfScoreLess(JTextField tfScoreLess) {
		this.tfScoreLess = tfScoreLess;
	}

	public JTextField getTfScoreMore() {
		return tfScoreMore;
	}

	public void setTfScoreMore(JTextField tfScoreMore) {
		this.tfScoreMore = tfScoreMore;
	}

	public JGreenButton getBtnPrint() {
		return btnPrint;
	}

	public JGreenButton getBtnExportSheet() {
		return btnExportSheet;
	}
	
}

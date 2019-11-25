package ru.fssprus.r82.swing.table;

/**
 * @author Chernyj Dmitry
 *
 */
public interface UpdatableController {
	abstract void edit(int index);
	abstract void nextPage();
	abstract void previousPage();
	abstract void delete(int index);
	abstract void goToPage(int page);
}

package ru.fssprus.r82.swing.ulils;

public interface UpdatableController {
	void edit(int index);
	void nextPage();
	void previousPage();
	void delete(int index);
	void goToPage(int page);
}

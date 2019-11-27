package ru.fssprus.r82.utils;

import java.awt.Color;

/**
 * @author Chernyj Dmitry
 *
 */
public class MarkCounter {

	private static final int A = 90;
	private static final int B = 80;
	private static final int C = 70;

	private static final String A_WORDS = "ОТЛИЧНО";
	private static final String B_WORDS = "ХОРОШО";
	private static final String C_WORDS = "УДОВЛЕТВОРИТЕЛЬНО";
	private static final String D_WORDS = "НЕ УДОВЛЕТВОРИТЕЛЬНО";
	
	private static final String[] ALL_MARKS = { A_WORDS, B_WORDS, C_WORDS, D_WORDS };
	
	private static final Color A_COLOR = new Color(0x77FF77);
	private static final Color B_COLOR = new Color(0xAAFFAA);
	private static final Color C_COLOR = new Color(0xFFAA00);
	private static final Color D_COLOR = new Color(0xFF7777);

	public static final int countMarkInPercent(int totalAmount, int correctAnswers) {
		double calculations = Math.abs(((double) correctAnswers / totalAmount) * 100);
		return (int) calculations;
	}
	
	public static final String[] getAllMarksWords() {
		return ALL_MARKS;
	}

	public static final String countMarkInECTS(int totalAmount, int correctAnswers) {
		int inPercent = countMarkInPercent(totalAmount, correctAnswers);

		if (inPercent > A)
			return "A";
		else if (inPercent <= A && inPercent > B)
			return "B";
		else if (inPercent <= B && inPercent > C)
			return "C";

		return "D";
	}

	public static final int countInOneToFive(int totalAmount, int correctAnswers) {
		String inECTS = countMarkInECTS(totalAmount, correctAnswers);

		switch (inECTS) {
		case "A":
			return 5;
		case "B":
			return 4;
		case "C":
			return 3;
		default:
			return 2;

		}
	}

	public static final String countInWords(int totalAmount, int correctAnswers) {
		String inECTS = countMarkInECTS(totalAmount, correctAnswers);

		switch (inECTS) {
		case "A":
			return A_WORDS;
		case "B":
			return B_WORDS;
		case "C":
			return C_WORDS;
		default:
			return D_WORDS;
		}
	}
	
	public static final Color countInColors(int totalAmount, int correctAnswers) {
		String inECTS = countMarkInECTS(totalAmount, correctAnswers);

		switch (inECTS) {
		case "A":
			return A_COLOR;
		case "B":
			return B_COLOR;
		case "C":
			return C_COLOR;
		default:
			return D_COLOR;
		}
	}
}

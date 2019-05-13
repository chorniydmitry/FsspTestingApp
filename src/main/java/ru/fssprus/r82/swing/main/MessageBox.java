package ru.fssprus.r82.swing.main;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MessageBox extends JOptionPane {
	private static final long serialVersionUID = -2426219006577522299L;
	
	private static final String READY = "Готово!";
	
	public static void showReadyDialog(Component component) {
		MessageBox.showMessageDialog(component, READY, null, JOptionPane.INFORMATION_MESSAGE);
	}
}

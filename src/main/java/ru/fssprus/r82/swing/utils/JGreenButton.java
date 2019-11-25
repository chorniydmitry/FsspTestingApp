package ru.fssprus.r82.swing.utils;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.Color;

import javax.swing.JButton;

public class JGreenButton extends JButton {
	private static final long serialVersionUID = 4915857996100933178L;
	
	private static final Color BACKGROUND = new Color(0x03a06c);
	private static final Color FOREGROUND = new Color(0xffffff);
	private static final Color DISABLED = new Color(0xaaaaaa);
	
	public JGreenButton() {
		super();
	}
	
	public JGreenButton(String text) {
		super(text);
	}
	
	@Override
	public Color getBackground() {
		if(this.isEnabled())
			return BACKGROUND;
			
		return DISABLED;
	}
	
	@Override
	public Color getForeground() {
		return FOREGROUND;
	}
	
	@Override
	public void setBackground(Color bg) {}
	
	@Override
	public void setForeground(Color fg) {}

}

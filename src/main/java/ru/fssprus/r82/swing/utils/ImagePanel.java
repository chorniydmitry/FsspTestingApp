package ru.fssprus.r82.swing.utils;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class ImagePanel extends JComponent {
	private static final long serialVersionUID = 3605464082989248048L;
	private ImageIcon image;
    public ImagePanel(ImageIcon image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, this);
    }
}

package gui.swingview;

import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * The menu bar at the top of the window.
 * This will be the main mechanism for user input.
 */
public class MenuBar extends JMenuBar {
  public MenuBar() {
    super();

    JMenu visualize = new JMenu("Visualize");
    visualize.setMnemonic(KeyEvent.VK_V);
    this.add(visualize);

    JMenu filter = new JMenu("Filter");
    filter.setMnemonic(KeyEvent.VK_F);
    this.add(filter);

    JMenu transform = new JMenu("Transform");
    transform.setMnemonic(KeyEvent.VK_T);
    this.add(transform);

    JMenu flip = new JMenu("Flip");
    flip.setMnemonic(KeyEvent.VK_P);
    this.add(flip);

    JMenu adjust = new JMenu("Adjust");
    adjust.setMnemonic(KeyEvent.VK_A);
    this.add(adjust);

    // Visualize
    JMenuItem visRed = new JMenuItem("Red");
    JMenuItem visGreen = new JMenuItem("Green");
    JMenuItem visBlue = new JMenuItem("Blue");
    JMenuItem visVal = new JMenuItem("Value");
    JMenuItem visInt = new JMenuItem("Intensity");
    JMenuItem visLuma = new JMenuItem("Luma");
    visualize.add(visRed);
    visualize.add(visGreen);
    visualize.add(visBlue);
    visualize.add(visVal);
    visualize.add(visInt);
    visualize.add(visLuma);

    // Filter
    JMenuItem filterBlur = new JMenuItem("Blur");
    JMenuItem filterSharp = new JMenuItem("Sharpen");
    filter.add(filterBlur);
    filter.add(filterSharp);

    // Transform
    JMenuItem transGray = new JMenuItem("Grayscale");
    JMenuItem transSepia = new JMenuItem("Sepia");
    transform.add(transGray);
    transform.add(transSepia);

    // Flip
    JMenuItem flipVert = new JMenuItem("Vertical");
    JMenuItem flipHoriz = new JMenuItem("Horiz");
    flip.add(flipVert);
    flip.add(flipHoriz);

    // Adjust
    JMenuItem adjBright = new JMenuItem("Brighten");
    JMenuItem adjDark = new JMenuItem("Darken");
    adjust.add(adjBright);
    adjust.add(adjDark);

    // Save and load
    JMenuItem save = new JMenuItem("Save...");
    save.setMnemonic(KeyEvent.VK_S);

    JMenuItem load = new JMenuItem("Load...");
    load.setMnemonic(KeyEvent.VK_L);

    this.add(save, 0);
    this.add(load, 0);
  }
}

package gui.swingview;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import commands.Blur;
import controller.ImageEditorSwingController;

/**
 * The menu bar at the top of the window.
 * This will be the main mechanism for user input.
 */
public class MenuBar extends JMenuBar {
  private final String curImageName;
  private final ImageEditorSwingController ctrl;

  /**
   * Creates a new menu bar.
   *
   * @param curImageName the current image name
   * @param ctrl         the controller
   * @throws IllegalArgumentException if either argument is null
   */
  public MenuBar(String curImageName, ImageEditorSwingController ctrl)
          throws IllegalArgumentException {
    super();
    if (curImageName == null || ctrl == null) {
      throw new IllegalArgumentException("Can't have null args!");
    }
    this.curImageName = curImageName;
    this.ctrl = ctrl;
    String mutator = this.curImageName + " " + this.curImageName;
    ActionListener al = new SimpleMenuListener(this.ctrl);

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
    visRed.setActionCommand("gray red " + mutator);
    visRed.addActionListener(al);
    visualize.add(visRed);

    JMenuItem visGreen = new JMenuItem("Green");
    visGreen.setActionCommand("gray green " + mutator);
    visGreen.addActionListener(al);
    visualize.add(visGreen);

    JMenuItem visBlue = new JMenuItem("Blue");
    visBlue.setActionCommand("gray blue " + mutator);
    visBlue.addActionListener(al);
    visualize.add(visBlue);

    JMenuItem visVal = new JMenuItem("Value");
    visVal.setActionCommand("gray value " + mutator);
    visVal.addActionListener(al);
    visualize.add(visVal);

    JMenuItem visInt = new JMenuItem("Intensity");
    visInt.setActionCommand("gray intensity " + mutator);
    visInt.addActionListener(al);
    visualize.add(visInt);

    JMenuItem visLuma = new JMenuItem("Luma");
    visLuma.setActionCommand("gray luma " + mutator);
    visLuma.addActionListener(al);
    visualize.add(visLuma);

    // Filter
    JMenuItem filterBlur = new JMenuItem("Blur");
    filterBlur.setActionCommand("blur " + mutator);
    filterBlur.addActionListener(al);
    filter.add(filterBlur);

    JMenuItem filterSharp = new JMenuItem("Sharpen");
    filterSharp.setActionCommand("sharpen " + mutator);
    filterSharp.addActionListener(al);
    filter.add(filterSharp);

    // Transform
    JMenuItem transGray = new JMenuItem("Grayscale");
    transGray.setActionCommand("generic-grayscale " + mutator);
    transGray.addActionListener(al);
    transform.add(transGray);

    JMenuItem transSepia = new JMenuItem("Sepia");
    transSepia.setActionCommand("sepia " + mutator);
    transSepia.addActionListener(al);
    transform.add(transSepia);

    // Flip
    JMenuItem flipVert = new JMenuItem("Vertical");
    flipVert.setActionCommand("flip vertical " + mutator);
    flipVert.addActionListener(al);
    flip.add(flipVert);

    JMenuItem flipHoriz = new JMenuItem("Horiz");
    flipHoriz.setActionCommand("flip horizontal " + mutator);
    flipHoriz.addActionListener(al);
    flip.add(flipHoriz);

    // Adjust
    JMenuItem adjBright = new JMenuItem("Brighten");
    JMenuItem adjDark = new JMenuItem("Darken");
    adjust.add(adjBright);
    adjust.add(adjDark);

    // Histogram
    JMenu hgram = new JMenu("Histogram");
    hgram.setMnemonic(KeyEvent.VK_H);
    this.add(hgram);

    // Save and load
    JMenu save = new JMenu("Save...");
    save.setMnemonic(KeyEvent.VK_S);

    JMenu load = new JMenu("Load...");
    load.setMnemonic(KeyEvent.VK_L);

    this.add(save, 0);
    this.add(load, 0);
  }


}

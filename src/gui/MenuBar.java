package gui;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import gui.controller.ImageEditorSwingController;
import gui.listeners.FileChooserMenuListener;
import gui.listeners.NumberInputMenuListener;
import gui.listeners.ResizerListener;
import gui.listeners.SimpleMenuListener;
import gui.view.ImageEditorGUIView;

/**
 * The menu bar at the top of the window. This will be the main mechanism for user input.
 */
public class MenuBar extends JMenuBar {

  /**
   * Creates a new menu bar.
   *
   * @param curImageName the current image name
   * @param ctrl         the controller
   * @throws IllegalArgumentException if either argument is null
   */
  public MenuBar(String curImageName, ImageEditorSwingController ctrl, ImageEditorGUIView view)
          throws IllegalArgumentException {
    super();
    if (curImageName == null || ctrl == null || view == null) {
      throw new IllegalArgumentException("Can't have null args!");
    }
    String mutator = curImageName + " " + curImageName;
    ActionListener al = new SimpleMenuListener(ctrl, view);

    JMenu visualize = new JMenu("Visualize");
    visualize.setMnemonic(KeyEvent.VK_V);
    this.add(visualize);

    JMenu filter = new JMenu("Filter");
    filter.setMnemonic(KeyEvent.VK_I);
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
    ActionListener niml = new NumberInputMenuListener(ctrl, view, curImageName);
    ActionListener rl = new ResizerListener(ctrl, view, curImageName);

    JMenuItem adjBright = new JMenuItem("Brighten");
    adjBright.setActionCommand("brighten");
    adjBright.addActionListener(niml);
    adjust.add(adjBright);

    JMenuItem adjDark = new JMenuItem("Darken");
    adjDark.setActionCommand("darken");
    adjDark.addActionListener(niml);
    adjust.add(adjDark);

    JMenuItem adjResize = new JMenuItem("Downsize");
    adjResize.setActionCommand("resize");
    adjResize.addActionListener(rl);
    adjust.add(adjResize);

//     Histogram
    JMenu hgram = new JMenu("Histogram");
    hgram.setMnemonic(KeyEvent.VK_H);
    JMenuItem hgramItem = new JMenuItem("Generate Histogram");
    hgramItem.setActionCommand("hgram " + curImageName);
    hgramItem.addActionListener(al);
    hgram.add(hgramItem);
    this.add(hgram);

    // Save and load
    ActionListener fcml = new FileChooserMenuListener(ctrl, curImageName, view);
    JMenu file = new JMenu("File");
    file.setMnemonic(KeyEvent.VK_F);
    this.add(file, 0);

    JMenuItem load = new JMenuItem("Load...");
    load.setMnemonic(KeyEvent.VK_L);
    load.setActionCommand("load");
    load.addActionListener(fcml);
    file.add(load);

    JMenuItem save = new JMenuItem("Save...");
    save.setMnemonic(KeyEvent.VK_S);
    save.setActionCommand("save");
    save.addActionListener(fcml);
    file.add(save);

    // Mask-Command
    JMenu maskMenu = new JMenu("Mask Operation");
    maskMenu.setMnemonic(KeyEvent.VK_M);

    // Visualize Mask Menu
    JMenu visualizeMaskMenu = new JMenu("Visualize with mask");
    JMenuItem visRedMask = new JMenuItem("Red");
    visRedMask.setActionCommand("mask-command gray red " + mutator);
    visRedMask.addActionListener(fcml);
    visualizeMaskMenu.add(visRedMask);
    // |
    JMenuItem visGreenMask = new JMenuItem("Green");
    visGreenMask.setActionCommand("mask-command gray green " + mutator);
    visGreenMask.addActionListener(fcml);
    visualizeMaskMenu.add(visGreenMask);
    // |
    JMenuItem visBlueMask = new JMenuItem("Blue");
    visBlueMask.setActionCommand("mask-command gray blue " + mutator);
    visBlueMask.addActionListener(fcml);
    visualizeMaskMenu.add(visBlueMask);
    // |
    JMenuItem visValMask = new JMenuItem("Value");
    visValMask.setActionCommand("mask-command gray value " + mutator);
    visValMask.addActionListener(fcml);
    visualizeMaskMenu.add(visValMask);
    // |
    JMenuItem visIntMask = new JMenuItem("Intensity");
    visIntMask.setActionCommand("mask-command gray intensity " + mutator);
    visIntMask.addActionListener(fcml);
    visualizeMaskMenu.add(visIntMask);
    // |
    JMenuItem visLumaMask = new JMenuItem("Luma");
    visLumaMask.setActionCommand("mask-command gray luma " + mutator);
    visLumaMask.addActionListener(fcml);
    visualizeMaskMenu.add(visLumaMask);

    // Filter Mask Menu
    JMenu filterMaskMenu = new JMenu("Filter with mask");
    JMenuItem filterBlurMask = new JMenuItem("Blur");
    filterBlurMask.setActionCommand("mask-command blur " + mutator);
    filterBlurMask.addActionListener(fcml);
    filterMaskMenu.add(filterBlurMask);
    // |
    JMenuItem filterSharpMask = new JMenuItem("Sharpen");
    filterSharpMask.setActionCommand("mask-command sharpen " + mutator);
    filterSharpMask.addActionListener(fcml);
    filterMaskMenu.add(filterSharpMask);


    // Transform Mask Menu
    JMenu transformMaskMenu = new JMenu("Transform with mask");
    JMenuItem transGrayMask = new JMenuItem("Grayscale");
    transGrayMask.setActionCommand("mask-command generic-grayscale " + mutator);
    transGrayMask.addActionListener(fcml);
    transformMaskMenu.add(transGrayMask);
    // |
    JMenuItem transSepiaMask = new JMenuItem("Sepia");
    transSepiaMask.setActionCommand("mask-command sepia " + mutator);
    transSepiaMask.addActionListener(fcml);
    transformMaskMenu.add(transSepiaMask);

    // Adjust Mask Menu
    JMenu adjustMaskMenu = new JMenu("Adjust");
    JMenuItem adjBrightMask = new JMenuItem("Brighten");
    adjBrightMask.setActionCommand("mask-command brighten ");
    adjBrightMask.addActionListener(fcml);
    adjustMaskMenu.add(adjBrightMask);
    // |
    JMenuItem adjDarkMask = new JMenuItem("Darken");
    adjDarkMask.setActionCommand("mask-command darken ");
    adjDarkMask.addActionListener(fcml);
    adjustMaskMenu.add(adjDarkMask);

    maskMenu.add(visualizeMaskMenu);
    maskMenu.add(filterMaskMenu);
    maskMenu.add(transformMaskMenu);
    maskMenu.add(adjustMaskMenu);
    this.add(maskMenu);

    // help
    JMenu helpMenu = new JMenu("Help");
    JMenuItem help = new JMenuItem("Show commands");
    helpMenu.setMnemonic(KeyEvent.VK_H);
    help.setActionCommand("help");
    help.addActionListener(al);
    helpMenu.add(help);
    this.add(helpMenu);
  }


}

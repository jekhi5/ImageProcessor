package gui.swingview;

import javax.swing.JFrame;

import gui.swingview.ImageViewerBar.ImageSelectorButton;
import utilities.ImageFactory;

public class Tester {
  public static void main(String[] args) {
    // JFrame popup = new PopupDialog("Hello World");
//    JFrame menuTest = new JFrame("Menu test");
//    menuTest.setJMenuBar(new MenuBar());
//    menuTest.setSize(400, 300);
//    menuTest.setVisible(true);
//    menuTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    menuTest.setLocationRelativeTo(null);


    ImageViewerBar selectorBar = new ImageViewerBar(500, 700);
    selectorBar.addImageToBar("tester image", ImageFactory.createImage("res/diagram.jpg"));
    selectorBar.render(false);

    JFrame imageSelectorTest = new JFrame("Image Selector Test");
    imageSelectorTest.setSize(1000, 1000);
    imageSelectorTest.add(selectorBar);
    imageSelectorTest.setVisible(true);
    imageSelectorTest.setLocationRelativeTo(null);
    imageSelectorTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JFrame testFrame = new JFrame("Test");
    ImageViewerBar.ImageSelectorButton button = new ImageSelectorButton("testImage",
            ImageViewerBar.toBufferedImage(ImageFactory.createImage("res/diagram.jpg")),
            20);
    testFrame.add(button);
    testFrame.setSize(1000, 1000);
    testFrame.setVisible(true);
    testFrame.setLocationRelativeTo(null);
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}

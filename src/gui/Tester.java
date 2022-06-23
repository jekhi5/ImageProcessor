package gui;

import java.io.IOException;

import gui.controller.ImageEditorSwingController;
import gui.controller.ImageEditorSwingControllerImpl;
import gui.view.ImageEditorGUIView;
import gui.view.ImageEditorSwingView;
import model.BasicImageEditorModel;
import model.ImageEditorModel;

public class Tester {
  public static void main(String[] args) throws IOException {
//    JFrame popup = new PopupDialog("Hello World");
//    JFrame test = new JFrame("Menu test");
//
//    ImageEditorModel model = new BasicImageEditorModel();
//    ImageEditorView view = new ImageEditorSwingView();
//    ImageEditorSwingController ctrl = new ImageEditorSwingControllerImpl(model, view);
//    JMenuBar menuBar = new MenuBar("image", ctrl);
//    test.setJMenuBar(menuBar);
//    test.setSize(1000, 1000);
//    test.setVisible(true);
//    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    test.setLocationRelativeTo(null);
//
//    JComponent viewerBar = new ImageSelectorBar(500, 500);
//    viewerBar.add(ImageSelectorBar.getImageSelectorButton("test image 1", ImageFactory.createImage(
//            "res/diagram.jpg")));
//    viewerBar.add(ImageSelectorBar.getImageSelectorButton("test image 2", ImageFactory.createImage(
//            "res/diagram.jpg")));
//
//    test.add(viewerBar);
//    test.repaint();


//    JFrame testViewPort = new JFrame("View Port");
//    testViewPort.setSize(750, 750);
//    testViewPort.setVisible(true);
//    testViewPort.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    testViewPort.setLocationRelativeTo(null);
//
//    JComponent viewPort = new ImageViewPort(500, 500,
//            ImageIO.read(new File("res/diagram.jpg")));
//
//    testViewPort.add(viewPort);
//    //testViewPort.add(new JLabel("Something"));
//    testViewPort.repaint();


    ImageEditorGUIView view = new ImageEditorSwingView();
    ImageEditorModel model = new BasicImageEditorModel();
    ImageEditorSwingController controller = new ImageEditorSwingControllerImpl(model, view);
    view.accept(controller);
//
//    JFrame test = new JFrame("test");
//    test.setSize(500, 500);
//
//    JComponent viewPort = new ImageViewPort(200, 200, null);
//
////    JComponent selector = new ImageSelectorBar(200, 300);
////    JComponent button =
////            new ImageSelectorButton("testImage", ImageIO.read(new File("res/diagram.jpg")),
////                    new ImageEditorSwingView(), "testImage");
//
//
//    //selector.add(button);
//    test.getContentPane().add(viewPort);
//    test.setVisible(true);
  }
}
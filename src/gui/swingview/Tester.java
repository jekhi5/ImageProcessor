package gui.swingview;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import controller.ImageEditorSwingController;
import controller.ImageEditorSwingControllerImpl;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import utilities.ImageFactory;
import utilities.ImageUtil;
import view.ImageEditorView;

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
//    JComponent viewerBar = new ImageViewerBar(500, 500);
//    viewerBar.add(ImageViewerBar.getImageSelectorButton("test image 1", ImageFactory.createImage(
//            "res/diagram.jpg")));
//    viewerBar.add(ImageViewerBar.getImageSelectorButton("test image 2", ImageFactory.createImage(
//            "res/diagram.jpg")));
//
//    test.add(viewerBar);
//    test.repaint();




    JFrame testViewPort = new JFrame("View Port");
    testViewPort.setSize(750, 750);
    testViewPort.setVisible(true);
    testViewPort.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testViewPort.setLocationRelativeTo(null);

    JComponent viewPort = new ImageViewPort(500, 500,
            ImageIO.read(new File("res/diagram.jpg")));

    testViewPort.add(viewPort);
    //testViewPort.add(new JLabel("Something"));
    testViewPort.repaint();
  }
}

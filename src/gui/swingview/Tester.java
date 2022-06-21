package gui.swingview;

import javax.swing.*;

import controller.ImageEditorSwingController;
import controller.ImageEditorSwingControllerImpl;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import view.ImageEditorView;

public class Tester {
  public static void main(String[] args) {
//    JFrame popup = new PopupDialog("Hello World");
    JFrame test = new JFrame("Menu test");

    ImageEditorModel model = new BasicImageEditorModel();
    ImageEditorView view = new ImageEditorSwingView();
    ImageEditorSwingController ctrl = new ImageEditorSwingControllerImpl(model, view);
    JMenuBar menuBar = new MenuBar("image", ctrl);
    test.setJMenuBar(menuBar);
    test.setSize(500, 300);
//    test.pack();
    test.setVisible(true);
    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    test.setLocationRelativeTo(null);
  }
}

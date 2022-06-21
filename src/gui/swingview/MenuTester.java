package gui.swingview;

import controller.ImageEditorSwingController;
import controller.ImageEditorSwingControllerImpl;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import view.ImageEditorGUIView;

public class MenuTester {
  public static void main(String[] args) {
    ImageEditorModel model = new BasicImageEditorModel();
    ImageEditorGUIView view = new ImageEditorSwingView();
    ImageEditorSwingController controller = new ImageEditorSwingControllerImpl(model, view);


  }
}

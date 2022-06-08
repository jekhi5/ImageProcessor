import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

import commands.Brighten;
import commands.Darken;
import commands.Flip;
import commands.Grayscale;
import commands.ImageEditorCommand;
import controller.ImageEditorController;
import controller.ImageEditorTextController;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;
import view.ImageEditorTextView;
import view.ImageEditorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for {@link controller.ImageEditorTextController}
 *
 * @author emery and Jacob Kline
 * @created 2022-06-05
 */
public class ImageEditorTextControllerTest {

  ImageEditorModel model;
  ImageEditorView view;
  ImageEditorController controller;
  Appendable log;

  String initialMessage =
          "Welcome to ImageEditor! Please enter a command:\n> ";
  String finalMessage = "> Thanks for using ImageEditor!\n";

  String loadingCheckeredImage =
          initialMessage + "addImage(checkered,\n" + ImageUtil.createImageFromPath("res" +
                  "" + slash + "CheckeredBlackBottom_3x4.ppm").toString() +
                  ")\nSuccessfully loaded image \"checkered\" from res" +
                  slash + "CheckeredBlackBottom_3x4.ppm!\n";
  String loadCheckeredBottom = "load res" + slash + "CheckeredBlackBottom_3x4.ppm checkered ";

  private static final String slash = System.getProperty("file.separator");

  @Before
  public void init() {
    log = new StringBuilder();
    model = new MockModel(log);
    view = new ImageEditorTextView(log);
  }

  // Testing constructor with null model
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullModel() {
    new ImageEditorTextController(null, new ImageEditorTextView(), new StringReader(""));
  }

  // Testing constructor with null view
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullView() {
    new ImageEditorTextController(new BasicImageEditorModel(), null, new StringReader(""));
  }

  // Testing constructor with null readable
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullReadable() {
    new ImageEditorTextController(new BasicImageEditorModel(), new ImageEditorTextView(),
            null);
  }

  // Testing loading an image and not overwriting the name of another image already in the model
  @Test
  public void loadingImageNoOverwrite() {
    Reader reader = new StringReader(loadCheckeredBottom + "q");
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage + "addImage(checkered,\n" + ImageUtil.createImageFromPath("res" +
                    "" + slash + "CheckeredBlackBottom_3x4.ppm").toString() + ")" +
                    "\nSuccessfully loaded image " +
                    "\"checkered\" from res" + slash + "CheckeredBlackBottom_3x4.ppm!\n" + finalMessage,
            this.log.toString());
  }

  // Testing loading an image and overwriting the name of another image
  @Test
  public void loadingImageWithOverwrite() {
    Reader reader = new StringReader(
            loadCheckeredBottom + "load res" + slash + "LemonChiffon_1x1.ppm image quit");
    controller = new ImageEditorTextController(model, view, reader);
    Image image2 = ImageUtil.createImageFromPath("res" + slash + "LemonChiffon_1x1.ppm");

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(loadingCheckeredImage + "> addImage(image,\n" + image2.toString() +
            ")\nSuccessfully loaded image \"image\" from res" + slash + "LemonChiffon_1x1" +
            ".ppm!\n" +
            finalMessage, this.log.toString());
  }

  // Testing saving an image without overwriting
  @Test
  public void saving_NotOverwriting() {
    Reader reader =
            new StringReader(
                    loadCheckeredBottom + " save testOut" + slash +
                            "checkered_saved.ppm checkered false exit");
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(loadingCheckeredImage + "> getImage(checkered)\nImage successfully saved to " +
            "testOut" + slash + "checkered_saved.ppm\n" + finalMessage, this.log.toString());


    // CLEANUP
    if (!new File("testOut" + slash + "checkered_saved.ppm").delete()) {
      fail("File was not deleted! Please try again!");
    }
  }

  // Testing saving an image with overwriting
  @Test
  public void saving_Overwriting() {
    String[] overwriteTypes = {"y", "Y", "yes", "YeS", "t", "T", "true", "TRue"};
    for (String overwriteType : overwriteTypes) {
      this.init();
      savingHandling(overwriteType);
    }
  }

  private void savingHandling(String overwriteCommand) {
    File tempFile = new File("testOut" + slash + "tempFile.ppm");
    try {
      if (!tempFile.createNewFile()) {
        fail("Temp file wasn't created!");
      }
    } catch (IOException e) {
      fail("Temp file wasn't created!");
    }


    Reader reader =
            new StringReader(loadCheckeredBottom + " save testOut" + slash + "tempFile.ppm " +
                    "checkered " + overwriteCommand + " exit");
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(loadingCheckeredImage + "> getImage(checkered)\nImage successfully saved to " +
            "testOut" + slash + "tempFile.ppm\n" + finalMessage, this.log.toString());


    // CLEANUP
    if (!new File("testOut" + slash + "tempFile.ppm").delete()) {
      fail("File was not deleted! Please try again!");
    }
  }

  private void commandTesting(String[] spellings, String[] types, String resultingMessageHalf2) {

    ImageEditorModel tempModel = new BasicImageEditorModel();
    tempModel.addImage("pre-op", ImageUtil.createImageFromPath("res"
            + slash + "CheckeredBlackBottom_3x4.ppm"));

    for (String typeOfCommand : spellings) {
      for (String formOfCommand : types) {
        this.init();
        ImageEditorCommand command = getCommand(typeOfCommand, formOfCommand);
        command.apply(tempModel);

        assertEquals("", this.log.toString());
        runCommand(typeOfCommand, formOfCommand);
        assertEquals(loadingCheckeredImage + "> getImage(checkered)\naddImage" +
                "(checkered,\n" + tempModel.getImage("post-op").toString() + ")\n" +
                resultingMessageHalf2 + "\n> Thanks for using ImageEditor!\n", this.log.toString());
      }
    }
  }

  private void runCommand(String spelling, String typeOfCommand) {
    Reader reader = new StringReader(
            loadCheckeredBottom + spelling + " " + typeOfCommand + " checkered checkered q");
    controller = new ImageEditorTextController(model, view, reader);

    controller.launch();
  }

  private ImageEditorCommand getCommand(String typeOfCommand, String formOfCommand) {
    String naming = " pre-op " + " post-op";
    switch (typeOfCommand.toLowerCase()) {
      case "greyscale":
      case "grayscale":
      case "grey":
      case "gray":
        return new Grayscale(new Scanner(new StringReader(formOfCommand + naming)));
      case "flip":
        return new Flip(new Scanner(new StringReader(formOfCommand + naming)));
      case "brighten":
        return new Brighten(new Scanner(new StringReader(formOfCommand + naming)));
      case "darken":
        return new Darken(new Scanner(new StringReader(formOfCommand + naming)));
      default:
        throw new IllegalStateException("Error. No found command with this name.");
    }
  }

  // Testing flip
  @Test
  public void flip() {
    String[] spellings = {"flip", "fLiP"};
    String[] types = {"vertical", "horizontal"};
    String resultingMessageHalf2 = "Flip successful!";

    commandTesting(spellings, types, resultingMessageHalf2);
  }

  // Testing Greyscale using all spellings and all types
  @Test
  public void greyScale() {
    String[] spellings = {"Grayscale", "GrAyScAle", "greyscale", "gREYSCALE", "grey", "GREY",
            "gray", "GrAY"};
    String[] typesOfGreyscale = {"red", "ReD", "green", "GReeN", "blue", "BlUE", "value", "VaLuE"
            , "intensity", "INtENsITy", "luma", "Luma"};
    String resultingMessageHalf2 = "Grayscale successful!";

    commandTesting(spellings, typesOfGreyscale, resultingMessageHalf2);
  }

  private void testingBrightenDarken(boolean testingBrighten) {
    String[] spellings;
    String[] typeOfModification =
            new String[260]; // shows that brighten values can be given above 255
    String resultingMessageHalf2;

    if (testingBrighten) {
      spellings = new String[]{"brighten", "BrIGhtEN"};
      resultingMessageHalf2 = "Brighten successful!";
    } else {
      spellings = new String[]{"darken", "DarKEn"};
      resultingMessageHalf2 = "Darken successful!";
    }

    for (int i = 0; i < typeOfModification.length; i += 1) {
      typeOfModification[i] = i + "";
    }

    commandTesting(spellings, typeOfModification, resultingMessageHalf2);
  }

  // Testing brighten
  @Test
  public void brighten() {
    this.testingBrightenDarken(true);
  }

  // Testing darken
  @Test
  public void darken() {
    this.testingBrightenDarken(false);
  }


  // Testing giving bad command
  @Test
  public void badCommand() {
    Reader reader = new StringReader("hello world exit");
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage + "Invalid command: \"hello\". Please try again.\n" +
                    "> Invalid command: \"world\". Please try again.\n" + finalMessage,
            this.log.toString());
  }

  // Testing quitting (all types) and hanging input
  @Test
  public void quitting() {
    String[] quittingOption = {"q", "Q", "quit", "QuIt", "exit", "ExiT", ""/*this is a hanging
    input but it should still quit*/};

    for (String option : quittingOption) {
      this.init();
      runQuitting(option);
    }
  }

  private void runQuitting(String quitOption) {
    Reader reader = new StringReader(quitOption);
    controller = new ImageEditorTextController(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage.substring(0, initialMessage.length() - 2) + finalMessage,
            this.log.toString());
  }

  @Test
  public void testHangingInCommand() {

    String[] commandString = {"load", "LoAd", "save", "SaVE", "flip", "fLIp", "gray", "GrAY",
            "grey", "GREY", "grayscale", "GRaYscALe", "greyscale", "GReYSCAle", "brighten",
            "BRIghTEn", "darken", "DARKEn"};

    for (String command : commandString) {
      try {
        Reader reader = new StringReader(command);
        controller = new ImageEditorTextController(model, view, reader);

        controller.launch();
      } catch (IllegalStateException e) {
        assertEquals("Scanner ran out of inputs.", e.getMessage());
      }
    }
  }

  // TODO: Integration tests
  // TODO: For every command show one argument being wrong in each location (can be same method)


  private static class MockModel implements ImageEditorModel {

    Appendable log;
    ImageEditorModel model;

    public MockModel(Appendable log) {
      this.log = log;
      model = new BasicImageEditorModel();
    }


    @Override
    public Image getImage(String name) throws IllegalArgumentException {
      try {
        this.log.append("getImage(").append(name).append(")").append("\n");
      } catch (IOException e) {
        this.log = new StringBuilder("ERROR APPENDING IN getImage()!!");
      }
      return this.model.getImage(name);
    }

    @Override
    public void addImage(String name, Image image) throws IllegalArgumentException {
      try {
        this.log.append("addImage(").append(name).append(",\n").append(image.toString()).append(")")
                .append("\n");
      } catch (IOException e) {
        this.log = new StringBuilder("ERROR APPENDING IN addImage()!!");
      }

      this.model.addImage(name, image);
    }
  }
}
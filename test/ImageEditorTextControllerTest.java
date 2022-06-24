import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import commands.AbstractCommand;
import commands.Brighten;
import commands.Darken;
import commands.Downsize;
import commands.Flip;
import commands.Grayscale;
import commands.ImageEditorCommand;
import commands.MaskedCommand;
import controller.ImageEditorTextController;
import controller.ImageEditorTextControllerImpl;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageUtil;
import view.ImageEditorTextView;
import view.ImageEditorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link ImageEditorTextControllerImpl}.
 */
public class ImageEditorTextControllerTest {

  static final String NEW_LINE = System.lineSeparator();
  private static final String SLASH = System.getProperty("file.separator");
  ImageEditorModel model;
  ImageEditorView view;
  ImageEditorTextController controller;
  Appendable log;
  Map<Class<? extends AbstractCommand>, String> commandNames;
  String initialMessage =
          "Welcome to ImageEditor! For information about the supported file types or available " +
                  "commands enter \"help\" and press <enter>. Please enter a command:" + NEW_LINE +
                  "> ";
  String finalMessage = "> Thanks for using ImageEditor!" + NEW_LINE;

  String loadingCheckeredImage =
          initialMessage + "Executed command: LoadImage" + NEW_LINE +
                  "Successfully loaded image \"checkered\" from test" +
                  SLASH + "testRes" + SLASH + "checkered.ppm!" + NEW_LINE;
  String loadCheckeredBottom = "load test" + SLASH + "testRes" + SLASH + "checkered.ppm checkered ";


  @Before
  public void init() {
    log = new StringBuilder();
    model = new MockModel(log);
    view = new ImageEditorTextView(log);

    commandNames = new HashMap<>();
    commandNames.put(commands.Brighten.class, "Brighten");
    commandNames.put(commands.Darken.class, "Darken");
    commandNames.put(commands.Flip.class, "Flip");
    commandNames.put(commands.Grayscale.class, "Grayscale");
    commandNames.put(commands.LoadImage.class, "Load");
    commandNames.put(commands.SaveImage.class, "Save");
  }

  @After
  public void deleteFiles() {
    File testOut = new File("test" + SLASH + "testOut");
    if (!testOut.isDirectory()) {
      return;
    }
    for (File f : Objects.requireNonNull(testOut.listFiles())) {
      if (!f.delete()) {
        throw new IllegalStateException("Error deleting files. Please clear testOut directory.");
      }
    }
  }

  // Testing constructor with null model
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullModel() {
    new ImageEditorTextControllerImpl(null, new ImageEditorTextView(), new StringReader(""));
  }

  // Testing constructor with null view
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullView() {
    new ImageEditorTextControllerImpl(new BasicImageEditorModel(), null, new StringReader(""));
  }

  // Testing constructor with null readable
  @Test(expected = IllegalArgumentException.class)
  public void testingConstructor_NullReadable() {
    new ImageEditorTextControllerImpl(new BasicImageEditorModel(), new ImageEditorTextView(),
            null);
  }

  // Testing loading an image and not overwriting the name of another image already in the model
  @Test
  public void loadingImageNoOverwrite() {
    Reader reader = new StringReader(loadCheckeredBottom + "q");
    controller = new ImageEditorTextControllerImpl(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage + "Executed command: LoadImage" +
            NEW_LINE + "Successfully loaded image " +
            "\"checkered\" from test" + SLASH + "testRes" + SLASH + "checkered.ppm!"
            + NEW_LINE + finalMessage, this.log.toString());
  }

  // Testing loading an image and overwriting the name of another image
  @Test
  public void loadingImageWithOverwrite() {
    Reader reader = new StringReader(
            loadCheckeredBottom + "load test" + SLASH + "testRes" + SLASH +
                    "LemonChiffon_1x1.ppm image quit");
    controller = new ImageEditorTextControllerImpl(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(loadingCheckeredImage + "> Executed command: LoadImage"
            + NEW_LINE + "Successfully loaded image \"image\" from test" + SLASH + "testRes" +
            SLASH + "LemonChiffon_1x1.ppm!" + NEW_LINE +
            finalMessage, this.log.toString());
  }

  // Testing saving an image without overwriting
  @Test
  public void saving_NotOverwriting() {
    Reader reader =
            new StringReader(
                    loadCheckeredBottom + " save test" + SLASH + "testOut" + SLASH +
                            "checkered_saved.ppm checkered false exit");
    controller = new ImageEditorTextControllerImpl(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(
            loadingCheckeredImage + "> Executed command: SaveImage" + NEW_LINE + "Image " +
                    "successfully saved to test" + SLASH + "testOut" + SLASH + "checkered_saved.ppm"
                    + NEW_LINE + finalMessage, this.log.toString());


    // CLEANUP
    if (!new File("test" + SLASH + "testOut" + SLASH + "checkered_saved.ppm").delete()) {
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

    boolean t = true;
    assertTrue(t);
  }

  private void savingHandling(String overwriteCommand) {
    File tempFile = new File("test" + SLASH + "testOut" + SLASH + "tempFile.ppm");
    try {
      if (!tempFile.createNewFile()) {
        fail("Temp file wasn't created!");
      }
    } catch (IOException e) {
      fail("Temp file wasn't created!");
    }


    Reader reader =
            new StringReader(loadCheckeredBottom + " save test" + SLASH + "testOut" + SLASH +
                    "tempFile.ppm checkered " + overwriteCommand + " exit");
    controller = new ImageEditorTextControllerImpl(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(loadingCheckeredImage + "> Executed command: SaveImage" + NEW_LINE +
            "Image successfully saved to test" + SLASH + "testOut" + SLASH + "tempFile.ppm" +
            NEW_LINE + finalMessage, this.log.toString());


    // CLEANUP
    if (!new File("test" + SLASH + "testOut" + SLASH + "tempFile.ppm").delete()) {
      fail("File was not deleted! Please try again!");
    }
  }

  private void commandTesting(String[] spellings, String[] types, String resultingMessageHalf2) {

    ImageEditorModel tempModel = new BasicImageEditorModel();
    tempModel.addImage("pre-op", ImageUtil.createImageFromPath("test" + SLASH + "testRes"
            + SLASH + "checkered.ppm"));

    for (String typeOfCommand : spellings) {
      for (String formOfCommand : types) {
        this.init();
        ImageEditorCommand command = getCommand(typeOfCommand, formOfCommand);
        tempModel.execute(command);

        assertEquals("", this.log.toString());
        runCommand(typeOfCommand, formOfCommand);
        assertEquals(loadingCheckeredImage
                + "> Executed command: " + commandNames.get(command.getClass())
                + NEW_LINE + resultingMessageHalf2
                + NEW_LINE + "> Thanks for using ImageEditor!"
                + NEW_LINE, this.log.toString());
      }
    }
  }

  private void runCommand(String spelling, String typeOfCommand) {
    Reader reader = new StringReader(
            loadCheckeredBottom + spelling + " " + typeOfCommand + " checkered checkered q");
    controller = new ImageEditorTextControllerImpl(model, view, reader);

    controller.launch();
  }

  private ImageEditorCommand getCommand(String typeOfCommand, String formOfCommand) {
    String naming = " pre-op post-op";
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
        throw new IllegalStateException("No found command with this name.");
    }
  }

  // Testing flip
  @Test
  public void flip() {
    String[] spellings = {"flip", "fLiP"};
    String[] types = {"vertical", "horizontal"};
    String resultingMessageHalf2 = "Flip successful!";

    commandTesting(spellings, types, resultingMessageHalf2);

    boolean t = true;
    assertTrue(t);
  }

  // Testing GenericGrayscale using all spellings and all types
  @Test
  public void greyScale() {
    String[] spellings =
            {"Grayscale", "GrAyScAle", "greyscale", "gREYSCALE", "grey", "GREY", "gray", "GrAY"};
    String[] typesOfGreyscale =
            {"red", "ReD", "green", "GReeN", "blue", "BlUE", "value", "VaLuE", "intensity",
                    "INtENsITy", "luma", "Luma"};
    String resultingMessageHalf2 = "Grayscale successful!";

    commandTesting(spellings, typesOfGreyscale, resultingMessageHalf2);

    boolean t = true;
    assertTrue(t);
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

    boolean t = true;
    assertTrue(t);
  }

  // Testing darken
  @Test
  public void darken() {
    this.testingBrightenDarken(false);

    boolean t = true;
    assertTrue(t);
  }

  @Test
  public void testingMask() {
    ImageEditorModel tempModel = new BasicImageEditorModel();
    tempModel.addImage("pre-op", ImageUtil.createImageFromPath("test" + SLASH + "testRes"
            + SLASH + "FullyRed_3x3.ppm"));
    String pathToMask = "test" + SLASH + "testRes" + SLASH + "mask.ppm";
    Map<String, Function<Scanner, ImageEditorCommand>> mapWithOnlyGrayscaleCommand =
            new HashMap<>();
    mapWithOnlyGrayscaleCommand.put("grayscale", Grayscale::new);


    ImageEditorCommand maskCommand =
            new MaskedCommand(new Scanner(new StringReader(pathToMask + " grayscale " +
                    "red pre-op post-op q")), mapWithOnlyGrayscaleCommand);

    tempModel.execute(maskCommand);

    assertEquals("", this.log.toString());

    Reader reader = new StringReader(
            "load test" + SLASH + "testRes" + SLASH + "FullyRed_3x3.ppm pre-op mask-command " +
                    pathToMask + " grayscale red pre-op post-op q");
    controller = new ImageEditorTextControllerImpl(model, view, reader);
    controller.launch();

    assertEquals("Welcome to ImageEditor! For information about the supported file types or " +
            "available commands enter \"help\" and press <enter>. Please enter a command:"
            + NEW_LINE + "> Executed command: LoadImage"
            + NEW_LINE + "Successfully loaded image \"pre-op\" from test/testRes/FullyRed_3x3.ppm!"
            + NEW_LINE + "> Executed command: MaskedCommand"
            + NEW_LINE + "Grayscale successful!"
            + NEW_LINE + "> Thanks for using ImageEditor!"
            + NEW_LINE, this.log.toString());
  }

  @Test
  public void testingDownsize() {
    ImageEditorModel tempModel = new BasicImageEditorModel();
    tempModel.addImage("checkered", ImageUtil.createImageFromPath("test" + SLASH + "testRes"
            + SLASH + "checkered.ppm"));


    ImageEditorCommand downsizeCommand =
            new Downsize(new Scanner(new StringReader(" 2 2 checkered post-op")));

    tempModel.execute(downsizeCommand);

    assertEquals("", this.log.toString());

    Reader reader = new StringReader(loadCheckeredBottom + " downsize 2 2 checkered post-op q");
    controller = new ImageEditorTextControllerImpl(model, view, reader);
    controller.launch();

    assertEquals(loadingCheckeredImage
            + "> Executed command: Downsize"
            + NEW_LINE + "Downsize successful!"
            + NEW_LINE + "> Thanks for using ImageEditor!"
            + NEW_LINE, this.log.toString());
  }


  // Testing giving bad command
  @Test
  public void badCommand() {
    Reader reader = new StringReader("hello world exit");
    controller = new ImageEditorTextControllerImpl(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage + "Invalid command: \"hello\". Please try again." + NEW_LINE +
                    "> Invalid command: \"world\". Please try again." + NEW_LINE + finalMessage,
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

    boolean t = true;
    assertTrue(t);
  }

  private void runQuitting(String quitOption) {
    Reader reader = new StringReader(quitOption);
    controller = new ImageEditorTextControllerImpl(model, view, reader);

    assertEquals("", this.log.toString());
    controller.launch();
    assertEquals(initialMessage.substring(0, initialMessage.length() - 2) + finalMessage,
            this.log.toString());
  }

  @Test
  public void testHangingInCommand() {

    String[] commandString =
            {"load", "LoAd", "save", "SaVE", "flip", "fLIp", "gray", "GrAY", "grey",
                    "GREY", "grayscale", "GRaYscALe", "greyscale", "GReYSCAle", "brighten",
                    "BRIghTEn", "darken", "DARKEn"};

    for (String command : commandString) {
      Appendable output = new StringBuilder();
      ImageEditorView v = new ImageEditorTextView(output);
      Reader reader = new StringReader(command);
      controller = new ImageEditorTextControllerImpl(model, v, reader);

      controller.launch();

      assertEquals(output.toString(),
              "Welcome to ImageEditor! For information about the supported file types or " +
                      "available commands enter \"help\" and press <enter>. Please enter a command:"
                      + NEW_LINE + "> Error: Insufficient command input. Quitting..." + NEW_LINE +
                      "Thanks for using ImageEditor!" + NEW_LINE);
    }
  }

  // Testing an IO Exception
  @Test(expected = IllegalStateException.class)
  public void testingIOException() {
    ImageEditorView view = new ImageEditorTextView(new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("lol");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("lol");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("lol");
      }
    });

    Reader reader = new StringReader("load test" + SLASH + "testRes" + SLASH + "checkered.ppm " +
            "checkered");
    ImageEditorTextController
            controller = new ImageEditorTextControllerImpl(this.model, view, reader);
    controller.launch();
  }

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
        this.log.append("Executed command: SaveImage").append(NEW_LINE);
      } catch (IOException e) {
        this.log = new StringBuilder("ERROR APPENDING IN getImage()!!");
      }
      return this.model.getImage(name);
    }

    @Override
    public void addImage(String name, Image image) throws IllegalArgumentException {
      try {
        this.log.append("addImage(").append(name)/*.append("," + NEW_LINE).append(image.toPPMText
        ())*/.append(")").append("").append(NEW_LINE);
      } catch (IOException e) {
        this.log = new StringBuilder("ERROR APPENDING IN addImage()!!");
      }

      this.model.addImage(name, image);
    }

    @Override
    public String execute(ImageEditorCommand cmd) throws IllegalArgumentException {
      try {
        this.log.append("Executed command: ").append(cmd.getClass().toString()
                        .substring(1 + cmd.getClass().toString().lastIndexOf(".")))
                .append(NEW_LINE);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return model.execute(cmd);
    }
  }
}
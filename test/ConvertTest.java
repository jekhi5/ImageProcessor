import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.util.Scanner;

import commands.ImageEditorCommand;
import commands.Convert;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import utilities.ImageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * The test class for the convert command.
 */
public class ConvertTest {

  private static final String SLASH = System.getProperty("file.separator");
  private static final String FROM_PATH = "test" + SLASH + "testRes" + SLASH + "checkered.";
  private static final String TO_PATH = "test" + SLASH + "testOut" + SLASH + "checkered.";
  ImageEditorCommand convertCommand;

  // Testing converting
  @Test
  public void apply() {
    String[] types = {"ppm", "bmp", "png", "jpg"};

    for (String fromType : types) {
      for (String toType : types) {
        performConversion(fromType, toType);

        // JPG is a compressed file so it will be different
        if (fromType.equalsIgnoreCase("jpg")) {
          assertNotEquals(ImageFactory.createImage(FROM_PATH + toType),
                  ImageFactory.createImage(TO_PATH + toType));
        } else {
          assertEquals(ImageFactory.createImage(FROM_PATH + toType),
                  ImageFactory.createImage(TO_PATH + toType));
        }

        if (!new File(TO_PATH + toType).delete()) {
          fail("File not deleted!");
        }
      }
    }
  }

  // To test trying to convert an image that doesn't exist
  @Test
  public void invalidSource() {
    ImageEditorModel temp = new BasicImageEditorModel();
    ImageEditorCommand convertCommand = new Convert(new Scanner(new StringReader("test" + SLASH +
            "bungus" + SLASH + "checkered.ppm test" + SLASH + "testOut" + SLASH +
            "checkered.jpg n")));
    assertEquals("Convert failed: File test/bungus/checkered.ppm not found!",
            convertCommand.apply(temp));
  }


  // To test trying to save an image that doesn't exist
  @Test
  public void invalidDestination() {
    ImageEditorModel temp = new BasicImageEditorModel();
    ImageEditorCommand convertCommand = new Convert(new Scanner(new StringReader("test" + SLASH +
            "testRes" + SLASH + "checkered.ppm test" + SLASH + "bungus" + SLASH +
            "checkered.ppm n")));
    assertEquals("Convert failed: Bad path: test/bungus/checkered.ppm",
            convertCommand.apply(temp));
  }

  // Performing the actual conversion
  private void performConversion(String fromType, String toType) {
    convertCommand =
            new Convert(new Scanner(
                    new StringReader(FROM_PATH + fromType + " " + TO_PATH + toType + " no")));

    convertCommand.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNullCommand() {
    new Convert(null);
  }
}
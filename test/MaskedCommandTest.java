import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import commands.Grayscale;
import commands.ImageEditorCommand;
import commands.MaskedCommand;
import model.BasicImageEditorModel;
import model.ImageEditorModel;
import model.image.Image;
import utilities.ImageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MaskedCommandTest {

  private static final String SLASH = System.getProperty("file.separator");
  private static final Image RED_IMAGE =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "FullyRed_3x3.ppm");
  private static final Image POST_MASK_IMAGE =
          ImageFactory.createImage("test" + SLASH + "testRes" + SLASH + "Masked_FullyRed_3x3.ppm");
  private static final String PATH_TO_MASK = "test" + SLASH + "testRes" + SLASH + "mask.ppm";
  private final Map<String, Function<Scanner, ImageEditorCommand>>
          mapWithOnlyGrayscaleCommand;

  public MaskedCommandTest() {
    this.mapWithOnlyGrayscaleCommand = new HashMap<>();

    this.mapWithOnlyGrayscaleCommand.put("grayscale", Grayscale::new);
  }

  @Test
  public void testCommand() {
    ImageEditorModel model = new BasicImageEditorModel();

    model.addImage("redImage", RED_IMAGE);

    assertNotEquals(model.getImage("redImage"), POST_MASK_IMAGE);
    model.execute(new MaskedCommand(new Scanner(
            new StringReader(PATH_TO_MASK + " grayscale red redImage redImage q")),
            this.mapWithOnlyGrayscaleCommand));
    assertEquals(model.getImage("redImage"), POST_MASK_IMAGE);
  }
}
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.GenericGrayscale;
import commands.ImageEditorCommand;

/**
 * Tests for GenericGrayscale.
 */
public class GenericGrayscaleTest extends AbstractCommandTest {

  private static final List<ImageEditorCommand> COMMAND_FORMS =
          Arrays.asList(new GenericGrayscale(new Scanner(new StringReader("checkered " +
                          "generic-grayscale_checkered"))),
                  new GenericGrayscale(
                          new Scanner(new StringReader("checkered generic-grayscale_checkered"))));
  private static final List<String> ORDER_OF_TYPES =
          Arrays.asList("generic-grayscale", "generic-grayscale");
  private static final List<ImageEditorCommand> ILLEGAL_FORMS =
          Arrays.asList(
                  new GenericGrayscale(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))),
                  new GenericGrayscale(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))));
  private static final String SUCCESSFUL_MESSAGE = "Generic Grayscale successful!";

  /**
   * To construct a test for a {@code GenericGrayscale} command.
   */
  public GenericGrayscaleTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new GenericGrayscale(null);
  }
}
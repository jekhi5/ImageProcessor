import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.ImageEditorCommand;
import commands.Sepia;

public class SepiaTest extends AbstractCommandTest {

  private static final List<ImageEditorCommand> COMMAND_FORMS =
          Arrays.asList(new Sepia(new Scanner(new StringReader("checkered sepia_checkered"))),
                  new Sepia(new Scanner(new StringReader("checkered sepia_checkered"))));
  private static final List<String> ORDER_OF_TYPES = Arrays.asList("sepia", "sepia");
  private static final List<ImageEditorCommand> ILLEGAL_FORMS =
          Arrays.asList(new Sepia(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))),
                  new Sepia(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))));
  private static final String SUCCESSFUL_MESSAGE = "Sepia successful!";

  /**
   * To construct a test for a {@code Sepia} command.
   */
  public SepiaTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new Sepia(null);
  }
}
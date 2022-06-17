import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.ImageEditorCommand;
import commands.Blur;

/**
 * The testing class for the Blue command.
 */
public class BlurTest extends AbstractCommandTest {

  private static final List<ImageEditorCommand> COMMAND_FORMS =
          Arrays.asList(new Blur(new Scanner(new StringReader("checkered blur_checkered"))),
                  new Blur(new Scanner(new StringReader("checkered blur_checkered"))));
  private static final List<String> ORDER_OF_TYPES = List.of("blur", "blur");
  private static final List<ImageEditorCommand> ILLEGAL_FORMS =
          Arrays.asList(new Blur(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))),
                  new Blur(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))));
  private static final String SUCCESSFUL_MESSAGE = "Blur successful!";


  /**
   * To construct a test for the {@code Blur} command.
   */
  public BlurTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new Blur(null);
  }
}
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;

import commands.Downsize;
import commands.ImageEditorCommand;

public class DownsizeTest extends AbstractCommandTest {


  private static final List<ImageEditorCommand> COMMAND_FORMS =
          List.of(new Downsize(new Scanner(new StringReader("2 2 " +
                  "checkered 2_checkered"))));
  private static final List<String> ORDER_OF_TYPES = List.of("2");
  private static final List<ImageEditorCommand> ILLEGAL_FORMS =
          List.of(new Downsize(new Scanner(new StringReader("abc abc something whoopsies"))));
  private static final String SUCCESSFUL_MESSAGE = "Downsize successful!";

  /**
   * To construct a test for the Downsize command.
   */
  public DownsizeTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new Downsize(null);
  }
}
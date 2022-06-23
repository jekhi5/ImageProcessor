import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.AbstractCommand;
import commands.Blur;
import commands.Downsize;
import commands.ImageEditorCommand;
import model.ImageEditorModel;

//public class DownsizeTest extends AbstractCommandTest {
//
//
//  private static final List<ImageEditorCommand> COMMAND_FORMS =
//          Arrays.asList(new Downsize(new Scanner(new StringReader("100 100 blur_checkered"))),
//                  new Downsize(new Scanner(new StringReader("checkered blur_checkered"))));
//  private static final List<String> ORDER_OF_TYPES = List.of("blur", "blur");
//  private static final List<ImageEditorCommand> ILLEGAL_FORMS =
//          Arrays.asList(new Blur(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))),
//                  new Blur(new Scanner(new StringReader("NOT_AN_IMAGE whoopsies"))));
//  private static final String SUCCESSFUL_MESSAGE = "Blur successful!";
//
//  /**
//   * To construct a test for the downsize command.
//   *
//   * @param commandForms      the different forms that the command could be used in (vertical, red,
//   *                          100, etc)
//   * @param orderOfTypes      the order that the types of commands are listed in
//   * @param illegalForms      examples of illegal types that are not allowed to be used with the
//   *                          command
//   * @param successfulMessage the message that the command returns when the operation is fully
//   *                          successful
//   */
//  public DownsizeTest() {
//    super(commandForms, orderOfTypes, illegalForms, successfulMessage);
//  }
//
//  @Override
//  protected void getNullCommand() {
//    new Downsize(null);
//  }
//}

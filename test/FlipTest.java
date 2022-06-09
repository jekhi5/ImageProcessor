import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.Flip;
import commands.Grayscale;
import commands.ImageEditorCommand;

public class FlipTest extends AbstractCommandTest {

  private static final List<ImageEditorCommand> COMMAND_FORMS =
          Arrays.asList(
                  new Flip(new Scanner(new StringReader("vertical checkered vertical_checkered"))),
                  new Flip(new Scanner(new StringReader("vErTiCaL checkered red_checkered"))),
                  new Flip(new Scanner(
                          new StringReader("horizontal checkered horizontal_checkered"))),
                  new Flip(new Scanner(
                          new StringReader("hORiZoNtAL checkered horizontal_checkered"))));

  private static final List<String> ORDER_OF_TYPES = Arrays.asList("vertical", "VErtICal",
          "horizontal", "HOriZOntAL");

  private static final List<ImageEditorCommand> ILLEGAL_FORMS =
          Arrays.asList(new Grayscale(new Scanner(new StringReader("VerTiCaL NOT_AN_IMAGE " +
                          "wont-reach-this-argument"))),
                  new Grayscale(new Scanner(new StringReader("diagonal checkered " +
                          "wont-reach-this-argument"))));

  private static final String SUCCESSFUL_MESSAGE = "Flip successful!";

  @Override
  protected String getSuccessfulMessage() {
    return SUCCESSFUL_MESSAGE;
  }

  @Override
  protected List<ImageEditorCommand> getIllegalForms() {
    return ILLEGAL_FORMS;
  }

  @Override
  protected List<String> getOrderOfTypes() {
    return ORDER_OF_TYPES;
  }

  @Override
  protected List<ImageEditorCommand> getCommandForms() {
    return COMMAND_FORMS;
  }

  @Override
  protected void getNullCommand() {
    new Flip(null);
  }
}

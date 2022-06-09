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
                  new Flip(new Scanner(new StringReader("vErTiCaL checkered vertical_checkered"))),
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

  public FlipTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new Flip(null);
  }
}

import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.Grayscale;
import commands.ImageEditorCommand;

import static org.junit.Assert.assertEquals;

public class GrayscaleTest extends AbstractCommandTest {

  private static final List<ImageEditorCommand> COMMAND_FORMS =
          Arrays.asList(
                  new Grayscale(new Scanner(new StringReader("red checkered red_checkered"))),
                  new Grayscale(new Scanner(new StringReader("ReD checkered red_checkered"))),
                  new Grayscale(new Scanner(new StringReader("green checkered green_checkered"))),
                  new Grayscale(new Scanner(new StringReader("GReEn checkered green_checkered"))),
                  new Grayscale(new Scanner(new StringReader("blue checkered blue_checkered"))),
                  new Grayscale(new Scanner(new StringReader("blUE checkered blue_checkered"))),
                  new Grayscale(new Scanner(new StringReader("value checkered value_checkered"))),
                  new Grayscale(new Scanner(new StringReader("vAluE checkered value_checkered"))),
                  new Grayscale(
                          new Scanner(new StringReader("intensity checkered intensity_checkered"))),
                  new Grayscale(
                          new Scanner(new StringReader("intEnSIty checkered intensity_checkered"))),
                  new Grayscale(new Scanner(new StringReader("luma checkered luma_checkered"))),
                  new Grayscale(new Scanner(new StringReader("LUMA checkered luma_checkered"))));

  private static final List<String> ORDER_OF_TYPES = Arrays.asList("red", "red", "green", "green"
          , "blue", "blue", "value", "value", "intensity", "intensity", "luma", "luma");

  private static final List<ImageEditorCommand> ILLEGAL_FORMS =
          Arrays.asList(new Grayscale(new Scanner(new StringReader("ReD NOT_AN_IMAGE " +
                          "wont-reach-this-argument"))),
                  new Grayscale(new Scanner(new StringReader("purple checkered " +
                          "wont-reach-this-argument"))));

  private static final String SUCCESSFUL_MESSAGE = "Grayscale successful!";

  public GrayscaleTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new Grayscale(null);
  }
}
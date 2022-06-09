import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.Brighten;
import commands.ImageEditorCommand;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Brighten}.
 */
public class BrightenTest extends AbstractCommandTest {
  private static final List<ImageEditorCommand> COMMAND_FORMS = Arrays.asList(
          new Brighten(new Scanner(new StringReader("100 checkered 100_checkered"))),
          new Brighten(new Scanner(new StringReader("100 checkered 100_checkered"))));

  private static final List<String> ORDER_OF_TYPES =
          Arrays.asList("100", "100");

  private static final List<ImageEditorCommand> ILLEGAL_FORMS = Arrays.asList(new Brighten(
                  new Scanner(new StringReader("1000 NOT_AN_IMAGE wont-reach-this-argument"))),
          new Brighten(new Scanner(
                  new StringReader("-10 checkered wont-reach-this-argument"))));

  private static final String SUCCESSFUL_MESSAGE = "Brighten successful!";

  /**
   * Creates a new BrightenTest.
   */
  public BrightenTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new Brighten(null);
  }

  @Test
  public void brightenNeg() {
    Scanner sc = new Scanner(new StringReader(" -1 checkered br-new-1"));
    String r = new Brighten(sc).apply(model);
    assertEquals("Brighten failed: amount must be positive, was: -1", r);
  }

  @Test
  public void brightenNonInt() {
    Scanner sc = new Scanner(new StringReader(" 10.2 checkered da-new-1"));
    String r = new Brighten(sc).apply(model);
    assertEquals("Brighten failed: amount must be a positive integer!", r);
  }

  @Test
  public void brightenOver255() {
    Scanner sc1 = new Scanner(new StringReader(" 255 checkered br-new-2"));
    Scanner sc2 = new Scanner(new StringReader(" 20000 checkered br-new-3"));
    ImageEditorCommand br1 = new Brighten(sc1);
    ImageEditorCommand br2 = new Brighten(sc2);
    br1.apply(model);
    br2.apply(model);
    assertEquals(model.getImage("br-new-2"), model.getImage("br-new-3"));
  }
}

import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.Darken;
import commands.ImageEditorCommand;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Darken}.
 */
public class DarkenTest extends AbstractCommandTest {
  private static final List<ImageEditorCommand> COMMAND_FORMS = Arrays.asList(
          new Darken(new Scanner(new StringReader("150 checkered 150_checkered"))),
          new Darken(new Scanner(new StringReader("150 checkered 150_checkered"))));

  private static final List<String> ORDER_OF_TYPES =
          Arrays.asList("150", "150");

  private static final List<ImageEditorCommand> ILLEGAL_FORMS = Arrays.asList(new Darken(
                  new Scanner(new StringReader("1000 NOT_AN_IMAGE wont-reach-this-argument"))),
          new Darken(new Scanner(
                  new StringReader("-10 checkered wont-reach-this-argument"))));

  private static final String SUCCESSFUL_MESSAGE = "Darken successful!";

  /**
   * Creates a new DarkenTest.
   */
  public DarkenTest() {
    super(COMMAND_FORMS, ORDER_OF_TYPES, ILLEGAL_FORMS, SUCCESSFUL_MESSAGE);
  }

  @Override
  protected void getNullCommand() {
    new Darken(null);
  }

  @Test
  public void darkenNeg() {
    Scanner sc = new Scanner(new StringReader(" -1 checkered da-new-1"));
    String r = new Darken(sc).apply(model);
    assertEquals("Darken failed: amount must be positive, was: -1", r);
  }

  @Test
  public void darkenNonInt() {
    Scanner sc = new Scanner(new StringReader(" 10.2 checkered da-new-1"));
    String r = new Darken(sc).apply(model);
    assertEquals("Darken failed: amount must be a positive integer!", r);
  }

  @Test
  public void darkenOver255() {
    Scanner sc1 = new Scanner(new StringReader(" 255 checkered da-new-2"));
    Scanner sc2 = new Scanner(new StringReader(" 20000 checkered da-new-3"));
    ImageEditorCommand da1 = new Darken(sc1);
    ImageEditorCommand da2 = new Darken(sc2);
    da1.apply(model);
    da2.apply(model);
    assertEquals(model.getImage("da-new-2"), model.getImage("da-new-3"));
  }
}

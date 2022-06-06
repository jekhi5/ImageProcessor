package commands;

import java.util.Map;
import java.util.Scanner;

import model.image.ImageModel;

/**
 * A test command. It takes in two arguments and has the view print them out.
 *
 * @author emery
 * @created 2022-06-05
 */
public class TestCommand extends AbstractCommand {

  public TestCommand(Scanner in) {
    super(in, 2);

  }

  @Override
  public String apply(Map<String, ImageModel> images) {
    return "Test: " + args[0] + " " + args[1];
  }
}

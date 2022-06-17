import org.junit.Test;

import commands.v2.Convert;

/**
 * The test class for the convert command.
 */
public class ConvertTest {



  @Test(expected = IllegalArgumentException.class)
  public void getNullCommand() {
    new Convert(null);
  }
}
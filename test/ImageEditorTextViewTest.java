import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import view.ImageEditorTextView;
import view.ImageEditorView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link view.ImageEditorTextView}.
 */
public class ImageEditorTextViewTest {
  Random rand;
  String alphabet;

  @Before
  public void init() {
    rand = new Random(1);
    alphabet = "abcdefghijklmnopqrstuvxyz 1234567890";
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAppendable() {
    new ImageEditorTextView(null);
  }

  @Test
  public void renderMessageFuzzyTest() throws IOException {
    // fuzzy testing
    String[] arr = alphabet.split("");
    for (int i = 0; i < 1000; i += 1) {
      StringBuilder str = new StringBuilder();
      for (int n = 0; n < Math.abs(rand.nextInt()) % 50; n += 1) {
        int index = Math.abs(rand.nextInt() % alphabet.length());
        str.append(arr[index]);
      }
      Appendable out = new StringBuilder();
      ImageEditorView v = new ImageEditorTextView(out);
      v.renderMessage(str.toString());
      assertEquals(str.toString(), out.toString());
    }
  }
}
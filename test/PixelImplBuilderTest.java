import model.pixel.PixelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link model.pixel.PixelImpl.PixelImplBuilder}.
 */
public class PixelImplBuilderTest extends PixelBuilderTest {
  public PixelImplBuilderTest() {
    super(new PixelImpl.PixelImplBuilder());
  }

  @Override
  public void buildDefault() {
    assertEquals(new PixelImpl(0, 0, 0, 255), builder.build());
  }
}

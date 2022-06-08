import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;

/**
 * Tests for {@link model.pixel.PixelImpl.PixelImplBuilder}.
 *
 * @author emery
 * @created 2022-06-07
 */
public class PixelImplBuilderTest extends PixelBuilderTest {
  public PixelImplBuilderTest() {
    super(new PixelImpl.PixelImplBuilder());
  }
}

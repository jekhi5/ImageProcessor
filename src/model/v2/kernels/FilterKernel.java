package model.v2.kernels;

import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

/**
 * Represents the implementation of an image kernel that is an odd-sided square mapping of numbers.
 */
public class FilterKernel extends AbstractMatrixOperator {
  /**
   * To set this kernel's matrix to the given matrix. Because the Builder always maintains an
   * odd-side-length square size of the matrix, we do not need to check that here.
   *
   * @param matrix the matrix of doubles
   */
  protected FilterKernel(double[][] matrix) {
    super(matrix);
  }

  @Override
  public Pixel resultAt(int row, int col, Image image) throws IllegalArgumentException {

    if (image == null) {
      throw new IllegalArgumentException("Error. The given image cannot be null.");
    }

    int pixelR = row - (this.matrix.length / 2);
    int pixelC = col - (this.matrix.length / 2);
    int[] resultRGB = new int[3];

    for (int r = 0; r < this.matrix.length; r++) {
      for (int c = 0; c < this.matrix.length; c++) {
        int red = 0;
        int green = 0;
        int blue = 0;
        // If the pixel exists, then set channelValue to the desired channel's value in that
        // pixel, if not, we've already instantiated _channelValue_ to 0
        try {
          Pixel p = image.getPixelAt(pixelR, pixelC);
          red = (int) (p.getRed() * (this.matrix[r][c]));
          green = (int) (p.getGreen() * (this.matrix[r][c]));
          blue = (int) (p.getBlue() * (this.matrix[r][c]));
        } catch (IllegalArgumentException ignored) {
        }

        resultRGB[0] = Math.max(0, Math.min(resultRGB[0] + red, 255));
        resultRGB[1] = Math.max(0, Math.min(resultRGB[1] + green, 255));
        resultRGB[2] = Math.max(0, Math.min(resultRGB[2] + blue, 255));

        pixelC++;
      }
      pixelR++;
      pixelC = col - (this.matrix.length / 2);
    }
    int alpha = image.getPixelAt(row, col).getAlpha();
    PixelImpl.PixelImplBuilder pb = new PixelImpl.PixelImplBuilder()
            .red(resultRGB[0])
            .green(resultRGB[1])
            .blue(resultRGB[2])
            .alpha(alpha)

    return new PixelImpl(resultRGB[0], resultRGB[1], resultRGB[2],
            );
  }

  /**
   * A builder for FilterKernel.
   * Allows user to set size and values.
   */
  public static class KernelBuilder extends MatrixOperatorBuilder {

    /**
     * Creates a new KernelBuilder.
     */
    public KernelBuilder() {
      super();
    }

    @Override
    public PixelOperator build() throws IllegalStateException {
      return new FilterKernel(matrix);
    }
  }
}

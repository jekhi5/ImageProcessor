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
    int[] resultRGB = new int[4];

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
    return new PixelImpl(resultRGB[0], resultRGB[1], resultRGB[2],
            image.getPixelAt(row, col).getAlpha());
  }

  public static class KernelBuilder extends MatrixOperatorBuilder {

    @Override
    public PixelOperator build() throws IllegalStateException {
      return null;
    }

    /**
     * To set the size of the matrix. This must be the first call after the constructor and cannot
     * be called more than once.
     *
     * @param size the dimensions of the matrix. Must be positive and odd
     * @return this builder after setting the size
     * @throws IllegalArgumentException if the size of the matrix has already been set or the size
     *                                  is negative/even
     */
    public MatrixOperatorBuilder size(int size) throws IllegalArgumentException {
      if (this.matrix == null && (size < 1 || size % 2 == 0)) {
        throw new IllegalArgumentException(
                "Error. Given size cannot be negative or even. Given: " + size);
      }

      // Will initialize all locations to 0.0
      this.matrix = new double[size][size];
      return this;
    }

  }
}

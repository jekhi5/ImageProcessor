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
      throw new IllegalArgumentException("The given image cannot be null.");
    }

    int pixelR = row - (this.matrix.length / 2);
    int pixelC = col - (this.matrix.length / 2);
    double[] resultRGB = new double[3];

    for (int r = 0; r < this.matrix.length; r++) {
      for (int c = 0; c < this.matrix.length; c++) {
        double red = 0;
        double green = 0;
        double blue = 0;
        try {
          Pixel p = image.getPixelAt(pixelR, pixelC);
          red = (p.getRed() * (this.matrix[r][c]));
          green = (p.getGreen() * (this.matrix[r][c]));
          blue = (p.getBlue() * (this.matrix[r][c]));
        } catch (IllegalArgumentException ignored) {
        }

        resultRGB[0] += red;
        resultRGB[1] += green;
        resultRGB[2] += blue;

        pixelC++;
      }
      pixelR++;
      pixelC = col - (this.matrix.length / 2);
    }
    resultRGB[0] = Math.max(0, Math.min(resultRGB[0], 255));
    resultRGB[1] = Math.max(0, Math.min(resultRGB[1], 255));
    resultRGB[2] = Math.max(0, Math.min(resultRGB[2], 255));
    int alpha = image.getPixelAt(row, col).getAlpha();

    return new PixelImpl.PixelImplBuilder()
            .red((int) resultRGB[0])
            .green((int) resultRGB[1])
            .blue((int) resultRGB[2])
            .alpha(alpha)
            .build();
  }

  /**
   * A builder for FilterKernel. Allows user to set size and values.
   */
  public static class KernelBuilder extends MatrixOperatorBuilder {

    /**
     * Creates a new KernelBuilder.
     */
    public KernelBuilder() {
      super();
    }

    /**
     * To create a KernelBuilder with the given Matrix.
     *
     * @param matrix the matrix to make the kernel builder with
     * @return the newly constructed KernelBuilder
     */
    public KernelBuilder(double[][] matrix) {
      super(matrix);
    }

    @Override
    public PixelOperator build() throws IllegalStateException {
      return new FilterKernel(matrix);
    }
  }
}

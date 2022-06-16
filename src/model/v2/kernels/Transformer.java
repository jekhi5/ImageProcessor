package model.v2.kernels;

import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

/**
 * A PixelOperator specialized for color filtering operations like grayscale or sepia. A Transformer
 * only acts based on a single pixel's color, as opposed to the surrounding pixels.
 */
public class Transformer extends AbstractMatrixOperator {

  /**
   * To set this kernel's matrix to the given matrix. Because the Builder always maintains an
   * odd-side-length square size of the matrix, we do not need to check that here.
   *
   * @param matrix the matrix of doubles
   */
  protected Transformer(double[][] matrix) {
    super(matrix);
  }

  @Override
  public Pixel resultAt(int row, int col, Image image) throws IllegalArgumentException {
    Pixel origPixel = image.getPixelAt(row, col);
    int[] rgb = new int[]{origPixel.getRed(), origPixel.getGreen(), origPixel.getBlue()};
    double[] rgbPrime = new double[3];

//    for (int c = 0; c < 3; c++) {
//      double prime = 0;
//      for (int r = 0; r < 3; r++) {
//        prime += matrix[r][c] * rgb[c];
//      }
//      rgbPrime[c] = prime;
//    }

    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        rgbPrime[r] += matrix[r][c] * rgb[c];
      }
    }

    for (int i = 0; i < 3; i++) {
      rgbPrime[i] = Math.max(0, Math.min(255, rgbPrime[i]));
    }

    return new PixelImpl.PixelImplBuilder()
            .red((int) rgbPrime[0])
            .green((int) rgbPrime[1])
            .blue((int) rgbPrime[2])
            .alpha(origPixel.getAlpha())
            .build();
  }


  /**
   * A builder for Transformers. Allows users to set values, but not sizes, because a Color
   * Transform
   */
  public static class TransformerBuilder extends MatrixOperatorBuilder {

    /**
     * Creates a new TransformerBuilder.
     */
    public TransformerBuilder() {
      super(new double[3][3]);
    }

    private TransformerBuilder(double[][] matrix) {
      super(matrix);
    }

    /**
     * Throws an IllegalCallerException because we can't set the size of a Color Transformer. This
     * allows us to enforce an invariant such that the dimensions of a Transformer matrix are al
     *
     * @param size a size
     * @return nothing
     * @throws IllegalCallerException when called.
     */
    @Override
    public MatrixOperatorBuilder size(int size) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("A transformer matrix is always 3x3!");
    }

    @Override
    public PixelOperator build() throws IllegalStateException {
      return new Transformer(matrix);
    }
  }
}

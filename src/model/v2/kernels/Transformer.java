package model.v2.kernels;

/**
 * A PixelOperator specialized for color filtering operations like grayscale or sepia.
 * A Transformer only acts based on a single pixel's color, as opposed to the surrounding pixels.
 */
public class Transformer extends FilterKernel {

  /**
   * To set this kernel's matrix to the given matrix. Because the Builder always maintains an
   * odd-side-length square size of the matrix, we do not need to check that here.
   *
   * @param matrix      the matrix of doubles
   * @param channelFunc
   */
  protected Transformer(double[][] matrix) {
     super(matrix);
  }


  /**
   * A builder for Transformers.
   */
  public static class TransformerBuilder extends KernelBuilder {

    /**
     * Creates a new TransformerBuilder.
     */
    public TransformerBuilder() {
      super();
    }


  }

}

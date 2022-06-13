package model.v2;

import java.lang.invoke.SwitchPoint;
import java.util.function.Function;

import model.image.Image;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

/**
 * Represents the implementation of an image kernel that is an odd-sided square mapping of numbers.
 */
public class KernelImpl implements Kernel {

  private final double[][] matrix;
  private final Function<Pixel, Integer> channelFunc;

  /**
   * To set this kernel's matrix to the given matrix. Because the Builder always maintains an
   * odd-side-length square size of the matrix, we do not need to check that here.
   *
   * @param matrix the matrix of doubles
   */
  private KernelImpl(double[][] matrix, Function<Pixel, Integer> channelFunc) {
    this.matrix = matrix;
    this.channelFunc = channelFunc;
  }

  @Override
  public int resultAt(int row, int col, Image image) throws IllegalArgumentException {

    if (image == null) {
      throw new IllegalArgumentException("Error. The given image cannot be null.");
    }

    int pixelR = row - (this.matrix.length / 2);
    int pixelC = col - (this.matrix.length / 2);
    int result = 0;

    for (int r = 0; r < this.matrix.length; r++) {
      for (int c = 0; c < this.matrix.length; c++) {
        int channelValue = 0;
        // If the pixel exists, then set channelValue to the desired channel's value in that
        // pixel, if not, we've already instantiated _channelValue_ to 0
        try {
          Pixel p = image.getPixelAt(pixelR, pixelC);
          channelValue = (int) (this.channelFunc.apply(p) * (this.matrix[r][c]));


        } catch (IllegalArgumentException ignored) {
        }

        result += channelValue;

        pixelC++;
      }
      pixelR++;
      pixelC = col - (this.matrix.length / 2);
    }
    return Math.max(0, Math.min(255, result));
  }

  /**
   * Represents a builder for a {@link Kernel}.
   */
  public static class KernelBuilder {

    private double[][] matrix;
    private Function<Pixel, Integer> channelFunc;

    /**
     * To set the size of the matrix. This must be the first call after the constructor and cannot
     * be called more than once.
     *
     * @param size the dimensions of the matrix. Must be positive and odd
     * @return this builder after setting the size
     * @throws IllegalArgumentException if the size of the matrix has already been set or the size
     *                                  is negative/even
     */
    public KernelBuilder size(int size) throws IllegalArgumentException {
      if (this.matrix == null && (size < 1 || size % 2 == 0)) {
        throw new IllegalArgumentException(
                "Error. Given size cannot be negative or even. Given: " + size);
      }

      // Will initialize all locations to 0.0
      this.matrix = new double[size][size];
      return this;
    }

    /**
     * To set the value held at the given coordinates in the matrix to the given value.
     *
     * @param row   the row of the matrix to set the given value
     * @param col   the col of the matrix to set the given value
     * @param value the value that will be placed at the given coordinates
     * @return this builder after setting the value
     * @throws IllegalArgumentException if the matrix's size has not been set using the
     *                                  {@link KernelBuilder#size(int)} method, or if the
     *                                  coordinates are out of bounds of the matrix
     */
    public KernelBuilder valueAt(int row, int col, double value) throws IllegalArgumentException {
      if (this.matrix == null) {
        this.throwSizeNotSet();
      } else if (row < 0 || row > this.matrix.length || col < 0 || col > this.matrix[row].length) {
        throw new IllegalArgumentException("Error. The given coordinates are out of bounds for " +
                "this kernel. Given: (" + row + ", " + col + "). The dimensions of this matrix " +
                "are: " + this.matrix.length + "x" + this.matrix.length);
      }

      this.matrix[row][col] = value;
      return this;
    }

    /**
     * To set the channel func of this builder to the given function.
     *
     * @param channelFunc the channel function that will take a pixel and return the value of a
     *                    specific channel
     * @return this builder after setting the function
     * @throws IllegalArgumentException if the given function is null
     */
    public KernelBuilder channelFunc(Function<Pixel, Integer> channelFunc)
            throws IllegalArgumentException {
      if (channelFunc == null) {
        throw new IllegalArgumentException("Error. The channel function cannot be null.");
      }

      this.channelFunc = channelFunc;
      return this;
    }


    /**
     * To build this kernel builder as a {@link KernelImpl}.
     *
     * @return the newly constructed KernelImpl with this matrix as the new KernelImpl's matrix
     * @throws IllegalStateException if the size of the matrix was never set
     */
    public KernelImpl build() throws IllegalStateException {
      if (this.matrix == null) {
        this.throwSizeNotSet();
      } else if (this.channelFunc == null) {
        throw new IllegalStateException("Error. The channel function was not set! You must call " +
                "the KernelBuilder.channelFunc(Function<Pixel, Integer>) method some-point before " +
                "building!");
      }

      return new KernelImpl(this.matrix, this.channelFunc);
    }

    private void throwSizeNotSet() throws IllegalStateException {
      throw new IllegalStateException("Error. The size of the matrix was not set! You must call" +
              " KernelBuilder.size(int) as the first call after creating the builder.");
    }
  }
}

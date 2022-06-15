package model.v2.kernels;

import model.image.Image;
import model.pixel.Pixel;

/**
 * An abstract class which contains code used in both FilterKernel and Transformer
 */
public abstract class AbstractMatrixOperator implements PixelOperator {
  protected double[][] matrix;

  protected AbstractMatrixOperator(double[][] matrix) {
    this.matrix = matrix;
  }

  @Override
  public abstract Pixel resultAt(int row, int col, Image image) throws IllegalArgumentException;

  protected static abstract class MatrixOperatorBuilder {
    protected double[][] matrix;

    public

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
    public MatrixOperatorBuilder valueAt(int row, int col, double value)
            throws IllegalArgumentException {
      if (this.matrix == null) {
        this.throwSizeNotSet();
      } else if (row < 0 || row > this.matrix.length || col < 0 || col > this.matrix[row].length) {
        throw new IllegalArgumentException("Error. The given coordinates are out of bounds for " +
                "this matrix. Given: (" + row + ", " + col + "). The dimensions of this matrix " +
                "are: " + this.matrix.length + "x" + this.matrix.length);
      }

      this.matrix[row][col] = value;
      return this;
    }

    /**
     * To build this kernel builder as a {@link FilterKernel}.
     *
     * @return the newly constructed KernelImpl with this matrix as the new KernelImpl's matrix
     * @throws IllegalStateException if the size of the matrix was never set
     */
    public abstract PixelOperator build() throws IllegalStateException;


    protected void throwSizeNotSet() throws IllegalStateException {
      throw new IllegalStateException("Error. The size of the matrix was not set! You must call" +
              " size(int) as the first call after creating the builder.");
    }
  }
}

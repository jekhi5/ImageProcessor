package model.v2;

import model.image.Image;
import model.pixel.Pixel;

/**
 * Represents an image kernel that is an odd-sided square mapping of numbers.
 */
public interface Kernel {

  /**
   * To apply {@code this} kernel to the given {@link Image} at the given coordinates, and return
   * the resulting value.
   *
   * @param row   the row of the {@link Pixel} in the {@code Image}, to be placed at the center of
   *              {@code this} {@code Kernel}
   * @param col   the col of the {@link Pixel} in the {@code Image}, to be placed at the center of
   *              {@code this} {@code Kernel}
   * @param image the {@code Image} that {@code this} {@code Kernel} will be applied to
   * @return the resulting integer after applying {@code this} {Kernel} to the given {@code Image }
   *         and chopping off the resulting double's decimal value
   * @throws IllegalArgumentException if the row or col is out of bounds of the given {@code image},
   *                                  or if the {@code image} is {@code null}
   */
  int resultAt(int row, int col, Image image) throws IllegalArgumentException;
}

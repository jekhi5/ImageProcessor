package seamcarving;

import java.util.ArrayList;
import java.util.List;

import model.image.Image;
import model.pixel.Pixel;

/**
 * Represents a grid of an image's pixels.
 */
class PixelGrid {

  private LinkedPixel topLeftPixel;
  private int width;
  private int height;


  /**
   * Creates a pixel grid from the pixels in the given image.
   *
   * @param image the image to convert to a gridded image
   * @throws java.lang.IllegalArgumentException if the given image is null, if either the width or
   *                                            height of the given image is less than or equal to
   *                                            0, or if the given image is not rectangular
   */
  public PixelGrid(Image image) throws IllegalArgumentException {
    List<List<Pixel>> pixelGrid = new ArrayList<>();
    for (int row = 0; row < image.getHeight(); row += 1) {
      List<Pixel> curRow = new ArrayList<>();
      for (int col = 0; col < image.getWidth(); col += 1) {
        curRow.add(image.getPixelAt(row, col));
      }
      pixelGrid.add(curRow);
    }

    this.topLeftPixel = generateGrid(pixelGrid);
    this.width = image.getWidth();
    this.height = image.getHeight();
  }

  /**
   * Creates a pixel grid from the given grid of pixels.
   *
   * @param pixelGrid the grid of pixels
   * @throws java.lang.IllegalArgumentException if the given grid is null, if either the width or
   *                                            height of the given grid is less than or equal to 0,
   *                                            or if the given grid is not rectangular
   */
  public PixelGrid(List<List<Pixel>> pixelGrid) {
    this.topLeftPixel = generateGrid(pixelGrid);
    this.width = pixelGrid.size();
    this.height = pixelGrid.get(0).size();
  }

  private static LinkedPixel generateGrid(List<List<Pixel>> pixelGrid) {
    if (pixelGrid == null) {
      throw new IllegalArgumentException("The given image data cannot be null.");
    } else if (pixelGrid.size() <= 0 || pixelGrid.get(0).size() <= 0) {
      throw new IllegalArgumentException(
              "The width nor the height of the image data can be less " +
                      "than 1.");
    }

    int sizeOfFirstRow = pixelGrid.get(0).size();
    for (List<Pixel> row : pixelGrid) {
      if (row.size() != sizeOfFirstRow) {
        throw new IllegalArgumentException("All rows of the grid must be the same length!");
      }
    }

    LinkedPixel topLeftPixel = new LinkedPixelImpl(pixelGrid.get(0).get(0));

    LinkedPixel currentPixel = topLeftPixel;
    LinkedPixel leftmostPixelInRow = currentPixel;
    LinkedPixel matchingPixelInRowAbove = leftmostPixelInRow;

    // This is the leftmost pixel of the row

    // To create the first row of pixels
    for (int col = 1; col < pixelGrid.size(); col += 1) {
      LinkedPixel nextPixel = new LinkedPixelImpl(pixelGrid.get(0).get(col));
      nextPixel.linkLeft(currentPixel);
      currentPixel = nextPixel;
    }


    // To create the rest of the grid
    for (int row = 1; row < pixelGrid.get(0).size(); row += 1) {

      // This is the first pixel in the row
      currentPixel = new LinkedPixelImpl(pixelGrid.get(row).get(0));
      currentPixel.linkUp(matchingPixelInRowAbove);
      leftmostPixelInRow = currentPixel;

      for (int col = 1; col < pixelGrid.get(row).size(); col += 1) {
        matchingPixelInRowAbove = matchingPixelInRowAbove.getRight();

        LinkedPixel nextPixel = new LinkedPixelImpl(pixelGrid.get(row).get(col));
        nextPixel.linkLeft(currentPixel);
        nextPixel.linkUp(matchingPixelInRowAbove);
        currentPixel = nextPixel;
      }

      matchingPixelInRowAbove = leftmostPixelInRow;
    }

    return topLeftPixel;
  }

  /**
   * Performs a single iteration of finding the best seam to remove and removing it.
   */
  public void iterate(boolean removingVerticalSeam) {
    SeamInfo seamToRemove = findSeamToRemove(removingVerticalSeam);
    removeSeam(seamToRemove);
  }

  /**
   * To get a {@code Pixel} at the given coordinates.
   *
   * @param row the row of the requested pixel
   * @param col the column of the requested pixel
   * @return the {@code Pixel} at the given coordinates
   * @throws IllegalArgumentException if the coordinates are at an invalid location
   */
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row >= height || col >= width || row < 0 || col < 0) {
      throw new IllegalArgumentException("The given row and column are out of bounds. The max row" +
              " for this image is: " + (height - 1) + " and the make col for this image is: " +
              (width - 1));
    }

    LinkedPixel curPixel = topLeftPixel;

    for (int r = 0; r <= row; r += 1) {
      curPixel = curPixel.getDown();
    }

    for (int c = 0; c <= col; c += 1) {
      curPixel = curPixel.getRight();
    }

    return curPixel.getPixelDelegate();
  }

  /**
   * Gets the least interesting seam in the given list of {@code SeamInfo}s.
   *
   * @param seams the list of seams to be considered
   * @return the seam with the lowest seamWeight
   */
  private SeamInfo findLeastInteresting(List<SeamInfo> seams) {
    //TODO: See if there is a .stream() operation that can perform the function of this method.
    // Maybe .reduce()?
    SeamInfo leastInterestingSeam = seams.get(0);

    // To get the seam with the lowest weight
    for (int i = 1; i < seams.size(); i += 1) {
      if (seams.get(i).seamWeight < leastInterestingSeam.seamWeight) {
        leastInterestingSeam = seams.get(i);
      }
    }

    return leastInterestingSeam;
  }

  /**
   * Gets the seam with the lowest energy in the entirety of this grid depending on the requested
   * direction of the seam.
   *
   * @param searchingVertical the flag to determine if the seam to be found should be vertical or
   *                          horizontal
   * @return the {@code SeamInfo} with the lowest energy
   */
  private SeamInfo findSeamToRemove(boolean searchingVertical) {

    ArrayList<SeamInfo> newSeams = new ArrayList<>();

    ArrayList<SeamInfo> seams = new ArrayList<>();

    SeamInfo firstNeighbor; // When searching vert., left neighbor, when horiz, upper neighbor
    SeamInfo middleNeighbor;
    SeamInfo thirdNeighbor; // When searching vert, right neighbor, when horiz, lower neighbor

    SeamInfo newSeam;

    // Represents a seam that is really heavy. Is used to represent a seam that would go
    // outside the image. IE: When searching for a vertical seam, the first neighbor of a pixel
    // on the far left of the image doesn't exist, so we fill it with this so the algorithm
    // doesn't choose it.
    SeamInfo heavySeam =
            new SeamInfo(null, new BorderPixel().getEnergy(), null, searchingVertical);

    LinkedPixel currentPixel = topLeftPixel;
    LinkedPixel firstPixelInChunk = currentPixel;

    // The chunk of pixels we will be iterating over. If we were searching vertically, then
    // we iterate over entire rows at a time, and then to each pixel within that row. If we are
    // searching horizontally, then we iterate over entire columns at a time, and then to each
    // pixel within that column. And so we need to know which dimension that direction pertains
    // to.
    int chunkDimension = searchingVertical ? height : width;

    // This is the inner chink. IE: iterating over the selected row, or iterating over the
    // selected column.
    int innerChunkDimension = searchingVertical ? width : height;

    // To load the list with the first row/col of seams depending on the desired direction
    for (int innerAxis = 0; innerAxis < innerChunkDimension; innerAxis += 1) {
      newSeam = new SeamInfo(currentPixel, currentPixel.getEnergy(), null,
              searchingVertical);
      seams.add(newSeam);
      currentPixel = topLeftPixel.getRight();

      if (topLeftPixel.isBorderPixel()) {
        throw new IllegalStateException("The given width is too large!");
      }
    }

    if (!topLeftPixel.isBorderPixel()) {
      throw new IllegalStateException("The given width is too small!");
    }

    firstPixelInChunk = firstPixelInChunk.getDown();
    currentPixel = firstPixelInChunk;

    for (int outerAxis = 1; outerAxis < chunkDimension; outerAxis += 1) {

      // To iterate through each column to find the best seam for each pixel
      for (int innerAxis = 0; innerAxis < innerChunkDimension; innerAxis += 1) {
        try {
          firstNeighbor = seams.get(innerAxis - 1);
        } catch (IndexOutOfBoundsException e) {
          firstNeighbor = heavySeam;
        }

        middleNeighbor = seams.get(innerAxis);

        try {
          thirdNeighbor = seams.get(innerAxis + 1);
        } catch (IndexOutOfBoundsException e) {
          thirdNeighbor = heavySeam;
        }

        SeamInfo bestSeam = this.findBestSeam(firstNeighbor, middleNeighbor, thirdNeighbor);
        newSeam =
                new SeamInfo(currentPixel, currentPixel.getEnergy() + bestSeam.seamWeight, bestSeam,
                        searchingVertical);

        newSeams.add(innerAxis, newSeam);

        // To override all the old seams with the newly created seams
        seams = new ArrayList<>(newSeams);

        newSeams = new ArrayList<>();
      }

      // We want to move to the start of the row/col depending on which direction we are
      // searching foe
      firstPixelInChunk =
              searchingVertical ? firstPixelInChunk.getDown() : firstPixelInChunk.getRight();
      currentPixel = firstPixelInChunk;
    }

    return this.findLeastInteresting(seams);
  }


  /**
   * Gets the least interesting seam of the three given.
   *
   * @param neighbor1      the first neighbor (up for horizontal seams and left for vertical seams)
   *                       to consider
   * @param middleNeighbor the middle neighbor to consider
   * @param neighbor3      the third neighbor (down for horizontal seams and right for vertical
   *                       seams) to consider
   * @return the {@code SeamInfo} that has the lowest energy of the three given
   */
  private SeamInfo findBestSeam(SeamInfo neighbor1, SeamInfo middleNeighbor, SeamInfo neighbor3) {

    // We make the new seam based on the smallest weight of the two available neighbors
    // Field of Field is allowed here because these classes are inherently linked and
    // they work with one another. In class Professor Lerner mentioned that this was
    // acceptable if we can prove that it's valid. This applies to all future cases

    if ((neighbor1.seamWeight <= middleNeighbor.seamWeight)
            && (neighbor1.seamWeight <= neighbor3.seamWeight)) {

      return neighbor1;

    } else if ((middleNeighbor.seamWeight < neighbor1.seamWeight)
            && (middleNeighbor.seamWeight <= neighbor3.seamWeight)) {

      return middleNeighbor;

    } else {

      return neighbor3;
    }
  }

  /**
   * Removes the given seam from the image.
   *
   * @throws java.lang.IllegalArgumentException if the given seam is null
   */
  private void removeSeam(SeamInfo seamToRemove) throws IllegalArgumentException {

    if (seamToRemove == null) {
      throw new IllegalArgumentException("The given seam cannot be null.");
    }

    boolean seekingVertSeam = seamToRemove.isVertical;

    if ((width <= 0 && seekingVertSeam)
            || (height <= 0 && !seekingVertSeam)) {
      this.topLeftPixel = new BorderPixel();
      this.height = 1;
      this.width = 1;
    } else {
      // This ensures that there actually are pixels image left in the grid.
      // If there is just one pixel left, then there is essentially
      // no grid, thus we should do nothing
      if (!topLeftPixel.isBorderPixel()) {

        width -= seekingVertSeam ? 1 : 0;
        height -= !seekingVertSeam ? 1 : 0;

        LinkedPixel pixelToRemove = seamToRemove.pixel;
        SeamInfo nextSeam = seamToRemove.cameFrom;

        if (seekingVertSeam) {
          removeVerticalSeam(nextSeam, pixelToRemove);
        } else {
          removeHorizontalSeam(nextSeam, pixelToRemove);
        }
      }
    }
  }

  private void removeHorizontalSeam(SeamInfo initialSeam, LinkedPixel initialPixelToRemove) {

    SeamInfo nextSeam = initialSeam;
    LinkedPixel pixelToRemove = initialPixelToRemove;

    // To iterate through the grid until we run out of seams
    // This will terminate because we are constantly moving left towards the end of the image
    while (nextSeam != null) {
      pixelToRemove.getDown().linkUp(pixelToRemove.getUp());

      // Is the next pixel to the left and up?
      // We can do field of field here because we know next seam isn't null
      if (nextSeam.pixel == pixelToRemove.getLeft().getUp()) {
        pixelToRemove.getUp().linkLeft(pixelToRemove.getLeft());
        // Is the next pixel to the right and down?
      } else if (nextSeam.pixel == pixelToRemove.getLeft().getDown()) {
        pixelToRemove.getDown().linkLeft(pixelToRemove.getLeft());
      }

      // if neither case is true, we know the next pixel to remove is straight to the left,
      // and so we don't need to reconnect any horizontal connections

      pixelToRemove = nextSeam.pixel;
      nextSeam = nextSeam.cameFrom;

    }

    if (pixelToRemove == topLeftPixel) {
      this.topLeftPixel = topLeftPixel.getDown();
    }

    // This is for the leftmost column
    pixelToRemove.getDown().linkUp(pixelToRemove.getUp());
  }

  private void removeVerticalSeam(SeamInfo initialSeam, LinkedPixel initialPixelToRemove) {

    SeamInfo nextSeam = initialSeam;
    LinkedPixel pixelToRemove = initialPixelToRemove;


    // To iterate through the grid until we run out of seams
    // This will terminate because we are constantly moving
    // upwards towards the end of the image
    while (nextSeam != null) {

      pixelToRemove.getRight().linkLeft(pixelToRemove.getLeft());

      // Is the next pixel up and to the right?
      // We can do field of field here because we know next seam isn't null
      if (nextSeam.pixel == pixelToRemove.getUp().getLeft()) {
        pixelToRemove.getLeft().linkUp(pixelToRemove.getUp());
        // Is the next pixel up and to the right?
      } else if (nextSeam.pixel == pixelToRemove.getUp().getRight()) {
        pixelToRemove.getRight().linkUp(pixelToRemove.getUp());
      }

      // if neither case is true, we know the next pixel to remove is straight up,
      // and so we don't need to reconnect any vertical connections

      pixelToRemove = nextSeam.pixel;
      nextSeam = nextSeam.cameFrom;
    }

    if (pixelToRemove == topLeftPixel) {
      this.topLeftPixel = topLeftPixel.getRight();
    }

    // This is for the top row
    pixelToRemove.getRight().linkLeft(pixelToRemove.getLeft());
  }

  /**
   * Represents a Seam. The pixel held within is the bottom most pixel.
   */
  private static class SeamInfo {
    private final LinkedPixel pixel;
    private final double seamWeight;
    private final SeamInfo cameFrom;
    private final boolean isVertical;

    SeamInfo(LinkedPixel pixel, double seamWeight, SeamInfo cameFrom, boolean isVertical) {
      this.pixel = pixel;
      this.seamWeight = seamWeight;
      this.cameFrom = cameFrom;
      this.isVertical = isVertical;
    }
  }

}
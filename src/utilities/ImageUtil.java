package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import model.image.Image;
import model.image.PPMImage;
import model.pixel.Pixel;
import model.pixel.PixelImpl;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * To create an {@link Image} object of the proper type based on the given path to the image.
   *
   * @param path is the path to the image
   * @return the newly created image
   * @throws IllegalArgumentException if the path is bad or the filetype is not supported
   */
  public static Image createImageFromPath(String path) throws IllegalArgumentException {
    // As more filetypes become accepted, we will extend this map
    Map<String, Function<String, Image>> supportedFileTypes = new HashMap<>();
    supportedFileTypes.put(".ppm", ImageUtil::readPPM);

    String suffix = ImageUtil.getSuffix(path);
    if (supportedFileTypes.containsKey(suffix)) {
      return supportedFileTypes.get(suffix).apply(path);
    } else {
      throw new IllegalArgumentException("Error. Filetype: \"" + suffix + "\" is not supported.");
    }
  }

  /**
   * To get the suffix of a path.
   *
   * @param path is the path to the file
   * @return the string version of the filetype including the period
   * @throws IllegalArgumentException if the path is bad
   */
  private static String getSuffix(String path) throws IllegalArgumentException {
    int indexOfSuffix = path.lastIndexOf(".");
    if (indexOfSuffix < 0) {
      throw new IllegalArgumentException("Error. Invalid path.");
    } else {
      return path.substring(indexOfSuffix);
    }
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   * @return a completed PPMImage
   * @throws IllegalArgumentException if there is a bad path, or the width or height of the image is
   *                                  less than 1
   */
  private static PPMImage readPPM(String filename) throws IllegalArgumentException {
    List<List<Pixel>> resultingPixelGrid = new ArrayList<>();
    Scanner sc;

    FileInputStream fis;
    try {
      fis = new FileInputStream(filename);
      sc = new Scanner(fis);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("Error. The given image has a width or height of 0.");
    }

    sc.nextInt(); // max value

    for (int i = 0; i < height; i++) {
      List<Pixel> curRow = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        curRow.add(new PixelImpl(r, g, b, 255));
      }
      resultingPixelGrid.add(curRow);
    }

    try {
      fis.close();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
    sc.close();
    return new PPMImage(resultingPixelGrid);
  }

  /**
   * To save the given image to the given path. If the user provides an extension which does not
   * match the filetype that the image was originally loaded from, it may corrupt the file, and this
   * is on the user.
   *
   * <p>
   * <p>
   * If {@code shouldOverwrite} is {@code true}:
   * <ol>
   *   <li>this method will attempt to delete the file currently stored at the given path</li>
   *   <li>and then attempt to place the given image at the given path</li>
   * </ol>
   * <p>
   * If {code shouldOverwrite} is {@code false}:
   * <ol>
   *   <li>this method will attempt to place the given image at the given path</li>
   * </ol>
   * <p>
   * If any of these operations fail, the method will throw an IllegalArgumentException.
   *
   * </p>
   *
   * @param image           is the image to store
   * @param path            is the path to store the image
   * @param shouldOverwrite is a flag for whether this method should overwrite the file currently
   *                        stored at this location
   * @throws IllegalArgumentException if the path is bad or any of the storing/deleting fails
   */
  public static void saveImage(Image image, String path, boolean shouldOverwrite)
          throws IllegalArgumentException {

    if (image == null || path == null) {
      throw new IllegalArgumentException("Error. The given image or path was null.");
    }

    File file = new File(path);

    if (shouldOverwrite && file.exists()) {
      boolean wasSuccessfullyDeleted = file.delete();

      if (!wasSuccessfullyDeleted) {
        throw new IllegalArgumentException("Error. Cannot delete file at path: " + path);
      }
    }

    try {
      if (!file.createNewFile()) {
        throw new IllegalArgumentException("Error. Could not create file from path: " + path +
                ". There was already a file at this location. To overwrite, add \"true\" to command.");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Error. Bad path: " + path);
    } catch (SecurityException e) {
      throw new IllegalArgumentException(
              "Error. Cannot create file at given path: " + path + "\n" + e.getMessage());
    }

    BufferedWriter ppmWriter;
    try {
      ppmWriter = new BufferedWriter(new FileWriter(path));
      ppmWriter.write(image.toPPMText());
      ppmWriter.close();
    } catch (IOException e) {
      throw new IllegalArgumentException(
              "Error. Cannot write to a file at the given path: " + path);
    }
  }
}


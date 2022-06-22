package utilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import model.pixel.PixelBuilder;
import model.pixel.PixelImpl;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * To create an {@link Image} object of the proper type based on the given path to the image. In
   * order to create uniformity between many file types, our program normalizes all colors to have
   * minimum component values of 0, and maximum component values of 255. This has the following
   * benefits: - uniformity - easy conversion to hexadecimal color codes - easier to work and do
   * math with and the following cons: - loss of information in some file formats (uncommon)
   *
   * @param path is the path to the image
   * @return the newly created image
   * @throws IllegalArgumentException if the path is bad or the filetype is not supported
   */
  public static Image createImageFromPath(String path) throws IllegalArgumentException {
    // As more filetypes become accepted, we will extend this map
    Map<String, Function<String, Image>> supportedFileTypes = new HashMap<>();
    supportedFileTypes.put("ppm", ImageUtil::readPPM);

    String suffix = ImageUtil.getSuffix(path);
    if (supportedFileTypes.containsKey(suffix)) {
      return supportedFileTypes.get(suffix).apply(path);
    } else {
      throw new IllegalArgumentException("Filetype: \"" + suffix + "\" is not supported.");
    }
  }

  /**
   * To get the suffix of a path.
   *
   * @param path is the path to the file
   * @return the string version of the filetype including the period
   * @throws IllegalArgumentException if the path is bad
   */
  public static String getSuffix(String path) throws IllegalArgumentException {
    int indexOfSuffix = path.lastIndexOf(".");
    if (indexOfSuffix < 0 || indexOfSuffix >= path.length() - 1) {
      throw new IllegalArgumentException("Invalid path.");
    } else {
      return path.substring(indexOfSuffix + 1);
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
      throw new IllegalArgumentException("The given image has a width or height of 0.");
    }

    double maxValue = sc.nextInt(); // max value

    for (int i = 0; i < height; i++) {
      List<Pixel> curRow = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        if (Math.min(r, Math.min(g, b)) < 0 || maxValue >= 65536) {
          throw new IllegalArgumentException("Invalid PPM file!");
        }

        // scale values into 255
        r = Math.min(255, r);
        g = Math.min(255, g);
        b = Math.min(255, b);

        PixelBuilder build = new PixelImpl.PixelImplBuilder();
        build.red(r);
        build.green(g);
        build.blue(b);
        curRow.add(build.build());
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
   * To construct a buffered image from an instance of {@link model.image.Image}.
   *
   * @param image the image to convert
   * @return a buffered image of the given image
   */
  public static BufferedImage toBufferedImage(Image image) {
    BufferedImage bi =
            new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int r = 0; r < image.getHeight(); r++) {
      for (int c = 0; c < image.getWidth(); c++) {
        Pixel p = image.getPixelAt(r, c);
        Color color = new Color(p.getRed(), p.getGreen(), p.getBlue());
        bi.setRGB(c, r, color.getRGB());
      }
    }
    return bi;
  }
}


package model.v2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * A class that specifically saves images in different formats.
 */
public class ImageSaver {

  /**
   * Functionally identical to ImageIO.write(~), except that it adds support for PPM files. Note
   * that a PPMImage will have to be converted to a {@link BufferedImage} before writing.
   *
   * @param img        the {@link BufferedImage} representing the image. This should be a
   *                   {@code TYPE_INT_ARBG} {@link BufferedImage}.
   * @param type       the desired image file type
   * @param outputFile the file to where the image will be saved.
   * @throws IOException if there was an issue saving the file
   */
  public static void write(BufferedImage img, String type, File outputFile) throws IOException {
    switch (type) {
      case "ppm":
        savePPM(outputFile, toPPMText(img));
        break;
      case "png":
        try {
          ImageIO.write(img, type, outputFile);
        } catch (FileNotFoundException e) {
          throw new IOException("can't find path!");
        }
        break;
      case "jpg":
      case "bmp":
        // basically there is a bug where ImageIO.write() doesn't work properly
        // if it is using ARGB color values (32-bit) to save to an image format without transparency.
        // We surmount this by creating a new BI with RGB color values (24-bit) for the relevant types
        BufferedImage noAlpha = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = noAlpha.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        try {
          ImageIO.write(noAlpha, type, outputFile);
        } catch (FileNotFoundException e) {
          throw new IOException("can't find path!");
        }


        break;
    }
  }

  private static String toPPMText(BufferedImage img) {
    List<String> result = new ArrayList<>();
    result.add("P3");

//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
//    LocalDateTime now = LocalDateTime.now();

    result.add("# This image was created by the the Jacob Kline and Emery Jacobowitz's Image " +
            "Editor on: "/* + dtf.format(now)*/);

    result.add(img.getWidth() + " " + img.getHeight());

    // The maxValue will go here

    int highestValue = -1;
    for (int r = 0; r < img.getHeight(); r++) {
      for (int c = 0; c < img.getWidth(); c++) {
        Color color = new Color(img.getRGB(c, r));

        int pixRed = color.getRed();
        int pixGreen = color.getGreen();
        int pixBlue = color.getBlue();

        highestValue = Math.max(highestValue, (Math.max(Math.max(pixRed, pixGreen), pixBlue)));

        result.add(pixRed + "");
        result.add(pixGreen + "");
        result.add(pixBlue + "");
      }
    }

    result.add(3, highestValue + "");

    return String.join("\n", result) + "\n";
  }

  private static void savePPM(File file, String ppmText) throws IOException {
    try {
      if (!file.createNewFile()) {
        throw new IOException("Could not create file from path: " + file.getPath() +
                ". There was already a file at this location. " +
                "To overwrite, add \"true\" to command.");
      }
    } catch (IOException e) {
      throw new IOException("Bad path: " + file.getPath());
    } catch (SecurityException e) {
      throw new IOException(
              "Cannot create file at given path: " + file.getPath() + "\n" + e.getMessage());
    }

    BufferedWriter ppmWriter;
    try {
      ppmWriter = new BufferedWriter(new FileWriter(file.getPath()));
      ppmWriter.write(ppmText);
      ppmWriter.close();
    } catch (IOException e) {
      throw new IOException(
              "Cannot write to a file at the given path: " + file.getPath());
    }
  }
}

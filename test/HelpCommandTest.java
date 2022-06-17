import org.junit.Test;

import commands.ImageEditorCommand;
import commands.HelpCommand;

import static org.junit.Assert.assertEquals;

public class HelpCommandTest {

  private static final String ITALICS_ON = "\033[3m";
  private static final String ORANGE = "\033[91m";
  private static final String GRAY = "\033[90m";
  private static final String RESET = "\033[0m";
  private static final String LINE_SEPARATOR = System.lineSeparator();

  @Test
  public void apply() {
    ImageEditorCommand helpCommand = new HelpCommand();

    assertEquals("The following file types are supported:" + LINE_SEPARATOR +
            "\t.ppm, .jpg, .png, and .bmp." + LINE_SEPARATOR + LINE_SEPARATOR +

            "The acceptable commands are as follows:" + LINE_SEPARATOR + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Help" + RESET + "\" command displays this message. It " +
            "doesn't take any arguments." + LINE_SEPARATOR + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Load" + RESET + "\" command loads an image at the given path" +
            " into this image editor with a custom name. To use this command, use the following " +
            "syntax:" + LINE_SEPARATOR +
            "\t" + ORANGE + "load " + GRAY + "<PATH_TO_IMAGE> <NAME_TO_USE_IN_IMG_PROCESSOR>" +
            RESET + LINE_SEPARATOR + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Save" + RESET + "\" command saves the image with the given " +
            "custom name to the given path. Be sure to add whether you want to attempt to " +
            "overwrite a file that might already be at the given path! To use this command, " +
            "use the following syntax:" + LINE_SEPARATOR +
            "\t" + ORANGE + "save " + GRAY + "<NEW_PATH_TO_IMAGE> <NAME_IN_IMG_PROCESSOR> " +
            "<SHOULD_OVERWRITE (\"" + ORANGE + "yes" + GRAY + "\" or \"" + ORANGE + "no" + GRAY +
            "\")>" + RESET + LINE_SEPARATOR + LINE_SEPARATOR +

            ITALICS_ON + ORANGE + "Note: For all of the following commands, If you want to " +
            "overwrite the original image in the editor, provide the same name for the original " +
            "image and the newly-modified image." + RESET + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Blur" + RESET + "\" command blurs the image with the given " +
            "custom name and adds it to the editor with the given custom name. To use this " +
            "command, use the following syntax:" + LINE_SEPARATOR + "\t" + ORANGE + "blur " + GRAY +
            "<ORIGINAL_IMAGE_NAME> <BLURRED_IMAGE_NAME> " + RESET + LINE_SEPARATOR +
            LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Flip" + RESET + "\" command either vertically or " +
            "horizontally flips the image with the given name based on the requested " +
            "type of flip and adds it to the editor with the other given name. To use this " +
            "command, use the following syntax:" + LINE_SEPARATOR + "\t" + ORANGE + "flip " +
            GRAY + "<TYPE_OF_FLIP (\"" + ORANGE + "vertical" + GRAY + "\" or \"" + ORANGE +
            "horizontal" + GRAY + "\")> <ORIGINAL_IMAGE_NAME> <FLIPPED_IMAGE_NAME> " + RESET +
            LINE_SEPARATOR + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Darken" + RESET + "\" and \"" + ITALICS_ON + "Brighten" +
            RESET + "\" commands are very similar. They brighten and darken (depending on the " +
            "requested operation) the image with the given name and adds it to the editor with " +
            "the other given name.  To use this command, use the following syntax:" +
            LINE_SEPARATOR + "\t{" + ORANGE + "darken" + RESET + "/" + ORANGE + "brighten" +
            RESET + "}" + GRAY + " <AMT_TO_ADJUST (\"" + ORANGE + "0-255" + GRAY +
            "\")> <ORIGINAL_IMAGE_NAME> <NEWLY_ADJUSTED_IMAGE> " + RESET + LINE_SEPARATOR +
            LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Grayscale" + RESET + " command has many options. The " +
            "following are supported grayscale modes:" + LINE_SEPARATOR +
            "\t" + ORANGE + "red" + RESET + " - sets every pixel's green and blue components to " +
            "whatever" +
            " the red component is." + LINE_SEPARATOR +
            "\t" + ORANGE + "green" + RESET + " - sets every pixel's red and blue components to " +
            "whatever the green component is." + LINE_SEPARATOR +
            "\t" + ORANGE + "blue" + RESET + " - sets every pixel's red and green components to " +
            "whatever the blue component is." + LINE_SEPARATOR +
            "\t" + ORANGE + "value" + RESET + " - sets every pixel's red, green, and blue " +
            "components to whatever the highest component is." + LINE_SEPARATOR +
            "\t" + ORANGE + "intensity" + RESET + " - sets every pixel's red, green, and blue " +
            "components to whatever the average of the three is." + LINE_SEPARATOR +
            "\t" + ORANGE + "luma" + RESET + " - multiplies the red component by 0.2126, the " +
            "green component by 0.7152, and the blue component by 0.0722." + LINE_SEPARATOR +
            LINE_SEPARATOR +

            "This command performs the requested grayscale on the image with the given name and " +
            "adds it to the editor with the other given name. To use this command, use the " +
            "following syntax:" + LINE_SEPARATOR + "\t" + ORANGE + "grayscale " + GRAY +
            "<GRAYSCALE_MODE> <ORIGINAL_IMAGE_NAME> <GRAYSCALED_IMAGE_NAME> " + RESET +
            LINE_SEPARATOR + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Generic-Grascale" + RESET + "\" command performs a luma " +
            "grayscale in a single command on the image with the given custom name and adds it " +
            "to the editor with the given custom name. To use this command, use the following " +
            "syntax:" + LINE_SEPARATOR + "\t" + ORANGE + "generic-grayscale " + GRAY +
            "<ORIGINAL_IMAGE_NAME> <GRAYED_IMAGE_NAME> " + RESET + LINE_SEPARATOR + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Sepia" + RESET + "\" command performs a Sepia " +
            "filter on the image with the given custom name and adds it to the editor with the " +
            "given custom name. To use this command, use the following syntax:" + LINE_SEPARATOR +
            "\t" + ORANGE + "sepia " + GRAY + "<ORIGINAL_IMAGE_NAME> <FILTERED_IMAGE_NAME> " +
            RESET + LINE_SEPARATOR + LINE_SEPARATOR +

            "The \"" + ITALICS_ON + "Sharpen" + RESET + "\" command performs a sharpening " +
            "filter on the image with the given custom name and adds it to the editor with the " +
            "given custom name. To use this command, use the following syntax:" + LINE_SEPARATOR +
            "\t" + ORANGE + "sharpen " + GRAY + "<ORIGINAL_IMAGE_NAME> <FILTERED_IMAGE_NAME> " +
            RESET + LINE_SEPARATOR + LINE_SEPARATOR +

            "Enjoy!!", helpCommand.apply(null));
  }
}
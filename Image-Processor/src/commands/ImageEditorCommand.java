package commands;

/**
 * Commands represent a string of edits to an image that result in an overarching complete edit.
 * For example, in order to greyscale an image according to the red-level, ALL the pixels in
 * the image must be updated. This can be accomplished by allowing a single command to handle the
 * entire operation. This is both to make the code easier to read, and more user-friendly.
 */
public interface ImageEditorCommand {
}

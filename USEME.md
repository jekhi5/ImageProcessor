# ImageEditor

## A lightweight program for modifying images.

---
ImageEditor is a tool for editing images,
including functionality such as flipping, gray-scaling, brightening, and darkening.

### Setup

Launch with no command line arguments to use the GUI.
Launch with `-text` to use the interactive scripting mode.
Launch with `-file <path>` to run the specified script.

### Usage

#### Console

Once the program launches, it will display a welcome message. Simply type in and run commands, with
all arguments separated by whitespace.
If a command requires more arguments than you have provided, nothing will happen until you provide
them (even after pressing enter).

#### Scripting

Commands can be loaded from a text file, exactly as they would be typed, provided each command is
separated by whitespace.
It is not strictly necessary to end your script with a `quit` command. This is nonetheless
recommended for clarity.

#### GUI

The GUI is very simple to use.

- To begin, load an image into the editor. This is done under `File -> Load`.
- You may load multiple images this way, but you can only do operations on one image at a time.
    - To switch the current image, select it from the sidebar.
- Most operations that can be done using a console command can be accomplished using the menu bar at
  the top of the window.
    - In order, these are:
    - `File -> Save`: Saves an image to a given file.
    - `Visualize`: Corresponds to `grayscale`
        - You may select the component you wish to grayscale the image to.
    - `Filter`: Operations using a filter
        - You may select either `blur` or `sharpen`.
    - `Transform`: Operations using color transformations
        - You may select either `grayscale` (applies a luma-based grayscale), or `sepia`.
    - `Flip`: Changes image orientation
        - You may select either `vertical` or `horizontal`.
    - `Adjust`: Miscellaneous image adjustments
        - You may select `brighten` or `darken`. Each of these will prompt you for a numerical
          amount.
        - You mau also select `downsize`. This will prompt you for the new dimensions of the image.
    - `Help -> Show Commands`: Displays a list of all commands
    - `Mask Operation`: Allows any operation to be done using an image mask. When using this
      command, you will be prompted to load the mask from the file system.
- The main image viewport is scrollable, as is the sidebar.
- A histogram displaying the red, green, blue, and intensity levels of each image will pop up in its
  own window on every change.
- To exit the editor, select the 'X' at the top of the window.

### Command List

| Command             | Syntax                                                                 | Description                                                                                                                                                                                                                                         |
|:--------------------|:-----------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `load `             | `load <path> <image-name>`                                             | Loads an image from the path into the editor, under the given name                                                                                                                                                                                  |
| `save`              | `save <path> <image-name> {yes ǀ no}`                                  | Saves an image currently open in the editor to the given path. Overwrites any existing file if the final argument is `yes`.                                                                                                                         |
| `brighten`          | `brighten <amount> <image-name> <new-name>`                            | Increases the brightness of the image by the given amount in the range [0-255]                                                                                                                                                                      |
| `darken`            | `darken <amount> <image-name> <new-name>`                              | Decreases the brightness of the image by the given amount in the range [0-255]                                                                                                                                                                      |
| `flip`              | `flip {vertical ǀ horizontal} <image-name> <new-name>`                 | Mirrors the image in the given dimension                                                                                                                                                                                                            |
| `grayscale`         | `grayscale <mode> <image-name> <new-name>`                             | Grayscale the image based on the given mode. See [Grayscale](#Grayscale) for more information                                                                                                                                                       |
| `same`              | `same <image-name> <image-name>`                                       | Tells you if the two images are the same or different.                                                                                                                                                                                              |
| `blur`              | `blur <image-name> <new-name>`                                         | Applies a Gaussian blur                                                                                                                                                                                                                             |
| `sharpen`           | `sharpen <image-name> <new-name>`                                      | Sharpens the image                                                                                                                                                                                                                                  |
| `downsize`          | `downsize <new-width> <new-height> <image-name> <new-name>`            | Resizes the image to the given new width and height. The new width and new height must be less than or equal to their current counterparts                                                                                                          |
| `generic-grayscale` | `generic-grayscale <image-name> <new-name>`                            | Applies a luma grayscale to the image using a color transformation.                                                                                                                                                                                 |
| `sepia`             | `sepia <image-name> <new-name>`                                        | Applies a sepia filter to the image.                                                                                                                                                                                                                |
| `mask-command`      | `mask-command <path-to-mask> <...command with all associated args...>` | Applies the given command to the pixels in the given image whose matching coordinates in the mask are fully black pixels. Because of this mapping, the mask must be the exact same dimensions as the image it will be applied to                    |
| `convert`           | `convert <image-path> <new-path>`                                      | Converts an image from one type to another. DOES NOT add images to the editor.                                                                                                                                                                      |
| `help`              | `help` or `h`                                                          | Displays information about all available commands.                                                                                                                                                                                                  |
| `quit`              | `quit` or `q` or `exit`                                                | Exits the ImageEditor. All unsaved data will be lost.                                                                                                                                                                                               |

No commands *modify* the existing image in the editor. Instead, they open a new image in the editor
with the `<new-name>`,
which reflects the changes made to the original image. If you wish to overwrite an image in the
editor, simply provide the same value for `<image-name>` and `<new-name>`.
<br>
<br>
All commands are case-insensitive, except for image names and file paths.

#### Grayscale

The `grayscale` command places a grayscale filter over an image, which means that the three color
components
(red, green, blue) are all set to the same amount, based on their existing values in each pixel.
There are six modes:

| Mode        | Grayscale to:                                   |
|:------------|:------------------------------------------------|
| `red`       | the red component                               |
| `green`     | the green component                             |
| `blue`      | the blue component                              |
| `value`     | the maximum component                           |
| `intensity` | the average of red, green, and blue             |
| `luma`      | 0.2126*r* + 0.7152*b* + 0.0722*b*, rounded down |

---

## Design Overview

Our design follows the Model-View-Controller archetype.

#### Model

- The model consists of several parts. The most important is an `ImageEditorModel`, an interface
  that
  provides three methods: storing images, retrieving images, and executing commands on images.
  This interface is implemented by `BasicImageEditorModel.` Think of this as a box that all the
  images
  are stored in, so that they can be taken out and manipulated.

- We have standardized implementations of `Image`s and `Pixel`s, which provide the bare minimum
  functionality.
  An `Image` knows how to get and set pixels at given positions, and get its dimensions. A `Pixel`
  can
  give
  information about its 4-channel colors (RGBA). Pixels can be built using the `PixelBuilder` class,
  which provides an easy way to construct them.

- `Image` is implemented by `PPMImage`--which specifically represents a P3 PPM image--
  and `BetterImage`, which can represent any kind of image.

- The reason that `Image` and `Pixel` exist as public interfaces is so that they can be influenced
  by
  commands. The `ImageEditorCommand` interface merely allows commands to be executed by
  an `ImageEditorModel`,
  which then provides any `Image`s the command queries. Commands should all extend `AbstractCommand`
  ,
  which
  provides a constructor that will set the command up with any number of arguments. Having commands
  separately from the rest of the model (i.e. we don't have a "grayscaleImage" method
  in `ImageEditorModel`)
  allows them to be discrete, and to form a bridge between the model and controller. This way, the
  controller
  can simply pass the relevant command to the model. Some commands also interact with the file
  system,
  like `SaveImage` and `LoadImage`.

- The `ImageEditorCommand` interface also facilitates the transfer of data from the model to the
  controller,
  as it returns a status method for each command, which is generated using details normally
  invisible
  to the controller.
  This way, the controller can tell if anything else needs to be done.

- The `PixelOperator` interface represents either a Kernel or Color Transformation operation.
  While these have different functions, they have the same general form (i.e. applying a matrix to a
  pixel on an image,
  and subsequently returning a new pixel).

#### View

The view is made up of two interfaces. One being the `ImageEditorView` and the other being the
`ImageEditorGUIView`. Together they allow the user to use the program either through a text
based interactive scripting method or through the use of a graphical user interface.

The text view is fairly simple. It operates through the console. It prompts the user for a
command, which they must type out in full. After entering the command, the console will attempt
to parse and execute the given command and will print a message detailing to outcome of the
parsing (ranging from informing the user that it was unable to read the command, all the way to
"operation successful!")

The graphical user interface is much more intricate under the hood, however, it allows for the
user to interact with the program more easily. Through the use of menu buttons, scrollable
components, and easily understandable interactive components. With the GUI, the user can perform
most of the same commands as the text based editor with a simple click of a button. The view
port is where each image is displayed, and as the user adds more images to the editor, they can
switch between images by selecting each image's corresponding button. Every operation will be
performed on the image that is currently "focused" (IE: the image that is currently being
displayed in the view port).

#### Controller

The `ImageEditorTextController` is the controller of the first phase of our design, as it is
exclusively for a text-based program. Its implementation allows the user to give text input either
as a text file or through the console, and sends the appropriate commands to model. It uses the view
to display information to the user.

The `ImageEditorSwingController` is the controller of the second phase of our design, as it is
exclusively for a GUI-based program. Its implementation allows the user to interact with the
program using their mouse to click the operation that they want to perform, rather than needing
to type in each one. Each button sends the information to the controller, which uses said
information to run a command. The information sent to the controller contains all the necessary
parts of the requested command, which ensures that there are no parsing errors along the way.

There is also the `ImageUtil` class, which contains static methods that help read and store files,
and the `Runner` class which runs the program, including telling the controller to take either
console or text file input.

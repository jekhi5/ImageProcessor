# ImageEditor

CHANGES:

- PPMImage -> ImageImpl
- Additions to ImageUtil.CreateImageFromPath for more filetypes

## A lightweight program for modifying images.

---
ImageEditor is a tool for editing images,
including functionality such as flipping, gray-scaling, brightening, and darkening.

### Setup

There is one command line argument used in this program. Launch the program with `manual` to use
input from the console,
or use a file path to read input from a text file. The file path may not contain any spaces.

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

### Command List

| Command     | Syntax                                                 | Description                                                                                                                 |
|:------------|:-------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------|
| `load `     | `load <path> <image-name>`                             | Loads an image from the path into the editor, under the given name                                                          |
| `save`      | `save <path> <image-name> {yes ǀ no}`                  | Saves an image currently open in the editor to the given path. Overwrites any existing file if the final argument is `yes`. |
| `brighten`  | `brighten <amount> <image-name> <new-name>`            | Increases the brightness of the image by the given amount in the range [0-255]                                              |
| `darken`    | `darken <amount> <image-name> <new-name>`              | Decreases the brightness of the image by the given amount in the range [0-255]                                              |
| `flip`      | `flip {vertical ǀ horizontal} <image-name> <new-name>` | Mirrors the image in the given dimension                                                                                    |
| `grayscale` | `grayscale <mode> <image-name> <new-name>`             | Grayscale the image based on the given mode. See [Grayscale](#Grayscale) for more information                               |
| `same`      | `same <image-name> <image-name>`                       | Tells you if the two images are the same or different.                                                                      |
| `quit`      | `quit` or `q` or `exit`                                | Exits the ImageEditor. All unsaved data will be lost.                                                                       |

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

The model consists of several parts. The most important is an `ImageEditorModel`, an interface that
provides three methods: storing images, retrieving images, and executing commands on images.
This interface is implemented by `BasicImageEditorModel.` Think of this as a box that all the images
are stored in, so that they can be taken out and manipulated.

We have standardized implementations of `Image`s and `Pixel`s, which provide the bare minimum
functionality.
An `Image` knows how to get and set pixels at given positions, and get its dimensions. A `Pixel` can
give
information about its 4-channel colors (RGBA). Pixels can be built using the `PixelBuilder` class,
which provides an easy way to construct them.

For now, `Image` is only implemented by `PPMImage`, which specifically represents a P3 PPM image.
More implementations may be added in the future. `PPMmage`s specifically are iterable, and produce
a `PixelIterator`,
though we contend that not every kind of `Image` will need to be iterable, so we didn't put that in
the interface.
`PixelIterator` is as of now unused, though it may be helpful in the future.

The reason that `Image` and `Pixel` exist as public interfaces is so that they can be influenced by
commands. The `ImageEditorCommand` interface merely allows commands to be executed by
an `ImageEditorModel`,
which then provides any `Image`s the command queries. Commands should all extend `AbstractCommand`,
which
provides a constructor that will set the command up with any number of arguments. Having commands
separately from the rest of the model (i.e. we don't have a "grayscaleImage" method
in `ImageEditorModel`)
allows them to be discrete, and to form a bridge between the model and controller. This way, the
controller
can simply pass the relevant command to the model. Some commands also interact with the file system,
like `SaveImage` and `LoadImage`.

The `ImageEditorCommand` interface also facilitates the transfer of data from the model to the
controller,
as it returns a status method for each command, which is generated using details normally invisible
to the controller.
This way, the controller can tell if anything else needs to be done.

#### View

The view is the simplest component. It is one interface, `ImageEditorView`. All the implementation
of
this interface does is have some way to render a message to the user. For now, that is through text.

#### Controller

The `ImageEditorTextController` is the controller of this phase of our design, as it is exclusively
for
a text-based program. Its implementation allows the user to give text input either as a text file or
through the console, and sends the appropriate commands to model. It uses the view to display
information to
the user.

There is also the `ImageUtil` class, which contains static methods that help read and store files,
and the `TextRunner` class which runs the program, including telling the controller to take either
console or text file input.

<br>
<br>

*ImageEditor belongs to Jacob Kline and Emery Jacobowitz and may not be used without permission.*

<br>
<br>
The Ben Lerner example image is copyright of Northeastern University, 2022,
and Professor Lerner probably retains rights to his likeness as well. 
Our use falls under fair use, as we are not publishing this project publicly.
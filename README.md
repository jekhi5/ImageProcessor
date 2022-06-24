## Design Changes and Justifications

### Assignment 6:
- The `ImageEditorCommand` interface had an additional method added to allow a command to be run with a given mask.
  - All implementing classes have also been updated to reflect this change.
- New `ImageEditorCommands` had to be created for masking and downsizing, and were added to the controller.

### Assignment 5:
- The `Image` interface now has a method that saves to a file, instead of generating savable text,
    - We realized that not every image type could be saved as ASCII text.
- Deleted `PixelIterator`
    - We didn't ever find a use for it, and we realized that having pixel information devoid of
      location
      information is kind of useless.
- Added `ImageSaver` and `ImageFactory` classes
    - It became apparent that creating type-agnostic image objects, and saving them to specific
      filetypes
      was a somewhat complicated task, and we felt that it would be better to encapsulate it within
      a
      helper class.
    - Furthermore, this allowed us to limit our reliance on `ImageUtil`,
      which now only has the ability to read PPM files.

## Completeness

The whole program is complete. It may still be extended in the future,
but it currently works in its entirety.

---
<br>
<br>

*ImageEditor belongs to Jacob Kline and Emery Jacobowitz and may not be used without permission.*

<br>
<br>
The Ben Lerner example image is copyright of Northeastern University, 2022,
and Professor Lerner probably retains rights to his likeness as well. 
Our use falls under fair use, as we are not publishing this project publicly.
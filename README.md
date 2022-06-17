## Design Changes and Justifications

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
# img-crawler

## Motivation
First of all, this is not a generic crawler tool, I built it to crawl the images from this [website](https://mp.weixin.qq.com/s/YpMnJ5_625zOq_huX8e83g), it's a Chinese history carton with a few embedded images in the HTML.


## Installation

Download from https://github.com/xavierchow/img-crawler/releases.

## Usage

Feed with URLs like https://mp.weixin.qq.com/s/YpMnJ5_625zOq_huX8e83g

    $ java -jar img-crawler-0.1.0-standalone.jar [url]

The images will be downloaded in the current directory with the naming of `photos-{num}.jpeg`

## Process the images

This is not part of this tool(jar), but it's the funny part. The reason why I download those images is that I want to print them out.
However they are in the same width but not in the same height. My plan is to print them out with A4 size, so I need to stitch them and crop it with same height.

### ImageMagic
I use [montage](http://www.imagemagick.org/Usage/montage/) and [convert](http://www.imagemagick.org/Usage/files/#read_frames) from [ImageMagic](https://imagemagick.org/index.php) package to do the stitch and crop.

### Stitch
To run the following command I need the total number of images, it would be easy to collect from the output of the java command above, the `out.jpeg` would be a long vertical image.
```
$ montage photos-%d.jpeg[1-16] -tile 1x16 -geometry +0+0 out.jpeg
```

### Crop

Crop it with height of `1180`.

```
$ convert out.jpeg -crop x1180 +repage tile%02d.jpeg
```

## License

Copyright © 2020 XavierZhou

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

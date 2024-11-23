This topic discusses k-dimensional trees, or kd trees. This data structure makes it possible to efficiently find, among a set of points of ℝk, the closest neighbor of a given point.

Kd trees are similar to binary search trees, except that the order used to distribute the descendants of a node n between the left and right subtrees depends on the depth of n in the tree: 
we sort the nodes alternately following each of their k coordinates.

## Application 🚀:
- For common applications, the colors of images seen on a screen are encoded with 24 bits, 8 bits for each of the three colors red, green and blue, resulting in a point.
- To compress an image, the GIF image format selects 256 colors, from 16777216 possible, to best represent the image. The method of setting 256 colors independently of the image, like the web palette, gives very poor results. You must select a color palette suited to the image.

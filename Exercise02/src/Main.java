import geometry.Rectangle;

import static geometry.Rectangle.isIntersection;

/*
    2) Test if two rectangles intersect

    Considering two rectangles in a discrete grid (like pixels in a display), each defined by two points, return
    true if they intersect, false otherwise.
    Note: the points are included in the rectangle and have a dimension of 1 unit; the rectangle (0, 0; 1, 1) has an
    area of 4 units.

    Example:
        |..........+---+
        |..........|.C.|
        |..+-------#---+
      10|..|.......|....
        |..|...A...|....
        |..|.......|....
        |..|...#####-+..
        |..|...#####.|..
      5 |..+---#####.|..
        |......|..B..|..
        |......|.....|..
        |......+-----+..
      1 |...............
      0 +----|----|----|
        0    5    10   15

        A = (3, 5; 11, 11)
        B = (7, 2; 13, 7)
        C = (11, 11; 15, 13)

        intersects(A, B) => true
        intersects(A, C) => true
        intersects(B, C) => false
 */

public static void main(String[] ignoredArgs) {
    // Definindo os retângulos A, B e C
    Rectangle A = new Rectangle(3, 5, 11, 11);
    Rectangle B = new Rectangle(7, 2, 13, 7);
    Rectangle C = new Rectangle(11, 11, 15, 13);

    // Testando as interseções
    System.out.println("intersects(A, B): " + isIntersection(A, B)); // true
    System.out.println("intersects(A, C): " + isIntersection(A, C)); // true
    System.out.println("intersects(B, C): " + isIntersection(B, C)); // false
}
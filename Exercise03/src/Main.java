import geometry.Retangulo;

import static geometry.Retangulo.areaOfIntersection;

/*
3) Compute the area of intersection between two rectangles

    Considering two rectangles in a discrete grid (like pixels in a display), each defined by two points, compute
    the area of intersection between the two.

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
    0    5   10    15

    A = (3, 5; 11, 11)
    B = (7, 2; 13, 7)
    C = (11, 11; 15, 13)

    areaOfIntersection(A, B) = 15
    areaOfIntersection(A, C) = 1
 */

public static void main(String[] ignoredArgs) {

    // Exemplo de uso
    Retangulo A = new Retangulo(3, 5, 11, 11);
    Retangulo B = new Retangulo(7, 2, 13, 7);
    Retangulo C = new Retangulo(11, 11, 15, 13);

    System.out.println("Área de interseção entre A e B: " + areaOfIntersection(A, B)); // 15
    System.out.println("Área de interseção entre A e C: " + areaOfIntersection(A, C)); // 1
}
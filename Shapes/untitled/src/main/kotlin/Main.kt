fun test_rect() {
    var x = Rect(4, 3, 4, 2)

    print("Rect test:\n")
    print("Rect position 0: ${x}\n")
    x.rotate(RotateDirection.Clockwise, 3, -3)
    print("Rect position 1: ${x}\n")
    x.rotate(RotateDirection.Clockwise, 3, -3)
    x.rotate(RotateDirection.Clockwise, 3, -3)
    print("Rect position 2: ${x}\n")
}

fun test_square() {
    var x = Square(4, 3, 2)

    print("Square test:\n")
    print("Square position 0: ${x}\n")
    x.rotate(RotateDirection.Clockwise, 3, -3)
    print("Square position 1: ${x}\n")
    x.rotate(RotateDirection.Clockwise, 3, -3)
    x.rotate(RotateDirection.Clockwise, 3, -3)
    print("Square position 2: ${x}\n")
}

fun test_circle() {
    var x = Circle(4, 3, 2)

    print("Circle test:\n")
    print("Circle position 0: ${x}\n")
    x.rotate(RotateDirection.Clockwise, 3, -3)
    print("Circle position 1: ${x}\n")
    x.rotate(RotateDirection.Clockwise, 3, -3)
    x.rotate(RotateDirection.Clockwise, 3, -3)
    print("Circle position 2: ${x}\n")
}

fun main(args: Array<String>) {
    test_rect()
    test_square()
    test_circle()
}
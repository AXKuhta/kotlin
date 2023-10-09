class Square(var x_: Int, var y_: Int, var a: Int) : Rect(x_, y_, a, a) {
    // Let square be a rect

    override fun toString() = "Square(${x}, ${y}, ${a})"
}

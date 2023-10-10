import kotlin.math.PI

class Circle(var x: Int, var y: Int, var r:Int) : Movable, Figure(0), Transforming {
    constructor(circle: Circle) : this(circle.x, circle.y, circle.r)

    override fun move(dx: Int, dy: Int) {
        x += dx; y += dy
    }

    override fun area(): Float {
        return (2*PI*r).toFloat()
    }

    // Resizing by int is strange
    // The rect will only ever grow
    override fun resize(zoom: Int) {
        r *= zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val new_x = x - centerX
        val new_y = y - centerY

        val sin_a = when (direction) {
            RotateDirection.Clockwise -> 1
            RotateDirection.CounterClockwise -> -1
        }

        val s:Int = new_y*sin_a
        val t:Int = -new_x*sin_a

        x = s + centerX
        y = t + centerY
    }

    override fun toString() = "Circle(${x}, ${y}, ${r})"
}

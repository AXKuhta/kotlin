
// Fixed: width/height should be vars in order for resize() to work
open class Rect(var x: Int, var y: Int, var width: Int, var height: Int) : Movable, Figure(0), Transforming {
    constructor(rect: Rect) : this(rect.x, rect.y, rect.width, rect.height)

    override fun move(dx: Int, dy: Int) {
        x += dx; y += dy
    }

    override fun area(): Float {
        return (width * height).toFloat()
    }

    // Resizing by int is strange
    // The rect will only ever grow
    override fun resize(zoom: Int) {
        width *= zoom
        height *= zoom

        move(-width/2, -height/2)
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        // Move to new reference frame
        val new_x = x - centerX
        val new_y = y - centerY

        // This was a proper matrix transform previously
        // Simplified for 90 / -90 only
        val sin_a = when (direction) {
            RotateDirection.Clockwise -> 1
            RotateDirection.CounterClockwise -> -1
        }

        val s:Int = new_y*sin_a
        val t:Int = -new_x*sin_a

        // Move back
        x = s + centerX
        y = t + centerY

        // Swap width/height always
        val tmp = width
        width = height
        height = tmp
    }

    override fun toString() = "Rect(${x}, ${y}, ${width}, ${height})"
}

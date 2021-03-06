@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.sin
import java.lang.Math.cos

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
        if (center.distance(other.center) <= radius + other.radius) 0.0
        else center.distance(other.center) - radius - other.radius

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) - radius < 1e-10

    operator fun contains(points: Array<out Point>): Boolean {
        for (point in points) if (!this.contains(point)) return false
        return true
    }
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException()
    var max = 0.0
    var begin = Point(0.0, 0.0)
    var end = Point(0.0, 0.0)
    for (i in 0..points.size - 2) for (j in i + 1 until points.size) {
        val distance = points[i].distance(points[j])
        if (distance > max) {
            max = distance
            begin = points[i]
            end = points[j]
        }
    }
        return Segment(begin, end)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle =
        Circle(Point(((diameter.begin.x + diameter.end.x) / 2), (diameter.begin.y + diameter.end.y) / 2),
                diameter.begin.distance(diameter.end) / 2)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val denominator = sin(angle - other.angle)
        return Point((other.b * cos(angle) - b * cos(other.angle)) / denominator,
                     (other.b * sin(angle) - b * sin(other.angle)) / denominator)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = when {
    s.end.x == s.begin.x -> Line(s.begin, Math.PI / 2)
    s.end.y == s.begin.y -> Line(s.begin, 0.0)
    (s.end.y - s.begin.y > 0) xor (s.end.x - s.begin.x > 0) ->
        Line(s.begin,Math.PI - Math.atan(Math.abs(s.end.y - s.begin.y) / Math.abs(s.end.x - s.begin.x)))
    else -> Line(s.begin, Math.atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x)))
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = when {
    b.x == a.x -> Line(a, Math.PI / 2)
    b.y == a.y -> Line(a, 0.0)
    (b.y - a.y > 0) xor (b.x - a.x > 0) -> Line(a,Math.PI - Math.atan(Math.abs(b.y - a.y) / Math.abs(b.x - a.x)))
    else -> Line(a, Math.atan((b.y - a.y) / (b.x - a.x)))
}
/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line =
        Line(Point((a.x + b.x) / 2, (a.y + b.y) / 2), (Math.PI / 2 + lineByPoints(a, b).angle) % Math.PI)

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var first = Circle(Point(0.0, 0.0), 0.0)
    var second = Circle(Point(0.0, 0.0), 0.0)
    var min = Double.MAX_VALUE
    for (i in 0..circles.size - 2) for (j in i + 1 until circles.size) when (circles[i].distance(circles[j])) {
        0.0 -> return Pair(circles[i], circles[j])
        in 0.0..min -> {
            min = circles[i].distance(circles[j])
            first = circles[i]
            second = circles[j]
        }
    }
    if (min == Double.MAX_VALUE) throw IllegalArgumentException()
    else return Pair(first, second)
}
/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val ab = bisectorByPoints(a, b)
    val bc = bisectorByPoints(b, c)
    val center = ab.crossPoint(bc)
    val radius = center.distance(a)
    return Circle(center, radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = when {
    points.isEmpty() -> throw IllegalArgumentException()
    points.size == 1 -> Circle(points[0], 0.0)
    else -> {
        var circle = circleByDiameter(diameter(*points))
        if (points in circle) circle
        else {
            var min = Double.MAX_VALUE
            for (i in 0..points.size - 3) for (j in i + 1..points.size - 2) {
                val bisector = bisectorByPoints(points[i], points[j])
                for (k in j + 1 until points.size) {
                    val center = bisector.crossPoint(bisectorByPoints(points[j], points[k]))
                    val c = Circle(center, center.distance(points[k]))
                    if (points in c && c.radius < min) {
                        min = c.radius
                        circle = c
                    }
                }
            }
            circle
        }
    }
}
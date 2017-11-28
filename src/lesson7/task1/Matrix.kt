@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
        if (height < 1 || width < 1) throw IllegalArgumentException()
        else MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val map = mutableMapOf<Cell, E>()

    init {
        for (i in 0 until height) for (j in 0 until width)
        map[Cell(i, j)] = e
    }

    override fun get(row: Int, column: Int): E  = get(Cell(row, column))

    override fun get(cell: Cell): E  = map[cell] ?: throw IllegalArgumentException()

    override fun set(row: Int, column: Int, value: E) {
        if (row !in 0 until height || column !in 0 until width) throw IllegalArgumentException()
        else map[Cell(row, column)] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?) =
            if (other is Matrix<*> && height == other.height && width == other.width) {
                var log = true
                for (i in 0 until height) for (j in 0 until width) if (other[i, j] != this[i, j]) {log = false; break}
                log
            } else false


    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + map.hashCode()
        return result
    }

    override fun toString(): String {
        val res = mutableListOf<String>()
        res.add("[ ")
        for (row in 0 until height) {
            res.add("[")
            for (column in 0 until width) {
                res.add(this[row, column].toString())
                res.add(",")
            }
            res.removeAt(res.size - 1)
            res.add("] ")
        }
        res.add("]")
        return res.joinToString("")
    }
}
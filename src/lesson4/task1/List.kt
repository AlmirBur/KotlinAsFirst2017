@file:Suppress("UNUSED_PARAMETER")
package lesson4.task1

import java.lang.Math.*
import lesson1.task1.discriminant
import lesson3.task1.*

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.map { it * it }.sum())

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    return if (list.size == 0) 0.0
    else list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val mean = mean(list)
    if (mean == 0.0) return list
    else for (i in 0 until list.size) list[i] -= mean
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var c = 0.0
    for (i in 0 until a.size) c += a[i] * b[i]
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var result = 0.0
    var newX = 1.0
    for (i in 0 until p.size) {
        result += newX * p[i]
        newX *= x
    }
    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    for (i in 1 until list.size) list[i] += list[i - 1]
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val list = mutableListOf<Int>()
    var newN = n
    while (newN != 1) {
        list.add(minDivisor(newN))
        newN /= minDivisor(newN)
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString("*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var newN = n
    do {
        list.add(0, newN % base)
        newN /= base
    } while (newN != 0)
    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val digitName = listOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
    val newN = convert(n, base).map { digitName[it] }
    return newN.joinToString("")
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var degree = 1
    var result = 0
    for (i in digits.size - 1 downTo 0) {
        result += digits[i] * degree
        degree *= base
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val list = mutableListOf<Int>()
    val digitName = listOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
    for (i in 0..str.length - 1) {
        for (j in 0..36)
            if (str[i] == digitName[j]) {
                list.add(j)
                break
            }
    }
    return decimal(list, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    val name = listOf(listOf("I", "V"), listOf("X", "L"), listOf("C", "D"), listOf("M"))
    var newN = n
    var mod: Int
    var result = ""
    for (i in 0..2) {
        mod = newN % 10
        if (mod == 0) {
            newN /= 10
            continue
        }
        when {
            mod == 4 || mod == 9 -> result = name[i][0] + name[mod / 5 + i][(mod / 5 + 1) % 2] + result
            else -> {
                var temp = ""
                for (j in 2..mod % 5 + mod / 5) temp += name[i][0]
                result = name[i][mod / 5] + temp + result
            }
        }
        newN /= 10
    }
    for (i in 1..n / 1000) result = "M" + result
    return result
}

/**
fun roman (n: Int): String {
    val name = listOf(listOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"),
                      listOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"),
                      listOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"),
                      listOf("", "M"))
    var newN = n
    var mod = n % 10
    var result = ""
    for (i in 0..2) {
        result = name[i][mod] + result
        newN /= 10
        mod = newN % 10
    }
    for (i in 1..n / 1000) result = "M" + result
    return result
}
*/

/**
fun part(n: Int, id: Int): MutableList<String> {
    if (n == 0) return mutableListOf()
    val extra = listOf("", "тысяч", "миллионов", "миллиардов", "триллионов")
    val name = listOf(listOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"),
                      listOf("", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"),
                      listOf("", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"),
                      listOf("", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"),
                      listOf("", "одна тысяча", "две тысячи", "три тысячи", "четыре тысячи"),
                      listOf("", "один миллион", "два миллиона", "три миллиона", "четыре миллиона"),
                      listOf("", "один миллиард", "два миллиарда", "три миллиарда", "четыре миллиарда"),
                      listOf("", "один триллион", "два триллиона", "три триллиона", "четыре триллиона"))
    var result = mutableListOf<String>()
    var newN = n
    var logic = true
    if (n % 100 in 11..19) {
        result.add(name[2][n / 100])
        result.add(name[3][n % 10])
        result.add(extra[id])
        return result
    }
    for (i in 0..2) {
        if (i == 0 && extra[id] != "" && n % 10 in  1..4) {
            result.add(name[3 + id][n % 10])
            logic = false
            newN /= 10
            continue
        }
        result.add(0, name[i][newN % 10])
        newN /= 10
    }
    if (logic) result.add(extra[id])
    return result
}
*/

fun part(n: Int, extra: String): MutableList<String> {
    if (n == 0) return mutableListOf()
    val name = listOf(listOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"),
            listOf("", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"),
            listOf("", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"),
            listOf("", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"),
            listOf("", "одна тысяча", "две тысячи", "три тысячи", "четыре тысячи"),
            listOf("", "одна тысяча", "две тысячи", "три тысячи", "четыре тысячи"))
    var result = mutableListOf<String>()
    var newN = n
    var logic = true
    if (n % 100 in 11..19) {
        result.add(name[2][n / 100])
        result.add(name[3][n % 10])
        result.add(extra)
        return result
    }
    for (i in 0..2) {
        if (i == 0 && extra != "" && n % 10 in  1..4) {
            result.add(name[4][n % 10])
            logic = false
            newN /= 10
            continue
        }
        result.add(0, name[i][newN % 10])
        newN /= 10
    }
    if (logic == true) result.add(extra)
    return result
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

/**
fun russian(n: Int): String {
    if (n == 0) return "ноль"
    var result = mutableListOf<String>()
    var newN = abs(n)
    for (i in 0..(digitNumber(abs(n)) - 1)/ 3) {
        result = (part(newN % 1000, i) + result).toMutableList()
        newN /= 1000
    }
    if (n < 0) result.add(0, "минус")
    return (result).filter { it != "" }.joinToString(" ")
}

*/

fun russian(n: Int): String =
        (part(n / 1000, "тысяч") + part(n % 1000, "")).filter { it != ""}.joinToString(" ")
@file:Suppress("UNUSED_PARAMETER")
package lesson5.task1

import lesson4.task1.part
import java.lang.IllegalArgumentException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря")

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    return try {
        if (parts[0].length !in 1..2 || months.indexOf(parts[1]) + 1 == 0 || parts[2].toInt() < 0) ""
        else twoDigitStr(parts[0].toInt()) + "." +
             twoDigitStr(months.indexOf(parts[1]) + 1) + "." +
             "${parts[2].toInt()}"
    }
    catch (e: NumberFormatException) { "" }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    return try {
        when {
            (parts[1].toInt() !in 1..12) -> ""
            (parts[0].length != 2 || parts[1].length != 2) -> ""
            else -> "${parts[0].toInt()} ${months[parts[1].toInt() - 1]} ${parts[2].toInt()}"
        }
    }
    catch (e: NumberFormatException) { "" }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    if (phone == "+") return ""
    val whiteList = "+1234567890 -()"
    val number = mutableListOf<Char>()
    for (char in phone) {
        when {
            char !in whiteList -> return ""
            whiteList.indexOf(char) in 0..10 -> number.add(char)
        }
    }
    return number.joinToString("")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val whiteList = "1234567890 -%"
    for (char in jumps) if (char !in whiteList) return -1
    val parts = jumps.split(" ").filter { it != "-" && it != "%" && it != "" }
    var max = -1
    return try {
        for (element in parts) if (element.toInt() > max) max = element.toInt()
        max
    }
    catch (e: NumberFormatException) { -1 }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val whiteList = "1234567890 -+%"
    for (char in jumps) if (char !in whiteList) return -1
    val parts = jumps.split(" ").filter { it != "" }
    var max = -1
    if (parts.size % 2 == 1 || parts.isEmpty()) return -1
    return try {
        for (i in 0 until parts.size step 2)
            if (parts[i].toInt() > max && parts[i + 1].last() == '+') max = parts[i].toInt()
        max
    }
    catch (e: NumberFormatException) { -1 }
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val whiteList = "1234567890 -+"
    for (char in expression) if (char !in whiteList) throw IllegalArgumentException()
    val parts = expression.split(" ").filter { it != "" }
    if (parts.isEmpty()) throw IllegalArgumentException()
    try {
        var sum = parts[0].toInt()
        for (i in 1 until parts.size step 2)  when {
                parts[i + 1].toInt() < 0 -> throw IllegalArgumentException()
                parts[i] == "+" -> sum += parts[i + 1].toInt()
                parts[i] == "-" -> sum -= parts[i + 1].toInt()
                else -> throw IllegalArgumentException()
        }
        return sum
    }
    catch (e: NumberFormatException) { throw IllegalArgumentException() }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.toLowerCase().split(" ")
    var index = 0
    for (i in 1 until parts.size) {
        if (parts[i] == parts[i - 1]) return index
        index += parts[i - 1].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String = TODO()

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val whiteList = "><+-[] "
    var posConv = cells / 2
    var posComm = 0
    var bracketsSum = 0
    var log = false
    for (char in commands) when {
        char !in whiteList -> throw IllegalArgumentException()
        char == ']' && !log -> throw IllegalArgumentException()
        char == '[' && !log -> { log = true; bracketsSum++ }
        char == '[' -> bracketsSum++
        char == ']' -> bracketsSum--
    }
    if (bracketsSum != 0) throw IllegalArgumentException()
    val conveyor = mutableListOf<Int>()
    for (i in 0 until cells) conveyor.add(0)
    if (commands.isEmpty()) return conveyor
    try {
        for (i in 1..limit) {
            when (commands[posComm]) {
                '>' -> { posComm++; posConv++ }
                '<' -> { posComm++; posConv-- }
                '+' -> { conveyor[posConv]++; posComm++ }
                '-' -> { conveyor[posConv]--; posComm++ }
                '[' -> if (conveyor[posConv] == 0) {
                           bracketsSum = 1
                           posComm++
                           while (bracketsSum != 0) {
                               if (commands[posComm] == '[') bracketsSum++
                               if (commands[posComm] == ']') bracketsSum--
                               posComm++
                           }
                       }
                       else posComm++
                ']' -> if (conveyor[posConv] != 0) {
                           bracketsSum = -1
                           posComm++
                           while (bracketsSum != 0) {
                               if (commands[posComm - 2] == '[') bracketsSum++
                               if (commands[posComm - 2] == ']') bracketsSum--
                               posComm--
                           }
                       }
                       else posComm++
                ' ' -> posComm++
            }
            if (posComm == commands.length) break
            if (posConv !in 0 until cells) throw IllegalStateException()
        }
        return conveyor
    }
    catch (e: IndexOutOfBoundsException) { throw IllegalStateException() }
}
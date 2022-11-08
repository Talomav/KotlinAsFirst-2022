@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.isPrime
import lesson3.task1.minDivisor
import lesson3.task1.revert
import kotlin.math.pow
import kotlin.math.sqrt

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

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
            val root = sqrt(y)
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
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
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
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

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
    val lowerCase = str.lowercase().filter { it != ' ' }
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
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    if (v.isNotEmpty()) {
        var amount = 0.0
        for (element in v) amount += element.pow(2.0)
        return sqrt(amount)
    }
    return 0.0
}

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    return if (list.isEmpty()) 0.0
    else list.sum() / list.size
}

/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isNotEmpty()) {
        val avg = list.sum() / list.size
        for (i in 0 until list.size) list[i] = list[i] - avg
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    if (a.isNotEmpty() && b.isNotEmpty()) {
        var C = 0
        for (i in a.indices) C += a[i] * b[i]
        return C
    }
    return 0
}

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    if (p.isNotEmpty()) {
        var amount = p[0]
        for (i in 1 until p.size)
            amount += p[i] * (x.toDouble().pow(i)).toInt()
        return amount
    }
    return 0
}

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if (list.isNotEmpty()) {
        var amount = 0
        for (i in 0 until list.size) {
            val number = list[i]
            list[i] += amount
            amount += number
        }
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    if (isPrime(n)) return listOf(n)
    var number = n
    val result = mutableListOf<Int>()
    var divider = 2
    while (number > 1) {
        if (isPrime(divider)) {
            if (number % divider == 0) {
                number /= divider
                result += divider
            }
            if (number % divider != 0) divider += 1
        } else divider += 1
    }
    return result
}

/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    var result = ""
    for (i in 0 until factorize(n).size) {
        result += factorize(n)[i]
        if (i != factorize(n).size - 1) result += "*"
    }
    return result
}

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val result = mutableListOf<Int>()
    var number = n
    while (number > 0) {
        result += number % base
        number /= base
    }
    result.reverse()
    return result
}

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var result = ""
    var number = n
    while (number > 0) {
        if (number % base >= 10) {
            val lastDigit = when (number % base) {
                10 -> "a"
                11 -> "b"
                12 -> "c"
                13 -> "d"
                14 -> "e"
                15 -> "f"
                16 -> "g"
                17 -> "h"
                18 -> "i"
                19 -> "j"
                20 -> "k"
                21 -> "l"
                22 -> "m"
                23 -> "n"
                24 -> "o"
                25 -> "p"
                26 -> "q"
                27 -> "r"
                28 -> "s"
                29 -> "t"
                30 -> "u"
                31 -> "v"
                32 -> "w"
                33 -> "x"
                34 -> "y"
                else -> "z"
            }
            result += lastDigit
        } else result += number % base
        number /= base
    }
    return result.reversed()
}

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var i = digits.size
    var d = 0
    var result = 0.0
    while (i >= 1) {
        result += digits[d] * base.toDouble().pow(i - 1)
        d += 1
        i -= 1
    }
    return result.toInt()
}

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = TODO()

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var result = ""
    var digit: String
    if (n >= 1000) {
        digit = when (n % 10000 - n % 1000) {
            1000 -> "M"
            2000 -> "MM"
            3000 -> "MMM"
            else -> ""
        }
        result += digit
    }
    if (n >= 100) {
        digit = when (n % 1000 - n % 100) {
            100 -> "C"
            200 -> "CC"
            300 -> "CCC"
            400 -> "CD"
            500 -> "D"
            600 -> "DC"
            700 -> "DCC"
            800 -> "DCCC"
            900 -> "CM"
            else -> ""
        }
        result += digit
    }
    if (n % 100 != 0) {
        digit = when (n % 100 - n % 10) {
            10 -> "X"
            20 -> "XX"
            30 -> "XXX"
            40 -> "XL"
            50 -> "L"
            60 -> "LX"
            70 -> "LXX"
            80 -> "LXXX"
            90 -> "XC"
            else -> ""
        }
        result += digit
    }
    digit = when (n % 10) {
        1 -> "I"
        2 -> "II"
        3 -> "III"
        4 -> "IV"
        5 -> "V"
        6 -> "VI"
        7 -> "VII"
        8 -> "VIII"
        9 -> "IX"
        else -> ""
    }
    return result + digit
}

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var result = ""
    var digit: String
    var flag = true
    if (n >= 100000) {
        digit = when ((n % 1000000 - n % 100000) / 1000) {
            100 -> "сто "
            200 -> "двести "
            300 -> "триста "
            400 -> "четыреста "
            500 -> "пятьсот "
            600 -> "шестьсот "
            700 -> "семьсот "
            800 -> "восемьсот "
            900 -> "девятьсот "
            else -> ""
        }
        result += digit
    }
    if (n >= 10000) {
        if (n % 100000 in 10000..19999) {
            digit = when ((n % 100000 - n % 1000) / 1000) {
                10 -> "десять тысяч "
                11 -> "одиннадцать тысяч "
                12 -> "двенадцать тысяч "
                13 -> "тринадцать тысяч "
                14 -> "четырнадцать тысяч "
                15 -> "пятнадцать тысяч "
                16 -> "шестнадцать тысяч "
                17 -> "семнадцать тысяч "
                18 -> "восемнадцать тысяч "
                19 -> "девятнадцать тысяч "
                else -> ""
            }
            flag = false
        } else digit = when ((n % 100000 - n % 10000) / 1000) {
            20 -> "двадцать "
            30 -> "тридцать "
            40 -> "сорок "
            50 -> "пятьдесят "
            60 -> "шестьдесят "
            70 -> "семьдесят "
            80 -> "восемьдесят "
            90 -> "девяносто "
            else -> ""
        }
        result += digit
    }
    if (n >= 1000 && flag) {
        digit = when ((n % 10000 - n % 1000) / 1000) {
            1 -> "одна тысяча "
            2 -> "две тысячи "
            3 -> "три тысячи "
            4 -> "четыре тысячи "
            5 -> "пять тысяч "
            6 -> "шесть тысяч "
            7 -> "семь тысяч "
            8 -> "восемь тысяч "
            9 -> "девять тысяч "
            else -> "тысяч "
        }
        result += digit
    }
    if (n > 100) {
        digit = when (n % 1000 - n % 100) {
            100 -> "сто "
            200 -> "двести "
            300 -> "триста "
            400 -> "четыреста "
            500 -> "пятьсот "
            600 -> "шестьсот "
            700 -> "семьсот "
            800 -> "восемьсот "
            900 -> "девятьсот "
            else -> ""
        }
        result += digit
    }
    flag = true
    if (n >= 10 && n % 100 != 0) {
        if (n in 10..19) {
            digit = when (n % 100) {
                10 -> "десять"
                11 -> "одиннадцать"
                12 -> "двенадцать"
                13 -> "тринадцать"
                14 -> "четырнадцать"
                15 -> "пятнадцать"
                16 -> "шестнадцать"
                17 -> "семнадцать"
                18 -> "восемнадцать"
                19 -> "девятнадцать"
                else -> ""
            }
            flag = false
        } else {
            digit = when (n % 100 - n % 10) {
                20 -> "двадцать "
                30 -> "тридцать "
                40 -> "сорок "
                50 -> "пятьдесят "
                60 -> "шестьдесят "
                70 -> "семьдесят "
                80 -> "восемьдесят "
                90 -> "девяносто "
                else -> ""
            }
        }
        result += digit
    }
    if (flag) {
        digit = when (n % 10) {
            1 -> "один"
            2 -> "два"
            3 -> "три"
            4 -> "четыре"
            5 -> "пять"
            6 -> "шесть"
            7 -> "семь"
            8 -> "восемь"
            9 -> "девять"
            else -> ""
        }
        result += digit
    }
    while (result.takeLast(1) == " ")
        result = result.replaceFirst(".$".toRegex(), "")
    return result
}
@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr
import kotlin.math.abs

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
    sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая (2 балла)
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean {
    val firstNumber: Int = (number - number % 1000) / 1000
    val secondNumber: Int = (number % 1000 - number % 100) / 100
    val thirdNumber: Int = (number % 100 - number % 10) / 10
    val fourthNumber: Int = number % 10
    return ((firstNumber + secondNumber) == (thirdNumber + fourthNumber))
}
/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    return ((x1 == x2) or (y1 == y2) or (abs(x2 - x1) == (abs(y2 - y1))))
}

/**
 * Простая (2 балла)
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    if ((month in 1..7 step 2) or (month in 8..12 step 2)) return 31
    if ((((year % 4 == 0) and (year % 100 == 0) and
                (year % 400 != 0)) or (year % 4 != 0)) and (month == 2)) return 28
    if ((month == 2) and (year % 4 == 0)) return 29
    else return 30
}

/**
 * Простая (2 балла)
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean = TODO()
/**    if ((x1 == x2) and (y1 == y2) and (r1 <= r2) return true
}
 */

/**
 * Средняя (3 балла)
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    return (((a <= r) and (b <= s)) or ((b <= r) and (c <= s)) or ((c <= r) and (a <= s)) or
            ((a <= s) and (b <= r)) or ((b <= s) and (c <= r)) or ((c <= s) and (a <= r)))
}
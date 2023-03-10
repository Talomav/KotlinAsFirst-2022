@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import java.math.RoundingMode
import kotlin.math.max
import kotlin.math.pow

/**
 * Класс "вещественное число с фиксированной точкой"
 *
 * Общая сложность задания - сложная, общая ценность в баллах -- 20.
 * Объект класса - вещественное число с заданным числом десятичных цифр после запятой (precision, точность).
 * Например, для ограничения в три знака это может быть число 1.234 или -987654.321.
 * Числа можно складывать, вычитать, умножать, делить
 * (при этом точность результата выбирается как наибольшая точность аргументов),
 * а также сравнить на равенство и больше/меньше, преобразовывать в строку и тип Double.
 *
 * Вы можете сами выбрать, как хранить число в памяти
 * (в виде строки, целого числа, двух целых чисел и т.д.).
 * Представление числа должно позволять хранить числа с общим числом десятичных цифр не менее 9.
 */
class FixedPointNumber : Comparable<FixedPointNumber> {
    /**
     * Точность - число десятичных цифр после запятой.
     */
    private var number = ""

    private fun check(s: String): Boolean {
        val regex = """[^-.\d]""".toRegex()
        if (s.contains(regex) || s.replace(".", "").replace("-", "").length >= 16)
            return false
        return true
    }

    val precision: Int
        get() {
            val list = number.split(".")
            return if (list.size == 1 || list[1].dropLastWhile { it.toString() == "0" } == "") 0
            else list[1].length
        }

    /**
     * Конструктор из строки, точность выбирается в соответствии
     * с числом цифр после десятичной точки.
     * Если строка некорректна или цифр слишком много,
     * бросить NumberFormatException.
     *
     * Внимание: этот или другой конструктор можно сделать основным
     */
    constructor(s: String) {
        if (!check(s)) throw NumberFormatException()
        number = s.dropLastWhile { it.toString() == "0" }
        if (number.last().toString() == ".") number += 0
    }

    /**
     * Конструктор из вещественного числа с заданной точностью
     */
    constructor(d: Double, p: Int) {
        if (d.toString().split(".")[1].dropLastWhile { it.toString() == "0" }.length != p)
            throw NumberFormatException()
        number = d.toString()
    }

    /**
     * Конструктор из целого числа (предполагает нулевую точность)
     */
    constructor(i: Int) {
        number = "$i.0"
    }

    /**
     * Сложение.
     *
     * Здесь и в других бинарных операциях
     * точность результата выбирается как наибольшая точность аргументов.
     * Лишние знаки отрбрасываются, число округляется по правилам арифметики.
     */
    operator fun plus(other: FixedPointNumber): FixedPointNumber {
        val result = (this.toDouble() + other.toDouble()).toString()
        return FixedPointNumber(result)
    }

    /**
     * Смена знака
     */
    operator fun unaryMinus(): FixedPointNumber =
        FixedPointNumber((-this.toDouble()).toString())

    /**
     * Вычитание
     */
    operator fun minus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber((this.toDouble() - other.toDouble()).toString())

    /**
     * Умножение
     */
    private fun rounding(s: String, p: Int): String {
        var number = s.toBigDecimal().setScale(p, RoundingMode.HALF_UP).toString()
        var precision = p
        while (number.last().code >= 53 && precision > 0) {
            number = number.toBigDecimal().setScale(precision, RoundingMode.HALF_UP).toString()
            precision -= 1
        }
        return number
    }

    operator fun times(other: FixedPointNumber): FixedPointNumber {
        val maxPrecision = max(this.precision, other.precision)
        val result = (this.toDouble() * other.toDouble()).toString()
        if (result.split(".")[1].length > maxPrecision)
            return FixedPointNumber(rounding(result, maxPrecision))
        return FixedPointNumber(result)
    }

    /**
     * Деление
     */
    operator fun div(other: FixedPointNumber): FixedPointNumber {
        val maxPrecision = max(this.precision, other.precision)
        val result = (this.toDouble() / other.toDouble()).toString()
        if (result.split(".")[1].length > maxPrecision)
            return FixedPointNumber(rounding(result, maxPrecision))
        return FixedPointNumber(result)
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (other !is FixedPointNumber) return false
        if (this.toDouble() == other.toString().toDouble()) return true
        return false
    }

    /**
     * Сравнение на больше/меньше
     */
    override fun compareTo(other: FixedPointNumber): Int =
        (this.toDouble() - other.toDouble()).toInt()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = number

    /**
     * Преобразование к вещественному числу
     */
    fun toDouble(): Double = number.toDouble()
    override fun hashCode(): Int = number.hashCode()
}
@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlinx.html.P
import java.lang.IllegalArgumentException
import kotlin.math.abs
import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    private val coeffs = if (coeffs.isEmpty()) {
        doubleArrayOf(0.0)
    } else {
        coeffs.dropWhile { it == 0.0 }.toDoubleArray()
    }


    fun coeff(i: Int): Double = coeffs[coeffs.size - 1 - i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var result = 0.0
        for (i in coeffs.indices) {
            result += coeffs[i] * x.pow(coeffs.size - i - 1)
        }
        return result
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        for (i in coeffs.indices) {
            if (abs(coeffs[i]) > 0.0) return coeffs.size - i - 1
        }
        return 0
    }

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        var thisCoeffs = this.coeffs
        var otherCoeffs = other.coeffs
        val minSize = minOf(coeffs.size, other.coeffs.size)
        val max = maxOf(coeffs.size, other.coeffs.size)
        while (thisCoeffs.size != otherCoeffs.size) {
            if (coeffs.size == minSize) thisCoeffs = doubleArrayOf(0.0) + thisCoeffs
            else otherCoeffs = doubleArrayOf(0.0) + otherCoeffs
        }
        var result = doubleArrayOf()
        for (i in 0 until max) {
            result += thisCoeffs[i] + otherCoeffs[i]
        }
        return Polynom(*result)
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom {
        var result = doubleArrayOf()
        for (i in coeffs.indices) {
            result += -coeffs[i]
        }
        return Polynom(*result)
    }

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom {
        var thisCoeffs = this.coeffs
        var otherCoeffs = other.coeffs
        val minSize = minOf(coeffs.size, other.coeffs.size)
        val max = maxOf(coeffs.size, other.coeffs.size)
        while (thisCoeffs.size != otherCoeffs.size) {
            if (coeffs.size == minSize) thisCoeffs = doubleArrayOf(0.0) + thisCoeffs
            else otherCoeffs = doubleArrayOf(0.0) + otherCoeffs
        }
        var result = doubleArrayOf()
        for (i in 0 until max) {
            result += thisCoeffs[i] - otherCoeffs[i]
        }
        return Polynom(*result)
    }

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val result = DoubleArray(this.degree() + other.degree() + 1)
        for (i in coeffs.indices) {
            for (j in other.coeffs.indices) {
                result[i + j] += coeffs[i] * other.coeffs[j]
            }
        }
        return Polynom(*result)
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */

    operator fun div(other: Polynom): Polynom {
        var divisible = this
        var divisor = other
        if (divisor.degree() == 0 && divisor.coeffs.last() == 0.0)
            throw IllegalArgumentException()
        val result = DoubleArray(this.degree() - other.degree() + 1)
        if (divisor.degree() == 0) {
            for (i in divisible.coeffs.indices)
                result[i] = divisible.coeffs[i] / divisor.coeffs.last()
            return Polynom(*result)
        }
        while (other.degree() <= divisible.degree()) {
            val number = DoubleArray(divisible.degree() - divisor.degree() + 1)
            number[0] = divisible.coeffs[0] / divisor.coeffs[0]
            divisor = divisor.times(Polynom(*number))
            result[result.size - (divisible.degree() - other.degree()) - 1] = number[0]
            divisible = divisible.minus(divisor)
            divisor = other
        }
        return Polynom(*result)
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom =
        this.minus(this.div(other).times(other))

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (other !is Polynom) return false
        if (this === other) return true
        return this.coeffs.contentEquals(other.coeffs)
    }

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int = this.coeffs.contentHashCode()
}

@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import java.lang.IllegalArgumentException

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 14.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {
    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    private val bookMap = mutableMapOf<String, MutableSet<String>>()
    fun addHuman(name: String): Boolean {
        if (name !in bookMap.keys) {
            bookMap[name] = mutableSetOf()
            return true
        }
        return false
    }

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean {
        if (name in bookMap.keys) {
            bookMap.remove(name)
            return true
        }
        return false
    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    private fun checkPhone(phone: String): Boolean {
        val regex = """[^+*#\d]""".toRegex()
        if (phone.contains(regex))
            return false
        return true
    }

    fun addPhone(name: String, phone: String): Boolean {
        if (name !in bookMap.keys || !checkPhone(phone))
            return false
        if (checkPhone(phone)) {
            for ((key, value) in bookMap) {
                if (phone in value)
                    return false
            }
        } else throw IllegalArgumentException()
        bookMap[name]?.add(phone)
        return true
    }

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {
        if (name in bookMap.keys && bookMap.isNotEmpty() && phone in bookMap[name]!!) {
            bookMap[name]?.remove(phone)
            return true
        }
        return false
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> {
        if (name !in bookMap.keys || bookMap[name].isNullOrEmpty())
            return setOf()
        return bookMap[name]!!
    }

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        for ((key, value) in bookMap) {
            if (phone in value)
                return key
        }
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is PhoneBook || this.bookMap.keys != other.bookMap.keys)
            return false
        for ((key, value) in this.bookMap) {
            if (this.bookMap[key] != other.bookMap[key]) return false
        }
        return true
    }

    override fun hashCode(): Int = bookMap.hashCode()
}
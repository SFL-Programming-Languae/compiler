package me.alex_s168.multiplatform.collection

class Stream<T>(
    private val list: MutableList<T>
): Collection<T> {

    override val size by list::size
    override fun isEmpty(): Boolean =
        list.isEmpty()

    override fun iterator(): Iterator<T> =
        list.iterator()

    override fun containsAll(elements: Collection<T>): Boolean =
        list.containsAll(elements)

    override fun contains(element: T): Boolean =
        list.contains(element)

    var done: Boolean = false
        private set

    fun setDone() {
        done = true
    }

    fun add(item: T) {
        list.add(0, item)
    }

    operator fun plusAssign(item: T) {
        add(item)
    }

    fun consume(): T? {
        while (!done && list.isEmpty()) {
            // wait for more items
        }
        return if (list.isNotEmpty()) {
            list.removeLast()
        } else {
            null
        }
    }

    fun consumeUntil(predicate: (T) -> Boolean): List<T> {
        val result = mutableListOf<T>()
        while (true) {
            val x = consume()
                ?: break
            result += x
            if (predicate(x)) {
                break
            }
        }
        return result
    }

    fun consumeWhile(predicate: (T) -> Boolean): List<T> {
        val result = mutableListOf<T>()
        while (true) {
            val x = peek()
                ?: break
            if (!predicate(x)) {
                break
            }
            consume()
            result += x
        }
        return result
    }

    fun consumeAll(): List<T> {
        val result = mutableListOf<T>()
        while (true) {
            val x = consume()
                ?: break
            result += x
        }
        return result
    }

    fun peek(): T? {
        while (!done && list.isEmpty()) {
            // wait for more items
        }
        return if (list.isNotEmpty()) {
            list.last()
        } else {
            null
        }
    }

    fun hasNext(): Boolean =
        !done || list.isNotEmpty()
    
}
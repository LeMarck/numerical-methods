package io.github.lemarck

/**
 * 05. Численные методы решения задачи Коши для обыкновенных дифференциальных уравнений.
 */

fun main (args: Array<String>) {
    val a = 0.0
    val b = 1.0
    val h =  arrayOf(.1, .05)
    println("Точный метод:")
    h.forEach { println(methodExact(a, b, it)) }
    println("Метод Эйлера:")
    h.forEach { println(methodEuler(a, b, it)) }
    println("Метод Рунге-Кутта:")
    h.forEach { println(methodRungeKutta(a, b, it)) }
    println("Метод Симпсона:")
    h.forEach { println(methodSimpsona(a, b, it)) }
}

fun func (x: Double, y: Double) = -x * (y + 1)

fun rungeKutt (x: Double, y: Double, h: Double): Double {
    fun k1 (x: Double, y: Double) = h * func(x, y)
    fun k2 (x: Double, y: Double) = h * func(x + h / 2, y + .5 * k1(x, y))
    fun k3 (x: Double, y: Double) = h * func(x + h / 2, y + .5 * k2(x, y))
    fun k4 (x: Double, y: Double) = h * func(x + h, y + k3(x, y))

    return y + (1.0 / 6.0) * (k1(x, y) + 2 * k2(x, y) + 2 * k3(x, y) + k4(x, y))
}

fun methodExact (a: Double, b: Double, h: Double): List<Double> {
    val result = arrayListOf<Double>()
    (0..((b - a) / h).toInt()).forEach {
        result.add(2 * Math.pow(Math.E, -(Math.pow(it * h, 2.0) / 2)) - 1)
    }

    return result
}

fun methodEuler (a: Double, b: Double, h: Double): List<Double> {
    val result = arrayListOf<Double>(1.0)
    (0..((b - a) / h - 1).toInt()).forEach {
        val y = result.last()
        result.add(y + h * func(it * h, y))
    }

    return result
}

fun methodRungeKutta (a: Double, b: Double, h: Double): List<Double> {
    val result = arrayListOf<Double>(1.0)
    (0..((b - a) / h - 1).toInt()).forEach {
        val y = result.last()
        result.add(rungeKutt(it * h, y, h))
    }

    return result
}

fun methodSimpsona (a: Double, b: Double, h: Double): List<Double> {
    val result = arrayListOf<Double>(1.0, rungeKutt(a, 1.0, h))
    (0..((b - a) / h - 2).toInt()).forEach {
        val x = it * h
        val xn = x + 2 * h
        val y = result.last()
        val y0 = result[result.size - 2]
        result.add((y0 + (h / 3) * (4 * func(x + h, y) + func(x, y0) - xn)) / (1 + (h / 3) * xn))
    }

    return result
}

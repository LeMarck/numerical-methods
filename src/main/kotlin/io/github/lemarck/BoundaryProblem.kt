package io.github.lemarck

/**
 * 06. Численные методы решения краевой задачи для обыкновенных дифференциальных уравнений.
 */

fun main (args: Array<String>) {
    val a = 0.0
    val b = 1.0
    println("Точный метод:")
    println(methodExact(a, b, 20).size)
    println("Метод Стрельбы:")
    println(methodShooting(a, b, 20))
    println("Метод Прогонки:")
    println(methodRunning(a, b, 20))
}

fun f(x: Double, y: Double) = y + 8.4 + 3.2 * x * (1 - x)

fun y(x: Double) = Math.pow(Math.E, x) + Math.pow(Math.E, -x) + 3.2 * x * (x - 1) - 2

fun methodExact (a: Double, b: Double, n: Int): List<Double> {
    val h = (b - a) / n

    return List(n + 1, {i -> y(i * h)})
}

fun methodShooting (a: Double, b: Double, n: Int): List<Double> {
    val h = (b - a) / n
    var z = -3.2

    fun Y (x: Double, y: Double, z: Double) = y + h * z * (1 + h/2)
    fun Z (x: Double, y: Double, z: Double) = z + h * f(x + h/2, y + (h/2) * f(x, y))
    fun G (m: Double) = Y(1.0, m, z) + Z(1.0, m, z) - 2 * Math.E - 1.2

    fun halfDivision (a: Double, b: Double): Double {
        val c = (a + b) / 2.0
        val fa = G(a)
        val fb = G(b)
        val fc = G(c)
        val len = Math.abs(b - a)
        if (len < Math.pow(10.0, -4.0)) {
            return c
        }

        return if (fa * fc < 0) halfDivision(a, c) else halfDivision(c, b)
    }

    var y = halfDivision(a, a + h)
    val result = arrayListOf<Double>()

    (0..n).forEach {
        result.add(y)
        val x = it * h
        y = Y(x, y, z)
        z = Z(x, y, z)
    }

    return result
}

fun methodRunning (a: Double, b: Double, n: Int): List<Double> {
    val h = (b - a) / n

    fun L (l: Double) = 1 /  ((2 + Math.pow(h, 2.0)) - l)
    fun M (x: Double, m: Double, l: Double) = (Math.pow(h, 2.0) * (8.4 + 3.2 * x * (1 - x)) - m) / (l - (2 + Math.pow(h, 2.0)))
    fun Y (m: Double, l: Double) = (h * (2 * Math.E + 1.2) + m) / ((1 + h) - l)

    var m = 3.2 * h
    var l = 1.0

    val arrayM = arrayListOf<Double>(m)
    val arrayL = arrayListOf<Double>(l)

    (2..n).forEach {
        m = M(it * h, m, l)
        l = L(l)
        arrayM.add(m)
        arrayL.add(l)
    }

    val arrayY = arrayListOf<Double>(Y(m, l))

    (0..arrayM.size - 1).forEach {
        m = arrayM[arrayM.size - it - 1]
        l = arrayL[arrayL.size - it - 1]
        arrayY.add(l * arrayY.last() + m)
    }

    return arrayY.reversed()
}

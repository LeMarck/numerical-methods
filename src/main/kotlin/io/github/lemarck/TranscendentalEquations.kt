package io.github.lemarck

/**
 * 02. Решение трансцендентных уравнений.
 */

fun main (args: Array<String>) {
    val funcs = listOf(
        fun (x: Double): Double = 1.4 * Math.cos(x) - Math.pow(Math.E, x),  // f(x)
        fun (x: Double): Double = -1.4 * Math.sin(x) - Math.pow(Math.E, x), // f'(x)
        fun (x: Double): Double = -1.4 * Math.cos(x) - Math.pow(Math.E, x)  // f"(x)
    )
    fun fi (x: Double) = x + 0.431 * funcs[0](x)
    println(HalfDivision(0.0, 0.5, funcs[0]))
    println(ModifiedNewton(0.0, 0.5, funcs))
    println(SimpleIteration(0.0, ::fi))
}

fun HalfDivision (a: Double, b: Double, f: (x: Double) -> Double): Double {
    println("Метод половинного деления:")
    println("%-3s%-20s%-20s%-20s%-20s%-25s%-25s%-25s".format("№", "a", "b", "c", "|b - a|", "f(a)", "f(b)", "f(c)"))
    var count = 0
    fun calc (a: Double, b: Double): Double {
        val c = (a + b) / 2
        val f_a = f(a)
        val f_b = f(b)
        val f_c = f(c)
        val len = Math.abs(b - a)
        println("%-3s%-20s%-20s%-20s%-20s%-25s%-25s%-25s".format(++count, a, b, c, len, f_a, f_b, f_c))
        if (len < 0.5 * Math.pow(10.0, -4.0)) {
            return c
        }
        return if (f_a * f_c < 0) calc(a, c) else calc(c, b)
    }
    return calc(a, b)
}

fun ModifiedNewton (a: Double, b: Double, funcs: List<(x: Double) -> Double>): Double {
    val x_0 = if (funcs[0](a) * funcs[2](a) > 0) a else b
    println("Модифицированный метод Ньютона:")
    println("Xo = $x_0")
    println("%-3s%-25s%-25s%-25s".format("№", "Xn+1", "|Xn+1 - Xn|", "f(Xn+1)"))
    var count = 0
    fun calc (x_n: Double): Double {
        val x_n1 = x_n - funcs[0](x_n) / funcs[1](x_0)
        val len = Math.abs(x_n1 - x_n)
        val f_x_n1 = funcs[0](x_n1)
        println("%-3s%-25s%-25s%-25s".format(++count, x_n1, len, f_x_n1))
        return if (len < 0.5 * Math.pow(10.0, -4.0)) x_n1 else calc(x_n1)
    }
    return calc(x_0)
}

fun SimpleIteration (a: Double, f: (x: Double) -> Double): Double {
    var count = 0
    println("Метод простой итерации:")
    println("%-3s%-25s%-25s%-25s".format("№", "Xn", "Xn+1", "|Xn+1 - Xn|"))
    fun calc (x: Double): Double {
        val x_n = f(x)
        val len = Math.abs(x_n - x)
        println("%-3s%-25s%-25s%-25s".format(++count, x, x_n, len))
        return if (len < 0.5 * Math.pow(10.0, -4.0)) x_n else calc(x_n)
    }
    return calc(a)
}

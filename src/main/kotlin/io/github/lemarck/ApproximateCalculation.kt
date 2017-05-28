package io.github.lemarck

/**
 * 01. Приближенное вычисление суммы числового ряда.
 * Метод Куммера улучшения сходимости числового ряда.
 */

fun main (args: Array<String>) {
    val B1 = Math.pow(Math.PI, 2.0) / 6
    val B2 = 1.2020569
    val N = 2 * Math.pow(10.0, 7.0).toInt()
    val M = 2122
    val L = 98
    println(sum(N, ::S))
    println(-.45 * sum(M, ::P) + B1)
    println(.45 * (sum(L, ::Q) - B2) + B1)
}

fun sum (n: Int, exp: (n: Double) -> Double): Double {
    return (1..n).sumByDouble { exp(it.toDouble()) }
}

fun S (n: Double) = n / Math.pow((n + .15), 3.0)

fun P (n: Double) = (Math.pow(n, 2.0) + 0.15 * n + .0075) / (Math.pow(n, 2.0) * Math.pow((n + .15), 3.0))

fun Q (n: Double) = (.3 * Math.pow(n, 2.0) + .06 * n + .003375) / (Math.pow(n, 3.0) * Math.pow((n + .15), 3.0))

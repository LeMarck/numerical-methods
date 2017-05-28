package io.github.lemarck

/**
 * 03. Численные методы решения систем линейных алгебраических уравнений.
 */

fun main (args: Array<String>) {
    val eps = .5 * Math.pow(10.0, -4.0)
    val x = arrayOf(.91, 1.982457, 2.79)
    println("Метод Якоби:")
    println(iterator(x, eps, ::Jacobi))
    println("Метод Зейделя:")
    println(iterator(x, eps, ::Seidel))
}

fun iterator (x: Array<Double>, eps: Double, exp: (x: Array<Double>) -> Array<Double>): Array<Double> {
    var res = x.copyOf()
    var count = 0
    do {
        val oldX = res.copyOf()
        res = exp(res)
        count++
    } while (res[0] - oldX[0] < eps || res[1] - oldX[1] < eps || res[2] - oldX[2] < eps)
    println(++count)

    return exp(res)
}

fun Jacobi (x: Array<Double>) = arrayOf(
    .91 + .133333 * x[1] - .066667 * x[2],
    1.982457 + .1 * x[0] + .1 * x[2],
    2.79 - .613347 * x[0] + .41 * x[1]
)

fun Seidel (x: Array<Double>): Array<Double> {
    val x1 = .91 + .133333 * x[1] - .066667 * x[2]
    val x2 = 1.982457 + .1 * x1 + .1 * x[2]
    val x3 = 2.79 - .613347 * x1 + .41 * x2

    return arrayOf(x1, x2, x3)
}

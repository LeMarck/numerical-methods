package io.github.lemarck

/**
 * 04. Приближенное вычисление определенного интеграла.
 */

fun main (args: Array<String>) {
    val a = 0.0
    val b = 1.0
    val h =  arrayOf(.1, .05, .025)
    println("Метод правых прямоугольников:")
    h.forEach { println(methodRightRectangles(a, b, it)) }
    println("Формула Грегори:")
    h.forEach { println(methodGregory(a, b, it)) }
    println("Метод Гаусса:")
    println(methodGauss())
}

fun func (x: Double) = Math.sqrt(1 + Math.pow(x, 3.0))

fun methodRightRectangles (a: Double, b: Double, h: Double) =
    (1..((b - a) / h).toInt()).sumByDouble { func(a + it * h) } * h

fun methodGregory (a: Double, b: Double, h: Double): Double {
    val sum = (1..((b - a) / h - 1).toInt()).sumByDouble { func(a + it * h) } * h

    return (func(a) + func(b)) * h / 2 + sum + (-3 * func(a) + 4 * func(a + h) -
        func(a + 2 * h) - func(b - 2 * h) + 4 * func(b - h) - 3 * func(b)) * h / 24
}

fun methodGauss () = .5 * (func(.5 * (-1 / Math.sqrt(3.0)) + .5) + func(.5 * (1 / Math.sqrt(3.0)) + .5))

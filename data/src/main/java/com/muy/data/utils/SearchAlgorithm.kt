package com.muy.data.utils

import java.util.*

class SearchAlgorithm {
    private lateinit var bmBC: IntArray
    private lateinit var bmGs: IntArray
    private var comparisons = 0
    private fun preBmBc(x: CharArray) {
        val m = x.size
        bmBC = IntArray(ALPHABET_SIZE)
        Arrays.fill(bmBC, m)
        for (i in 0 until m - 1) {
            bmBC[x[i].toInt()] = m - i - 1
        }
    }

    fun suffixes(x: CharArray): IntArray {
        val m = x.size
        val suff = IntArray(m)
        var g = m - 1
        var f = m - 1
        suff[m - 1] = m
        for (i in m - 2 downTo 0) {
            if (i > g && i + m - 1 - f < m && suff[i + m - 1 - f] < i - g) {
                suff[i] = suff[i + m - 1 - f]
            } else { //if (i < g) {
                g = i
                //}
                f = g
                while (g >= 0 && x[g] == x[g + m - 1 - f]) {
                    --g
                }
                suff[i] = f - g
            }
        }
        return suff
    }

    fun preBmGs(x: CharArray) {
        val m = x.size
        bmGs = IntArray(m)
        val suff = suffixes(x)
        Arrays.fill(bmGs, m)
        var j = 0
        for (i in m - 1 downTo -1) {
            if (i == -1 || suff[i] == i + 1) {
                while (j < m - 1 - i) {
                    if (bmGs[j] == m) {
                        bmGs[j] = m - 1 - i
                    }
                    ++j
                }
            }
        }
        for (i in 0 until m - 1) {
            bmGs[m - 1 - suff[i]] = m - 1 - i
        }
    }

    fun search(text: String, pattern: String): Boolean {
        val y = text.toCharArray()
        val x = pattern.toCharArray()
        val n = y.size // string length
        val m = x.size // pattern length
        val resultado: MutableList<Int> = ArrayList()
        var j = 0
        var i = 0
        comparisons = 0
        /* Precompute */preBmBc(x)
        preBmGs(x)
        /* Searching */while (j <= n - m) {
            i = m - 1
            while (i >= 0 && x[i] == y[i + j]) {
                comparisons++
                i--
            }
            j += if (i < 0) {
                resultado.add(j)
                bmGs[0]
            } else {
                Math.max(bmGs[i], bmBC[y[i + j].toInt()] - m + 1 + i)
            }
        }
        return resultado.size > 0
    }

    companion object {
        private const val ALPHABET_SIZE = 256
    }
}
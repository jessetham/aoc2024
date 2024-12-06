import kotlin.math.sign

fun main() {
    val testInput = readInputAsLines("Day04_test")
    val testParsed = Day04.parse(testInput)
    check(Day04.part1(testParsed) == 18)
    check(Day04.part2(testParsed) == 9)

    val input = readInputAsLines("Day04")
    val parsed = Day04.parse(input)
    Day04.part1(parsed).println()
    Day04.part2(parsed).println()
}

private object Day04 {
    fun parse(input: List<String>) = input.map {
        it.toCharArray()
    }

    fun part1(grid: List<CharArray>): Int {
        fun search(r: Int, c: Int, mr: Int, mc: Int): Int {
            var mr = mr
            var mc = mc
            var count = 0
            repeat(4) {
                val found = buildString {
                    var dr = 0
                    var dc = 0
                    repeat(4) {
                        append(grid.getOrNull(r + dr)?.getOrNull(c + dc) ?: "")
                        dr += mr.sign
                        dc += mc.sign
                    }
                }
                if (found == "XMAS") {
                    count++
                }
                mr = -mc.also { mc = mr }
            }
            return count
        }

        var total = 0
        for ((r, row) in grid.withIndex()) {
            for (c in row.indices) {
                // search up, down, left, right
                total += search(r, c, 4, 0)
                // search diagonal and anti-diagonal
                total += search(r, c, 4, 4)
            }
        }
        return total
    }

    fun part2(grid: List<CharArray>): Int {
        fun search(r: Int, c: Int): Int {
            val diag =
                (grid.getOrNull(r - 1)?.getOrNull(c - 1) == 'M' && grid.getOrNull(r + 1)?.getOrNull(c + 1) == 'S') ||
                        (grid.getOrNull(r - 1)?.getOrNull(c - 1) == 'S' && grid.getOrNull(r + 1)
                            ?.getOrNull(c + 1) == 'M')
            val anti =
                (grid.getOrNull(r - 1)?.getOrNull(c + 1) == 'M' && grid.getOrNull(r + 1)?.getOrNull(c - 1) == 'S') ||
                        (grid.getOrNull(r - 1)?.getOrNull(c + 1) == 'S' && grid.getOrNull(r + 1)
                            ?.getOrNull(c - 1) == 'M')
            return if (grid[r][c] == 'A' && diag && anti) 1 else 0
        }

        var total = 0
        for ((r, row) in grid.withIndex()) {
            for (c in row.indices) {
                total += search(r, c)
            }
        }
        return total
    }
}
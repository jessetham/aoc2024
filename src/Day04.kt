import kotlin.math.sign

fun main() {
    val testInput = readInputAsLines("Day04_test")
    val testParsed = parse(testInput)
    check(part1(testParsed) == 18)
    check(part2(testParsed) == 9)

    val input = readInputAsLines("Day04")
    val parsed = parse(input)
    part1(parsed).println()
    part2(parsed).println()
}

private fun parse(input: List<String>) = input.map {
    it.toCharArray()
}

private fun part1(grid: List<CharArray>): Int {
    fun search(r: Int, c: Int, mr: Int, mc: Int): Int {
        var mr = mr
        var mc = mc
        var count = 0
        repeat(4) {
            var found = buildString {
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

private fun part2(grid: List<CharArray>): Int {
    fun search(r: Int, c: Int): Int {
        var diag = (grid.getOrNull(r - 1)?.getOrNull(c - 1) == 'M' && grid.getOrNull(r + 1)?.getOrNull(c + 1) == 'S') ||
                (grid.getOrNull(r - 1)?.getOrNull(c - 1) == 'S' && grid.getOrNull(r + 1)?.getOrNull(c + 1) == 'M')
        val anti = (grid.getOrNull(r - 1)?.getOrNull(c + 1) == 'M' && grid.getOrNull(r + 1)?.getOrNull(c - 1) == 'S') ||
                (grid.getOrNull(r - 1)?.getOrNull(c + 1) == 'S' && grid.getOrNull(r + 1)?.getOrNull(c - 1) == 'M')
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
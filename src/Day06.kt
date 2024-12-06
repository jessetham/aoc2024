fun main() {
    val testInput = readInputAsLines("Day06_test")
    val testGrid = Day06.parse(testInput)
    check(Day06.part1(testGrid) == 41)
    check(Day06.part2(testGrid) == 6)

    val input = readInputAsLines("Day06")
    val grid = Day06.parse(input)
    Day06.part1(grid).println()
    Day06.part2(grid).println()
}

private object Day06 {
    fun parse(lines: List<String>) = lines.map { it.toCharArray() }

    fun part1(grid: List<CharArray>) = grid.patrol().first.map { it.position }.toSet().size

    fun part2(grid: List<CharArray>): Int {
        val copy = grid.map { it.copyOf() }
        val (route, _) = copy.patrol()
        val visitedLocations = route.map { it.position }.toSet()
        // We could use coroutines to run this count asynchronously, but I don't want to import the coroutines package
        return visitedLocations.count { (r, c) ->
            if (grid[r][c] == '^')
                return@count false
            copy[r][c] = '#'
            val (_, hasCycle) = copy.patrol()
            copy[r][c] = '.'
            hasCycle
        }
    }

    private data class Guard(val r: Int, val c: Int, val dr: Int, val dc: Int) {
        val position = r to c

        fun move() = this.copy(r = r + dr, c = c + dc)
        fun rotate() = this.copy(dr = dc, dc = -dr)
    }

    private fun List<CharArray>.getStartingPosition(): Array<Int> {
        for ((r, row) in this.withIndex()) {
            for (c in row.indices) {
                if (this[r][c] == '^') {
                    return arrayOf(r, c)
                }
            }
        }
        throw IllegalStateException("no guard found")
    }

    private fun List<CharArray>.peek(guard: Guard) = this.getOrNull(guard.r + guard.dr)?.getOrNull(guard.c + guard.dc)

    // Returns a set of visited guard locations (with orientation) and whether the patrol has a cycle
    private fun List<CharArray>.patrol(): Pair<Set<Guard>, Boolean> {
        val (sr, sc) = this.getStartingPosition()
        var guard = Guard(sr, sc, -1, 0)
        val visited = mutableSetOf(guard)
        while (this.peek(guard) != null) {
            guard = when (this.peek(guard)) {
                '#' -> guard.rotate()
                '.', '^' -> guard.move()
                else -> throw IllegalStateException("unexpected tile: ${this.peek(guard)}")
            }
            if (guard in visited) {
                return visited to true
            }
            visited.add(guard)
        }
        return visited to false
    }
}
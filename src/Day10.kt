fun main() {
    val testMap = readInputAsLines("Day10_test").map { it.map(Char::digitToInt) }
    check(Day10.part1(testMap) == 36)
    check(Day10.part2(testMap) == 81)

    val map = readInputAsLines("Day10").map { it.map(Char::digitToInt) }
    Day10.part1(map).println()
    Day10.part2(map).println()
}

private object Day10 {
    fun part1(map: List<List<Int>>) = scoreAllTrailheads(map)

    fun part2(map: List<List<Int>>) = scoreAllTrailheads(map, false)

    fun scoreAllTrailheads(map: List<List<Int>>, trackSeen: Boolean = true): Int {
        var score = 0
        for ((r, row) in map.withIndex()) {
            for ((c, i) in row.withIndex()) {
                if (i == 0)
                    score += scoreTrailhead(map, r, c, trackSeen)
            }
        }
        return score
    }

    private fun scoreTrailhead(map: List<List<Int>>, sr: Int, sc: Int, trackSeen: Boolean = true): Int {
        val height = map.size
        val width = map[0].size
        val seen = mutableSetOf(sr to sc)
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(sr to sc)
        var score = 0
        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()
            if (map[r][c] == 9) {
                score++
                continue
            }
            var dr = 0
            var dc = 1
            for (i in 0 until 4) {
                val nr = r + dr
                val nc = c + dc
                dr = dc.also { dc = -dr }
                if (trackSeen && nr to nc in seen) {
                    continue
                }
                if (nr in 0 until height && nc in 0 until width && map[nr][nc] == map[r][c] + 1) {
                    queue.add(nr to nc)
                    if (trackSeen) {
                        seen.add(nr to nc)
                    }
                }
            }
        }
        return score
    }
}
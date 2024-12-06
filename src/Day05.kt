fun main() {
    val testInput = readInputAsText("Day05_test")
    val testParsed = Day05.parse(testInput)
    check(Day05.part1(testParsed.first, testParsed.second) == 143)
    check(Day05.part2(testParsed.first, testParsed.second) == 123)

    val input = readInputAsText("Day05")
    val parsed = Day05.parse(input)
    Day05.part1(parsed.first, parsed.second).println()
    Day05.part2(parsed.first, parsed.second).println()
}

private object Day05 {
    fun parse(input: String): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        val (orderingRules, updates) = input.split("\n\n")
        val inedges = mutableMapOf<Int, MutableList<Int>>()
        for (line in orderingRules.lines()) {
            val (from, to) = line.split('|').let { it[0].toInt() to it[1].toInt() }
            val edges = inedges.getOrElse(to) { mutableListOf() }
            edges.add(from)
            inedges[to] = edges
        }
        val parsedUpdates = updates.lines().map { line -> line.split(",").map { it.toInt() } }
        return inedges to parsedUpdates
    }

    // Not the fastest topological sort algorithm, but it gets the job done
    private fun topologicalSort(inedges: Map<Int, List<Int>>, update: List<Int>): List<Int> {
        val numsInUpdate = update.toSet()
        val seen = mutableSetOf<Int>()
        val reordered = mutableListOf<Int>()
        while (reordered.size < update.size) {
            val next = update.first { u ->
                val edges = inedges.getOrElse(u) { mutableListOf() }
                edges.all { it !in numsInUpdate || it in seen } && u !in seen
            }
            reordered.add(next)
            seen.add(next)
        }
        return reordered
    }

    fun part1(inedges: Map<Int, List<Int>>, updates: List<List<Int>>) =
        updates.filter { topologicalSort(inedges, it) == it }.sumOf { it[it.size / 2] }

    fun part2(inedges: Map<Int, List<Int>>, updates: List<List<Int>>) =
        updates.map { topologicalSort(inedges, it) }
            .filterIndexed { i, sorted -> sorted != updates[i] }
            .sumOf { it[it.size / 2] }
}
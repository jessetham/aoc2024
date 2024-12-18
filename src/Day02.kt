fun main() {
    val testInput = readInputAsLines("Day02_test")
    val testParsed = Day02.parse(testInput)
    check(Day02.part1(testParsed) == 2)
    check(Day02.part2(testParsed) == 4)

    val input = readInputAsLines("Day02")
    val parsed = Day02.parse(input)
    Day02.part1(parsed).println()
    Day02.part2(parsed).println()
}

private object Day02 {
    fun parse(input: List<String>) = input.map { line ->
        line.split(" ").map { it.toInt() }
    }

    fun part1(report: List<List<Int>>) = report.count { row ->
        // If candidates size is 1, then the predicates are satisfied without any removals needed
        makeCandidates(row, ::isIncreasingAndInRange).size == 1 || makeCandidates(
            row,
            ::isDecreasingAndInRange
        ).size == 1
    }

    fun part2(report: List<List<Int>>) = report.count { row ->
        // Need to double-check if candidates meet the predicates after creating candidates
        makeCandidates(row, ::isIncreasingAndInRange).any { candidate ->
            candidate.windowed(2).all { isIncreasingAndInRange(it[0], it[1]) }
        } || makeCandidates(row, ::isDecreasingAndInRange).any { candidate ->
            candidate.windowed(2).all { isDecreasingAndInRange(it[0], it[1]) }
        }
    }

    private fun isIncreasingAndInRange(prev: Int, curr: Int) = prev < curr && curr - prev in 1..3

    private fun isDecreasingAndInRange(prev: Int, curr: Int) = prev > curr && prev - curr in 1..3

    private fun makeCandidates(row: List<Int>, predicate: (Int, Int) -> Boolean): List<List<Int>> {
        var prev = 0
        for ((i, num) in row.withIndex()) {
            if (i == 0 || predicate(prev, num)) {
                prev = num
            } else {
                return listOf(
                    row.filterIndexed { index, _ -> index != i - 1 },
                    row.filterIndexed { index, _ -> index != i })
            }
        }
        return listOf(row)
    }
}
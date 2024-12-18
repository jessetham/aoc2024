fun main() {
    val testInput = readInputAsLines("Day03_test").first()
    check(Day03.part1(testInput) == 161)
    check(Day03.part2(testInput) == 48)

    val input = readInputAsLines("Day03").joinToString(separator = "")
    Day03.part1(input).println()
    Day03.part2(input).println()
}

private object Day03 {
    private val mulRegex = """mul\(([0-9]{1,3}),([0-9]{1,3})\)""".toRegex()
    private val doRegex = """do\(\)""".toRegex()
    private val dontRegex = """don't\(\)""".toRegex()

    fun part1(input: String): Int {
        val matches = mulRegex.findAll(input)
        return matches.sumOf { result ->
            val (l, r) = result.destructured
            l.toInt() * r.toInt()
        }
    }

    fun part2(input: String): Int {
        var enabled = true
        var sum = 0
        for (i in input.indices) {
            when {
                doRegex.matchAt(input, index = i) != null -> enabled = true
                dontRegex.matchAt(input, index = i) != null -> enabled = false
                enabled -> {
                    mulRegex.matchAt(input, index = i)?.let { matchResult ->
                        val (l, r) = matchResult.destructured
                        sum += l.toInt() * r.toInt()
                    }
                }
            }
        }
        return sum
    }
}
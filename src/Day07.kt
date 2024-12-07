fun main() {
    val testInput = readInputAsLines("Day07_test")
    val testParsed = Day07.parse(testInput)
    check(Day07.part1(testParsed) == 3749L)
    check(Day07.part2(testParsed) == 11387L)

    val input = readInputAsLines("Day07")
    val parsed = Day07.parse(input)
    Day07.part1(parsed).println()
    Day07.part2(parsed).println()
}

private object Day07 {
    fun parse(input: List<String>): List<Pair<Long, List<Long>>> {
        val parsed = mutableListOf<Pair<Long, List<Long>>>()
        for (line in input) {
            val split = line.split(": ")
            parsed.add(split[0].toLong() to split[1].split(" ").map { it.toLong() })
        }
        return parsed
    }

    fun part1(input: List<Pair<Long, List<Long>>>) = calculateTCR(input, listOf('+', '*'))

    fun part2(input: List<Pair<Long, List<Long>>>) = calculateTCR(input, listOf('+', '*', '|'))

    private fun calculateTCR(input: List<Pair<Long, List<Long>>>, availableOperators: List<Char>) =
        input.sumOf { (testValue, nums) ->
            val isValid = backtrack(nums, 1, availableOperators, mutableListOf(), testValue)
            if (isValid) testValue else 0
        }

    private fun backtrack(
        nums: List<Long>,
        i: Int,
        availableOperators: List<Char>,
        operators: MutableList<Char>,
        target: Long,
    ): Boolean {
        val evaluated = evaluate(nums, operators)
        if (i == nums.size) {
            return evaluated == target
        } else if (evaluated > target) {
            return false
        }
        for (op in availableOperators) {
            operators.add(op)
            val isValid = backtrack(nums, i + 1, availableOperators, operators, target)
            operators.removeLast()
            if (isValid) {
                return true
            }
        }
        return false
    }

    private fun evaluate(operands: List<Long>, operators: List<Char>): Long {
        val operandsStack = operands.reversed().toMutableList()
        val operatorsStack = operators.reversed().toMutableList()
        while (operatorsStack.isNotEmpty()) {
            val l = operandsStack.removeLast()
            val r = operandsStack.removeLast()
            val res = when (val operator = operatorsStack.removeLast()) {
                '+' -> l + r
                '*' -> l * r
                '|' -> "$l$r".toLong()
                else -> throw IllegalStateException("unknown operator $operator")
            }
            operandsStack.add(res)
        }
        return operandsStack.last()
    }
}
import kotlin.math.absoluteValue

fun main() {
    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInputAsLines("Day01_test")
    val testParsed = Day01.parse(testInput)
    check(Day01.part1(testParsed) == 11)
    check(Day01.part2(testParsed) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputAsLines("Day01")
    val parsed = Day01.parse(input)
    Day01.part1(parsed).println()
    Day01.part2(parsed).println()
}

private object Day01 {
    fun parse(input: List<String>): List<List<Int>> {
        val nums = mutableListOf(mutableListOf<Int>(), mutableListOf())
        for (line in input) {
            val split = line.split("   ")
            nums[0].add(split[0].toInt())
            nums[1].add(split[1].toInt())
        }
        return nums
    }

    fun part1(input: List<List<Int>>): Int {
        val sorted = input.map { it.sorted() }
        return (sorted[0] zip sorted[1]).sumOf { (it.first - it.second).absoluteValue }
    }

    fun part2(input: List<List<Int>>): Int {
        val counter = input[1].groupingBy { it }.eachCount()
        return input[0].sumOf { it * counter.getOrDefault(it, 0) }
    }
}
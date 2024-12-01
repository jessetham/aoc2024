import kotlin.math.absoluteValue

fun main() {
    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(parseInput(testInput)) == 11)
    check(part2(parseInput(testInput)) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(parseInput(input)).println()
    part2(parseInput(input)).println()
}

private fun parseInput(input: List<String>): List<List<Int>> {
    val nums = mutableListOf(mutableListOf<Int>(), mutableListOf())
    for (line in input) {
        val split = line.split("   ")
        nums[0].add(split[0].toInt())
        nums[1].add(split[1].toInt())
    }
    return nums
}

private fun part1(input: List<List<Int>>): Int {
    val sorted = input.map { it.sorted() }
    return (sorted[0] zip sorted[1]).sumOf { (it.first - it.second).absoluteValue }
}

private fun part2(input: List<List<Int>>): Int {
    val counter = input[1].groupingBy { it }.eachCount()
    return input[0].sumOf { it * counter.getOrDefault(it, 0) }
}
fun main() {
    val testInput = readInputAsText("Day11_test").split(" ").map { it.toLong() }
    Day11.countNodesAfterBlinks(testInput, 25).println()

    val input = readInputAsText("Day11").split(" ").map { it.toLong() }
    Day11.countNodesAfterBlinks(input, 25).println()
    Day11.countNodesAfterBlinks(input, 75).println()
}

private object Day11 {
    fun countNodesAfterBlinks(nums: List<Long>, numBlinks: Int): Long {
        fun solve(num: Long, blinksLeft: Int, memo: MutableMap<Pair<Long, Int>, Long>): Long {
            if (blinksLeft == 0) {
                return 1
            } else if (num to blinksLeft !in memo) {
                val digits = num.toString()
                memo[num to blinksLeft] = when {
                    num == 0L -> solve(1, blinksLeft - 1, memo)
                    digits.length % 2 == 0 -> {
                        val l = digits.substring(0 until digits.length / 2).toLong()
                        val r = digits.substring(digits.length / 2 until digits.length).toLong()
                        solve(l, blinksLeft - 1, memo) + solve(r, blinksLeft - 1, memo)
                    }

                    else -> solve(num * 2024, blinksLeft - 1, memo)
                }
            }
            return memo.getValue(num to blinksLeft)
        }

        val memo = mutableMapOf<Pair<Long, Int>, Long>()
        return nums.sumOf { solve(it, numBlinks, memo) }
    }
}
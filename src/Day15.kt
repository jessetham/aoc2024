fun main() {
    val input = readInputAsText("Day15")
    val split = input.split("\n\n")
    val grid = split[0].lines().map { it.toCharArray() }
    var start = Vec2(0, 0)
    for (r in grid.indices) {
        for (c in grid[r].indices) {
            if (grid[r][c] == '@') {
                start = Vec2(r.toLong(), c.toLong())
                break
            }
        }
    }
    val moves = split[1].split('\n').joinToString(separator = "").map {
        when (it) {
            '^' -> Vec2(-1, 0)
            '>' -> Vec2(0, 1)
            'v' -> Vec2(1, 0)
            '<' -> Vec2(0, -1)
            else -> throw IllegalArgumentException("Invalid move: $it")
        }
    }
    Day15.calculateBoxGpsSum(grid, start, moves).println()
}

private object Day15 {
    fun calculateBoxGpsSum(grid: List<CharArray>, start: Vec2, moves: List<Vec2>): Long {
        moveRobot(grid, start, moves)
        var sum = 0L
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == 'O') {
                    sum += 100 * r + c
                }
            }
        }
        return sum
    }

    private fun moveRobot(grid: List<CharArray>, start: Vec2, moves: List<Vec2>) {
        var robo = start
        for (move in moves) {
            val moved = robo + move
            val (mr, mc) = moved.indices
            robo = when (grid.getOrNull(mr)?.getOrNull(mc)) {
                '.', '@' -> moved
                'O' -> if (moveBox(grid, moved, move)) moved else robo
                else -> robo
            }
        }
    }

    private fun moveBox(grid: List<CharArray>, start: Vec2, direction: Vec2): Boolean {
        var current = start
        while (current.x in grid.indices && current.y in grid[0].indices) {
            val (r, c) = current.indices
            when (grid[r][c]) {
                '#' -> break
                '.', '@' -> {
                    val (sr, sc) = start.indices
                    grid[sr][sc] = '.'.also { grid[r][c] = 'O' }
                    return true
                }

                else -> current += direction
            }
        }
        return false
    }
}
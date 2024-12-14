fun main() {
    val games = readInputAsText("Day13").split("\n\n").map(Day13.Game::fromInput)
    Day13.part1(games).println()
    Day13.part2(games).println()
}

private object Day13 {
    fun part1(games: List<Game>) = games.sumOf {
        val (a, b) = solveEquations(it)
        if (a <= 100 && b <= 100) a * 3 + b else 0
    }

    fun part2(games: List<Game>) = games
        .map { it.copy(prize = Vec2(it.prize.x + 10000000000000, it.prize.y + 10000000000000)) }
        .sumOf {
            val (a, b) = solveEquations(it)
            a * 3 + b
        }

    // The game can be represented by a linear system of equations. We have two equations and two unknowns so we can solve
    // for the unknowns.
    private fun solveEquations(game: Game): Pair<Long, Long> {
        val bNum = game.buttonA.x * game.prize.y - game.prize.x * game.buttonA.y
        val bDen = game.buttonA.x * game.buttonB.y - game.buttonB.x * game.buttonA.y
        if (bNum % bDen != 0L) {
            return 0L to 0L
        }
        val numButtonBPresses = bNum / bDen
        val aNum = game.prize.x - numButtonBPresses * game.buttonB.x
        if (aNum % game.buttonA.x != 0L) {
            return 0L to 0L
        }
        val numButtonAPresses = aNum / game.buttonA.x
        return numButtonAPresses to numButtonBPresses
    }

    data class Game(val prize: Vec2, val buttonA: Vec2, val buttonB: Vec2) {
        companion object {
            fun fromInput(input: String): Game {
                val lines = input.split("\n")
                val aX = lines[0].substringAfter('X').substringBefore(',').toLong()
                val aY = lines[0].substringAfter('Y').toLong()
                val bX = lines[1].substringAfter('X').substringBefore(',').toLong()
                val bY = lines[1].substringAfter('Y').toLong()
                val prizeX = lines[2].substringAfter("X=").substringBefore(',').toLong()
                val prizeY = lines[2].substringAfter("Y=").toLong()
                return Game(Vec2(prizeX, prizeY), Vec2(aX, aY), Vec2(bX, bY))
            }
        }
    }

    data class Vec2(val x: Long, val y: Long) {
        operator fun plus(other: Vec2) = Vec2(x = x + other.x, y = y + other.y)
    }
}
fun main() {
    val testInput = readInputAsLines("Day08_test")
    val testAntennas = Day08.parse(testInput)
    val testHeight = testInput.size
    val testWidth = testInput[0].length
    check(Day08.part1(testAntennas, testHeight, testWidth) == 14)
    check(Day08.part2(testAntennas, testHeight, testWidth) == 34)

    val input = readInputAsLines("Day08")
    val antennas = Day08.parse(input)
    val height = input.size
    val width = input[0].length
    Day08.part1(antennas, height, width).println()
    Day08.part2(antennas, height, width).println()
}

private object Day08 {
    fun parse(input: List<String>): Map<Char, List<Coordinate>> {
        val antennas = mutableMapOf<Char, MutableList<Coordinate>>()
        for ((r, row) in input.withIndex()) {
            for ((c, p) in row.withIndex()) {
                if (p != '.') {
                    val coord = Coordinate(r, c)
                    val locations = antennas.getOrElse(p) { mutableListOf() }
                    locations.add(coord)
                    antennas[p] = locations
                }
            }
        }
        return antennas
    }

    fun part1(antennas: Map<Char, List<Coordinate>>, height: Int, width: Int) =
        getAllAntinodes(antennas) { c1, c2 -> c1.getAntinodes(c2, height, width) }.toSet().size

    fun part2(antennas: Map<Char, List<Coordinate>>, height: Int, width: Int) =
        getAllAntinodes(antennas) { c1, c2 -> c1.getAntinodesWithResonants(c2, height, width) }.toSet().size

    private fun getAllAntinodes(
        antennas: Map<Char, List<Coordinate>>,
        f: (Coordinate, Coordinate) -> List<Coordinate>
    ) = antennas.values.flatMap { locations ->
        val antinodes = mutableListOf<Coordinate>()
        for (i in locations.indices) {
            for (j in i + 1 until locations.size) {
                antinodes.addAll(f(locations[i], locations[j]))
            }
        }
        antinodes
    }

    data class Coordinate(val r: Int, val c: Int) {
        fun plus(other: Coordinate) = Coordinate(r + other.r, c + other.c)

        fun minus(other: Coordinate) = Coordinate(r - other.r, c - other.c)

        fun getAntinodes(other: Coordinate, height: Int, width: Int): List<Coordinate> {
            val delta = this.minus(other)
            return listOf(
                this.plus(delta),
                this.minus(delta).minus(delta)
            ).filter { it.r in 0 until height && it.c in 0 until width }
        }

        fun getAntinodesWithResonants(other: Coordinate, height: Int, width: Int): List<Coordinate> {
            val delta = this.minus(other)
            val antinodes = mutableListOf(this)
            var antinode = this.plus(delta)
            while (antinode.r in 0 until height && antinode.c in 0 until width) {
                antinodes.add(antinode)
                antinode = antinode.plus(delta)
            }
            antinode = this.minus(delta)
            while (antinode.r in 0 until height && antinode.c in 0 until width) {
                antinodes.add(antinode)
                antinode = antinode.minus(delta)
            }
            return antinodes
        }
    }
}
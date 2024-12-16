import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInputAsText(name: String) = Path("src/$name.txt").readText().trim()
/**
 * Reads lines from the given input txt file.
 */
fun readInputAsLines(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

data class Vec2(val x: Long, val y: Long) {
    operator fun plus(other: Vec2) = Vec2(x = x + other.x, y = y + other.y)
}

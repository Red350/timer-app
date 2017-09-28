package red.padraig.twotapp.extensions

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Red on 27/09/2017.
 */
class LongExtensionsTest {

    val time: Long = 3600000 + 120000 + 3000 + 4   // 1h 2m 3s 4ms

    @Before
    fun setUp() {
    }

    @Test
    fun testGetHours() {
        Assert.assertEquals(1, time.getHours())
    }

    @Test
    fun testGetMinutes() {
        Assert.assertEquals(2, time.getMinutes())
    }

    @Test
    fun testGetSeconds() {
        Assert.assertEquals(3, time.getSeconds())
    }

    @Test
    fun testGetMilliseconds() {
        Assert.assertEquals(4, time.getMilliseconds())
    }
}
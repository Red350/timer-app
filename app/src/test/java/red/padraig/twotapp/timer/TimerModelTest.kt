package red.padraig.twotapp.timer

import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Test

/**
 * Created by Red on 21/09/2017.
 */
class TimerModelTest {

    val mockSharedPreferences: SharedPreferences = mock()
    val timer = TimerModel(mockSharedPreferences)

    @Test
    fun testHourSets_IfValueInRange() {
        Assert.assertEquals(0, timer.hour)
        timer.hour = 50
        Assert.assertEquals(50, timer.hour)
        timer.hour = 99
        Assert.assertEquals(99, timer.hour)
        timer.hour = 0
        Assert.assertEquals(0, timer.hour)

    }

    @Test
    fun testHourDoesNotSet_IfValueOutsideRange() {
        Assert.assertEquals(0, timer.hour)
        timer.hour = -1
        Assert.assertEquals(0, timer.hour)
        timer.hour = 100
        Assert.assertEquals(0, timer.hour)
    }

    @Test
    fun testMinSets_IfValueInRange() {
        Assert.assertEquals(0, timer.minute)
        timer.minute = 50
        Assert.assertEquals(50, timer.minute)
        timer.minute = 99
        Assert.assertEquals(99, timer.minute)
        timer.minute = 0
        Assert.assertEquals(0, timer.minute)

    }

    @Test
    fun testMinDoesNotSet_IfValueOutsideRange() {
        Assert.assertEquals(0, timer.minute)
        timer.minute = -1
        Assert.assertEquals(0, timer.minute)
        timer.minute = 100
        Assert.assertEquals(0, timer.minute)
    }


}
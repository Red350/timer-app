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
        Assert.assertEquals(0, timer.hours)
        timer.hours = 50
        Assert.assertEquals(50, timer.hours)
        timer.hours = 99
        Assert.assertEquals(99, timer.hours)
        timer.hours = 0
        Assert.assertEquals(0, timer.hours)

    }

    @Test
    fun testHourDoesNotSet_IfValueOutsideRange() {
        Assert.assertEquals(0, timer.hours)
        timer.hours = -1
        Assert.assertEquals(0, timer.hours)
        timer.hours = 100
        Assert.assertEquals(0, timer.hours)
    }

    @Test
    fun testMinSets_IfValueInRange() {
        Assert.assertEquals(0, timer.minutes)
        timer.minutes = 50
        Assert.assertEquals(50, timer.minutes)
        timer.minutes = 99
        Assert.assertEquals(99, timer.minutes)
        timer.minutes = 0
        Assert.assertEquals(0, timer.minutes)

    }

    @Test
    fun testMinDoesNotSet_IfValueOutsideRange() {
        Assert.assertEquals(0, timer.minutes)
        timer.minutes = -1
        Assert.assertEquals(0, timer.minutes)
        timer.minutes = 100
        Assert.assertEquals(0, timer.minutes)
    }


}
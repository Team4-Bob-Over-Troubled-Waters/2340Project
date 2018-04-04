package cs2340.bob_over_troubled_waters.homelessshelterapplication;

import org.junit.Before;
import org.junit.Test;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;

import static org.junit.Assert.*;

/**
 * Unit testing
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {

    @Before
    public void setUp() {
        DataPoster.setEnabled(false);
    }

    @Test (timeout = 200)
    public void testMethod() {

    }
}
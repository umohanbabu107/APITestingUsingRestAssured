package testsForLogsGeneration;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.util.ServiceLoaderUtil
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class SampleTests {
    private static final Logger logger = LogManager.getLogger(SampleTests.class);

    // Passed tests
    @Test
    public void test1_Pass() {
        logger.info(" test1_Pass is started ");
        Assert.assertTrue(true, "Test 1 should pass.");
        logger.info(" test1_Pass is finished ");
    }

    @Test
    public void test2_Pass() {
        Assert.assertEquals(1, 1, "Test 2 should pass.");
    }

    @Test
    public void test3_Pass() {
        Assert.assertFalse(false, "Test 3 should pass.");
    }

    @Test
    public void test4_Pass() {
        Assert.assertNotNull("Selenium", "Test 4 should pass.");
    }

    @Test
    public void test5_Pass() {
        Assert.assertTrue(5 > 2, "Test 5 should pass.");
    }

    // Failed tests
    @Test
    public void test6_Fail() {
        Assert.assertEquals(2, 3, "Test 6 should fail.");
    }

    @Test
    public void test7_Fail() {
        Assert.assertTrue(false, "Test 7 should fail.");
    }

    @Test
    public void test8_Fail() {
        Assert.assertNull("NotNull", "Test 8 should fail.");
    }

    @Test
    public void test9_Fail() {
        Assert.assertFalse(true, "Test 9 should fail.");
    }

    @Test
    public void test10_Fail() {
        Assert.assertNotEquals("Hello", "Hello", "Test 10 should fail.");
    }

    // Skipped tests
    @Test
    public void test11_Skip() {
        throw new SkipException("Skipping Test 11.");
    }

    @Test
    public void test12_Skip() {
        throw new SkipException("Skipping Test 12.");
    }

    @Test
    public void test13_Skip() {
        throw new SkipException("Skipping Test 13.");
    }

    @Test
    public void test14_Skip() {
        throw new SkipException("Skipping Test 14.");
    }

    @Test
    public void test15_Skip() {
        throw new SkipException("Skipping Test 15.");
    }

    // Combination of Pass/Fail/Skip
    @Test
    public void test16_Pass() {
        Assert.assertTrue(true, "Test 16 should pass.");
    }

    @Test
    public void test17_Fail() {
        Assert.assertEquals("Automation", "Manual", "Test 17 should fail.");
    }

    @Test
    public void test18_Pass() {
        Assert.assertNotNull(10, "Test 18 should pass.");
    }

    @Test
    public void test19_Fail() {
        Assert.assertEquals(100, 200, "Test 19 should fail.");
    }

    @Test
    public void test20_Skip() {
        throw new SkipException("Skipping Test 20.");
    }
}

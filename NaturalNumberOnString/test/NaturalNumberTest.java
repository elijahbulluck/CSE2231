import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    @Test
    public final void testDefaultConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testConstructorInt() {
        /*
         * Set up variables and call method under test
         */
        int i = 4754;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testConstructorString() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testConstructorNN() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber forTest = this.constructorTest(8009);
        NaturalNumber n = this.constructorTest(forTest);
        NaturalNumber nExpected = this.constructorRef(forTest);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10IntZeroes() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10IntAddFromZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(5);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(5);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10Int() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(7302);
        NaturalNumber nExpected = this.constructorRef(73029);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10MaxInt() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);
        nExpected.multiplyBy10(0);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10StringZeroes() {
        /*
         * Set up variables and call method under test
         */
        String zero = "0";
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10StringAddFromZero() {
        /*
         * Set up variables and call method under test
         */
        String zero = "0";
        String five = "5";
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(five);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(5);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10String() {
        /*
         * Set up variables and call method under test
         */
        String begNum = "23452";
        String endNum = "234528";
        NaturalNumber n = this.constructorTest(begNum);
        NaturalNumber nExpected = this.constructorRef(endNum);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(8);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10StringMaxInt() {
        /*
         * Set up variables and call method under test
         */
        // make the first String max int and the second String
        //another digit longer to test the limits of nn
        String begNum = "2147483647";
        String endNum = "21474836479";
        NaturalNumber n = this.constructorTest(begNum);
        NaturalNumber nExpected = this.constructorRef(endNum);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10NNZeroes() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorTest();
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10NNAddFromZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorTest();
        NaturalNumber nine = this.constructorRef(9);
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(nine);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10NN() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber begNum = this.constructorTest(2468);
        NaturalNumber endNum = this.constructorRef(24680);
        NaturalNumber n = this.constructorTest(begNum);
        NaturalNumber nExpected = this.constructorRef(endNum);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10NNMaxInt() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber begNum = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber endNum = this.constructorRef(Integer.MAX_VALUE);
        endNum.multiplyBy10(0);
        NaturalNumber n = this.constructorTest(begNum);
        NaturalNumber nExpected = this.constructorRef(endNum);
        //use multiplyBy10 to match n with nExpected
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10IntsZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10IntsZeroRemainder() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(5);
        NaturalNumber nExpected = this.constructorRef(0);
        //use multiplyBy10 to match n with nExpected
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(5, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10Ints() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(5078);
        NaturalNumber nExpected = this.constructorRef(507);
        //use multiplyBy10 to match n with nExpected
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(8, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10StringZeroes() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10StringRem() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("6");
        NaturalNumber nExpected = this.constructorRef("0");
        //use multiplyBy10 to match n with nExpected
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(6, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10String() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("56789");
        NaturalNumber nExpected = this.constructorRef("5678");
        //use multiplyBy10 to match n with nExpected
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(9, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10NNZeroes() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorTest(0);
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10NNZeroRemainder() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber eight = this.constructorTest(8);
        NaturalNumber zero = this.constructorRef(0);
        NaturalNumber n = this.constructorTest(eight);
        NaturalNumber nExpected = this.constructorRef(zero);
        //use multiplyBy10 to match n with nExpected
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(8, remainder);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroIntsZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroIntsNonZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(6048);
        NaturalNumber nExpected = this.constructorRef(6048);
        /*
         * Assert that values of variables match expectations
         */
        assertFalse(n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroStringZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroStringNonZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("2422");
        NaturalNumber nExpected = this.constructorRef("2422");
        /*
         * Assert that values of variables match expectations
         */
        assertFalse(n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroNNZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorTest(0);
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        /*
         * Assert that values of variables match expectations
         */
        assertTrue(n.isZero());
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIsZeroNNNonZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber ten = this.constructorTest(10);
        NaturalNumber n = this.constructorTest(ten);
        NaturalNumber nExpected = this.constructorRef(ten);
        /*
         * Assert that values of variables match expectations
         */
        assertFalse(n.isZero());
        assertEquals(nExpected, n);
    }
}

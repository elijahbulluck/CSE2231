import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Elijah Bulluck
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value, hasKey, and size
    @Test
    public final void testConstructor() {
        Map<String, String> m = this.constructorTest();
        Map<String, String> m1 = this.constructorRef();
        assertEquals(m.size(), 0);
        assertEquals(m.size(), 0);
        assertEquals("{}", m.toString());
        assertEquals("{}", m1.toString());
        assertEquals(m, m1);
    }

    /*
     * Add a string to an empty map.
     */
    @Test
    public final void testAddEmpty() {
        Map<String, String> m = this.constructorTest();
        m.add("a", "b");
        Map<String, String> mExpected = this.createFromArgsTest("a", "b");
        assertEquals(m, mExpected);
    }

    /*
     * Add a string to a non-empty map.
     */
    @Test
    public final void testAddNonEmpty() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        m.add("e", "f");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f");
        assertEquals(m, mExpected);
    }

    /*
     * Remove a pair from a map to get an empty map.
     */
    @Test
    public final void testRemoveEmpty() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b");
        Pair<String, String> p = m.remove("a");
        Pair<String, String> p2 = mExpected.remove("a");
        assertEquals(p, p2);
        assertEquals("{}", m.toString());
        assertEquals(m, mExpected);
    }

    /*
     * Remove a string from a non-empty set.
     */
    @Test
    public final void testRemoveNonEmpty() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        Pair<String, String> p = m.remove("a");
        Map<String, String> mExpected = this.createFromArgsRef("c", "d", "e",
                "f");
        assertEquals("a", p.key());
        assertEquals("b", p.value());
        assertEquals(m, mExpected);
    }

    /*
     * Tests value.
     */
    @Test
    public final void testValue() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        String s = m.value("a");
        assertEquals("b", s);
    }

    /*
     * Tests value with multiple pairs.
     */
    @Test
    public final void testValueMulti() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        String s = m.value("c");
        assertEquals("d", s);
    }

    /*
     * Tests hasKey.
     */
    @Test
    public final void testHasKey() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        assertEquals(true, m.hasKey("a"));
    }

    /*
     * Tests hasKey.
     */
    @Test
    public final void testHasKeyMulti() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        assertEquals(true, m.hasKey("e"));
    }

    /*
     * Tests hasKey. (false
     */
    @Test
    public final void testHasKeyFalse() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        assertEquals(false, m.hasKey("g"));
    }

    /*
     * Remove any from a set.
     */
    @Test
    public final void testRemoveAny() {
        // 1. The Setup
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f");
        // 2. The Call
        Pair<String, String> p = m.removeAny();
        // 3. The Evaluation
        mExpected.remove(p.key());
        assertEquals(false, mExpected.hasKey(p.key()));
        assertEquals(m, mExpected);
    }

    /*
     * Test for length of empty map.
     */
    @Test
    public final void testLengthEmpty() {
        Map<String, String> m = this.constructorTest();
        int len = m.size();
        assertEquals("{}", m.toString());
        assertEquals(0, len);
    }

    /*
     * Test for length of non-empty map.
     */
    @Test
    public final void testSize() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        int len = m.size();
        assertEquals(3, len);
    }

}

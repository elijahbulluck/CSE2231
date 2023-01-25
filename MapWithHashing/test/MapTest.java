import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
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
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
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

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    @Test
    public final void testConstructor() {
        Map<String, String> s = this.constructorTest();
        Map<String, String> sExpected = this.constructorRef();
        assertEquals(s, sExpected);

    }

    @Test
    public final void testArgsConstructorEmpty() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();
        assertEquals(s.size(), sExpected.size());
        assertEquals("{}", s.toString());
        assertEquals("{}", sExpected.toString());
        assertEquals(s, sExpected);

    }

    @Test
    public final void testArgsConstructor() {
        Map<String, String> s = this.createFromArgsTest("a", "b");
        Map<String, String> sExpected = this.createFromArgsRef("a", "b");
        assertEquals(s.size(), sExpected.size());
        assertTrue(s.hasKey("a"));
        assertTrue(sExpected.hasKey("a"));
        assertEquals("b", s.value("a"));
        assertEquals("b", sExpected.value("a"));
        assertEquals(s, sExpected);

    }

    /**
     * Test for Empty add
     */
    @Test
    public final void testEmptyAdd() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef("1", "2");

        s.add("1", "2");
        boolean result = false;
        for (Pair<String, String> x : sExpected) {
            if (s.hasKey(x.key()) && s.hasValue(x.value())
                    && s.key(x.value()).equals(x.key())) {
                result = true;
            }
        }

        assertTrue(result);
    }

    /**
     * Test for add
     */
    @Test
    public final void testAdd1() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        Map<String, String> sExpected = this.createFromArgsRef("1", "2", "3",
                "4");

        s.add("3", "4");
        boolean result = false;
        for (Pair<String, String> x : sExpected) {
            if (s.hasKey(x.key()) && s.hasValue(x.value())
                    && s.key(x.value()).equals(x.key())) {
                result = true;
            }
        }
        assertTrue(result);
    }

    /**
     * Test for add
     */
    @Test
    public final void testAdd2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        m.add("e", "f");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f");
        assertEquals(m, mExpected);
    }

    /**
     * Test for Remove.
     */
    @Test
    public final void testRemove1() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> sExpected = this.createFromArgsRef("3", "4");

        s.remove("1");
        assertTrue(!s.hasKey("1") && s.equals(sExpected));

    }

    /**
     * Test for Remove.
     */
    @Test
    public final void testRemove2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        Pair<String, String> p = m.remove("a");
        Map<String, String> mExpected = this.createFromArgsRef("c", "d", "e",
                "f");
        assertEquals("a", p.key());
        assertEquals("b", p.value());
        assertEquals(m, mExpected);
    }

    /**
     * Test for RemoveToEmpty.
     */
    @Test
    public final void testRemoveToEmpty() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        Map<String, String> sExpected = this.createFromArgsRef();

        s.remove("1");
        assertTrue(!s.hasKey("1") && s.equals(sExpected));

    }

    /**
     * Test for RemoveAny.
     */
    @Test
    public final void testRemoveAny1() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        int len = s.size();
        s.removeAny();
        int newLen = s.size();
        assertTrue(newLen == len - 1);
    }

    /**
     * Test for RemoveAny.
     */
    @Test
    public final void testRemoveAny2() {
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

    /**
     * Test for RemoveAny.
     */
    @Test
    public final void testRemoveAnyEmpty() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        int len = s.size();
        s.removeAny();
        int newLen = s.size();
        assertTrue(newLen == len - 1);
    }

    /**
     * Test for Value.
     */
    @Test
    public final void testValue() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        String test = s.value("1");
        String test2 = s.value("3");
        assertTrue(test.equals("2") && test2.equals("4"));
    }

    /**
     * Test for Value.
     */
    @Test
    public final void testValueMulti() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        String s = m.value("c");
        assertEquals("d", s);
    }

    /**
     * Test for Value.
     */
    @Test
    public final void testValueSingle() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        String test = s.value("1");
        assertTrue(test.equals("2"));
    }

    /**
     *
     * Test for Has-key.
     */
    @Test
    public final void testHasKey() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        assertTrue(s.hasKey("1") && s.hasKey("3") && !s.hasKey("2"));
    }

    /**
     *
     * Test for Has-key.
     */
    @Test
    public final void testHasKey2() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        assertTrue(s.hasKey("1") && s.hasKey("3") && !s.hasKey("4"));
    }

    /**
     *
     * Test for Has-key.
     */
    @Test
    public final void testHasKeyFalse() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        assertFalse(s.hasKey("5"));
    }

    /**
     * Test for Size.
     */
    @Test
    public final void testSize() {
        Map<String, String> s = this.createFromArgsTest("1", "2", "3", "4");
        int sizeTest = s.size();
        int sizeRef = 2;
        assertEquals(sizeTest, sizeRef);
    }

    /**
     * Test for Size.
     */
    @Test
    public final void testSingleSize() {
        Map<String, String> s = this.createFromArgsTest("1", "2");
        int sizeTest = s.size();
        int sizeRef = 1;
        assertEquals(sizeTest, sizeRef);
    }

    /**
     * Test for Size.
     */
    @Test
    public final void testEmptySize() {
        Map<String, String> s = this.createFromArgsTest();
        int sizeTest = s.size();
        int sizeRef = 0;
        assertEquals(sizeTest, sizeRef);
    }

}

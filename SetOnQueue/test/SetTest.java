import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Elijah Bulluck
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    /*
     * Test of set constructor.
     */
    @Test
    public final void testConstructor() {
        Set<String> s = this.constructorTest();
        assertEquals(s.size(), 0);
        assertEquals("{}", s.toString());
    }

    /*
     * Add a string to an empty set.
     */
    @Test
    public final void testAddEmpty() {
        Set<String> s = this.constructorTest();
        s.add("a");
        assertEquals("{a}", s.toString());
    }

    /*
     * Add a string to a non-empty set.
     */
    @Test
    public final void testAddNonEmpty() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        s.add("e");
        Set<String> s2 = this.createFromArgsRef("a", "b", "c", "d", "e");
        assertEquals(s, s2);
    }

    /*
     * Remove a string from a set to get an empty set.
     */
    @Test
    public final void testRemoveEmpty() {
        Set<String> s = this.createFromArgsTest("a");
        String rem = s.remove("a");
        assertEquals("a", rem);
        assertEquals("{}", s.toString());
    }

    /*
     * Remove a string from a non-empty set.
     */
    @Test
    public final void testRemoveNonEmpty() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        String rem = s.remove("b");
        Set<String> s2 = this.createFromArgsRef("a", "c", "d");
        assertEquals("b", rem);
        assertEquals(s, s2);
    }

    /*
     * Tests contains for one item in a set.
     */
    @Test
    public final void testContainsOne() {
        Set<String> s = this.createFromArgsTest("a");
        assertEquals(true, s.contains("a"));
    }

    /*
     * Tests contains for multiple items in a set.
     */
    @Test
    public final void testContainsMulti() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        assertEquals(true, s.contains("c"));
    }

    /*
     * Tests when contains is false.
     */
    @Test
    public final void testDoesNotContain() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        assertEquals(false, s.contains("t"));
    }

    /*
     * Remove any from a set.
     */
    @Test
    public final void testRemoveAny() {
        // 1. The Setup
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d", "e", "f");
        Set<String> s2 = this.createFromArgsRef("a", "b", "c", "d", "e", "f");
        // 2. The Call
        String rem = s.removeAny();
        // 3. The Evaluation
        s2.remove(rem);
        assertEquals(false, s2.contains(rem));
        assertEquals(s, s2);
    }

    /*
     * Test for length of empty set.
     */
    @Test
    public final void testSizeEmpty() {
        Set<String> s = this.constructorTest();
        int len = s.size();
        assertEquals("{}", s.toString());
        assertEquals(0, len);
    }

    /*
     * Test for length of non-empty set.
     */
    @Test
    public final void testSize() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        int len = s.size();
        assertEquals(4, len);
    }

}

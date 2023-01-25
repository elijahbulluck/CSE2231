import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Elijah and Mati
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
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

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and
    // size
    /**
     * Test for constructor
     */
    @Test
    public final void constructor() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();

        assertEquals(sExpected, s);
    }

    /**
     * Tests add
     */
    @Test
    public final void testAddSize0() {

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("blue");

        s.add("blue");

        assertEquals(sExpected, s);

    }

    /**
     * Tests add
     */
    @Test
    public final void testAddSize1() {

        Set<String> s = this.createFromArgsTest("blue");
        Set<String> sExpected = this.createFromArgsRef("blue", "green");

        s.add("green");

        assertEquals(sExpected, s);

    }

    /**
     * Test for add.
     */
    @Test
    public final void testAdd2() {

        Set<String> s = this.createFromArgsTest("blue", "green");
        Set<String> sExpected = this.createFromArgsRef("blue", "green",
                "black");

        s.add("black");
        assertEquals(s, sExpected);
    }

    /**
     * Tests remove
     */
    @Test
    public final void testRemove0() {

        Set<String> s = this.createFromArgsTest("blue");
        Set<String> sExpected = this.createFromArgsRef();

        String removed = s.remove("blue");

        assertEquals(sExpected, s);
        assertEquals("blue", removed);

    }

    /**
     * Test for remove.
     */
    @Test
    public final void testRemove1() {

        Set<String> s = this.createFromArgsTest("blue", "green");
        Set<String> sExpected = this.createFromArgsRef("blue");

        s.remove("green");
        assertEquals(s, sExpected);
    }

    /**
     * Test for remove.
     */
    @Test
    public final void testRemove2() {

        Set<String> s = this.createFromArgsTest("blue", "green", "black");
        Set<String> sExpected = this.createFromArgsRef("blue", "black");
        String removed = s.remove("green");

        assertEquals(sExpected, s);
        assertEquals("green", removed);
    }

    /**
     * Tests removeAny,
     */
    @Test
    public final void testRemoveAny0() {

        Set<String> s = this.createFromArgsTest("blue");
        Set<String> sExpected = this.createFromArgsRef("blue");
        String sRemoved = s.removeAny();

        assertTrue(sExpected.contains(sRemoved));
        sExpected.remove(sRemoved);
        assertTrue(s.size() == sExpected.size());

    }

    /**
     * Tests removeAny
     */
    @Test
    public final void testRemoveAny1() {

        Set<String> s = this.createFromArgsTest("blue", "green");
        Set<String> sExpected = this.createFromArgsRef("blue", "green");
        String sRemoved = s.removeAny();

        assertTrue(sExpected.contains(sRemoved));
        sExpected.remove(sRemoved);
        assertTrue(s.size() == sExpected.size());

    }

    /**
     * Tests removeAny
     */
    @Test
    public final void testRemoveAny2() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("blue", "green", "black");
        Set<String> sExpected = this.createFromArgsRef("blue", "green",
                "black");
        String sRemoved = s.removeAny();

        assertTrue(sExpected.contains(sRemoved));
        sExpected.remove(sRemoved);
        assertTrue(s.size() == sExpected.size());

    }

    /**
     * Tests contains false.
     */
    @Test
    public final void testContainsFalse() {

        Set<String> s = this.createFromArgsTest("blue", "green", "black");
        Set<String> sExpected = this.createFromArgsRef("blue", "green",
                "black");

        boolean sContains = s.contains("yellow");
        boolean sEContains = s.contains("yellow");

        assertEquals(sExpected, s);
        assertEquals(sEContains, sContains);

    }

    /**
     * Tests contains false.
     */
    @Test
    public final void testContainsFalseEmpty() {

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        boolean sContains = s.contains("blue");
        boolean sEContains = s.contains("blue");

        assertEquals(sExpected, s);
        assertEquals(sEContains, sContains);

    }

    /**
     * Test for Contains.
     */
    @Test
    public final void testContainsTrue1() {

        Set<String> s = this.createFromArgsTest("green");
        Set<String> sExpected = this.createFromArgsRef("green");

        boolean sContains = s.contains("green");
        boolean sEContains = s.contains("green");

        assertEquals(sExpected, s);
        assertEquals(sEContains, sContains);

    }

    /**
     * Test for Contains.
     */
    @Test
    public final void testContainsTrue2() {
        /*
         * ,* Set up variables ,
         */
        Set<String> s = this.createFromArgsTest("black");
        Set<String> sExpected = this.createFromArgsRef("black");

        String color = "black";
        assertTrue(s.contains(color));
        assertTrue(sExpected.contains(color));

    }

    /**
     * Test for Contains.
     */
    @Test
    public final void testContainsTrue3() {
        /*
         * ,* Set up variables ,
         */
        Set<String> s = this.createFromArgsTest("blue", "green", "red");
        Set<String> sExpected = this.createFromArgsRef("blue", "green", "red");

        String color = "green";
        assertTrue(s.contains(color));
        assertTrue(sExpected.contains(color));

    }

    /**
     * Test for Contains.
     */
    @Test
    public final void testContainsFalse1() {

        Set<String> s = this.createFromArgsTest("blue", "green", "red");
        Set<String> sExpected = this.createFromArgsRef("blue", "green", "red");

        boolean containsColor = s.contains("yellow");

        assertEquals(sExpected, s);
        assertEquals(false, containsColor);

    }

    /**
     * Test for Contains.
     */
    @Test
    public final void testContainsFalse5() {

        Set<String> s = this.createFromArgsTest("blue", "green", "red");
        Set<String> sExpected = this.createFromArgsRef("blue", "green", "red");

        boolean containsColor = s.contains("red");

        assertEquals(sExpected, s);
        assertEquals(true, containsColor);

    }

    /**
     * Test for Size.
     */
    @Test
    public final void testSize0() {

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        int size1 = s.size();
        int size2 = sExpected.size();
        assertTrue(size1 == size2);
    }

    /**
     * Test for Size.
     */
    @Test
    public final void testSize1() {

        Set<String> s = this.createFromArgsTest("blue");
        Set<String> sExpected = this.createFromArgsRef("blue");

        int size1 = s.size();
        int size2 = sExpected.size();
        assertTrue(size1 == size2);
    }

    /**
     * Test for Size.
     */
    @Test
    public final void testSize2() {

        Set<String> s = this.createFromArgsTest("blue", "green", "red");
        Set<String> sExpected = this.createFromArgsRef("blue", "green", "red");

        int size1 = s.size();
        int size2 = sExpected.size();
        assertTrue(size1 == size2);
    }

    /**
     * Test for Size.
     */
    @Test
    public final void testSize3() {

        Set<String> s = this.createFromArgsTest("blue", "green", "red",
                "yellow", "black");
        Set<String> sExpected = this.createFromArgsRef("blue", "green", "red",
                "yellow", "black");

        int size1 = s.size();
        int size2 = sExpected.size();
        assertTrue(size1 == size2);
    }

}

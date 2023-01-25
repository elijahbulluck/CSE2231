import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Elijah Bulluck
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    // TODO - add test cases for constructor, add, remove, and length
    @Test
    public final void testConstructor() {
        Sequence<String> s = this.constructorTest();
        assertEquals(s.length(), 0);
        assertEquals("<>", s.toString());
    }

    /*
     * Add a string to an empty sequence.
     */
    @Test
    public final void testAddEmpty() {
        Sequence<String> s = this.constructorTest();
        s.add(0, "a");
        assertEquals("<a>", s.toString());

    }

    /*
     * Add a string to a non-empty sequence.
     */
    @Test
    public final void testAddNonEmpty() {
        Sequence<String> s = this.createFromArgsTest("a,b,c,d");
        s.add(0, "e");
        assertEquals("<e,a,b,c,d>", s.toString());
    }

    /*
     * Remove a string from a sequence to get an empty sequence.
     */
    @Test
    public final void testRemoveEmpty() {
        Sequence<String> s = this.createFromArgsTest("a");
        String rem = s.remove(0);
        assertEquals("a", rem);
        assertEquals("<>", s.toString());
    }

    /*
     * Remove a string from a non-empty sequence.
     */
    @Test
    public final void testRemoveNonEmpty() {
        Sequence<String> s = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        String rem = s.remove(4);
        assertEquals("e", rem);
        assertEquals("<a,b,c,d,f>", s.toString());
    }

    /*
     * Test for length of empty sequence
     */
    @Test
    public final void testLengthEmpty() {
        Sequence<String> s = this.constructorTest();
        int len = s.length();
        assertEquals("<>", s.toString());
        assertEquals(0, len);
    }

    /*
     * Test for length of non-empty sequence
     */
    @Test
    public final void testLength() {
        Sequence<String> s = this.constructorTest();
        s.add(0, "a");
        s.add(1, "b");
        s.add(2, "c");
        int len = s.length();
        assertEquals("<a,b,c>", s.toString());
        assertEquals(3, len);
    }

}

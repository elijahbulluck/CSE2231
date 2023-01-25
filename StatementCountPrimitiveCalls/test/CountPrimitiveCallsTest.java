import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code CountPrimitiveCalls}'s static method
 * countOfPrimitiveCalls.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCallsTest {

    /**
     * Creates and returns a {@code Statement} constructed from a given input
     * file.
     *
     * @param fileName
     *            the name of the file containing the statement
     * @param block
     *            flag to indicate whether to read an entire BLOCK (sequence of
     *            statements) or a single statement
     * @return the constructed statement
     * @requires <pre>
     * [fileName is the name of a file containing zero, one, or more
     *  valid BL statements]
     * </pre>
     * @ensures createFromArgs = [statement(s) from file fileName]
     */
    private Statement createFromArgs(String fileName, boolean block) {
        SimpleReader in = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(in);
        in.close();
        Statement s = new Statement1();
        if (block) {
            s.parseBlock(tokens);
        } else {
            s.parse(tokens);
        }
        return s;
    }

    @Test
    public void test1true() {
        Statement s1 = this.createFromArgs("data/test1.bl", true);
        Statement s2 = this.createFromArgs("data/test1.bl", true);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        assertEquals(25, count);
        assertEquals(s2, s1);
    }

    // TODO: add other (smaller) test cases as needed
    @Test
    public void test1false() {
        Statement s1 = this.createFromArgs("data/test1.bl", false);
        Statement s2 = this.createFromArgs("data/test1.bl", false);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        //this will only read a single statement, so should be 1 ("move" at
        //the beginning)
        assertEquals(1, count);
        assertEquals(s2, s1);
    }

    @Test
    public void testWhileTrue() {
        Statement s1 = this.createFromArgs("data/while.bl", true);
        Statement s2 = this.createFromArgs("data/while.bl", true);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        assertEquals(2, count);
        assertEquals(s2, s1);
    }

    @Test
    public void testWhileFalse() {
        Statement s1 = this.createFromArgs("data/while.bl", false);
        Statement s2 = this.createFromArgs("data/while.bl", false);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        //this file only has 1 statement, so answer should be the same as true
        assertEquals(2, count);
        assertEquals(s2, s1);
    }

    @Test
    public void blCodeTestTrue() {
        Statement s1 = this.createFromArgs("data/BLCode.bl", true);
        Statement s2 = this.createFromArgs("data/BLCode.bl", true);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        assertEquals(4, count);
        assertEquals(s2, s1);
    }

    @Test
    public void blCodeTestFalse() {
        Statement s1 = this.createFromArgs("data/BLCode.bl", false);
        Statement s2 = this.createFromArgs("data/BLCode.bl", false);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        //this one will only read 1 statement, so answer should be 1
        assertEquals(1, count);
        assertEquals(s2, s1);
    }

    @Test
    public void ifElseTest() {
        Statement s1 = this.createFromArgs("data/ifelse.bl", false);
        Statement s2 = this.createFromArgs("data/ifelse.bl", false);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        //this one will only read 1 statement, so should be 1
        assertEquals(1, count);
        assertEquals(s2, s1);
    }

    @Test
    public void ifElseTestTrue() {
        Statement s1 = this.createFromArgs("data/ifelse.bl", true);
        Statement s2 = this.createFromArgs("data/ifelse.bl", true);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        assertEquals(5, count);
        assertEquals(s2, s1);
    }

    @Test
    public void ifTestTrue() {
        Statement s1 = this.createFromArgs("data/if.bl", true);
        Statement s2 = this.createFromArgs("data/if.bl", true);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        assertEquals(4, count);
        assertEquals(s2, s1);
    }

    @Test
    public void ifTestFalse() {
        Statement s1 = this.createFromArgs("data/ifelse.bl", false);
        Statement s2 = this.createFromArgs("data/ifelse.bl", false);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        //should be 1 because of the instruction outside of if block
        assertEquals(1, count);
        assertEquals(s2, s1);
    }

}

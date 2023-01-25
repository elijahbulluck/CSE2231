import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;

/**
 * JUnit test fixture for {@code BinarySearchTreeMethods}'s static methods
 * isInTree (and removeSmallest).
 */
public final class BinarySearchTreeMethodsTest {

    /**
     * Constructs and return a BST created by inserting the given {@code args}
     * into an empty tree in the order in which they are provided.
     *
     * @param args
     *            the {@code String}s to be inserted in the tree
     * @return the BST with the given {@code String}s
     * @requires [the Strings in args are all distinct]
     * @ensures createBSTFromArgs = [the BST with the given Strings]
     */
    private static BinaryTree<String> createBSTFromArgs(String... args) {
        BinaryTree<String> t = new BinaryTree1<String>();
        for (String s : args) {
            BinaryTreeUtility.insertInTree(t, s);
        }
        return t;
    }

    @Test
    public void sampleTest() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    // TODO: add here other test cases for BinarySearchTreeMethods.isInTree
    // (and for BinarySearchTreeMethods.removeSmallest)

    @Test
    public void inTreeTrue() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c", "d");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c", "d");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void inTreeFalse() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("3", "4", "2", "6");
        BinaryTree<String> t2 = createBSTFromArgs("3", "4", "2", "6");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "7");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void inTreeTrue1() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("a");
        BinaryTree<String> t2 = createBSTFromArgs("a");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void inTreeFalse1() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("a");
        BinaryTree<String> t2 = createBSTFromArgs("a");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "f");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void inTreeFalseEmpty() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("");
        BinaryTree<String> t2 = createBSTFromArgs("");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void removeSmallestTest() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("d", "c", "b", "a");
        BinaryTree<String> t2 = createBSTFromArgs("d", "c", "b");
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("a", smallest);
        assertEquals(t2, t1);
    }

    @Test
    public void removeSmallestTest1() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("d");
        BinaryTree<String> t2 = createBSTFromArgs();
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("d", smallest);
        assertEquals(t2, t1);
    }

    @Test
    public void removeSmallestTest2() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("e", "j", "h", "g");
        BinaryTree<String> t2 = createBSTFromArgs("j", "h", "g");
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("e", smallest);
        assertEquals(t2, t1);
    }

    @Test
    public void removeSmallestTest3() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("a", "b", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "c");
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("a", smallest);
        assertEquals(t2, t1);
    }

}

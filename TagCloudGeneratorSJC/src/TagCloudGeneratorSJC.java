import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The program shall ask the user for the name of an input file, for the name of
 * an output file, and for the number of words to be included in the generated
 * tag cloud (a positive integer, say N). The program shall respect the user
 * input as being the complete relative or absolute path as the name of the
 * input file, or the name of the output file, and will not augment the given
 * path in any way, e.g, it will not supply its own filename extension. For
 * example, a reasonable user response for the name of the input file could
 * directly result in the String value "data/importance.txt"; similarly, a
 * reasonable user response for the name of the output file could directly
 * result in the String value "data/importance.html". The output shall be a
 * single well-formed HTML file displaying the name of the input file in a
 * heading followed by a tag cloud of the N words with the highest count in the
 * input. The words shall appear in alphabetical order.
 *
 * @author Mati and Elijah
 */

public final class TagCloudGeneratorSJC {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGeneratorSJC() {
    }

    /**
     * String containing list of separators.
     */
    private static final String SEPARATORS = "'., ()-_?\"/!@#$%^&*\t1234567890:"
            + ";[]{}+=~`><";

    /**
     * Set up maximum recurrence for the words.
     */
    private static int maxCount = 1;

    /**
     * Set up minimum recurrence for the words.
     */
    private static int minCount = 1;

    /**
     * Set up maximum font size for a word.
     */
    private static final int maxFontSize = 48;

    /**
     * Set up minimum font size for a word.
     */
    private static final int minFontSize = 11;

    /**
     * Compare {@code Map.Pair<String, Integer>}s in alphabetical order
     * according to key value.
     */
    @SuppressWarnings("serial")
    private static class alphabetSort
            implements Serializable, Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {

            String s1 = o1.getKey();
            String s2 = o2.getKey();
            int compare = s1.compareToIgnoreCase(s2);

            if (compare == 0) {
                compare = s1.compareTo(s2);
            }

            return compare;
        }
    }

    /**
     * Compare {@code Map.Pair<String, Integer>}s by decreasing order by value.
     */
    @SuppressWarnings("serial")
    private static class numericalSort
            implements Serializable, Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {

            Integer i1 = o1.getValue();
            Integer i2 = o2.getValue();
            int compare = i2.compareTo(i1);

            if (compare == 0) {
                compare = o1.getKey().compareTo(o2.getKey());
            }

            return compare;
        }
    }

    /**
     * Outputs the opening tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     * @param inFile
     *            the name of the file to read from
     * @param numWords
     *            the number of words with the highest word counts to be
     *            displayed
     * @updates {@code out.content}
     * @requires
     *
     *           <pre>
     * {@code out.is_open and [inFile is not null]}
     *           </pre>
     *
     * @ensures
     *
     *          <pre>
     * {@code out.content = #out.content * [the HTML opening tags]}
     *          </pre>
     */
    private static void outputHTMLHeader(PrintWriter out, String inFile,
            int numWords) {
        assert out != null : "Violation of: out is not null";
        assert inFile != null : "Violation of: inFile is not null";
        /*
         * Output the index header HTML text.
         */
        out.println("<html>");
        out.println("<head>");
        out.println(
                "<title>Top " + numWords + " words in " + inFile + "</title>");
        out.println(
                "<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + numWords + " words in " + inFile + "</h2>");
        out.println("<hr>");
        out.println("<div class = \"cdiv\">");
        out.println("<p class = \"cbox\">");
    }

    /**
     * Returns the first "word" or "separator string" in the given {@code text}
     * starting at the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param pos
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires
     *
     *           <pre>
     * {@code 0 <= position < |text|}
     *           </pre>
     *
     * @ensures
     *
     *          <pre>
     * {@code nextWord =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)}
     *          </pre>
     */
    private static String nextWordOrSeparator(String text, int pos,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= pos : "Violation of: 0 <= position";
        assert pos < text.length() : "Violation of: position < |text|";

        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= pos : "Violation of: 0 <= position";
        assert pos < text.length() : "Violation of: position < |text|";

        boolean isSep = separators.contains(text.charAt(pos));

        int nextPos = pos + 1;
        while (nextPos < text.length()
                && (separators.contains(text.charAt(nextPos)) == isSep)) {

            nextPos++;

        }
        return text.substring(pos, nextPos);
    }

    /**
     * Creates a List of the entries in countWords sorted by their word counts.
     *
     * @clears countWords
     * @param countWords
     *            the Map of words and their counts to sort
     * @return a list of the Map.Entrys from countWords sorted in descending
     *         order by their word counts
     * @ensures countSort contains all of the Map.Entrys from #countWords
     *
     */
    private static List<Map.Entry<String, Integer>> countSort(
            Map<String, Integer> countWords) {
        List<Map.Entry<String, Integer>> countSort = new ArrayList<Map.Entry<String, Integer>>();
        Set<Map.Entry<String, Integer>> countW = countWords.entrySet();
        Iterator<Map.Entry<String, Integer>> itr = countW.iterator();

        while (itr.hasNext()) {
            Map.Entry<String, Integer> current = itr.next();
            itr.remove();
            countSort.add(current);
        }
        Comparator<Map.Entry<String, Integer>> order = new numericalSort();
        countSort.sort(order);
        return countSort;

    }

    /**
     * Reads file read by SimpleReader text word as a key of wordCounts and the
     * number of times it appears as its value.
     *
     *
     * @param wordCounts
     *            the Map to hold each word and its count
     * @param text
     *            the SimpleReader connected to the file whose words and counts
     *            are to be recorded
     * @param separators
     *            the Set of characters defined as separating words
     * @replaces wordCounts
     * @requires text.is_open
     * @ensures
     *
     *          <pre>
     * each key in wordCounts is a unique word as defined by separators
     *               from text.content and
     *               each unique word from text.content is a key in wordCounts and
     *               each key's value is the # of times it appears in in.content and
     *               text.is_open
     *          </pre>
     *
     * @throws IOException
     *             if an I/O exception occurs
     */
    private static void countWord(Map<String, Integer> wordCounts,
            Set<Character> separators, BufferedReader text) throws IOException {
        wordCounts.clear();
        String firstLine = text.readLine();
        while (firstLine != null) {
            firstLine = firstLine.toLowerCase();
            int linePos = 0;
            while (linePos < firstLine.length()) {
                String words = nextWordOrSeparator(firstLine, linePos,
                        separators);
                if (!separators.contains(words.charAt(0))) {
                    if (!wordCounts.containsKey(words)) {
                        wordCounts.put(words, 1);
                    } else {
                        int count = wordCounts.get(words) + 1;
                        wordCounts.remove(words);
                        wordCounts.put(words, count);
                    }
                }
                linePos += words.length();

            }
            firstLine = text.readLine();
        }

    }

    /**
     * 
     * 
     * @param maxSize
     *            the maximum count in the tag cloud words
     *
     * @param minSzie
     *            the minimum count in the tag cloud words
     *
     * @param count
     *            the count of the word whose font size is to be calculated
     *
     * @return the font size of the word
     **/
    private static String fontSize(int maxSize, int minSize, int count) {
        int fontSize = maxFontSize - minFontSize;
        fontSize *= (count - minSize);
        int subSize = maxSize - minSize;
        if (subSize != 0) {
            fontSize /= subSize;
        } else {
            fontSize = minFontSize;
        }
        fontSize += minFontSize;

        return "f" + fontSize;
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @return set of characters equal to entries(str)
     * @ensures generateElements = entries(str)
     **/
    private static Set<Character> generateElements(String str) {
        Set<Character> elements = new HashSet<Character>();
        for (int i = 0; i < str.length(); i++) {
            if (!elements.contains(str.charAt(i))) {
                elements.add(str.charAt(i));
            }
        }
        return elements;

    }

    /**
     * Outputs the HTML for a total of numWords words from wordCounts with the
     * highest counts in alphabetical order for a CSS tag cloud.
     *
     * @param output
     *            the file to print the top words to
     * @param numWords
     *            the number of top words to print
     * @param wordCounts
     *            the map of words and their counts
     * @clears wordCounts
     * @ensures output.content = #output.content * numWords many top words from
     *          wordCounts and output.is_open
     */
    private static void outputBlock(PrintWriter output, int numWords,
            Map<String, Integer> wordCounts) {

        List<Map.Entry<String, Integer>> count = countSort(wordCounts);
        List<Map.Entry<String, Integer>> top = new ArrayList<Map.Entry<String, Integer>>();
        while (count.size() > 0 && top.size() < numWords) {
            Map.Entry<String, Integer> topWord = count.get(0);
            count.remove(0);
            top.add(topWord);

        }

        if (top.size() > 0) {
            minCount = top.get(top.size() - 1).getValue();
            maxCount = top.get(0).getValue();
        }
        Comparator<Map.Entry<String, Integer>> order = new alphabetSort();
        top.sort(order);
        for (Map.Entry<String, Integer> wordCount : top) {
            String fontSize = fontSize(maxCount, minCount,
                    wordCount.getValue());
            String tag = "<span style=\"cursor:default\" class=\"" + fontSize
                    + "\" title=\"count: " + wordCount.getValue() + "\">"
                    + wordCount.getKey() + "</span>";
            output.println(tag);
        }

    }

    /**
     * Output the ending tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     * @ensures html file has ending tags
     */
    private static void outputFooter(PrintWriter output) {

        output.println("</p>");
        output.println("</div>");
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        BufferedReader bInput = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("Enter name of the input file: ");
        String inputFile = "";
        BufferedReader input = null;
        while (input == null) {
            try {
                inputFile = bInput.readLine();
                input = new BufferedReader(new FileReader(inputFile));
            } catch (IOException e) {
                System.out.print(e + " Invalid Input File: ");
            }
        }

        String file = inputFile;
        System.out.print("Enter the name of the output file: ");
        String outFile = "";
        PrintWriter out = null;
        while (out == null) {
            try {
                outFile = bInput.readLine();
                out = new PrintWriter(
                        new BufferedWriter(new FileWriter(outFile)));
            } catch (IOException e) {
                System.out.print(e + " Invalid Output File: ");

            }
        }

        System.out.print("Enter number of words(N): ");
        int numWords = 0;
        boolean flag = true;
        while (flag) {
            try {
                numWords = Integer.parseInt(bInput.readLine());
                if (numWords > 0) {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: number format incorrect");
                System.out.print("Enter number of words(N): ");
            } catch (IOException e) {
                System.out.println("Error: Input cant be read");
                System.out.print("Enter number of words(N): ");
            }
            if (numWords < 0) {
                System.out.print(
                        "Enter number  greater than or equal 0, words(N): ");
                numWords = 0;
            }
        }

        outputHTMLHeader(out, file, numWords);
        Map<String, Integer> wordCounts = new HashMap<String, Integer>();
        try {
            countWord(wordCounts, generateElements(SEPARATORS), input);
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        outputBlock(out, numWords, wordCounts);
        outputFooter(out);

        out.close();
        try {
            bInput.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }
        System.out.print("Program Successed");
    }

}
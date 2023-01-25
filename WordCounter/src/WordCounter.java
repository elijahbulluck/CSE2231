import java.util.Arrays;
import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * A program that gets a text file from the user and outputs an html file with a
 * table full of words and their counts.
 *
 * @author Elijah Bulluck
 *
 */

public class WordCounter {

    /**
     * Compare {@code String}s in lexicographic order, also ignoring capital
     * letters.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            //use toLowerCase so case is ignored
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    }

    //using nextWordOrSeparator from SWI
    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
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
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int counter = 0;
        String maxWoS = "";
        char c = 'a';
        if (separators.contains(text.charAt(position))) {
            while (counter < text.substring(position, text.length()).length()) {
                c = text.charAt(counter + position);
                if (separators.contains(text.charAt(position + counter))) {
                    maxWoS = maxWoS + c;
                    counter++;
                } else {
                    counter = text.substring(position, text.length()).length();
                }

            }
            counter = 0;
        } else {
            if (!separators.contains(text.charAt(position))) {
                while (counter < text.substring(position, text.length())
                        .length()) {
                    c = text.charAt(counter + position);
                    if (!separators.contains(text.charAt(position + counter))) {
                        maxWoS = maxWoS + c;
                        counter++;
                    } else {
                        counter = text.substring(position, text.length())
                                .length();
                    }
                }
            }
            counter = 0;
        }
        return maxWoS;

    }

    //generateElements from SWI
    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        charSet.clear();
        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                charSet.add(str.charAt(i));
            }
        }

    }

    /**
     * Creates an array of the keys from map and sorts them alphabetically.
     *
     * @param m
     *            map that will be used for arr
     *
     * @return a new, alphabetically sorted array
     * @ensures arr is in alphabetical order
     */
    public static String[] createArr(Map<String, Integer> m) {
        String[] arr = new String[m.size()];
        Map<String, Integer> temp = m.newInstance();
        temp.transferFrom(m);
        int count = 0;
        //use example from class of safe map loop
        while (temp.size() > 0) {
            Map.Pair<String, Integer> p = temp.removeAny();
            //set position in array equal to key value
            arr[count] = p.key();
            //add the key and value back to the map
            m.add(p.key(), p.value());
            count++;
        }
        //sort array into alphabetical order using Arrays.sort
        Arrays.sort(arr, new StringLT());
        return arr;
    }

    /**
     * Updates given map to alphabetical order.
     *
     * @param arr
     *            given alphabetical arr
     *
     * @param m
     *            map that will be updated
     * @param s
     *            set of separators to be used in nextWordOrSeparator
     * @updates m
     * @ensures map is in alphabetical order
     */
    private static void updateMapAlphabetically(String[] arr,
            Map<String, Integer> m) {
        //update map to alphabetical order
        Map<String, Integer> copy = new Map1L<String, Integer>();
        for (int i = 0; i < arr.length; i++) {
            copy.add(arr[i], m.value(arr[i]));
        }
        m.transferFrom(copy);
    }

    /**
     * Creates a map from the words in the text file
     *
     * @param file
     *            the input text file
     * @param m
     *            an empty map that will end with having all of the words
     * 
     * @updates m
     *
     * @ensures <pre>
     * file's words = m's keys
     * </pre>
     */
    public static void createMap(SimpleReader file, Map<String, Integer> m) {
        assert m != null : "Violation of: m is not null";
        int pos = 0;
        Set<Character> specialChars = new Set1L<Character>();
        //Characters that are considering separators
        String chars = " \t~`!@#$%^&*()-_+={}[]|;:'<>,.?/";
        //create a set of the special characters
        generateElements(chars, specialChars);
        while (!file.atEOS()) {
            String line = file.nextLine();
            //reset position on line for each line
            pos = 0;
            while (pos < line.length()) {
                //find the next character or word
                String charOrWord = nextWordOrSeparator(line, pos,
                        specialChars);
                //first we have to check if the string is a word
                if (!specialChars.contains(charOrWord.charAt(0))) {
                    //add to map if it's a word that hasnt been seen yet
                    if (!m.hasKey(charOrWord)) {
                        m.add(charOrWord, 1);
                    }
                    //if it is there, increase the value
                    else {
                        int val = m.value(charOrWord);
                        val++;
                        //now replace the value that was associated before
                        m.replaceValue(charOrWord, val);
                    }
                }
                //Increase position to consider the next word or separator in the line.
                pos = pos + charOrWord.length();
            }
        }
    }

    /**
     * Creates an HTML page with a table based on the map as a parameter.
     *
     * @param output
     *            the file this is being output to
     * 
     * @param m
     *            the map that has all the words and counts
     *
     * @param arr
     *            the array that has all of the words in alphabetical order
     * @param input
     *            the text file input
     * @clears m
     * @ensures <pre>
     * {HTML table elements = m}
     * </pre>
     *
     */
    public static void createTable(SimpleWriter output, SimpleReader input,
            Map<String, Integer> m, String[] arr) {
        assert m != null : "Violation of: m is not null";
        assert arr != null : "Violation of: arr is not null";
        output.println("<html>");
        output.println("<head>");
        output.println("<title>Words Counted in " + input.name() + "</title>");
        output.println("<body>");
        output.println("<h2>Words Counted in " + input.name() + "</h2>");
        output.println("<hr />");
        //now create the table
        output.println("<table border=\"1\">");
        output.println("<tr>");
        output.println("<th>Words</th>");
        output.println("<th>Counts</th>");
        output.println("</tr>");
        //Loops through the Map, writing out the Keys and its respective Values
        int pos = 0;
        while (m.size() > 0) {
            Pair<String, Integer> p = m.remove(arr[pos]);
            output.println("<tr>");
            output.println("<td>" + p.key() + "</td>");
            output.println("<td>" + p.value() + "</td>");
            output.println("</tr>");
            pos++;
        }
        output.println("</table>");
        output.println("</body>");
        output.println("</html>");

    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        out.println("Enter the name of the input file: ");
        String inName = in.nextLine();
        SimpleReader inFile = new SimpleReader1L("data/" + inName);
        out.println("Enter the name of the output file: ");
        //Creates output file, stored in data folder
        SimpleWriter outFile = new SimpleWriter1L("data/" + in.nextLine());
        Map<String, Integer> wordMap = new Map1L<String, Integer>();
        createMap(inFile, wordMap);
        String[] arr = createArr(wordMap);
        updateMapAlphabetically(arr, wordMap);
        createTable(outFile, inFile, wordMap, arr);
        out.print("Finished!");
        in.close();
        out.close();
        inFile.close();
        outFile.close();

    }

}

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
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;
import components.utilities.FormatChecker;
import components.utilities.Reporter;

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
public final class TagCloud {
	/**
	 * Default constructor--private to prevent instantiation.
	 */
	private TagCloud() {
		// no code needed here
	}

	/**
	 * String containing list of separators.
	 */
	private static final String SEPARATORS = "'., ()-_?\"/!@#$%^&*\t1234567890:" + ";[]{}+=~`><";

	/**
	 * Set up maximum recurrence for the words.
	 */
	private static int max = 0;

	/**
	 * Set up minimum recurrence for the words.
	 */
	private static int min = 0;

	/**
	 * Set up maximum font size for a word.
	 */
	private static final int maxFontSize = 48;

	/**
	 * Set up minimum font size for a word.
	 */
	private static final int minFontSize = 11;

	/**
	 * Compare {@code Map.Pair<String, Integer>}s in alphabetical order according to
	 * key value..
	 */
	private static class alphabetSort implements Comparator<Map.Pair<String, Integer>> {
		@Override
		public int compare(Map.Pair<String, Integer> o1, Map.Pair<String, Integer> o2) {

			String s1 = o1.key();
			String s2 = o2.key();

			return s1.compareToIgnoreCase(s2);
		}
	}

	/**
	 * Compare {@code Map.Pair<String, Integer>}s by decreasing order by value.
	 */
	private static class numericalSort implements Comparator<Map.Pair<String, Integer>> {
		@Override
		public int compare(Map.Pair<String, Integer> o1, Map.Pair<String, Integer> o2) {

			Integer i1 = o1.value();
			Integer i2 = o2.value();
			return i2.compareTo(i1);
		}
	}

	/**
	 * Returns the first "word" or "separator string" in the given {@code text}
	 * starting at the given {@code position}.
	 *
	 * @param text       the {@code String} from which to get the word or separator
	 *                   string
	 * @param pos        the starting index
	 * @param separators the {@code Set} of separator characters
	 * @return the first word or separator string found in {@code text} starting at
	 *         index {@code position}
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
	private static String nextWordOrSeparator(String text, int pos, Set<Character> separators) {
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
		while (nextPos < text.length() && (separators.contains(text.charAt(nextPos)) == isSep)) {

			nextPos++;

		}
		return text.substring(pos, nextPos);
	}

	/**
	 * Outputs the opening tags in the generated HTML file.
	 *
	 * @param out      the output stream
	 * @param inFile   the name of the file to read from
	 * @param numWords the number of words with the highest word counts to be
	 *                 displayed
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
	private static void outputHTMLHeader(SimpleWriter out, String inFile, int numWords) {
		assert out != null : "Violation of: out is not null";
		assert out.isOpen() : "Violation of: out.is_open";
		assert inFile != null : "Violation of: inFile is not null";
		/*
		 * Output the index header HTML text.
		 */
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Top " + numWords + " words in " + inFile + "</title>");
		out.println("<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>Top " + numWords + " words in " + inFile + "</h2>");
		out.println("<hr>");
		out.println("<div class = \"cdiv\">");
		out.println("<p class = \"cbox\">");
	}

	/**
	 * Counts the recurrence of each word in a given input file {@code file} and
	 * puts each word and its corresponding count into a map {@code wordcount}.
	 *
	 * @param wordCounts the Map to hold each word and its count
	 * @param text       the SimpleReader connected to the file whose words and
	 *                   counts are to be recorded
	 * @param separators the Set of characters defined as separating words
	 * @replaces wordCounts
	 * @requires text.is_open
	 * @ensures
	 *
	 *          <pre>
	 * each key in wordCounts is a unique word as defined by separators
	 * from text.content and each unique word from text.content is a key in wordCounts and
	 * each key's value is the number of times it appears in content and text
	 *          </pre>
	 */
	private static void countWord(Map<String, Integer> wordCounts, Set<Character> separators, SimpleReader text) {
		wordCounts.clear();
		while (!text.atEOS()) {
			String firstIndex = text.nextLine().toLowerCase();
			int listLine = 0;
			while (listLine < firstIndex.length()) {
				String word = nextWordOrSeparator(firstIndex, listLine, separators);
				if (!separators.contains(word.charAt(0))) {
					if (!wordCounts.hasKey(word)) {
						wordCounts.add(word, 1);
					} else {
						int newCount = wordCounts.value(word) + 1;
						wordCounts.remove(word);
						wordCounts.add(word, newCount);
					}
				}
				listLine += word.length();

			}
		}

	}

	/**
	 * Output the ending tags in the generated HTML file.
	 *
	 * @param out the output stream
	 */
	private static void outputFooter(SimpleWriter output) {
		assert output != null : "Violation of: out is not null";
		assert output.isOpen() : "Violation of: out.is_open";

		output.println("</p>");
		output.println("</div>");
		output.println("</body>");
		output.println("</html>");
	}

	/**
	 * Generates the set of characters in the given {@code String} into the given
	 * {@code Set}.
	 *
	 * @param str the given {@code String}
	 * @return set of characters equal to entries(str)
	 * @ensures generateElements = entries(str)
	 **/
	private static Set<Character> generateElements(String str) {
		Set<Character> elements = new Set1L<Character>();
		for (int i = 0; i < str.length(); i++) {
			if (!elements.contains(str.charAt(i))) {
				elements.add(str.charAt(i));
			}
		}
		return elements;

	}

	/*
	 * @param maxSize the maximum count in the tag cloud words
	 *
	 * @param minSzie the minimum count in the tag cloud words
	 *
	 * @param count the count of the word whose font size is to be calculated
	 *
	 * @return the font size of the word
	 */
	private static String fontSize(int maxSize, int minSize, int count) {
		int fontSize = maxFontSize - minFontSize;
		fontSize *= (count - minSize);
		int subSize = maxSize - minSize;
		if (subSize != 0) {
			fontSize /= subSize;
		} else {
			fontSize = 0;
		}
		fontSize += minFontSize;

		return "f" + fontSize;

	}

	/**
	 * @param output     the output file
	 * @param wordCounts the sorting machine of words and their recurrence
	 * @param numWords   the amount of words to be put into the output {@code out}
	 * @updates out
	 */
	private static void outputBlock(SimpleWriter output, int numWords, Map<String, Integer> wordCounts) {

		Comparator<Pair<String, Integer>> order = new numericalSort();
		SortingMachine<Map.Pair<String, Integer>> sort;
		sort = new SortingMachine2<Map.Pair<String, Integer>>(order);
		while (wordCounts.size() > 0) {
			sort.add(wordCounts.removeAny());
		}
		sort.changeToExtractionMode();

		Comparator<Pair<String, Integer>> alphbatical = new alphabetSort();
		SortingMachine<Map.Pair<String, Integer>> alphbaticalSort;
		alphbaticalSort = new SortingMachine2<Map.Pair<String, Integer>>(alphbatical);

		if (sort.size() > 0) {
			Map.Pair<String, Integer> maxMapPair = sort.removeFirst();
			max = maxMapPair.value();
			alphbaticalSort.add(maxMapPair);
		}
		int counter = 0;
		while (counter < numWords && sort.size() > 1) {
			Map.Pair<String, Integer> wordCount = sort.removeFirst();
			alphbaticalSort.add(wordCount);
			counter++;
		}

		if (sort.size() > 0) {
			Map.Pair<String, Integer> minMapPair = sort.removeFirst();
			min = minMapPair.value();
			alphbaticalSort.add(minMapPair);

		}
		alphbaticalSort.changeToExtractionMode();
		while (alphbaticalSort.size() > 0) {
			Map.Pair<String, Integer> wordCount = alphbaticalSort.removeFirst();
			String fontSize = fontSize(max, min, wordCount.value());
			String tag = "<span style=\"cursor:default\" class=\"" + fontSize + "\" title=\"count: " + wordCount.value()
					+ "\">" + wordCount.key() + "</span>";
			output.println(tag);
		}
	}

	/**
	 * Main method.
	 *
	 * @param args the command line arguments; unused here
	 */
	public static void main(String[] args) {
		SimpleReader in = new SimpleReader1L();
		SimpleWriter out = new SimpleWriter1L();

		// Get the input file's name and the name of the file to write to.
		out.print("Enter the name of the input text file: ");
		String inputFile = in.nextLine();
		out.print("Enter the name of the output file: ");
		String outputFile = in.nextLine();
		out.print("Enter number of words to be included in the generated tag cloud: ");

		String wordNumStr = in.nextLine();
		boolean validNumber = FormatChecker.canParseInt(wordNumStr);
		if (validNumber) {
			int wordNum = Integer.parseInt(wordNumStr);
			Reporter.assertElseFatalError(wordNum > 0, ": Input Number Must be positive!!");
			Reporter.assertElseFatalError(!inputFile.equals(outputFile), ": Must be positive!!");

			SimpleWriter output = new SimpleWriter1L(outputFile);

			outputHTMLHeader(output, inputFile, wordNum);
			SimpleReader input = new SimpleReader1L(inputFile);

			Map<String, Integer> wordCounts = new Map1L<String, Integer>();
			countWord(wordCounts, generateElements(SEPARATORS), input);
			outputBlock(output, wordNum, wordCounts);

			outputFooter(output);
			input.close();
			output.close();
		} else if (wordNumStr.equals("")) {
			Reporter.assertElseFatalError(validNumber, ": Enter Numbers!!");
		} else {
			Reporter.assertElseFatalError(validNumber, ": Enter only Integer Numbers!!");
		}
		in.close();
		out.close();
	}

}

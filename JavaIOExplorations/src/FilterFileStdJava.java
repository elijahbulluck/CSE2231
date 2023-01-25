import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * Program to copy a text file into another file.
 *
 * @author Elijah Bulluck
 *
 */
public final class FilterFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private FilterFileStdJava() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) {

        BufferedReader input;
        BufferedReader strFile;
        PrintWriter output;
        try {
            input = new BufferedReader(new FileReader(args[0]));
            strFile = new BufferedReader(new FileReader(args[2]));
            output = new PrintWriter(
                    new BufferedWriter(new FileWriter(args[1])));
        } catch (IOException e) {
            System.err.println("Error opening file");
            return;
        }

        try {
            java.util.Set<String> set = new HashSet<>();
            String str = strFile.readLine();
            while (str != null) {
                set.add(str);
                str = strFile.readLine();
            }
            String s = input.readLine();
            while (s != null) {
                boolean inLine = false;
                for (String line : set) {
                    if (s.contains(line) && inLine == false) {
                        output.println(s);
                        inLine = true;
                    }
                }
                s = input.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
        try {
            input.close();
            output.close();
            strFile.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }
    }
}

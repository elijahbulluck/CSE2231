import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple program to exercise EmailAccount functionality.
 */
public final class EmailAccountMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private EmailAccountMain() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.print("Enter a first and last name seperated by a space: ");
        String name = in.nextLine();
        Sequence<String> allEmails = new Sequence1L<String>();
        int pos = 0;
        while (name != "") {
            String firstName = name.substring(0, name.indexOf(" "));
            String lastName = name.substring(name.indexOf(" ") + 1,
                    name.length());
            EmailAccount myAccount = new EmailAccount1(firstName, lastName);
            allEmails.add(pos, myAccount.emailAddress());
            for (int i = allEmails.length() - 1; i >= 0; i--) {
                out.println(allEmails.entry(i));
            }
            out.print("Enter a first and last name seperated by a space: ");
            name = in.nextLine();
        }
        in.close();
        out.close();
    }

}

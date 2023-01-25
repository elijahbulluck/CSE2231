import components.map.Map;
import components.map.Map1L;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Elijah Bulluck
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    String email;
    String firstName;
    String lastName;
    static Map<String, Integer> m = new Map1L<String, Integer>();

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
        if (m.hasKey(lastName.toLowerCase())) {
            int newLastNum = m.value(lastName.toLowerCase()) + 1;
            m.replaceValue(lastName.toLowerCase(), newLastNum);
            this.email = lastName.toLowerCase() + "." + newLastNum + "@osu.edu";
        } else {
            m.add(lastName.toLowerCase(), 1);
            this.email = lastName.toLowerCase() + ".1@osu.edu";
        }
    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String emailAddress() {
        return this.email;
    }

    @Override
    public String toString() {
        String emailAndName = "Name: " + this.firstName + " " + this.lastName
                + ", Email: " + this.email;
        return emailAndName;
    }

}

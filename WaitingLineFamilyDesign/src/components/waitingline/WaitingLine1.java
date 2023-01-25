package components.waitingline;

import java.util.Iterator;

import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * {@code WaitingLine} represented as a {@code Sequence} of entries, with
 * implementations of primary methods.
 *
 * @param <T>
 *            type of {@code WaitingLine} entries
 * @correspondence this = $this.entries
 */
public class WaitingLine1<T> extends WaitingLineSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Entries included in {@code this}.
     */
    private Sequence<T> entries;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.entries = new Sequence1L<T>();
    }

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public WaitingLine1() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final WaitingLine<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(WaitingLine<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof WaitingLine1<?> : ""
                + "Violation of: source is of dynamic type Queue3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Queue3<?>, and
         * the ? must be T or the call would not have compiled.
         */
        WaitingLine1<T> localSource = (WaitingLine1<T>) source;
        this.entries = localSource.entries;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(T x) {
        assert x != null : "Violation of: x is not null";
        if (!this.isInLine(x)) {
            this.entries.add(this.entries.length(), x);
        }
    }

    @Override
    public final T dequeue() {
        assert this.length() > 0 : "Violation of: this /= <>";
        return this.entries.remove(0);
    }

    @Override
    public final int length() {
        return this.entries.length();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.entries.iterator();
    }

    @Override
    public boolean isInLine(T x) {
        boolean inLine = false;
        for (T y : this.entries) {
            if (y.equals(x)) {
                inLine = true;
            }
        }
        return inLine;
    }

}

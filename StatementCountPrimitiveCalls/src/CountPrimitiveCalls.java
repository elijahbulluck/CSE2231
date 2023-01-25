import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Elijah Bulluck
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */
                int blockLength = s.lengthOfBlock();
                for (int i = 0; i < blockLength; i++) {
                    Statement child = s.removeFromBlock(i);
                    count += countOfPrimitiveCalls(child);
                    s.addToBlock(i, child);
                }
                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */
                Statement ifBody = s.newInstance();
                Condition c = s.disassembleIf(ifBody);
                count = countOfPrimitiveCalls(ifBody);
                s.assembleIf(c, ifBody);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */
                Statement ifBody = s.newInstance();
                Statement elseBody = s.newInstance();
                Condition c = s.disassembleIfElse(ifBody, elseBody);
                count = countOfPrimitiveCalls(ifBody)
                        + countOfPrimitiveCalls(elseBody);
                s.assembleIfElse(c, ifBody, elseBody);

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */
                Statement whileBody = s.newInstance();
                Condition c = s.disassembleWhile(whileBody);
                count = countOfPrimitiveCalls(whileBody);
                s.assembleWhile(c, whileBody);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */
                String instruction = s.disassembleCall();
                if (instruction.equals("turnright")
                        || instruction.equals("move")
                        || instruction.equals("infect")
                        || instruction.equals("turnleft")
                        || instruction.equals("skip")) {
                    count = 1;
                }
                s.assembleCall(instruction);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

}

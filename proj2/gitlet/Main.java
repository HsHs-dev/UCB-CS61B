package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Hassan Siddig
 *  Starting Date: Saturday, July 5, 2025 3:08:16 PM GMT+03:00
 */
public class Main {

    private static final int INIT_ARGS = 1;
    private static final int ADD_ARGS = 2;
    private static final int COMMIT_ARGS = 2;
    private static final int REMOVE_ARGS = 2;
    private static final int LOG_ARGS = 1;
    private static final int GLOBAL_LOG_ARGS = 1;
    private static final int FIND_ARGS = 2;
    private static final int STATUS_ARGS = 1;
    private static final int BRANCH_ARGS = 2;
    private static final int REMOVE_BRANCH_ARGS = 2;
    private static final int RESET_ARGS = 2;
    private static final int MERGE_ARGS = 2;

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                validateArgs("init", args, INIT_ARGS);
                Repository.init();
                break;
            case "add":
                validateArgs("add", args, ADD_ARGS);
                Repository.add(args[1]);
                break;
            case "commit":
                validateArgs("commit", args, COMMIT_ARGS);
                Repository.commit(args[1]);
                break;
            case "rm":
                validateArgs("rm", args, REMOVE_ARGS);
                Repository.remove(args[1]);
                break;
            case "log":
                validateArgs("log", args, LOG_ARGS);
                Repository.log();
                break;
            case "global-log":
                validateArgs("global-log", args, GLOBAL_LOG_ARGS);
                Repository.globalLog();
                break;
            case "find":
                validateArgs("find", args, FIND_ARGS);
                Repository.find(args[1]);
                break;
            case "status":
                validateArgs("status", args, STATUS_ARGS);
                Repository.status();
                break;
            case "checkout":
                validateArgs("checkout", args, 0);
                Repository.checkout(args);
                break;
            case "branch":
                validateArgs("branch", args, BRANCH_ARGS);
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                validateArgs("rm-branch", args, REMOVE_BRANCH_ARGS);
                Repository.removeBranch(args[1]);
                break;
            case "reset":
                validateArgs("reset", args, RESET_ARGS);
                Repository.reset(args[1]);
                break;
            case "merge":
                validateArgs("merge", args, MERGE_ARGS);
                Repository.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }

    private static void validateArgs(String cmd, String[] args, int expectedNumArgs) {

        if (cmd.equals("checkout")) {
            validateCheckout(args);
            return;
        }

        if (args.length != expectedNumArgs) {
            incorrectOp();
        }
    }

    private static void validateCheckout(String[] args) {
        switch (args.length) {
            case 2:
                break;
            case 3:
                if (!args[1].equals("--")) {
                    incorrectOp();
                }
                break;
            case 4:
                if (!args[2].equals("--")) {
                    incorrectOp();
                }
                break;
            default:
                incorrectOp();
        }
    }

    private static void incorrectOp() {
        System.out.println("Incorrect operands.");
        System.exit(0);
    }
}

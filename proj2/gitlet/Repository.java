package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Hassan Siddig
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static void init() {
    }

    public static void add(String arg) {
    }

    public static void commit(String arg) {
    }

    public static void remove(String arg) {
    }

    public static void log() {
    }

    public static void globalLog() {
    }

    public static void find(String arg) {
    }

    public static void status() {
    }

    public static void checkout(String[] args) {
    }

    public static void branch(String arg) {
    }

    public static void removeBranch(String arg) {
    }

    public static void reset(String arg) {
    }

    public static void merge(String arg) {
    }

    /* TODO: fill in the rest of this class. */
}

package gitlet;

import java.io.File;
import static gitlet.Utils.*;
import java.util.LinkedList;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Hassan Siddig
 */
public class Repository {

    /** The current working directory. */
    private static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    private static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The commits list */
    private static LinkedList<Commit> commits = new LinkedList<>();

    public static void init() {

        // initializing the .gitlet directory
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        }

        // create the initial commit and add it to the commits list
        Commit init = new Commit("initial commit");
        commits.add(init);

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

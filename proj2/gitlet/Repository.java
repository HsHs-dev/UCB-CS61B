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

    /** The blob directory, which contains blobs of the current files */
    private static final File BLOB_DIR = join(".gitlet", "blobs");

    /** The commits list */
    private static final LinkedList<Commit> commits = new LinkedList<>();

    /** HEAD pointer that points to the current commit */
    private static Commit HEAD;

    /** Master branch pointer */
    private static Commit master = HEAD;

    public static void init() {

        // check if a gitlet repo already exists
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        // initialize the gitlet repo
        GITLET_DIR.mkdir();

        // create the initial commit and add it to the commits list
        Commit init = new Commit("initial commit", null);

        // add the init commit to the commits list
        commits.add(init);

        // assign HEAD pointer
        HEAD = commits.getLast();
    }

    public static void add(String addedFile) {

        File file = new File(CWD, addedFile);

        // check if the file exist
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        // create the blobs directory if not created
        if (!BLOB_DIR.exists()) {
            BLOB_DIR.mkdir();
        }

        // compute the SHA-1 hash of the file
        byte[] content = readContents(file);
        String shaName = sha1(content);

        File fileBlob = join(BLOB_DIR, shaName);

        // skip writing if the file already exists
        if (fileBlob.exists()) {
            return;
        }

        // write the file with its hash as its name
        writeContents(fileBlob, content);

        // if the file exists in the current commit don't add it to the staging area
        String currCommitFileHash = HEAD.getFile(addedFile);
        if (currCommitFileHash != null && currCommitFileHash.equals(shaName)) {
            return;
        }

        // add the file to the staging area
        Staging stage = Staging.load();
        stage.addition(addedFile, shaName);

    }

    public static void commit(String message) {

        // check if the commit message is blank
        // a blank commit message is either empty or only contains whitespaces
        if (message.isBlank()) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }

        // create a new commit with the provided commit message
        Commit newCommit = new Commit(message, HEAD);

        // copy the tracked files by the parent to the current commit
        newCommit.copyParentFiles();

        // add the files from the staged for addition area
        Staging stageArea = Staging.load();
        if (stageArea.currAdd().isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        newCommit.addFiles(stageArea.currAdd());

        // remove the files from the staged for removal area
        newCommit.removeFiles(stageArea.currRemove());

        // clear the staging area
        stageArea.clear();

        // add the commit to the commits' tree
        commits.add(newCommit);

        // advance the head pointer
        HEAD = commits.getLast();


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

}

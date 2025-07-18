package gitlet;

import java.io.File;
import static gitlet.Utils.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    /** Commits folder */
    private static final File COMMITS_DIR = join(GITLET_DIR, "commits");

    public static void init() {

        // check if a gitlet repo already exists
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        // initialize the gitlet repo
        GITLET_DIR.mkdir();

        // create the initial commit and add it to the commits list
        Commit init = new Commit("initial commit");
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
        String currCommitFileHash = Commit.getFile(addedFile);
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
        Commit newCommit = new Commit(message);

        // copy the tracked files by the parent to the current commit
        newCommit.copyParentFiles();

        // add the files from the staged for addition area
        Staging stageArea = Staging.load();
        if (stageArea.currAdd().isEmpty() && stageArea.currRemove().isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        newCommit.addFiles(stageArea.currAdd());

        // remove the files from the staged for removal area
        newCommit.removeFiles(stageArea.currRemove());

        // clear the staging area
        stageArea.clear();

        // add the commit to the commits' tree and advance the head pointer
        newCommit.addCommit();

        // write the commit
        newCommit.writeCommit();
    }

    public static void remove(String fileName) {

        // remove the file from the staging area if it's staged for addition
        Staging stageArea = Staging.load();
        String wasStaged = stageArea.removeStaged(fileName);

        // stage the file for removal if it's tracked in the current commit
        Commit currentCommit = Commit.load();
        boolean tracked = currentCommit.exists(fileName);
        if (tracked) {
            stageArea.addRemoval(fileName);

            // remove the file from the working directory if the user has not already done so
            restrictedDelete(fileName);
        }

        // the file is neither staged nor tracked by the head commit
        if (wasStaged == null && !tracked) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
    }

    public static void log() {

        // load the current commit
        Commit currentCommit = Commit.load();

        LinkedList<String> list = currentCommit.commits;
        Iterator<String> iter = list.descendingIterator();
        while (iter.hasNext()) {
            String commit = iter.next();
            File currentCommitFile = join(COMMITS_DIR, commit);
            Commit loadedCommit = readObject(currentCommitFile, Commit.class);
            System.out.println("===");
            System.out.println("commit " + loadedCommit.getHash());
            System.out.println("Date: " + loadedCommit.getTimestamp());
            System.out.println(loadedCommit.getMessage());
            System.out.println();
        }

    }

    public static void globalLog() {

        // store all the commits in a list
        List<String> commits = plainFilenamesIn(COMMITS_DIR);

        // loop through the commits and print them
        if (commits == null) {
            return;
        }

        for (String currCommit: commits) {

            // load the commit
            File commitFile = join(COMMITS_DIR, currCommit);
            Commit commit = readObject(commitFile, Commit.class);

            System.out.println("===");
            System.out.println("commit " + commit.getHash());
            System.out.println("Date: " + commit.getTimestamp());
            System.out.println(commit.getMessage());
            System.out.println();
        }
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

package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Hassan Siddig
 */
public class Commit implements Serializable {

    /** The current working directory. */
    private static final File CWD = new File(System.getProperty("user.dir"));

    /** The .gitlet directory. */
    private static final File GITLET_DIR = join(CWD, ".gitlet");

    /** Commits directory */
    private static final File COMMITS_DIR = join(GITLET_DIR, "commits");

    /** HEAD file */
    private static final File HEAD_FILE = join(GITLET_DIR, "HEAD");

    /** Branches directory */
    private static final File BRANCHES_DIR = join(GITLET_DIR, "branches");

    /** The message of this Commit. */
    private String message;

    /** The parent commit of the current Commit */
    private String parent;

    /** The timestamp of this Commit */
    private String timestamp;

    /** The tracked files by this commit */
    private Map<String, String> filesMap = new TreeMap<>();

    /** Commits list */
    public LinkedList<String> commits = new LinkedList<>();

    /** Commit's hash value */
    private String hash;

    public Commit(String message) {
        if (!COMMITS_DIR.exists()) {
            init(message);
            return;
        }
        this.message = message;
        this.timestamp = writeTimestamp();
        this.parent = Arrays.toString(readContents(getHead()));
    }



    private void init(String message) {
        COMMITS_DIR.mkdir();
        this.timestamp = "Thu Jan 1 00:00:00 1970 +0000";
        this.parent = null;
        this.message = message;
        this.hash = commitHash();

        // add the commit to the commits' tree
        commits.add(this.hash);

        // write the object to the commits directory
        File initObj = join(COMMITS_DIR, this.hash);
        writeObject(initObj, this);

        // assign the HEAD pointer
        writeContents(HEAD_FILE, this.hash);

        // create the branches directory with default master branch
        BRANCHES_DIR.mkdir();
        File master = join(BRANCHES_DIR, "master");
        writeContents(master, this.hash);

        // assign the head pointer to the master branch
        writeContents(HEAD_FILE, "master");
    }

    private String writeTimestamp() {
        ZonedDateTime now = ZonedDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return now.format(formatter);
    }

    /** Copy the files of the current parent */
    public void copyParentFiles() {
        Commit parentCommit = load();
        this.filesMap = new TreeMap<>(parentCommit.filesMap);
        this.commits = new LinkedList<>(parentCommit.commits);
    }

    /**
     * @return the current commit (HEAD commit)
     */
    public static Commit load() {
        String headCommit = readContentsAsString(getHead());
        File currentCommitFile = join(COMMITS_DIR, headCommit);
        return readObject(currentCommitFile, Commit.class);
    }

    /**
     * @return the head branch file
     */
    public static File getHead() {
        String head = readContentsAsString(HEAD_FILE);
        return join(BRANCHES_DIR, head);
    }

    /**
     * Check if the provided file is tracked by the commit
     * @param fileName file name to be looked up
     * @return true if the file is tracked by the commit
     */
    public boolean contains(String fileName) {
        return filesMap.containsKey(fileName);
    }

    /**
     * Copy the provided files on top of the current commit's files
     * @param files the files to be copied
     */
    public void addFiles(Map<String, String> files) {
        this.filesMap.putAll(files);
    }

    /**
     * @param files set of files to be removed from the current commit's files
     */
    public void removeFiles(Set<String> files) {
        for (String file: files) {
            this.filesMap.remove(file);
        }
    }

    /**
     * @return a hash value of the current commit
     */
    public String commitHash() {
        StringBuilder filesMapHash = new StringBuilder();

        for (Map.Entry<String, String> entry: filesMap.entrySet()) {
            filesMapHash.append(entry.getKey()).append(entry.getValue());
        }

        if (parent == null) {
            return sha1(filesMapHash.toString(), message, timestamp);
        }

        return sha1(filesMapHash.toString(), parent, message, timestamp);
    }

    /** Adds a new commit to the commit tree */
    public void addCommit() {
        this.hash = commitHash();
        commits.add(this.hash);
        String head = readContentsAsString(HEAD_FILE);
        File headBranch = join(BRANCHES_DIR, head);
        writeContents(headBranch, commits.getLast());
    }

    /**
     * write the commit to a file in commits' dir
     */
    public void writeCommit() {
        File commitFile = join(COMMITS_DIR, hash);
        writeObject(commitFile, this);
    }

    /**
     * @return the commit's ID
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * @return the commit's timestamp
     */
    public String getTimestamp() {
        return this.timestamp;
    }

    /**
     * @return the commit's message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Returns the value associated with the fileName
     * @param fileName the name of the file to look up its value
     * @return the hash value associated with the file
     */
    public String getVal(String fileName) {
        return filesMap.get(fileName);
    }

    /**
     * @return a map of tracked files by this commit
     */
    public Map<String, String> getFiles() {
        return new TreeMap<>(filesMap);
    }

    /**
     * change the head pointer to the given branchName
     * @param branchName the branch name
     */
    public static void checkout(String branchName) {
        writeContents(HEAD_FILE, branchName);
    }

}

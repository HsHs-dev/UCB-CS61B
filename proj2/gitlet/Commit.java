package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
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

    /** Shortened commits' ids directory */
    private static final File SHORT = join(GITLET_DIR, "short");

    /** The message of this Commit. */
    private String message;

    /** The parent commit of the current Commit */
    private String parent;

    /** The timestamp of this Commit */
    private String timestamp;

    /** The tracked files by this commit */
    private Map<String, String> filesMap = new TreeMap<>();

    /** Commits list */
    private LinkedList<String> commits = new LinkedList<>();

    /** Commit's hash value */
    private String hash;

    public Commit(String commitMessage) {
        if (!COMMITS_DIR.exists()) {
            init(commitMessage);
            return;
        }
        this.message = commitMessage;
        this.timestamp = writeTimestamp();
        this.parent = Arrays.toString(readContents(getHead()));
    }



    private void init(String commitMessage) {
        this.timestamp = "Thu Jan 1 00:00:00 1970 +0000";
        this.parent = null;
        this.message = commitMessage;
        this.hash = commitHash();

        // add the commit to the commits' tree
        commits.add(this.hash);

        // write the object to the commits directory
        COMMITS_DIR.mkdir();
        File initCommit = join(COMMITS_DIR, this.hash);
        writeObject(initCommit, this);

        // write the shortened commit's id to short directory
        SHORT.mkdir();
        File shortInitCommit = join(SHORT, this.hash.substring(0, 6));
        writeContents(shortInitCommit, this.hash);

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

    /**
     * @return the parents' commit chain of this commit
     */
    public LinkedList<String> getCommits() {
        return new LinkedList<>(commits);
    }

    /**
     * write the commit id to the shortened commits' id directory
     */
    public void writeShort() {
        File shortCommit = join(SHORT, this.hash.substring(0, 6));
        writeContents(shortCommit, this.hash);
    }
}

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

    /** Commits folder */
    private static final File COMMITS_DIR = join(GITLET_DIR, "commits");

    /** HEAD file */
    private static final File HEAD_FILE = join(GITLET_DIR, "HEAD");

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

    /** Master branch */ // TODO

    /** Commit's hash value */
    private String hash;

    public Commit(String message) {
        if (!COMMITS_DIR.exists()) {
            init(message);
            return;
        }
        this.message = message;
        this.timestamp = writeTimestamp();
        this.parent = Arrays.toString(readContents(HEAD_FILE));
    }


    private void init(String message) {
        COMMITS_DIR.mkdir();
        this.timestamp = "Thu Jan 1 00:00:00 1970 +0000";
        this.parent = null;
        this.message = message;
        this.hash = commitHash();

        // add the commit to the commits' tree
        commits.add(this.hash);

        // write the object to the commits folder
        File initObj = join(COMMITS_DIR, this.hash);
        writeObject(initObj, this);

        // assign the HEAD pointer
        writeContents(HEAD_FILE, this.hash);
    }

    private String writeTimestamp() {
        ZonedDateTime now = ZonedDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return now.format(formatter);
    }

    /**
     * @param fileName the name of the file to be looked up
     * @return the associated hash value
     */
    public static String getFile(String fileName) {
        Commit parentCommit = load();
        return parentCommit.filesMap.get(fileName);
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
        String head = readContentsAsString(HEAD_FILE);
        File currentCommitFile = join(COMMITS_DIR, head);
        return readObject(currentCommitFile, Commit.class);
    }

    public boolean exists(String fileName) {
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
        writeContents(HEAD_FILE, commits.getLast());
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


}

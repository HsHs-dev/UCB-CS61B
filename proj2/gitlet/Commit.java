package gitlet;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Hassan Siddig
 */
public class Commit implements Serializable {

    /** The message of this Commit. */
    private String message;

    /** The parent commit of the current Commit */
    private Commit parent;

    /** The timestamp of this Commit */
    private String timestamp;

    /** The tracked files by this commit */
    private Map<String, String> filesMap;

    public Commit(String message, Commit parent) {
        if (message.equals("initial commit")) {
            this.timestamp = "Thu Jan 1 00:00:00 1970 +0000";
            this.filesMap = new TreeMap<>();
            return;
        }
        this.message = message;
        this.timestamp = getTimestamp();
        this.parent = parent;
    }

    private String getTimestamp() {
        ZonedDateTime now = ZonedDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return now.format(formatter);
    }

    /**
     * @param fileName the name of the file to be looked up
     * @return the associated hash value
     */
    public String getFile(String fileName) {
        return this.filesMap.get(fileName);
    }

    /** Copy the files of the current parent */
    public void copyParentFiles() {
        this.filesMap = new TreeMap<>(this.parent.filesMap);
    }

    /**
     * Copy the provided files on top of the current commit's files
     * @param files the files to be copied
     */
    public void addFiles(Map<String, String> files) {
        this.filesMap.putAll(files);
    }

    public void removeFiles(Set<String> files) {
        for (String file: files) {
            this.filesMap.remove(file);
        }
    }
}

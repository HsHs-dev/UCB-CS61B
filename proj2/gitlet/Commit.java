package gitlet;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.util.Map;
import java.util.TreeMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Hassan Siddig
 */
public class Commit {

    /** The message of this Commit. */
    private String message;

    /** The parent commit of the current Commit */
    private Commit parent;

    /** The timestamp of this Commit */
    private String timestamp;

    /** The tracked files by this commit */
    Map<String, String> filesMap = new TreeMap<>();

    public Commit(String message) {
        if (message.equals("initial commit")) {
            this.timestamp = "Thu Jan 1 00:00:00 1970 +0000";
            this.message = message;
            this.parent = null;
            return;
        }
        this.message = message;
        this.timestamp = getTimestamp();
        // TODO
        // this.parent = ??
    }

    private String getTimestamp() {
        ZonedDateTime now = ZonedDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return now.format(formatter);
    }
}

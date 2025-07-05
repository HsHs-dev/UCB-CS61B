package gitlet;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.util.Date; // TODO: You'll likely use this in this class

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

    public Commit(String message) {
        this.message = message;
        this.timestamp = getTimestamp();
        // TODO
        this.parent = null;
    }

    private String getTimestamp() {
        ZonedDateTime now = ZonedDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return now.format(formatter);
    }
}

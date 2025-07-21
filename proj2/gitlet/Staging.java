package gitlet;


import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

/** Represent the staging and removal areas of the added files */
public class Staging implements Serializable {

    /** The current working directory. */
    private static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    private static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The staged file which keeps track of staging area */
    private static final File STAGED = join(GITLET_DIR, "staged");

    /** Represents the files that are staged for addition */
    private Map<String, String> addStaged = new TreeMap<>();

    /** Represents the files that are staged for removal */
    private Set<String> removeStaged = new TreeSet<>();

    /** add a file to the staging area */
    public void addition(String fileName, String fileHash) {
        addStaged.put(fileName, fileHash);
        saveStaging();
    }

    /** Remove a file from the staging area */
    public String removeStaged(String fileName) {
        String staged = addStaged.remove(fileName);
        saveStaging();
        return staged;
    }

    /** Adds a file to the staging for removal area */
    public void addRemoval(String fileName) {
        removeStaged.add(fileName);
        saveStaging();
    }

    /** write the current object to .gitlet/staged file */
    private void saveStaging() {
        File staged = join(GITLET_DIR, "staged");
        writeObject(staged, this);
    }

    /**
     * @return the staging object
     */
    public static Staging load() {
        if (!STAGED.exists()) {
            return new Staging();
        }
        return readObject(STAGED, Staging.class);
    }

    /** Clear the staging area */
    public void clear() {
        addStaged.clear();
        removeStaged.clear();
        saveStaging();
    }

    /**
     * @return Map of the files staged for addition
     */
    public Map<String, String> currAdd() {
        return this.addStaged;
    }

    /**
     * @return Set of the files staged for removal
     */
    public Set<String> currRemove() {
        return this.removeStaged;
    }

}

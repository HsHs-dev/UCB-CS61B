package gitlet;

import java.io.File;
import static gitlet.Utils.*;

import java.util.*;

/** Represents a gitlet repository.
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

    /** HEAD file */
    private static final File HEAD_FILE = join(GITLET_DIR, "HEAD");

    /** Branches directory */
    private static final File BRANCHES_DIR = join(GITLET_DIR, "branches");

    /** Shortened commits' ids directory */
    private static final File SHORT = join(GITLET_DIR, "short");

    public static void init() {

        // check if a gitlet repo already exists
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already "
                    + "exists in the current directory.");
            System.exit(0);
        }

        // initialize the gitlet repo
        GITLET_DIR.mkdir();

        // create the initial commit and add it to the commits list
        Commit init = new Commit("initial commit");
    }

    public static void add(String addedFile) {

        // check if a .gitlet dir exists
        checkInit();

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

        // write the file if it doesn't already exist
        if (!fileBlob.exists()) {
            // write the file with its hash as its name
            writeContents(fileBlob, content);
        }

        // if the file exists in the current commit don't add it to the staging area
        Commit currCommit = Commit.load();
        String currCommitFileHash = currCommit.getVal(addedFile);
        if (currCommitFileHash != null && currCommitFileHash.equals(shaName)) {
            return;
        }

        // add the file to the staging area
        Staging stage = Staging.load();
        stage.addition(addedFile, shaName);
    }

    public static void commit(String message) {

        // check if a .gitlet dir exists
        checkInit();

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

        // write the commit to the short commit directory
        newCommit.writeShort();
    }

    public static void remove(String fileName) {

        // check if a .gitlet dir exists
        checkInit();

        // remove the file from the staging area if it's staged for addition
        Staging stageArea = Staging.load();
        String wasStaged = stageArea.removeStaged(fileName);

        // stage the file for removal if it's tracked in the current commit
        Commit currentCommit = Commit.load();
        boolean tracked = currentCommit.contains(fileName);
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

        // check if a .gitlet dir exists
        checkInit();

        // load the current commit
        Commit currentCommit = Commit.load();

        LinkedList<String> list = currentCommit.getCommits();
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

        // check if a .gitlet dir exists
        checkInit();

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

    public static void find(String message) {

        // check if a .gitlet dir exists
        checkInit();

        List<String> commits = plainFilenamesIn(COMMITS_DIR);

        if (commits == null) {
            return;
        }

        List<String> matched = new ArrayList<>();

        for (String currentCommit: commits) {
            File commitFile = join(COMMITS_DIR, currentCommit);
            Commit commit = readObject(commitFile, Commit.class);

            if (commit.getMessage().equals(message)) {
                matched.add(currentCommit);
            }
        }

        if (matched.isEmpty()) {
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }

        for (String commitMessage: matched) {
            System.out.println(commitMessage);
        }
    }

    public static void status() {

        // check if a .gitlet dir exists
        checkInit();

        // branches
        System.out.println("=== Branches ===");

        List<String> branches = plainFilenamesIn(BRANCHES_DIR);
        String currentBranch = readContentsAsString(HEAD_FILE);
        for (String branch: branches) {
            if (branch.equals(currentBranch)) {
                System.out.println("*" + branch);
                continue;
            }
            System.out.println(branch);
        }
        System.out.println();

        // staged for addition files
        System.out.println("=== Staged Files ===");

        Staging staged = Staging.load();
        Set<String> addedFiles = staged.currAdd().keySet();
        for (String file: addedFiles) {
            System.out.println(file);
        }
        System.out.println();

        // staged for removal files
        System.out.println("=== Removed Files ===");

        Set<String> removedFiles = staged.currRemove();
        for (String file: removedFiles) {
            System.out.println(file);
        }
        System.out.println();

        System.out.println("=== Modifications Not Staged For Commit ===\n");
        System.out.println("=== Untracked Files ===\n");
    }

    public static void checkout(String[] args) {

        // check if a .gitlet dir exists
        checkInit();

        switch (args.length) {
            case 2:
                checkoutBranch(args[1]);
                break;
            case 3:
                checkoutFile(args[2]);
                break;
            case 4:
                checkoutCommit(args[1], args[3]);
                break;
            default:
                break;
        }
    }

    private static void checkoutFile(String fileName) {

        // load the head commit
        Commit headCommit = Commit.load();

        // check if the file exists in the commit
        checkCommitFile(headCommit, fileName);

        // get the file from the blobs directory
        String fileBlobName = headCommit.getVal(fileName);
        File fileBlob = join(BLOB_DIR, fileBlobName);
        byte[] content = readContents(fileBlob);

        // overwrite the file in the CWD
        File writtenFile = join(CWD, fileName);
        writeContents(writtenFile, content);
    }

    private static void checkoutCommit(String id, String fileName) {

        // get the full commit id if the id is shortened
        id = checkShort(id);

        // check if the commit with the specified id exists
        File commitFile = join(COMMITS_DIR, id);
        checkCommit(commitFile);

        Commit commit = readObject(commitFile, Commit.class);

        // check if the file exists in the commit
        checkCommitFile(commit, fileName);

        // write the file to the cwd
        writeFile(commit.getVal(fileName), fileName);
    }

    private static void checkCommit(File commit) {
        if (!commit.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
    }

    private static String checkShort(String id) {

        if (id.length() == 40) {
            return id;
        }

        id = id.substring(0, Math.min(6, id.length()));

        List<String> shortCommits = plainFilenamesIn(SHORT);
        for (String file: shortCommits) {
            if (file.equals(id)) {
                return readContentsAsString(join(SHORT, file));
            }
        }

        File commitFile = join(COMMITS_DIR, "null");
        checkCommit(commitFile);

        return null;
    }

    private static void checkoutBranch(String branchName) {

        File branch = join(BRANCHES_DIR, branchName);

        // check if the branch exists
        if (!branch.exists()) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }

        String currentBranch = readContentsAsString(HEAD_FILE);
        // check if the checked-out branch is the current branch
        if (branchName.equals(currentBranch)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }

        String commitId = readContentsAsString(branch);
        checkoutCommID(commitId);

        // change the head pointer to the new branch
        Commit.checkout(branchName);
    }


    private static void checkoutCommID(String commitId) {

        // If a working file is untracked in the current branch
        // and would be overwritten by the checkout,
        // print There is an untracked file in the way;
        // delete it, or add and commit it first. and exit;

        // cwd files
        List<String> cwdFiles = plainFilenamesIn(CWD);

        // current commit files
        Commit headCommit = Commit.load();
        Map<String, String> headCommitFiles = headCommit.getFiles();

        // target commit files
        File targetCommitFile = join(COMMITS_DIR, commitId);
        Commit targetCommit = readObject(targetCommitFile, Commit.class);
        Map<String, String> targetCommitFiles = targetCommit.getFiles();

        for (String file: cwdFiles) {
            if (!headCommitFiles.containsKey(file) && targetCommitFiles.containsKey(file)) {
                System.out.println("There is an untracked file in the way; "
                        + "delete it, or add and commit it first.");
                System.exit(0);
            }
        }

        // delete the files in the cwd that aren't in target commit
        for (String file: cwdFiles) {
            if (!targetCommitFiles.containsKey(file)) {
                restrictedDelete(file);
            }
        }

        // write the files tracked by the target commit to the cwd
        for (Map.Entry<String, String> file: targetCommitFiles.entrySet()) {
            writeFile(file.getValue(), file.getKey());
        }

        // clear the staging area
        Staging staging = Staging.load();
        staging.clear();
    }

    private static void checkCommitFile(Commit commit, String fileName) {
        if (!commit.contains(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
    }

    private static void writeFile(String fileVal, String fileName) {

        File fileBlob = join(BLOB_DIR, fileVal);
        byte[] content = readContents(fileBlob);

        File writtenFile = join(CWD, fileName);
        writeContents(writtenFile, content);
    }


    public static void branch(String branchName) {

        // check if a .gitlet dir exists
        checkInit();

        File branch = join(BRANCHES_DIR, branchName);

        // check if the branch already exists
        if (branch.exists()) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }

        // create the branch and points it to the current head commit
        String headCommit = readContentsAsString(Commit.getHead());
        writeContents(branch, headCommit);
    }

    public static void removeBranch(String branchName) {

        // check if a .gitlet dir exists
        checkInit();

        List<String> branches = plainFilenamesIn(BRANCHES_DIR);

        // check if the branch exists
        if (!branches.contains(branchName)) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }

        // check if the to be removed branch is the current branch
        String currentBranch = readContentsAsString(HEAD_FILE);
        if (currentBranch.equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }

        // remove the branch from the branches directory
        File branchFile = join(BRANCHES_DIR, branchName);
        /* Couldn't use the restrictedDelete method because it's meant for CWD files */
        branchFile.delete();

    }

    public static void reset(String id) {

        // check if a .gitlet dir exists
        checkInit();

        // get the full commit id if the id is shortened
        id = checkShort(id);

        File commitFile = join(COMMITS_DIR, id);

        // check if the commit exist
        checkCommit(commitFile);

        // checking out the commit files
        checkoutCommID(id);

        // move the current branch's head to the commit
        String branch = readContentsAsString(HEAD_FILE);
        File branchFile = join(BRANCHES_DIR, branch);
        writeContents(branchFile, id);

    }

    public static void merge(String arg) {

        // check if a .gitlet dir exists
        checkInit();

    }

    private static void checkInit() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

}

package it.unisa.thesis.mosvi.utils.oldgit;


import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public final class GitRepository implements Serializable {

    private File directory;
    
    public GitRepository() {
    }

    /**
     * Initializes repository Reads all commits from git-log and generate
     * instances of Commit Class
     *
     * @return true if initializes is successful, false otherwise
     */
    public boolean initialize() {
        if (directory != null) {
            return true;
        }
        return false;
    }

    /**
     * Save the Git Repository
     *
     * @param path Path of file where save the repository
     */
    public void save(String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            BufferedOutputStream buffer = new BufferedOutputStream(fileOut);
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(this);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(GitRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Serialized data is saved in " + path);
    }

    /**
     * Returns the directory of repository
     *
     * @return File rapresent the directory of repository
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Sets the directory of repository
     *
     * @param directory : File rapresent the directory of repository
     */
    public void setDirectory(File directory) {
        this.directory = directory;
    }

}

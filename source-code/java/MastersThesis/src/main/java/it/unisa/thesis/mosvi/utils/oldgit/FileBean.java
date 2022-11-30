package it.unisa.thesis.mosvi.utils.oldgit;



import java.util.Objects;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public final class FileBean implements java.io.Serializable {

    private String path;

    public FileBean() {

    }

    public FileBean(String path) {
        this.setPath(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileBean) {
            FileBean file2 = (FileBean) obj;
            return this.getPath().equals(file2.getPath());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.path);
        return hash;
    }

}

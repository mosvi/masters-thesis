package it.unisa.thesis.mosvi.utils.oldgit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Process;
import java.util.Date;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 * <p/>
 * GitDM - Git Data Mining
 */
public class Git {

    public static void clone(String url, boolean isSVN, String projectName, String dir, String version) throws IOException, InterruptedException {
        System.out.println(new Date()+" - Start cloning into " + dir);
        String[] cmd;
        File file = new File(dir);
        if (file.exists()) {
            if (version != null) {
                if (isSVN) {
                    cmd = new String[]{"git", "svn", "clone", url, projectName, "--branch", version};
                } else {
                    cmd = new String[]{"git", "clone", url, projectName, "--branch", version};
                }
            } else if (isSVN) {
                cmd = new String[]{"git", "svn", "clone", url, projectName};
            } else {
                cmd = new String[]{"git", "clone", url, projectName};
            }
            Process p = Runtime.getRuntime().exec(cmd, null, file);
            p.waitFor();
        }
        System.out.println("End cloning");
    }

    public static String generateLogFileMasterOnly(File directory) throws IOException {
        // String cmd =
        // "git --git-dir "+directory.getAbsolutePath().replace(" ",
        // "\\ ")+"/.git log --format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?";

        String dir = directory.getAbsolutePath() + "/.git";
        String[] cmd = new String[]{"git", "--git-dir", dir, "log",
            "--first-parent", "--reverse",
            "--format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?"};
        String line;
        String log = "";
        Process p = Runtime.getRuntime().exec(cmd);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                p.getInputStream()))) {
            while ((line = in.readLine()) != null) {
                log += line;
            }
            log = formatLogContent(log);
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }

    private static String generateLogFile(File directory) {
        // String cmd =
        // "git --git-dir "+directory.getAbsolutePath().replace(" ",
        // "\\ ")+"/.git log --format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?";

        String dir = directory.getAbsolutePath() + "/.git";
        String[] cmd = new String[]{"git", "--git-dir", dir, "log",
            "--reverse",
            "--format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?"};
        String line;
        String log = "";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    log += line;
                }
                log = formatLogContent(log);
            }
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }

    public static List<FileBean> gitList(File directory, String version) {
        String dir = directory.getAbsolutePath() + "/.git";
        ArrayList<FileBean> files = new ArrayList<>();
        String line;
        String v;
        if (version != null)
            v = version;
        else
            v = "master";
        String cmd = "git --git-dir \"" + dir + "\" ls-tree -r "+v+" --name-only";
        System.out.println("INVOKING: "+cmd);
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    files.add(new FileBean(line.split(" ")[0]));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return files;
    }

    public static void gitReset(File directory) {
        String dir = directory.getAbsolutePath() + "/.git";

        String[] cmd = new String[]{"git", "--git-dir", dir, "reset --hard origin/HEAD"};

        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String formatLogContent(String content) {
        String step1 = content.replace("\n", " ");
        return step1.replace("-?end?", "\n");
    }

    public static void clean(File directory) {
        String dir = directory.getAbsolutePath() + "/.git";
        String[] cmd = new String[]{"git", "--git-dir", dir, "gc"};

        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        p.destroy();
    }
}

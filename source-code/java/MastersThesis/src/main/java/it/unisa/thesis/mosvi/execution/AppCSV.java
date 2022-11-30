package it.unisa.thesis.mosvi.execution;

import java.util.Arrays;
import java.util.List;

public class AppCSV {

    private String appName = "";
    private String githubUrl = "";

    AppCSV(){
    }

    AppCSV(String name, String githubUrl) {
        this.appName = checkName(name);
        this.githubUrl = checkLink(githubUrl);
    }

    public String getAppName() {
        return appName;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setAppName(String appName) {
        this.appName = checkName(appName);
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = checkLink(githubUrl);
    }

    public String checkName(String appName) {
        if (appName.contains(".")) {
            List<String> name = Arrays.asList(appName.split("\\."));

            if (!name.equals(null) && !name.isEmpty()) {
                appName = name.get(name.size() - 1);
            }
        }
        return  appName;
    }

    public String checkLink(String link) {
        if (!link.contains(".git")) {
            link = link + ".git";
        }
        return link;
    }
}

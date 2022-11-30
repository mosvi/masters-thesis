package it.unisa.thesis.mosvi.execution;

import it.unisa.thesis.mosvi.utils.oldgit.FileBean;
import it.unisa.thesis.mosvi.utils.oldgit.Git;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectParser {
    public ProjectParser() {
    }

    public List<ClassBean> getAllProjectClassBeans(String version, String projectPath) throws IOException {
        List<FileBean> repoFiles = Git.gitList(new File(projectPath), version);
        System.out.println("Repo Files Size: " + repoFiles.size());
        List<ClassBean> projectClasses = new ArrayList<ClassBean>();
        for (FileBean repoFile : repoFiles) {
            if (repoFile.getPath().contains(".java")) {
                File workTreeFile = new File(projectPath + "/" + repoFile.getPath());
                if (workTreeFile.exists()) {
                    ArrayList<ClassBean> foundClasses = ReadSourceCode.readSourceCode(workTreeFile);
                    //Forced to do it there as readSource code is a mess and recursive. It should be put in read source code.
                    foundClasses.forEach(clazz -> {
                        clazz.setContainingFileName(repoFile.getPath());
                        clazz.setSourceFile(workTreeFile);
                    });
                    projectClasses.addAll(foundClasses);
                }
            }
        }
        return projectClasses;
    }


}

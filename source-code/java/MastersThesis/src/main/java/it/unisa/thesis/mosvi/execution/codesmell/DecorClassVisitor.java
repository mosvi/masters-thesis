package it.unisa.thesis.mosvi.execution.codesmell;


import it.unisa.thesis.mosvi.execution.Analysis;
import it.unisa.thesis.mosvi.execution.ProjectParser;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.util.List;

public class DecorClassVisitor implements CommitVisitor {

    private Commit mCommit;


    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

        mCommit = commit;

        if(Analysis.count==2){
            CSVHeading(writer);
            Analysis.count++;
        }

        try {
            List<ClassBean> allProjectClasses = parseProject(repo.getPath(), null);

            SpaghettiCodeRule spaghettiCodeRule = new SpaghettiCodeRule();
            GodClassRule godClassRule = new GodClassRule();
            ComplexClassRule complexClassRule = new ComplexClassRule();

            allProjectClasses.forEach(classBean -> {

                String isSpaghetti = spaghettiCodeRule.isSmelly(classBean) ? "1" : "0";
                String isLarge = godClassRule.isSmelly(classBean) ? "1" : "0";
                String isComplex = complexClassRule.isSmelly(classBean) ? "1" : "0";

                writer.write(
                        mCommit.getHash(),
                        commit.getAuthor().getName(),
                        commit.getMsg(),
                        classBean.getQualifiedName(),
                        isSpaghetti,
                        isLarge,
                        isComplex
                );

            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CSVHeading(PersistenceMechanism writer){
        writer.write(
                "Commit Hash",
                "Committers Name",
                "Commit Message",
                "ClassQualifiedName",
                "Decor_Spaghetti_Code",
                "Decor_Large_Class",
                "Decor_Complex_Class"
        );
    }

    private static List<ClassBean> parseProject(String repoPath, String tag) throws Exception {
        ProjectParser projectParser = new ProjectParser();
        return projectParser.getAllProjectClassBeans(tag, repoPath);
    }
}
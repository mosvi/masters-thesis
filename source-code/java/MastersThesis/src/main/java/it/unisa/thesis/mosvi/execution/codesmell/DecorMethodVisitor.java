package it.unisa.thesis.mosvi.execution.codesmell;

import it.unisa.thesis.mosvi.execution.Analysis;
import it.unisa.thesis.mosvi.execution.ProjectParser;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.util.List;

public class DecorMethodVisitor implements CommitVisitor {

    private Commit mCommit;


    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

        mCommit = commit;

        if(Analysis.count==3){
            CSVHeading(writer);
            Analysis.count++;
        }

        try {
            List<ClassBean> allProjectClasses = parseProject(repo.getPath(), null);

            LongMethodRule longMethodRule = new LongMethodRule();
            FeatureEnvyRule featureEnvyRule = new FeatureEnvyRule();

            allProjectClasses.forEach(classBean -> {

                classBean.getMethods().forEach(methodBean -> {
                    String isFE = featureEnvyRule.isSmelly(methodBean) ? "1" : "0";
                    String isLong = longMethodRule.isSmelly(methodBean) ? "1" : "0";

                    writer.write(
                            mCommit.getHash(),
                            commit.getAuthor().getName(),
                            commit.getMsg(),
                            methodBean.getQualifiedName(),
                            isFE,
                            isLong
                    );

                });

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
                "MethodQualifiedName",
                "Decor_Feature_Envy",
                "Decor_long_Method"
        );
    }

    private static List<ClassBean> parseProject(String repoPath, String tag) throws Exception {
        ProjectParser projectParser = new ProjectParser();
        return projectParser.getAllProjectClassBeans(tag, repoPath);
    }
}
package it.unisa.thesis.mosvi.execution.ckmetrics;

import it.unisa.thesis.mosvi.execution.Analysis;
import it.unisa.thesis.mosvi.execution.ProjectParser;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.util.List;

public class CKMetricsVisitor implements CommitVisitor {


    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

        if(Analysis.count==1){
            CSVHeading(writer);
            Analysis.count++;
        }


        try {
            List<ClassBean> allProjectClasses = parseProject(repo.getPath(), null);

            allProjectClasses.forEach(classBean -> {

                int LOC = CKMetrics.getLOC(classBean);
                int LCOM = CKMetrics.getLCOM(classBean);
                int CBO = CKMetrics.getCBO(classBean);
                int WMC = CKMetrics.getWMC(classBean);


                writer.write(
                        commit.getHash(),
                        commit.getAuthor().getName(),
                        commit.getMsg(),
                        classBean.getQualifiedName(),
                        LOC,
                        LCOM,
                        CBO,
                        WMC
                );

            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static List<ClassBean> parseProject(String repoPath, String tag) throws Exception {
        ProjectParser projectParser = new ProjectParser();
        return projectParser.getAllProjectClassBeans(tag, repoPath);
    }

    public void CSVHeading(PersistenceMechanism writer){
        writer.write(
                "Commit Hash",
                "Committers Name",
                "Commit Message",
                "ClassQualifiedName",
                "LOC",
                "LCOM",
                "CBO",
                "WMC"
        );
    }

}
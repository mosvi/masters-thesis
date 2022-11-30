package it.unisa.thesis.mosvi.execution.andsmell;

import it.unisa.thesis.mosvi.execution.Analysis;
import it.unisa.thesis.mosvi.utils.ast.ASTUtilities;
import it.unisa.thesis.mosvi.execution.ProjectParser;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AndSmellVisitor implements CommitVisitor {


    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

        if(Analysis.count==0){
            CSVHeading(writer);
            Analysis.count++;
        }

        try {
            // Package filtering
            List<ClassBean> allClasses = parseProject(repo.getPath(), null);
            DWSmell dwaSmell = new DWSmell();
            IDSSmell idsSmell = new IDSSmell();
            ISSmell isSmell = new ISSmell();
            LTSmell ltSmell = new LTSmell();
            MIMSmell mimSmell = new MIMSmell();



            allClasses.forEach(classBean -> {

                CompilationUnit compilationUnit = null;
                try {
                    compilationUnit = ASTUtilities.getCompilationUnit(classBean.getSourceFile(), classBean.getImports().toArray(new String[0]));
                 } catch (IOException e) {
                    e.printStackTrace();
                }
                if (compilationUnit.getPackage() != null) {

                    List<TypeDeclaration> topTypes = (List<TypeDeclaration>) compilationUnit.types();
                    // Get up to level 1 inner classes
                    List<TypeDeclaration> innerTypes = topTypes.stream()
                            .flatMap(type -> Arrays.stream(type.getTypes()))
                            .collect(Collectors.toList());
                    List<TypeDeclaration> types = new ArrayList<>(topTypes);
                    types.addAll(innerTypes);
                    for (TypeDeclaration type : types) {
                        //System.out.println("\tClass: " + type.getName().getIdentifier());
                        classBean.setTypeDeclaration(type);

                        String isDWSmell = dwaSmell.isSmelly(classBean) ? "1" : "0";
                        String isIDSSmell = idsSmell.isSmelly(classBean) ? "1" : "0";
                        String isISSmell = isSmell.isSmelly(classBean) ? "1" : "0";
                        String isLTSmell = ltSmell.isSmelly(classBean) ? "1" : "0";
                        String isMIMSmell = mimSmell.isSmelly(classBean) ? "1" : "0";


                        writer.write(
                                commit.getHash(),
                                commit.getAuthor().getName(),
                                commit.getMsg(),
                                classBean.getQualifiedName(),
                                isDWSmell,
                                isIDSSmell,
                                isISSmell,
                                isLTSmell,
                                isMIMSmell
                        );

                    }
                }
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
                "DW_Smell",
                "IDS_Smell",
                "IS_Smell",
                "LT_Smell",
                "MIM_Smell"
        );
    }

}
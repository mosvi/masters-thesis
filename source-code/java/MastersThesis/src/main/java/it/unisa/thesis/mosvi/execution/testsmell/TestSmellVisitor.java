package it.unisa.thesis.mosvi.execution.testsmell;

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

public class TestSmellVisitor implements CommitVisitor {


    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

        if(Analysis.count==4){
            CSVHeading(writer);
            Analysis.count++;
        }

        try {
            // Package filtering
            List<ClassBean> allClasses = parseProject(repo.getPath(), null);

            AssertionRoulette assertionRoulette = new AssertionRoulette();
            EagerTest eagerTest = new EagerTest();
            GeneralFixture generalFixture = new GeneralFixture();
            MysteryGuest mysteryGuest = new MysteryGuest();
            ResourceOptimistism resourceOptimistism = new ResourceOptimistism();



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

                        String isAssertionRoulette = assertionRoulette.isSmelly(classBean) ? "1" : "0";
                        String isEagerTest = eagerTest.isSmelly(classBean) ? "1" : "0";
                        String isGeneralFixture = generalFixture.isSmelly(classBean) ? "1" : "0";
                        String isMysteryGuest = mysteryGuest.isSmelly(classBean) ? "1" : "0";
                        String isResourceOptimistism = resourceOptimistism.isSmelly(classBean) ? "1" : "0";


                        writer.write(
                                commit.getHash(),
                                commit.getAuthor().getName(),
                                commit.getMsg(),
                                classBean.getQualifiedName(),
                                isAssertionRoulette,
                                isEagerTest,
                                isGeneralFixture,
                                isMysteryGuest,
                                isResourceOptimistism
                        );

                    }
                }
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
                "Assertion_Roulette",
                "Eager_Test",
                //"Lazy_Test",
                "General_Fixture",
                "Mystery_Guest",
                "Resource_Optimistism"
        );
    }

    private static List<ClassBean> parseProject(String repoPath, String tag) throws Exception {
        ProjectParser projectParser = new ProjectParser();
        return projectParser.getAllProjectClassBeans(tag, repoPath);
    }

}
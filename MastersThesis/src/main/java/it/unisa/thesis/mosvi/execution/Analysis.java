package it.unisa.thesis.mosvi.execution;

import it.unisa.thesis.mosvi.execution.andsmell.AndSmellVisitor;
import it.unisa.thesis.mosvi.execution.ckmetrics.CKMetricsVisitor;
import it.unisa.thesis.mosvi.execution.codesmell.DecorClassVisitor;
import it.unisa.thesis.mosvi.execution.codesmell.DecorMethodVisitor;
import it.unisa.thesis.mosvi.execution.testsmell.TestSmellVisitor;
import it.unisa.thesis.mosvi.utils.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Analysis implements Study {

    public static int count;
    private static String mAppName;

    public static void main(String[] args) throws FileNotFoundException {
        mAppName = "";
        count = 0;

        //file directory of csv file of applications
        String fileDir = "C:\\Users\\utente\\Desktop\\Thesis\\project\\apps.csv";

        //for reading apps name and github-link
        readCSV(fileDir);

    }

    public static void readCSV(String dir) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(dir));
        //parsing a CSV file into the constructor of Scanner class
        sc.useDelimiter(",");
        //setting comma as delimiter pattern
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            List<String> elements = Arrays.asList(line.split(","));

            AppCSV data = new AppCSV(elements.get(0), elements.get(1));
            data.setAppName(elements.get(0));
            data.setGithubUrl(elements.get(1));
            mAppName = "";
            count = 0;

            //Cloning app repository from github
            cloneRepo(data.getGithubUrl(), data.getAppName());

            //System.out.print(data.getAppName() + " "+ data.getGithubUrl()+ "\n");
        }
        sc.close();
        //closes the scanner
    }

    public static void cloneRepo(String url, String name) {
        try {

            Git.clone(url, "C:\\Users\\utente\\Desktop\\Thesis\\project\\cloned-apps\\" + name);
            mAppName = name;

            //start analysis through repodriller
            new RepoDriller().start(new Analysis());
            
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {

        //for android-specific smells
        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\utente\\Desktop\\Thesis\\project\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new AndSmellVisitor(), new CSVFile("C:\\Users\\utente\\Desktop\\Thesis\\project\\results\\android-specific-smells\\"
                        +mAppName+"-android-smells.csv"))
                .mine();

        //for ck-metrics
        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\utente\\Desktop\\Thesis\\project\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new CKMetricsVisitor(), new CSVFile("C:\\Users\\utente\\Desktop\\Thesis\\project\\results\\ck-metrics\\"
                        +mAppName+"-ck-metrics.csv"))
                .mine();

        //for class related code-smells
        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\utente\\Desktop\\Thesis\\project\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new DecorClassVisitor(), new CSVFile("C:\\Users\\utente\\Desktop\\Thesis\\project\\results\\class-smells\\"
                        +mAppName+"-class-smells.csv"))
                .mine();

        //for method related code-smells
        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\utente\\Desktop\\Thesis\\project\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new DecorMethodVisitor(), new CSVFile("C:\\Users\\utente\\Desktop\\Thesis\\project\\results\\method-smells\\"
                        +mAppName+"-method-smells.csv"))
                .mine();

        //for test-smells
        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\utente\\Desktop\\Thesis\\project\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new TestSmellVisitor(), new CSVFile("C:\\Users\\utente\\Desktop\\Thesis\\project\\results\\test-smells\\"
                        +mAppName+"-test-smells.csv"))
                .mine();
    }
}

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
import scala.util.regexp.Base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Analysis implements Study {

    public static int count;
    private static String mAppName;
    private static int mSno=0;
    public static String Base_Dir="F:\\Thesis\\project";

    public static void main(String[] args) {
        mAppName = "";
        count = 0;

        //file directory of csv file of applications
        //C:\\Users\\utente\\Desktop\\Thesis\\project

        String fileDir = Base_Dir + "\\apps.csv";

        try{
            //for reading apps name and github-link
            readCSV(fileDir);
        }catch (Exception e){
            System.out.println("Main Exception: "+e.getMessage());
        }

    }

    public static void readCSV(String dir) {

        try{
            Scanner sc = new Scanner(new File(dir));
            //parsing a CSV file into the constructor of Scanner class
            sc.useDelimiter(",");

            //mSno = 0;
            //setting comma as delimiter pattern
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<String> elements = Arrays.asList(line.split(","));

                AppCSV data = new AppCSV(elements.get(0), elements.get(1));
                mAppName = "";
                count = 0;
                //mSno++;
                //Cloning app repository from github
                cloneRepo(data.getGithubUrl(), data.getAppName());

                //System.out.print(mSno+" "+data.getAppName() + " "+ data.getGithubUrl()+ "\n");
            }
            sc.close();
            //closes the scanner
        }catch (Exception e){
            System.out.println("CSV Exception: "+e.getMessage());
        }
    }

    public static void cloneRepo(String url, String name) {
        try {

            Git.clone(url, Base_Dir + "\\cloned-apps\\" + name);
            mAppName = name;

            //start analysis through repodriller
            new RepoDriller().start(new Analysis());
            
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("Git Exception: "+e.getMessage());
        }
    }

    @Override
    public void execute() {

        //for android-specific smells
        new RepositoryMining()
                .in(GitRepository.singleProject(Base_Dir + "\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new AndSmellVisitor(), new CSVFile(Base_Dir + "\\results\\android-specific-smells\\"
                        +mAppName+"-android-smells.csv"))
                .mine();

        //for ck-metrics
        new RepositoryMining()
                .in(GitRepository.singleProject(Base_Dir + "\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new CKMetricsVisitor(), new CSVFile(Base_Dir + "\\results\\ck-metrics\\"
                        +mAppName+"-ck-metrics.csv"))
                .mine();

        //for class related code-smells
        new RepositoryMining()
                .in(GitRepository.singleProject(Base_Dir + "\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new DecorClassVisitor(), new CSVFile(Base_Dir + "\\results\\class-smells\\"
                        +mAppName+"-class-smells.csv"))
                .mine();

        //for method related code-smells
        new RepositoryMining()
                .in(GitRepository.singleProject(Base_Dir + "\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new DecorMethodVisitor(), new CSVFile(Base_Dir + "\\results\\method-smells\\"
                        +mAppName+"-method-smells.csv"))
                .mine();

        //for test-smells
        new RepositoryMining()
                .in(GitRepository.singleProject(Base_Dir + "\\cloned-apps\\" + mAppName))
                .through(Commits.all())
                .process(new TestSmellVisitor(), new CSVFile(Base_Dir + "\\results\\test-smells\\"
                        +mAppName+"-test-smells.csv"))
                .mine();
    }
}

package it.unisa.thesis.mosvi.utils.oldgit;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Process {

    private GitRepository gitRepository;

    private String stringAllPreviousCommits = "";

    // Init GitRepository (init AuthorRepository) (done)
    // Init BugZillaRepository (done)
    // Identify BugFix commit for all bug (done)
    // Identify FixInducingChanges for all bug (done)

    /**
     * Per ogni autore Prendo tutti i commit dell'autore Vedo se in questi
     * commit c'è un fixinducingchanges per qualche bug Per questi commit che
     * introducono bug controllo: - Quale era la conoscenza della classe
     * rispetto a tutte le altre finora commitatte (su 10 commit totali solo 2
     * avevano coinvolto quella classe) - Quale è la somiglianza semantica
     * rispetto a tutte le altre classi finora committate (copio in un file di
     * testo le classi committate, una volta ogni commit) - Somiglianza
     * strutturale
     */
    public void initGitRepository(String directoryPath) {
        this.gitRepository = new GitRepository();
        File directory = new File(directoryPath);
        this.gitRepository.setDirectory(directory);

        System.out.println("Init Repository");
        this.gitRepository.initialize();
        System.out.println("End init repository");
        //System.out.println("\nCommits: \n\n");
        // gitRepository.printAllCommits();
    }

    public void saveGitRepository(String filePath) {
        this.gitRepository.save(filePath);
    }

    public void initGitRepositoryFromFile(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            this.gitRepository = (GitRepository) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    //Could be depracated
    public void calculateAverage(int numOfAuthors, String path, String out) {
        int numOfcommitsGlobal = 0;
        int numOfRealIteration;
        try (FileWriter fw = new FileWriter(out);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("author name,"
                    + " author email,"
                    + " total commits,"
                    + " numAddedLines,"
                    + " numModifiedLines,"
                    + " numRemovedLines,"
                    + " numTouchedFiles,"
                    + " Conceptual Coupling - Dynamic Background,"
                    + " Conceptual Coupling - Dynamic Background Mean,"
                    + " Conceptual Coupling - Dynamic Background Median,"
                    + " Knowledge - Dynamic Background,"
                    + " Conceptual Coupling - Progressive Loss of Memory (Fixed Threshold),"
                    + " Conceptual Coupling - Progressive Loss of Memory (Fixed Threshold) Mean,"
                    + " Conceptual Coupling - Progressive Loss of Memory (Fixed Threshold) Median,"
                    + " Knowledge - Progressive Loss of Memory (Fixed Threshold),"
                    + " Conceptual Coupling - Progressive Loss of Memory (Percentage Threshold),"
                    + " Conceptual Coupling - Progressive Loss of Memory (Percentage Threshold) Mean,"
                    + " Conceptual Coupling - Progressive Loss of Memory (Percentage Threshold) Median,"
                    + " Knowledge - Progressive Loss of Memory (Percentage Threshold),"
                    + " Conceptual Coupling - Progressive Loss of Memory (Time Threshold),"
                    + " Conceptual Coupling - Progressive Loss of Memory (Time Threshold) Mean,"
                    + " Conceptual Coupling - Progressive Loss of Memory (Time Threshold) Median,"
                    + " Knowledge - Progressive Loss of Memory (Time Threshold)");
            // Variables
            int infoCum8 = 0;
            int infoCum9 = 0;
            int infoCum10 = 0;
            int infoCum11 = 0;
            double infoCum12 = 0;
            double infoCum13 = 0;
            double infoCum14 = 0;
            double infoCum15 = 0;
            double infoCum16 = 0;
            double infoCum17 = 0;
            double infoCum18 = 0;
            double infoCum19 = 0;
            double infoCum20 = 0;
            double infoCum21 = 0;
            double infoCum22 = 0;
            double infoCum23 = 0;
            double infoCum24 = 0;
            double infoCum25 = 0;
            double infoCum26 = 0;
            double infoCum27 = 0;
            // counters
            int countInfo8 = 0;
            int countInfo9 = 0;
            int countInfo10 = 0;
            int countInfo11 = 0;
            int countInfo12 = 0;
            int countInfo13 = 0;
            int countInfo14 = 0;
            int countInfo15 = 0;
            int countInfo16 = 0;
            int countInfo17 = 0;
            int countInfo18 = 0;
            int countInfo19 = 0;
            int countInfo20 = 0;
            int countInfo21 = 0;
            int countInfo22 = 0;
            int countInfo23 = 0;
            int countInfo24 = 0;
            int countInfo25 = 0;
            int countInfo26 = 0;
            int countInfo27 = 0;
            // average
            double averageInfo8 = 0;
            double averageInfo9 = 0;
            double averageInfo10 = 0;
            double averageInfo11 = 0;
            double averageInfo12 = 0;
            double averageInfo13 = 0;
            double averageInfo14 = 0;
            double averageInfo15 = 0;
            double averageInfo16 = 0;
            double averageInfo17 = 0;
            double averageInfo18 = 0;
            double averageInfo19 = 0;
            double averageInfo20 = 0;
            double averageInfo21 = 0;
            double averageInfo22 = 0;
            double averageInfo23 = 0;
            double averageInfo24 = 0;
            double averageInfo25 = 0;
            double averageInfo26 = 0;
            double averageInfo27 = 0;
            numOfRealIteration = 0;
            for (int i = 1; i < numOfAuthors; i++) {
                String csvFile = path + i + ".csv";
                BufferedReader br;
                String line;
                String cvsSplitBy = ",";
                boolean firstLine = true;
                boolean secondLine = false;

                // Variables
                int infoCumAut8 = 0;
                int infoCumAut9 = 0;
                int infoCumAut10 = 0;
                int infoCumAut11 = 0;
                double infoCumAut12 = 0;
                double infoCumAut13 = 0;
                double infoCumAut14 = 0;
                double infoCumAut15 = 0;
                double infoCumAut16 = 0;
                double infoCumAut17 = 0;
                double infoCumAut18 = 0;
                double infoCumAut19 = 0;
                double infoCumAut20 = 0;
                double infoCumAut21 = 0;
                double infoCumAut22 = 0;
                double infoCumAut23 = 0;
                double infoCumAut24 = 0;
                double infoCumAut25 = 0;
                double infoCumAut26 = 0;
                double infoCumAut27 = 0;

                // Counters
                int countAutInfo8 = 0;
                int countAutInfo9 = 0;
                int countAutInfo10 = 0;
                int countAutInfo11 = 0;
                int countAutInfo12 = 0;
                int countAutInfo13 = 0;
                int countAutInfo14 = 0;
                int countAutInfo15 = 0;
                int countAutInfo16 = 0;
                int countAutInfo17 = 0;
                int countAutInfo18 = 0;
                int countAutInfo19 = 0;
                int countAutInfo20 = 0;
                int countAutInfo21 = 0;
                int countAutInfo22 = 0;
                int countAutInfo23 = 0;
                int countAutInfo24 = 0;
                int countAutInfo25 = 0;
                int countAutInfo26 = 0;
                int countAutInfo27 = 0;

                // average
                double averageAutInfo8 = 0;
                double averageAutInfo9 = 0;
                double averageAutInfo10 = 0;
                double averageAutInfo11 = 0;
                double averageAutInfo12 = 0;
                double averageAutInfo13 = 0;
                double averageAutInfo14 = 0;
                double averageAutInfo15 = 0;
                double averageAutInfo16 = 0;
                double averageAutInfo17 = 0;
                double averageAutInfo18 = 0;
                double averageAutInfo19 = 0;
                double averageAutInfo20 = 0;
                double averageAutInfo21 = 0;
                double averageAutInfo22 = 0;
                double averageAutInfo23 = 0;
                double averageAutInfo24 = 0;
                double averageAutInfo25 = 0;
                double averageAutInfo26 = 0;
                double averageAutInfo27 = 0;

                int numOfcommits = 0;
                try {
                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {
                        String[] commitInfo = line.split(cvsSplitBy);

                        if (secondLine) {
                            pw.print(commitInfo[0] + ",");
                            pw.print(commitInfo[1] + ",");
                            secondLine = false;
                        }
                        if (firstLine) {
                            firstLine = false;
                            secondLine = true;
                            continue;
                        }
                        numOfcommits++;
                        numOfcommitsGlobal++;

                        // System.out.println("Commitinfo: "+commitInfo[7]);
                        if (commitInfo.length < 8) {
                            continue;
                        }
                        if (commitInfo[7].equalsIgnoreCase("true")) {
                            numOfRealIteration++;
                            continue;
                        }

                        int info8;
                        try {
                            info8 = Integer.parseInt(commitInfo[8]);
                        } catch (Exception e) {
                            info8 = 0;
                        }
                        countAutInfo8++;
                        countInfo8++;
                        if (info8 > 0) {
                            infoCumAut8 = infoCumAut8 + info8;
                            infoCum8 = infoCum8 + info8;
                        }

                        int info9;
                        try {
                            info9 = Integer.parseInt(commitInfo[9]);
                        } catch (Exception e) {
                            info9 = 0;
                        }
                        countAutInfo9++;
                        countInfo9++;
                        if (info9 > 0) {
                            infoCumAut9 = infoCumAut9 + info9;
                            infoCum9 = infoCum9 + info9;
                        }

                        int info10;
                        try {
                            info10 = Integer.parseInt(commitInfo[10]);
                        } catch (Exception e) {
                            info10 = 0;
                        }
                        countAutInfo10++;
                        countInfo10++;
                        if (info10 > 0) {
                            infoCumAut10 = infoCumAut10 + info10;
                            infoCum10 = infoCum10 + info10;
                        }

                        int info11;
                        try {
                            info11 = Integer.parseInt(commitInfo[11]);
                        } catch (Exception e) {
                            info11 = 0;
                        }
                        countAutInfo11++;
                        countInfo11++;
                        if (info11 > 0) {
                            infoCumAut11 = infoCumAut11 + info11;
                            infoCum11 = infoCum11 + info11;
                        }

                        double info12;
                        try {
                            info12 = Double.parseDouble(commitInfo[12]);
                        } catch (Exception e) {
                            info12 = 0;
                        }
                        countAutInfo12++;
                        countInfo12++;
                        if (info12 > 0) {
                            infoCumAut12 = infoCumAut12 + info12;
                            infoCum12 = infoCum12 + info12;
                        }

                        double info13;
                        try {
                            info13 = Double.parseDouble(commitInfo[13]);
                        } catch (Exception e) {
                            info13 = 0;
                        }
                        if (info13 > 0) {
                            infoCumAut13 = infoCumAut13 + info13;
                            infoCum13 = infoCum13 + info13;
                            countAutInfo13++;
                            countInfo13++;
                        }

                        double info14;
                        try {
                            info14 = Double.parseDouble(commitInfo[14]);
                        } catch (Exception e) {
                            info14 = 0;
                        }
                        countAutInfo14++;
                        countInfo14++;
                        if (info14 > 0) {
                            infoCumAut14 = infoCumAut14 + info14;
                            infoCum14 = infoCum14 + info14;
                        }

                        double info15;
                        try {
                            info15 = Double.parseDouble(commitInfo[15]);
                        } catch (Exception e) {
                            info15 = 0;
                        }
                        if (info15 > 0) {
                            infoCumAut15 = infoCumAut15 + info15;
                            infoCum15 = infoCum15 + info15;
                            countAutInfo15++;
                            countInfo15++;
                        }

                        double info16;
                        try {
                            info16 = Double.parseDouble(commitInfo[16]);
                        } catch (Exception e) {
                            info16 = 0;
                        }
                        countAutInfo16++;
                        countInfo16++;
                        if (info16 > 0) {
                            infoCumAut16 = infoCumAut16 + info16;
                            infoCum16 = infoCum16 + info16;
                        }

                        double info17;
                        try {
                            info17 = Double.parseDouble(commitInfo[17]);
                        } catch (Exception e) {
                            info17 = 0;
                        }
                        if (info17 > 0) {
                            infoCumAut17 = infoCumAut17 + info17;
                            infoCum17 = infoCum17 + info17;
                            countAutInfo17++;
                            countInfo17++;
                        }

                        double info18;
                        try {
                            info18 = Double.parseDouble(commitInfo[18]);
                        } catch (Exception e) {
                            info18 = 0;
                        }
                        countAutInfo18++;
                        countInfo18++;
                        if (info18 > 0) {
                            infoCumAut18 = infoCumAut18 + info18;
                            infoCum18 = infoCum18 + info18;
                        }

                        double info19;
                        try {
                            info19 = Double.parseDouble(commitInfo[19]);
                        } catch (Exception e) {
                            info19 = 0;
                        }
                        if (info19 > 0) {
                            infoCumAut19 = infoCumAut19 + info19;
                            infoCum19 = infoCum19 + info19;
                            countAutInfo19++;
                            countInfo19++;
                        }

                        double info20;
                        try {
                            info20 = Double.parseDouble(commitInfo[20]);
                        } catch (Exception e) {
                            info20 = 0;
                        }
                        countAutInfo20++;
                        countInfo20++;
                        if (info20 > 0) {
                            infoCumAut20 = infoCumAut20 + info20;
                            infoCum20 = infoCum20 + info20;
                        }

                        double info21;
                        try {
                            info21 = Double.parseDouble(commitInfo[21]);
                        } catch (Exception e) {
                            info21 = 0;
                        }
                        if (info21 > 0) {
                            infoCumAut21 = infoCumAut21 + info21;
                            infoCum21 = infoCum21 + info21;
                            countAutInfo21++;
                            countInfo21++;
                        }

                        double info22;
                        try {
                            info22 = Double.parseDouble(commitInfo[22]);
                        } catch (Exception e) {
                            info22 = 0;
                        }
                        countAutInfo22++;
                        countInfo22++;
                        if (info22 > 0) {
                            infoCumAut22 = infoCumAut22 + info22;
                            infoCum22 = infoCum22 + info22;
                        }

                        double info23;
                        try {
                            info23 = Double.parseDouble(commitInfo[23]);
                        } catch (Exception e) {
                            info23 = 0;
                        }
                        if (info23 > 0) {
                            infoCumAut23 = infoCumAut23 + info23;
                            infoCum23 = infoCum23 + info23;
                            countAutInfo23++;
                            countInfo23++;
                        }

                        double info24;
                        try {
                            info24 = Double.parseDouble(commitInfo[24]);
                        } catch (Exception e) {
                            info24 = 0;
                        }
                        countAutInfo24++;
                        countInfo24++;
                        if (info24 > 0) {
                            infoCumAut24 = infoCumAut24 + info24;
                            infoCum24 = infoCum24 + info24;
                        }

                        double info25;
                        try {
                            info25 = Double.parseDouble(commitInfo[25]);
                        } catch (Exception e) {
                            info25 = 0;
                        }
                        if (info25 > 0) {
                            infoCumAut25 = infoCumAut25 + info25;
                            infoCum25 = infoCum25 + info25;
                            countAutInfo25++;
                            countInfo25++;
                        }

                        double info26;
                        try {
                            info26 = Double.parseDouble(commitInfo[26]);
                        } catch (Exception e) {
                            info26 = 0;
                        }
                        countAutInfo26++;
                        countInfo26++;
                        if (info26 > 0) {
                            infoCumAut26 = infoCumAut26 + info26;
                            infoCum26 = infoCum26 + info26;
                        }

                        double info27;
                        try {
                            info27 = Double.parseDouble(commitInfo[27]);
                        } catch (Exception e) {
                            info27 = 0;
                        }
                        if (info27 > 0) {
                            infoCumAut27 = infoCumAut27 + info27;
                            infoCum27 = infoCum27 + info27;
                            countAutInfo27++;
                            countInfo27++;
                        }
                    }

                    if (infoCumAut8 > 0 && countAutInfo8 > 0) {
                        averageAutInfo8 = infoCumAut8 / countAutInfo8;
                    }
                    if (infoCumAut9 > 0 && countAutInfo9 > 0) {
                        averageAutInfo9 = infoCumAut9 / countAutInfo9;
                    }
                    if (infoCumAut10 > 0 && countAutInfo10 > 0) {
                        averageAutInfo10 = infoCumAut10 / countAutInfo10;
                    }
                    if (infoCumAut11 > 0 && countAutInfo11 > 0) {
                        averageAutInfo11 = infoCumAut11 / countAutInfo11;
                    }
                    if (infoCumAut12 > 0 && countAutInfo12 > 0) {
                        averageAutInfo12 = infoCumAut12 / countAutInfo12;
                    }
                    if (infoCumAut13 > 0 && countAutInfo13 > 0) {
                        averageAutInfo13 = infoCumAut13 / countAutInfo13;
                    }
                    if (infoCumAut14 > 0 && countAutInfo14 > 0) {
                        averageAutInfo14 = infoCumAut14 / countAutInfo14;
                    }
                    if (infoCumAut15 > 0 && countAutInfo15 > 0) {
                        averageAutInfo15 = infoCumAut15 / countAutInfo15;
                    }
                    if (infoCumAut16 > 0 && countAutInfo16 > 0) {
                        averageAutInfo16 = infoCumAut16 / countAutInfo16;
                    }
                    if (infoCumAut17 > 0 && countAutInfo17 > 0) {
                        averageAutInfo17 = infoCumAut17 / countAutInfo17;
                    }
                    if (infoCumAut18 > 0 && countAutInfo18 > 0) {
                        averageAutInfo18 = infoCumAut18 / countAutInfo18;
                    }
                    if (infoCumAut19 > 0 && countAutInfo19 > 0) {
                        averageAutInfo19 = infoCumAut19 / countAutInfo19;
                    }
                    if (infoCumAut20 > 0 && countAutInfo20 > 0) {
                        averageAutInfo20 = infoCumAut20 / countAutInfo20;
                    }
                    if (infoCumAut21 > 0 && countAutInfo21 > 0) {
                        averageAutInfo21 = infoCumAut21 / countAutInfo21;
                    }
                    if (infoCumAut22 > 0 && countAutInfo22 > 0) {
                        averageAutInfo22 = infoCumAut22 / countAutInfo22;
                    }
                    if (infoCumAut23 > 0 && countAutInfo23 > 0) {
                        averageAutInfo23 = infoCumAut23 / countAutInfo23;
                    }
                    if (infoCumAut24 > 0 && countAutInfo24 > 0) {
                        averageAutInfo24 = infoCumAut24 / countAutInfo24;
                    }
                    if (infoCumAut25 > 0 && countAutInfo25 > 0) {
                        averageAutInfo25 = infoCumAut25 / countAutInfo25;
                    }
                    if (infoCumAut26 > 0 && countAutInfo26 > 0) {
                        averageAutInfo26 = infoCumAut26 / countAutInfo26;
                    }
                    if (infoCumAut27 > 0 && countAutInfo27 > 0) {
                        averageAutInfo27 = infoCumAut27 / countAutInfo27;
                    }

                    // print avarage
                    pw.print(numOfcommits + ",");
                    pw.print(averageAutInfo8 + ",");
                    pw.print(averageAutInfo9 + ",");
                    pw.print(averageAutInfo10 + ",");
                    pw.print(averageAutInfo11 + ",");
                    pw.print(averageAutInfo12 + ",");
                    pw.print(averageAutInfo13 + ",");
                    pw.print(averageAutInfo14 + ",");
                    pw.print(averageAutInfo15 + ",");
                    pw.print(averageAutInfo16 + ",");
                    pw.print(averageAutInfo17 + ",");
                    pw.print(averageAutInfo18 + ",");
                    pw.print(averageAutInfo19 + ",");
                    pw.print(averageAutInfo20 + ",");
                    pw.print(averageAutInfo21 + ",");
                    pw.print(averageAutInfo22 + ",");
                    pw.print(averageAutInfo23 + ",");
                    pw.print(averageAutInfo24 + ",");
                    pw.print(averageAutInfo25 + ",");
                    pw.print(averageAutInfo26 + ",");
                    pw.println(averageAutInfo27);

                } catch (IOException ex) {
                    Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (infoCum8 > 0 && countInfo8 > 0) {
                    averageInfo8 = infoCum8 / countInfo8;
                }
                if (infoCum9 > 0 && countInfo9 > 0) {
                    averageInfo9 = infoCum9 / countInfo9;
                }
                if (infoCum10 > 0 && countInfo10 > 0) {
                    averageInfo10 = infoCum10 / countInfo10;
                }
                if (infoCum11 > 0 && countInfo11 > 0) {
                    averageInfo11 = infoCum11 / countInfo11;
                }
                if (infoCum12 > 0 && countInfo12 > 0) {
                    averageInfo12 = infoCum12 / countInfo12;
                }
                if (infoCum13 > 0 && countInfo13 > 0) {
                    averageInfo13 = infoCum13 / countInfo13;
                }
                if (infoCum14 > 0 && countInfo14 > 0) {
                    averageInfo14 = infoCum14 / countInfo14;
                }
                if (infoCum15 > 0 && countInfo15 > 0) {
                    averageInfo15 = infoCum15 / countInfo15;
                }
                if (infoCum16 > 0 && countInfo16 > 0) {
                    averageInfo16 = infoCum16 / countInfo16;
                }
                if (infoCum17 > 0 && countInfo17 > 0) {
                    averageInfo17 = infoCum17 / countInfo17;
                }
                if (infoCum18 > 0 && countInfo18 > 0) {
                    averageInfo18 = infoCum18 / countInfo18;
                }
                if (infoCum19 > 0 && countInfo19 > 0) {
                    averageInfo19 = infoCum19 / countInfo19;
                }
                if (infoCum20 > 0 && countInfo20 > 0) {
                    averageInfo20 = infoCum20 / countInfo20;
                }
                if (infoCum21 > 0 && countInfo21 > 0) {
                    averageInfo21 = infoCum21 / countInfo21;
                }
                if (infoCum22 > 0 && countInfo22 > 0) {
                    averageInfo22 = infoCum22 / countInfo22;
                }
                if (infoCum23 > 0 && countInfo23 > 0) {
                    averageInfo23 = infoCum23 / countInfo23;
                }
                if (infoCum24 > 0 && countInfo24 > 0) {
                    averageInfo24 = infoCum24 / countInfo24;
                }
                if (infoCum25 > 0 && countInfo25 > 0) {
                    averageInfo25 = infoCum25 / countInfo25;
                }
                if (infoCum26 > 0 && countInfo26 > 0) {
                    averageInfo26 = infoCum26 / countInfo26;
                }
                if (infoCum27 > 0 && countInfo27 > 0) {
                    averageInfo27 = infoCum27 / countInfo27;
                }   // print avarege global
                pw.print("Total ,");
                pw.print("Total ,");
                pw.print(numOfcommitsGlobal + ",");
                pw.print(averageInfo8 + ",");
                pw.print(averageInfo9 + ",");
                pw.print(averageInfo10 + ",");
                pw.print(averageInfo11 + ",");
                pw.print(averageInfo12 + ",");
                pw.print(averageInfo13 + ",");
                pw.print(averageInfo14 + ",");
                pw.print(averageInfo15 + ",");
                pw.print(averageInfo16 + ",");
                pw.print(averageInfo17 + ",");
                pw.print(averageInfo18 + ",");
                pw.print(averageInfo19 + ",");
                pw.print(averageInfo20 + ",");
                pw.print(averageInfo21 + ",");
                pw.print(averageInfo22 + ",");
                pw.print(averageInfo23 + ",");
                pw.print(averageInfo24 + ",");
                pw.print(averageInfo25 + ",");
                pw.print(averageInfo26 + ",");
                pw.print(averageInfo27);
            }

            System.out.println("Iteration skipped: " + numOfRealIteration);

        } catch (IOException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateAverageLight(int numOfAuthors, String path, String out) {
        int numOfcommitsGlobal = 0;
        FileWriter fw;
        try {
            fw = new FileWriter(out);

            int numOfRealIteration;
            int totalnumofCommit;
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.println("author name,"
                        + " author email,"
                        + " total commits,"
                        + " numAddedLines,"
                        + " numModifiedLines,"
                        + " numRemovedLines,"
                        + " numTouchedFiles,"
                        + " Conceptual Coupling - Dynamic Background,"
                        + " Conceptual Coupling - Dynamic Background Mean,"
                        + " Conceptual Coupling - Dynamic Background Median,"
                        + " Knowledge - Dynamic Background,"
                        + " Conceptual Coupling - Progressive Loss of Memory (Time Threshold),"
                        + " Conceptual Coupling - Progressive Loss of Memory (Time Threshold) Mean,"
                        + " Conceptual Coupling - Progressive Loss of Memory (Time Threshold) Median,"
                        + " Knowledge - Progressive Loss of Memory (Time Threshold)");
                // Variables
                int infoCum8 = 0;
                int infoCum9 = 0;
                int infoCum10 = 0;
                int infoCum11 = 0;
                double infoCum12 = 0;
                double infoCum13 = 0;
                double infoCum14 = 0;
                double infoCum15 = 0;
                double infoCum16 = 0;
                double infoCum17 = 0;
                double infoCum18 = 0;
                double infoCum19 = 0;
                // counters
                int countInfo8 = 0;
                int countInfo9 = 0;
                int countInfo10 = 0;
                int countInfo11 = 0;
                int countInfo12 = 0;
                int countInfo13 = 0;
                int countInfo14 = 0;
                int countInfo15 = 0;
                int countInfo16 = 0;
                int countInfo17 = 0;
                int countInfo18 = 0;
                int countInfo19 = 0;
                // average
                double averageInfo8 = 0;
                double averageInfo9 = 0;
                double averageInfo10 = 0;
                double averageInfo11 = 0;
                double averageInfo12 = 0;
                double averageInfo13 = 0;
                double averageInfo14 = 0;
                double averageInfo15 = 0;
                double averageInfo16 = 0;
                double averageInfo17 = 0;
                double averageInfo18 = 0;
                double averageInfo19 = 0;
                numOfRealIteration = 0;
                totalnumofCommit = 0;
                for (int i = 0; i < numOfAuthors; i++) {
                    System.out.println("autor: " + i);
                    String csvFile = path + i + ".csv";
                    BufferedReader br;
                    String line;
                    String cvsSplitBy = ",";
                    boolean firstLine = true;
                    boolean secondLine = false;

                    // Variables
                    int infoCumAut8 = 0;
                    int infoCumAut9 = 0;
                    int infoCumAut10 = 0;
                    int infoCumAut11 = 0;
                    double infoCumAut12 = 0;
                    double infoCumAut13 = 0;
                    double infoCumAut14 = 0;
                    double infoCumAut15 = 0;
                    double infoCumAut16 = 0;
                    double infoCumAut17 = 0;
                    double infoCumAut18 = 0;
                    double infoCumAut19 = 0;

                    // Counters
                    int countAutInfo8 = 0;
                    int countAutInfo9 = 0;
                    int countAutInfo10 = 0;
                    int countAutInfo11 = 0;
                    int countAutInfo12 = 0;
                    int countAutInfo13 = 0;
                    int countAutInfo14 = 0;
                    int countAutInfo15 = 0;
                    int countAutInfo16 = 0;
                    int countAutInfo17 = 0;
                    int countAutInfo18 = 0;
                    int countAutInfo19 = 0;

                    // average
                    double averageAutInfo8 = 0;
                    double averageAutInfo9 = 0;
                    double averageAutInfo10 = 0;
                    double averageAutInfo11 = 0;
                    double averageAutInfo12 = 0;
                    double averageAutInfo13 = 0;
                    double averageAutInfo14 = 0;
                    double averageAutInfo15 = 0;
                    double averageAutInfo16 = 0;
                    double averageAutInfo17 = 0;
                    double averageAutInfo18 = 0;
                    double averageAutInfo19 = 0;

                    int numOfcommits = 0;
                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {
                        String[] commitInfo = line.split(cvsSplitBy);

                        if (secondLine) {
                            pw.print(commitInfo[0] + ",");
                            pw.print(commitInfo[1] + ",");
                            secondLine = false;
                        }
                        if (firstLine) {
                            firstLine = false;
                            secondLine = true;
                            continue;
                        }
                        numOfcommits++;
                        numOfcommitsGlobal++;

                        totalnumofCommit++;

                        // System.out.println("Commitinfo: "+commitInfo[7]);
                        if (commitInfo.length < 8) {
                            continue;
                        }

                        int info8;
                        try {
                            info8 = Integer.parseInt(commitInfo[8]);
                        } catch (Exception e) {
                            info8 = 0;
                        }
                        countAutInfo8++;
                        countInfo8++;
                        if (info8 > 0) {
                            infoCumAut8 = infoCumAut8 + info8;
                            infoCum8 = infoCum8 + info8;
                        }

                        int info9;
                        try {
                            info9 = Integer.parseInt(commitInfo[9]);
                        } catch (Exception e) {
                            info9 = 0;
                        }
                        countAutInfo9++;
                        countInfo9++;
                        if (info9 > 0) {
                            infoCumAut9 = infoCumAut9 + info9;
                            infoCum9 = infoCum9 + info9;
                        }

                        int info10;
                        try {
                            info10 = Integer.parseInt(commitInfo[10]);
                        } catch (Exception e) {
                            info10 = 0;
                        }
                        countAutInfo10++;
                        countInfo10++;
                        if (info10 > 0) {
                            infoCumAut10 = infoCumAut10 + info10;
                            infoCum10 = infoCum10 + info10;
                        }

                        int info11;
                        try {
                            info11 = Integer.parseInt(commitInfo[11]);
                        } catch (Exception e) {
                            info11 = 0;
                        }
                        countAutInfo11++;
                        countInfo11++;
                        if (info11 > 0) {
                            infoCumAut11 = infoCumAut11 + info11;
                            infoCum11 = infoCum11 + info11;
                        }

                        double info12;
                        try {
                            info12 = Double.parseDouble(commitInfo[12]);
                        } catch (Exception e) {
                            info12 = 0;
                        }
                        countAutInfo12++;
                        countInfo12++;
                        if (info12 > 0) {
                            infoCumAut12 = infoCumAut12 + info12;
                            infoCum12 = infoCum12 + info12;
                        }

                        double info13;
                        try {
                            info13 = Double.parseDouble(commitInfo[13]);
                        } catch (Exception e) {
                            info13 = 0;
                        }
                        if (info13 > 0) {
                            infoCumAut13 = infoCumAut13 + info13;
                            infoCum13 = infoCum13 + info13;
                            countAutInfo13++;
                            countInfo13++;
                        }

                        double info14;
                        try {
                            info14 = Double.parseDouble(commitInfo[14]);
                        } catch (Exception e) {
                            info14 = 0;
                        }
                        countAutInfo14++;
                        countInfo14++;
                        if (info14 > 0) {
                            infoCumAut14 = infoCumAut14 + info14;
                            infoCum14 = infoCum14 + info14;
                        }

                        double info15;
                        try {
                            info15 = Double.parseDouble(commitInfo[15]);
                        } catch (Exception e) {
                            info15 = 0;
                        }
                        if (info15 > 0) {
                            infoCumAut15 = infoCumAut15 + info15;
                            infoCum15 = infoCum15 + info15;
                            countAutInfo15++;
                            countInfo15++;
                        }

                        double info16;
                        try {
                            info16 = Double.parseDouble(commitInfo[16]);
                        } catch (Exception e) {
                            info16 = 0;
                        }
                        countAutInfo16++;
                        countInfo16++;
                        if (info16 > 0) {
                            infoCumAut16 = infoCumAut16 + info16;
                            infoCum16 = infoCum16 + info16;
                        }

                        double info17;
                        try {
                            info17 = Double.parseDouble(commitInfo[17]);
                        } catch (Exception e) {
                            info17 = 0;
                        }
                        if (info17 > 0) {
                            infoCumAut17 = infoCumAut17 + info17;
                            infoCum17 = infoCum17 + info17;
                            countAutInfo17++;
                            countInfo17++;
                        }

                        double info18;
                        try {
                            info18 = Double.parseDouble(commitInfo[18]);
                        } catch (Exception e) {
                            info18 = 0;
                        }
                        countAutInfo18++;
                        countInfo18++;
                        if (info18 > 0) {
                            infoCumAut18 = infoCumAut18 + info18;
                            infoCum18 = infoCum18 + info18;
                        }

                        double info19;
                        try {
                            info19 = Double.parseDouble(commitInfo[19]);
                        } catch (Exception e) {
                            info19 = 0;
                        }
                        if (info19 > 0) {
                            infoCumAut19 = infoCumAut19 + info19;
                            infoCum19 = infoCum19 + info19;
                            countAutInfo19++;
                            countInfo19++;
                        }

                    }

                    if (infoCumAut8 > 0 && countAutInfo8 > 0) {
                        averageAutInfo8 = infoCumAut8 / countAutInfo8;
                    }
                    if (infoCumAut9 > 0 && countAutInfo9 > 0) {
                        averageAutInfo9 = infoCumAut9 / countAutInfo9;
                    }
                    if (infoCumAut10 > 0 && countAutInfo10 > 0) {
                        averageAutInfo10 = infoCumAut10 / countAutInfo10;
                    }
                    if (infoCumAut11 > 0 && countAutInfo11 > 0) {
                        averageAutInfo11 = infoCumAut11 / countAutInfo11;
                    }
                    if (infoCumAut12 > 0 && countAutInfo12 > 0) {
                        averageAutInfo12 = infoCumAut12 / countAutInfo12;
                    }
                    if (infoCumAut13 > 0 && countAutInfo13 > 0) {
                        averageAutInfo13 = infoCumAut13 / countAutInfo13;
                    }
                    if (infoCumAut14 > 0 && countAutInfo14 > 0) {
                        averageAutInfo14 = infoCumAut14 / countAutInfo14;
                    }
                    if (infoCumAut15 > 0 && countAutInfo15 > 0) {
                        averageAutInfo15 = infoCumAut15 / countAutInfo15;
                    }
                    if (infoCumAut16 > 0 && countAutInfo16 > 0) {
                        averageAutInfo16 = infoCumAut16 / countAutInfo16;
                    }
                    if (infoCumAut17 > 0 && countAutInfo17 > 0) {
                        averageAutInfo17 = infoCumAut17 / countAutInfo17;
                    }
                    if (infoCumAut18 > 0 && countAutInfo18 > 0) {
                        averageAutInfo18 = infoCumAut18 / countAutInfo18;
                    }
                    if (infoCumAut19 > 0 && countAutInfo19 > 0) {
                        averageAutInfo19 = infoCumAut19 / countAutInfo19;
                    }

                    // print avarage
                    pw.print(numOfcommits + ",");
                    pw.print(averageAutInfo8 + ",");
                    pw.print(averageAutInfo9 + ",");
                    pw.print(averageAutInfo10 + ",");
                    pw.print(averageAutInfo11 + ",");
                    pw.print(averageAutInfo12 + ",");
                    pw.print(averageAutInfo13 + ",");
                    pw.print(averageAutInfo14 + ",");
                    pw.print(averageAutInfo15 + ",");
                    pw.print(averageAutInfo16 + ",");
                    pw.print(averageAutInfo17 + ",");
                    pw.print(averageAutInfo18 + ",");
                    pw.print(averageAutInfo19 + ",");
                    pw.println();

                }
                if (infoCum8 > 0 && countInfo8 > 0) {
                    averageInfo8 = infoCum8 / countInfo8;
                }
                if (infoCum9 > 0 && countInfo9 > 0) {
                    averageInfo9 = infoCum9 / countInfo9;
                }
                if (infoCum10 > 0 && countInfo10 > 0) {
                    averageInfo10 = infoCum10 / countInfo10;
                }
                if (infoCum11 > 0 && countInfo11 > 0) {
                    averageInfo11 = infoCum11 / countInfo11;
                }
                if (infoCum12 > 0 && countInfo12 > 0) {
                    averageInfo12 = infoCum12 / countInfo12;
                }
                if (infoCum13 > 0 && countInfo13 > 0) {
                    averageInfo13 = infoCum13 / countInfo13;
                }
                if (infoCum14 > 0 && countInfo14 > 0) {
                    averageInfo14 = infoCum14 / countInfo14;
                }
                if (infoCum15 > 0 && countInfo15 > 0) {
                    averageInfo15 = infoCum15 / countInfo15;
                }
                if (infoCum16 > 0 && countInfo16 > 0) {
                    averageInfo16 = infoCum16 / countInfo16;
                }
                if (infoCum17 > 0 && countInfo17 > 0) {
                    averageInfo17 = infoCum17 / countInfo17;
                }
                if (infoCum18 > 0 && countInfo18 > 0) {
                    averageInfo18 = infoCum18 / countInfo18;
                }
                if (infoCum19 > 0 && countInfo19 > 0) {
                    averageInfo19 = infoCum19 / countInfo19;
                }   // print avarege global
                pw.print("Total ,");
                pw.print("Total ,");
                pw.print(numOfcommitsGlobal + ",");
                pw.print(averageInfo8 + ",");
                pw.print(averageInfo9 + ",");
                pw.print(averageInfo10 + ",");
                pw.print(averageInfo11 + ",");
                pw.print(averageInfo12 + ",");
                pw.print(averageInfo13 + ",");
                pw.print(averageInfo14 + ",");
                pw.print(averageInfo15 + ",");
                pw.print(averageInfo16 + ",");
                pw.print(averageInfo17 + ",");
                pw.print(averageInfo18 + ",");
                pw.print(averageInfo19 + ",");
                pw.println();
                // Closing the statistic's file
                pw.flush();
            }
            fw.close();

            System.out.println("Total Commit: " + totalnumofCommit);
            System.out.println("Iteration skipped: " + numOfRealIteration);

        } catch (IOException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    public GitRepository getGitRepository() {
        return this.gitRepository;
    }

    public void setGitRepository(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

}

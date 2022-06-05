package it.unisa.thesis.mosvi.utils;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Git {
    public static void clone(String uri, String destFolder) throws GitAPIException {
        System.out.println(new Date()+" - Start cloning into " + destFolder);
        File destDirectory = new File(destFolder);
        if(!destDirectory.exists()) {
            destDirectory.mkdirs();

            org.eclipse.jgit.api.Git.cloneRepository()
                    .setURI(uri)
                    .setDirectory(destDirectory)
                    .call();
        } else {
            System.out.println("Repository already cloned");
        }
    }

    private static void listRepositoryContents(Repository repository) throws IOException {

        Ref head = repository.getRef("HEAD");

        // a RevWalk allows to walk over commits based on some filtering that is defined
        RevWalk walk = new RevWalk(repository);

        RevCommit commit = walk.parseCommit(head.getObjectId());
        RevTree tree = commit.getTree();
        System.out.println("Having tree: " + tree);

        // now use a TreeWalk to iterate over all files in the Tree recursively
        // you can set Filters to narrow down the results if needed
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        while (treeWalk.next()) {
            System.out.println("found: " + treeWalk.getPathString());
        }
    }

    public static void checkoutToTag(String repoPath, String tag) throws IOException, GitAPIException {
        System.out.println("Checkout at "+tag);
        org.eclipse.jgit.api.Git.open(new File(repoPath))
                .checkout()
                .setName(tag)
                .call();
    }
}

package it.unisa.thesis.mosvi.execution;

import it.unisa.thesis.mosvi.utils.parser.ClassParser;
import it.unisa.thesis.mosvi.utils.parser.ClassVisitor;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadSourceCode {

    public static List<ClassBean> readSourceCodeFromString(String source) {
        List<ClassBean> classes = new ArrayList<>();
        // Get the package
        String regex = "package .*;";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        String belongingPackage = "";

        if (matcher.find()) {
            belongingPackage = matcher.group();
        }

        belongingPackage = belongingPackage.replace("package ", "");
        belongingPackage = belongingPackage.replace(";", "");

        regex = "import .*;";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(source);

        ArrayList<String> imports = new ArrayList<>();

        while (matcher.find()) {
            String tmpImport = matcher.group();
            if (!tmpImport.startsWith("java.")) {
                imports.add(tmpImport);
            }
        }

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(source.toCharArray());

        CompilationUnit unit = (CompilationUnit) parser.createAST(null);

        Collection<TypeDeclaration> classNodes = new ArrayList<>();
        unit.accept(new ClassVisitor(classNodes));

        ArrayList<ClassBean> classBeans = new ArrayList<>();
        for (TypeDeclaration classNode : classNodes) {
            classBeans.add(ClassParser.parse(classNode, belongingPackage, imports, unit));
        }

        if (classBeans.size() > 0) {
            classes.add(classBeans.get(0));
        }

        return classes;

    }

    public static ArrayList<ClassBean> readSourceCode(File workTreeFile) throws IOException {

        ArrayList<ClassBean> classes = new ArrayList<>();

        if (workTreeFile.isDirectory() && !workTreeFile.getName().equals(".DS_Store") && !workTreeFile.getName().equals("bin")) {
            for (File f : workTreeFile.listFiles()) {
                classes.addAll(readSourceCode(f));
            }
        } else {
            if (workTreeFile.getName().endsWith(".java")) {
                classes.addAll(parseFile(workTreeFile));
            }
        }

        return classes;

    }

    public static List<ClassBean> parseFile(File workTreeFile) throws IOException {
        String source = readFile(workTreeFile.getAbsolutePath());
        return readSourceCodeFromString(source);
    }

    private static String readFile(String nomeFile) throws IOException {
        InputStream is;
        InputStreamReader isr = null;

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int len;

        try {
            is = new FileInputStream(nomeFile);
            isr = new InputStreamReader(is);

            while ((len = isr.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }

            return sb.toString();
        } finally {
            if (isr != null) {
                isr.close();
            }
        }
    }

}

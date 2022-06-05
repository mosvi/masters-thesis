package it.unisa.thesis.mosvi.utils.parser.bean;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.util.*;

public class ClassBean extends ComponentBean implements Comparable {

    private Collection<InstanceVariableBean> instanceVariables;
    private Collection<MethodBean> methods;
    private Collection<String> imports;
    private String superclass;
    private String belongingPackage;
    private String containingFileName;
    private TypeDeclaration typeDeclaration;
    private File sourceFile;

    public ClassBean() {
        super(null);
        instanceVariables = new ArrayList<>();
        methods = new ArrayList<>();
        setImports(new ArrayList<>());
    }

    public Collection<InstanceVariableBean> getInstanceVariables() {
        return instanceVariables;
    }

    public void setInstanceVariables(Collection<InstanceVariableBean> pInstanceVariables) {
        instanceVariables = pInstanceVariables;
    }

    public void addInstanceVariables(InstanceVariableBean pInstanceVariable) {
        instanceVariables.add(pInstanceVariable);
    }

    public void removeInstanceVariables(InstanceVariableBean pInstanceVariable) {
        instanceVariables.remove(pInstanceVariable);
    }

    public Collection<MethodBean> getMethods() {
        return methods;
    }

    public void setMethods(Collection<MethodBean> pMethods) {
        pMethods.forEach(this::addMethod);
    }

    public void addMethod(MethodBean pMethod) {
        methods.add(pMethod);
        pMethod.setBelongingClass(this);
    }

    public void removeMethod(MethodBean pMethod) {
        methods.remove(pMethod);
        pMethod.setBelongingClass(null);
    }

    public TypeDeclaration getTypeDeclaration() {
        return typeDeclaration;
    }

    public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getContainingFileName() {
        return containingFileName;
    }

    public void setContainingFileName(String containingFileName) {
        this.containingFileName = containingFileName;
    }

    @Override
    public String toString() {
        return "{ClassBean "+getQualifiedName()+"}";
    }

    @Override
    public int compareTo(Object pClassBean) {
        return this.getName().compareTo(((ClassBean) pClassBean).getName());
    }

    public Collection<String> getImports() {
        return imports;
    }

    public void setImports(Collection<String> imports) {
        this.imports = imports;
    }

    @Override
    public String getQualifiedName() {
        return belongingPackage + "." + name;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public String getBelongingPackage() {
        return belongingPackage;
    }

    public void setBelongingPackage(String belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    @Override
    public boolean equals(Object arg) {
        if (arg instanceof ClassBean) {
            return this.getName().equals(((ClassBean) arg).getName())
                    && this.getBelongingPackage().equals(((ClassBean) arg).getBelongingPackage());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.belongingPackage);
        return hash;
    }

    public Double textualClassCohesion() {
        List<Double> similaritiesBetweenMethods = new LinkedList<>();

        for(MethodBean method: getMethods()){
            for(MethodBean otherMethod: getMethods()){
                if(!method.equals(otherMethod)){
                    Double methodsSimilarity = method.textualSimilarityWith(otherMethod);
                    similaritiesBetweenMethods.add(methodsSimilarity);
                }
            }
        }
        Double sum = similaritiesBetweenMethods.stream().reduce(0.0, Double::sum);
        int nOfComparisons = similaritiesBetweenMethods.size();
        return nOfComparisons == 0 ? 1 : sum/ nOfComparisons;
    }
}

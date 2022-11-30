package it.unisa.thesis.mosvi.utils.parser.bean;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MethodBean extends ComponentBean implements Comparable<Object> {

    private Collection<InstanceVariableBean> usedInstanceVariables;
    private Collection<MethodBean> methodCalls;
    private Collection<InstanceVariableBean> foreignAccessedFields;
    private Collection<InstanceVariableBean> selfAccessedFields;
    private Collection<String> names;
    private Type returnType;
    private List<SingleVariableDeclaration> parameters;
    private ClassBean belongingClass;
    private Collection<MethodBlockBean> blocks;
    private int startLine;
    private int endLine;

    public MethodBean() {
        super(null);
        usedInstanceVariables = new ArrayList<>();
        methodCalls = new ArrayList<>();
        foreignAccessedFields = new ArrayList<>();
        blocks = new ArrayList<>();
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    @Override
    public String getQualifiedName() {
        return belongingClass.getQualifiedName() + "." + name;
    }

    public Collection<String> getNames(){
        return names;
    }
    
    public void setNames(Collection<String> names){
        this.names = names;
    }

    public Collection<InstanceVariableBean> getUsedInstanceVariables() {
        return usedInstanceVariables;
    }

    public void setUsedInstanceVariables(
            Collection<InstanceVariableBean> pUsedInstanceVariables) {
        usedInstanceVariables = pUsedInstanceVariables;
    }

    public void addUsedInstanceVariables(InstanceVariableBean pInstanceVariable) {
        usedInstanceVariables.add(pInstanceVariable);
    }

    public void removeUsedInstanceVariables(
            InstanceVariableBean pInstanceVariable) {
        usedInstanceVariables.remove(pInstanceVariable);
    }

    public Collection<MethodBean> getMethodCalls() {
        return methodCalls;
    }

    public void setMethodCalls(Collection<MethodBean> pMethodCalls) {
        methodCalls = pMethodCalls;
    }

    public void addMethodCalls(MethodBean pMethodCall) {
        methodCalls.add(pMethodCall);
    }

    public void removeMethodCalls(MethodBean pMethodCall) {
        methodCalls.remove(pMethodCall);
    }
    
     public Collection<InstanceVariableBean> getForeignAccessedFields() {
        return foreignAccessedFields;
    }

    public void setForeignAccessedFields(Collection<InstanceVariableBean> accessedFields) {
        this.foreignAccessedFields = accessedFields;
    }

    public void addForeignAccessedField(InstanceVariableBean accessedField) {
        foreignAccessedFields.add(accessedField);
    }

    public void removeForeignAccessedField(InstanceVariableBean accessedField) {
        foreignAccessedFields.remove(accessedField);
    }
    
     public Collection<InstanceVariableBean> getSelfAccessedFields() {
        return selfAccessedFields;
    }

    public void setSelfAccessedFields(Collection<InstanceVariableBean> accessedFields) {
        this.selfAccessedFields = accessedFields;
    }

    public void addSelfAccessedField(InstanceVariableBean accessedField) {
        selfAccessedFields.add(accessedField);
    }

    public void removeSelfAccessedField(InstanceVariableBean accessedField) {
        selfAccessedFields.remove(accessedField);
    }

    public void addMethodBlock(MethodBlockBean block) {
        blocks.add(block);
        block.setBelongingMethod(this);
    }

    public Collection<MethodBlockBean> getBlocks() {
        return blocks;
    }

    @Override
    public String toString() {
        return "{ MethodBean "+ getQualifiedName() + "}";
//        String string = "("
//                + name
//                + "|"
//                + (textContent.length() > 10 ? textContent.replace("\n", " ")
//                .replace("\t", "").substring(0, 10).concat("...") : "")
//                + "|";
//
//        for (InstanceVariableBean usedInstanceVariable : usedInstanceVariables) {
//            string += usedInstanceVariable.getName() + ",";
//        }
//        string = string.substring(0, string.length() - 1);
//        string += "|";
//
//        for (MethodBean methodCall : methodCalls) {
//            string += methodCall.getName() + ",";
//        }
//        string = string.substring(0, string.length() - 1);
//        string += ")";
//
//        return string;

    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((MethodBean) o).getName());
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<SingleVariableDeclaration> getParameters() {
        return parameters;
    }

    public void setParameters(List<SingleVariableDeclaration> parameters) {
        this.parameters = parameters;
    }

    public ClassBean getBelongingClass() {
        return belongingClass;
    }

    public void setBelongingClass(ClassBean belongingClass) {
        this.belongingClass = belongingClass;
    }
    

    @Override
    public boolean equals(Object arg) {
        if (arg instanceof MethodBean) {
            return this.getQualifiedName().equals(((MethodBean) arg).getQualifiedName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getQualifiedName());
        return hash;
    }
    
    public boolean isAccessor(){
        if (getUsedInstanceVariables().size() == 1 && getName().startsWith("get") && getParameters().size() == 0 && getMethodCalls().size() ==0 && (getLOC() - getCLOC())<10){
            return true;
        }
        return false;
    }
    
    public boolean accessesToForeignField(InstanceVariableBean ib){
        return foreignAccessedFields.contains(ib);
    }
    
    public boolean accessesToSelfField(InstanceVariableBean ib){
        return selfAccessedFields.contains(ib);
    }    
    
    public boolean isMutator(){
        if (getUsedInstanceVariables().size() == 1 && getName().startsWith("set") && getParameters().size() == 1  && getReturnType().getClass().equals(void.class)){
            return true;
        }
        return false;
    }

    public Double textualMethodCohesion() {
        Double methodsSimilarities = 0.0;
        int comparisons = 0;

        for (MethodBlockBean currentBlock: getBlocks()){
            for (MethodBlockBean otherBlock: getBlocks()){
                if(!currentBlock.equals(otherBlock)){
                    methodsSimilarities += currentBlock.textualSimilarityWith(otherBlock);
                    comparisons++;
                }
            }
        }
        return comparisons == 0 ? 1 : methodsSimilarities/comparisons;
    }

    public Double meanSimilarityWithOtherClassMethods() {
        Double similarity = 0.0;
        int comparisons = 0;

        for (MethodBean otherMethod : getBelongingClass().getMethods()){
            if(!otherMethod.equals(this)){
                similarity += textualSimilarityWith(otherMethod);
                comparisons += 1;
            }
        }

        return comparisons == 0 ? 1 : similarity/comparisons;
    }
}

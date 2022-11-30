package it.unisa.thesis.mosvi.utils.parser;

import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.CommentBean;
import it.unisa.thesis.mosvi.utils.parser.bean.InstanceVariableBean;
import it.unisa.thesis.mosvi.utils.parser.bean.MethodBean;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Collection;

public class ClassParser {

    public static ClassBean parse(TypeDeclaration pClassNode,
                                  String belongingPackage, ArrayList<String> imports, CompilationUnit unit) {

        // Instantiate the bean
        ClassBean classBean = new ClassBean();

        if (pClassNode.getSuperclassType() != null) {
            classBean.setSuperclass(pClassNode.getSuperclassType().toString());
        } else {
            classBean.setSuperclass(null);
        }

        // Set the name
        classBean.setName(pClassNode.getName().toString());
        classBean.setImports(imports);
        classBean.setBelongingPackage(belongingPackage);

        // Get the instance variable nodes
        Collection<FieldDeclaration> instanceVariableNodes = new ArrayList<>();
        pClassNode.accept(new InstanceVariableVisitor(instanceVariableNodes));

        String classTextContent = pClassNode.toString();
        classBean.setTextContent(classTextContent);

        // Get the comment nodes
        Collection<Comment> comments = new ArrayList<>();
        pClassNode.accept(new CommentVisitor(comments));
        for (Comment comment : comments) {
            classBean.addComment(new CommentBean(comment.toString()));
        }

        // Get the instance variable beans from the instance variable nodes
        Collection<InstanceVariableBean> instanceVariableBeans = new ArrayList<>();
        for (FieldDeclaration instanceVariableNode : instanceVariableNodes) {
            instanceVariableBeans.add(InstanceVariableParser
                    .parse(instanceVariableNode));
        }

        // Set the collection of instance variables
        classBean.setInstanceVariables(instanceVariableBeans);

        // Get the method nodes
        Collection<MethodDeclaration> methodNodes = new ArrayList<>();
        pClassNode.accept(new MethodVisitor(methodNodes));
      

        // Get the method beans from the method nodes
        Collection<MethodBean> methodBeans = new ArrayList<>();
        for (MethodDeclaration methodNode : methodNodes) {
            methodBeans.add(MethodParser.parse(methodNode,
                    instanceVariableBeans, classBean, unit));
        }

        // Iterate over the collection of methods
        for (MethodBean classMethod : methodBeans) {

            // Instantiate a collection of class-defined invocations
            Collection<MethodBean> definedInvocations = new ArrayList<>();

            // Get the method invocations
            Collection<MethodBean> classMethodInvocations = classMethod
                    .getMethodCalls();

            // Iterate over the collection of method invocations
            for (MethodBean classMethodInvocation : classMethodInvocations) {

                definedInvocations.add(classMethodInvocation);
            }

            // Set the class-defined invocations
            classMethod.setMethodCalls(definedInvocations);

        }

        // Set the collection of methods
        classBean.setMethods(methodBeans);

        return classBean;

    }
}

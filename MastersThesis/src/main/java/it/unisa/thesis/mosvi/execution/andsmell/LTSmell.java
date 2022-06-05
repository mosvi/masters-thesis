package it.unisa.thesis.mosvi.execution.andsmell;

import it.unisa.thesis.mosvi.utils.ast.ASTUtilities;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LTSmell implements AndroidSmell {
    private static final String ACTIVITY = "Activity";
    private static final String APP_COMPACT_ACTIVITY = "AppCompactActivity";
    private static final String THREAD = "Thread";
    private static final String START = "start";
    private static final String INTERRUPT = "interrupt";

    private static boolean isCreated(TypeDeclaration typeDecl, VariableDeclarationFragment threadVar) {
        List<ClassInstanceCreation> classInstanceCreations = Arrays.stream(typeDecl.getMethods())
                .flatMap(node -> ASTUtilities.getClassInstanceCreations(node).stream())
                .collect(Collectors.toList());
        for (ClassInstanceCreation classInstanceCreation : classInstanceCreations) {
            if (classInstanceCreation.getType().toString().equals(THREAD)) {
                if (classInstanceCreation.getParent().getNodeType() == ASTNode.ASSIGNMENT) {
                    Assignment assignment = (Assignment) classInstanceCreation.getParent();
                    String fieldName = assignment.getLeftHandSide().getNodeType() == ASTNode.FIELD_ACCESS ?
                            ((FieldAccess) assignment.getLeftHandSide()).getName().getIdentifier() :
                            assignment.getLeftHandSide().toString();
                    if (fieldName.equals(threadVar.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isStartedButNotInterrupted(TypeDeclaration typeDecl, VariableDeclarationFragment threadVar) {
        List<MethodInvocation> methodInvocations = Arrays.stream(typeDecl.getMethods())
                .flatMap(node -> ASTUtilities.getMethodInvocations(node).stream())
                .collect(Collectors.toList());
        // Only invocation that call start() (with or without this keyword) of an instance variable not shadowed by a local one
        List<MethodInvocation> threadInvocations = methodInvocations
                .stream()
                .filter(methodInv -> methodInv.getExpression() != null)
                .filter(methodInv -> methodInv.getExpression().toString().equals(threadVar.getName().getIdentifier())
                        || (methodInv.getExpression().getNodeType() == ASTNode.FIELD_ACCESS
                        && ((FieldAccess) methodInv.getExpression()).getName().getIdentifier().equals(threadVar.getName().getIdentifier())))
                .filter(methodInv -> methodInv.getExpression().resolveTypeBinding().getName().equals(THREAD))
                .collect(Collectors.toList());
        boolean foundStart = threadInvocations.stream().anyMatch(threadInv -> threadInv.getName().getIdentifier().equals(START));
        boolean foundInterrupt = threadInvocations.stream().anyMatch(threadInv -> threadInv.getName().getIdentifier().equals(INTERRUPT));
        return foundStart && !foundInterrupt;
    }

    public boolean isLTSmell(ClassBean classBean) {

        boolean isSmell = false;

        if (classBean == null) {
            isSmell = false;
        }

        TypeDeclaration typeDecl = classBean.getTypeDeclaration();
        Type superClassType = typeDecl.getSuperclassType();
        if (superClassType == null) {
            isSmell = false;
        } else{
            String superclassType = superClassType.toString();
            if (!superclassType.equals(ACTIVITY) && !superclassType.equals(APP_COMPACT_ACTIVITY)) {
                isSmell = false;
            }

            // Only Thread instance variables
            List<FieldDeclaration> fieldDecls = Arrays.asList(typeDecl.getFields());
            List<VariableDeclarationFragment> threadInstanceVars = new ArrayList<>();
            fieldDecls
                    .stream()
                    .filter(fieldDecl -> fieldDecl.getType().toString().equals(THREAD))
                    .map(FieldDeclaration::fragments)
                    .forEach(threadInstanceVars::addAll);
            for (VariableDeclarationFragment threadInstanceVar : threadInstanceVars) {
                // Check if there is a ClassInstanceCreation of that Thread
                if (isCreated(typeDecl, threadInstanceVar)) {
                    // Not so fine grained, but it's a start: check the presence of start() and the absence of interrupt()
                    if (isStartedButNotInterrupted(typeDecl, threadInstanceVar)) {
                        isSmell = true;
                    }
                }
            }
        }
        // Only for activity subclasses
//        assert superClassType != null;


        return isSmell;
    }


    @Override
    public boolean isSmelly(ComponentBean cb) {
        return isLTSmell((ClassBean) cb);
    }
}


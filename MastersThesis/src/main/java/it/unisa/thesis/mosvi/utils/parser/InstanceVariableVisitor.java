package it.unisa.thesis.mosvi.utils.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

class InstanceVariableVisitor extends ASTVisitor {

    private final Collection<FieldDeclaration> instanceVariableNodes;
    private boolean firstTime;

    public InstanceVariableVisitor(Collection<FieldDeclaration> pInstanceVariableNodes) {
        instanceVariableNodes = pInstanceVariableNodes;
        firstTime = true;
    }

    @Override
    public boolean visit(FieldDeclaration pInstanceVariableNode) {
        instanceVariableNodes.add(pInstanceVariableNode);
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration pClassNode) {
        if (firstTime) {
            firstTime = false;
            return true;
        }
        return firstTime;
    }

}

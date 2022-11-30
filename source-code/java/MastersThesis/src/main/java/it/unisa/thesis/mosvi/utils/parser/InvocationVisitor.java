package it.unisa.thesis.mosvi.utils.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

class InvocationVisitor extends ASTVisitor {

    private final Collection<String> invocations;

    public InvocationVisitor(Collection<String> pInvocations) {
        invocations = pInvocations;
    }

    @Override
    public boolean visit(MethodInvocation pInvocationNode) {
        invocations.add(pInvocationNode.getName().toString());
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration pClassNode) {
        return false;
    }

}

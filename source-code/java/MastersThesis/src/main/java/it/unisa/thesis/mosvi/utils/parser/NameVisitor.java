package it.unisa.thesis.mosvi.utils.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

class NameVisitor extends ASTVisitor {

    private final Collection<String> names;

    public NameVisitor(Collection<String> pNames) {
        names = pNames;
    }

    @Override
    public boolean visit(SimpleName pNameNode) {
        names.add(pNameNode.toString());
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration pClassNode) {
        return false;
    }

}

package it.unisa.thesis.mosvi.utils.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

public class ClassVisitor extends ASTVisitor {

    private final Collection<TypeDeclaration> classNodes;

    public ClassVisitor(Collection<TypeDeclaration> pClassNodes) {
        classNodes = pClassNodes;
    }

    @Override
    public boolean visit(TypeDeclaration pClassNode) {
        classNodes.add(pClassNode);
        return true;
    }
 

}

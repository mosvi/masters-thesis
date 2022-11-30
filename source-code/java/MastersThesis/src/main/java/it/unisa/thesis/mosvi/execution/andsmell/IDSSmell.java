package it.unisa.thesis.mosvi.execution.andsmell;

import it.unisa.thesis.mosvi.utils.ast.ASTUtilities;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import org.eclipse.jdt.core.dom.*;

import java.util.List;


public class IDSSmell implements AndroidSmell {

    private static final String HASHMAP = "HashMap";
    private static final String INTEGER = "Integer";


    public IDSSmell(){}

    public boolean isIDSSmell(ClassBean classBean) {

        boolean isSmell = false;

        if (classBean == null) {
            isSmell = false;
        }
        MethodDeclaration[] methods = classBean.getTypeDeclaration().getMethods();
        for (MethodDeclaration methodDecl : methods) {
            // Local variables
            List<VariableDeclarationStatement> variableDeclarationStatements = ASTUtilities.getVariableDeclarationStatements(methodDecl);
            if (variableDeclarationStatements == null) {
                isSmell = false;
            }
            for (VariableDeclarationStatement varDecl : variableDeclarationStatements) {
                if (hasHashMapIntegerObjectType(varDecl.getType())) {
                    isSmell = true;
                }
            }

        }
        return isSmell;
    }

    private static boolean hasHashMapIntegerObjectType(Type typeNode) {
        if (!typeNode.isParameterizedType()) {
            return false;
        }
        ParameterizedType parameterizedType = (ParameterizedType) typeNode;
        if (!parameterizedType.getType().isSimpleType()) {
            return false;
        }
        SimpleType type = (SimpleType) parameterizedType.getType();
        if (!type.getName().toString().equals(HASHMAP)) {
            return false;
        }
        List<Type> typeParameters = parameterizedType.typeArguments();
        if (!typeParameters.get(0).isSimpleType()) {
            return false;
        }
        SimpleType FirstParType = (SimpleType) typeParameters.get(0);
        return FirstParType.getName().toString().equals(INTEGER) && typeParameters.get(1).isSimpleType();
    }

    @Override
    public boolean isSmelly(ComponentBean cb) {
        return isIDSSmell((ClassBean) cb);
    }
}

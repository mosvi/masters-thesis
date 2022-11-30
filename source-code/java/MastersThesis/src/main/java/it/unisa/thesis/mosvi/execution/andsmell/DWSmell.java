package it.unisa.thesis.mosvi.execution.andsmell;


import it.unisa.thesis.mosvi.utils.ast.ASTUtilities;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import org.eclipse.jdt.core.dom.*;

import java.util.List;

public class DWSmell implements AndroidSmell {
    private static final String ACQUIRE = "acquire";
    private static final String RELEASE = "release";
    private static final String POWER_MANAGER_WAKELOCK = "PowerManager.WakeLock";

    public DWSmell(){
    }

    public boolean isDWSmell(ClassBean classBean) {

        boolean isSmell = false;

        if (classBean == null) {
            isSmell = false;
        }
        MethodDeclaration[] methods = classBean.getTypeDeclaration().getMethods();

        for (MethodDeclaration methodDecl : methods) {
            List<MethodInvocation> methodInvocations = ASTUtilities.getMethodInvocations(methodDecl);


            if (methodInvocations == null) {
                isSmell = false;
            }

            for (int i = 0; i < methodInvocations.size(); i++) {
                MethodInvocation iMethodInvocation = methodInvocations.get(i);
                if (iMethodInvocation.getName().getIdentifier().equals(ACQUIRE) && iMethodInvocation.arguments().size() == 0) {
                    // Check if this variable has PowerManager.Wakelock type
                    if (hasPowerManagerWakelockType(iMethodInvocation.getExpression())) {
                        String acquireCaller = iMethodInvocation.getExpression().toString();
                        boolean found = false;
                        for (int j = i + 1; j < methodInvocations.size() && !found; j++) {
                            MethodInvocation jMethodInvocation = methodInvocations.get(j);
                            if (jMethodInvocation.getName().getIdentifier().equals(RELEASE)
                                    && jMethodInvocation.getExpression().toString().equals(acquireCaller)) {
                                found = true;
                            }
                        }
                        if (!found) {
                            isSmell = true;
                        }
                    }
                }
            }
        }
        return isSmell;
    }

    private static boolean hasPowerManagerWakelockType(Expression expression) {
        List<FieldDeclaration> fieldDecls = ASTUtilities.getFieldDeclarations(expression.getRoot());
        List<VariableDeclarationStatement> varDecls = ASTUtilities.getVariableDeclarationStatements(expression.getRoot());

        if (fieldDecls == null || varDecls == null) {
            return false;
        }
        for (FieldDeclaration fieldDecl : fieldDecls) {
            if (fieldDecl.getType().toString().equals(POWER_MANAGER_WAKELOCK)) {
                return true;
            }
        }
        for (VariableDeclarationStatement varDecl : varDecls) {
            if (varDecl.getType().toString().equals(POWER_MANAGER_WAKELOCK)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSmelly(ComponentBean cb) {
        return isDWSmell((ClassBean) cb);
    }
}


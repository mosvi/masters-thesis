package it.unisa.thesis.mosvi.execution.codesmell;

import it.unisa.thesis.mosvi.execution.ckmetrics.CKMetrics;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import it.unisa.thesis.mosvi.utils.parser.bean.MethodBean;

import java.util.Collection;



/*
 * 
   RULE_CARD: SpaghettiCode {
	RULE: SpaghettiCode {INTER: NoInheritanceClassGlobalVariable LongMethodMethodNoParameter} 
	RULE: LongMethodMethodNoParameter {INTER LongMethod MethodNoParameter}
	RULE: LongMethod { (METRIC: METHOD_LOC, VERY_HIGH, 0) }
	RULE: MethodNoParameter { (STRUCT: METHOD_NO_PARAM) }
	RULE: NoInheritanceClassGlobalVariable {INTER NoInheritance ClassGlobalVariable}
	RULE: NoInheritance { (METRIC: DIT, INF_EQ, 2, 0) }
	RULE: ClassGlobalVariable {INTER ClassOneMethod FieldPrivate}
	RULE: ClassOneMethod { (STRUCT: GLOBAL_VARIABLE, 1) } };

 * 
 */
public class SpaghettiCodeRule implements DetectionRule {

	public boolean isSpaghettiCode(ClassBean pClass) {
		          Collection<MethodBean> methods = pClass.getMethods();

		if(CKMetrics.getELOC(pClass) > 600) {
			if(hasLongMethodNoParameter(methods))
				return true;
		}
		return false;
	}

	private boolean hasLongMethodNoParameter(Collection<MethodBean> pMethods) {

		for(MethodBean methodBean: pMethods) {
			String[] tokenizedTextualContent = methodBean.getTextContent().split("\n");

			if( (tokenizedTextualContent.length > 100) || (methodBean.getParameters().size() == 0) )
				return true;
		}

		return false;
	}

    @Override
    public boolean isSmelly(ComponentBean cb) {
        return isSpaghettiCode((ClassBean) cb);
    }

}

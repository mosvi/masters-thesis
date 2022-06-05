package it.unisa.thesis.mosvi.execution.codesmell;

import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import it.unisa.thesis.mosvi.utils.parser.bean.MethodBean;


public class FeatureEnvyRule implements DetectionRule {

	public boolean isFeatureEnvy(MethodBean pMethod) {

		if(pMethod.getBelongingClass().getInstanceVariables().size() > 0) {	
			if( (pMethod.getMethodCalls().size() > 10) && 
					( (pMethod.getUsedInstanceVariables().size()/pMethod.getBelongingClass().getInstanceVariables().size()) > 5 ) ) 
				return true;
		} else if(pMethod.getBelongingClass().getInstanceVariables().size() == 0) {
			if(pMethod.getMethodCalls().size() > 10)
				return true;
		}
		return false;
	}

	public boolean isFeatureEnvy(int threshold, MethodBean pMethod, ClassBean pBelongingClass, ClassBean pCandidateEnvyClass) {

		int dependenciesWithCandidateEnvyClass = computeDependencies(pMethod, pCandidateEnvyClass);
		int dependenciesWithBelongingClass = computeDependencies(pMethod, pBelongingClass);
		double structuralDiff = dependenciesWithCandidateEnvyClass - dependenciesWithBelongingClass;

		if(structuralDiff > threshold) {
			return true;
		}

		return false;
	}

	private int computeDependencies(MethodBean pMethod, ClassBean pClass) {
		int dependencies = 0;

		for(MethodBean calledMethod : pMethod.getMethodCalls()) {

			for(MethodBean classMethod: pClass.getMethods()) {
				if(calledMethod.getName().equals(classMethod.getName())) 
					dependencies++;
			}

		}

		return dependencies;
	}

    @Override
    public boolean isSmelly(ComponentBean cb) {
        return isFeatureEnvy((MethodBean) cb);
    }
}

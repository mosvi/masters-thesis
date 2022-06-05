package it.unisa.thesis.mosvi.execution.codesmell;

import it.unisa.thesis.mosvi.execution.ckmetrics.CKMetrics;
import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;


public class ComplexClassRule implements DetectionRule {

	public boolean isComplexClass(ClassBean pClass) {

		if(CKMetrics.getMcCabeMetric(pClass) > 200)
				return true;

		return false;
	}

    @Override
    public boolean isSmelly(ComponentBean cb) {
        return isComplexClass((ClassBean)cb);
    }
}

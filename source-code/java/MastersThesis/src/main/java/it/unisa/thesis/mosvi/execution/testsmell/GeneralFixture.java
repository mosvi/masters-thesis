package it.unisa.thesis.mosvi.execution.testsmell;

import java.util.Vector;

import it.unisa.thesis.mosvi.utils.parser.bean.*;

public class GeneralFixture implements TestSmell{

	public boolean isGeneralFixture(ClassBean pClassBean) {

		boolean isTest = false;

		for (MethodBean mb : pClassBean.getMethods()) {
			
			if (mb.getName().toLowerCase().equals("setup")) {
				Vector<InstanceVariableBean> instanceVariablesInsideSetUp = (Vector<InstanceVariableBean>) mb
						.getUsedInstanceVariables();

				if (instanceVariablesInsideSetUp.size() > 0) {
					for (MethodBean mbInside : pClassBean.getMethods()) {
						if (!mbInside.getName().equals(
								pClassBean.getName())
								&& !mbInside.getName()
								.toLowerCase()
								.equals("setup")
								&& !mbInside.getName()
								.toLowerCase()
								.equals("teardown")) {
							Vector<InstanceVariableBean> tmpUsed = (Vector<InstanceVariableBean>) mbInside
									.getUsedInstanceVariables();
							for (InstanceVariableBean ivb : instanceVariablesInsideSetUp) {
								if (!tmpUsed.contains(ivb)) {
									isTest = true;
								}
							}
						} 
					}

				}
			}
		}
		
		return isTest;
	}

	@Override
	public boolean isSmelly(ComponentBean cb) {
		return isGeneralFixture((ClassBean) cb);
	}
}

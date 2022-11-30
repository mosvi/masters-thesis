package it.unisa.thesis.mosvi.execution.testsmell;

import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import it.unisa.thesis.mosvi.utils.parser.bean.MethodBean;

import java.util.Vector;

public class EagerTest implements TestSmell{
	
	public boolean isEagerTest(ClassBean pClassBean, ClassBean pProductionClass) {
		boolean eagerTest = false;
		
		for (MethodBean mb : pClassBean.getMethods()) {
			if (!mb.getName().equals(pClassBean.getName())
					&& !mb.getName().toLowerCase()
					.equals("setup")
					&& !mb.getName().toLowerCase()
					.equals("teardown") && !eagerTest) {

				//Vector<MethodBean> calledMethods = (Vector<MethodBean>) mb.getMethodCalls();
				Vector<MethodBean> calledMethods = new Vector<MethodBean>(mb.getMethodCalls());

				if (calledMethods.size() > 1){
					//Vector<MethodBean> cbMethods = (Vector<MethodBean>) pProductionClass.getMethods();
					Vector<MethodBean> cbMethods = new Vector<MethodBean>(pProductionClass.getMethods());
						int count = 0;
						for(MethodBean cm : calledMethods){
							for (MethodBean cbMethod : cbMethods){
								if (cbMethod.getName().toLowerCase().equals(cm.getName().toLowerCase())){
									count++;
								}
							}
						}

						if(count > 1)
							eagerTest = true;
					}
			}
		}

		return eagerTest;
	}

	@Override
	public boolean isSmelly(ComponentBean cb) {
		return isEagerTest((ClassBean) cb,(ClassBean) cb);
	}

}

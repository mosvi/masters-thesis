package it.unisa.thesis.mosvi.execution.testsmell;

import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import it.unisa.thesis.mosvi.utils.parser.bean.MethodBean;

public class MysteryGuest implements TestSmell{

	public boolean isMysteryGuest(ClassBean pClassBean) {
		boolean mysteryGuest = false;

		for (MethodBean mb : pClassBean.getMethods()) {
			String body = mb.getTextContent();

			if (!mysteryGuest){
				if (body.contains(" File ") || body.contains(" File(") || body.contains("db")){
					mysteryGuest = true;
				}
			}
		}

		return mysteryGuest;
	}

	@Override
	public boolean isSmelly(ComponentBean cb) {
		return isMysteryGuest((ClassBean) cb);
	}
}
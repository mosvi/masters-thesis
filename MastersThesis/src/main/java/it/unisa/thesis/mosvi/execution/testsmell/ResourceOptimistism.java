package it.unisa.thesis.mosvi.execution.testsmell;

import java.io.File;

import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;

public class ResourceOptimistism implements TestSmell{

	public boolean isResourceOptimistism(ClassBean pTestSuite) {
		String code = pTestSuite.getTextContent();
		String lines[] = code.split("\n");
		
		for(int k=0; k<lines.length; k++) {
			if(lines[k].contains(" File ")) {
				
				if(lines[k].indexOf("\"") != -1) {
					String definedPath = lines[k].substring(lines[k].indexOf("\"")+1, lines[k].lastIndexOf("\""));
					
					File definedFile = new File(definedPath);
					
					if(! definedFile.exists()) 
						return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean isSmelly(ComponentBean cb) {
		return isResourceOptimistism((ClassBean) cb);
	}
	
}

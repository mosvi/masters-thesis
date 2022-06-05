package it.unisa.thesis.mosvi.execution.testsmell;


import it.unisa.thesis.mosvi.utils.parser.bean.ClassBean;
import it.unisa.thesis.mosvi.utils.parser.bean.ComponentBean;
import it.unisa.thesis.mosvi.utils.parser.bean.MethodBean;

public class AssertionRoulette implements TestSmell{

	public boolean isAssertionRoulette(ClassBean pClassBean) {
		boolean assertionRoulette = false;
		
		for (MethodBean mb : pClassBean.getMethods()) {
			if (!assertionRoulette) {
				String methodBody = mb.getTextContent();
				methodBody = methodBody.replace(mb.getName(),
						"");

				while (methodBody.contains("assert")) {
					int indexStart = methodBody
							.indexOf("assert");
					int indexEnd = indexStart;
					char c = methodBody.charAt(indexStart);
					String substring = c + "";
					while (c != ';' && c != '}') {
						c = methodBody.charAt(indexEnd++);
						substring += c + "";
					}

					if (!substring.contains("\"")) {
						assertionRoulette = true;
					}

					methodBody = methodBody.replaceFirst(
							"assert", "");
				}

				while (methodBody.contains("fail(")) {
					int indexStart = methodBody
							.indexOf("fail(");
					int indexEnd = indexStart;
					char c = methodBody.charAt(indexStart);
					String substring = c + "";
					while (c != ';') {
						indexEnd++;
						c = methodBody.charAt(indexEnd);
						substring += c + "";
					}

					if (!substring.contains("\"")) {
						assertionRoulette = true;
					}

					methodBody = methodBody.replaceFirst(
							"fail\\(", "");
				}

				while (methodBody.contains("fail (")) {
					int indexStart = methodBody
							.indexOf("fail (");
					int indexEnd = indexStart;
					char c = methodBody.charAt(indexStart);
					String substring = c + "";
					while (c != ';') {
						indexEnd++;
						c = methodBody.charAt(indexEnd);
						substring += c + "";
					}

					if (!substring.contains("\"")) {
						assertionRoulette = true;
					}

					methodBody = methodBody.replaceFirst(
							"fail \\(", "");
				}
			}
		}
		
		return assertionRoulette;
	}

	@Override
	public boolean isSmelly(ComponentBean cb) {
		return isAssertionRoulette((ClassBean) cb);
	}
}

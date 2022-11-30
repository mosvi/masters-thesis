package it.unisa.thesis.mosvi.utils.parser;

import it.unisa.thesis.mosvi.utils.parser.bean.MethodBean;


class InvocationParser {

    public static MethodBean parse(String pInvocationName) {
        MethodBean methodBean = new MethodBean();
        methodBean.setName(pInvocationName);
        return methodBean;
    }

}

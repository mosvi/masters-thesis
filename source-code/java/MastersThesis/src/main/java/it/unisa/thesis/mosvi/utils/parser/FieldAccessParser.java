package it.unisa.thesis.mosvi.utils.parser;

import it.unisa.thesis.mosvi.utils.parser.bean.InstanceVariableBean;


class FieldAccessParser {

    public static InstanceVariableBean parse(String fieldAccessName) {
        InstanceVariableBean ivb = new InstanceVariableBean();
        ivb.setName(fieldAccessName);
        return ivb;
    }

}

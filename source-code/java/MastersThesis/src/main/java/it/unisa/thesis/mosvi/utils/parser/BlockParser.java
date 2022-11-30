package it.unisa.thesis.mosvi.utils.parser;

import it.unisa.thesis.mosvi.utils.parser.bean.MethodBlockBean;
import org.eclipse.jdt.core.dom.Block;

public class BlockParser {

    public static MethodBlockBean parse(Block block) {
        MethodBlockBean blockBean = new MethodBlockBean();
        blockBean.setTextContent(block.toString());
        return blockBean;
    }

}

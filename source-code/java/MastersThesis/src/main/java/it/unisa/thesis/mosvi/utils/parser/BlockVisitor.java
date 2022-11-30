package it.unisa.thesis.mosvi.utils.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class allows to visit the Blocks node (i.e.: the body of a method) of the AST representing the Class;
 *
 * @author Fabio Palomba;
 */
public class BlockVisitor extends ASTVisitor {
    private final List<Block> blocks = new ArrayList<>();

    @Override
    public boolean visit(Block node) {
        blocks.add(node);

        return super.visit(node);
    }

    /**
     * This method allows to get all the Blocks for the Class on which it is;
     *
     * @return a List of all Blocks;
     */
    public Collection<Block> getBlocks() {
        return blocks;
    }
}

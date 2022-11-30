package it.unisa.thesis.mosvi.utils.parser;

import org.eclipse.jdt.core.dom.*;

import java.util.Collection;

class CommentVisitor extends ASTVisitor {

    private final Collection<Comment> commentNodes;

    public CommentVisitor(Collection<Comment> commentNodes) {
        this.commentNodes = commentNodes;
    }

    @Override
    public boolean visit(BlockComment comment) {
        commentNodes.add(comment);
        return true;
    }
    
     @Override
    public boolean visit(Javadoc comment) {
        commentNodes.add(comment);
        return true;
    }
    
     @Override
    public boolean visit(LineComment comment) {
        commentNodes.add(comment);
        return true;
    }

}

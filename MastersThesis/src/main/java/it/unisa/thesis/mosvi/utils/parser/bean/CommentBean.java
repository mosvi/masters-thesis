package it.unisa.thesis.mosvi.utils.parser.bean;

import java.util.Objects;

public final class CommentBean {
    private final String commentText;

    public CommentBean(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentText() {
        return commentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentBean that = (CommentBean) o;

        return Objects.equals(commentText, that.commentText);
    }

    @Override
    public int hashCode() {
        return commentText != null ? commentText.hashCode() : 0;
    }
}

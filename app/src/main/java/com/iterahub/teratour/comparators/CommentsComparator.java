package com.iterahub.teratour.comparators;

import com.iterahub.teratour.models.CommentModel;

import java.util.Comparator;

public class CommentsComparator implements Comparator<CommentModel> {
    @Override
    public int compare(CommentModel c1, CommentModel c2) {
        return c1.getId().compareTo(c2.getId());
    }
}

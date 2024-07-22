package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Comment;

import java.util.List;

public interface ICommentService {

    Comment createComment(Long issueId, Long userId, String content) throws Exception;
    void deleteComment(Long commentId, Long userId) throws Exception;
    List<Comment> findCommentByIssueId(Long issueId) throws Exception;
}

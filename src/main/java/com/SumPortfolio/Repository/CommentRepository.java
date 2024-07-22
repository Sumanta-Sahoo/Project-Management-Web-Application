package com.SumPortfolio.Repository;

import com.SumPortfolio.Model.Comment;
import com.SumPortfolio.Model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIssueId(Long issueId);
}

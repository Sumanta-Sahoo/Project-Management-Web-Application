package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Comment;
import com.SumPortfolio.Model.Issue;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Repository.CommentRepository;
import com.SumPortfolio.Repository.IssueRepository;
import com.SumPortfolio.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {

        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalIssue.isEmpty())
            throw new Exception("Issue Not Found with issueId : " + issueId);
        if(optionalUser.isEmpty())
            throw new Exception("Issue Not Found with issueId : " + userId);

        Issue issue = optionalIssue.get();
        User user = optionalUser.get();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setIssue(issue);
        comment.setCreatedDateTime(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalComment.isEmpty())
            throw new Exception("Issue Not Found with issueId : " + commentId);
        if(optionalUser.isEmpty())
            throw new Exception("Issue Not Found with issueId : " + userId);

        Comment comment = optionalComment.get();
        User user = optionalUser.get();

        if(comment.getUser().equals(user))
            commentRepository.delete(comment);
        else
            throw new Exception("User having userId : " +userId + " is not authorized to delete the comment having commentId : " + commentId);

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) throws Exception {
        return commentRepository.findByIssueId(issueId);
    }
}

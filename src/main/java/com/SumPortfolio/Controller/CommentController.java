package com.SumPortfolio.Controller;

import com.SumPortfolio.Model.Comment;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Request.CreateCommentRequest;
import com.SumPortfolio.Response.MessageResponse;
import com.SumPortfolio.Service.ICommentService;
import com.SumPortfolio.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest createCommentRequest,
            @RequestHeader("Authorization") String token
            ) throws Exception{
        User user = userService.findUserByJwt(token);
        Comment createdComment = commentService.createComment(createCommentRequest.getIssueId(), user.getId(), createCommentRequest.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
        @PathVariable(required = false) Long commentId,
        @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Comment Successfully Deleted");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(
            @PathVariable(required = false) Long issueId
    ) throws Exception{
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}

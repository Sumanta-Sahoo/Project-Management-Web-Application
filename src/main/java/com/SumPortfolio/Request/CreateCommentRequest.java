package com.SumPortfolio.Request;

import com.SumPortfolio.Model.Issue;
import com.SumPortfolio.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

    private Long issueId;
    private String content;

}

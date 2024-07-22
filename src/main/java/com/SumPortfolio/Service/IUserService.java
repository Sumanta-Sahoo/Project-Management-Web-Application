package com.SumPortfolio.Service;

import com.SumPortfolio.Model.User;

public interface IUserService {

    User findUserByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws  Exception;

    User updateUserProjectSize(User user, Integer projectSize) throws Exception;

}

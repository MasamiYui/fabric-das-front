package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface UserMapper {

    HashMap<String, Object> findUserByPhone(String phone);

    HashMap<String, Object> findUserByEmail(String email);

    HashMap<String, Object> findUserByIdCard(String idcard);

    int addUser(User user);

    HashMap<String,Object> findUserById(@Param("id") String id);

    int  updateUserById(@Param("id") String id,@Param("password")String password);

}

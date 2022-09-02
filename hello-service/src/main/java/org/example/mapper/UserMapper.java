package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


/*加了这个注解 就表示了 这是一个Mybatis的mapper类
就相当于之前使用的spring整合mybatis接口 也可以使用@MapperScan("org.example.mapper")*/
@Mapper
public interface UserMapper {



    List<User> queryUserList();

    User queryUserById(int id);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);


}

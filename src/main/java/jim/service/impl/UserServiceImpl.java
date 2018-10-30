package jim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jim.dao.UserMapper;
import jim.model.User;
import jim.service.UserService;
import tk.mybatis.mapper.common.Mapper;

@Service
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService{
	 @Autowired
     private UserMapper userMapper;

     @Override
     public Mapper<User> getMapper() {
        return userMapper;
     }

     public User getUserById(int userId) {  
        return userMapper.selectByPrimaryKey(userId);  
     }  
     
     public User matchUser(String userName,String password) {
    	 User record=new User();
    	 record.setUserName(userName);
    	 record.setPassword(password);
         return userMapper.selectOne(record);
     }

}

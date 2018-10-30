package jim.service;

import jim.model.User;

public interface UserService {
	 
	  User getUserById(int userId);

	  User matchUser(String userName,String password);

}

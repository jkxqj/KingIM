package kingim.service;

import kingim.model.User;

public interface UserService {
	 
	  User getUserById(int userId);

	  User matchUser(String userName,String password);

}

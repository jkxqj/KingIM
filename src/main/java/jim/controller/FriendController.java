package jim.controller;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jim.model.User;
import jim.service.UserService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/friend")
public class FriendController {

	@Autowired
	UserService userService;
	
    @RequestMapping(value="/init/{userId}",produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String init(@PathVariable("userId")int userId){
        User user = userService.getUserById(userId);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mine = new HashMap<>();
        mine.put("username", user.getNickName());
        mine.put("id", user.getId());
        mine.put("status", "online");
        mine.put("sign", user.getSign());
        mine.put("avatar", user.getAvator());
        map.put("mine", mine);
        return JSON.toJSONString(map);
    }

    @RequestMapping("/test")
    public String test(){
        System.out.println("------------------------");
       return "main";
    }
}
import java.io.File;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nercel.cyberhouse.model.sns.UFile;
import com.nercel.cyberhouse.model.sns.UploadFile;
import com.nercel.cyberhouse.util.MongoUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("sns")
public class FileUpload {
	   @RequestMapping(value = "/uploadFile", produces = "text/plain; charset=utf-8")
	   public @ResponseBody String upload(@RequestParam(value = "file", required = false) MultipartFile file,int userId,HttpServletRequest request) {  
			UploadFile upFile = new UploadFile();
	        String path = request.getSession().getServletContext().getRealPath("upload/sns/"+userId+"/");  
	        String fileName = file.getOriginalFilename();
	        String name=UUID.randomUUID().toString()+"---"+fileName;
	        File targetFile = new File(path,name);  
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }  
	       try {  
	    	  file.transferTo(targetFile);  
	    	  upFile.setCode(0);
	    	  UFile uf=new UFile();
			  uf.setName(fileName);
	    	  uf.setSrc("upload/sns/"+userId+"/"+name);
	    	  upFile.setData(uf);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return JSON.toJSONString(upFile);    
	  }
}
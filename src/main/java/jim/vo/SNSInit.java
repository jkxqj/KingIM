package jim.vo;

/**
 * 即时聊天初始化model
 * @author jack
 */
public class SNSInit {
   //code=0 表示成功，其余表示失败
   private int code;
   // 失败信息,成功则为空
   private String msg;
   
   private SNSdata data;
  
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public SNSdata getData() {
		return data;
	}
	public void setData(SNSdata data) {
		this.data = data;
	}

   
}

package kingim.utils;

public enum ResponseType {
    SUCCESS(1,"成功"),
    FAIL(0,"失败"),
    USER_NOTFOUND(00,"用户不存在"),
    USER_WRONG(01,"用户名或密码不存在"),
    USER_DISABLE(02,"用户不可用"),
    LOGIN_NO(10,"未登录"),
    ERROR_DATABASE(20,"数据库错误"),
    APPLY_REPERT(30,"您已经发送过好友申请，请勿重复发送！"),
    APPLY_EXIST(31,"您已经添加该好友，请勿重复添加！");
    
    String msg;
    Integer code;
    
    private ResponseType(Integer code,String msg){
        this.msg = msg;
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    
}

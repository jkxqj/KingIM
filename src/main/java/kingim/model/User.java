package kingim.model;

import javax.persistence.*;

@Table(name = "t_user")
public class User {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 帐号
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 性别：0为男，1为女
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avator;

    /**
     * 在线状态：online为在线，hide为隐身，offline为离线
     */
    @Transient
    private String status;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 角色code：1为用户，2为管理员
     */
    @Column(name = "role_code")
    private Integer roleCode;

    private String version;

    private String sign;

    /**
     * 获取自增主键
     *
     * @return id - 自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增主键
     *
     * @param id 自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取帐号
     *
     * @return user_name - 帐号
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置帐号
     *
     * @param userName 帐号
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取性别：0为男，1为女
     *
     * @return gender - 性别：0为男，1为女
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置性别：0为男，1为女
     *
     * @param gender 性别：0为男，1为女
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取头像
     *
     * @return avator - 头像
     */
    public String getAvator() {
        return avator;
    }

    /**
     * 设置头像
     *
     * @param avator 头像
     */
    public void setAvator(String avator) {
        this.avator = avator == null ? null : avator.trim();
    }

    /**
     * 获取在线状态：online为在线，hide为隐身，offline为离线
     *
     * @return status - 在线状态：online为在线，hide为隐身，offline为离线
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置在线状态：online为在线，hide为隐身，offline为离线
     *
     * @param status 在线状态：online为在线，hide为隐身，offline为离线
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取角色code：1为用户，2为管理员
     *
     * @return role_code - 角色code：1为用户，2为管理员
     */
    public Integer getRoleCode() {
        return roleCode;
    }

    /**
     * 设置角色code：1为用户，2为管理员
     *
     * @param roleCode 角色code：1为用户，2为管理员
     */
    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * @return sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }
}
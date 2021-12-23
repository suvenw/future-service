//package com.suven.framework.sys.entity;
//
//
//import com.suven.framework.common.api.ApiDesc;
//import com.suven.framework.common.data.BaseEntity;
//import com.suven.framework.common.data.BaseStatusEntity;
//import com.suven.framework.common.enums.UserCodeEnum;
//import com.suven.framework.core.db.ext.DS;
//
//import java.util.Date;
//
///**
// * 短视频业务缓存类（用于刷新缓存）
// * @date 2019-09-03 11:33
// */
//@DS("sys")
//public class UserInfo extends BaseStatusEntity {
//
//    private static final long serialVersionUID = 1L;
//
//    /** nickName 昵称  */
//    @ApiDesc(value = "昵称", required = 0)
//    private String nickName;
//
//    /** userCode 自定义账号  */
//    @ApiDesc(value = "自定义账号", required = 0)
//    private String userCode;
//
//    /** password 密码  */
//    @ApiDesc(value = "密码", required = 0)
//    private String password;
//
//    /** userHeadImg 用户头像  */
//    @ApiDesc(value = "用户头像", required = 0)
//    private String userHeadImg;
//
//    /** weChatName 微信昵称  */
//    @ApiDesc(value = "微信昵称", required = 0)
//    private String weChatName;
//
//    /** weChatImg 微信昵称  */
//    @ApiDesc(value = "微信昵称", required = 0)
//    private String weChatImg;
//
//    /** sex 性别：0.不详，1.男，2.女  */
//    @ApiDesc(value = "性别：0.不详，1.男，2.女", required = 0)
//    private int sex;
//
//    /** phone 电话号码  */
//    @ApiDesc(value = "电话号码", required = 0)
//    private String phone;
//
//    /** birthdayType 生日类型：1.新历，2.农历  */
//    @ApiDesc(value = "生日类型：1.新历，2.农历", required = 0)
//    private String birthdayType;
//
//    /** birthday 生日日期  */
//    @ApiDesc(value = "生日日期", required = 0)
//    private Date birthday;
//
//    /** storeId 归属门店id  */
//    @ApiDesc(value = "归属门店id", required = 0)
//    private long storeId;
//
//    /** followUserId 美丽顾问Id  */
//    @ApiDesc(value = "美丽顾问Id", required = 0)
//    private String followUserId;
//
//    /** qrCode 二维码信息  */
//    @ApiDesc(value = "二维码信息", required = 0)
//    private String qrCode;
//
//    /** remarks 用户简介  */
//    @ApiDesc(value = "用户简介", required = 0)
//    private String remarks;
//
//
//    public static UserInfo build(){
//        return new UserInfo();
//    }
//
//    public void setNickName( String nickName){
//        this.nickName = nickName ;
//    }
//    public UserInfo toNickName( String nickName){
//        this.nickName = nickName ;
//        return this ;
//    }
//
//    public String getNickName(){
//        return this.nickName;
//    }
//    public void setUserCode( String userCode){
//        this.userCode = userCode ;
//    }
//    public UserInfo toUserCode( String userCode){
//        this.userCode = userCode ;
//        return this ;
//    }
//
//    public String getUserCode(){
//        return this.userCode;
//    }
//    public void setPassword( String password){
//        this.password = password ;
//    }
//    public UserInfo toPassword( String password){
//        this.password = password ;
//        return this ;
//    }
//
//    public String getPassword(){
//        return this.password;
//    }
//    public void setUserHeadImg( String userHeadImg){
//        this.userHeadImg = userHeadImg ;
//    }
//    public UserInfo toUserHeadImg( String userHeadImg){
//        this.userHeadImg = userHeadImg ;
//        return this ;
//    }
//
//    public String getUserHeadImg(){
//        return this.userHeadImg;
//    }
//    public void setWeChatName( String weChatName){
//        this.weChatName = weChatName ;
//    }
//    public UserInfo toWeChatName( String weChatName){
//        this.weChatName = weChatName ;
//        return this ;
//    }
//
//    public String getWeChatName(){
//        return this.weChatName;
//    }
//    public void setWeChatImg( String weChatImg){
//        this.weChatImg = weChatImg ;
//    }
//    public UserInfo toWeChatImg( String weChatImg){
//        this.weChatImg = weChatImg ;
//        return this ;
//    }
//
//    public String getWeChatImg(){
//        return this.weChatImg;
//    }
//    public void setSex( int sex){
//        this.sex = sex ;
//    }
//    public UserInfo toSex( int sex){
//        this.sex = sex ;
//        return this ;
//    }
//
//    public int getSex(){
//        return this.sex;
//    }
//    public void setPhone( String phone){
//        this.phone = phone ;
//    }
//    public UserInfo toPhone( String phone){
//        this.phone = phone ;
//        return this ;
//    }
//
//    public String getPhone(){
//        return this.phone;
//    }
//    public void setBirthdayType( String birthdayType){
//        this.birthdayType = birthdayType ;
//    }
//    public UserInfo toBirthdayType( String birthdayType){
//        this.birthdayType = birthdayType ;
//        return this ;
//    }
//
//    public String getBirthdayType(){
//        return this.birthdayType;
//    }
//    public void setBirthday( Date birthday){
//        this.birthday = birthday ;
//    }
//    public UserInfo toBirthday( Date birthday){
//        this.birthday = birthday ;
//        return this ;
//    }
//
//    public Date getBirthday(){
//        return this.birthday;
//    }
//    public void setStoreId( long storeId){
//        this.storeId = storeId ;
//    }
//    public UserInfo toStoreId( long storeId){
//        this.storeId = storeId ;
//        return this ;
//    }
//
//    public long getStoreId(){
//        return this.storeId;
//    }
//    public void setFollowUserId( String followUserId){
//        this.followUserId = followUserId ;
//    }
//    public UserInfo toFollowUserId( String followUserId){
//        this.followUserId = followUserId ;
//        return this ;
//    }
//
//    public String getFollowUserId(){
//        return this.followUserId;
//    }
//    public void setQrCode( String qrCode){
//        this.qrCode = qrCode ;
//    }
//    public UserInfo toQrCode( String qrCode){
//        this.qrCode = qrCode ;
//        return this ;
//    }
//
//    public String getQrCode(){
//        return this.qrCode;
//    }
//    public void setRemarks( String remarks){
//        this.remarks = remarks ;
//    }
//    public UserInfo toRemarks( String remarks){
//        this.remarks = remarks ;
//        return this ;
//    }
//
//    public String getRemarks(){
//        return this.remarks;
//    }
//}

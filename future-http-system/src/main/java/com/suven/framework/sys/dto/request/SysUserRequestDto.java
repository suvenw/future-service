package com.suven.framework.sys.dto.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xxx.xxx
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * ----------------------------------------------------------------------------
 * </p>
 * @ClassName: SysUserRequestDto.java
 * @Description: 用户表的数据交互处理类
 * @date 2019-10-18 12:35:25
 */
public class SysUserRequestDto extends BaseStatusEntity implements Serializable {




    /** nickName 昵称  */
    @ApiDesc(value = "昵称", required = 0)
    private String nickName;

    /** userCode 自定义账号  */
    @ApiDesc(value = "自定义账号", required = 0)
    private String userCode;

    /** password 密码  */
    @ApiDesc(value = "密码", required = 0)
    private String password;

    /** userHeadImg 用户头像  */
    @ApiDesc(value = "用户头像", required = 0)
    private String userHeadImg;

    /** weChatName 微信昵称  */
    @ApiDesc(value = "微信昵称", required = 0)
    private String weChatName;

    /** weChatImg 微信昵称图片  */
    @ApiDesc(value = "微信昵称图片", required = 0)
    private String weChatImg;

    /** sex 性别：0.不详，1.男，2.女  */
    @ApiDesc(value = "性别：0.不详，1.男，2.女", required = 0)
    private int sex;

    /** phone 电话号码  */
    @ApiDesc(value = "电话号码", required = 0)
    private String phone;

    /** birthdayType 生日类型：1.新历，2.农历  */
    @ApiDesc(value = "生日类型：1.新历，2.农历", required = 0)
    private String birthdayType;

    /** birthday 生日日期  */
    @ApiDesc(value = "生日日期", required = 0)
    private Date birthday;

    /** storeId 归属门店id  */
    @ApiDesc(value = "归属门店id", required = 0)
    private long storeId;

    /** followUserId 美丽顾问Id  */
    @ApiDesc(value = "美丽顾问Id", required = 0)
    private String followUserId;

    /** qrCode 二维码信息  */
    @ApiDesc(value = "二维码信息", required = 0)
    private String qrCode;

    /** remarks 用户简介  */
    @ApiDesc(value = "用户简介", required = 0)
    private String remarks;

    @ApiDesc(value = "开始时间")
    private Date startDate;

    @ApiDesc(value = "结束时间")
    private Date endDate;
    /** salt 用户账号盐值，用于加密  */
    @ApiDesc(value = "用户账号盐值，用于加密", required = 0)
    private String salt;


    @ApiDesc(value = "部门id集合",required = 0)
    private List<Long> departIds;

    @ApiDesc(value = "角色id集合",required = 0)
    private List<Long> roleIds;

    public static SysUserRequestDto build(){
        return new SysUserRequestDto();
    }


    public List<Long> getDepartIds() {
        return departIds;
    }

    public SysUserRequestDto setDepartIds(List<Long> departIds) {
        this.departIds = departIds;
        return this;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public SysUserRequestDto setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
        return this;
    }

    public void setNickName( String nickName){
        this.nickName = nickName ;
    }
    public SysUserRequestDto toNickName( String nickName){
        this.nickName = nickName ;
        return this ;
    }

    public String getNickName(){
        return this.nickName;
    }
    public void setUserCode( String userCode){
        this.userCode = userCode ;
    }
    public SysUserRequestDto toUserCode( String userCode){
        this.userCode = userCode ;
        return this ;
    }

    public String getUserCode(){
        return this.userCode;
    }
    public void setPassword( String password){
        this.password = password ;
    }
    public SysUserRequestDto toPassword( String password){
        this.password = password ;
        return this ;
    }

    public String getPassword(){
        return this.password;
    }
    public void setUserHeadImg( String userHeadImg){
        this.userHeadImg = userHeadImg ;
    }
    public SysUserRequestDto toUserHeadImg( String userHeadImg){
        this.userHeadImg = userHeadImg ;
        return this ;
    }

    public String getUserHeadImg(){
        return this.userHeadImg;
    }
    public void setWeChatName( String weChatName){
        this.weChatName = weChatName ;
    }
    public SysUserRequestDto toWeChatName( String weChatName){
        this.weChatName = weChatName ;
        return this ;
    }

    public String getWeChatName(){
        return this.weChatName;
    }
    public void setWeChatImg( String weChatImg){
        this.weChatImg = weChatImg ;
    }
    public SysUserRequestDto toWeChatImg( String weChatImg){
        this.weChatImg = weChatImg ;
        return this ;
    }

    public String getWeChatImg(){
        return this.weChatImg;
    }
    public void setSex( int sex){
        this.sex = sex ;
    }
    public SysUserRequestDto toSex( int sex){
        this.sex = sex ;
        return this ;
    }

    public int getSex(){
        return this.sex;
    }
    public void setPhone( String phone){
        this.phone = phone ;
    }
    public SysUserRequestDto toPhone( String phone){
        this.phone = phone ;
        return this ;
    }

    public String getPhone(){
        return this.phone;
    }
    public void setBirthdayType( String birthdayType){
        this.birthdayType = birthdayType ;
    }
    public SysUserRequestDto toBirthdayType( String birthdayType){
        this.birthdayType = birthdayType ;
        return this ;
    }

    public String getBirthdayType(){
        return this.birthdayType;
    }
    public void setBirthday( Date birthday){
        this.birthday = birthday ;
    }
    public SysUserRequestDto toBirthday( Date birthday){
        this.birthday = birthday ;
        return this ;
    }

    public Date getBirthday(){
        return this.birthday;
    }
    public void setStoreId( long storeId){
        this.storeId = storeId ;
    }
    public SysUserRequestDto toStoreId( long storeId){
        this.storeId = storeId ;
        return this ;
    }

    public long getStoreId(){
        return this.storeId;
    }
    public void setFollowUserId( String followUserId){
        this.followUserId = followUserId ;
    }
    public SysUserRequestDto toFollowUserId( String followUserId){
        this.followUserId = followUserId ;
        return this ;
    }

    public String getFollowUserId(){
        return this.followUserId;
    }
    public void setQrCode( String qrCode){
        this.qrCode = qrCode ;
    }
    public SysUserRequestDto toQrCode( String qrCode){
        this.qrCode = qrCode ;
        return this ;
    }

    public String getQrCode(){
        return this.qrCode;
    }
    public void setRemarks( String remarks){
        this.remarks = remarks ;
    }
    public SysUserRequestDto toRemarks( String remarks){
        this.remarks = remarks ;
        return this ;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getRemarks(){
        return this.remarks;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public SysUserRequestDto toSalt(String salt) {
        this.salt = salt;
        return this;
    }
}

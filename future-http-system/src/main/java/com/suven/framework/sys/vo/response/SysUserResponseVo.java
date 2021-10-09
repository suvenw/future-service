package com.suven.framework.sys.vo.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xxx.xxx
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * ----------------------------------------------------------------------------
 * </p>
 * @ClassName: SysUserResponseVo.java
 * @Description: 用户表的数据交互处理类
 * @date 2019-10-18 12:35:25
 */
public class SysUserResponseVo extends BaseStatusEntity implements Serializable {


    @ApiDesc(value = "状态，0：默认  1：禁号")
    private int status;

    @ApiDesc(value = "昵称")
    private String nickName;

    @ApiDesc(value = "手机号码")
    private String phone;

    @ApiDesc(value = "账号密码")
    private String password;

    @ApiDesc(value = "头像")
    private String headImage;

    @ApiDesc(value = "邮箱")
    private String email;

    @ApiDesc(value = "地址")
    private String address;

    @ApiDesc(value = "性别")
    private int sex;

    @ApiDesc(value = "生日")//datetime 不能存入1970年以前的时间戳
    private Date birthday;

    @ApiDesc(value = "IP")
    private String ip;

    @ApiDesc(value = "用户简介")
    private String remarks;

    @ApiDesc(value = "推荐人ID")
    private long referrerId;

    @ApiDesc(value = "二维码CODE")
    private String qrCode;

    @ApiDesc(value = "注册渠道")
    private int channel;

    @ApiDesc(value = "版本号")
    private int version;

    @ApiDesc(value = "邀请码")
    private String invitationCode;

    @ApiDesc(value = "注册平台")
    private int platform;

    @ApiDesc(value = "是否禁言，0：默认  1：禁言")
    private int banned;

    @ApiDesc(value = "所在城市ID")
    private int cityId;

    @ApiDesc(value = "所在城市")
    private String cityName;

    @ApiDesc(value = "是否展示（生日）0展示 1不展示")
    private int isShow;

    @ApiDesc(value = "等级")
    private int grade;

    @ApiDesc(value = "最后登陆时间")
    private Date lastLoginDate;

    @ApiDesc(value = "用户昵称（前端页面展示）")
    private String nickNameView;

    @ApiDesc(value = "是否版主", required = 0)
    private int isModerator;

    public static SysUserResponseVo build() {
        return new SysUserResponseVo();
    }

    public int getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(int isModerator) {
        this.isModerator = isModerator;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(long referrerId) {
        this.referrerId = referrerId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getNickNameView() {
        return nickNameView;
    }

    public void setNickNameView(String nickNameView) {
        this.nickNameView = nickNameView;
    }
}

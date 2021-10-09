package com.suven.framework.sys.vo.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

import java.util.Date;
import java.util.List;

/**
* @ClassName: SysUserRequestVo.java
* @Description: 用户表的数据交互处理类
* @author xxx.xxx
* @date   2019-10-18 12:35:25
* @version V1.0.0
* <p>
    * ----------------------------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    * ----------------------------------------------------------------------------
    * </p>
*/
public class SysUserRequestVo extends RequestParserVo {


        private long id;
        private int pageNo;
        private int pageSize;
		@ApiDesc(value = "登录用户账号")
		private String username;

		@ApiDesc(value = "开始时间")
		private Date startDate;

		@ApiDesc(value = "结束时间")
		private Date endDate;

		@ApiDesc(value = "状态，0：默认  1：禁号")
		private int status;

		@ApiDesc(value = "昵称")
		private String nickname;

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
		private long birthday;

		@ApiDesc(value = "生日（date 数据类型）")//datetime 不能存入1970年以前的时间戳
		private Date birthdayDate;

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

		@ApiDesc(value = "验证码", required = 0)
		private String captcha;

		@ApiDesc(value = "验证码key", required = 0)
		private String checkKey;

		@ApiDesc(value = "部门id集合",required = 0)
		private List<Long> departIds;

		@ApiDesc(value = "角色id集合",required = 0)
		private List<Long> roleIds;


    public static SysUserRequestVo build(){
        return new SysUserRequestVo();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public SysUserRequestVo setPhone(String phone) {
		this.phone = phone;
		return this;
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

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public Date getBirthdayDate() {
		return birthdayDate;
	}

	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCheckKey() {
		return checkKey;
	}

	public void setCheckKey(String checkKey) {
		this.checkKey = checkKey;
	}

	public List<Long> getDepartIds() {
		return departIds;
	}

	public void setDepartIds(List<Long> departIds) {
		this.departIds = departIds;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
}

package com.suven.framework.sys.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.sys.utils.JwtUtil;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.util.Constant;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.redis.RedisUtil;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.random.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.databene.commons.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.sys.entity.SysUserDepart;
import com.suven.framework.sys.service.SysDepartService;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.service.SysUserService;
import com.suven.framework.sys.vo.request.SysUserDepartRequestVo;
import com.suven.framework.sys.vo.request.SysUserRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleRequestVo;
import com.suven.framework.sys.vo.request.SysUserUpdatePwdRequestVo;
import com.suven.framework.sys.vo.response.LoginCodeResponseVo;
import com.suven.framework.sys.vo.response.SysDepartResponseVo;
import com.suven.framework.sys.vo.response.SysLoginResponseVo;
import com.suven.framework.sys.vo.response.SysUserResponseVo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**   
 * @Title: SysUserFacade.java
 * @author xxx.xxx
 * @date   2019-10-18 12:35:25
 * @version V1.0  
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: TODO(说明) 对象缓存统一模板实现类;  
 */
 
@Component
public class SysUserFacade {

	@Autowired
	private SysUserService  sysUserService;

	@Autowired
	private SysDepartService sysDepartService;

	@Autowired
	private RedisClusterServer redisClusterServer;

	@Autowired
	private SysUserDepartService sysUserDepartService;

	@Autowired
	private SysUserRoleService sysUserRoleService;



	/**
	 * 用户信息
	 *
	 * @param sysUser
	 * @return
	 */
	public SysLoginResponseVo userInfo(SysUserResponseVo sysUser) {

		SysLoginResponseVo result = new SysLoginResponseVo();
		// 	生成token
		String token = JwtUtil.createToken(JSON.toJSONString(sysUser));
		long current = System.currentTimeMillis();
		String key = RedisUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN, sysUser.getId());
		redisClusterServer.hmset(key, token, String.valueOf(current + JwtUtil.JWT_EXPIRE_TIME));

		int multi_depart = 0 ;
		List<SysDepartResponseVo> departList = new ArrayList<>();

		// 获取用户部门信息
		List<Long> departIds = sysUserDepartService.getDepartIdsByUserId(sysUser.getId());
		List<SysDepartResponseDto> departs =  sysDepartService.getSysDepartByIds(departIds);
		if(null != departList &&  departs.size() == 1){
//			sysUserService.updateUserDepart(userName, departs.get(0).getOrgCode());
			multi_depart = 1;
		} else  if(null != departList &&  departs.size() > 1){
			multi_depart = 2;
		}
		List<SysDepartResponseVo> resDtoList = new ArrayList<>();
		if(null != departs){
			departs.forEach(sysDepart -> {
				SysDepartResponseVo sysDepartResponseVo = SysDepartResponseVo.build().clone(sysDepart);
				resDtoList.add(sysDepartResponseVo);
			});
		}
		result.setDeparts(resDtoList).setToken(token).setUserInfo(sysUser).setMulti_depart(multi_depart);
		return result;
	}



	public SysResultCodeEnum checkUserIsEffective(SysUserResponseDto dto, SysUserRequestVo sysUserRequestVo) {
		Object checkCode = redisClusterServer.get(sysUserRequestVo.getCheckKey());
		if(checkCode==null) {
			return SysResultCodeEnum.SYS_LOGIN_CODE_FAIL;
		}
		if(!checkCode.equals(sysUserRequestVo.getCaptcha())) {
			return SysResultCodeEnum.SYS_LOGIN_CODE_FAIL;
		}
		if (dto == null ) {
			return SysResultCodeEnum.SYS_USER_FAIL;
		} if (dto != null && dto.getStatus()==2) {
			return SysResultCodeEnum.SYS_USER_BAND_FAIL;
		}

		//不存在角色，不让该用户登录
		List<SysUserRoleResponseDto>  sysUserRoleResponseDtos = sysUserRoleService.getListByUserId(dto.getId());
		if (CollectionUtil.isEmpty(sysUserRoleResponseDtos)) {
			return SysResultCodeEnum.SYS_USER_FAIL;
		}

		/*String userPassword = CryptUtil.encryptPassword(sysUserRequestVo.getPassword());
		userPassword = coverPassword(userPassword);
		String sysPassword = dto.getPassword();
		if (!userPassword.equals(sysPassword)) {
			return SysMsgEnum.SYS_USER_PWD_FAIL;
		}*/


		return null;
	}

	public LoginCodeResponseVo getCheckCode() {
		String code = RandomUtils.randomString(4);
		String key = CryptUtil.md5(code+System.currentTimeMillis());
		key = RedisUtil.formatKey(RedisKeys.USER_LOGIN_CHECK, key);
		redisClusterServer.setNx(key,code, Constant.CHECK_CODE_SECOND);
		return LoginCodeResponseVo.build().setKey(key).setCode(code);
	}



	public SysUserResponseDto getUserByName(String username) {
		return sysUserService.getUserByName(username);
	}


	public SysResultCodeEnum logout(HttpServletRequest request) {
		String token = request.getHeader(Constant.X_ACCESS_TOKEN);
		if(StringUtils.isEmpty(token)) {
			return SysResultCodeEnum.SYS_LOGOUT_FAIL;
		}
		String obj = JwtUtil.getUserInfo(token);
		if(StringUtils.isEmpty(obj)) {
			return SysResultCodeEnum.SYS_TOKEN_FAIL;
		}
		SysUserResponseVo vo = JSONObject.parseObject(obj,SysUserResponseVo.class);
		SysUserResponseDto dto = sysUserService.getUserByPhone(vo.getPhone());
		if(dto!=null) {
			String key = RedisUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN, dto.getId());
			redisClusterServer.hdel(key,token);
			return SysResultCodeEnum.SYS_SUCCESS;
		}else {
			return SysResultCodeEnum.SYS_TOKEN_FAIL;
		}
	}


	public Boolean deleteUserInDepart(SysUserDepartRequestVo sysUserDepartRequestVo, HttpRequestByIdListVo idListVo) {
		SysUserDepartRequestDto dto = SysUserDepartRequestDto.build().clone(sysUserDepartRequestVo);
		return sysUserDepartService.deleteUserInDepart(sysUserDepartRequestVo.getDepId(),idListVo.getIdList());
	}



	public SysResultCodeEnum updatePassword(SysUserUpdatePwdRequestVo userUpdatePwdRequestVo) {
		SysUserResponseDto dto = sysUserService.getUserByName(userUpdatePwdRequestVo.getUsername());
		if (null == dto) {
			return SysResultCodeEnum.SYS_USER_FAIL;
		}
		String passwordEncode = CryptUtil.encryptPassword(dto.getPassword());
		passwordEncode = coverPassword(passwordEncode);
		if (!dto.getPassword().equals(passwordEncode)) {
			return SysResultCodeEnum.SYS_USER_OLD_PWD_FAIL;
		}
		if (StringUtils.isEmpty(userUpdatePwdRequestVo.getPassword())) {
			return SysResultCodeEnum.SYS_USER_NEW_PWD_FAIL;
		}
		if (userUpdatePwdRequestVo.getPassword().trim().length()<6) {
			return SysResultCodeEnum.SYS_USER_NEW_PWD_LENGTH;
		}
		if (!userUpdatePwdRequestVo.getPassword().equals(userUpdatePwdRequestVo.getConfirmpassword())) {
			return SysResultCodeEnum.SYS_USER_TWO_PWD_FAIL;
		}
		String password = CryptUtil.encryptPassword(dto.getPassword());
		password = coverPassword(password);

		SysUserRequestDto requestDto = SysUserRequestDto.build().clone(dto);
		requestDto.setPassword(password);
		sysUserService.updateSysUser(requestDto);
		return SysResultCodeEnum.SYS_SUCCESS;
	}

	/**
	 * 查询用户部门信息by用户id
	 * @param userId
	 * @return
	 */
	public List<SysDepartResponseVo.DepartTreeRespVo> getUserDepartList(long userId) {
		List<SysUserDepart> userDeparts = sysUserDepartService.getListByUserId(userId);
		if (CollectionUtil.isEmpty(userDeparts)) {
			return new ArrayList<>();
		}
		List<Long> departIds = new ArrayList<>();
		userDeparts.forEach(u ->{
			departIds.add(u.getDepId());
		});
		List<SysDepart> departList = sysDepartService.getList(departIds);
		if (CollectionUtil.isEmpty(departList)){
			return new ArrayList<>();
		}
		List<SysDepartResponseVo.DepartTreeRespVo> departTreeRespVoList = new ArrayList<>();
		departList.forEach(d ->{
			departTreeRespVoList.add(SysDepartResponseVo.DepartTreeRespVo.build().convertByUserDepart(d));
		});
		return departTreeRespVoList;
	}

	public Boolean addSysUserRole(SysUserRoleRequestVo userDepartRequestVo, HttpRequestByIdListVo idListVo) {
		boolean isFlag = sysUserService.addSysUserRole(userDepartRequestVo.getRoleId(),idListVo.getIdList());
		return isFlag;
	}

	public Boolean deleteUserRole(SysUserRoleRequestVo userDepartRequestVo, HttpRequestByIdListVo idListVo) {
		boolean isFlag = sysUserService.deleteUserRole(userDepartRequestVo.getRoleId(),idListVo.getIdList());
		return isFlag;
	}


	private static String coverPassword(String sourcePassword) {
		String decryptPassword = CryptUtil.decryptPassword(sourcePassword);
		String password = CryptUtil.md5(decryptPassword) ;
		return password;
	}

	public ResponseResultList getSysUserList(BasePage page) {
		ResponseResultList<SysUserResponseDto> resultList = sysUserService.getSysUserByNextPage(page);
		if (null == resultList || resultList.getList().isEmpty()) {
			return ResponseResultList.build();
		}
		List<Long> userIds = new ArrayList<>(); //后面处理等级等信息
		List<SysUserResponseVo> listVo = new ArrayList<>();
		resultList.getList().forEach(e ->{
			userIds.add(e.getId());
			listVo.add(SysUserResponseVo.build().clone(e));
		});
		ResponseResultList result = ResponseResultList.build().toList(listVo)
				.toIsNextPage(resultList.getIsNextPage())
				.toPageIndex(resultList.getPageIndex())
				.toTotal(resultList.getTotal())
				;
		return result;

	}
}

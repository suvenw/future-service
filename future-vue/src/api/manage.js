import { axios } from '@/utils/request'
import Vue from 'vue'
import { ACCESS_TOKEN } from "@/store/mutation-types"
import CryptoJS from 'crypto-js'

const api = {
  user: '/api/user',
  role: '/api/role',
  service: '/api/service',
  permission: '/api/permission',
  permissionNoPager: '/api/permission/no-pager'
}

export default api

//post
export function postAction(url,parameter) {
  return axios({
    url: url,
    method:'post' ,
    data: parameter
  })
}

//post method= {post | put}
export function httpAction(url,parameter,method) {
  return axios({
    url: url,
    method:method ,
    data: parameter
  })
}

//put
export function putAction(url,parameter) {
  return axios({
    url: url,
    method:'put',
    data: parameter
  })
}

//get
export function getAction(url,parameter) {
  return axios({
    url: url,
    method: 'get',
    params: parameter
  })
}

//deleteAction
export function deleteAction(url,parameter) {
  return axios({
    url: url,
    method: 'post',
    params: parameter
  })
}

export function getUserList(parameter) {
  return axios({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function getRoleList(parameter) {
  return axios({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function getServiceList(parameter) {
  return axios({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getPermissions(parameter) {
  return axios({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService(parameter) {
  return axios({
    url: api.service,
    method: parameter.id == 0 ? 'post' : 'put',
    data: parameter
  })
}

/**
 * 下载文件 用于excel导出
 * @param url
 * @param parameter
 * @returns {*}
 */
export function downFile(url,parameter){
  return axios({
    url: url,
    data: parameter,
    method:'get' ,
    responseType: 'blob'
  })
}


/**
 * 文件上传参数设置 用于excel导入
 * @param data
 * @returns {FormData}
 */
export function fileUpload(file) {
  const formData = new FormData();
  let appkey = 'H@s0zRed!fiNger8';
  let token = Vue.ls.get(ACCESS_TOKEN);
  formData.append("appId",1000);
  formData.append("version",1000);
  formData.append("sysVersion",1);
  formData.append("platform",5);
  formData.append("channel",1005);
  formData.append("device",11111111111111);
  formData.append("accessToken",token);
  formData.append("userId",111111111);
  formData.append("fileSize",file.size);
  formData.append("cliSign",CryptoJS.MD5(formData + appkey).toString().substring(8, 24));
  formData.append("files",file);
  console.info(formData);
  return formData;
}


/**
 * 分块上传
 * @param data
 * @returns {FormData}
 */
export function fileBlockUpload(file, offset, size, fileMD5) {
  console.log('file.size：' + file.size)
  const formData = new FormData();
  let type = file.type.split('/')[1]
  let appkey = 'H@s0zRed!fiNger8';
  let token = Vue.ls.get(ACCESS_TOKEN);
  let maxLimit = offset + size
  if ((maxLimit - file.size) > 0) { //最后一块
    size = file.size - offset
    maxLimit = file.size
  }

  let chunk = file.slice(offset, maxLimit);
  formData.append("appId",1000);
  formData.append("version",1000);
  formData.append("sysVersion",1);
  formData.append("platform",5);
  formData.append("channel",1005);
  formData.append("device",11111111111111);
  formData.append("accessToken",token);
  formData.append("userId",111111111);
  formData.append("fileSize",file.size);
  formData.append("chunkSize",size);
  formData.append("cliSign",CryptoJS.MD5(formData + appkey).toString().substring(8, 24));
  formData.append("fileMd5", fileMD5)
  formData.append("offset",offset);
  formData.append("files",chunk);
  formData.append("fileType",type);
  console.log(formData)
  return formData;
}


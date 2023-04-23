/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
  return /^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}


/**
 * 中文
 * @param {*} s
 */
export function isChinese (s) {
  return /^[\u4E00-\u9FA5]/g.test(s)
}

/**
 * 身份证
 * @param {*} s
 */
export function isIdCard (s) {
  return /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/.test(s)
}

/**
 * double
 * @param {*} s
 */
export function isDouble (s) {
  return /^[0-9]+(.[0-9]{2})$/.test(s)
}

/**
 * 数字
 * @param {*} s
 */
export function isInt (s) {
  return /^[0-9]*$/.test(s)
}


/**
 * 数字
 * @param {*} s
 */
export function isDigit (s) {
  return /^-?[1-9]\d*$/.test(s)
}


/**
 * 日期和时间
 * @param {*} s
 */
export function isDateTime (s) {
  return /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/.test(s)
}

/**
 * 日期
 * @param {*} s
 */
export function isDate (s) {
  return /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/.test(s)
}

/**
 * 密码
 * @param {*} s
 */
export function isPasswd (s) {
  return /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]{5,17}$/.test(s)
}

/**
 * 浮点数  小数
 * @param {*} s
 */
export function isFloat (s) {
  return /^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/.test(s)
}

/**
 * 月份
 * @param {*} s
 */
export function isMonth (s) {
  return /^(0?[1-9]|1[0-2])$/.test(s)
}

/**
 * 天
 * @param {*} s
 */
export function isDay (s) {
  return /^((0?[1-9])|((1|2)[0-9])|30|31)$/.test(s)
}


/**
 * 是否为空
 * @param {*} s
 */
export function isNull(s) {
  return /\S/.test(s)
}


/**
 * 验证是否为空
 * @param rule
 * @param value
 * @param callback
 */
export function validateNotNull(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isNull(value)) {
      callback("不能为空!")
    }
  }
}

/**
 * 验证电话号码
 * @param rule
 * @param value
 * @param callback
 */
export function validatePhone(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isPhone(value)) {
      callback("请输入正确格式的电话号码!");
    }
  }
}


/**
 * 验证手机号码
 * @param rule 规则
 * @param value
 * @param callback
 */
export function validateMobile(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isMobile(value)) {
      callback("请输入正确格式的手机号码!");
    }
  }
}


/**
 * 验证中文
 * @param rule 规则
 * @param value
 * @param callback
 */
export function validateChinese(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isChinese(value)) {
      callback("请输入中文!");
    } else {

    }
  }
}

/**
 * 验证邮箱
 * @param rule 规则
 * @param value
 * @param callback
 */
export function validateEmail(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isEmail(value)) {
      callback("请输入正确格式的邮箱!")
    }
  }
}


/**
 *  验证身份证
 * @param rule
 * @param value
 * @param callback
 */
export function validateIdCard(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isIdCard(value)) {
      callback("请输入正确格式的身份证!")
    }
  }
}

/**
 * 验证双精度
 * @param rule
 * @param value
 * @param callback
 */
export function validateDouble(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isDouble(value)) {
      callback("请输入有两位小数的数字!")
    }
  }
}



/**
 * 验证整数
 * @param rule
 * @param value
 * @param callback
 */
export function validateInt(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isInt(value)) {
      callback("请输入整数!")
    }
  }
}

/**
 * 验证整数
 * @param rule
 * @param value
 * @param callback
 */
export function validateDigit(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isDigit(value)) {
      callback("请输入数字!")
    }
  }
}

/**
 * 验证日期时间
 * @param rule
 * @param value
 * @param callback
 */
export function validateDateTime(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isDateTime(value)) {
      callback("请输入正确格式的日期时间 YYYY-MM-DD hh:mm:ss!")
    }
  }
}


/**
 * 验证日期
 * @param rule
 * @param value
 * @param callback
 */
export function validateDate(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isDate(value)) {
      callback("请输入正确格式的日期 YYYY-MM-DD!")
    }
  }
}


/**
 * 验证网址
 * @param rule
 * @param value
 * @param callback
 */
export function validateURL(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isURL(value)) {
      callback("请输入正确格式的网址!")
    }
  }
}



/**
 * 验证密码
 * @param rule
 * @param value
 * @param callback
 */
export function validatePasswd(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isPasswd(value)) {
      callback("请输入正确格式的邮箱!")
    }
  }
}



/**
 * 浮点数 正数、负数、和小数
 * @param rule
 * @param value
 * @param callback
 */
export function validateFloat(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isFloat(value)) {
      callback("请输入有小数的数字!")
    }
  }
}


/**
 * 一年的12个月(01～09和1～12)
 * @param rule
 * @param value
 * @param callback
 */
export function validateMonth(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isMonth(value)) {
      callback("请输入月份，一年的12个月!")
    }
  }
}


/**
 * 一个月的31天(01～09和1～31)
 * @param rule
 * @param value
 * @param callback
 */
export function validateDay(rule, value, callback){
  if(!value){
    callback()
  }else {
    if (!isDay(value)) {
      callback("请输入正确天数，一个月的31天 !")
    }
  }
}


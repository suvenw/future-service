//package top.suven.future.generator.entity;
//
//
//
//import com.suven.framework.generator.enums.QueryTypeEnum;
//
//import java.io.Serializable;
//
///**
// * 列的属性
// *
// * @author suven
// * @email suvenw@gmail.com
// * @date 2016年12月20日 上午12:01:45
// */
//public class GenFieldEntity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 是否为主键
//     */
//    private Integer isKey;
//    /**
//     * 数据库字段名称
//     */
//    private String columnName;
//    /**
//     * 1.数据库字段注释
//     */
//    private String comments;
//    /**
//     * 1.数据库字段类型
//     */
//    private String dataType;
//
//    /**
//     * 1 表名(第一个字母小写)，如：tb_user => tbUser;
//     */
//    private String classname;
//    /**
//     * 1 属性名称(第一个字母大写)，如：tb_user => TbUser;
//     */
//    private String className;
//
//    /**
//     * 1 属性名称(第一个字母大写)，如：user_name => UserName
//     */
//    private String attrName;
//    /**
//     * 属性名称(第一个字母小写)，如：user_name => userName
//     */
//    private String attrname;
//    /**
//     * 字段类型
//     */
//    private String attrType;
//
//
//    /**
//     * 字段中文名称
//     */
//    private String chinaName;
//
//    /**
//     * 是否显示新增
//     */
//    private Integer isShowAdd;
//
//    /**
//     * 是否显示编辑
//     */
//    private Integer isShowEdit;
//
//    /**
//     * 是否显示详情
//     */
//    private Integer isShowDetail;
//
//    /**
//     * 是否列表显示
//     */
//    private Integer isShowList;
//
//    /**
//     * 是否Excel导入
//     */
//    private Integer isImport;
//
//    /**
//     * 是否导出Excel
//     */
//    private Integer isExport;
//
//    /**
//     * 是否查询
//     */
//    private Integer isQuery;
//
//    /**
//     * 查询类型
//     */
//    private Integer queryMode = QueryTypeEnum.EQ.getCode();
//
//    /**
//     * 显示类型
//     */
//    private String showType;
//
//    /**
//     * 排序
//     */
//    private Integer orderNum;
//
//    /**
//     * 字段名称
//     */
//    private String dictName;
//
//    /**
//     * 字段类型 1 枚举 2 字段 3 列表
//     */
//    private Integer dictType;
//
//    /**
//     * 前端校验
//     */
//    private Integer viewVerification;
//
//    /**
//     * 后台校验
//     */
//    private Integer serverVerification;
//
//    /**
//     * 允许空
//     */
//    private Integer notNull;
//
//    /**
//     * 最小
//     */
//    private String minNum;
//
//    /**
//     * 最大
//     */
//    private String maxNum;
//
//    /**
//     * 正则
//     */
//    private String regex;
//
//    /**
//     * 0 自定义 1 -邮箱 2 手机 3- 电话 4-身份证
//     */
//    private Integer regexType;
//
//    /**
//     * 字段默认值
//     */
//    private String fieldDefault;
//
//    /**
//     * 字段注释
//     */
//    private String fieldContent;
//
//    /**
//     * 字段长度
//     */
//    private Integer fieldLength;
//
//    /**
//     * 小数点位数
//     */
//    private Integer fieldPointLength;
//
//
////
//
//    public static   GenFieldEntity build(){
//        return new  GenFieldEntity();
//    }
//
//
//    public Integer getIsKey() {
//        return isKey;
//    }
//
//    public GenFieldEntity setIsKey(Integer isKey) {
//        this.isKey = isKey;
//        return this;
//    }
//
//    public String getColumnName() {
//        return columnName;
//    }
//
//    public GenFieldEntity setColumnName(String columnName) {
//        this.columnName = columnName;
//        return this;
//    }
//
//    public String getComments() {
//        return comments;
//    }
//
//    public GenFieldEntity setComments(String comments) {
//        this.comments = comments;
//        return this;
//    }
//
//    public String getDataType() {
//        return dataType;
//    }
//
//    public GenFieldEntity setDataType(String dataType) {
//        this.dataType = dataType;
//        return this;
//    }
//
//    public String getClassname() {
//        return classname;
//    }
//
//    public GenFieldEntity setClassname(String classname) {
//        this.classname = classname;
//        return this;
//    }
//
//    public String getClassName() {
//        return className;
//    }
//
//    public GenFieldEntity setClassName(String className) {
//        this.className = className;
//        return this;
//    }
//
//    public String getAttrName() {
//        return attrName;
//    }
//
//    public GenFieldEntity setAttrName(String attrName) {
//        this.attrName = attrName;
//        return this;
//    }
//
//    public String getAttrname() {
//        return attrname;
//    }
//
//    public GenFieldEntity setAttrname(String attrname) {
//        this.attrname = attrname;
//        return this;
//    }
//
//    public String getAttrType() {
//        return attrType;
//    }
//
//    public GenFieldEntity setAttrType(String attrType) {
//        this.attrType = attrType;
//        return this;
//    }
//
//    public String getChinaName() {
//        return chinaName;
//    }
//
//    public GenFieldEntity setChinaName(String chinaName) {
//        this.chinaName = chinaName;
//        return this;
//    }
//
//    public Integer getIsShowAdd() {
//        return isShowAdd;
//    }
//
//    public GenFieldEntity setIsShowAdd(Integer isShowAdd) {
//        this.isShowAdd = isShowAdd;
//        return this;
//    }
//
//    public Integer getIsShowEdit() {
//        return isShowEdit;
//    }
//
//    public GenFieldEntity setIsShowEdit(Integer isShowEdit) {
//        this.isShowEdit = isShowEdit;
//        return this;
//    }
//
//    public Integer getIsShowDetail() {
//        return isShowDetail;
//    }
//
//    public GenFieldEntity setIsShowDetail(Integer isShowDetail) {
//        this.isShowDetail = isShowDetail;
//        return this;
//    }
//
//    public Integer getIsShowList() {
//        return isShowList;
//    }
//
//    public GenFieldEntity setIsShowList(Integer isShowList) {
//        this.isShowList = isShowList;
//        return this;
//    }
//
//    public Integer getIsImport() {
//        return isImport;
//    }
//
//    public GenFieldEntity setIsImport(Integer isImport) {
//        this.isImport = isImport;
//        return this;
//    }
//
//    public Integer getIsExport() {
//        return isExport;
//    }
//
//    public GenFieldEntity setIsExport(Integer isExport) {
//        this.isExport = isExport;
//        return this;
//    }
//
//    public Integer getIsQuery() {
//        return isQuery;
//    }
//
//    public GenFieldEntity setIsQuery(Integer isQuery) {
//        this.isQuery = isQuery;
//        return this;
//    }
//
//    public Integer getQueryMode() {
//        return queryMode;
//    }
//
//    public GenFieldEntity setQueryMode(Integer queryMode) {
//        this.queryMode = queryMode;
//        return this;
//    }
//
//    public String getShowType() {
//        return showType;
//    }
//
//    public GenFieldEntity setShowType(String showType) {
//        this.showType = showType;
//        return this;
//    }
//
//    public Integer getOrderNum() {
//        return orderNum;
//    }
//
//    public GenFieldEntity setOrderNum(Integer orderNum) {
//        this.orderNum = orderNum;
//        return this;
//    }
//
//    public String getDictName() {
//        return dictName;
//    }
//
//    public GenFieldEntity setDictName(String dictName) {
//        this.dictName = dictName;
//        return this;
//    }
//
//    public Integer getDictType() {
//        return dictType;
//    }
//
//    public GenFieldEntity setDictType(Integer dictType) {
//        this.dictType = dictType;
//        return this;
//    }
//
//    public Integer getViewVerification() {
//        return viewVerification;
//    }
//
//    public GenFieldEntity setViewVerification(Integer viewVerification) {
//        this.viewVerification = viewVerification;
//        return this;
//    }
//
//    public Integer getServerVerification() {
//        return serverVerification;
//    }
//
//    public GenFieldEntity setServerVerification(Integer serverVerification) {
//        this.serverVerification = serverVerification;
//        return this;
//    }
//
//    public Integer getNotNull() {
//        return notNull;
//    }
//
//    public GenFieldEntity setNotNull(Integer notNull) {
//        this.notNull = notNull;
//        return this;
//    }
//
//    public String getMinNum() {
//        return minNum;
//    }
//
//    public GenFieldEntity setMinNum(String minNum) {
//        this.minNum = minNum;
//        return this;
//    }
//
//    public String getMaxNum() {
//        return maxNum;
//    }
//
//    public GenFieldEntity setMaxNum(String maxNum) {
//        this.maxNum = maxNum;
//        return this;
//    }
//
//    public String getRegex() {
//        return regex;
//    }
//
//    public GenFieldEntity setRegex(String regex) {
//        this.regex = regex;
//        return this;
//    }
//
//    public Integer getRegexType() {
//        return regexType;
//    }
//
//    public GenFieldEntity setRegexType(Integer regexType) {
//        this.regexType = regexType;
//        return this;
//    }
//
//    public String getFieldDefault() {
//        return fieldDefault;
//    }
//
//    public GenFieldEntity setFieldDefault(String fieldDefault) {
//        this.fieldDefault = fieldDefault;
//        return this;
//    }
//
//    public String getFieldContent() {
//        return fieldContent;
//    }
//
//    public GenFieldEntity setFieldContent(String fieldContent) {
//        this.fieldContent = fieldContent;
//        return this;
//    }
//
//    public Integer getFieldLength() {
//        return fieldLength;
//    }
//
//    public GenFieldEntity setFieldLength(Integer fieldLength) {
//        this.fieldLength = fieldLength;
//        return this;
//    }
//
//    public Integer getFieldPointLength() {
//        return fieldPointLength;
//    }
//
//    public GenFieldEntity setFieldPointLength(Integer fieldPointLength) {
//        this.fieldPointLength = fieldPointLength;
//        return this;
//    }
//}

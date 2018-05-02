package com.bosoft.entity;

/**
 * 列的属�??
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016�?12�?20�? 上午12:01:45
 */
public class ColumnEntity {
	//列名
    private String columnName;
    //列名类型
    private String dataType;
    //列名备注
    private String comments;
    
    //属�?�名�?(第一个字母大�?)，如：user_name => UserName
    private String attrName;
    //属�?�名�?(第一个字母小�?)，如：user_name => userName
    private String attrname;
    //属�?�类�?
    private String attrNameUppercase;
    //属�?�类�?
    private String attrType;
    //auto_increment
    private String extra;
    
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAttrname() {
		return attrname;
	}
	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getAttrNameUppercase() {
		return attrNameUppercase;
	}
	public void setAttrNameUppercase(String attrNameUppercase) {
		this.attrNameUppercase = attrNameUppercase;
	}
}

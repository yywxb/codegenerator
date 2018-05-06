package com.bosoft.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.bosoft.entity.ColumnEntity;
import com.bosoft.entity.TableEntity;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*; 
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class GenUtils {
	private static Map<String, String> templates = new HashMap<String, String> ();
	
	static {
		templates.put("DO", "template/DO.java.vm");
		templates.put("DTO", "template/DTO.java.vm");
		templates.put("Repository", "template/Repository.java.vm");
		templates.put("Service", "template/Service.java.vm");
		templates.put("ServiceImpl", "template/ServiceImpl.java.vm");
		templates.put("Controller", "template/Controller.java.vm");
		
		templates.put("Page", "template/angular/Page.html.vm");
		templates.put("Page-save", "template/angular/Page-save.html.vm");
		templates.put("JS", "template/angular/JS.js.vm");
		templates.put("JS-service", "template/angular/JS-service.js.vm");
		templates.put("Router", "template/angular/Router.js.vm");
		templates.put("i18n", "template/angular/i18n.js.vm");
		
	}

    /**
     * 生成代码
     * @throws Exception 
     */
    public static String generatorCode(Map<String, String> table, List<Map<String, String>> columns, 
    		String part, String moduleName, String tablePrefix, String tableNameCN) throws Exception {
    	
    	if(!templates.containsKey(part)) {
    		return part + " template is not existed!";
    	}
    	String template = templates.get(part);
    	
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName" ));
        tableEntity.setComments(table.get("tableComment" ));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));
        tableEntity.setClassNameUppercase(className.toUpperCase());
        tableEntity.setTableNameCN(tableNameCN);
        //列信息
        String idType = "Long";
        List<ColumnEntity> columsList = new ArrayList<>();
        for(Map<String, String> column : columns){
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName" ));
            columnEntity.setDataType(column.get("dataType" ));
            columnEntity.setComments(column.get("columnComment" ));
            columnEntity.setExtra(column.get("extra" ));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType" );
            
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }
            columnEntity.setAttrNameUppercase(attrName.toUpperCase());
            if(columnEntity.getColumnName().equals("id")) {
            	idType = attrType;
            }
            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        String mainPath = config.getString("mainPath" );
        mainPath = StringUtils.isBlank(mainPath) ? "io.renren" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("tableNameCN", tableEntity.getTableNameCN());
        map.put("classNameUppercase", tableEntity.getClassNameUppercase());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", config.getString("package" ));
        map.put("moduleName", moduleName);
        map.put("author", config.getString("author" ));
        map.put("email", config.getString("email" ));
        map.put("idType", idType);
        map.put("datetime", format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        VelocityContext context = new VelocityContext(map);

   
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, "UTF-8" );
        tpl.merge(context, sw);

        return sw.toString();
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix) && tableName.startsWith(tablePrefix)) {
            tableName = tableName.substring(tablePrefix.length());
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     * @throws Exception 
     */
    public static Configuration getConfig() throws Exception {
        try {
            return new PropertiesConfiguration("generator.properties" );
        } catch (ConfigurationException e) {
            throw new Exception("获取配置文件失败，", e);
        }
    }
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}

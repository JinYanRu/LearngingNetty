package com.raza.netty.sqlddl;

import io.netty.util.internal.StringUtil;
import lombok.Data;

import java.lang.reflect.Type;

@Data
public class Column {
    private String name;
    private String comment;
    private String type;
    private String length;
    private boolean notNull;


    public String getString(){
        if (name == null || "".equals(name)){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" ");
        String typeString = getTypeString(type, length);
        sb.append(typeString);
        sb.append(getLengthString(typeString,length));
        if (notNull){
            sb.append("  NOT NULL ");
        }else{
            sb.append("  NULL ");
        }
        sb.append("  COMMENT '");
        sb.append(comment);
        sb.append("',");
        return sb.toString();
    }

    String getLengthString(String type ,String length){
        if (type.equals("DATE") || type.equals("DATETIME") ||type.equals("TEXT")){
            return "";
        }
        return "(" +  length + ")";

    }

    String getTypeString(String type,String length){
        switch (type){
            case "CL":
                return "TEXT";
            case "VC":
                    return "VARCHAR" ;
            case "DT":
                return "DATETIME";
            case "D":
                return "DATE";
            case "C":
                return "CHAR";
            case "N":
                if (isInt(length)){
                    return "INT";
                }else {
                    return "DECIMAL";
                }
        }
        return null;
    }

    boolean isInt( String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setNotNull(String str) {
        if ("是".equals(str)){
            this.notNull = true;
        }
        else{
            this.notNull = false;
        }
    }
}

package com.shouzan.common.msg;

import java.util.List;

/**
 * Created by Ace on 2017/6/11.
 */
public class ObjectRestResponse<T> {
    Boolean rel;
    String msg;
    T result;
    Integer total;
    Integer code = -1;
    List<T> rows;

    public Boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public ObjectRestResponse rel(boolean rel) {
        this.setRel(rel);
        return this;
    }

    public ObjectRestResponse msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public ObjectRestResponse result(T result) {
        this.setResult(result);
        return this;
    }

    public ObjectRestResponse total(int total){
        this.setTotal(total);
        return this;
    }

    public ObjectRestResponse code(int code){
        this.setCode(code);
        return this;
    }

    public ObjectRestResponse rows(List<T> rows){
        this.setRows(rows);
        return this;
    }

    public Boolean getRel() {
        return rel;
    }

    public void setRel(Boolean rel) {
        this.rel = rel;
    }
}

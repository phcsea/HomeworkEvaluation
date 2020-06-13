package com.buguagaoshu.homework.common.domain;

import com.buguagaoshu.homework.common.enums.ReturnCodeEnum;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-06-04 13:07
 * 返回信息包装
 */
public class ResponseDetails extends HashMap<String, Object> {

    private ResponseDetails() {
        put("code", ReturnCodeEnum.SUCCESS.getCode());
        put("msg", ReturnCodeEnum.SUCCESS.getMsg());
        put("time", LocalDateTime.now());
    }

    public static ResponseDetails ok() {
        return new ResponseDetails();
    }

    public static ResponseDetails ok(Integer code, String msg) {
        ResponseDetails r = new ResponseDetails();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResponseDetails ok(String msg) {
        ResponseDetails r = new ResponseDetails();
        r.put("msg", msg);
        return r;
    }

    public static ResponseDetails ok(ReturnCodeEnum codeEnum) {
        ResponseDetails r = new ResponseDetails();
        r.put("code", codeEnum.getCode());
        r.put("msg", codeEnum.getMsg());
        return r;
    }

    public static ResponseDetails ok(Map<String, Object> map) {
        ResponseDetails r = new ResponseDetails();
        r.putAll(map);
        return r;
    }

    public static ResponseDetails ok(List<?> data) {
        ResponseDetails r = new ResponseDetails();
        r.put("data", data);
        return r;
    }

    @Override
    public ResponseDetails put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
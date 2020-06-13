package com.buguagaoshu.homework.common.enums;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-06-04 11:58
 * 作业提交状态
 */
public enum  HomeworkSubmitStatusEnum {
    /**
     * 作业提交后处理状态
     * */
    HOMEWORK_ERROR(-1, "作业不符合要求，被打回。"),
    NOT_SUBMITTED(0, "暂未提交"),
    TEMPORARY_STORAGE(1, "暂时保存，但未提交"),
    SUBMIT(2, "已经提交，但老师没有批改"),
    CORRECTION(3, "老师批改完成")
    ;

    int code;

    String msg;

    HomeworkSubmitStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
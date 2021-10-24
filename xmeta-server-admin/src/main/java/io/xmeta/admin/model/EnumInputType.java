package io.xmeta.admin.model;

public enum EnumInputType {

    /**
     * 密码输入框
     */
    Password("Password"),
    /**
     * 金额输入框
     */
    Money("Money"),
    /**
     * 文本域
     */
    Textarea("Textarea"),
    /**
     * 日期
     */
    Date("Date"),
    /**
     * 日期时间
     */
    DateTime("DateTime"),
    /**
     * 周
     */
    DateWeek("DateWeek"),
    /**
     * 月
     */
    DateMonth("DateMonth"),
    /**
     * 季度输入
     */
    DateQuarter("DateQuarter"),
    /**
     * 年份输入
     */
    DateYear("DateYear"),
    /**
     * 日期区间
     */
    DateRange("DateRange"),
    /**
     * 日期时间区间
     */
    DateTimeRange("DateTimeRange"),
    /**
     * 时间
     */
    Time("Time"),
    /**
     * 时间区间
     */
    TimeRange("TimeRange"),
    /**
     * 文本框
     */
    Text("Text"),
    /**
     * 下拉框
     */
    Select("Select"),
    /**
     * 多选框
     */
    Checkbox("Checkbox"),
    /**
     * 星级组件
     */
    Rate("Rate"),
    /**
     * 单选框
     */
    Radio("Radio"),
    /**
     * 按钮单选框
     */
    RadioButton("RadioButton"),
    /**
     * 进度条
     */
    Progress("Progress"),
    /**
     * 百分比组件
     */
    Percent("Percent"),
    /**
     * 数字输入框
     */
    Digit("Digit"),
    /**
     * 秒格式化
     */
    Second("Second"),
    /**
     * 头像
     */
    Avatar("Avatar"),
    /**
     * 代码框
     */
    Code("Code"),
    /**
     * 单选多选
     */
    Switch("Switch"),
    /**
     * 相对于当前时间
     */
    FromNow("FromNow"),
    /**
     * 图片
     */
    Image("Image"),
    /**
     * 图片
     */
    Media("Media"),
    /**
     * 代码框，但是带了 json 格式化
     */
    JsonCode("JsonCode"),
    /**
     * 颜色选择器
     */
    Color("Color"),
    /**
     * 富文本编辑器
     */
    Editor("Editor"),
    /**
     * 邮件输入框
     */
    Email("Email"),
    /**
     * 图标选择
     */
    Icon("Icon");

    private final String graphqlName;

    private EnumInputType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}

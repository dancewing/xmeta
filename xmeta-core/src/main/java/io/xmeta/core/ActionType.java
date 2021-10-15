package io.xmeta.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @projectName xmetadata
 * @class: ActionType
 * @author: DuChenYang
 * @datetime: 2021/6/22 15:01
 * @description:
 */
@Getter
@AllArgsConstructor
public enum ActionType {

    READ("read", "读取单条记录"),
    CREATE("create", "新增记录"),
    LIST("list", "查询列表"),
    UPDATE("update", "更新记录"),
    DELETE("delete", "删除记录");

    private final String type;
    private final String name;
}

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

    Get("读取单条记录"),
    Create("新增记录"),
    Query("查询列表"),
    Search("高级查询"),
    Update("更新记录"),
    Delete("删除记录");

    private final String name;
}

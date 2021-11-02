import * as models from "../../models";
/** 实体数据模型 */


// /** 实体id */
// entityId: string;
// /** 实体名称 */
// name: string;
// /** 实体类型 */
// entityType: string;
// /** 实体的字段属性 */
// properties: EntityProperty[];

export type EntityModel = {
  entityType: string;
} & Omit<models.Entity, "createdAt" | "updatedAt">

// /** 实体属性数据模型 */
// export interface EntityProperty {
//   /** 属性id */
//   propertyId: string;
//   /** 属性名称 */
//   name: string;
//   /** 属性类型 */
//   propertyType: string;
//   /** 是否主键 */
//   isPK?: boolean;
//   /** 是否外键 */
//   isFK?: boolean;
// }

/** 实体渲染模型 */
export type EntityCanvasModel = {
  x?: number;
  y?: number;
  width?: number;
  height?: number;
} & EntityModel

/** 连线渲染模型 */
export interface RelationCanvasModel {
  /** 连线id */
  relationId: string;
  /** source端实体id */
  sourceEntityId: string;
  /** target端实体id */
  targetEntityId: string;
  // /** source端关联属性 */
  // sourceProperty: EntityProperty;
  // /** target端关联属性 */
  // targetProperty: EntityProperty;
}

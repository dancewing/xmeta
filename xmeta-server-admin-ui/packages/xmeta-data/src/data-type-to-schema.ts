import * as models from "./models";
import * as schemas from "./schemas";

export const DATA_TYPE_TO_SCHEMA: { [dataType in models.EnumDataType]: any } = {
  [models.EnumDataType.Id]: schemas.id,
  [models.EnumDataType.SingleLineText]: schemas.singleLineText,
  [models.EnumDataType.MultiLineText]: schemas.multiLineText,
  [models.EnumDataType.WholeNumber]: schemas.wholeNumber,
  /**
   * @todo reference to minimumValue
   * @todo Check for the right value for precision
   */
  [models.EnumDataType.DecimalNumber]: schemas.decimalNumber,
  [models.EnumDataType.Date]: schemas.date,
  [models.EnumDataType.Time]: schemas.time,
  [models.EnumDataType.DateTime]: schemas.dateTime,

  /** @todo validate the actual selected entity */
  [models.EnumDataType.Lookup]: schemas.lookup,
  [models.EnumDataType.Email]: schemas.email,
  [models.EnumDataType.OptionSet]: schemas.optionSet,
  [models.EnumDataType.MultiSelectOptionSet]: schemas.multiSelectOptionSet,
  [models.EnumDataType.Boolean]: schemas.boolean,

  [models.EnumDataType.CreatedAt]: schemas.createdAt,
  [models.EnumDataType.UpdatedAt]: schemas.updatedAt,
  [models.EnumDataType.CreatedBy]: schemas.createdBy,
  [models.EnumDataType.UpdatedBy]: schemas.updatedBy,

  [models.EnumDataType.GeographicLocation]: schemas.geographicLocation,
  [models.EnumDataType.Password]: schemas.password,
  [models.EnumDataType.Username]: schemas.username,
  [models.EnumDataType.Roles]: schemas.roles,
  [models.EnumDataType.Json]: schemas.json,
};

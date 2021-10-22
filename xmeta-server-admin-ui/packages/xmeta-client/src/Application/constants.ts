import * as models from "../models";

const YELLOW = "#F5B82E";
const RED = "#FF6E6E";
const PINK = "#F685A1";
const TURQUOISE = "#41CADD";
const GREEN = "#8DD9B9";
const BLUE = "#20A4F3";

export const COLORS = [
  {
    value: YELLOW,
    label: "Yellow",
  },
  {
    value: RED,
    label: "Red",
  },
  {
    value: PINK,
    label: "Pink",
  },
  {
    value: TURQUOISE,
    label: "Turquoise",
  },
  {
    value: GREEN,
    label: "Green",
  },
  {
    value: BLUE,
    label: "Blue",
  },
];

export const COLOR_TO_NAME: {
  [key: string]: string;
} = {
  [YELLOW]: "yellow",
  [RED]: "red",
  [PINK]: "pink",
  [TURQUOISE]: "turquoise",
  [GREEN]: "green",
  [BLUE]: "blue",
};

export const sampleAppWithoutEntities: models.AppCreateWithEntitiesInput = {
  app: {
    name: "My app",
    description: "",
    color: GREEN,
  },
  commitMessage: "",
  entities: [],
};

export const sampleAppWithEntities: models.AppCreateWithEntitiesInput = {
  app: {
    name: "Sample app",
    description: "Sample application for e-commerce",
    color: YELLOW,
  },
  commitMessage: "",
  entities: [
    {
      name: "Orders",
      table: "t_order",
      fields: [
        {
          name: "Quantity",
          dataType: models.EnumDataType.WholeNumber,
        },
        {
          name: "Discount",
          dataType: models.EnumDataType.DecimalNumber,
        },
        {
          name: "Total Price",
          dataType: models.EnumDataType.WholeNumber,
        },
        {
          name: "Customer",
          dataType: models.EnumDataType.Lookup,
          properties: {
            relationType: models.EnumRelationType.manyToOne,
            relatedEntity: "Customer",
          }
        },
        {
          name: "Product",
          dataType: models.EnumDataType.Lookup,
          properties: {
            relationType: models.EnumRelationType.oneWay,
            relatedEntity: "Product",
          }
        }
      ],
    },
    {
      name: "Customer",
      table: "t_customer",
      fields: [
        {
          name: "First Name",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "Last Name",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "Email",
          dataType: models.EnumDataType.Email,
        },
        {
          name: "Phone",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "Orders",
          dataType: models.EnumDataType.Lookup,
          properties: {
            relationType: models.EnumRelationType.oneToMany,
            relatedEntity: "Orders",
          }
        },
        {
          name: "Addresses",
          dataType: models.EnumDataType.Lookup,
          properties: {
            relationType: models.EnumRelationType.oneToMany,
            relatedEntity: "Address",
          }
        }
      ],
    },
    {
      name: "Address",
      table: "t_address",
      fields: [
        {
          name: "Address 1",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "Address 2",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "City",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "State",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "Zip",
          dataType: models.EnumDataType.WholeNumber,
        },
        {
          name: "Customer",
          dataType: models.EnumDataType.Lookup,
          properties: {
            relationType: models.EnumRelationType.manyToOne,
            relatedEntity: "Customer",
          }
        }
      ],
    },
    {
      name: "Product",
      table: "t_product",
      fields: [
        {
          name: "Name",
          dataType: models.EnumDataType.SingleLineText,
        },
        {
          name: "Item Price",
          dataType: models.EnumDataType.DecimalNumber,
        },
        {
          name: "Description",
          dataType: models.EnumDataType.MultiLineText,
        },
      ],
    },
  ],
};

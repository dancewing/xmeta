import React, {useMemo} from "react";
import { Formik, FormikErrors } from "formik";
import omit from "lodash.omit";
import { isEmpty } from "lodash";
import { getSchemaForDataType } from "@xmeta/data";
import { ToggleField, TextField } from "@xmeta/design-system";
import * as models from "../models";
import { DisplayNameField } from "../Components/DisplayNameField";
import { Form } from "../Components/Form";
import NameField from "../Components/NameField";
import { validate } from "../util/formikValidateJsonSchema";
import { SYSTEM_DATA_TYPES } from "./constants";
import DataTypeSelectField from "./DataTypeSelectField";
import { SchemaFields } from "./SchemaFields";
import {Button, EnumButtonStyle} from "../Components/Button";
import {Grid, GridCell, GridRow} from "@rmwc/grid";
import EnumSelectField from "../Components/EnumSelectField";
import {useIntl} from "react-intl";

export type Values = {
  id: string; //the id field is required in the form context to be used in "DataTypeSelectField"
  name: string;
  displayName: string;
  column: string;
  dataType: models.EnumDataType;
  unique: boolean;
  required: boolean;
  searchable: boolean;
  description: string | null;
  properties: Object;
};

type Props = {
  onSubmit: (values: Values) => void;
  isDisabled?: boolean;
  defaultValues?: Partial<models.EntityField>;
  applicationId: string;
  entityId: string;
  entityDisplayName: string;
};

const FORM_SCHEMA = {
  required: ["name", "displayName"],
  properties: {
    displayName: {
      type: "string",
      minLength: 1,
    },
    name: {
      type: "string",
      minLength: 1,
    },
  },
};

const NON_INPUT_GRAPHQL_PROPERTIES = ["createdAt", "updatedAt", "__typename"];

export const INITIAL_VALUES: Values = {
  id: "",
  name: "",
  displayName: "",
  column: "",
  dataType: models.EnumDataType.SingleLineText,
  unique: false,
  required: false,
  searchable: false,
  description: "",
  properties: {},
};

const EntityFieldForm = ({
  onSubmit,
  defaultValues = {},
  isDisabled,
  applicationId,
  entityId,
  entityDisplayName,
}: Props) => {
  const intl = useIntl();
  const initialValues = useMemo(() => {
    const sanitizedDefaultValues = omit(
      defaultValues,
      NON_INPUT_GRAPHQL_PROPERTIES
    );
    return {
      ...INITIAL_VALUES,
      ...sanitizedDefaultValues,
    };
  }, [defaultValues]);

  function onKeyDown(keyEvent:any) {
    if ((keyEvent.charCode || keyEvent.keyCode) === 13) {
      keyEvent.preventDefault();
    }
  }

  return (
    <Formik
      initialValues={initialValues}
      validate={(values: Values) => {
        const errors: FormikErrors<Values> = validate<Values>(
          values,
          FORM_SCHEMA
        );
        //validate the field dynamic properties
        const schema = getSchemaForDataType(values.dataType);
        const propertiesError = validate<Object>(values.properties, schema);

        // Ignore related field ID error
        if ("relatedFieldId" in propertiesError) {
          // @ts-ignore
          delete propertiesError.relatedFieldId;
        }

        if (!isEmpty(propertiesError)) {
          errors.properties = propertiesError;
        }

        return errors;
      }}
      enableReinitialize
      onSubmit={onSubmit}
    >
      {(formik) => {
        const schema = getSchemaForDataType(formik.values.dataType);

        return (
          <Form childrenAsBlocks onKeyDown={onKeyDown}>
            {/*{!isDisabled && <FormikAutoSave debounceMS={1000} />}*/}
            <Grid>
              <GridRow>
                <GridCell span={6}>
                  <DisplayNameField
                    name="displayName"
                    label="Display Name"
                    disabled={isDisabled}
                    required
                  />
                </GridCell>
                <GridCell span={6}>
                  <NameField name="name" disabled={isDisabled} required />
                </GridCell>
              </GridRow>
              <GridRow>
                <GridCell span={6}>
                  <TextField name="column" label="Column" disabled={isDisabled} />
                </GridCell>
                <GridCell span={6}>
                  <TextField name="description"
                             label="Description"
                             disabled={isDisabled} />
                </GridCell>
              </GridRow>
              <GridRow style={{padding:"12px 0"}}>
                <GridCell span={12}>
                  <ToggleField
                      name="unique"
                      label="Unique Field"
                      disabled={isDisabled}
                  />
                  <ToggleField
                      name="required"
                      label="Required Field"
                      disabled={isDisabled}
                      style={{marginLeft: 20}}
                  />
                  <ToggleField
                      name="searchable"
                      label="Searchable"
                      disabled={isDisabled}
                      style={{marginLeft: 20}}
                  />
                </GridCell>
              </GridRow>
              <GridRow>
                <GridCell span={12}>
                  {!SYSTEM_DATA_TYPES.has(formik.values.dataType) && (
                      <DataTypeSelectField label="DataType" disabled={isDisabled || formik.values.properties?.dominant} />
                  )}
                  <SchemaFields
                      schema={schema}
                      isDisabled={isDisabled || formik.values.properties?.dominant}
                      applicationId={applicationId}
                      entityId={entityId}
                      entityDisplayName={entityDisplayName}
                  />
                  <EnumSelectField
                      label="InputType"
                      name="inputType"
                      disabled={isDisabled}
                      options={[]}
                  />
                </GridCell>
              </GridRow>
            </Grid>
            {/*{ || !formik.isValid}*/}
            <Button
                type="submit"
                buttonStyle={EnumButtonStyle.Primary}
                disabled={ isDisabled || !formik.dirty }
            >
              {intl.formatMessage({id: "update"})}
            </Button>
            <Button type="reset"
                    buttonStyle={EnumButtonStyle.CallToAction}
                    disabled={ isDisabled || !formik.dirty}
                    style={{marginLeft:20}}
            >
              {intl.formatMessage({id: "reset"})}
            </Button>
          </Form>
        );
      }}
    </Formik>
  );
};

export default EntityFieldForm;

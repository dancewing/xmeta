import React, { useMemo } from "react";
import { Formik } from "formik";

import omitDeep from "deepdash-es/omitDeep";

import * as models from "../models";
import { TextField } from "@xmeta/design-system";
import { DisplayNameField } from "../Components/DisplayNameField";
import NameField from "../Components/NameField";
import { Form } from "../Components/Form";
import { USER_ENTITY } from "./constants";
import { validate } from "../util/formikValidateJsonSchema";
import {Button, EnumButtonStyle} from "../Components/Button";
import {CROSS_OS_CTRL_ENTER} from "../util/hotkeys";
import {GlobalHotKeys} from "react-hotkeys";

type EntityInput = Omit<models.Entity, "fields" | "versionNumber">;

type Props = {
  entity?: models.Entity;
  applicationId: string;
  onSubmit: (entity: EntityInput) => void;
};

const NON_INPUT_GRAPHQL_PROPERTIES = [
  "createdAt",
  "updatedAt",
  "versionNumber",
  "fields",
  "permissions",
  "lockedAt",
  "lockedByUser",
  "lockedByUserId",
  "__typename",
];

const FORM_SCHEMA = {
  required: ["name", "displayName"],
  properties: {
    displayName: {
      type: "string",
      minLength: 2,
    },
    name: {
      type: "string",
      minLength: 2,
    },
  },
};

const CLASS_NAME = "entity-form";

const keyMap = {
  SUBMIT: CROSS_OS_CTRL_ENTER,
};

const EntityForm = React.memo(({ entity, applicationId, onSubmit }: Props) => {
  const initialValues = useMemo(() => {
    const sanitizedDefaultValues = omitDeep(
      {
        ...entity,
      },
      NON_INPUT_GRAPHQL_PROPERTIES
    );
    return sanitizedDefaultValues as EntityInput;
  }, [entity]);

  return (
    <div className={CLASS_NAME}>
      <Formik
        initialValues={initialValues}
        validate={(values) => {
          return validate(values, FORM_SCHEMA);
        }}
        enableReinitialize
        onSubmit={onSubmit}
      >
        {(formik) => {
          const handlers = {
            SUBMIT: formik.submitForm,
          };
          return (
            <Form childrenAsBlocks>
              <GlobalHotKeys keyMap={keyMap} handlers={handlers} />
              <>
                <NameField
                    name="name"
                    disabled={USER_ENTITY === entity?.name}
                    capitalized
                />
                <DisplayNameField name="displayName" label="Display Name" />
                <TextField
                    name="table"
                    label="Table"
                />
                <TextField
                  autoComplete="off"
                  textarea
                  rows={2}
                  name="description"
                  label="Description"
                />
                <Button
                    type="submit"
                    buttonStyle={EnumButtonStyle.Primary}
                    disabled={!formik.isValid || !formik.dirty }
                >
                  Update
                </Button>
                <Button type="reset"
                        buttonStyle={EnumButtonStyle.CallToAction}
                        disabled={!formik.dirty}
                        style={{marginLeft:20}}
                >
                  Reset
                </Button>
              </>
            </Form>
          );
        }}
      </Formik>
    </div>
  );
});

export default EntityForm;

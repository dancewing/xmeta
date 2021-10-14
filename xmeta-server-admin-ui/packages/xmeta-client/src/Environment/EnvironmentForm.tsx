import React, { useMemo } from "react";
import { Formik } from "formik";

import omitDeep from "deepdash-es/omitDeep";

import * as models from "../models";
import { TextField } from "@xmeta/design-system";
import { Form } from "../Components/Form";
import { validate } from "../util/formikValidateJsonSchema";
import {Button, EnumButtonStyle} from "../Components/Button";

type EntityInput = Omit<models.Environment, "fields" | "versionNumber">;

type Props = {
  entity?: models.Environment;
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
  required: ["name", "user", "address"],
  properties: {
    name: {
      type: "string",
      minLength: 2,
    },
    user: {
      type: "string",
      minLength: 2,
    },
    address: {
      type: "string",
      minLength: 2,
    },
  },
};

const CLASS_NAME = "entity-form";

const EnvironmentForm = React.memo(({ entity, applicationId, onSubmit }: Props) => {
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
          return (
            <Form childrenAsBlocks>
              <>
                <TextField
                  name="name"
                  label="Name"
                  required
                />

                <TextField
                    name="user"
                    label="User"
                    required
                />

                <TextField
                  name="password"
                  label="Password"
                />

                <TextField
                    width={350}
                    name="address"
                    label="JDBC URL"
                    required
                />
                <TextField
                  autoComplete="off"
                  textarea
                  rows={3}
                  name="description"
                  label="Description"
                />
                <Button
                    type="submit"
                    buttonStyle={EnumButtonStyle.Primary}
                    disabled={!formik.isValid}
                >
                  Create Entity
                </Button>
              </>
            </Form>
          );
        }}
      </Formik>
    </div>
  );
});

export default EnvironmentForm;

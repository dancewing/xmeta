import React, { useCallback } from "react";
import { gql, useMutation, useQuery } from "@apollo/client";
import { Formik, Form } from "formik";
import { validate } from "../util/formikValidateJsonSchema";

import { Snackbar } from "@rmwc/snackbar";
import "@rmwc/snackbar/styles";
import * as models from "../models";
import { formatError } from "../util/error";
import {EnumButtonStyle, TextField} from "@amplication/design-system";
import { useTracking } from "../util/analytics";
import { GET_CURRENT_WORKSPACE } from "./WorkspaceSelector";
import "./WorkspaceForm.scss";
import {useIntl} from "react-intl";
import {Button} from "../Components/Button";

type TData = {
  updateWorkspace: models.Workspace;
};

const FORM_SCHEMA = {
  required: ["name"],
  properties: {
    name: {
      type: "string",
      minLength: 2,
    },
  },
};

const CLASS_NAME = "workspace-form";

function WorkspaceForm() {
  const intl = useIntl();
  const { data, error } = useQuery<{
    currentWorkspace: models.Workspace;
  }>(GET_CURRENT_WORKSPACE);

  const { trackEvent } = useTracking();

  const [updateWorkspace, { error: updateError }] = useMutation<TData>(
    UPDATE_WORKSPACE
  );

  const handleSubmit = useCallback(
    (newData) => {
      const { name } = newData;
      trackEvent({
        eventName: "updateWorkspaceInfo",
      });
      updateWorkspace({
        variables: {
          data: {
            name,
          },
          workspaceId: data?.currentWorkspace.id,
        },
      }).catch(console.error);
    },
    [updateWorkspace, data, trackEvent]
  );

  const errorMessage = formatError(error || updateError);
  return (
    <div className={CLASS_NAME}>
      <h2>{intl.formatMessage({id: "workspace.settings"})}</h2>
      {data?.currentWorkspace && (
        <Formik
          initialValues={data.currentWorkspace}
          validate={(values: models.Workspace) => validate(values, FORM_SCHEMA)}
          enableReinitialize
          onSubmit={handleSubmit}
        >
          {(formik) => {
            return (
              <Form>
                <TextField name="name" label={intl.formatMessage({id: "workspace.name"})} />
                <Button
                    type="submit"
                    buttonStyle={EnumButtonStyle.Primary}
                    disabled={ !formik.isValid || !formik.dirty }
                >
                  {intl.formatMessage({id: "update"})}
                </Button>
                <Button type="reset"
                        buttonStyle={EnumButtonStyle.CallToAction}
                        disabled={ !formik.dirty}
                        style={{marginLeft:20}}
                >
                  {intl.formatMessage({id: "reset"})}
                </Button>
              </Form>
            );
          }}
        </Formik>
      )}
      <Snackbar open={Boolean(error)} message={errorMessage} />
    </div>
  );
}

export default WorkspaceForm;

const UPDATE_WORKSPACE = gql`
  mutation updateWorkspace(
    $data: WorkspaceUpdateInput!
    $workspaceId: String!
  ) {
    updateWorkspace(data: $data, where: { id: $workspaceId }) {
      id
      name
    }
  }
`;

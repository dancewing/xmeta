import { TextField } from "@amplication/design-system";
import { gql, Reference, useMutation } from "@apollo/client";
import { Snackbar } from "@rmwc/snackbar";
import { Form, Formik } from "formik";
import React, { useCallback } from "react";
import { GlobalHotKeys } from "react-hotkeys";
import { Button, EnumButtonStyle } from "../Components/Button";
import { EnumImages, SvgThemeImage } from "../Components/SvgThemeImage";
import * as models from "../models";
import { useTracking } from "../util/analytics";
import { formatError } from "../util/error";
import { validate } from "../util/formikValidateJsonSchema";
import { CROSS_OS_CTRL_ENTER } from "../util/hotkeys";
import "./NewWorkspace.scss";
import {useIntl} from "react-intl";

type CreateWorkspaceType = models.WorkspaceCreateInput;

type DType = {
  createWorkspace: models.Workspace;
};

const INITIAL_VALUES: CreateWorkspaceType = {
  name: "",
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
const CLASS_NAME = "new-workspace";

const keyMap = {
  SUBMIT: CROSS_OS_CTRL_ENTER,
};

type Props = {
  onWorkspaceCreated: (workspace: models.Workspace) => void;
};

const NewWorkspace = ({ onWorkspaceCreated }: Props) => {
  const { trackEvent } = useTracking();
  const intl = useIntl();
  const [createWorkspace, { error, loading }] = useMutation<DType>(
    CREATE_WORKSPACE,
    {
      onCompleted: (data) => {
        trackEvent({
          eventName: "createWorkspace",
          workspaceName: data.createWorkspace.name,
        });
        onWorkspaceCreated && onWorkspaceCreated(data.createWorkspace);
      },
      update(cache, { data }) {
        if (!data) return;

        const newWorkspace = data.createWorkspace;

        cache.modify({
          fields: {
            workspaces(existingWorkspaceRefs = [], { readField }) {
              const newWorkspaceRef = cache.writeFragment({
                data: newWorkspace,
                fragment: NEW_WORKSPACE_FRAGMENT,
              });

              if (
                existingWorkspaceRefs.some(
                  (WorkspaceRef: Reference) =>
                    readField("id", WorkspaceRef) === newWorkspace.id
                )
              ) {
                return existingWorkspaceRefs;
              }

              return [...existingWorkspaceRefs, newWorkspaceRef];
            },
          },
        });
      },
    }
  );

  const handleSubmit = useCallback(
    (data: CreateWorkspaceType) => {
      createWorkspace({
        variables: {
          data,
        },
      }).catch(console.error);
    },
    [createWorkspace]
  );

  const errorMessage = formatError(error);

  return (
    <div className={CLASS_NAME}>
      <SvgThemeImage image={EnumImages.Entities} />
      <div className={`${CLASS_NAME}__instructions`}>
        {intl.formatMessage({id: "workspace.create.title"})}
      </div>
      <Formik
        initialValues={INITIAL_VALUES}
        validate={(values: CreateWorkspaceType) =>
          validate(values, FORM_SCHEMA)
        }
        onSubmit={handleSubmit}
        validateOnMount
      >
        {(formik) => {
          const handlers = {
            SUBMIT: formik.submitForm,
          };
          return (
            <Form>
              <GlobalHotKeys keyMap={keyMap} handlers={handlers} />
              <TextField
                name="name"
                label={intl.formatMessage({id: 'workspace.name'})}
                disabled={loading}
                autoFocus
                hideLabel
                placeholder={intl.formatMessage({id: 'workspace.name.placeholder'})}
                autoComplete="off"
              />
              <Button
                type="submit"
                buttonStyle={EnumButtonStyle.Primary}
                disabled={!formik.isValid || loading}
              >
                {intl.formatMessage({id: 'save'})}
              </Button>
            </Form>
          );
        }}
      </Formik>
      <Snackbar open={Boolean(error)} message={errorMessage} />
    </div>
  );
};

export default NewWorkspace;

const CREATE_WORKSPACE = gql`
  mutation createWorkspace($data: WorkspaceCreateInput!) {
    createWorkspace(data: $data) {
      id
      name
    }
  }
`;

const NEW_WORKSPACE_FRAGMENT = gql`
  fragment NewWorkspace on Workspace {
    id
    name
  }
`;

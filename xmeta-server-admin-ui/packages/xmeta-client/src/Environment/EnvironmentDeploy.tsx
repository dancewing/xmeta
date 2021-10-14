import React, {useState, useCallback} from "react";
import {gql, useMutation, useQuery} from "@apollo/client";
import { Snackbar } from "@rmwc/snackbar";
import { CircularProgress } from "@rmwc/circular-progress";
import { Formik, Form } from "formik";
import { formatError } from "../util/error";
import * as models from "../models";
import PageContent from "../Layout/PageContent";
import "./EnvironmentList.scss";
import {SelectField, TextField} from "@amplication/design-system";
import {Button, EnumButtonStyle} from "../Components/Button";

type TData = {
  environments: models.Environment[];
};

type Props = {
  applicationId: string;
  buildId: string;
  onCompleted: () => void;
};

type UpdateData = {
  createDeployment: models.DeploymentCreateInput;
};

const CLASS_NAME = "entity-list";

const INITIAL_VALUES: models.DeploymentCreateInput = {
  buildId: "",
  environmentId: "",
  message: "",
};

const EnvironmentDeploy = ({ applicationId, buildId, onCompleted}: Props) => {

  const [error] = useState<Error>();

  const {
    data,
    loading,
    error: errorLoading
  } = useQuery<TData>(GET_ENTITIES, {
    variables: {
      id: applicationId,
    },
  });

  const [updateEntity] = useMutation<UpdateData>(
      DEPLOY_ENTITY,
      {
        onCompleted: (data) => {
          onCompleted();
        },
      }
  );

  const handleSubmit = useCallback(
      (data: Omit<models.DeploymentCreateInput, "versionNumber">) => {
        /**@todo: check why the "fields" and "permissions" properties are not removed by omitDeep in the form */
        console.log(111);
        let {
          environmentId,
          message
        } = data;

        updateEntity({
          variables: {
            data: {
              buildId,
              environmentId,
              message
            },
          },
        }).catch(console.error);
      },
      [updateEntity]
  );

  const errorMessage =
    formatError(errorLoading) || (error && formatError(error));

  return (
    <PageContent className={CLASS_NAME}>
      {loading && <CircularProgress />}
      <Formik
          initialValues={INITIAL_VALUES}
          enableReinitialize
          onSubmit={handleSubmit}
      >
        {(formik) => {
          return (
              <Form>
                {data?.environments && <SelectField options={data.environments.map(env=>{
                  return {label: env.name, value: env.id}
                })} label="Environment" name="environmentId" />}

                <TextField name="message" label="message" />

                <Button
                    type="submit"
                    buttonStyle={EnumButtonStyle.Primary}
                    disabled={!formik.isValid}
                >
                  Deploy
                </Button>
              </Form>
          );
        }}
      </Formik>


      <Snackbar open={Boolean(error || errorLoading)} message={errorMessage} />
    </PageContent>
  );
  /**@todo: move error message to hosting page  */
};

export default EnvironmentDeploy;

/**@todo: expand search on other field  */
/**@todo: find a solution for case insensitive search  */
export const GET_ENTITIES = gql`
  query getEnvironments(
    $id: String!
  ) {
    environments(
      where: { id: $id }
    ) {
      id
      name
      description
    }
  }
`;


const DEPLOY_ENTITY = gql`
  mutation createDeployment($data: DeploymentCreateInput!) {
    createDeployment(data: $data) {
      id
      message
    }
  }
`;

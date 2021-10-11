import React, {useState, useEffect, useCallback} from "react";
import {gql, useMutation, useQuery} from "@apollo/client";
import { Snackbar } from "@rmwc/snackbar";
import { CircularProgress } from "@rmwc/circular-progress";
import { Formik, Form } from "formik";
import { formatError } from "../util/error";
import * as models from "../models";
import PageContent from "../Layout/PageContent";
import "./EnvironmentList.scss";
import {SelectField} from "@amplication/design-system";
import {Button, EnumButtonStyle} from "../Components/Button";

type TData = {
  environments: models.Environment[];
};

type Props = {
  applicationId: string;
  buildId: string;
};

type UpdateData = {
  deployment: models.Deployment;
};

const CLASS_NAME = "entity-list";

const POLL_INTERVAL = 2000;

const EnvironmentDeploy = ({ applicationId, buildId }: Props) => {

  const [error] = useState<Error>();

  const {
    data,
    loading,
    error: errorLoading,
    refetch,
    stopPolling,
    startPolling,
  } = useQuery<TData>(GET_ENTITIES, {
    variables: {
      id: applicationId,
    },
  });

  //start polling with cleanup
  useEffect(() => {
    refetch().catch(console.error);
    startPolling(POLL_INTERVAL);
    return () => {
      stopPolling();
    };
  }, [refetch, stopPolling, startPolling]);

  const [updateEntity] = useMutation<UpdateData>(
      DEPLOY_ENTITY,
      {}
  );

  const handleSubmit = useCallback(
      (data: Omit<models.DeploymentCreateInput, "versionNumber">) => {
        /**@todo: check why the "fields" and "permissions" properties are not removed by omitDeep in the form */
        console.log(111);
        let {
          ...sanitizedCreateData
        } = data;

        updateEntity({
          variables: {
            data: {
              build: { connect: { id: buildId}},
              environment: { connect: { id: buildId}},
              ...sanitizedCreateData,
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
          initialValues={{}}
          enableReinitialize
          onSubmit={handleSubmit}
      >
        {(formik) => {
          return (
              <Form>
                {data?.environments && <SelectField options={data.environments.map(env=>{
                  return {label: env.name, value: env.id}
                })} label='Environment' name= 'environment' />}

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

import React, { useCallback } from "react";
import { Switch, match, useLocation } from "react-router-dom";
import { gql, useQuery, useMutation } from "@apollo/client";
import { Snackbar } from "@rmwc/snackbar";
import "@rmwc/snackbar/styles";
import * as models from "../models";
import { formatError } from "../util/error";
import EnvironmentForm from "./EnvironmentForm";
import PageContent from "../Layout/PageContent";
import useNavigationTabs from "../Layout/UseNavigationTabs";
import { useTracking, track } from "../util/analytics";
import RouteWithAnalytics from "../Layout/RouteWithAnalytics";

import "./Environment.scss";

type Props = {
  match: match<{ application: string; entityId: string; fieldId: string }>;
};

type TData = {
  environment: models.Environment;
};

type UpdateData = {
  updateEnvironment: models.Environment;
};

const NAVIGATION_KEY = "ENTITY";

const Environment = ({ match }: Props) => {
  const { entityId, application } = match.params;
  const { trackEvent } = useTracking();
  const location = useLocation();

  const { data, loading, error } = useQuery<TData>(GET_ENTITY, {
    variables: {
      id: entityId,
    },
  });

  useNavigationTabs(
    application,
    `${NAVIGATION_KEY}_${entityId}`,
    location.pathname,
    data?.environment.name
  );

  const [updateEntity, { error: updateError }] = useMutation<UpdateData>(
    UPDATE_ENTITY,
    {
      onCompleted: (data) => {
        trackEvent({
          eventName: "updateEntity",
          entityName: data.updateEnvironment.name,
        });
      },
    }
  );

  const handleSubmit = useCallback(
    (data: Omit<models.Environment, "versionNumber">) => {
      /**@todo: check why the "fields" and "permissions" properties are not removed by omitDeep in the form */

      let {
        id,
        ...sanitizedCreateData
      } = data;

      updateEntity({
        variables: {
          data: {
            appId: application, // eslint-disable-line @typescript-eslint/no-unused-vars
            ...sanitizedCreateData,
          },
          where: {
            id: id,
          },
        },
      }).catch(console.error);
    },
    [updateEntity]
  );

  const errorMessage = formatError(error || updateError);

  return (
    <PageContent>
      {loading ? (
        <span>Loading...</span>
      ) : !data ? (
        <span>can't find</span> /**@todo: Show formatted error message */
      ) : (
        <Switch>
          <RouteWithAnalytics path="/:application/environments/:entityId">
            <EnvironmentForm
              entity={data.environment}
              applicationId={application}
              onSubmit={handleSubmit}
            />
          </RouteWithAnalytics>
        </Switch>
      )}

      <Snackbar open={Boolean(error || updateError)} message={errorMessage} />
    </PageContent>
  );
};

const enhance = track((props) => {
  return { entityId: props.match.params.entityId };
});

export default enhance(Environment);

export const GET_ENTITY = gql`
  query getEnvironment($id: String!) {
    environment(where: { id: $id }) {
      id
      name
      description
      address
      user
      password
    }
  }
`;

const UPDATE_ENTITY = gql`
  mutation updateEnvironment($data: EnvironmentUpdateInput!, $where: WhereUniqueInput!) {
    updateEnvironment(data: $data, where: $where) {
      id
      appId
      name
      description
      address
      user
      password
    }
  }
`;

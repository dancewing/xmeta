import React from "react";
import { Switch, Route, match, useLocation } from "react-router-dom";
import { gql, useQuery } from "@apollo/client";
import { Snackbar } from "@rmwc/snackbar";
import "@rmwc/snackbar/styles";
import classNames from "classnames";
import * as models from "../models";
import { formatError } from "../util/error";
import PageContent from "../Layout/PageContent";
import { CircleBadge } from "@xmeta/design-system";
import ApplicationForm from "./ApplicationForm";
import "./ApplicationHome.scss";
import EntitiesTile from "./EntitiesTile";
import NewVersionTile from "./NewVersionTile";
import RolesTile from "./RolesTile";
import { COLOR_TO_NAME } from "./constants";
import useNavigationTabs from "../Layout/UseNavigationTabs";
import InnerTabLink from "../Layout/InnerTabLink";
import RouteWithAnalytics from "../Layout/RouteWithAnalytics";
import { SHOW_ROLE_PERMISSIONS } from "../feature-flags";
type Props = {
  match: match<{ application: string }>;
};

const CLASS_NAME = "application-home";
const NAVIGATION_KEY = "APP_HOME";

function ApplicationHome({ match }: Props) {
  const applicationId = match.params.application;
  const location = useLocation();

  const { data, error } = useQuery<{
    app: models.App;
  }>(GET_APPLICATION, {
    variables: {
      id: applicationId,
    },
  });
  useNavigationTabs(
    applicationId,
    NAVIGATION_KEY,
    location.pathname,
    data?.app.name
  );

  const errorMessage = formatError(error);

  return (
    <PageContent
      className={CLASS_NAME}
      sideContent={
        <>
          <div>
            <InnerTabLink to={`/${applicationId}/`} icon="home">
              概览
            </InnerTabLink>
          </div>
          <div>
            <InnerTabLink to={`/${applicationId}/update`} icon="settings">
              应用设置
            </InnerTabLink>
          </div>
        </>
      }
    >
      <Switch>
        <Route
          path="/:application/"
          render={() => (
            <>
              <div
                className={classNames(
                  `${CLASS_NAME}__header`,
                  `theme-${data && COLOR_TO_NAME[data.app.color]}`
                )}
              >
                {data?.app.name}
                <CircleBadge
                  name={data?.app.name || ""}
                  color={data?.app.color || "transparent"}
                />
              </div>
              <Switch>
                <RouteWithAnalytics
                  exact
                  path="/:application/"
                  component={() => (
                    <div className={`${CLASS_NAME}__tiles`}>
                      <NewVersionTile applicationId={applicationId} />
                      <EntitiesTile applicationId={applicationId} />
                      { SHOW_ROLE_PERMISSIONS && (
                          <RolesTile applicationId={applicationId} />
                      )}
                    </div>
                  )}
                />
                <RouteWithAnalytics
                  path="/:application/update"
                  component={ApplicationForm}
                />
              </Switch>
            </>
          )}
        />
      </Switch>
      <Snackbar open={Boolean(error)} message={errorMessage} />
    </PageContent>
  );
}

export default ApplicationHome;

export const GET_APPLICATION = gql`
  query getApplication($id: String!) {
    app(where: { id: $id }) {
      id
      createdAt
      updatedAt
      name
      description
      color
      githubTokenCreatedDate
      githubSyncEnabled
      githubRepo
      githubLastSync
      githubLastMessage
    }
  }
`;

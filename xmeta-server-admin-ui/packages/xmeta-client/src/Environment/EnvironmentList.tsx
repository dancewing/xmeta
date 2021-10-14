import React, { useState, useCallback, useEffect } from "react";
import { match } from "react-router-dom";
import { gql, useQuery } from "@apollo/client";
import { Snackbar } from "@rmwc/snackbar";
import { CircularProgress } from "@rmwc/circular-progress";

import { formatError } from "../util/error";
import * as models from "../models";
import { Dialog } from "@xmeta/design-system";
import useNavigationTabs from "../Layout/UseNavigationTabs";

import NewEntity from "./NewEntity";
import { EnvironmentListItem } from "./EnvironmentListItem";
import PageContent from "../Layout/PageContent";

import { Button, EnumButtonStyle } from "../Components/Button";
import "./EnvironmentList.scss";

type TData = {
  environments: models.Environment[];
};

type Props = {
  match: match<{ application: string }>;
};

const CLASS_NAME = "entity-list";
const NAVIGATION_KEY = "ENTITY_LIST";

const POLL_INTERVAL = 2000;

export const EnvironmentList = ({ match }: Props) => {
  const { application } = match.params;
  const [error, setError] = useState<Error>();

  useNavigationTabs(application, NAVIGATION_KEY, match.url, "Environments");

  const [newEntity, setNewEntity] = useState<boolean>(false);

  const handleNewEntityClick = useCallback(() => {
    setNewEntity(!newEntity);
  }, [newEntity, setNewEntity]);

  const {
    data,
    loading,
    error: errorLoading,
    refetch,
    stopPolling,
    startPolling,
  } = useQuery<TData>(GET_ENTITIES, {
    variables: {
      id: application,
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

  const errorMessage =
    formatError(errorLoading) || (error && formatError(error));

  return (
    <PageContent className={CLASS_NAME}>
      <Dialog
        className="new-entity-dialog"
        isOpen={newEntity}
        onDismiss={handleNewEntityClick}
        title="New Entity"
      >
        <NewEntity applicationId={application} />
      </Dialog>
      <div className={`${CLASS_NAME}__header`}>
        <Button
          className={`${CLASS_NAME}__add-button`}
          buttonStyle={EnumButtonStyle.Primary}
          onClick={handleNewEntityClick}
          icon="plus"
        >
          Add environment
        </Button>
      </div>
      <div className={`${CLASS_NAME}__title`}>
        {data?.environments.length} Environments
      </div>
      {loading && <CircularProgress />}

      {data?.environments.map((entity) => (
        <EnvironmentListItem
          key={entity.id}
          entity={entity}
          applicationId={application}
          onError={setError}
        />
      ))}

      <Snackbar open={Boolean(error || errorLoading)} message={errorMessage} />
    </PageContent>
  );
  /**@todo: move error message to hosting page  */
};

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

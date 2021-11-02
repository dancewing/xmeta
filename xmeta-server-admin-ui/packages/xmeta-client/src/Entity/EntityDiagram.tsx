import React from "react";
import {gql, useQuery} from "@apollo/client";
import {match } from "react-router-dom";
import {Snackbar} from "@rmwc/snackbar";
import * as models from "../models";
import {formatError} from "../util/error";
import "./EntityDiagram.scss";
import ERGraphPage from "../Graph";
import useNavigationTabs from "../Layout/UseNavigationTabs";

type TData = {
  entities: models.Entity[];
};

export const CLASS_NAME = "create-app-from-excel";

type Props = {
  match: match<{ application: string }>;
};
const NAME_FIELD = "displayName";

export const EntityDiagram= ({ match }: Props) => {
  const { application } = match.params;

  useNavigationTabs(application, "Diagram", match.url, "Diagram");

  const {
    data,
    loading,
    error,
  } = useQuery<TData>(GET_ENTITIES, {
    variables: {
      id: application,
      orderBy: {
        [NAME_FIELD]: models.SortOrder.Asc,
      },
    },
  });

  const errorMessage = formatError(error);

  return (
    <div className={CLASS_NAME}>
      <div className={`${CLASS_NAME}__layout`}>
        {!loading && data ?  (
          <ERGraphPage entities={data.entities} />
        ): null}
        <Snackbar
          open={Boolean(error)}
          message={errorMessage}
        />
      </div>
    </div>
  );
}

export const GET_ENTITIES = gql`
  query getEntities(
    $id: String!
    $orderBy: EntityOrderByInput
    $whereName: StringFilter
  ) {
    entities(
      where: { app: { id: $id }, displayName: $whereName }
      orderBy: $orderBy
    ) {
      id
      name
      displayName
      description
      lockedByUserId
      lockedAt
      lockedByUser {
        account {
          firstName
          lastName
        }
      }
      fields {
        id
        displayName
        name
        dataType
        required
        unique
        searchable
        description
        properties
        column
        inputType
        properties
      }
    }
  }
`;

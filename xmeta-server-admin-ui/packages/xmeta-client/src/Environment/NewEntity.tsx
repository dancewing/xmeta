import React, { useCallback, useEffect, useContext } from "react";
import { useHistory } from "react-router-dom";
import { Snackbar } from "@rmwc/snackbar";
import { gql, useMutation, Reference } from "@apollo/client";
import { pascalCase } from "pascal-case";
import { formatError } from "../util/error";
import * as models from "../models";
import {
  generateSingularDisplayName,
} from "../Components/PluralDisplayNameField";
import PendingChangesContext from "../VersionControl/PendingChangesContext";
import { useTracking } from "../util/analytics";
import "./NewEntity.scss";
import EnvironmentForm from "./EnvironmentForm";

type CreateEntityType = Omit<models.EntityCreateInput, "app">;

type DType = {
  createOneEntity: models.Entity;
};

type Props = {
  applicationId: string;
};

const CLASS_NAME = "new-entity";

const NewEntity = ({ applicationId }: Props) => {
  const { trackEvent } = useTracking();
  const pendingChangesContext = useContext(PendingChangesContext);

  const [createEntity, { error, data }] = useMutation<DType>(
    CREATE_ENTITY,
    {
      onCompleted: (data) => {
        pendingChangesContext.addEntity(data.createOneEntity.id);
        trackEvent({
          eventName: "createEntity",
          entityName: data.createOneEntity.displayName,
        });
      },
      update(cache, { data }) {
        if (!data) return;

        const newEntity = data.createOneEntity;

        cache.modify({
          fields: {
            entities(existingEntityRefs = [], { readField }) {
              const newEntityRef = cache.writeFragment({
                data: newEntity,
                fragment: NEW_ENTITY_FRAGMENT,
              });

              if (
                existingEntityRefs.some(
                  (EntityRef: Reference) =>
                    readField("id", EntityRef) === newEntity.id
                )
              ) {
                return existingEntityRefs;
              }

              return [...existingEntityRefs, newEntityRef];
            },
          },
        });
      },
    }
  );
  const history = useHistory();

  const handleSubmit = useCallback(
    (data: CreateEntityType) => {
      const displayName = data.displayName.trim();
      const singularDisplayName = generateSingularDisplayName(displayName);
      const name = pascalCase(singularDisplayName);

      createEntity({
        variables: {
          data: {
            ...data,
            displayName,
            name,
            app: { connect: { id: applicationId } },
          },
        },
      }).catch(console.error);
    },
    [createEntity, applicationId]
  );

  useEffect(() => {
    if (data) {
      history.push(`/${applicationId}/entities/${data.createOneEntity.id}`);
    }
  }, [history, data, applicationId]);

  const errorMessage = formatError(error);

  return (
    <div className={CLASS_NAME}>

      <EnvironmentForm applicationId={applicationId} onSubmit={handleSubmit} />

      <Snackbar open={Boolean(error)} message={errorMessage} />
    </div>
  );
};

export default NewEntity;

const CREATE_ENTITY = gql`
  mutation createEntity($data: EntityCreateInput!) {
    createOneEntity(data: $data) {
      id
      name
      fields {
        id
        name
        dataType
      }
    }
  }
`;

const NEW_ENTITY_FRAGMENT = gql`
  fragment NewEntity on Entity {
    id
    name
    fields {
      id
      name
      dataType
    }
  }
`;

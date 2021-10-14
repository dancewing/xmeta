import React, { useCallback, useContext, useState } from "react";
import { gql, useMutation, Reference } from "@apollo/client";
import * as models from "../models";
import {
  ConfirmationDialog,
  Panel,
  EnumPanelStyle,
} from "@xmeta/design-system";
import { Link, useHistory } from "react-router-dom";
import { Button, EnumButtonStyle } from "../Components/Button";
import PendingChangesContext from "../VersionControl/PendingChangesContext";
import { USER_ENTITY } from "./constants";
import "./EnvironmentListItem.scss";

const CONFIRM_BUTTON = { icon: "trash_2", label: "Delete" };
const DISMISS_BUTTON = { label: "Dismiss" };

type DType = {
  deleteEntity: { id: string };
};

type Props = {
  applicationId: string;
  entity: models.Environment;
  onDelete?: () => void;
  onError: (error: Error) => void;
};

const CLASS_NAME = "entity-list-item";

export const EnvironmentListItem = ({
  entity,
  applicationId,
  onDelete,
  onError,
}: Props) => {
  const pendingChangesContext = useContext(PendingChangesContext);
  const history = useHistory();

  const [confirmDelete, setConfirmDelete] = useState<boolean>(false);

  const [deleteEntity, { loading: deleteLoading }] = useMutation<DType>(
    DELETE_ENTITY,
    {
      update(cache, { data }) {
        if (!data) return;
        const deletedEntityId = data.deleteEntity.id;

        cache.modify({
          fields: {
            entities(existingEntityRefs, { readField }) {
              return existingEntityRefs.filter(
                (entityRef: Reference) =>
                  deletedEntityId !== readField("id", entityRef)
              );
            },
          },
        });
      },
      onCompleted: (data) => {
        pendingChangesContext.addEntity(data.deleteEntity.id);
        onDelete && onDelete();
      },
    }
  );

  const handleDelete = useCallback(
    (event) => {
      event.stopPropagation();
      setConfirmDelete(true);
    },
    [setConfirmDelete]
  );

  const handleDismissDelete = useCallback(() => {
    setConfirmDelete(false);
  }, [setConfirmDelete]);

  const handleConfirmDelete = useCallback(() => {
    setConfirmDelete(false);
    deleteEntity({
      variables: {
        entityId: entity.id,
      },
    }).catch(onError);
  }, [entity, deleteEntity, onError]);

  const handleRowClick = useCallback(() => {
    history.push(`/${applicationId}/environments/${entity.id}`);
  }, [history, applicationId, entity]);

  return (
    <>
      <ConfirmationDialog
        isOpen={confirmDelete}
        title={`Delete ${entity.name}`}
        confirmButton={CONFIRM_BUTTON}
        dismissButton={DISMISS_BUTTON}
        message="Are you sure you want to delete this entity?"
        onConfirm={handleConfirmDelete}
        onDismiss={handleDismissDelete}
      />
      <Panel
        className={CLASS_NAME}
        clickable
        onClick={handleRowClick}
        panelStyle={EnumPanelStyle.Bordered}
      >
        <div className={`${CLASS_NAME}__row`}>
          <Link
            className={`${CLASS_NAME}__title`}
            title={entity.name}
            to={`/${applicationId}/environments/${entity.id}`}
          >
            {entity.name}
          </Link>
          <span className="spacer" />
          {!deleteLoading && entity.name !== USER_ENTITY && (
            <Button
              buttonStyle={EnumButtonStyle.Clear}
              icon="trash_2"
              onClick={handleDelete}
            />
          )}
        </div>
        <div className={`${CLASS_NAME}__row`}>
          <span className={`${CLASS_NAME}__description`}>
            {entity.description}
          </span>
        </div>
      </Panel>
    </>
  );
};

const DELETE_ENTITY = gql`
  mutation deleteEntity($entityId: String!) {
    deleteEntity(where: { id: $entityId }) {
      id
    }
  }
`;

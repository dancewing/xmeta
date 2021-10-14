import React, {useCallback, useContext, useMemo, useState} from "react";

import { Link } from "react-router-dom";
import { Icon } from "@rmwc/icon";
import { Dialog } from "@xmeta/design-system";
import * as models from "../models";
import { EnumButtonStyle, Button } from "../Components/Button";

import useBuildWatchStatus from "./useBuildWatchStatus";
import { BuildStepsStatus } from "./BuildStepsStatus";
import { HelpPopover } from "../Components/HelpPopover";
import EnvironmentDeploy from '../Environment/EnvironmentDeploy';
import useLocalStorage from "react-use-localstorage";

import "./BuildSummary.scss";
import PendingChangesContext from "./PendingChangesContext";

const CLASS_NAME = "build-summary";

export const EMPTY_STEP: models.ActionStep = {
  id: "",
  createdAt: null,
  name: "",
  status: models.EnumActionStepStatus.Waiting,
  message: "",
};

export const GENERATE_STEP_NAME = "GENERATE_APPLICATION";
export const BUILD_DOCKER_IMAGE_STEP_NAME = "BUILD_DOCKER";
export const DEPLOY_STEP_NAME = "DEPLOY_APP";
export const PUSH_TO_GITHUB_STEP_NAME = "PUSH_TO_GITHUB";

type Props = {
  build: models.Build;
  onError: (error: Error) => void;
};

const LOCAL_STORAGE_KEY_SHOW_SANDBOX_HELP = "ShowGSandboxContextHelp";

const BuildSummary = ({ build, onError }: Props) => {
  const { data } = useBuildWatchStatus(build);
  const pendingChangesContext = useContext(PendingChangesContext);

  const [newDeploy, setNewDeploy] = useState<boolean>(false);

  const handleNewDeployClick = useCallback(() => {
    setNewDeploy(!newDeploy);
  }, [newDeploy, setNewDeploy]);

  const [showSandboxHelp, setShowSandboxHelp] = useLocalStorage(
    LOCAL_STORAGE_KEY_SHOW_SANDBOX_HELP,
    "false"
  );

  const handleDismissHelpSandbox = useCallback(() => {
    setShowSandboxHelp("false");
  }, [setShowSandboxHelp]);

  const stepBuildDocker = useMemo(() => {
    if (!data.build.action?.steps?.length) {
      return EMPTY_STEP;
    }
    return (
      data.build.action.steps.find(
        (step) => step.name === BUILD_DOCKER_IMAGE_STEP_NAME
      ) || EMPTY_STEP
    );
  }, [data.build.action]);

  const stepDeploy = useMemo(() => {
    if (!data.build.action?.steps?.length) {
      return EMPTY_STEP;
    }
    return (
      data.build.action.steps.find((step) => step.name === DEPLOY_STEP_NAME) ||
      null
    );
  }, [data.build.action]);

  const deployment =
    data.build.deployments &&
    data.build.deployments.length &&
    data.build.deployments[0];

  return (
    <div className={`${CLASS_NAME}`}>
      <Dialog
          className="new-entity-dialog"
          isOpen={newDeploy}
          onDismiss={handleNewDeployClick}
          title="Deploy to environment"
      >
        <EnvironmentDeploy applicationId={build.appId} buildId={build.id} onCompleted={handleNewDeployClick}  />
      </Dialog>
      <div className={`${CLASS_NAME}__download`}>
        <Button
            buttonStyle={EnumButtonStyle.Primary}
            onClick={handleNewDeployClick}
            disabled={pendingChangesContext.pendingChanges.length > 0}
        >
          Deploy to environment
        </Button>
      </div>

      <HelpPopover
        onDismiss={handleDismissHelpSandbox}
        content={
          <div>
            All your committed changes are continuously deployed to a sandbox
            environment on the Amplication cloud so you can easily access your
            application for testing and development purposes.
          </div>
        }
        open={showSandboxHelp === "false" ? false : true}
        align={"top"}
      >
        {stepBuildDocker.status === models.EnumActionStepStatus.Running ||
        stepDeploy?.status === models.EnumActionStepStatus.Running ? (
          <Link to={`/${build.appId}/builds/${build.id}`}>
            <Button
              buttonStyle={EnumButtonStyle.Secondary}
              eventData={{
                eventName: "BuildSandboxViewDetailsClick",
              }}
            >
              <BuildStepsStatus status={models.EnumActionStepStatus.Running} />
              Preparing sandbox environment...
            </Button>
          </Link>
        ) : deployment &&
          stepDeploy?.status === models.EnumActionStepStatus.Success ? (
          <a href={deployment.environment.address} target="app">
            <Button
              buttonStyle={EnumButtonStyle.Secondary}
              icon="link_2"
              eventData={{
                eventName: "openPreviewApp",
                versionNumber: data.build.version,
              }}
            >
              Open Sandbox environment
            </Button>
          </a>
        ) : (
          <div className={`${CLASS_NAME}__sandbox`}>
            {stepDeploy ? (
              <Link to={`/${build.appId}/builds/${build.id}`}>
                <Button
                  buttonStyle={EnumButtonStyle.Secondary}
                  eventData={{
                    eventName: "buildFailedViewDetails",
                  }}
                >
                  <BuildStepsStatus
                    status={models.EnumActionStepStatus.Failed}
                  />
                  Deployment to sandbox failed
                </Button>
              </Link>
            ) : (
              <>
                <Icon icon={{ size: "xsmall", icon: "info_circle" }} />
                <span>Commit changes to start deployment to sandbox. </span>
              </>
            )}
          </div>
        )}
      </HelpPopover>
    </div>
  );
};

export default BuildSummary;

import React, { useMemo } from "react";
import * as models from "../models";
import { Icon } from "@rmwc/icon";
import { Tooltip } from "@primer/components";
import {
  EMPTY_STEP,
  DEPLOY_STEP_NAME,
} from "./BuildSteps";
import { BuildStepsStatus } from "./BuildStepsStatus";
import "./BuildStatusIcons.scss";

const CLASS_NAME = "build-status-icons";
const TOOLTIP_DIRECTION = "nw";

type BuildStatusIconsProps = {
  build: models.Build;
};
export const BuildStatusIcons = ({ build }: BuildStatusIconsProps) => {
  const stepDeploy = useMemo(() => {
    if (!build?.action?.steps?.length) {
      return EMPTY_STEP;
    }
    return (
      build.action.steps.find((step) => step.name === DEPLOY_STEP_NAME) ||
      EMPTY_STEP
    );
  }, [build]);

  return (
      <Tooltip
        direction={TOOLTIP_DIRECTION}
        wrap
        aria-label="Publish App to Sandbox"
        className={`${CLASS_NAME}__status`}
      >
        <BuildStepsStatus status={stepDeploy?.status} />
        <Icon icon="publish" />
      </Tooltip>
  );
};

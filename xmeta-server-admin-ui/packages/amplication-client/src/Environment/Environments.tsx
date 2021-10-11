import React from "react";
import { Switch, Route, match } from "react-router-dom";
import RouteWithAnalytics from "../Layout/RouteWithAnalytics";

import { EnvironmentList } from "./EnvironmentList";
import "./Environments.scss";

import Environment from "./Environment";

type Props = {
  match: match;
};

function Environments({ match }: Props) {
  return (
    <Switch>
      <RouteWithAnalytics
        exact
        path="/:application/environments/"
        component={EnvironmentList}
      />
      <Route path="/:application/environments/:entityId" component={Environment} />
    </Switch>
  );
}

export default Environments;

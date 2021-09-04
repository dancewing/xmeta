import React from "react";
import { Route, RouteProps, useLocation } from "react-router-dom";
import usePageTracking from "../util/usePageTracking";

type Props = Omit<RouteProps, "render">;

function RouteWithAnalytics(props: Props) {
  const { children, component, ...rest } = props;
  const location = useLocation();
  //we use the entire url as a key to force re-render of the content
  //component when switching between different routes on the same page
  //or different resources on the same route
  const key = location.pathname;

  return (
    <Route
      {...rest}
      key={key}
      render={(props) => {
        return (
          <RouteWithAnalyticsContent>
            {component ? React.createElement(component, props) : children}
          </RouteWithAnalyticsContent>
        );
      }}
    />
  );
}

function RouteWithAnalyticsContent({
  children,
}: {
  children: React.ReactNode;
}) {
  usePageTracking();
  return <div>{children}</div>;
}

export default RouteWithAnalytics;

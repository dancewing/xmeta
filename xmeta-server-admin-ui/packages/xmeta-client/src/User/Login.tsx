import React, { useCallback, useEffect, useMemo } from "react";
import { Location } from "history";
import { useHistory, useLocation, Link } from "react-router-dom";
import useApi from '../api';
import {useIntl} from 'react-intl';
import { Formik } from "formik";
import { CircularProgress } from "@rmwc/circular-progress";
import { Snackbar } from "@rmwc/snackbar";
import { REACT_APP_GITHUB_CLIENT_ID } from "../env";
import { setToken } from "../authentication/authentication";
import { formatError } from "../util/error";
import { TextField } from "@xmeta/design-system";
import { Button } from "../Components/Button";
import { Form } from "../Components/Form";
import queryString from "query-string";
import { Icon } from "@rmwc/icon";
import { DEFAULT_PAGE_SOURCE, SIGN_IN_PAGE_CONTENT } from "./constants";

import { GitHubLoginButton } from "./GitHubLoginButton";
import WelcomePage from "../Layout/WelcomePage";
import "./Login.scss";

type Values = {
  email: string;
  password: string;
};

interface LocationStateInterface {
  from?: Location;
}

const CLASS_NAME = "login-page";
const URL_SOURCE_PARAM = "utm_source";

const INITIAL_VALUES: Values = {
  email: "",
  password: "",
};

const Login = () => {
  const history = useHistory();
  const location = useLocation();
  const [data, { loading, error }, request] = useApi( '/login', 'POST');
  const intl = useIntl();
  const content = useMemo(() => {
    const s: LocationStateInterface | undefined | null = location.state;
    const urlParams = new URLSearchParams(s?.from?.search);
    const source = urlParams.get(URL_SOURCE_PARAM) || DEFAULT_PAGE_SOURCE;
    return (
      SIGN_IN_PAGE_CONTENT[source] || SIGN_IN_PAGE_CONTENT[DEFAULT_PAGE_SOURCE]
    );
  }, [location.state]);

  const handleSubmit = useCallback(
    (data) => {
      request(data).catch(console.error);
    },
    [request]
  );

  const urlError = useMemo(() => {
    const params = queryString.parse(location.search);
    console.log("params", params);
    console.log("params.error", params.error);
    return params.error;
  }, [location.search]);

  useEffect(() => {
    if (data) {
      setToken(data);
      // @ts-ignore
      let { from } = location.state || { from: { pathname: "/" } };
      if (from === "login") {
        from = "/";
      }
      history.replace(from);
    }
  }, [data, history, location]);

  const errorMessage = formatError(error);

  return (
    <WelcomePage {...content}>
      <span className={`${CLASS_NAME}__title`}>{intl.formatMessage({id: "welcome"})}</span>
      <Formik initialValues={INITIAL_VALUES} onSubmit={handleSubmit}>
        <Form childrenAsBlocks>
          {urlError && (
            <div className={`${CLASS_NAME}__login-error`}>
              <Icon icon="alert_circle" />
              {urlError}
            </div>
          )}

          {REACT_APP_GITHUB_CLIENT_ID ? (
            <>
              <div className={`${CLASS_NAME}__message`}>
                Welcome to {content.name}. Please use your GitHub account to
                sign&nbsp;in.
              </div>
              <GitHubLoginButton />
              <div className={`${CLASS_NAME}__signup`}>
                Do not have a GitHub account?{" "}
                <a href="https://github.com/join" target="Github">
                  Join GitHub
                </a>
              </div>
            </>
          ) : (
            <>
              <TextField
                label={intl.formatMessage({id: "email"})}
                name="email"
                type="email"
                autoComplete="email"
              />
              <TextField
                label={intl.formatMessage({id: "password"})}
                name="password"
                type="password"
                autoComplete="current-password"
                minLength={8}
              />
              <Button
                type="submit"
                eventData={{
                  eventName: "signInWithUserName",
                }}
              >
                {intl.formatMessage({id: "login"})}
              </Button>
              <div className={`${CLASS_NAME}__signup`}>
                {intl.formatMessage({id: "no-account"})} <Link to="/signup">{intl.formatMessage({id: "register"})}</Link>
              </div>
            </>
          )}

          {loading && <CircularProgress />}
          <Snackbar open={Boolean(error)} message={errorMessage} />
        </Form>
      </Formik>
    </WelcomePage>
  );
};

export default Login;

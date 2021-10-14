import React, { useCallback, useEffect } from "react";
import { useHistory, useLocation, Link } from "react-router-dom";
import {useIntl} from 'react-intl';
import { gql, useMutation } from "@apollo/client";
import { CircularProgress } from "@rmwc/circular-progress";
import { Snackbar } from "@rmwc/snackbar";
import "@rmwc/snackbar/styles";
import "@rmwc/circular-progress/styles";
import { setToken } from "../authentication/authentication";
import { formatError } from "../util/error";
import { Formik, Form } from "formik";
import WelcomePage from "../Layout/WelcomePage";
import { TextField } from "@xmeta/design-system";
import { Button } from "../Components/Button";
import { SIGN_IN_PAGE_CONTENT, DEFAULT_PAGE_SOURCE } from "./constants";
import "./Signup.scss";

type Values = {
  email: string;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
  workspaceName: string;
};

const CLASS_NAME = "signup-page";

const PAGE_CONTENT = SIGN_IN_PAGE_CONTENT[DEFAULT_PAGE_SOURCE];

const INITIAL_VALUES: Values = {
  email: "",
  password: "",
  confirmPassword: "",
  firstName: "",
  lastName: "",
  workspaceName: "",
};

const Signup = () => {
  const history = useHistory();
  const location = useLocation();
  const [signup, { loading, data, error }] = useMutation(DO_SIGNUP);
  const intl = useIntl();
  const handleSubmit = useCallback(
    (values) => {
      const { confirmPassword, ...data } = values; // eslint-disable-line @typescript-eslint/no-unused-vars
      signup({
        variables: {
          data: {
            ...data,
          },
        },
      }).catch(console.error);
    },
    [signup]
  );

  useEffect(() => {
    if (data) {
      setToken(data.signup.token);
      // @ts-ignore
      const { from } = location.state || { from: { pathname: "/create-app" } };
      history.replace(from);
    }
  }, [data, history, location]);

  const errorMessage = formatError(error);

  return (
    <WelcomePage {...PAGE_CONTENT}>
      <span className={`${CLASS_NAME}__title`}>{intl.formatMessage({id: "register.title"})}</span>
      <Formik initialValues={INITIAL_VALUES} onSubmit={handleSubmit}>
        <Form>
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
            autoComplete="new-password"
            minLength={8}
          />
          <TextField
            label={intl.formatMessage({id: "confirm.password"})}
            name="confirmPassword"
            type="password"
            autoComplete="newPassword"
            minLength={8}
            helpText="Confirm Password should match Password exactly"
            // invalid={formik.values.password !== formik.values.confirmPassword}
          />
          <TextField
            label={intl.formatMessage({id: "firstName"})}
            name="firstName"
            type="text"
            autoComplete="given-name"
          />
          <TextField
            label={intl.formatMessage({id: "lastName"})}
            name="lastName"
            type="text"
            autoComplete="family-name"
          />
          <TextField
            label={intl.formatMessage({id: "workspace.title"})}
            name="workspaceName"
            type="text"
            autoComplete="workspace"
          />

          <Button>{intl.formatMessage({id: "register"})}</Button>
          <p className={`${CLASS_NAME}__signup`}>
            {intl.formatMessage({id: "have-account"})} <Link to="/login">{intl.formatMessage({id: "login"})}</Link>
          </p>
          {loading && <CircularProgress />}
          <Snackbar open={Boolean(error)} message={errorMessage} />
        </Form>
      </Formik>
    </WelcomePage>
  );
};

export default Signup;

const DO_SIGNUP = gql`
  mutation signup($data: SignupInput!) {
    signup(data: $data) {
      token
    }
  }
`;

import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter as Router } from "react-router-dom";
import LanguageProvider from './LanguageProvider';
import {
  ApolloClient,
  InMemoryCache,
  createHttpLink,
  ApolloProvider,
  from,
} from "@apollo/client";
import { onError } from "@apollo/client/link/error";
import * as amplicationDesignSystem from "@xmeta/design-system";
import { SnackbarQueue, createSnackbarQueue } from '@rmwc/snackbar';
import "@xmeta/design-system/icons";
import "@rmwc/grid/styles";
import "./index.scss";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import { getToken, setToken } from "./authentication/authentication";
import { setContext } from "@apollo/client/link/context";
import basename from './basename';

import { translationMessages } from './i18n';

const params = new URLSearchParams(window.location.search);
const token = params.get("token");
if (token) {
  setToken(token);
}

export const queue = createSnackbarQueue()

const httpLink = createHttpLink({
  uri: "/graphql",
});

const authLink = setContext((_, { headers }) => {
  // get the authentication token from local storage if it exists
  const token = getToken();
  // return the headers to the context so httpLink can read them
  return {
    headers: {
      ...headers,
      Authorization: token ? `Bearer ${token}` : "",
    },
  };
});

const errorLink = onError(({ graphQLErrors, networkError }) => {
  if (graphQLErrors)
    graphQLErrors.forEach(({ message, locations, path }) =>
        queue.notify({
          title: <b>Error</b>,
          body: `${message}`,
          icon: 'error',
        })
    );

    if (networkError) {
      queue.notify({
        title: <b>Error</b>,
        body: `${networkError}`,
        icon: 'error',
      })
    };
});


const apolloClient = new ApolloClient({
  cache: new InMemoryCache(),
  link: from([errorLink, authLink.concat(httpLink)]),
});

ReactDOM.render(
    <React.StrictMode>
      <ApolloProvider client={apolloClient}>
        <LanguageProvider messages={translationMessages}>
          <amplicationDesignSystem.Provider>
            <Router basename={basename}>
              <App />
              <SnackbarQueue messages={queue.messages} />
            </Router>
          </amplicationDesignSystem.Provider>
        </LanguageProvider>
      </ApolloProvider>
    </React.StrictMode>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

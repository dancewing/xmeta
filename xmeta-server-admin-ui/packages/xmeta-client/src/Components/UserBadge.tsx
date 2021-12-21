import React, {useEffect} from "react";
import * as models from "../models";
import useApi from '../api';
import {UserAvatar} from "@xmeta/design-system";

import "./UserBadge.scss";
import {identity} from "../util/analytics";

function UserBadge() {
  //const authenticated = useAuthenticated();
  const [ data ] = useApi<null, models.User>('/me', 'GET', {shouldRequest: ()=>true});

  useEffect(() => {
    if (data) {
      identity(data.id, {
        createdAt: data.createdAt,
        email: data.account?.email,
      });
    }
  }, [data]);

  return data ? (
      <UserAvatar
          firstName={data.account?.firstName}
          lastName={data.account?.lastName}
      />
  ) : null;
}

export default UserBadge;

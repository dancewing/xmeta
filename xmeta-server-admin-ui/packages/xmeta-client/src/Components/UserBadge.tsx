import React, {useEffect} from "react";
import {Tooltip} from "@primer/components";

import * as models from "../models";
import useApi from '../api';
import {UserAvatar} from "@xmeta/design-system";

import "./UserBadge.scss";
import {identity} from "../util/analytics";

const TOOLTIP_DIRECTION = "e";

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
    <Tooltip
      direction={TOOLTIP_DIRECTION}
      noDelay
      wrap
      aria-label={`${data.account?.firstName} ${data.account?.lastName}`}
    >
      <UserAvatar
        firstName={data.account?.firstName}
        lastName={data.account?.lastName}
      />
    </Tooltip>
  ) : null;
}

export default UserBadge;

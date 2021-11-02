import React from 'react';
import './Entity.scss';
import { EntityCanvasModel } from './interface';
import { EntityType } from './constants';
import { Icon } from "@rmwc/icon";
import * as models from "../../models";
interface Props {
  entity: EntityCanvasModel;
}

export default class Entity extends React.PureComponent<Props, {}> {
  render() {
    const { entity } = this.props;
    const getCls = () => {
      if (entity.entityType === EntityType.FACT) {
        return 'fact';
      } else if (entity.entityType === EntityType.DIM) {
        return 'dim';
      } else {
        return 'other';
      }
    };
    return (
      <div className={`entity-container ${getCls()}`}>
        <div className={`content ${getCls()}`}>
          <div className="head">
            <div className="title">
              <Icon icon={{icon: 'list', size: "xsmall"}} className="type" />
              <span>{entity?.name}</span>
            </div>
            <Icon icon={{icon: 'more_horizontal', size: "xsmall"}} />
          </div>
          <div className="body">
            {entity.fields?.map((property: models.EntityField) => {
              return (
                <div className="body-item" key={property.id}>
                  <div className="type">{property.dataType}</div>
                  <div className="name">
                    {property.name}
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    );
  }
}

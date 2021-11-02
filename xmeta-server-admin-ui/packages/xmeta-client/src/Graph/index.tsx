import React,{useMemo} from 'react';
import './index.scss';
import ERGraphEditor from './entity-relation';
import * as models from "../models";
import { EntityCanvasModel } from './entity-relation/interface';
import { EntityType } from './entity-relation/constants';

type Props = {
    entities : models.Entity[]
}

const ERGraphPage: React.FC<Props> = (props: Props) => {

    const {entities} = props;
    const initialValues  = useMemo(( ): EntityCanvasModel[]=>{
        return entities.map((entity)=>{
            return {
                ...entity,
                entityType: EntityType.FACT,
                x: 100,
                y: 400,
                width: 214,
                height: 248,
            }
        })
    }, [entities]);
  return (
    <div className="erGraphDemo">
      <ERGraphEditor entities={initialValues} />
    </div>
  );
};

export default ERGraphPage;

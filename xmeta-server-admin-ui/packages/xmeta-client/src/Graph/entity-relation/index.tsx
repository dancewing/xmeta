import React from 'react';
import ERGraph from '../editor';
import {EdgeConfig, NodeConfig} from '../xflow';
import {EntityCanvasModel} from './interface';
import Entity from './Entity';
import * as models from '../../models';

type Prop = {
    entities: EntityCanvasModel[]
}

export default class EREditor extends React.PureComponent<Prop, {}> {
    calRenderData = () => {
        const nodes: NodeConfig[] = this.props.entities.map(
            (entity: EntityCanvasModel) => {
                const {id, x, y, width, height} = entity;
                const nodeData: NodeConfig = {
                    x,
                    y,
                    width,
                    height,
                    id,
                    render: (data: EntityCanvasModel) => {
                        return <Entity entity={data}/>;
                    },
                    data: entity,
                };
                return nodeData;
            },
        );

        const edges: EdgeConfig[] = [];
        this.props.entities.forEach((entity) => {

            entity.fields?.filter((field) => field.dataType === models.EnumDataType.Lookup)
                .forEach((field) => {
                    const {relatedEntityId} = field.properties;
                    const edgeData: EdgeConfig = {
                        id: `${entity.id}_${field.id}`,
                        source: entity.id,
                        target: relatedEntityId,
                        label: '1:N',
                        // render: (data: RelationCanvasModel) => {
                        //   return null;
                        // },
                        data: entity,
                    };
                    edges.push(edgeData);
                })
        });

        return {nodes, edges};
    };

    render() {
        const {nodes, edges} = this.calRenderData();
        return (
            <div style={{width: '100%', height: '100%'}}>
                <ERGraph
                    data={{
                        nodes,
                        edges,
                    }}
                    // graphOptions={{}}
                />
            </div>
        );
    }
}

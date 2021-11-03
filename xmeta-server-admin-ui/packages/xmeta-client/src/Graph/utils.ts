import * as models from '../models';

enum NomlEntityTypes {
  package,
  entity,
  enum,
  relation,
  field,
}

export type NomlEntity = {
  name: string;
  type: NomlEntityTypes;
  renderType: string;
  description?: string;
  children?: NomlEntity[];
};

export function entityToNoml(entities: models.Entity[]): string {
  let nomlEntities: NomlEntity[] = [];

  const allEntityNames = entities.map((it) => it.name);
  let unProcessedEntities: string[] = [...allEntityNames];

  if (unProcessedEntities.length > 0) {
    const [processed] = processEntities(
        entities,
        unProcessedEntities,
        unProcessedEntities
    );
    // parse remaining entities
    nomlEntities = [...nomlEntities, ...processed];
  }
  // convert to noml format
  const nomlText = nomlEntities.map(mapToNoml);
  return `${nomlText.join("\n")}\n`;
}

function processEntities(
    entities: models.Entity[],
    unProcessed: string[],
    filter: string[] = []
): [NomlEntity[], string[]] {
  const out: NomlEntity[] = [];
  entities.forEach((entity) => {
    if (filter.length > 0 && !filter.includes(entity.name)) {
      return;
    }
    out.push({
      name: entity.name,
      type: NomlEntityTypes.entity,
      renderType: "",
      children: entity.fields?.map((field) => {
        return {
          name: `${field.dataType} | ${field.name} ${
              field.required
                  ? "*"
                  : ""
          }`,
          type: NomlEntityTypes.field,
          renderType: "",
        };
      }),
    });
    unProcessed = unProcessed.filter((it) => it !== entity.name);
  });

  const processedRelations: string[] = [];

  entities.forEach((rel) => {
    rel.fields?.filter((field) => field.dataType === models.EnumDataType.Lookup).forEach((field) => {
      const relEntityId = field.properties.relatedEntityId;
      const relationType = field.properties.relationType as models.EnumRelationType;
      const relRelationType = getRelRelationType(relationType);
      const relEntity = findActualEntities(relEntityId, entities);
      //检查关联对象有无添加过relation
      if (relRelationType != null) {
        if (!processedRelations.includes(`${relEntityId}_${rel.id}_${relRelationType}`)) {
          out.push({
            name: `[${rel.name}] ${getCardinality(relationType)} [${relEntity.name}]`,
            type: NomlEntityTypes.relation,
            renderType: "",
          });
          processedRelations.push(`${rel.id}_${relEntity.id}_${relationType}`);
        }
      } else {
        out.push({
          name: `[${rel.name}] ${getCardinality(relationType)} [${relEntity.name}]`,
          type: NomlEntityTypes.relation,
          renderType: "",
        });
      }

    })
  });

  return [out, unProcessed];
}

function findActualEntities(
    id: string,
    entities: models.Entity[]
): models.Entity {
  return entities.filter((entity) => entity.id === id)[0];
}


function mapToNoml(it: NomlEntity): string {
  switch (it.type) {
    case NomlEntityTypes.field:
    case NomlEntityTypes.relation:
      return `${it.name}\n`;
    default:
      return `[<table>${it.name} | ${it.children?.map(mapToNoml).join(" || ")}\n]`;
  }
}


function getRelRelationType(cardinality: models.EnumRelationType) {
  switch (cardinality) {
    case "OneToMany":
      return models.EnumRelationType.ManyToOne;
    case "ManyToOne":
      return models.EnumRelationType.OneToMany;
    case "OneWay":
      return null;
    case "ManyToMany":
      return models.EnumRelationType.ManyToMany;
    default:
      return null;
  }
}

function getCardinality(cardinality: models.EnumRelationType) {
  switch (cardinality) {
    case "OneToMany":
      return "o- (1..*)";
    case "ManyToOne":
    case "OneWay":
      return "(1..*) -o";
    case "ManyToMany":
      return "(*..*) o-o";
    default:
      return "(1..*) ->";
  }
}



export const downloadImage = (evt) => {
  const canvasElement = document.getElementById("canvas") as HTMLCanvasElement;
  const url = canvasElement.toDataURL("image/png");
  evt.currentTarget.href = url;
};

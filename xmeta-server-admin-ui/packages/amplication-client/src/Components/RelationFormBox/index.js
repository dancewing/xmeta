import React from 'react';
import PropTypes from 'prop-types';
import { Inputs } from '@buffetjs/custom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useGlobalContext } from 'xmeta-helper-plugin';
import getTrad from '../../utils/getTrad';
import RelationTargetPicker from '../RelationTargetPicker';
import Wrapper from './Wrapper';

const RelationFormBox = ({
  disabled,
  error,
  header,
  isMain,
  name,
  onChange,
  oneThatIsCreatingARelationWithAnother,
  target,
  value,
  label,
  labelValue,
}) => {
  const { formatMessage } = useGlobalContext();

  return (
    <Wrapper>
      <div className="box-header">
        {isMain ? (
          <p>
            <FontAwesomeIcon
              icon={['far', 'caret-square-right']}
              style={{ fontSize: 12, marginTop: '-3px' }}
            />
            {header}
          </p>
        ) : (
          <RelationTargetPicker
            target={target}
            onChange={onChange}
            oneThatIsCreatingARelationWithAnother={oneThatIsCreatingARelationWithAnother}
          />
        )}
      </div>
      <div className="box-body">
        <Inputs
          autoFocus={isMain}
          disabled={disabled}
          label={formatMessage({
            id: getTrad('form.attribute.item.defineRelation.fieldName'),
          })}
          error={error}
          type="text"
          onChange={onChange}
          name={name}
          value={value}
        />
        <Inputs
          disabled={disabled}
          label={formatMessage({
            id: getTrad('form.attribute.item.defineRelation.displayName'),
          })}
          error={error}
          type="text"
          onChange={onChange}
          name={label}
          value={labelValue}
        />
      </div>
    </Wrapper>
  );
};

RelationFormBox.defaultProps = {
  disabled: false,
  error: null,
  header: null,
  isMain: false,
  onChange: () => {},
  oneThatIsCreatingARelationWithAnother: null,
  target: null,
  value: '',
  labelValue: '',
};

RelationFormBox.propTypes = {
  disabled: PropTypes.bool,
  error: PropTypes.string,
  header: PropTypes.string,
  isMain: PropTypes.bool,
  name: PropTypes.string.isRequired,
  onChange: PropTypes.func,
  oneThatIsCreatingARelationWithAnother: PropTypes.string,
  target: PropTypes.string,
  value: PropTypes.string,
  label: PropTypes.string.isRequired,
  labelValue: PropTypes.string,
};

export default RelationFormBox;

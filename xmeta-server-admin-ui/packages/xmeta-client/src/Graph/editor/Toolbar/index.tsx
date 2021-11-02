import React from 'react';
import './index.scss';
import {Button, EnumButtonStyle} from "../../../Components/Button";

interface Props {
  className?: string;
  onZoomIn: () => void;
  onZoomOut: () => void;
  onFitContent: () => void;
  onRealContent: () => void;
}

const CLASS_NAME = "handler"

const GraphToolbar: React.FC<Props> = (props) => {
  //const { onZoomIn, onZoomOut, onFitContent, onRealContent } = props;
  const { onZoomIn, onZoomOut } = props;

  return (
    <ul className={CLASS_NAME}>
        <Button
            className={`${CLASS_NAME}__toolbar__button`}
            buttonStyle={EnumButtonStyle.Clear}
            type="button"
            onClick={onZoomIn}
            icon="zoom_in"
        />
        <Button
            className={`${CLASS_NAME}__toolbar__button`}
            buttonStyle={EnumButtonStyle.Clear}
            type="button"
            onClick={onZoomOut}
            icon="zoom_out"
        />
        <Button
            className={`${CLASS_NAME}__toolbar__button`}
            buttonStyle={EnumButtonStyle.Clear}
            type="button"
            onClick={onZoomOut}
            icon="list"
        />

      {/*<Popover
        overlayClassName={styles.popover}
        content="放大"
        placement="left"
      >
        <li onClick={onZoomIn} className={styles.item}>
          <ZoomInOutlined />
        </li>
      </Popover>

      <Popover
        overlayClassName={styles.popover}
        content="缩小"
        placement="left"
      >
        <li onClick={onZoomOut} className={styles.item}>
          <ZoomOutOutlined />
        </li>
      </Popover>
      <Popover
        overlayClassName={styles.popover}
        content="实际尺寸"
        placement="left"
      >
        <li onClick={onRealContent} className={styles.item}>
          <OneToOneOutlined />
        </li>
      </Popover>
      <Popover
        overlayClassName={styles.popover}
        content="适应画布"
        placement="left"
      >
        <li onClick={onFitContent} className={styles.item}>
          <CompressOutlined />
        </li>
      </Popover>*/}

    </ul>
  );
};

export default GraphToolbar;

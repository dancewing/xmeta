import React from 'react';
import './index.scss';
import {Button, EnumButtonStyle} from "../../Components/Button";
import LineIcon from "react-lineicons";

interface Props {
    className?: string;
    ranker: string;
    direction: string;
    onZoomIn?: () => void;
    onZoomOut?: () => void;
    onFitContent?: () => void;
    onRealContent?: () => void;
    toggleRanker?: () => void;
    toggleDirection?: () => void;
}

const CLASS_NAME = "handler"

const GraphToolbar: React.FC<Props> = (props) => {
    //const { onZoomIn, onZoomOut, onFitContent, onRealContent } = props;
    const {onZoomIn, onZoomOut, onFitContent, toggleRanker, toggleDirection} = props;

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
                onClick={onFitContent}
                icon="maximize"
            />

            <span className="seperator">|</span>

            <Button
                onClick={toggleRanker}
                title={`Cycle graph ranker strategy [current: ${props.ranker}]`}
                className={`${CLASS_NAME}__toolbar__button`}
                buttonStyle={EnumButtonStyle.Clear}
            >
                {props.ranker === "longest-path" ? (
                    <LineIcon name="bricks" />
                ) : (
                    <LineIcon name="grid-alt" />
                )}
            </Button>
            <Button
                onClick={toggleDirection}
                title={`Cycle graph direction [current: ${props.direction}]`}
                className={`${CLASS_NAME}__toolbar__button`}
                buttonStyle={EnumButtonStyle.Clear}
            >
                {props.direction === "down" ? (
                    <LineIcon name="arrow-right" />
                ) : (
                    <LineIcon name="arrow-down" />
                )}
            </Button>

        </ul>
    );
};

export default GraphToolbar;

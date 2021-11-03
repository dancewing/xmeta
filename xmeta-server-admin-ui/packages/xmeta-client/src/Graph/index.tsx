import React from "react";
import throttle from "lodash.throttle";
import * as models from '../models';
import GraphToolbar from './Toolbar/index';
// nomnoml dependencies
import {draw} from "nomnoml";

import {CanvasPanner} from "./CanvasPanner";
import './index.scss';
import {entityToNoml} from "./utils";
import {DEFAULT_DIRECTIVE, Directive} from "./constants";

// this cannot have any space before the directives
// const NOMNOML_STYLE_DARK = `
// #stroke: #22273c
// #fill: #e8eaf0;#7950ed;
// \n`;
// const NOMNOML_STYLE = `
// #arrowSize: 0.5
// #lineWidth: 2
// #spacing: 40
// #title: jhipster-jdl
// #zoom: 1.0
// \n`;

type Props = {
    theme: string;
    entities: models.Entity[];
}
type State = {
    hasError: boolean,
    errorTooltip: string,
    isCanvasMode: boolean;
} & Directive;

export class Studio extends React.PureComponent<Props, State> {
    private panner:any;
    private canvasRef = React.createRef<HTMLCanvasElement>();
    private canvasPannerRef = React.createRef<HTMLDivElement>();

    constructor(props: Props) {
        super(props);
        this.state = {
            ...DEFAULT_DIRECTIVE,
            isCanvasMode: true,
            hasError: false,
            errorTooltip: ""};
    }

    componentDidMount() {
       // window.addEventListener("hashchange", this.props.reloadStorage);
        window.addEventListener(
            "resize",
            throttle(() => this.renderJDL(), 750, { leading: true })
        );
       // window.addEventListener("keydown", saveAs);
        if (this.canvasPannerRef.current) {
            this.panner = new CanvasPanner(
                this.canvasPannerRef.current,
                () => this.renderJDL(),
                throttle
            );
        }
        this.updateCode();
    }

    componentDidUpdate(prevProps: Props) {
        // if (
        //     prevProps.isLightMode !== this.props.isLightMode ||
        //     prevProps.direction !== this.props.direction ||
        //     prevProps.ranker !== this.props.ranker
        // ) {
        //     this.renderJDL();
        // }
    }

    componentWillUnmount() {
        //window.removeEventListener("hashchange", this.props.reloadStorage);
      //  window.removeEventListener("keydown", saveAs);
    }

    getDefaultDirectives = ({ isLightMode= false, ranker="", direction="" }) => {
        // let style = isLightMode
        //     ? NOMNOML_STYLE
        //     : NOMNOML_STYLE_DARK + NOMNOML_STYLE;
        // style += `#ranker: ${ranker}\n#direction: ${direction}\n`;
        let style = '';
        Object.keys(this.state).forEach((key)=>{
            style += `#${key}: ${this.state[key]}\n`;
        })
        console.log(style);

        return style;
    };

    renderJDL = () => {
        try {
            const canvas = this.canvasRef.current;
            const nomlVal = entityToNoml(this.props.entities);
            console.log(nomlVal);
            const finalVal = this.getDefaultDirectives(this.state) + nomlVal;
            draw(canvas, finalVal, this.panner.zoom());
            this.panner.positionCanvas(canvas);
        } catch (e) {
            console.log(e);
            this.handleError(e);
        }
    };

    updateCode = () => {
        this.renderJDL();
    };

    handleError = (e) => {
        let msg = "";
        if (e.message) {
            msg = e.message;
        } else {
            msg = "An error occurred, look at the console";
        }
        this.setState({
            hasError: true,
            errorTooltip: msg,
        })
    };

    getClassNames = (isCanvasMode:boolean) => {
        const isLightMode = this.props.theme === 'light';
        return `${isLightMode ? "light-theme" : "dark-theme"} ${
            isCanvasMode ? "canvas-mode" : ""
        }`
    }

    zoomIn = () => {
        this.panner.magnify(2);
    };

    zoomOut = () => {
        this.panner.magnify(-2);
    };

    reset = () => {
        this.panner.reset();
    };

    toggleDirection = () => {
        if (this.state.direction === 'down') {
            this.setState({direction: 'right'})
        } else {
            this.setState({direction: 'down'})
        }
        this.renderJDL();
    }

    toggleRanker = () => {
        if (this.state.ranker === 'tight-tree') {
            this.setState({ranker: 'longest-path'})
        } else {
            this.setState({ranker: 'tight-tree'})
        }
        this.renderJDL();
    }

    render() {
        const { isCanvasMode } = this.state;
        return (
            <div
                className={`er-editor-demo-container ${this.getClassNames(isCanvasMode)}`}
            >
                {/* <!-- canvas holding the UML diagram --> */}
                <canvas className="canvas" id="canvas" ref={this.canvasRef} />
                {/* <!-- shows a tooltip on error --> */}
                <span className="error-tooltip">{this.state.errorTooltip}</span>
                {/* <!-- canvas tools and pan/zoom handler --> */}
                {/*<CanvasTools panner={this.panner} classToggler={this.classToggler} />*/}
                <div
                    className="canvas-panner"
                    ref={this.canvasPannerRef}
                >
                    <GraphToolbar
                        ranker={this.state.ranker}
                        direction={this.state.direction}
                        toggleDirection={this.toggleDirection}
                        toggleRanker={this.toggleRanker}
                        onZoomIn={this.zoomIn}
                        onZoomOut={this.zoomOut}
                        onFitContent={this.reset} />
                </div>

            </div>
        );
    }
}

export default Studio;

export interface Vec {
  x: number;
  y: number;
}

export type Throttler = (
  func: (arg: any) => void,
  timespan: number,
  opts?: any
) => (arg: any) => void;

export class CanvasPanner {
  offset: Vec = { x: 0, y: 0 };

  zoomLevel: number = 0;

  constructor(
    element: HTMLElement,
    private onChange: () => void,
    throttle: Throttler
  ) {

    const magnify = (e: any) => {
      this.zoomLevel = Math.min(10, this.zoomLevel - (e.deltaY < 0 ? -1 : 1));
      onChange();
    };

    element.addEventListener("wheel", throttle(magnify, 50), { passive: true });
  }

  positionCanvas(element: HTMLCanvasElement) {
    const viewport = window;
    const w = element.width / this.superSampling;
    const h = element.height / this.superSampling;
    element.style.top = 300 * (1 - h / viewport.innerHeight) + this.offset.y + "px";
    //element.style.left = 150 + (viewport.innerWidth - w) / 2 + this.offset.x + "px";
    element.style.left = 150  + this.offset.x + "px";
    element.style.width = w + "px";
    element.style.height = h + "px";
  }

  superSampling = window.devicePixelRatio || 1;

  zoom(): number {
    return this.superSampling * Math.exp(this.zoomLevel / 10);
  }

  magnify(diff: number) {
    this.zoomLevel = Math.min(10, this.zoomLevel + diff);
    this.onChange();
  }

  reset() {
    this.zoomLevel = 1;
    this.offset = { x: 0, y: 0 };
    this.onChange();
  }
}

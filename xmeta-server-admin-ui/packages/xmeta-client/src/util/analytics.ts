import * as reactTracking from "react-tracking";

export interface Event {
  eventName: string;
  [key: string]: unknown;
}

export const track: reactTracking.Track<Event> = reactTracking.track;

export const useTracking: () => Omit<
  reactTracking.TrackingProp<Event>,
  "trackEvent"
> & {
  trackEvent: (event: Event) => void;
} = reactTracking.useTracking;

export function dispatch(event: Partial<Event>) {
}

export function init() {
}

type EventProps = {
  [key: string]: unknown;
};

export function identity(userId: string, props: EventProps) {
}

export function page(name?: string, props?: EventProps) {
}

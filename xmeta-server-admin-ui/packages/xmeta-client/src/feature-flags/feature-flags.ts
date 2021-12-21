import { getFeatureFlags } from "./get-feature-flags";

const featureFlags = getFeatureFlags();

export const SHOW_UI_ELEMENTS = Boolean(featureFlags.SHOW_UI_ELEMENTS);
export const SHOW_ROLE_PERMISSIONS = Boolean(featureFlags.SHOW_ROLE_PERMISSIONS);
export const SHOW_ENV_UI = Boolean(featureFlags.SHOW_ENV_UI);
export const SHOW_DEPLOYER = Boolean(featureFlags.SHOW_DEPLOYER);

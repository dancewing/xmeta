import * as Axios from 'axios'

export interface Settings {
  baseUrl: string;
}
export type CustomSettings = Partial<Settings>

type GenericValue = string | object | number | boolean | undefined | null;

export interface IDataObject {
  [key: string]: GenericValue | IDataObject | GenericValue[] | IDataObject[];
}

export const ACTIONS = {
  REQUEST_START: 'REQUEST_START',
  REQUEST_END: 'REQUEST_END',
}

export const InitState = {
  loading: false,
}

export interface InitState {
  loading: boolean,
}

export type ApiResponse = Axios.AxiosResponse<JsonObject>
export type SingleData = JsonObject | undefined | any
export type Data = SingleData | SingleData[] | undefined | any
export type RequestFn = (
    data: Data
) => Promise<any>

export interface Context {
  baseUrl: string;
  settings?: Settings
  isSSR?: boolean
  collection?: SSRCollection
  isConfigured?: boolean
  clearCache?: () => void
}
export interface CustomContext extends Omit<Context, 'settings'> {
  baseUrl: string;
  settings?: CustomSettings
}
export interface SSRCollection {
  ssrConfigs: SSRConfigs[]
  cacheKeys: Set<string>
}
export interface SSRConfigs {
  cacheKey: string
}
export interface ApiProviderProps {
  context: Context
}
export interface Options {
  headers?: IDataObject
  watch?: any[]
  dependencies?: dependencies
  skip?: boolean
  useCache?: boolean
  $cacheKey?: string
  $hasChangedConfig?: boolean
  handleData?: (data: Data, newState: State) => any
  shouldRequest?: () => boolean | void
}
export interface dependencies {
  readonly [key: string]: any
}

export interface CacheData {
  response?: ApiResponse | ApiResponse[]
  error?: Axios.AxiosError
}
export interface Action extends CacheData {
  type: string
}
export interface State extends InitState, CacheData, JsonObject {
  data?: Data
  prevData?: Data
  prevState?: State
  dependencies?: Options['dependencies']
}

export interface RefData {
  id: number
  cacheKey: string
  state: State
  refreshFlag: number
  isInit: boolean
  isRequesting: boolean
  hasFed: boolean
  timeoutID: number
  useCache: boolean
}

export interface DispatchClusterElement {
  id: number
  dispatch: React.Dispatch<Action>
  refData: RefData
}


export interface JsonObject {
  [key: string]: any
}

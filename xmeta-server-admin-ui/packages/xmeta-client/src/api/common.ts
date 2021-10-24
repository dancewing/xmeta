import * as ReactUseApi from './interface';

export const ACTIONS = {
  REQUEST_START: 'REQUEST_START',
  REQUEST_END: 'REQUEST_END',
}
export const initState = {
  loading: false,
  fromCache: false,
}

// for cache
export const tidyResponse = (response: ReactUseApi.ApiResponse) => {
  if (response) {
    delete response.config
    delete response.request
  }
  return response
}

export const getResponseData = (
  state: ReactUseApi.State
) => {
  const { response } = state
  const isMultiApis = Array.isArray(response)
  let data = isMultiApis
    ? (response as ReactUseApi.ApiResponse[]).map((each) => each.data)
    : (response as ReactUseApi.ApiResponse).data
  return data
}

export const isObject = (target: any) =>
  !!target && Object.prototype.toString.call(target) === '[object Object]'

export const isFunction = (target: any) =>
  !!target && typeof target === 'function'

export const isNil = (value: any) =>
  value === undefined || value === null || value === ''

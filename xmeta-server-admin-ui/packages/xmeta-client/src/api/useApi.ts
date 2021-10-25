import React, {useCallback, useContext, useReducer, useMemo, useRef, useEffect} from 'react';
import {Method} from 'axios';
import * as ReactUseApi from './interface';
import {makeRestApiRequest} from './helpers';
import {ApiContext} from './context';
import {ACTIONS, getResponseData, initState, isObject, isFunction} from './common';

export function useApi<Request = ReactUseApi.Data, Response = ReactUseApi.Data>(
    url: string,
    method: Method,
    opt?: ReactUseApi.Options,
): [Response, ReactUseApi.State, ReactUseApi.RequestFn] {

    const context = useContext(ApiContext);

    const ref = useRef(
        useMemo(
            () =>
                ({
                    id: Date.now(),
                    isRequesting: false,
                    isInit: false,
                    hasFed: false,
                    refreshFlag: 1,
                } as ReactUseApi.RefData),
            []
        )
    )

    const options = useMemo(
        () => handleUseApiOptions(opt),
        [opt]
    )

    const { current: refData } = ref

    const {headers, shouldRequest} = options;

    let defaultState = {...initState};

    refData.isInit = true

    const [state, dispatch] = useReducer(reducer, defaultState)
    const {loading, data} = state

    const request = useCallback(
        async (data: Request) => {
            refData.isRequesting = true
            return fetchApi(url, method, context, dispatch, data, headers);
        },
        [context, dispatch, refData, headers, method, url]
    )

    const shouldFetchApi = useCallback(
        (forRerender = false) => {
            let shouldRequestResult: boolean = false;
            if (isFunction(shouldRequest)) {
                // @ts-ignore
                shouldRequestResult = !!shouldRequest() as boolean
            }
            return (
                !refData.isRequesting &&
                ((forRerender
                        ? shouldRequestResult === true
                        : // false means skip
                        shouldRequestResult !== false))
            )
        },
        [refData, shouldRequest]
    )

    useEffect(()=>{
        if (shouldFetchApi(true)) {
            refData.refreshFlag = Date.now()
            request(undefined);
        }
    }, [refData])

    if (!loading && refData.isRequesting) {
        refData.isRequesting = false
    }

    return [data, state, request]
}

export const reducer = (
    state: ReactUseApi.State,
    action: ReactUseApi.Action
): ReactUseApi.State => {
    const {type} = action
    switch (type) {
        case ACTIONS.REQUEST_START: {
            return {
                loading: true,
                error: undefined,
            }
        }
        case ACTIONS.REQUEST_END: {
            const {response, error} = action
            const {...prevState} = state
            const {data: prevData} = prevState
            const newState = {
                ...prevState,
                prevData,
                prevState,
                loading: false,
                response,
                error,
            }
            newState.data = error ? undefined : getResponseData(newState)
            return newState
        }
        default:
            return state
    }
}

export const fetchApi = async (
    url: string,
    method: Method,
    context: ReactUseApi.Context,
    dispatch: React.Dispatch<ReactUseApi.Action>,
    data?: ReactUseApi.Data,
    headers?: ReactUseApi.IDataObject,
) => {
    let {response, error} = {} as ReactUseApi.CacheData
    try {
        dispatch({type: ACTIONS.REQUEST_START})
        response = await makeRestApiRequest(context, method, url, data, headers)
        dispatch({type: ACTIONS.REQUEST_END, response, error})
    } catch (err) {
        error = err
        dispatch({
            type: ACTIONS.REQUEST_END,
            error,
        })
    }
}

export const handleUseApiOptions = (
    opt?: ReactUseApi.Options
) => {
    return isObject(opt)
        ? ({...opt} as ReactUseApi.Options)
        : ({} as ReactUseApi.Options)
}

export default useApi

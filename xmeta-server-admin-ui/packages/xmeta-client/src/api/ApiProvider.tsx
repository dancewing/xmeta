import React from 'react'

import { ApiContext } from './context'
import * as ReactUseApi from './interface';

export const ApiProvider: React.FC<ReactUseApi.ApiProviderProps> = ({
  context ,
  children,
}) => {
  return (
    <ApiContext.Provider value={context}>{children}</ApiContext.Provider>
  )
}

ApiProvider.displayName = 'ApiProvider'

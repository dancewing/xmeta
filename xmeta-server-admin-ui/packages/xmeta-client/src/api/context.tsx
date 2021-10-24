import { createContext } from 'react'
import * as ReactUseApi from './interface';

export const ApiContext = createContext<ReactUseApi.Context>({baseUrl: "/"})

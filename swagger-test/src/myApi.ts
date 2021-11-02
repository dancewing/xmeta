/* eslint-disable */
/* tslint:disable */
/*
 * ---------------------------------------------------------------
 * ## THIS FILE WAS GENERATED VIA SWAGGER-TYPESCRIPT-API        ##
 * ##                                                           ##
 * ## AUTHOR: acacode                                           ##
 * ## SOURCE: https://github.com/acacode/swagger-typescript-api ##
 * ---------------------------------------------------------------
 */

export interface Order {
  id?: string;

  /** @format date-time */
  createdAt?: string;

  /** @format date-time */
  updatedAt?: string;

  /** @format int32 */
  quantity?: number;
  discount?: number;

  /** @format int32 */
  totalPrice?: number;
  customer?: Customer;
  product?: Product;
}

export interface BooleanFilter {
  eq?: boolean;
  not?: boolean;
}

export interface Shop {
  id?: string;

  /** @format date-time */
  createdAt?: string;

  /** @format date-time */
  updatedAt?: string;
  name?: string;
}

export interface IntFilter {
  /** @format int32 */
  eq?: number;

  /** @format int32 */
  not?: number;
  in?: number[];
  notIn?: number[];

  /** @format int32 */
  lt?: number;

  /** @format int32 */
  lte?: number;

  /** @format int32 */
  gt?: number;

  /** @format int32 */
  gte?: number;
}

export interface User {
  id?: string;

  /** @format date-time */
  createdAt?: string;

  /** @format date-time */
  updatedAt?: string;
  firstName?: string;
  lastName?: string;
  username: string;
  password: string;
  roles: string;
}

export interface Pageable {
  sort?: Sort;

  /** @format int64 */
  offset?: number;

  /** @format int32 */
  pageNumber?: number;

  /** @format int32 */
  pageSize?: number;
  paged?: boolean;
  unpaged?: boolean;
}

export interface Address {
  id?: string;

  /** @format date-time */
  createdAt?: string;

  /** @format date-time */
  updatedAt?: string;
  address1?: string;
  address2?: string;
  city?: string;
  state?: string;

  /** @format int32 */
  zip?: number;
  customer?: Customer;
}

export interface Customer {
  id?: string;

  /** @format date-time */
  createdAt?: string;

  /** @format date-time */
  updatedAt?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  orders?: Order[];
  address?: Address[];
  shop?: Shop[];
}

export interface DateTimeFilter {
  /** @format date-time */
  eq?: string;

  /** @format date-time */
  not?: string;
  in?: string[];
  notIn?: string[];

  /** @format date-time */
  lt?: string;

  /** @format date-time */
  lte?: string;

  /** @format date-time */
  gt?: string;

  /** @format date-time */
  gte?: string;
}

export interface Product {
  id?: string;

  /** @format date-time */
  createdAt?: string;

  /** @format date-time */
  updatedAt?: string;
  name?: string;
  itemPrice?: number;
  description?: string;

  /** @format date-time */
  publishDate?: string;
  optionSet?: string;
  multiOptionSet?: string;
}

export interface StringFilter {
  eq?: string;
  not?: string;
  in?: string[];
  notIn?: string[];
  lt?: string;
  lte?: string;
  gt?: string;
  gte?: string;
  contains?: string;
  startsWith?: string;
  endsWith?: string;
  mode?: "Default" | "Insensitive";
}

export interface Sort {
  sorted?: boolean;
  unsorted?: boolean;
  empty?: boolean;
}

export type QueryParamsType = Record<string | number, any>;
export type ResponseFormat = keyof Omit<Body, "body" | "bodyUsed">;

export interface FullRequestParams extends Omit<RequestInit, "body"> {
  /** set parameter to `true` for call `securityWorker` for this request */
  secure?: boolean;
  /** request path */
  path: string;
  /** content type of request body */
  type?: ContentType;
  /** query params */
  query?: QueryParamsType;
  /** format of response (i.e. response.json() -> format: "json") */
  format?: ResponseFormat;
  /** request body */
  body?: unknown;
  /** base url */
  baseUrl?: string;
  /** request cancellation token */
  cancelToken?: CancelToken;
}

export type RequestParams = Omit<FullRequestParams, "body" | "method" | "query" | "path">;

export interface ApiConfig<SecurityDataType = unknown> {
  baseUrl?: string;
  baseApiParams?: Omit<RequestParams, "baseUrl" | "cancelToken" | "signal">;
  securityWorker?: (securityData: SecurityDataType | null) => Promise<RequestParams | void> | RequestParams | void;
  customFetch?: typeof fetch;
}

export interface HttpResponse<D extends unknown, E extends unknown = unknown> extends Response {
  data: D;
  error: E;
}

type CancelToken = Symbol | string | number;

export enum ContentType {
  Json = "application/json",
  FormData = "multipart/form-data",
  UrlEncoded = "application/x-www-form-urlencoded",
}

export class HttpClient<SecurityDataType = unknown> {
  public baseUrl: string = "";
  private securityData: SecurityDataType | null = null;
  private securityWorker?: ApiConfig<SecurityDataType>["securityWorker"];
  private abortControllers = new Map<CancelToken, AbortController>();
  private customFetch = (...fetchParams: Parameters<typeof fetch>) => fetch(...fetchParams);

  private baseApiParams: RequestParams = {
    credentials: "same-origin",
    headers: {},
    redirect: "follow",
    referrerPolicy: "no-referrer",
  };

  constructor(apiConfig: ApiConfig<SecurityDataType> = {}) {
    Object.assign(this, apiConfig);
  }

  public setSecurityData = (data: SecurityDataType | null) => {
    this.securityData = data;
  };

  private encodeQueryParam(key: string, value: any) {
    const encodedKey = encodeURIComponent(key);
    return `${encodedKey}=${encodeURIComponent(typeof value === "number" ? value : `${value}`)}`;
  }

  private addQueryParam(query: QueryParamsType, key: string) {
    return this.encodeQueryParam(key, query[key]);
  }

  private addArrayQueryParam(query: QueryParamsType, key: string) {
    const value = query[key];
    return value.map((v: any) => this.encodeQueryParam(key, v)).join("&");
  }

  protected toQueryString(rawQuery?: QueryParamsType): string {
    const query = rawQuery || {};
    const keys = Object.keys(query).filter((key) => "undefined" !== typeof query[key]);
    return keys
      .map((key) => (Array.isArray(query[key]) ? this.addArrayQueryParam(query, key) : this.addQueryParam(query, key)))
      .join("&");
  }

  protected addQueryParams(rawQuery?: QueryParamsType): string {
    const queryString = this.toQueryString(rawQuery);
    return queryString ? `?${queryString}` : "";
  }

  private contentFormatters: Record<ContentType, (input: any) => any> = {
    [ContentType.Json]: (input: any) =>
      input !== null && (typeof input === "object" || typeof input === "string") ? JSON.stringify(input) : input,
    [ContentType.FormData]: (input: any) =>
      Object.keys(input || {}).reduce((formData, key) => {
        const property = input[key];
        formData.append(
          key,
          property instanceof Blob
            ? property
            : typeof property === "object" && property !== null
            ? JSON.stringify(property)
            : `${property}`,
        );
        return formData;
      }, new FormData()),
    [ContentType.UrlEncoded]: (input: any) => this.toQueryString(input),
  };

  private mergeRequestParams(params1: RequestParams, params2?: RequestParams): RequestParams {
    return {
      ...this.baseApiParams,
      ...params1,
      ...(params2 || {}),
      headers: {
        ...(this.baseApiParams.headers || {}),
        ...(params1.headers || {}),
        ...((params2 && params2.headers) || {}),
      },
    };
  }

  private createAbortSignal = (cancelToken: CancelToken): AbortSignal | undefined => {
    if (this.abortControllers.has(cancelToken)) {
      const abortController = this.abortControllers.get(cancelToken);
      if (abortController) {
        return abortController.signal;
      }
      return void 0;
    }

    const abortController = new AbortController();
    this.abortControllers.set(cancelToken, abortController);
    return abortController.signal;
  };

  public abortRequest = (cancelToken: CancelToken) => {
    const abortController = this.abortControllers.get(cancelToken);

    if (abortController) {
      abortController.abort();
      this.abortControllers.delete(cancelToken);
    }
  };

  public request = async <T = any, E = any>({
    body,
    secure,
    path,
    type,
    query,
    format,
    baseUrl,
    cancelToken,
    ...params
  }: FullRequestParams): Promise<HttpResponse<T, E>> => {
    const secureParams =
      ((typeof secure === "boolean" ? secure : this.baseApiParams.secure) &&
        this.securityWorker &&
        (await this.securityWorker(this.securityData))) ||
      {};
    const requestParams = this.mergeRequestParams(params, secureParams);
    const queryString = query && this.toQueryString(query);
    const payloadFormatter = this.contentFormatters[type || ContentType.Json];
    const responseFormat = format || requestParams.format;

    return this.customFetch(`${baseUrl || this.baseUrl || ""}${path}${queryString ? `?${queryString}` : ""}`, {
      ...requestParams,
      headers: {
        ...(type && type !== ContentType.FormData ? { "Content-Type": type } : {}),
        ...(requestParams.headers || {}),
      },
      signal: cancelToken ? this.createAbortSignal(cancelToken) : void 0,
      body: typeof body === "undefined" || body === null ? null : payloadFormatter(body),
    }).then(async (response) => {
      const r = response as HttpResponse<T, E>;
      r.data = null as unknown as T;
      r.error = null as unknown as E;

      const data = !responseFormat
        ? r
        : await response[responseFormat]()
            .then((data) => {
              if (r.ok) {
                r.data = data;
              } else {
                r.error = data;
              }
              return r;
            })
            .catch((e) => {
              r.error = e;
              return r;
            });

      if (cancelToken) {
        this.abortControllers.delete(cancelToken);
      }

      if (!response.ok) throw data;
      return data;
    });
  };
}

/**
 * @title Spring Metadata API
 */
export class Api<SecurityDataType extends unknown> extends HttpClient<SecurityDataType> {
  api = {
    /**
     * No description
     *
     * @tags Product
     * @name GetAllProduct
     * @summary Product apis
     * @request GET:/api/meta/1453666071546732544
     */
    getAllProduct: (params: RequestParams = {}) =>
      this.request<Product, any>({
        path: `/api/meta/1453666071546732544`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Product
     * @name SaveProduct
     * @summary create new record for Product
     * @request POST:/api/meta/1453666071546732544
     */
    saveProduct: (data: Product, params: RequestParams = {}) =>
      this.request<Product, any>({
        path: `/api/meta/1453666071546732544`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Product
     * @name GetOneProduct
     * @summary  get one record of Product by ID
     * @request GET:/api/meta/1453666071546732544/{id}
     */
    getOneProduct: (id: any, params: RequestParams = {}) =>
      this.request<Product, any>({
        path: `/api/meta/1453666071546732544/${id}`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Product
     * @name UpdateProduct
     * @summary update one record of Product by ID
     * @request POST:/api/meta/1453666071546732544/{id}
     */
    updateProduct: (id: any, data: Product, params: RequestParams = {}) =>
      this.request<Product, any>({
        path: `/api/meta/1453666071546732544/${id}`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Product
     * @name UpdateProduct2
     * @summary delete one record of Product by ID
     * @request DELETE:/api/meta/1453666071546732544/{id}
     * @originalName updateProduct
     * @duplicate
     */
    updateProduct2: (id: any, params: RequestParams = {}) =>
      this.request<Product, any>({
        path: `/api/meta/1453666071546732544/${id}`,
        method: "DELETE",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Customer
     * @name GetAllCustomer
     * @summary Customer apis
     * @request GET:/api/meta/1453666071093747712
     */
    getAllCustomer: (params: RequestParams = {}) =>
      this.request<Customer, any>({
        path: `/api/meta/1453666071093747712`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Customer
     * @name SaveCustomer
     * @summary create new record for Customer
     * @request POST:/api/meta/1453666071093747712
     */
    saveCustomer: (data: Customer, params: RequestParams = {}) =>
      this.request<Customer, any>({
        path: `/api/meta/1453666071093747712`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Customer
     * @name GetOneCustomer
     * @summary  get one record of Customer by ID
     * @request GET:/api/meta/1453666071093747712/{id}
     */
    getOneCustomer: (id: any, params: RequestParams = {}) =>
      this.request<Customer, any>({
        path: `/api/meta/1453666071093747712/${id}`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Customer
     * @name UpdateCustomer
     * @summary update one record of Customer by ID
     * @request POST:/api/meta/1453666071093747712/{id}
     */
    updateCustomer: (id: any, data: Customer, params: RequestParams = {}) =>
      this.request<Customer, any>({
        path: `/api/meta/1453666071093747712/${id}`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Customer
     * @name UpdateCustomer2
     * @summary delete one record of Customer by ID
     * @request DELETE:/api/meta/1453666071093747712/{id}
     * @originalName updateCustomer
     * @duplicate
     */
    updateCustomer2: (id: any, params: RequestParams = {}) =>
      this.request<Customer, any>({
        path: `/api/meta/1453666071093747712/${id}`,
        method: "DELETE",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Address
     * @name GetAllAddress
     * @summary Address apis
     * @request GET:/api/meta/1453666071303462912
     */
    getAllAddress: (params: RequestParams = {}) =>
      this.request<Address, any>({
        path: `/api/meta/1453666071303462912`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Address
     * @name SaveAddress
     * @summary create new record for Address
     * @request POST:/api/meta/1453666071303462912
     */
    saveAddress: (data: Address, params: RequestParams = {}) =>
      this.request<Address, any>({
        path: `/api/meta/1453666071303462912`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Address
     * @name GetOneAddress
     * @summary  get one record of Address by ID
     * @request GET:/api/meta/1453666071303462912/{id}
     */
    getOneAddress: (id: any, params: RequestParams = {}) =>
      this.request<Address, any>({
        path: `/api/meta/1453666071303462912/${id}`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Address
     * @name UpdateAddress
     * @summary update one record of Address by ID
     * @request POST:/api/meta/1453666071303462912/{id}
     */
    updateAddress: (id: any, data: Address, params: RequestParams = {}) =>
      this.request<Address, any>({
        path: `/api/meta/1453666071303462912/${id}`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Address
     * @name UpdateAddress2
     * @summary delete one record of Address by ID
     * @request DELETE:/api/meta/1453666071303462912/{id}
     * @originalName updateAddress
     * @duplicate
     */
    updateAddress2: (id: any, params: RequestParams = {}) =>
      this.request<Address, any>({
        path: `/api/meta/1453666071303462912/${id}`,
        method: "DELETE",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Order
     * @name GetAllOrder
     * @summary Order apis
     * @request GET:/api/meta/1453666070770786304
     */
    getAllOrder: (params: RequestParams = {}) =>
      this.request<Order, any>({
        path: `/api/meta/1453666070770786304`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Order
     * @name SaveOrder
     * @summary create new record for Order
     * @request POST:/api/meta/1453666070770786304
     */
    saveOrder: (data: Order, params: RequestParams = {}) =>
      this.request<Order, any>({
        path: `/api/meta/1453666070770786304`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Order
     * @name GetOneOrder
     * @summary  get one record of Order by ID
     * @request GET:/api/meta/1453666070770786304/{id}
     */
    getOneOrder: (id: any, params: RequestParams = {}) =>
      this.request<Order, any>({
        path: `/api/meta/1453666070770786304/${id}`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Order
     * @name UpdateOrder
     * @summary update one record of Order by ID
     * @request POST:/api/meta/1453666070770786304/{id}
     */
    updateOrder: (id: any, data: Order, params: RequestParams = {}) =>
      this.request<Order, any>({
        path: `/api/meta/1453666070770786304/${id}`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Order
     * @name UpdateOrder2
     * @summary delete one record of Order by ID
     * @request DELETE:/api/meta/1453666070770786304/{id}
     * @originalName updateOrder
     * @duplicate
     */
    updateOrder2: (id: any, params: RequestParams = {}) =>
      this.request<Order, any>({
        path: `/api/meta/1453666070770786304/${id}`,
        method: "DELETE",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Shop
     * @name GetAllShop
     * @summary Shop apis
     * @request GET:/api/meta/1453666070972112896
     */
    getAllShop: (params: RequestParams = {}) =>
      this.request<Shop, any>({
        path: `/api/meta/1453666070972112896`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Shop
     * @name SaveShop
     * @summary create new record for Shop
     * @request POST:/api/meta/1453666070972112896
     */
    saveShop: (data: Shop, params: RequestParams = {}) =>
      this.request<Shop, any>({
        path: `/api/meta/1453666070972112896`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Shop
     * @name GetOneShop
     * @summary  get one record of Shop by ID
     * @request GET:/api/meta/1453666070972112896/{id}
     */
    getOneShop: (id: any, params: RequestParams = {}) =>
      this.request<Shop, any>({
        path: `/api/meta/1453666070972112896/${id}`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags Shop
     * @name UpdateShop
     * @summary update one record of Shop by ID
     * @request POST:/api/meta/1453666070972112896/{id}
     */
    updateShop: (id: any, data: Shop, params: RequestParams = {}) =>
      this.request<Shop, any>({
        path: `/api/meta/1453666070972112896/${id}`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags Shop
     * @name UpdateShop2
     * @summary delete one record of Shop by ID
     * @request DELETE:/api/meta/1453666070972112896/{id}
     * @originalName updateShop
     * @duplicate
     */
    updateShop2: (id: any, params: RequestParams = {}) =>
      this.request<Shop, any>({
        path: `/api/meta/1453666070972112896/${id}`,
        method: "DELETE",
        ...params,
      }),

    /**
     * No description
     *
     * @tags User
     * @name GetAllUser
     * @summary User apis
     * @request GET:/api/meta/1453666069306974208
     */
    getAllUser: (params: RequestParams = {}) =>
      this.request<User, any>({
        path: `/api/meta/1453666069306974208`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags User
     * @name SaveUser
     * @summary create new record for User
     * @request POST:/api/meta/1453666069306974208
     */
    saveUser: (data: User, params: RequestParams = {}) =>
      this.request<User, any>({
        path: `/api/meta/1453666069306974208`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags User
     * @name GetOneUser
     * @summary  get one record of User by ID
     * @request GET:/api/meta/1453666069306974208/{id}
     */
    getOneUser: (id: any, params: RequestParams = {}) =>
      this.request<User, any>({
        path: `/api/meta/1453666069306974208/${id}`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags User
     * @name UpdateUser
     * @summary update one record of User by ID
     * @request POST:/api/meta/1453666069306974208/{id}
     */
    updateUser: (id: any, data: User, params: RequestParams = {}) =>
      this.request<User, any>({
        path: `/api/meta/1453666069306974208/${id}`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags User
     * @name UpdateUser2
     * @summary delete one record of User by ID
     * @request DELETE:/api/meta/1453666069306974208/{id}
     * @originalName updateUser
     * @duplicate
     */
    updateUser2: (id: any, params: RequestParams = {}) =>
      this.request<User, any>({
        path: `/api/meta/1453666069306974208/${id}`,
        method: "DELETE",
        ...params,
      }),
  };
}

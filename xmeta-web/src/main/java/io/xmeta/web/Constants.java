package io.xmeta.web;

public class Constants {

    public static final String DEFAULT_META_API_URL = "/api/meta";

    public static final String META_API_URL = "${xmeta.api-docs.path:#{T(io.xmeta.web.Constants).DEFAULT_META_API_URL}}";
}

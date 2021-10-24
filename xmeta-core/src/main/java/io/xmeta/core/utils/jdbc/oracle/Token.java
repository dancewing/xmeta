package io.xmeta.core.utils.jdbc.oracle;

public class Token {

    private final String token;
    private final int type;

    public Token(String token, int type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Token");
        sb.append("{token='").append(token).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}

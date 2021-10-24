package io.xmeta.core.utils.jdbc.oracle;

public class Address {

    private final String protocol;

    private final String host;

    private final String port;

    public Address(String protocol, String host, String port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (host != null ? !host.equals(address.host) : address.host != null) return false;
        if (port != null ? !port.equals(address.port) : address.port != null) return false;
        if (protocol != null ? !protocol.equals(address.protocol) : address.protocol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = protocol != null ? protocol.hashCode() : 0;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Address");
        sb.append("{protocol='").append(protocol).append('\'');
        sb.append(", host='").append(host).append('\'');
        sb.append(", port='").append(port).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

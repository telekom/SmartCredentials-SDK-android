package de.telekom.smartcredentials.core.eid

enum class TlsConfiguration {
    /** A secure TLS connection that requires a recent client platform and a recent server. */
    RESTRICTED_TLS,
    /**
     * A modern TLS configuration that works on most client platforms and can connect to most servers.
     * This is OkHttp's default configuration.
     */
    MODERN_TLS,
    /**
     * A backwards-compatible fallback configuration that works on obsolete client platforms and can
     * connect to obsolete servers. When possible, prefer to upgrade your client platform or server
     * rather than using this configuration.
     */
    COMPATIBLE_TLS
}
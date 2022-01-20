package de.telekom.smartcredentials.eid.mapper

import de.telekom.smartcredentials.core.eid.TlsConfiguration
import okhttp3.ConnectionSpec

class TlsConfigurationMapper {

    fun map(configuration: TlsConfiguration): ConnectionSpec {
        return when (configuration) {
            TlsConfiguration.COMPATIBLE_TLS -> ConnectionSpec.COMPATIBLE_TLS
            TlsConfiguration.MODERN_TLS -> ConnectionSpec.MODERN_TLS
            else -> {
                ConnectionSpec.RESTRICTED_TLS
            }
        }
    }

}
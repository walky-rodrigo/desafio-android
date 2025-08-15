package remote_config

import java.util.concurrent.TimeUnit

class RemoteConfig {

    fun getCacheMaxDuration(): Long {
        return TimeUnit.HOURS.toMillis(12)
    }
}
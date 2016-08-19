package com.kotlin_stalkgram.elio.kotlinstalkgram

import org.junit.Before
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows

abstract class BaseTest {
    @Before
    @Throws(Exception::class)
    open fun setUp() {
        MockitoAnnotations.initMocks(this)

        if (RuntimeEnvironment.application != null) {
            val shadowApp = Shadows.shadowOf(RuntimeEnvironment.application)
            shadowApp.grantPermissions("android.permission.INTERNET")
        }
    }
}

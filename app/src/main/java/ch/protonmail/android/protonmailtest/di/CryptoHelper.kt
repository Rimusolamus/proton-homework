package ch.protonmail.android.protonmailtest.di

import ch.protonmail.android.crypto.CryptoLib
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoHelper @Inject constructor(
) {
    val instance: CryptoLib = CryptoLib()
}

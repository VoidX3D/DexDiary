package com.easylife.diary.domain.backup

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object BackupCrypto {
    private const val IV_LENGTH = 12
    private const val TAG_LENGTH = 128

    fun encrypt(raw: ByteArray, password: String): ByteArray {
        val iv = ByteArray(IV_LENGTH).also { SecureRandom().nextBytes(it) }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, deriveKey(password), GCMParameterSpec(TAG_LENGTH, iv))
        val encrypted = cipher.doFinal(raw)
        return iv + encrypted
    }

    fun decrypt(payload: ByteArray, password: String): ByteArray {
        require(payload.size > IV_LENGTH) { "Encrypted backup payload is invalid." }
        val iv = payload.copyOfRange(0, IV_LENGTH)
        val body = payload.copyOfRange(IV_LENGTH, payload.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, deriveKey(password), GCMParameterSpec(TAG_LENGTH, iv))
        return cipher.doFinal(body)
    }

    private fun deriveKey(password: String): SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-256")
        val keyBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return SecretKeySpec(keyBytes, "AES")
    }
}

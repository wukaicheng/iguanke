package cn.kaicity.app.iguangke.util

import androidx.annotation.IntegerRes
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import javax.crypto.Cipher

object EncryptUtil {


    fun rsa(data: String): String {

        val keyFactory = KeyFactory.getInstance("RSA")
        val modulus = BigInteger(
            "00b5eeb166e069920e80bebd1fea4829d3d1f3216f2aabe79b6c47a3c18dcee5fd22c2e7ac519cab59198ece036dcf289ea8201e2a0b9ded307f8fb704136eaeb670286f5ad44e691005ba9ea5af04ada5367cd724b5a26fdb5120cc95b6431604bd219c6b7d83a6f8f24b43918ea988a76f93c333aa5a20991493d4eb1117e7b1",
            16
        )

        val exponent = BigInteger("010001", 16)
        val spec = RSAPublicKeySpec(modulus, exponent)
        val cipher = Cipher.getInstance("RSA/None/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(spec))
        val bt = cipher.doFinal(data.reversed().toByteArray())
        return bytes2Hex(bt)
    }

    private fun bytes2Hex(bt: ByteArray?): String {
        val sb = StringBuilder()
        var strHex = ""
        for (b in bt!!) {
            strHex = Integer.toHexString(b.toInt() and 0xFF)
            if (strHex.length == 1) sb.append(0)
            sb.append(strHex)
        }
        return sb.toString()
    }


}

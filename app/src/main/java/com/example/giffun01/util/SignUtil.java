package com.example.giffun01.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.example.giffun01.global.GifFun;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUtil {

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            sb.append(HEX_DIGITS[(aB & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[aB & 0x0f]);
        }
        return sb.toString();
    }

    public static String getAppSignature() {
        try {
            PackageInfo info = GifFun.getContext().getPackageManager().getPackageInfo(GifFun.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            Signature[] signatures = info.signatures;
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

}

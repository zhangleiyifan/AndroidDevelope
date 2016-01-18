package com.gyz.androiddevelope.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: guoyazhou
 * @date: 2016-01-18 16:51
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 检查是否安装了sd卡
     *
     * @return false 未安装
     */
    public static boolean sdcardMounted() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED) && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        }
        return false;
    }

    /**
     * 获取SD卡剩余空间的大小
     *
     * @return long SD卡剩余空间的大小（单位：byte）
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDSize() {
        final String str = Environment.getExternalStorageDirectory().getPath();
        final StatFs localStatFs = new StatFs(str);
        final long blockSize = localStatFs.getBlockSizeLong();
        return localStatFs.getAvailableBlocksLong() * blockSize;
    }

    public static String getMD5(String str) {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.trim().getBytes());
            byte[] messageDigest = digest.digest();
            return toHexString(messageDigest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;

    }

    // MD5相关函数
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 转换为十六进制字符串
     *
     * @param messageDigest byte数组
     * @return String byte数组处理后字符串
     */
    public static String toHexString(byte[] messageDigest) {

        StringBuffer sb = new StringBuffer(messageDigest.length * 2);
        for (final byte element : messageDigest) {
            sb.append(HEX_DIGITS[(element & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[element & 0x0f]);
        }
        return sb.toString();
    }

    public static void saveObject(String path, Object object) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        File file = new File(path);
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(object);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object restoreObject(String path) {

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;

        File file = new File(path);

        if (!file.exists())
            return null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}

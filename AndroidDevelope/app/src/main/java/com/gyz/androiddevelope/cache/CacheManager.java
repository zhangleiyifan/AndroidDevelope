package com.gyz.androiddevelope.cache;

import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.util.FileUtil;
import com.gyz.androiddevelope.util.L;

import java.io.File;

/**
 * @author: guoyazhou
 * @date: 2016-01-19 16:28
 */
public class CacheManager {
    private static final String TAG = "CacheManager";


    /**
     * sdcard 最小空间，如果小于10M，不会再向sdcard里面写入任何数据
     */
    public static final long SDCARD_MIN_SPACE = 1024 * 1024 * 10;

    private static CacheManager cacheManager;

    private CacheManager() {

    }

    /**
     * 获取CacheManager实例
     *
     * @return
     */
    public static synchronized CacheManager getInstance() {

        if (cacheManager == null) {
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    /**
     * 将CacheItem从磁盘读取出来
     *
     * @param path
     * @return 缓存数据CachItem
     */
    public synchronized CacheItem getFromCache(String path) {

        CacheItem cacheItem = null;
        Object findItem = FileUtil.restoreObject(AppContants.CACHE_PATH + path);

        if (findItem != null) {
            cacheItem = (CacheItem) findItem;
        }

        if (cacheItem == null) {
            return cacheItem;
        }

        //缓存过期
        long time = cacheItem.getTimeStamp();
        if (System.currentTimeMillis() > time) {
            return null;
        }
        return cacheItem;
    }

    /**
     * 清除缓存文件
     */
    public void clearAllCache() {

        File file = null;
        File[] files = null;
        if (FileUtil.sdcardMounted()) {
            file = new File(AppContants.CACHE_PATH);
            files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    file1.delete();
                }
            }
        }
    }

    /**
     * 将CacheItem缓存到磁盘
     *
     * @param item
     * @return 是否缓存，True：缓存成功，False：不能缓存
     */
    public synchronized boolean putIntoCache(CacheItem item) {

        if (FileUtil.getSDSize() > SDCARD_MIN_SPACE) {
            FileUtil.saveObject(AppContants.CACHE_PATH + item.getKey(), item);
            return true;
        }
        return false;
    }

    public void initCacheDir() {

        // sdcard已经挂载并且空间不小于10M，可以写入文件;小于10M时，清除缓存
        if (FileUtil.sdcardMounted()) {
            if (FileUtil.getSDSize() < SDCARD_MIN_SPACE) {
                clearAllCache();
            } else {
                final File dir = new File(AppContants.CACHE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                    L.d("初始化 initCacheDir");
                }
            }
        }
    }


    /**
     * 查询是否有key对应的缓存文件
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        File file = new File(AppContants.CACHE_PATH + key);
        return file.exists();
    }


    /**
     * API data 缓存到文件
     *
     * @param key
     * @param data
     * @param
     */
    public void putFileCache(String key, String data, long expiredTime) {

        String md5Key = FileUtil.getMD5(key);

        CacheItem cacheItem = new CacheItem(md5Key, data, expiredTime);
        putIntoCache(cacheItem);

    }


    /**
     * 从文件缓存中取出缓存，没有则返回空
     *
     * @param key
     * @return
     */
    public String getFileCache(String key) {
        String md5Key = FileUtil.getMD5(key);

        if (contains(md5Key)) {
            CacheItem cacheItem = getFromCache(md5Key);
            if (cacheItem != null)
                return cacheItem.getData();
        }
        return null;
    }
}

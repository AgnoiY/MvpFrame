package com.mvpframe.capabilities.http.download;

/**
 * 下载回调接口
 * <p>
 * Data：2019/07/08
 *
 * @author yong
 */
public interface DownloadProgressCallback {

    /**
     * 下载进度回调
     *
     * @param currentSize 当前值
     * @param totalSize   总大小
     */
    void progress(long currentSize, long totalSize);

}

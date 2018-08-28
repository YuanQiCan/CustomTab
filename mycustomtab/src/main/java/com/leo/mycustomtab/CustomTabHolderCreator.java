package com.leo.mycustomtab;

/**
 * Created by leo on 2018/8/27.
 */
public interface CustomTabHolderCreator <VH extends CustomTabHolder> {
    /**
     * @return
     */
    public VH createCustomHolder();
}

package com.fish.apple.core.web.repository;

public interface SequenceRepository {
    /**
     * 刷新指定序列队列
     * @param sequenceName
     */
    void flush(String sequenceName);

    /**
     * 获取序列值
     * @param sequenceName
     * @return
     */
    Long next(String sequenceName);
}

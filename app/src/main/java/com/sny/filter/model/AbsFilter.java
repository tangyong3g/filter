package com.sny.filter.model;

/**
 * Created by Administrator on 2018/1/17.
 * 产生随机数，当前客户端过滤的属性
 */

public abstract class AbsFilter implements IFilter {

    /**
     * 产生随机数，当前客户端被过滤的比率
     */
    public String percent;

    @Override
    public boolean filter(AbsFilter suitableCondition) throws FilterException {
        return false;
    }
}

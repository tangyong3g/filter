package com.sny.filter.model;

/**
 * Created by ty_sany@163.com on 2018/1/18.
 */

public interface IFilter {

    /**
     * @param suitableCondition 符合条件的参数
     * @return
     */
    boolean filter(AbsFilter suitableCondition) throws FilterException;

}

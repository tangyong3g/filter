package com.sny.filter.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/17.
 * 产生随机数，当前客户端过滤的属性
 * <p>
 * 这个生命周期和服务器需求一样长，需要被持久化
 * <p>
 * <p>
 * <p>
 * <p>
 * {
 * "percent": 30,
 * "isCalculate": false,
 * "calculateAndSelected": false,
 * "country": "",
 * "language": ""
 * }
 * <p>
 * {\"percent\":30,\"isCalculate\":false,\"calculateAndSelected\":false,\"country\":\"\",\"language\":\"\"}
 * </p>
 * <p>
 * 关于firebase时间效益的问题 默认是12小时。
 */

public abstract class AbsFilter implements IFilter, Serializable {

    public static final String Tag = "Filter";

    /**
     * 产生随机数，当前客户端被过滤的比率
     */
    public int percent;

    /**
     * 客户端是否参与过计算,每次客户端只会参与一次计算，对于一个用户的一个case来说。
     */
    public boolean isCalculate = false;

    /**
     * 每个用户在整个case，完整周期中只会被选中一次。
     */
    public boolean calculateAndSelected = false;


    @Override
    public boolean filter(AbsFilter suitableCondition) throws FilterException {

        //释放比例是否在增长
        boolean increment = suitableCondition.percent - percent > 0;

        /**
         * 处理增长和下降不同的客户端算法
         */

        if (increment) {
            boolean result = increatemet(suitableCondition);
            percent = suitableCondition.percent;

            return result;

        } else {

            boolean result = subtract(suitableCondition);
            percent = suitableCondition.percent;

            return result;
        }

    }


    /**
     * 计算比例上各项的的情况
     *
     * @return
     */
    private boolean increatemet(AbsFilter suitableCondition) {

        //增量，并且被计算过，不在样本考虑范围内。
        if (calculateAndSelected) {
            return false;
        }

        float calcPercent = (float) (suitableCondition.percent - percent) / (float) (100 - percent) * 100;

        boolean result = Math.random() * 100 < calcPercent;

        calculateAndSelected = result;

        return result;

    }


    /**
     * 当比例下降时，选择计算样本为原来以为被选中的用户
     *
     * @param suitableCondition
     * @return
     */
    private boolean subtract(AbsFilter suitableCondition) {

        boolean result = false;

        //下降时范围是原来被选中的样本，然后重新计算
        if (calculateAndSelected) {

            float calcPercent = (float) (suitableCondition.percent) / (float) (percent) * 100;
            result = (Math.random() * 100 < calcPercent);
        }
        calculateAndSelected = result;

        return result;
    }
}

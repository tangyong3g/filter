package com.sny.filter.model;

import android.text.TextUtils;


/**
 * Created by ty_sany@163.com on 2018/1/18.
 * <p>
 * <p>区域过滤器</p>
 */
public class DirectFilter extends AbsFilter {

    /**
     * 国家
     */
    public String country;

    /**
     * 语言
     */
    public String language;

    @Override
    public boolean filter(AbsFilter suitableCondition) throws FilterException {

        boolean result = true;

        if (suitableCondition == null) {
            throw new FilterException("no suitable condition params, plz check!");
        }

        //随机数比率是否能通过
        if (!super.filter(suitableCondition)) {
            return false;
        }

        boolean type = suitableCondition instanceof DirectFilter;

        //类型不一至，无法起来过滤作用,直接通过
        if (!type) {
            return true;
        }

        DirectFilter temp = (DirectFilter) suitableCondition;

        //找到不通过的成立
        if (temp.language != null && !TextUtils.isEmpty(temp.language) && !temp.language.equals(this.language)) {
            return false;
        }

        //判断国家
        if (temp.country != null && !TextUtils.isEmpty(temp.country) && !temp.country.equals(this.country)) {
            return false;
        }
        return result;
    }
}

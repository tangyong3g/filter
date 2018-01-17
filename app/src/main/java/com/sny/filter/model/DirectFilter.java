package com.sny.filter.model;

import android.text.TextUtils;


/**
 * Created by Administrator on 2018/1/18.
 */
public class DirectFilter extends AbsFilter {

    public String country;
    public String launguage;

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
        if (!TextUtils.isEmpty(temp.launguage) && !temp.launguage.equals(this.launguage)) {
            return false;
        }

        //判断国家
        if (!TextUtils.isEmpty(temp.country) && !temp.country.equals(this.country)) {
            return false;
        }
        return result;
    }
}

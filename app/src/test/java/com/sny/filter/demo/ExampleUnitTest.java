package com.sny.filter.demo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sny.filter.model.AbsFilter;
import com.sny.filter.model.DirectFilter;

import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    //    @Test
    public void randomone() throws Exception {

        int Atype = 0;
        int BType = 0;

        int count = 10000;
        while (count > 0) {
            if (Math.random() * 100 > 30) {
                BType++;
            } else {
                Atype++;
            }
            count--;
        }

        assertTrue(BType > 30);
    }


    /**
     * 1：创建1000个客户端样本
     * 2：服务器下发30%样本为可用。
     * 3: 产生这30%的样本出来。
     *
     * @throws Exception
     */
//    @Test
    public void testIncreateTenPercent() throws Exception {

        //样本数量
        int testCount = 1000;

        //服务器条件
        AbsFilter suitableFilter = new DirectFilter();
        suitableFilter.percent = 30;
        suitableFilter = (DirectFilter) suitableFilter;
        ((DirectFilter) suitableFilter).language = null;
        ((DirectFilter) suitableFilter).country = null;

        //容器装载 选择和没有选择的样本
        List<AbsFilter> selectedSample = new ArrayList<>();
        List<AbsFilter> unSelectedSample = new ArrayList<>();

        for (int i = 0; i < testCount; i++) {

            //模拟客户端得到数据开始计算
            DirectFilter directFilter = new DirectFilter();
            boolean selected = directFilter.filter(suitableFilter);

            if (selected) {
                selectedSample.add(directFilter);
            } else {
                unSelectedSample.add(directFilter);
            }
        }
        System.out.print("被选中的样本:" + selectedSample.size());
        System.out.print("没有被选中的样本:" + unSelectedSample.size());

    }


    /**
     * 1：创建1000个客户端样本
     * 2：服务器下发30%样本为可用。
     * 3: 产生这30%的样本出来。
     * 4: 然后再增长 到 40%
     *
     * @throws Exception
     */
//    @Test
    public void testRandomTwo() throws Exception {

        //样本数量
        int testCount = 10000;

        int selectedSampleCount = 0;
        int unSelectedSampleCount = 0;

        //服务器条件
        AbsFilter suitableFilter = new DirectFilter();
        suitableFilter.percent = 10;
        suitableFilter = (DirectFilter) suitableFilter;
        ((DirectFilter) suitableFilter).language = null;
        ((DirectFilter) suitableFilter).country = null;

        //容器装载 选择和没有选择的样本
        List<AbsFilter> selectedSample = new ArrayList<>();
        List<AbsFilter> unSelectedSample = new ArrayList<>();

        for (int i = 0; i < testCount; i++) {

            //模拟客户端得到数据开始计算
            DirectFilter directFilter = new DirectFilter();
            boolean selected = directFilter.filter(suitableFilter);

            if (selected) {
                selectedSample.add(directFilter);
            } else {
                unSelectedSample.add(directFilter);
            }
        }
        System.out.println("被选中的样本:" + selectedSample.size());
        System.out.println("没有被选中的样本:" + unSelectedSample.size());
        System.out.println("第一个阶段完成" + unSelectedSample.size());
        System.out.println("第二阶段开始开始增长到40%");

        AbsFilter suitableFilterTwo = new DirectFilter();
        suitableFilterTwo.percent = 60;
        suitableFilterTwo = (DirectFilter) suitableFilterTwo;
        ((DirectFilter) suitableFilterTwo).language = null;
        ((DirectFilter) suitableFilterTwo).country = null;


        List<AbsFilter> total = new ArrayList<>();

        total.addAll(selectedSample);
        total.addAll(unSelectedSample);


        for (int i = 0; i < total.size(); i++) {

            DirectFilter filter = (DirectFilter) total.get(i);
            filter.filter(suitableFilterTwo);

            if (filter.calculateAndSelected) {
                selectedSampleCount++;
            } else {
                unSelectedSampleCount++;
            }
        }

        System.out.println("选中的样本:\t" + selectedSampleCount);
        System.out.println("未选中的样本:\t" + unSelectedSampleCount);

    }


    /**
     * 1：创建1000个客户端样本
     * 2：服务器下发30%样本为可用。
     * 3: 产生这30%的样本出来。
     * 4: 然后再下降  10%.
     *
     * @throws Exception
     */
//    @Test
    public void testRandomThree() throws Exception {

//        String jsonStr = "{\\\"percent\\\":30,\\\"isCalculate\\\":false,\\\"calculateAndSelected\\\":false,\\\"country\\\":\\\"\\\",\\\"language\\\":\\\"\\\"}";
//
//        int percent =

        //样本数量
        int testCount = 10000;

        int selectedSampleCount = 0;
        int unSelectedSampleCount = 0;

        System.out.println("第一个阶段开始共有" + testCount + "用户\t\n选择30%的用户");

        //服务器条件
        AbsFilter suitableFilter = new DirectFilter();
        suitableFilter.percent = 30;
        suitableFilter = (DirectFilter) suitableFilter;
        ((DirectFilter) suitableFilter).language = null;
        ((DirectFilter) suitableFilter).country = null;

        //容器装载 选择和没有选择的样本
        List<AbsFilter> selectedSample = new ArrayList<>();
        List<AbsFilter> unSelectedSample = new ArrayList<>();

        for (int i = 0; i < testCount; i++) {

            //模拟客户端得到数据开始计算
            DirectFilter directFilter = new DirectFilter();
            boolean selected = directFilter.filter(suitableFilter);

            if (selected) {
                selectedSample.add(directFilter);
            } else {
                unSelectedSample.add(directFilter);
            }
        }
        System.out.println("被选中的样本:" + selectedSample.size());
        System.out.println("没有被选中的样本:" + unSelectedSample.size());
        System.out.println("第一个阶段完成");

        System.out.println("第二阶段开始下降到10%");

        AbsFilter suitableFilterTwo = new DirectFilter();
        suitableFilterTwo.percent = 10;
        suitableFilterTwo = (DirectFilter) suitableFilterTwo;
        ((DirectFilter) suitableFilterTwo).language = null;
        ((DirectFilter) suitableFilterTwo).country = null;


        List<AbsFilter> total = new ArrayList<>();

        total.addAll(selectedSample);
        total.addAll(unSelectedSample);


        for (int i = 0; i < total.size(); i++) {

            DirectFilter filter = (DirectFilter) total.get(i);
            filter.filter(suitableFilterTwo);

            if (filter.calculateAndSelected) {
                selectedSampleCount++;
            } else {
                unSelectedSampleCount++;
            }
        }

        System.out.println("选中的样本:\t" + selectedSampleCount);
        System.out.println("未选中的样本:\t" + unSelectedSampleCount);

    }

    /**
     * 1：创建1000个客户端样本
     * 2：服务器下发30%样本为可用。
     * 3: 产生这30%的样本出来。
     * 4: 然后再下降  10%.
     *
     * @throws Exception
     */
//    @Test
    public void testRandomFour() throws Exception {

        String jsonStr = "{   \"percent\": 30,   \"isCalculate\": false,   \"calculateAndSelected\": false,   \"country\": \"\",   \"language\": \"\" }";

        Gson gson = new Gson();
        DirectFilter filter = gson.fromJson(jsonStr, DirectFilter.class);


        System.out.println("选中的样本:\t" + filter.percent);

    }

}
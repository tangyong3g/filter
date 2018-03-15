package com.sny.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.net.core.service.config.ServiceRemoteConfigInstance;
import com.sny.filter.model.AbsFilter;
import com.sny.filter.model.DirectFilter;
import com.sny.filter.model.FilterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2018/2/2.
 * <p>
 * 用来管理项目中所有需要控制比例问题，区域问题，对客户端进行动态配置的管理者，生命周期等于项目
 * <p>
 * <p>
 * <p>
 * 1.在application 初始化的时候就需要创建
 * 2.在key的时候使用拿出相应的值
 * </p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 问题：
 * <p>
 * 1.没有采用延迟处理的方式，一开始就加载了。这样加大的服务器
 * <p>
 * <p>
 * </p>
 */

public class FilterManager {

    private static final String TAG = "filter";

    private static Context mContext;
    private static FilterManager instance;
    //管理项目中所有的需要控制 区域,语言投放需求配置项
    private Map<String, AbsFilter> filterMap;
    //序列化文件名
    private static final String serilizableFile = "filter.ca";


    private Map<String, AbsFilter> getFilterMap() {
        if (filterMap == null) {
            filterMap = new HashMap<>();
        }
        return filterMap;
    }


    private FilterManager() {

        StackTraceElement[] stacks = new Throwable().getStackTrace();
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + (stacks[1].getClassName() + "\t" + stacks[1].getMethodName()));
        }
        if (filterMap == null) {
            filterMap = new HashMap<>();
        }

        init();
    }

    private void init() {

        StackTraceElement[] stacks = new Throwable().getStackTrace();

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + (stacks[1].getClassName() + "\t" + stacks[1].getMethodName()));
        }

        long start = System.currentTimeMillis();
        //读取序列化值
        filterMap = readObj(getInternalStorageFile());
        //读取后台值
        initFirebaseServer();
        Log.i(TAG, "init use time :" + (System.currentTimeMillis() - start));
    }


    private void initFirebaseServer() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + (stacks[1].getClassName() + "\t" + stacks[1].getMethodName()));
        }

        FirebaseRemoteConfig.getInstance().setDefaults(R.xml.default_value);
        ServiceRemoteConfigInstance.getInstance(mContext.getApplicationContext()).setIsSupportFireBase(true, R.xml.default_value, new ServiceRemoteConfigInstance.OnFirebaseFectchComplete() {
            @Override
            public void onComplete(@NonNull Task task) {

                getDirectionValue("filter");

            }

        }, 0);


    }

    /**
     * 得到Instance实例
     *
     * @return
     */
    public static FilterManager getInstance(Context context) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + (stacks[1].getClassName() + "\t" + stacks[1].getMethodName()));
        }

        if (instance == null) {
            mContext = context;
            instance = new FilterManager();
        }

        return instance;
    }


    /**
     * 根据需求的Key获取当前用户是否是选中用户
     * <p>
     * TODO 确保服务器的值能够拿到. self  Direction的属性不全,加入进去并且完成序列化,这里性能可能有问题
     *
     * @return boolean
     */
    public boolean getDirectionValue(String key) {

        StackTraceElement[] stacks = new Throwable().getStackTrace();

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + (stacks[1].getClassName() + "\t" + stacks[1].getMethodName()));
        }

        /**
         * 计算过了，无需要再计算直接取值即可。
         */
        if (filterMap != null) {
            AbsFilter filter = filterMap.get(key);

            if (filter != null) {
                return filter.calculateAndSelected;
            }
        }

        boolean result = false;

        String value = ServiceRemoteConfigInstance.getInstance(mContext.getApplicationContext()).getString(key);
        Gson gson = new Gson();
        DirectFilter suitableFilter = gson.fromJson(value, DirectFilter.class);

        DirectFilter self = new DirectFilter();

        try {

            result = self.filter(suitableFilter);
            getFilterMap().put(key, self);

            writeObj(getFilterMap());

        } catch (FilterException ex) {
            ex.printStackTrace();
        }

        return result;
    }


    /**
     * 读取序列化文件
     *
     * @return ArrayList<ServiceConnectConfig>
     */
    private Map<String, AbsFilter> readObj(File file) {

        StackTraceElement[] stacks = new Throwable().getStackTrace();

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + (stacks[1].getClassName() + "\t" + stacks[1].getMethodName()));
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Map<String, AbsFilter> result = null;
        try {

            //对象反序列化过程
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            result = (Map<String, AbsFilter>) ois.readObject();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ca) {
            ca.printStackTrace();
        } catch (Exception eofEx) {
            eofEx.printStackTrace();
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (IOException exi) {
                exi.printStackTrace();
            } catch (NullPointerException nullEx) {
                nullEx.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 序列化对象，整个List序列化
     *
     * @param object
     */
    private void writeObj(Map<String, AbsFilter> object) {

        StackTraceElement[] stacks = new Throwable().getStackTrace();

        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + (stacks[1].getClassName() + "\t" + stacks[1].getMethodName()));
        }

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {

            File file = getInternalStorageFile();

            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);

            Log.i(TAG, "serilizable success");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();

        } finally {
            try {
                oos.flush();
                oos.close();
                fos.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }


    /**
     * 创建存储序列化文件
     *
     * @param
     */
    private File getInternalStorageFile() {

        File folder = mContext.getFilesDir();
        File file = new File(folder.getAbsolutePath() + File.separator + serilizableFile);

        boolean success = false;
        if (!file.exists()) {
            try {
                success = file.createNewFile();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        if (success) {
            Log.i(TAG, file.getAbsolutePath() + "create success!");
        }
        return file;
    }


}

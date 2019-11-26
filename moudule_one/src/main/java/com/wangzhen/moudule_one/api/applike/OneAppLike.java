package com.wangzhen.moudule_one.api.applike;

import android.util.Log;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.wangzhen.moudule_one.api.service.OneRouterService;
import com.wangzhen.moudule_one.business.serviceimpl.OneRouterServiceImpl;

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
public class OneAppLike implements IApplicationLike {
    @Override
    public void onCreate() {
        Log.e("IApplicationLike","OneAppLike onCreate");
        registerService();
    }

    @Override
    public void onStop() {

    }

    private void registerService() {
        Router.getInstance().addService(OneRouterService.class, new OneRouterServiceImpl());
    }
}

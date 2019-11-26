package com.wangzhen.moudule_one.business.serviceimpl;

import android.content.Context;
import android.content.Intent;

import com.wangzhen.moudule_one.api.service.OneRouterService;
import com.wangzhen.moudule_one.business.OneActivity;

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
public class OneRouterServiceImpl implements OneRouterService {

    @Override
    public void launchOneActivity(Context context) {
        Intent intent = new Intent(context, OneActivity.class);
        context.startActivity(intent);
    }
}

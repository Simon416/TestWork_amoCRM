package com.example.test_amocrm.utils;

import android.content.Context;

import com.example.test_amocrm.R;

import javax.inject.Inject;

public class StringProviderImpl implements StringProvider {

    private final Context context;

    @Inject
    public StringProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getMessageErrorCode110() {
        return context.getString(R.string.error_code_110);
    }

    @Override
    public String getMessageErrorCode111() {
        return context.getString(R.string.error_code_111);
    }

    @Override
    public String getMessageErrorCode112() {
        return context.getString(R.string.error_code_112);
    }

    @Override
    public String getMessageErrorCode113() {
        return context.getString(R.string.error_code_113);
    }

    @Override
    public String getMessageErrorCode101() {
        return context.getString(R.string.error_code_101);
    }

    @Override
    public String getMessageErrorCode400() {
        return context.getString(R.string.error_code_400);
    }

    @Override
    public String getMessageErrorCode401() {
        return context.getString(R.string.error_code_401);
    }

    @Override
    public String getMessageErrorCode403() {
        return context.getString(R.string.error_code_403);
    }

    @Override
    public String getMessageErrorCode404() {
        return context.getString(R.string.error_code_404);
    }

    @Override
    public String getMessageErrorCode500() {
        return context.getString(R.string.error_code_500);
    }

    @Override
    public String getMessageErrorCode502() {
        return context.getString(R.string.error_code_502);
    }

    @Override
    public String getMessageErrorCode503() {
        return context.getString(R.string.error_code_503);
    }

    @Override
    public String getMessageErrorServer() {
        return context.getString(R.string.error_server);
    }

    @Override
    public String getMessageErrorCode() {
        return context.getString(R.string.error_code);
    }

    @Override
    public String getMessageUndefinedError() {
        return context.getString(R.string.error_undefined);
    }
}

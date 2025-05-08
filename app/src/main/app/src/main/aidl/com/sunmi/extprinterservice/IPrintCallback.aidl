package com.sunmi.extprinterservice;

interface IPrintCallback {
    void onRunResult(boolean isSuccess);
    void onReturnString(String result);
    void onRaiseException(int code, String msg);
}

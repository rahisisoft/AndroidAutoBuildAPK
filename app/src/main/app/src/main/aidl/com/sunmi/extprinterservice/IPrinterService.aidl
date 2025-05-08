package com.sunmi.extprinterservice;

interface IPrinterService {
    void printText(String text, in android.os.Bundle options);
    void lineWrap(int n, in android.os.Bundle options);
}

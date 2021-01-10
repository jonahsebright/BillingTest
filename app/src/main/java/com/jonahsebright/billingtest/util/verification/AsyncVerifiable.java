package com.jonahsebright.billingtest.util.verification;

import com.jonahsebright.billingtest.util.OnCompleteListener;

public interface AsyncVerifiable<T> {
    void verify(T toVerify, OnCompleteListener onCompleteListener);
}

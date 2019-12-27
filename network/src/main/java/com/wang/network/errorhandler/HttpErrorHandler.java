package com.wang.network.errorhandler;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable){
        return Observable.error(ExceptionHandle.HandleException(throwable));
    }
}

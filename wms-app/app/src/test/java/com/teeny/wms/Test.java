package com.teeny.wms;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Test
 * @since 2017/9/12
 */

public class Test {
    private Flowable<Object> mFlowable;

    private void test(){
        if (mFlowable == null){
            mFlowable = Flowable.just(new Object());
        }
        mFlowable.subscribe(new FlowableSubscriber<Object>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}

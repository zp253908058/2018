package com.teeny.wms.datasouce.net;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.view.ProgressView;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see EmptySubscriber
 * @since 2017/8/11
 */

public class EmptySubscriber extends ResponseSubscriber<EmptyEntity> {

    public EmptySubscriber(ProgressView progressView) {
        super(progressView);
    }

    @Override
    public void doNext(EmptyEntity data) {

    }

    @Override
    public void doComplete() {

    }
}

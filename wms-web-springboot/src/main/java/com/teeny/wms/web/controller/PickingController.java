package com.teeny.wms.web.controller;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.request.OutputPickingRequestEntity;
import com.teeny.wms.web.model.response.OutPickingTaskEntity;
import com.teeny.wms.web.model.response.OutputPickingOrderEntity;
import com.teeny.wms.web.service.PickingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PickingController
 * @since 2018/1/21
 */
@RestController
@RequestMapping("/api/picking/")
public class PickingController {

    private PickingService mService;

    @Autowired
    public void setService(PickingService service) {
        mService = service;
    }

    /**
     * 初始化
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("initialize")
    public ResponseEntity<OutputPickingOrderEntity> initialize(@RequestHeader("account") String account, @User UserEntity entity) {
        return new ResponseEntity<>(mService.initialize(account, entity.getId()));
    }

    /**
     * 获取工作任务
     *
     * @return List<OutPickingTaskEntity>
     */
    @GetMapping("taskList")
    public ResponseEntity<List<OutPickingTaskEntity>> getTaskList(@RequestHeader("account") String account, @User UserEntity entity) {
        return new ResponseEntity<>(mService.getTaskList(account, entity.getId()));
    }

    @PostMapping("complete")
    public ResponseEntity<EmptyEntity> complete(@RequestHeader("account") String account, @RequestBody OutputPickingRequestEntity entity, @User UserEntity user) {
        mService.complete(account, entity, user.getId());
        return new ResponseEntity<>();
    }
}

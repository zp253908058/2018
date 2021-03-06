package com.teeny.wms.web.controller;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.StringMapEntity;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.response.DocumentResponseEntity;
import com.teeny.wms.web.model.response.HistoryGoodsEntity;
import com.teeny.wms.web.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description: 公用
 *
 * @author zp
 * @version 1.0
 * @see CommonController
 * @since 2017/11/7
 */
@RestController
public class CommonController {

    private CommonService mCommonService;

    @Autowired
    public CommonController(CommonService commonService) {
        mCommonService = commonService;
    }

    // 测试接口
    @GetMapping(value = "/log/test")
    public ResponseEntity<EmptyEntity> test() {
        return new ResponseEntity<>();
    }

    @GetMapping(value = "/api/home/warehouseList")
    public ResponseEntity<List<KeyValueEntity>> getWarehouseList(@RequestHeader("account") String account) {
        return new ResponseEntity<>(mCommonService.getWarehouseList(account));
    }

    @GetMapping(value = "/api/home/documentList")
    public ResponseEntity<DocumentResponseEntity> getDocumentInfo(@RequestHeader("account") String account, @RequestHeader("sId") int sId) {
        return new ResponseEntity<>(mCommonService.getDocumentList(account, sId));
    }

    @GetMapping("/api/home/username")
    public ResponseEntity<String> getUsername(@User UserEntity entity) {
        return new ResponseEntity<>(entity.getUsername());
    }

    //获取历史货位
    @GetMapping(value = "/api/common/historyLocation")
    public ResponseEntity<List<StringMapEntity>> getHistoryLocation(@RequestHeader("account") String account, @RequestParam("goodsId") int pId) {
        return new ResponseEntity<>(mCommonService.getHistoryLocation(account, pId));
    }

    //获取仓库
    @GetMapping(value = "/api/common/warehouseList")
    public ResponseEntity<List<KeyValueEntity>> getWarehouses(@RequestHeader("account") String account) {
        return new ResponseEntity<>(mCommonService.obtainWarehouseList(account));
    }

    /**
     * 获取库区
     *
     * @param id      仓库id
     * @param account 账套
     * @return
     */
    @GetMapping(value = "/api/common/repositoryList/{id}")
    public ResponseEntity<List<KeyValueEntity>> getRepositoryList(@PathVariable("id") int id, @RequestHeader("account") String account) {
        return new ResponseEntity<>(mCommonService.obtainRepositoryList(account, id));
    }

    /**
     * 获取区域
     *
     * @param id      库区id
     * @param account 账套
     * @return
     */
    @GetMapping(value = "/api/common/areaList/{id}")
    public ResponseEntity<List<KeyValueEntity>> getAreaList(@PathVariable("id") int id, @RequestHeader("account") String account) {
        return new ResponseEntity<>(mCommonService.obtainAreaList(account, id));
    }

    /**
     * 获取区域
     *
     * @param condition 查询条件
     * @param account   账套
     * @return
     */
    @GetMapping(value = "/api/common/historyGoods")
    public ResponseEntity<List<HistoryGoodsEntity>> getHistoryGoods(@RequestHeader("account") String account, @RequestParam("condition") String condition) {
        return new ResponseEntity<>(mCommonService.getHistoryGoods(account, condition));
    }
}

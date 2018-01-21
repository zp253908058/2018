package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.response.OutPickingTaskEntity;
import com.teeny.wms.web.model.response.OutputPickingEntity;
import com.teeny.wms.web.model.response.OutputPickingOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PickingMapper
 * @since 2018/1/21
 */
@Repository
@Mapper
public interface PickingMapper {

    List<KeyValueEntity> getOrderList(@Param("account") String account, @Param("userId") int userId);

    OutputPickingOrderEntity getData(@Param("account") String account, @Param("id") int id);

    List<OutPickingTaskEntity> getTaskList(@Param("account") String account, @Param("userId") int userId);

    void delete(@Param("account") String account, @Param("id") int id);

    void add(@Param("account") String account, @Param("id") int id, @Param("list") List<OutputPickingEntity> list);

    void updateDetailDate(@Param("account") String account, @Param("id") int id, @Param("userId") int userId);

    void updateDate(@Param("account") String account, @Param("id") int id);
}

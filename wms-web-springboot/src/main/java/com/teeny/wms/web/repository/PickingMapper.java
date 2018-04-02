package com.teeny.wms.web.repository;

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
 * @since 2018/3/17
 */

@Mapper
@Repository
public interface PickingMapper {

    Integer getIdByTrigger(@Param("account") String account, @Param("userId") int userId);

    OutputPickingOrderEntity getDataById(@Param("account") String account, @Param("id") int id);

    void updateBillState(@Param("account") String account, @Param("id") int id);

    List<OutPickingTaskEntity> getTaskList(@Param("account") String account, @Param("userId") int userId);

    void delete(@Param("account") String account, @Param("id") int id);

    void add(@Param("account") String account, @Param("id") int id, @Param("list") List<OutputPickingEntity> list);

    void updateDetailDate(@Param("account") String account, @Param("id") int id, @Param("number") float number, @Param("userId") int userId);

    void updateDate(@Param("account") String account, @Param("id") int id);
}

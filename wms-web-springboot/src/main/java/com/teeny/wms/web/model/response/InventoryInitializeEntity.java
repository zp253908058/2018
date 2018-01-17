package com.teeny.wms.web.model.response;

import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryInitializeEntity
 * @since 2018/1/16
 */
@Alias("InventoryInitializeEntity")
public class InventoryInitializeEntity {

    private List<RepositoryEntity> repositoryList;

    private int unfinishedNumber;

    private int finishedNumber;

    public List<RepositoryEntity> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<RepositoryEntity> repositoryList) {
        this.repositoryList = repositoryList;
    }

    public int getUnfinishedNumber() {
        return unfinishedNumber;
    }

    public void setUnfinishedNumber(int unfinishedNumber) {
        this.unfinishedNumber = unfinishedNumber;
    }

    public int getFinishedNumber() {
        return finishedNumber;
    }

    public void setFinishedNumber(int finishedNumber) {
        this.finishedNumber = finishedNumber;
    }
}

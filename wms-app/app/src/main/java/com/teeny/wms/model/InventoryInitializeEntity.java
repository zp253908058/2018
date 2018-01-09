package com.teeny.wms.model;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryInitializeEntity
 * @since 2017/12/27
 */

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

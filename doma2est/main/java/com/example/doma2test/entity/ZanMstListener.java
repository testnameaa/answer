package com.example.doma2test.entity;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

/**
 * 
 */
public class ZanMstListener implements EntityListener<ZanMst> {

    @Override
    public void preInsert(ZanMst entity, PreInsertContext<ZanMst> context) {
    }

    @Override
    public void preUpdate(ZanMst entity, PreUpdateContext<ZanMst> context) {
    }

    @Override
    public void preDelete(ZanMst entity, PreDeleteContext<ZanMst> context) {
    }

    @Override
    public void postInsert(ZanMst entity, PostInsertContext<ZanMst> context) {
    }

    @Override
    public void postUpdate(ZanMst entity, PostUpdateContext<ZanMst> context) {
    }

    @Override
    public void postDelete(ZanMst entity, PostDeleteContext<ZanMst> context) {
    }
}
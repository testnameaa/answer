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
public class RhinConvMstListener implements EntityListener<RhinConvMst> {

    @Override
    public void preInsert(RhinConvMst entity, PreInsertContext<RhinConvMst> context) {
    }

    @Override
    public void preUpdate(RhinConvMst entity, PreUpdateContext<RhinConvMst> context) {
    }

    @Override
    public void preDelete(RhinConvMst entity, PreDeleteContext<RhinConvMst> context) {
    }

    @Override
    public void postInsert(RhinConvMst entity, PostInsertContext<RhinConvMst> context) {
    }

    @Override
    public void postUpdate(RhinConvMst entity, PostUpdateContext<RhinConvMst> context) {
    }

    @Override
    public void postDelete(RhinConvMst entity, PostDeleteContext<RhinConvMst> context) {
    }
}
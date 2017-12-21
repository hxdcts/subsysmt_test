package com.pfb.biz.vo.base;

import com.pfb.entity.base.AbstractBase;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 下游商户上送参数基类
 */
public class OuterRequestBase extends AbstractBase {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

package com.prodnees.core.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public abstract class MapperUtil {
    public static Mapper getDozer() {
        return DozerBeanMapperBuilder.buildDefault();
    }
}

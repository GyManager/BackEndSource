package org.gymanager.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface GyManagerConverter<S,T> extends Converter<S,T> {

    default List<T> convert(List<S> sourceList){
        return Objects.isNull(sourceList)? null : sourceList.stream().map(this::convert).collect(Collectors.toList());
    }
}

package com.renansouza.base;

import io.micronaut.core.annotation.Introspected;

import javax.annotation.Nullable;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@Introspected
public class SortingAndOrderArguments {

    @Nullable
    private Integer offset;

    @Nullable
    private Integer max;

    @Nullable
    @Pattern(regexp = "id|name|registration")
    private String sort;

    @Pattern(regexp = "asc|ASC|desc|DESC")
    @Nullable
    private String order;

    public SortingAndOrderArguments() {}

    public Optional<Integer> getOffset() {
        return Optional.ofNullable(offset);
    }

    public void setOffset(@Nullable Integer offset) {
        this.offset = offset;
    }

    public Optional<Integer> getMax() {
        return Optional.ofNullable(max);
    }

    public void setMax(@Nullable Integer max) {
        this.max = max;
    }

    public Optional<String> getSort() {
        return Optional.ofNullable(sort);
    }

    public void setSort(@Nullable String sort) {
        this.sort = sort;
    }

    public Optional<String> getOrder() {
        return Optional.ofNullable(order);
    }

    public void setOrder(@Nullable String order) {
        this.order = order;
    }
}
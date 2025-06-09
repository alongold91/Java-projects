package com.example.dto;

import com.example.validation.OffsetAndLimit;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@OffsetAndLimit(message = "Offset and limit must be passed together or none of them at all")
public class GetListOfUserTodosRequest {
    @Positive(message = "User id must be a positive number")
    private long userId;
    @PositiveOrZero(message = "Offset can not be negative")
    private int offset;
    @Positive(message = "Limit can not be negative")
    private int limit;

    public GetListOfUserTodosRequest() {
    }

    public GetListOfUserTodosRequest(long userId, int offset, int limit) {
        this.userId = userId;
        this.offset = offset;
        this.limit = limit;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}

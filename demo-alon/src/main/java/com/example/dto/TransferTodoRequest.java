package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferTodoRequest {
    @NotNull(message = "Todo id can't be null")
    @Positive(message = "Todo id has to be a positive number")
    Long todoId;
    @NotNull(message = "User id to transfer from can't be null")
    @Positive(message = "User id to transfer from has to be a positive number")
    Long fromUserId;
    @NotNull(message = "User id to transfer to can't be null")
    @Positive(message = "User id to transfer to has to be a positive number")
    Long toUserId;

    public TransferTodoRequest() {
    }

    public TransferTodoRequest(
            Long todoId,
            Long fromUserId,
            Long toUserId) {
        this.todoId = todoId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

}

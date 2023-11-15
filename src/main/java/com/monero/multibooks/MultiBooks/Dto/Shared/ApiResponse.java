package com.monero.multibooks.MultiBooks.Dto.Shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {

    private Object data;
    private String message;

    public ApiResponse(Object t, String message) {
        this.data = t;
        this.message = message;
    }
}

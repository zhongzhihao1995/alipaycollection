package com.tony.alipaycollection.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private RetEnum ret;
    private String retMessage;

    public ApiException(RetEnum ret) {
        super(ret.asMessage());
        this.ret = ret;
        this.retMessage = ret.asMessage();
    }

    public ApiException(RetEnum ret, String retMessage) {
        super(retMessage);
        this.ret = ret;
        this.retMessage = retMessage;
    }

}

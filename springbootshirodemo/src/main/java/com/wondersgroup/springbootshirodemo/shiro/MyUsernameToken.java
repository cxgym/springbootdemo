package com.wondersgroup.springbootshirodemo.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class MyUsernameToken extends UsernamePasswordToken {

    private String token;

    private Boolean sso;

    private Boolean intelli;

    public MyUsernameToken() {
    }

    public MyUsernameToken(String token) {
        this.token = token;
    }

    public MyUsernameToken(String token, Boolean sso) {
        this.token = token;
        this.sso = sso;
    }

}

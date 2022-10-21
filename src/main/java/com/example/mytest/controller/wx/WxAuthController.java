package com.example.mytest.controller.wx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2022/10/2121:23
 */
@RestController
@Slf4j
public class WxAuthController {


    @GetMapping("/wx/auth")
    public String auth(String signature, String timestamp, String nonce, String echostr) {
        return echostr;
    }
}

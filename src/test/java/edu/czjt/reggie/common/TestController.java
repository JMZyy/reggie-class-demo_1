package edu.czjt.reggie.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jinkun.tian on 2023/5/10
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/exception")
    public String testException() {
        throw new CustomException("Test exception");
    }
}

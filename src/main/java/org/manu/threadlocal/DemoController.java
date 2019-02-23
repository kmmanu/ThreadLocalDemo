package org.manu.threadlocal;


import lombok.extern.slf4j.Slf4j;
import org.manu.threadlocal.context.RequestContext;
import org.manu.threadlocal.context.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        User user = RequestContext.get();
        log.info("Requested by {}", user);
        return "Hello " + user.getName();
    }
}

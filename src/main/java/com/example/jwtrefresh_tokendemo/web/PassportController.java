package com.example.jwtrefresh_tokendemo.web;



import com.example.jwtrefresh_tokendemo.result.GraceJSONResult;
import com.example.jwtrefresh_tokendemo.result.ResponseStatusEnum;
import com.example.jwtrefresh_tokendemo.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequestMapping("passport")
@RestController
public class PassportController {


    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("login")
    public GraceJSONResult login(@RequestParam("userId") String userId) throws Exception {

        String token = jwtUtils.createToken(userId);
        String refreshToken = jwtUtils.createRefreshToken(userId);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("refreshToken", refreshToken);

        jwtUtils.getTokenMap().put("token", token);
        jwtUtils.getTokenMap().put("refreshToken", refreshToken);



        return GraceJSONResult.ok(jwtUtils.getTokenMap());
    }

    @PostMapping("follow")
    public GraceJSONResult follow() throws Exception {
        return GraceJSONResult.ok();
    }

}

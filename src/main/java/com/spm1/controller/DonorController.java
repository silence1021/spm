package com.spm1.controller;

import com.spm1.entity.Donor;
import com.spm1.service.DonorService;
import com.spm1.tools.HttpResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/donor")
@Slf4j
@RequiredArgsConstructor
public class DonorController {
    private final DonorService donorService;

    @PostMapping("/login")
    public HttpResponseEntity donorLogin(@RequestBody Donor donor, HttpServletResponse response) {
        // Login by ID
        List<Donor> userList_id = donorService.query()
                .eq("id", eightDigitStringToUUID(donor.getId()))
                .eq("password", donor.getPassword()).list();

        // Login by Tel
        List<Donor> userList_tel = donorService.query()
                .eq("tel", donor.getTel())
                .eq("password", donor.getPassword()).list();
        userList_id.addAll(userList_tel);
        if (userList_id.isEmpty()) {
            return HttpResponseEntity.response(false, "登录", null);
        } else {
            Donor loggedInUser = userList_id.get(0);
            Cookie cookie = new Cookie("id", loggedInUser.getId());
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return HttpResponseEntity.response(true, "登录", loggedInUser);
        }
    }

    @PostMapping("/signUp")
    public HttpResponseEntity addDonorInfo(@RequestBody Donor donor) {
        List<Donor> userList = donorService.query()
                .eq("id", donor.getId()).list(); 
        if (userList.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String returnId = uuidToEightDigitString(uuid);
            donor.setId(uuid);
            donorService.save(donor);
            donor.setId(returnId);
            return HttpResponseEntity.response(true, "创建", donor);
        } else
            return HttpResponseEntity.response(false, "创建", null);
    }

    @PostMapping("/modify")
    public HttpResponseEntity modifyUserinfo(@RequestBody Donor donor) {
        boolean success = donorService.updateById(donor);
        return HttpResponseEntity.response(success, "修改", null);
    }

    @PostMapping("/delete")
    public HttpResponseEntity deleteUserById(@RequestBody Donor donor) {
        boolean success = donorService.removeById(donor);
        return HttpResponseEntity.response(success, "删除", null);
    }

    private static String uuidToEightDigitString(String uuid) {
        // 将UUID转换为BigInteger
        BigInteger bigInteger = new BigInteger(uuid.replace("-", ""), 16);
        // 将BigInteger转换为8位数字字符串
        return String.format("%08d", bigInteger.mod(BigInteger.valueOf(100000000L)));
    }

    private static String eightDigitStringToUUID(String eightDigitString) {
        // 将8位数字字符串转换为BigInteger
        BigInteger bigInteger = new BigInteger(eightDigitString);

        // 将BigInteger转换为UUID
        String uuidString = String.format("%032x", bigInteger);
        return UUID.fromString(
                uuidString.substring(0, 8) + "-" +
                        uuidString.substring(8, 12) + "-" +
                        uuidString.substring(12, 16) + "-" +
                        uuidString.substring(16, 20) + "-" +
                        uuidString.substring(20)).toString();
    }

}

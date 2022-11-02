package com.kh.fitness.api;

import com.kh.fitness.service.ChangePasswordService;
import com.kh.fitness.api.util.PathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathUtils.API_V1)
@RequiredArgsConstructor
public class ChangePasswordController {
    ChangePasswordService changePasswordService;

    @PutMapping("/change-password")
    public void changePassword(@RequestBody String password, @AuthenticationPrincipal UserDetails userDetails) {
        changePasswordService.changePassword(password, userDetails.getUsername());
    }
}

package com.kms.mygram.controller.api;

import com.kms.mygram.auth.Principal;
import com.kms.mygram.domain.User;
import com.kms.mygram.dto.ProfileEditDto;
import com.kms.mygram.exception.ValidException;
import com.kms.mygram.service.UserService;
import com.kms.mygram.validator.ProfileEditValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final ProfileEditValidator profileEditValidator;

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(
            @AuthenticationPrincipal Principal principal,
            @Valid ProfileEditDto profileEditDto,
            BindingResult bindingResult
    ){
        profileEditValidator.validate(profileEditDto, bindingResult);
        if (bindingResult.hasErrors()){
            StringBuffer buffer = new StringBuffer("Validation Fail\n");
            for(FieldError error : bindingResult.getFieldErrors())
                buffer.append(error.getDefaultMessage() + "\n");
            throw new ValidException(buffer.toString());
        }
        User userEntity = userService.updateUser(principal.getUser().getUserId(), profileEditDto);
        principal.setUser(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}
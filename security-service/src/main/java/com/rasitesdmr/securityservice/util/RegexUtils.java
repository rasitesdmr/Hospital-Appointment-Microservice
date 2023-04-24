package com.rasitesdmr.securityservice.util;


import com.rasitesdmr.securityservice.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
@Slf4j
public class RegexUtils {

    public String identityNumberNo(String identityNumber) {
        String regex = "^(?!0)\\d{11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(identityNumber);
        if (matcher.find()) {
            return identityNumber;
        } else {
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            log.error("[Metot: {}] - Geçersiz kimlik numarası : {}", methodName, identityNumber);
            throw new BadRequestException("TC numarası 11 haneli olmalı. Sıfır ile başlamamalı. Harf içermemeli. Özel Karakter içermemeli.");
        }
    }


    public String passwordRegex(String password) {

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (matcher.find()) {
            return password;
        } else {
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            log.error("[Metot: {}] - Geçersiz şifre: {}", methodName, password);
            throw new BadRequestException("Geçersiz şifre . Lütfen şu bilgileri göz önüne alarak giriniz. En az bir küçük harf içermelidir, En az bir büyük harf içermelidir , En az bir sayı içermelidir , En az bir özel karakter içermelidir [@$!%?&] ,En az sekiz karakter olmalıdır ");
        }

    }

    public String emailRegex(String email) {
        String regex = "^(?<!\\?)[a-zA-Z0-9]+[._a-zA-Z0-9]*[a-zA-Z]*@[a-zA-Z0-9]{2,}.[a-zA-Z.]{2,3}[?<!\\com]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            return email;
        } else {
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            log.error("[Metot: {}] - Geçersiz email: {}", methodName, email);
            throw new BadRequestException("Geçersiz email. Lütfen şu bilgileri göz önüne alarak giriniz . Nokta kullanılabilir onun dışında özel karakter kullanmayın. ");

        }
    }
}

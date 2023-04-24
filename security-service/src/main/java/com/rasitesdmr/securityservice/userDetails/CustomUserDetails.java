package com.rasitesdmr.securityservice.userDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.model.Role;
import kafka.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String identityNumber;
    private final String password;
    private final List<Role> roles;

    public CustomUserDetails(User user) {
        this.identityNumber = user.getIdentityNumber();
        this.password = user.getPassword();
        this.roles = user.getRoleList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<Role> roleList = new ArrayList<>(roles);
        for (Object object : roleList) {
            Role role = mapper.convertValue(object, Role.class);
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;
    }

    /**
     * Kullanıcının şifresini döndürür.
     *
     * @return kullanıcının şifresi
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Kullanıcının kimlik numarasını döndürür.
     *
     * @return kullanıcının kimlik numarası
     */
    @Override
    public String getUsername() {
        return identityNumber;
    }

    /**
     * Kullanıcının hesabının süresinin dolup dolmadığını kontrol eder.
     *
     * @return true , çünkü hesap süresi dolmamış olarak kabul ediliyor.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Kullanıcının hesabının kilitli olup olmadığını kontrol eder.
     *
     * @return true , çünkü hesap kilitli değil olarak kabul ediliyor.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Kullanıcının kimlik bilgilerinin süresinin dolup dolmadığını kontrol eder.
     *
     * @return true , çünkü kimlik bilgilerinin süresi dolmamış olarak kabul ediliyor.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * Kullanıcının hesabının etkin olup olmadığını kontrol eder.
     *
     * @return true , çünkü hesap etkin olarak kabul ediliyor.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}

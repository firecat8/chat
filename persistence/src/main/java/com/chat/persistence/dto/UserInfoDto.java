package com.chat.persistence.dto;

import com.chat.domain.UserInfo;

/**
 *
 * @author gdimitrova
 */
public class UserInfoDto extends AbstractDto implements UserInfo{

   private String firstname;

   private String lastname;

   private String email;

   private String phone;

   private String city;

   @Override
    public String getFirstname() {
        return firstname;
    }

   @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

   @Override
    public String getLastname() {
        return lastname;
    }

   @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

   @Override
    public String getEmail() {
        return email;
    }

   @Override
    public void setEmail(String email) {
        this.email = email;
    }

   @Override
    public String getPhone() {
        return phone;
    }

   @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

   @Override
    public String getCity() {
        return city;
    }

   @Override
    public void setCity(String city) {
        this.city = city;
    }

}

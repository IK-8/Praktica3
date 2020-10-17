package com.example.kozlovskiy;

import java.util.ArrayList;
import java.util.List;

public class UserStaticInfo {

    /**
     * ключ параметра login
     */
    public  final static String LOGIN = "login";
    /**
     * ключ параметра позиция
     */
    public  final static String POSITION = "position";
    /**
     * "ключ" списка пользователей используемых в FireBase
     */
    public  final static String USERS_SIGN_IN_INFO = "UsersSignInInfo";
    /**
     * "ключ" списка профилей пользователей используемых в FireBase
     */
    public  final static String USERS_PROFILE_INFO = "UsersProfileInfo";
    /**
     * "ключ" пароля
     */
    public  final static String PASSWORD = "password";
    /**
     * "ключ" id профиля
     */
    public  final static String PROFILE_ID = "profileId";
    /**
     * "ключ" имени пользователя
     */
    public  final static String NAME = "name";
    /**
     * "ключ" возраста пользователя
     */
    public  final static String AGE = "age";
    /**
     * "ключ" статуса пользователя
     */
    public  final static String STATE = "state";
    /**
     * id пользователя
     */
    public static String profileId;

    /**
     * список пользователей
     */
   public static List<User> users = new ArrayList<>();
    public UserStaticInfo() {
        if(users.size()==0)
            AddUsersInList();
    }

    private void AddUsersInList() {
        users.add(new User("Иван","Я усталь",19));
        users.add(new User("Иван","Я усталь",19));
        users.add(new User("Иван","Я усталь",19));
        users.add(new User("Иван","Я усталь",19));
    }
}

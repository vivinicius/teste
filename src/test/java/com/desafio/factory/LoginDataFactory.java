package com.desafio.factory;

import com.desafio.pojo.Login;

public class LoginDataFactory {

    public static Login fazerLogin(){
        Login login = new Login();
        login.setUsername("emilys");
        login.setPassword("emilyspass");
        return login;
    }

    public static Login fazerLoginSemSenha(){
        Login login = new Login();
        login.setUsername("emilys");
        login.setPassword("");
        return login;
    }

}

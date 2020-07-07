package Utils;

import Services.Hibernate.DAO.UserDaoImpl;
import Services.Hibernate.entity.LoginInfo;

public class AccountValidation {
    private String username;
    private String password;


    public AccountValidation(String username, String pw) {
        this.username = username;
        this.password = pw;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validate() {
        //...
        UserDaoImpl userDao = new UserDaoImpl();
        LoginInfo u = userDao.getUserByUsername(username);
        if (Crypto.checkpw(password, u.getHashpw())) {
            return true;
        }
        return false;
    }
}

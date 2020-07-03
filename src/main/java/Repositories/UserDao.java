package Repositories;

import Services.Hibernate.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUser();
    User getUserByUsername(String username);
    void updatePassword(int user_id, char[] newpassword);
    void addUser(String username, String password, String email, int type);
    void deleteUser(int user_id);
}

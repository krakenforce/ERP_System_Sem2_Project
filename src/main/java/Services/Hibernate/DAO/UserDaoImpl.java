package Services.Hibernate.DAO;

import Repositories.UserDao;
import Services.Hibernate.entity.User;
import Services.Hibernate.utils.HibernateUtil;
import Utils.Crypto;
import Utils.Email;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {
    public UserDaoImpl() {
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();
        User u;

        try {
            s.beginTransaction();
            u = s.get(User.class, username);
            s.getTransaction().commit();
        }finally {
            s.close();
        }


        return u;
    }


    @Override
    public void updatePassword(int user_id, char[] newpassword) {

    }

    @Override
    public void addUser(String username, String password, String email, int type) {
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();

        try{
            s.beginTransaction();

            User u = new User(username, Crypto.hashpw(password), email, type);
            s.save(u);

            s.getTransaction().commit();

        }finally {
            s.close();
        }
    }

    @Override
    public void deleteUser(int user_id) {

    }

    public int resetPassword(String username, String email) {

        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();
        int rs = 0;
        try {
            s.beginTransaction();
            User u = s.get(User.class, username);

            if (u.getEmail().equals(email)) {
                String newPw = Crypto.generateRandomPassword(7);
                String newHashPw = Crypto.hashpw(newPw);
                u.setHashpw(newHashPw);

                String message = "your new password is: " +  newPw;

                Email emailUtil = new Email(System.getenv("MAIL_USER"), System.getenv("MAIL_PW"), message);
                emailUtil.sendMessageToEmail("Your new password", email);

            }
            s.getTransaction().commit();

        }finally {
            s.close();
        }

        return rs;




    }

}

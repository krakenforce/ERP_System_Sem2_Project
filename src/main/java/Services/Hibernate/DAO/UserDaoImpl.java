package Services.Hibernate.DAO;

import Repositories.UserDao;
import Services.Hibernate.entity.LoginInfo;
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
    public List<LoginInfo> getAllUser() {
        return null;
    }

    @Override
    public LoginInfo getUserByUsername(String username) {
        SessionFactory fac = HibernateUtil.getSessionFactory();
        Session s = fac.openSession();
        LoginInfo u;

        try {
            s.beginTransaction();
            u = s.get(LoginInfo.class, username);
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

            LoginInfo u = new LoginInfo(username, Crypto.hashpw(password), email, type);
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
            LoginInfo u = s.get(LoginInfo.class, username);

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

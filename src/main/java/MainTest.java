import Services.Hibernate.DAO.LoginInfoDAO;
import Services.Hibernate.entity.LoginInfo;

public class MainTest {
    public static void main(String[] args) {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUsername("test");
        loginInfo.setEmail("test@gmail.com");
        loginInfo.setHashpw("adsfahsdfhuieruwrwebr");
        loginInfo.setType(1);

        LoginInfoDAO loginInfoDAO = new LoginInfoDAO();
        loginInfoDAO.saveLoginInfo(loginInfo);
    }
}

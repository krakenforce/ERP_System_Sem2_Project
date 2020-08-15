import Services.Hibernate.DAO.LoginInfoDAO;
import Services.Hibernate.DAO.SalesManDAO;
import Services.Hibernate.entity.LoginInfo;
import Services.Hibernate.entity.Salesman;

public class MainTest {
    public static void main(String[] args) {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUsername("test");
        loginInfo.setEmail("test@gmail.com");
        loginInfo.setHashpw("adsfahsdfhuieruwrwebr");

        LoginInfoDAO loginInfoDAO = new LoginInfoDAO();
        loginInfoDAO.saveLoginInfo(loginInfo);
    }
}

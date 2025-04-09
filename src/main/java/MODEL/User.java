package MODEL;

public class User {
    private String UserName;
    private String PassWord;
    private int Role_ID; // Thêm trường ROLE_ID để phân quyền


    public User(String userName, String passWord, int role_ID) {
        UserName = userName;
        PassWord = passWord;
        Role_ID = role_ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public int getRole_ID() {
        return Role_ID;
    }

    public void setRole_ID(int role_ID) {
        Role_ID = role_ID;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", Role_ID=" + Role_ID +
                '}';
    }
}

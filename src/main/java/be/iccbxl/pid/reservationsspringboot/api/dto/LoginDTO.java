package be.iccbxl.pid.reservationsspringboot.api.dto;


public class LoginDTO {
    private String login;
    private String password;
    
    // Constructeurs, getters et setters
    public LoginDTO() {}
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
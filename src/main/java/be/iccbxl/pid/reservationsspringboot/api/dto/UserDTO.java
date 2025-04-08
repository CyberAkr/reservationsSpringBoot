package be.iccbxl.pid.reservationsspringboot.api.dto;

public class UserDTO {
    private String login;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String langue;
    
    // Constructeur par défaut (NÉCESSAIRE pour la désérialisation JSON)
    public UserDTO() {}
    
    // Constructeur avec tous les champs
    public UserDTO(String login, String email, String firstname, String lastname, String password, String langue) {
        this.login = login;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.langue = langue;
    }
    
    // Getters et setters
    // ...

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return this.password;
    }    
    public void setPassword(String password) {
        this.password = password;
    }   }
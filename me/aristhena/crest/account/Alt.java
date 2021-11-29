package me.aristhena.crest.account;

public class Alt
{
    private String email;
    private String username;
    private String password;
    
    public Alt(final String email, final String name, final String pass) {
        this.email = email;
        this.username = name;
        this.password = pass;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public void setUsername(final String name) {
        this.username = name;
    }
    
    public void setPassword(final String pass) {
        this.password = pass;
    }
}

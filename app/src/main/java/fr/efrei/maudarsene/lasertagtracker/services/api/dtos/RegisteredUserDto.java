package fr.efrei.maudarsene.lasertagtracker.services.api.dtos;

public class RegisteredUserDto
{
    public String id ;
    public String username;

    public RegisteredUserDto(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public RegisteredUserDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
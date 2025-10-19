package za.co.carhire.dto.authenticationDTO;
/* User.java

     User domain/authentication class

     Author: Bonga Velem

     Student Number: 220052379

     */
public record AuthenticationResponse(
        String token,
        UserDTO user,
        String tokenType
) {
    public AuthenticationResponse(String token, UserDTO user) {
        this(token, user, "Bearer");
    }
}

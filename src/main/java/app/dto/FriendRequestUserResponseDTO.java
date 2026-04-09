package app.dto;

import app.entities.Friendship;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestUserResponseDTO {
    String name;
    Long requestID;

    public FriendRequestUserResponseDTO(UserDTO user, Long requestID){
        this.name = user.getUsername();
        this.requestID = requestID;
    }
    public FriendRequestUserResponseDTO(){

    }
}


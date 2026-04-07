package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class PrivacySettings {
    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
    private boolean isProfilePublic;
    private boolean isPostsPublic;
    private boolean isFriendsListPublic;


    public PrivacySettings(boolean isProfilePublic, boolean isPostsPublic, boolean isFriendsListPublic) {
        this.isProfilePublic = isProfilePublic;
        this.isPostsPublic = isPostsPublic;
        this.isFriendsListPublic = isFriendsListPublic;
    }


}

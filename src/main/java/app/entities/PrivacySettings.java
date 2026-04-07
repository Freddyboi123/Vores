package app.entities;

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
    private User users;
    private boolean isProfilePublic;
    private boolean isPostsPublic;
    private boolean isFriendsListPublic;


    public PrivacySettings(boolean isProfilePublic, boolean isPostsPublic, boolean isFriendsListPublic) {
        this.isProfilePublic = isProfilePublic;
        this.isPostsPublic = isPostsPublic;
        this.isFriendsListPublic = isFriendsListPublic;
    }


}

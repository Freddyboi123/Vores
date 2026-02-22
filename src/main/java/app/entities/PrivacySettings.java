package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class PrivacySettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "privacySettings")
    private User userId;
    private boolean isProfilePublic;
    private boolean isPostsPublic;
    private boolean isFriendsListPublic;


    public PrivacySettings(boolean isProfilePublic, boolean isPostsPublic, boolean isFriendsListPublic) {
        this.isProfilePublic = isProfilePublic;
        this.isPostsPublic = isPostsPublic;
        this.isFriendsListPublic = isFriendsListPublic;

    }
}

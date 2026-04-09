package app.dto;

import app.entities.Comment;
import app.entities.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class FullPostDTO {
    private PostResponseDTO post;
    private Set<CommentResponseDTO> allComments = new HashSet<>();

    public FullPostDTO(){}


    public FullPostDTO(Post post, Set<CommentDTO> comment){
        this.post = new PostResponseDTO(post);

        if(comment != null) {
            for (CommentDTO c : comment) {
                allComments.add(new CommentResponseDTO(c));
            }
        }
    }
}


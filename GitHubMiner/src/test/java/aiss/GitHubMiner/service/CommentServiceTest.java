package aiss.GitHubMiner.service;

import aiss.GitHubMiner.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService service;

    @Test
    @DisplayName("Prueba de Test de Comentarios")
    void getComments() {
        Comment comentario1 = service.getComment();
        System.out.println(comentario1);
    }

}

package guru.springframework.spring5webfluxrest.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 15:43
 */

@Data
@Document
public class Category {

    @Id
    private String id;

    private String description;
}

package guru.springframework.spring5webfluxrest.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 16:01
 */
@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Vendor {

    @Id
    private String id;

    private String firstName;
    private String lastName;
}

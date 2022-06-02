package pcmarket.demo.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {
    private Long id;
    private String name;
    private int price;
    private String album;
    private String singer;
    private int stockQuantity;
}

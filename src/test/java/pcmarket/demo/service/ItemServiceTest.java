package pcmarket.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pcmarket.demo.domain.Item;
import pcmarket.demo.domain.Member;
import pcmarket.demo.repository.ItemRepository;
import pcmarket.demo.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 등록() throws Exception {
        Item item1 = new Item();
        item1.setName("포카");
        item1.setAlbum("마지막");
        item1.setPrice(1000);
        item1.setSinger("정우");

        System.out.println("결과");
        System.out.println(itemRepository.findAll());




    }
}

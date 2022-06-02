package pcmarket.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pcmarket.demo.domain.Item;
import pcmarket.demo.domain.Member;
import pcmarket.demo.domain.Order;
import pcmarket.demo.domain.OrderStatus;
import pcmarket.demo.exception.NotEnoughStockException;
import pcmarket.demo.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        Member member = createMember();
        Item item = createItem("nct pc", 1000, "2집", "jw", 2);
        int orderCount = 1;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        org.junit.Assert.assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        org.junit.Assert.assertEquals("상품주문시 종류 상태 정확", 1, getOrder.getOrderItems().size());
        org.junit.Assert.assertEquals("상품주문시 주문 가격", 1000*1, getOrder.getTotalPrice());
        org.junit.Assert.assertEquals("상품주문시 재고", 2-1, item.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
//    @Test
    public void 상품주문_재고수량초과() throws Exception {
        Member member = createMember();
        Item item = createItem("nct pc", 1000, "2집", "jw", 2);
        int orderCount = 5;
        orderService.order(member.getId(), item.getId(), orderCount);
        fail("재고 예외");
    }

    @Test
    public void 주문취소() {
        Member member = createMember();
        Item item = createItem("nct pc", 1000, "2집", "jw", 2);
        int orderCount = 1;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);
        org.junit.Assert.assertEquals("주문 취소시 상태 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        org.junit.Assert.assertEquals("주문 취소 상품 재고 증가", 2, item.getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setPassword("1234");
        em.persist(member);
        return member;
    }

    private Item createItem(String name, int price, String album, String singer, int stockQuantity) {
        Item card = new Item();
        card.setName(name);
        card.setPrice(price);
        card.setAlbum(album);
        card.setSinger(singer);
        card.setStockQuantity(stockQuantity);
        em.persist(card);
        return card;
    }
}

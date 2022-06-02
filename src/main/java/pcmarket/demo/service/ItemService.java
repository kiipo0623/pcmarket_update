package pcmarket.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcmarket.demo.domain.Item;
import pcmarket.demo.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    void updateItem(Long itemId, Item param) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setSinger(param.getSinger());
        findItem.setAlbum(param.getAlbum());
        findItem.setStockQuantity(param.getStockQuantity());
        // 영속 상태이기 때문에 여기서 커밋되는 것. flush 날림(바뀐 것 업데이트)
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public void addStock() {

    }
}

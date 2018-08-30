package com.africanjesus.fortnitepal.repositories;

import com.africanjesus.fortnitepal.ItemType;
import com.africanjesus.fortnitepal.RarityType;
import com.africanjesus.fortnitepal.ReleaseStatus;
import com.africanjesus.fortnitepal.model.Item;
import com.africanjesus.fortnitepal.services.ItemServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ItemDataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ItemDataLoader.class);

    private final ItemServiceImpl itemServiceImpl;

    @Autowired
    public ItemDataLoader(ItemServiceImpl itemServiceImpl) {
        this.itemServiceImpl = itemServiceImpl;
    }


    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Item Data ");
        String path = "/images/";
        //static/images/test.png
        Item item1 = new Item("Raptor","Royale Air Force Test Pilot", RarityType.LEGENDARY, path + "test.png", null, null, null, 2000, 0, null, ItemType.OUTFIT, ReleaseStatus.RELEASED);
        itemServiceImpl.save(item1);
    }

}

package com.africanjesus.fortnitepal.repositories.dataloaders;

import com.africanjesus.fortnitepal.ItemType;
import com.africanjesus.fortnitepal.RarityType;
import com.africanjesus.fortnitepal.ReleaseStatus;
import com.africanjesus.fortnitepal.model.Item;
import com.africanjesus.fortnitepal.model.Obtained;
import com.africanjesus.fortnitepal.services.ItemServiceImpl;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
        String url = "http://localhost:8080/images";
        String[] itemInfo;
        Obtained obtained;
        Item item;
        List<String> images;
        CSVReader reader = new CSVReader(new FileReader(new File("").getAbsolutePath() + "/fortnitepal/src/main/resources/static/fortniteitems.csv"));

        while((itemInfo = reader.readNext()) != null){
            item = new Item();
            obtained  = new Obtained();
            for(int i = 0; i < 1; i++){
                item.setName(itemInfo[0]);
                item.setDesc(itemInfo[1]);
                item.setRarityType(determineRarityTypeByString(itemInfo[2]));

                images = new ArrayList<>();
                if(itemInfo[13].equalsIgnoreCase("outfit")){
                    images.add(url + "/outfit/" + itemInfo[3] + ".png");
                    images.add(url + "/outfit/" + itemInfo[3] + "-full.png");
                    images.add(url + "/outfit/" + itemInfo[3] + "-feature.png");
                }else if(itemInfo[13].equalsIgnoreCase("backbling")){
                    images.add(url + "/backbling/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("emoticon")){
                    images.add(url + "/emoticon/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("glider")){
                    images.add(url + "/glider/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("harvestingtool")){
                    images.add(url + "/harvestingtool/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("spray")){
                    images.add(url + "/spray/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("contrail")){
                    images.add(url + "/contrail/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("toy")){
                    images.add(url + "/toy/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("loadingscreen")){
                    images.add(url + "/loadingscreen/" + itemInfo[3] + ".png");
                }else if(itemInfo[13].equalsIgnoreCase("dance")){
                    images.add(url + "/dance/" + itemInfo[3] + ".png");
                }
                item.setImages(images);

                obtained.setSeason(Integer.parseInt(itemInfo[4]));
                obtained.setTier(Integer.parseInt(itemInfo[5]));
                obtained.setIncludedInName(itemInfo[6]);
                obtained.setPromo(itemInfo[7]);
                obtained.setVbuckPrice(Integer.parseInt(itemInfo[8]));
                obtained.setPrice(Double.parseDouble(itemInfo[9]));
                obtained.setChallenege(itemInfo[10]);
                item.setObtained(obtained);

                item.setStyleSet(itemInfo[11]);
                item.setSet(itemInfo[12]);
                item.setItemType(determineItemTypeByString(itemInfo[13]));
                item.setStatus(ReleaseStatus.valueOf(itemInfo[14]));
            }
            itemServiceImpl.save(item);
        }
        logger.info("Finished Item Data ");

    }
    private RarityType determineRarityTypeByString(String typeString){

        if(typeString.equalsIgnoreCase(RarityType.LEGENDARY.name())){
            return RarityType.LEGENDARY;
        }else if(typeString.equalsIgnoreCase(RarityType.EPIC.name())){
            return RarityType.EPIC;
        }else if(typeString.equalsIgnoreCase(RarityType.RARE.name())){
            return RarityType.RARE;
        }else if(typeString.equalsIgnoreCase(RarityType.COMMON.name())){
            return RarityType.COMMON;
        }else{
            return RarityType.UNCOMMON;
        }
    }

    private ItemType determineItemTypeByString(String typeString){
        if(typeString.equalsIgnoreCase(ItemType.BACKBLING.name())){
            return ItemType.BACKBLING;
        }else if(typeString.equalsIgnoreCase(ItemType.CONTRAIL.name())){
            return ItemType.CONTRAIL;
        }else if(typeString.equalsIgnoreCase(ItemType.SPRAY.name())){
            return ItemType.SPRAY;
        }else if(typeString.equalsIgnoreCase(ItemType.OUTFIT.name())){
            return ItemType.OUTFIT;
        }else if(typeString.equalsIgnoreCase(ItemType.HARVESTINGTOOL.name())){
            return ItemType.HARVESTINGTOOL;
        }else if(typeString.equalsIgnoreCase(ItemType.DANCE.name())){
            return ItemType.DANCE;
        }else if(typeString.equalsIgnoreCase(ItemType.EMOTICON.name())){
            return ItemType.EMOTICON;
        }else if(typeString.equalsIgnoreCase(ItemType.LOADINGSCREEN.name())){
            return ItemType.LOADINGSCREEN;
        }else if(typeString.equalsIgnoreCase(ItemType.GLIDER.name())){
            return ItemType.GLIDER;
        }else{
            return ItemType.TOY;
        }
    }

}

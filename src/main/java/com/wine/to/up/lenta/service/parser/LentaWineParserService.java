package com.wine.to.up.lenta.service.parser;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class LentaWineParserService {

    public List<Optional<ProductDTO>> parseWineList(ParserRsp wineList) {

        List<Optional<ProductDTO>> productDTOList = new ArrayList<>();

        List<JSONObject> productList = wineList.getWineList()
                .stream()
                .map(Optional::get)
                .collect(Collectors.toList());

        for (JSONObject jsonObject : productList) {
            productDTOList.add(getProductDTO(jsonObject));
        }
        return productDTOList;
    }

    private Optional<ProductDTO> getProductDTO(JSONObject jsonObject) {
        return Optional.of(ProductDTO.builder()
                .brand(jsonObject.getString("wineName"))
                .country(jsonObject.getString("wineCountry"))
                .image(jsonObject.getString("wineImage"))
                .rating(Float.parseFloat(jsonObject.getString("wineRating")))
                .discount(Integer.parseInt(jsonObject.getString("wineDiscountPercentage")
                        .replace("%","")))
                .price(Float.parseFloat(jsonObject.getString("winePriceDiscount")
                        .replace(" ₽","")
                        .replace(" ",".")))
                .oldPrice(Float.parseFloat(jsonObject.getString("winePriceNormal")
                        .replace(" ₽","")
                        .replace(" ",".")))
                .build());
    }


//        Iterator wine_iterator = wineList.iterator();
//
//        FileWriter writer = new FileWriter("file1.txt", true);
//
//        while (wine_iterator.hasNext()) {
//
//            JSONObject next_wine = (JSONObject) wine_iterator.next();
//            if (next_wine.has("wineTitle") && !next_wine.getString("wineTitle").isBlank()) {
//                next_wine.keySet().forEach(keyStr ->
//                {
//                    Object keyValue = next_wine.get(keyStr);
////                    System.out.println("\uD83C\uDF77" + keyStr + " \uD83E\uDD74 " + keyValue);
//                    try {
//                        writer.write("\uD83C\uDF77" + keyStr + " \uD83E\uDD74 " + keyValue + "\n");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//                writer.write(next_wine.toString());
//                System.out.print("\n\n\n");
//            }
//        }
}


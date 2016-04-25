package com.gyz.androiddevelope.view.cardslidepanel;

/**
 * 卡片数据装载对象
 *
 * @author xmuSistone
 */
public class CardDataItem {

    public CardDataItem(String imagePath) {
        this.imagePath = imagePath;
    }
    public CardDataItem( ) {
    }

    public String imagePath;
    public  String userName;
    public  int likeNum;
    public int imageNum;
}

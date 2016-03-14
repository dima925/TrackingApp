package com.xs.android.tesseract.helper;

/**
 * Created by Dima Maykov on 3/12/2016.
 */
public class MessageItemData {
    private String title;
    private String content;
    private String datetime;
    private int imageUrl;

    public MessageItemData(String title,String content, String datetime)
    {

        this.title = title;
        this.content = content;
        this.datetime = datetime;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getDatetime() {
        return this.datetime;
    }


}

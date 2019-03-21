package com.example.mason_map.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.example.mason_map.model.item.Item;

@Root(name = "channel", strict = false)
public class Channel implements Serializable {

    @Element(required = false, name = "pubDate")
    private String pubDate;

    @ElementList(inline = true, entry = "item", name = "item")
    private List<Item> items;

    //Getter Functions:

    public String getPubDate() {
        return pubDate;
    }

    public List<Item> getItems() {
        return items;
    }

    //Setter Functions::
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Channel: \n [Items: " + this.items.toString() + "]";
    }
}


package com.example.mason_map.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.example.mason_map.model.item.Item;

@Root(name = "rss", strict = false)
public class RSS implements Serializable{

    @Element(name = "channel")
    private Channel channel;

    //Getter Functions:
    public Channel getChannel() {
        return channel;
    }
    public List<Item> getItems() {
        return channel.getItems();
    }

    //Setter Functions:
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return this.channel.toString();
    }
}

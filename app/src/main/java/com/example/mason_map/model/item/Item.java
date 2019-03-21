package com.example.mason_map.model.item;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Item implements Serializable {

    @Element(required = false, name = "title")
    private String title;

    @Element(required = false, name = "link")
    private String link;

    @Element(required = false, name = "enclosure")
    private String enclosure;

    @Element(required = false, name = "description")
    private String description;

    @ElementList(inline = true, required = false, name = "category")
    private List<String> category;

    @Element(required = false, name = "pubDate")
    private String pubDate;

    @Element(required = false, name = "start")
    private String start;

    @Element(required = false, name = "end")
    private String end;

    @Element(required = false, name = "location")
    private String location;

    @Element(required = false, name = "status")
    private String status;

    @Element(required = false, name = "author")
    private String author;

    //@Element(required = false, name = "host")
    @Path("host")
    @Text(required=false)
    private String host;

    //Constructor:
    public Item(){

    }

    public Item(String title, String link, String enclosure, String description, List<String> category, String pubDate, String start, String end, String location, String status, String author, String host) {
        this.title = title;
        this.link = link;
        this.enclosure = enclosure;
        this.description = description;
        this.category = category;
        this.pubDate = pubDate;
        this.start = start;
        this.end = end;
        this.location = location;
        this.status = status;
        this.author = author;
        this.host = host;
    }

    //Getter Functions:
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategory() {
        return category;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getAuthor() {
        return author;
    }

    public String getHost() {
        return host;
    }

    //Setter Functions:
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Item: { " +
                "title = '" + this.title + "' " +
                "description = '" + this.description + "' " +
                "location = '" + this.location + "' " +
                "start = '" + this.start + "' " +
                "end = '" + this.end + "' " +
                "}\n";
    }
}


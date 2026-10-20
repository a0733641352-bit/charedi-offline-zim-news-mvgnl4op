package com.generated.app;

public class NewsArticle {
    private final String id;
    private final String sourceName;
    private final String sourceUrl;
    private final String category;
    private final String title;
    private final String date;
    private final String contentHtml;
    private final String author;
    private final boolean hasVideo;
    private final int readTimeMinutes;

    public NewsArticle(String id, String sourceName, String sourceUrl, String category,
                       String title, String date, String contentHtml, String author,
                       boolean hasVideo, int readTimeMinutes) {
        this.id = id;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.category = category;
        this.title = title;
        this.date = date;
        this.contentHtml = contentHtml;
        this.author = author;
        this.hasVideo = hasVideo;
        this.readTimeMinutes = readTimeMinutes;
    }

    public String getId() { return id; }
    public String getSourceName() { return sourceName; }
    public String getSourceUrl() { return sourceUrl; }
    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getContentHtml() { return contentHtml; }
    public String getAuthor() { return author; }
    public boolean hasVideo() { return hasVideo; }
    public int getReadTimeMinutes() { return readTimeMinutes; }
}

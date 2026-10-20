package com.generated.app;

import java.util.List;

public class ZimPackage {
    private final String id;
    private final String fileName;
    private final String displayName;
    private final String createdAt;
    private final String fileSize;
    private final int articleCount;
    private final boolean includesVideo;
    private final List<String> sourceList;
    private final String rawHtmlContent;

    public ZimPackage(String id, String fileName, String displayName, String createdAt,
                      String fileSize, int articleCount, boolean includesVideo,
                      List<String> sourceList, String rawHtmlContent) {
        this.id = id;
        this.fileName = fileName;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.fileSize = fileSize;
        this.articleCount = articleCount;
        this.includesVideo = includesVideo;
        this.sourceList = sourceList;
        this.rawHtmlContent = rawHtmlContent;
    }

    public String getId() { return id; }
    public String getFileName() { return fileName; }
    public String getDisplayName() { return displayName; }
    public String getCreatedAt() { return createdAt; }
    public String getFileSize() { return fileSize; }
    public int getArticleCount() { return articleCount; }
    public boolean isIncludesVideo() { return includesVideo; }
    public List<String> getSourceList() { return sourceList; }
    public String getRawHtmlContent() { return rawHtmlContent; }
}

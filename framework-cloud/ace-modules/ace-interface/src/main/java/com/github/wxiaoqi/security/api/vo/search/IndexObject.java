package com.github.wxiaoqi.security.api.vo.search;


import java.io.Serializable;

/**
 * Description:索引对象
 *
 * @author ace
 **/
public class IndexObject implements Comparable<IndexObject>, Serializable {

    private static final long serialVersionUID = 1468212500895591433L;

    private String id;

    private String title;

    private String keywords;

    private String descripton;

    private String postDate;

    private String url;
    /*相似度*/
    private float score;

    public IndexObject() {
        super();
    }

    public IndexObject(String _id, String _keywords, String _descripton, String _postDate, float _score) {
        super();
        this.id = _id;
        this.keywords = _keywords;
        this.score = _score;
        this.descripton = _descripton;
        this.postDate = _postDate;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int compareTo(IndexObject o) {
        if (this.score < o.getScore()) {
            return 1;
        } else if (this.score > o.getScore()) {
            return -1;
        }
        return 0;
    }


}

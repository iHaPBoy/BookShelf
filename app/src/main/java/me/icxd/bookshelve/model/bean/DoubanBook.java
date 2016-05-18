package me.icxd.bookshelve.model.bean;

import java.util.List;

/**
 * Created by HaPBoy on 5/14/16.
 */
public class DoubanBook {

    /**
     * rating : {"max":10,"numRaters":8,"average":"0.0","min":0}
     * subtitle :
     * author : ["杨超"]
     * pubdate : 2009-10
     * tags : [{"count":1,"name":"2009","title":"2009"},{"count":1,"name":"中国","title":"中国"},{"count":1,"name":"中国文化","title":"中国文化"},{"count":1,"name":"文化研究","title":"文化研究"},{"count":1,"name":"民俗","title":"民俗"},{"count":1,"name":"社会","title":"社会"}]
     * origin_title :
     * image : http://img3.douban.com/mpic/s6633520.jpg
     * binding :
     * translator : []
     * catalog :
     * pages : 310
     * images : {"small":"http://img3.douban.com/spic/s6633520.jpg","large":"http://img3.douban.com/lpic/s6633520.jpg","medium":"http://img3.douban.com/mpic/s6633520.jpg"}
     * alt : http://book.douban.com/subject/4021776/
     * id : 4021776
     * publisher :
     * isbn10 : 7538727469
     * isbn13 : 9787538727463
     * title : 中国民俗
     * url : http://api.douban.com/v2/book/4021776
     * alt_title :
     * author_intro :
     * summary : 《中国民俗》以人文的视角、故事体文本、精致的图片，将博大精深的学术文化转为轻松愉悦的普及读本，一改过去生硬乏味的叙述方式，不追求完整全面，只追求要点常识，不追求专业深奥，只追求好读易懂，让您怀着轻松的心情，用最短的时间领略中国文化引人入胜的独特魅力。
     中国自古就有“入国问禁，入乡随俗”的民间传统，民俗是最贴近身心和生活、并世世代代锤炼和传承的文化传统。《中国民俗》全景式地展现了它的形成、延展与传承的演进过程，从中华民族的饮食文化、服饰文化、传统建筑、传统节日、婚庆礼仪、信仰禁忌以及民间艺术等诸多层面，以生动的语言、精美的图片、丰富的人文蕴含，给我们展开了一幅精彩生动的民俗画卷。
     * price : 40.00元
     */

    /**
     * max : 10
     * numRaters : 8
     * average : 0.0
     * min : 0
     */
    private RatingEntity rating;

    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    /**
     * small : http://img3.douban.com/spic/s6633520.jpg
     * large : http://img3.douban.com/lpic/s6633520.jpg
     * medium : http://img3.douban.com/mpic/s6633520.jpg
     */
    private ImagesEntity images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private String price;
    private List<String> author;
    /**
     * count : 1
     * name : 2009
     * title : 2009
     */
    private List<TagsEntity> tags;
    private List<?> translator;

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }

    public void setTranslator(List<?> translator) {
        this.translator = translator;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public String getImage() {
        return image;
    }

    public String getBinding() {
        return binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getPages() {
        return pages;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public String getPrice() {
        return price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public List<TagsEntity> getTags() {
        return tags;
    }

    public List<?> getTranslator() {
        return translator;
    }

    public static class RatingEntity {
        private int max;
        private int numRaters;
        private String average;
        private int min;

        public void setMax(int max) {
            this.max = max;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public String getAverage() {
            return average;
        }

        public int getMin() {
            return min;
        }
    }

    public static class ImagesEntity {
        private String small;
        private String large;
        private String medium;

        public void setSmall(String small) {
            this.small = small;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }
    }

    public static class TagsEntity {
        private int count;
        private String name;
        private String title;

        public void setCount(int count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCount() {
            return count;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }
    }
}

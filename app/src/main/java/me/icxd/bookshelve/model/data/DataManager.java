package me.icxd.bookshelve.model.data;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import me.icxd.bookshelve.app.MyApplication;
import me.icxd.bookshelve.config.DoubanAPI;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.model.bean.DoubanBook;

/**
 * Created by HaPBoy on 5/14/16.
 */
public class DataManager {

    // API: Get BookInfo Via ISBN
    public static void getBookInfoFromISBN(String isbn, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = DoubanAPI.bookISBNApi + isbn;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, listener, errorListener);

        MyApplication.getRequestQueue().add(jsObjRequest);
    }

    // API: Get BookSearch
    public static void getBookSearch(String bookName, int start, Response.Listener listener, Response.ErrorListener errorListener) {
        // URLencode
        try {
            bookName = URLEncoder.encode(bookName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(DoubanAPI.bookSearchApi, bookName, start);
        Log.i("API", url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, listener, errorListener);

        MyApplication.getRequestQueue().add(jsObjRequest);
    }

    // JSONObject -> DoubanBook
    public static DoubanBook jsonObject2DoubanBook(JSONObject book) {
        DoubanBook bookData = new DoubanBook();
        try {

            // rating
            DoubanBook.RatingEntity ratingEntity = new DoubanBook.RatingEntity();
            ratingEntity.setMax(book.getJSONObject("rating").getInt("max"));
            ratingEntity.setNumRaters(book.getJSONObject("rating").getInt("numRaters"));
            ratingEntity.setAverage(book.getJSONObject("rating").getString("average"));
            ratingEntity.setMin(book.getJSONObject("rating").getInt("min"));
            bookData.setRating(ratingEntity);

            bookData.setSubtitle(book.getString("subtitle"));
            bookData.setPubdate(book.getString("pubdate"));
            bookData.setOrigin_title(book.getString("origin_title"));
            bookData.setImage(book.getString("image"));
            bookData.setBinding(book.getString("binding"));
            bookData.setCatalog(book.getString("catalog"));
            bookData.setPages(book.getString("pages"));

            // images
            DoubanBook.ImagesEntity imagesEntity = new DoubanBook.ImagesEntity();
            imagesEntity.setSmall(book.getJSONObject("images").getString("small"));
            imagesEntity.setMedium(book.getJSONObject("images").getString("medium"));
            imagesEntity.setLarge(book.getJSONObject("images").getString("large"));
            bookData.setImages(imagesEntity);

            bookData.setAlt(book.getString("alt"));
            bookData.setId(book.getString("id"));
            bookData.setPublisher(book.getString("publisher"));
            bookData.setIsbn10(book.getString("isbn10"));
            bookData.setIsbn13(book.has("isbn13") ? book.getString("isbn13") : "");
            bookData.setTitle(book.getString("title"));
            bookData.setUrl(book.getString("url"));
            bookData.setAlt_title(book.getString("alt_title"));
            bookData.setAuthor_intro(book.getString("author_intro"));
            bookData.setSummary(book.getString("summary"));
            bookData.setPrice(book.getString("price"));

            // author
            List<String> author = new ArrayList<>();
            JSONArray authors = book.getJSONArray("author");
            for (int i = 0; i < authors.length(); i++) {
                author.add(authors.getString(i));
            }
            bookData.setAuthor(author);

            // translators
            List<String> translator = new ArrayList<>();
            JSONArray translators = book.getJSONArray("translator");
            for (int i = 0; i < translators.length(); i++) {
                translator.add(translators.getString(i));
            }
            bookData.setTranslator(translator);

            // tags
            List<DoubanBook.TagsEntity> tags = new ArrayList<>();
            JSONArray tagsJson = book.getJSONArray("tags");
            for (int i = 0; i < tagsJson.length(); i++) {
                DoubanBook.TagsEntity tagsEntity = new DoubanBook.TagsEntity();
                tagsEntity.setCount(tagsJson.getJSONObject(i).getInt("count"));
                tagsEntity.setName(tagsJson.getJSONObject(i).getString("name"));
                tagsEntity.setTitle(tagsJson.getJSONObject(i).getString("title"));
                tags.add(tagsEntity);
            }
            bookData.setTags(tags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookData;
    }

    // DoubanBook -> Book
    public static Book doubanBook2Book(DoubanBook book) {
        Book book_db = new Book();

        // author
        StringBuilder authorString = new StringBuilder();
        if (book.getAuthor() != null) {
            for (int i = 0; i < book.getAuthor().size(); i++) {
                if (i < book.getAuthor().size() - 1) {
                    authorString.append(book.getAuthor().get(i).toString());
                    authorString.append("、");
                } else {
                    authorString.append(book.getAuthor().get(i).toString());
                }
            }
        }
        book_db.setAuthor(authorString.toString());

        // translators
        StringBuilder translatorString = new StringBuilder();
        if (book.getTranslator() != null) {
            for (int i = 0; i < book.getTranslator().size(); i++) {
                if (i < book.getTranslator().size() - 1) {
                    translatorString.append(book.getTranslator().get(i).toString());
                    translatorString.append("、");
                } else {
                    translatorString.append(book.getTranslator().get(i).toString());
                }
            }
        }
        book_db.setTranslator(translatorString.toString());

        book_db.setAuthor_intro(book.getAuthor_intro());
        book_db.setImage(book.getImages().getLarge());
        book_db.setPages(book.getPages());
        book_db.setPrice(book.getPrice());
        book_db.setPubdate(book.getPubdate());
        book_db.setSubtitle(book.getSubtitle());
        book_db.setSummary(book.getSummary());
        book_db.setTitle(book.getTitle());
        book_db.setUrl(book.getUrl());
        book_db.setPublisher(book.getPublisher());
        book_db.setIsbn13(book.getIsbn13().isEmpty() ? book.getIsbn10() : book.getIsbn13());
        book_db.setAverage(book.getRating().getAverage());
        book_db.setFavourite(false);
        book_db.setNote("");
        book_db.setNote_date("");

        // tags
        if (book.getTags() != null) {
            if (book.getTags().size() >= 3) {
                book_db.setTag1(book.getTags().get(0).getName());
                book_db.setTag2(book.getTags().get(1).getName());
                book_db.setTag3(book.getTags().get(2).getName());
            } else {
                switch (book.getTags().size()) {
                    case 0:
                        break;
                    case 1:
                        book_db.setTag1(book.getTags().get(0).getName());
                        break;
                    case 2:
                        book_db.setTag1(book.getTags().get(0).getName());
                        book_db.setTag2(book.getTags().get(1).getName());
                        break;
                }
            }
        }
        return book_db;
    }
}

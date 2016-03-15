package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-11 15:10
 */
public class LatestNewsBean {

    public String date;
    public List<Story> stories;
    @SerializedName(value = "top_stories")
    public List<TopStory> topStories;


    public class TopStory{

        private int id;
        private String title;
        private String ga_prefix;
        private String image;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }


}

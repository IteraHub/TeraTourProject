
package com.iterahub.teratour.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class MarkerModel {


    @PrimaryKey(autoGenerate = true)
    public int PrimaryKey;

    @SerializedName("status")
    @Expose
    private Boolean status;
    @Ignore
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     */
    public MarkerModel() {
    }

    /**
     * @param status
     * @param data
     */
    public MarkerModel(Boolean status, Data data) {
        super();
        this.status = status;
        this.data = data;
    }
    public int getPrimaryKey() {
        return PrimaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        PrimaryKey = primaryKey;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Entity
    public class Data {

        @PrimaryKey
        @SerializedName("Id")
        @Expose
        public int id;
        @SerializedName("Titles")
        @Expose
        private String titles;
        @SerializedName("Description")
        @Expose
        private String description;
        @Ignore
        @SerializedName("images")
        @Expose
        private List<Image> images = null;

        /**
         * No args constructor for use in serialization
         */
        public Data() {
        }

        /**
         * @param id
         * @param description
         * @param images
         * @param titles
         */
        public Data(int id, String titles, String description, List<Image> images) {
            super();
            this.id = id;
            this.titles = titles;
            this.description = description;
            this.images = images;
        }

        public int getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitles() {
            return titles;
        }

        public void setTitles(String titles) {
            this.titles = titles;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        @Entity
        public class Image {

            @PrimaryKey
            @SerializedName("ImageId")
            @Expose
            public Integer imageId;
            @SerializedName("Markerid")
            @Expose
            private Integer markerid;
            @SerializedName("Imageurl")
            @Expose
            private String imageurl;

            /**
             * No args constructor for use in serialization
             */
            public Image() {
            }

            /**
             * @param markerid
             * @param imageId
             * @param imageurl
             */
            @Ignore
            public Image(Integer imageId, Integer markerid, String imageurl) {
                super();
                this.imageId = imageId;
                this.markerid = markerid;
                this.imageurl = imageurl;
            }

            public Integer getImageId() {
                return imageId;
            }

            public void setImageId(Integer imageId) {
                this.imageId = imageId;
            }

            public Integer getMarkerid() {
                return markerid;
            }

            public void setMarkerid(Integer markerid) {
                this.markerid = markerid;
            }

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

        }
    }
}




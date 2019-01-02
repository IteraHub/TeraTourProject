package com.iterahub.teratour.models;

/**
 * Created by ACER on 4/14/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class SlackMessage {

    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SlackMessage() {
    }

    /**
     *
     * @param attachments
     */
    public SlackMessage(List<Attachment> attachments) {
        super();
        this.attachments = attachments;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Parcel
    public static class Attachment {

        @SerializedName("fallback")
        @Expose
        private String fallback;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("pretext")
        @Expose
        private String pretext;
        @SerializedName("author_name")
        @Expose
        private String authorName;
        @SerializedName("author_link")
        @Expose
        private String authorLink;
        @SerializedName("author_icon")
        @Expose
        private String authorIcon;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("title_link")
        @Expose
        private String titleLink;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("fields")
        @Expose
        private List<Field> fields = null;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("thumb_url")
        @Expose
        private String thumbUrl;
        @SerializedName("footer")
        @Expose
        private String footer;
        @SerializedName("footer_icon")
        @Expose
        private String footerIcon;
        @SerializedName("ts")
        @Expose
        private Integer ts;

        /**
         * No args constructor for use in serialization
         *
         */
        public Attachment() {
        }

        /**
         *
         * @param authorIcon
         * @param text
         * @param ts
         * @param imageUrl
         * @param authorLink
         * @param footerIcon
         * @param footer
         * @param pretext
         * @param fallback
         * @param title
         * @param titleLink
         * @param color
         * @param authorName
         * @param thumbUrl
         * @param fields
         */
        public Attachment(String fallback, String color, String pretext, String authorName, String authorLink, String authorIcon, String title, String titleLink, String text, List<Field> fields, String imageUrl, String thumbUrl, String footer, String footerIcon, Integer ts) {
            super();
            this.fallback = fallback;
            this.color = color;
            this.pretext = pretext;
            this.authorName = authorName;
            this.authorLink = authorLink;
            this.authorIcon = authorIcon;
            this.title = title;
            this.titleLink = titleLink;
            this.text = text;
            this.fields = fields;
            this.imageUrl = imageUrl;
            this.thumbUrl = thumbUrl;
            this.footer = footer;
            this.footerIcon = footerIcon;
            this.ts = ts;
        }

        public String getFallback() {
            return fallback;
        }

        public void setFallback(String fallback) {
            this.fallback = fallback;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getPretext() {
            return pretext;
        }

        public void setPretext(String pretext) {
            this.pretext = pretext;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorLink() {
            return authorLink;
        }

        public void setAuthorLink(String authorLink) {
            this.authorLink = authorLink;
        }

        public String getAuthorIcon() {
            return authorIcon;
        }

        public void setAuthorIcon(String authorIcon) {
            this.authorIcon = authorIcon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleLink() {
            return titleLink;
        }

        public void setTitleLink(String titleLink) {
            this.titleLink = titleLink;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<Field> getFields() {
            return fields;
        }

        public void setFields(List<Field> fields) {
            this.fields = fields;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public void setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
        }

        public String getFooter() {
            return footer;
        }

        public void setFooter(String footer) {
            this.footer = footer;
        }

        public String getFooterIcon() {
            return footerIcon;
        }

        public void setFooterIcon(String footerIcon) {
            this.footerIcon = footerIcon;
        }

        public Integer getTs() {
            return ts;
        }

        public void setTs(Integer ts) {
            this.ts = ts;
        }

        @Parcel
        public static class Field {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("value")
            @Expose
            private String value;
            @SerializedName("short")
            @Expose
            private Boolean _short;

            /**
             * No args constructor for use in serialization
             *
             */
            public Field() {
            }

            /**
             *
             * @param title
             * @param _short
             * @param value
             */
            public Field(String title, String value, Boolean _short) {
                super();
                this.title = title;
                this.value = value;
                this._short = _short;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public Boolean getShort() {
                return _short;
            }

            public void setShort(Boolean _short) {
                this._short = _short;
            }

        }

    }

}
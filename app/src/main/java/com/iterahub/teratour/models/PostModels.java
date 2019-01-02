package com.iterahub.teratour.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Arinze on 10/21/2017.
 */

@Parcel
public class PostModels {

    @PrimaryKey()
    int primaryKey;
    @SerializedName("status")
    @Expose
    boolean status;
    @SerializedName("data")
    @Expose
    @Ignore
    ArrayList<PostData> data;

    public PostModels(){

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<PostData> getData() {
        return data;
    }

    public void setData(ArrayList<PostData> data) {
        this.data = data;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Parcel
    @Entity
    public static class PostData {

        @PrimaryKey()
        @SerializedName("id")
        @Expose
         int id;
        @SerializedName("user_id")
        @Expose
         int userId;
        @SerializedName("title")
        @Expose
         String title;
        @SerializedName("text")
        @Expose
         String text;
        @SerializedName("createdAt")
        @Expose
         String createdAt;
        @SerializedName("updated_at")
        @Expose
         String updatedAt;
        @SerializedName("total_likes")
        @Expose
         int totalLikes;
        @SerializedName("liked_by_user")
        @Expose
         boolean likedByUser;
        @SerializedName("latest_comment")
        @Expose
        @Ignore
         LatestComment latestComment;
        @SerializedName("user")
        @Expose
        @Ignore
        PostOwner user;
        @SerializedName("media")
        @Expose
        @Ignore
        Media PostMedia;

        public PostData(){

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getTotalLikes() {
            return totalLikes;
        }

        public void setTotalLikes(int totalLikes) {
            this.totalLikes = totalLikes;
        }

        public boolean getLikedByUser() {
            return likedByUser;
        }

        public void setLikedByUser(boolean likedByUser) {
            this.likedByUser = likedByUser;
        }

        public LatestComment getLatestComment() {
            return latestComment;
        }

        public void setLatestComment(LatestComment latestComment) {
            this.latestComment = latestComment;
        }

        public PostOwner getUser() {
            return user;
        }

        public void setUser(PostOwner user) {
            this.user = user;
        }

        public Media getPostMedia() {
            return PostMedia;
        }

        public void setPostMedia(Media postMedia) {
            PostMedia = postMedia;
        }

        @Parcel
        @Entity
        public static class LatestComment {

            @PrimaryKey()
            @SerializedName("id")
            @Expose
             int id;
            @SerializedName("user_id")
            @Expose
             int userId;
            @SerializedName("post_id")
            @Expose
             int postId;
            @SerializedName("text")
            @Expose
             String text;
            @SerializedName("createdAt")
            @Expose
             String createdAt;
            @SerializedName("updated_at")
            @Expose
             String updatedAt;
            @SerializedName("user")
            @Expose
            @Ignore
            CommentOwner user;

            public int getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(Integer postId) {
                this.postId = postId;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public CommentOwner getUser() {
                return user;
            }

            public void setUser(CommentOwner user) {
                this.user = user;
            }

            @Parcel
            @Entity
            public static class CommentOwner {



                int commentId;
                @PrimaryKey()
                @SerializedName("id")
                @Expose
                int id;
                @SerializedName("firstname")
                @Expose
                String firstname;
                @SerializedName("lastname")
                @Expose
                String lastname;
                @SerializedName("about")
                @Expose
                String about;
                @SerializedName("location")
                @Expose
                String location;
                @SerializedName("gender")
                @Expose
                int gender;
                @SerializedName("image_url")
                @Expose
                String imageUrl;
                @SerializedName("coverphoto_url")
                @Expose
                String coverphotoUrl;
                @SerializedName("username")
                @Expose
                String username;

                public int getCommentId() {
                    return commentId;
                }

                public void setCommentId(int commentId) {
                    this.commentId = commentId;
                }

                public int getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getFirstname() {
                    return firstname;
                }

                public void setFirstname(String firstname) {
                    this.firstname = firstname;
                }

                public String getLastname() {
                    return lastname;
                }

                public void setLastname(String lastname) {
                    this.lastname = lastname;
                }

                public String getAbout() {
                    return about;
                }

                public void setAbout(String about) {
                    this.about = about;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(Integer gender) {
                    this.gender = gender;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getCoverphotoUrl() {
                    return coverphotoUrl;
                }

                public void setCoverphotoUrl(String coverphotoUrl) {
                    this.coverphotoUrl = coverphotoUrl;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }
            }

        }
        @Parcel
        @Entity
        public static class PostOwner {

            @PrimaryKey()
            @SerializedName("id")
            @Expose
             int id;
             int postId;
            @SerializedName("firstname")
            @Expose
             String firstname;
            @SerializedName("lastname")
            @Expose
             String lastname;
            @SerializedName("about")
            @Expose
             String about;
            @SerializedName("location")
            @Expose
             String location;
            @SerializedName("dob")
            @Expose
             String dob;
            @SerializedName("gender")
            @Expose
             int gender;
            @SerializedName("image_url")
            @Expose
             String imageUrl;
            @SerializedName("coverphoto_url")
            @Expose
             String coverphotoUrl;
            @SerializedName("email")
            @Expose
             String email;
            @SerializedName("username")
            @Expose
             String username;
            @SerializedName("createdAt")
            @Expose
             String createdAt;
            @SerializedName("updated_at")
            @Expose
             String updatedAt;

            public int getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getFirstname() {
                return firstname;
            }

            public void setFirstname(String firstname) {
                this.firstname = firstname;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getCoverphotoUrl() {
                return coverphotoUrl;
            }

            public void setCoverphotoUrl(String coverphotoUrl) {
                this.coverphotoUrl = coverphotoUrl;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }
        }

        @Entity
        @Parcel
        public static class Media {

            @PrimaryKey()
            @SerializedName("id")
            @Expose
            public int id;
            @SerializedName("media_image")
            @Expose
            public String mediaUrl;
            @SerializedName("post_id")
            @Expose
            public int postId;
            @SerializedName("createdAt")
            @Expose
            public String created_at;
            @SerializedName("updated_at")
            @Expose
            public String updated_at;

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }



            /**
             * No args constructor for use in serialization
             *
             */
            public Media() {
            }

            /**
             *
             * @param id
             * @param mediaUrl
             * @param postId
             */
            @Ignore
            public Media(int id, String mediaUrl, int postId) {
                super();
                this.id = id;
                this.mediaUrl = mediaUrl;
                this.postId = postId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getmediaUrl() {
                return mediaUrl;
            }

            public void setmediaUrl(String mediaUrl) {
                this.mediaUrl = mediaUrl;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }
        }
    }
}



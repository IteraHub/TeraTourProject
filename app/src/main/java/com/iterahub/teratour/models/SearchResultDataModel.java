package com.iterahub.teratour.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arinzedroid on 8/30/2018.
 */

public class SearchResultDataModel {

    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("data")
    private SearchData searchData;

    public SearchResultDataModel(boolean status, SearchData searchData) {
        this.status = status;
        this.searchData = searchData;
    }

    public SearchData getSearchData() {
        return searchData;
    }

    public void setSearchData(SearchData searchData) {
        this.searchData = searchData;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public class SearchData{
        @Expose
        @SerializedName("posts")
        private List<PostModels.PostData> postDataList;
        @Expose
        @SerializedName("users")
        private List<User.UserData> userDataList;

        private SearchData(List<PostModels.PostData> postDataList, List<User.UserData> userDataList) {
            this.postDataList = postDataList;
            this.userDataList = userDataList;
        }

        public List<User.UserData> getUserDataList() {
            return userDataList;
        }

        public void setUserDataList(List<User.UserData> userDataList) {
            this.userDataList = userDataList;
        }

        public List<PostModels.PostData> getPostDataList() {
            return postDataList;
        }

        public void setPostDataList(List<PostModels.PostData> postDataList) {
            this.postDataList = postDataList;
        }
    }

}

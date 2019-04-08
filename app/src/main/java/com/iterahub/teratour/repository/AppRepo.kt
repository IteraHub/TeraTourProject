package com.iterahub.teratour.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log

import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.iterahub.teratour.TeraTour
import com.iterahub.teratour.comparators.CommentsComparator
import com.iterahub.teratour.comparators.MessagesComparator
import com.iterahub.teratour.database.DatabaseInitializer
import com.iterahub.teratour.logics.AccountLogics

import com.iterahub.teratour.models.ChatsModel
import com.iterahub.teratour.models.CommentModel
import com.iterahub.teratour.models.CommentModels
import com.iterahub.teratour.models.CommentsOnPost
import com.iterahub.teratour.models.LikeModel
import com.iterahub.teratour.models.MarkerModel
import com.iterahub.teratour.api_service.WebService
import com.iterahub.teratour.models.MediaModel
import com.iterahub.teratour.models.MessagesModel
import com.iterahub.teratour.models.PostModel
import com.iterahub.teratour.models.PostModels
import com.iterahub.teratour.models.SearchResultDataModel
import com.iterahub.teratour.models.User
import com.iterahub.teratour.models.UserBody
import com.iterahub.teratour.models.CommentsBody
import com.iterahub.teratour.models.UserModel
import com.iterahub.teratour.utils.DateTimeUtils
import com.iterahub.teratour.utils.NetworkUtils

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by Arinze on 11/8/2017.
 */

class AppRepo {
    private val webservice: WebService

    private val firestoreDB = FirebaseFirestore.getInstance()
    private val mFirebaseDatabase = FirebaseDatabase.getInstance()

    //                        if(querySnapshot != null && !querySnapshot.isEmpty()){
    //                            for (QueryDocumentSnapshot doc :querySnapshot) {
    //                                postModelsList.add(doc.toObject(PostModel.class));
    //                            }
    //                            postModelMutableLiveData.postValue(postModelsList);
    //                        }else{
    //                            postModelMutableLiveData.postValue(null);
    //                        }
    val allPosts: LiveData<List<PostModel>>
        get() {
            val postModelMutableLiveData = MutableLiveData<List<PostModel>>()
            val postModelsList = ArrayList<PostModel>()
            firestoreDB.collection(POST_DETAILS_TABLE)
                    .orderBy("created_at", Query.Direction.DESCENDING)
                    .limit(50).get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val querySnapshot = task.result
                            for (doc in querySnapshot!!) {
                                postModelsList.add(doc.toObject(PostModel::class.java!!))
                            }
                            postModelMutableLiveData.postValue(postModelsList)
                        } else {
                            postModelMutableLiveData.postValue(null)
                            Log.e(this.javaClass.getSimpleName(), "Error >>", task.exception)
                        }
                    }
            return postModelMutableLiveData
        }

    init {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        }

        // add logging as last interceptor
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(NetworkInterceptor())

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build()
        webservice = retrofit.create(WebService::class.java!!)
    }

    fun getMarkerDetails(Id: String): LiveData<MarkerModel> {
        val MarkerData = MutableLiveData<MarkerModel>()
        webservice.getMarkers(Id).enqueue(object : Callback<MarkerModel> {
            override fun onResponse(call: Call<MarkerModel>, response: Response<MarkerModel>) {
                MarkerData.postValue(response.body())
            }

            override fun onFailure(call: Call<MarkerModel>, t: Throwable) {
                AccountLogics.logMsg(this.javaClass, t.message)
            }
        })
        return MarkerData
    }

    fun registerUser(userBody: UserBody): LiveData<User> {
        val userMutableLiveData = MutableLiveData<User>()
        webservice.RegisterUser(userBody).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    userMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })
        return userMutableLiveData
    }

    fun verifyUser(auth: String): LiveData<User.UserData> {
        var auth = auth
        auth = "Basic $auth"
        AccountLogics.logMsg(this.javaClass, auth)
        val userMutableLiveData = MutableLiveData<User.UserData>()
        webservice.VerifyUser(auth).enqueue(object : Callback<User.UserData> {
            override fun onResponse(call: Call<User.UserData>, response: Response<User.UserData>) {
                if (response.isSuccessful) {
                    userMutableLiveData.postValue(response.body())
                } else if (response.code() == 401) {

                }
            }

            override fun onFailure(call: Call<User.UserData>, t: Throwable) {

            }
        })
        return userMutableLiveData
    }

    fun getPosts(auth: String): LiveData<PostModels> {
        var auth = auth
        auth = "Basic $auth"
        val postModelMutableLiveData = MutableLiveData<PostModels>()
        if (NetworkUtils.isNetworkAvailable()) {
            webservice.getPosts(auth).enqueue(object : Callback<PostModels> {
                override fun onResponse(call: Call<PostModels>, response: Response<PostModels>) {
                    if (response.body() != null) {
                        postModelMutableLiveData.postValue(response.body())
                        DatabaseInitializer.addPostsAsync(TeraTour.getAppDatabase(), Objects.requireNonNull<PostModels>(response.body()))
                    }
                }

                override fun onFailure(call: Call<PostModels>, t: Throwable) {

                }
            })
        } else {
            DatabaseInitializer.readPostsAsync(TeraTour.getAppDatabase()) { posts ->
                if (posts != null)
                    postModelMutableLiveData.postValue(posts)
            }
        }

        return postModelMutableLiveData
    }

    fun getCommentsOnPost(auth: String, postId: Int): LiveData<CommentsOnPost> {
        var auth = auth
        auth = "Basic $auth"
        val CommentsMutableLiveData = MutableLiveData<CommentsOnPost>()
        if (NetworkUtils.isNetworkAvailable()) {
            //call from the server
            webservice.getComments(auth, postId).enqueue(object : Callback<CommentsOnPost> {
                override fun onResponse(call: Call<CommentsOnPost>,
                                        response: Response<CommentsOnPost>) {
                    val commentsOnPost = response.body()
                    if (response.isSuccessful && commentsOnPost != null) {
                        CommentsMutableLiveData.postValue(commentsOnPost)

                        //Add posts to db
                        if (commentsOnPost.commentData != null)
                            DatabaseInitializer.addCommentsArraylistAsync(TeraTour.getAppDatabase(), commentsOnPost.commentData)
                    }
                }

                override fun onFailure(call: Call<CommentsOnPost>, t: Throwable) {

                }
            })
        } else {
            //call from the db
        }
        return CommentsMutableLiveData
    }

    fun postComments(auth: String, postId: Int, commentsBody: CommentsBody): LiveData<CommentModels> {
        var auth = auth
        auth = "Basic $auth"
        val mutableLiveData = MutableLiveData<CommentModels>()
        webservice.postComments(auth, postId, commentsBody).enqueue(object : Callback<CommentModels> {
            override fun onResponse(call: Call<CommentModels>, response: Response<CommentModels>) {
                if (response.isSuccessful && response.body() != null) {
                    val commentModels = response.body()
                    mutableLiveData.postValue(commentModels)
                    if (commentModels != null && commentModels.data != null) {
                        val latestComment = commentModels.data
                        DatabaseInitializer.addCommentsAsync(TeraTour.getAppDatabase(), latestComment)
                    }
                }
            }

            override fun onFailure(call: Call<CommentModels>, t: Throwable) {

            }
        })
        return mutableLiveData
    }

    fun likePost(auth: String, postId: Int): LiveData<LikeModel> {
        var auth = auth
        auth = "Basic $auth"
        val likeModelMutableLiveData = MutableLiveData<LikeModel>()
        webservice.likePost(auth, postId).enqueue(object : Callback<LikeModel> {
            override fun onResponse(call: Call<LikeModel>, response: Response<LikeModel>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        likeModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LikeModel>, t: Throwable) {

            }
        })
        return likeModelMutableLiveData
    }

    fun unLikePost(auth: String, postId: Int): LiveData<LikeModel> {
        var auth = auth
        auth = "Basic $auth"
        val unLikeModelMutableLiveData = MutableLiveData<LikeModel>()
        webservice.unLikePost(auth, postId).enqueue(object : Callback<LikeModel> {
            override fun onResponse(call: Call<LikeModel>, response: Response<LikeModel>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        unLikeModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LikeModel>, t: Throwable) {

            }
        })
        return unLikeModelMutableLiveData
    }

    fun search(auth: String, query: String): LiveData<SearchResultDataModel> {
        var auth = auth
        auth = "Basic $auth"
        val searchResultDataModelMutableLiveData = MutableLiveData<SearchResultDataModel>()
        webservice.search(auth, query).enqueue(object : Callback<SearchResultDataModel> {
            override fun onResponse(call: Call<SearchResultDataModel>, response: Response<SearchResultDataModel>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        searchResultDataModelMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<SearchResultDataModel>, t: Throwable) {

            }
        })
        return searchResultDataModelMutableLiveData
    }

    //Firebase start region

    fun createUser(userModel: UserModel): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()
        firestoreDB.collection(USER_DETAILS_TABLE).document(userModel.id).set(userModel)
                .addOnSuccessListener { docRef -> isSuccessful.postValue(true) }
                .addOnFailureListener { exception ->
                    isSuccessful.postValue(false)
                    Log.e(this.javaClass.getSimpleName(), "Error adding data >> ", exception)
                }
        return isSuccessful
    }

    fun updateUser(userModel: UserModel): LiveData<Boolean>{
        val isSuccessListener = MutableLiveData<Boolean>()
        val userMap = HashMap<String,Any?>()
        userMap["image_url"] = userModel.image_url
        userMap["firstname"] = userModel.firstname
        userMap["lastname"] = userModel.lastname
        userMap["password"] = userModel.password
        //userMap["about"] = userModel.about
        userMap["cover_photo_url"] = userModel.cover_photo_url
        userMap["updated_at"] = Date()

        firestoreDB.collection(USER_DETAILS_TABLE).document(userModel.id)
                .update(userMap)
                .addOnSuccessListener { isSuccessListener.postValue(true) }
                .addOnFailureListener{isSuccessListener.postValue(false)}

        return isSuccessListener
    }

    fun isUserNameUnique(username: String): LiveData<UserModel> {
        val userModelMutableLiveData = MutableLiveData<UserModel>()
        firestoreDB.collection(USER_DETAILS_TABLE).whereEqualTo("username", username)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result == null || task.result!!.documents.isEmpty()) {
                            userModelMutableLiveData.postValue(null)
                        } else {
                            val doc = task.result
                            userModelMutableLiveData.postValue(doc!!.documents[0].toObject(UserModel::class.java!!))
                        }
                    }
                }
        return userModelMutableLiveData
    }

    fun isEmailUnique(email: String): LiveData<UserModel> {
        val userModelMutableLiveData = MutableLiveData<UserModel>()
        firestoreDB.collection(USER_DETAILS_TABLE).whereEqualTo("email", email)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val doc = task.result
                        if (doc == null || doc.documents.isEmpty()) {
                            userModelMutableLiveData.postValue(null)
                        }else{
                            userModelMutableLiveData.postValue(doc!!.documents[0].toObject(UserModel::class.java))
                        }
                    } else {
                        userModelMutableLiveData.postValue(null)
                        Log.e(this.javaClass.simpleName, "Error >> ", task.exception)
                    }
                }
        return userModelMutableLiveData
    }

    fun loginUser(username: String, password: String): LiveData<UserModel> {
        val userModelMutableLiveData = MutableLiveData<UserModel>()
        firestoreDB.collection(USER_DETAILS_TABLE)
                .whereEqualTo("password", password)
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener { data ->
                    if (data.documents.isEmpty()) {
                        userModelMutableLiveData.postValue(null)
                    } else
                        userModelMutableLiveData.postValue(data.documents[0].toObject(UserModel::class.java))
                }
                .addOnFailureListener { error ->
                    userModelMutableLiveData.postValue(null)
                    Log.e(this.javaClass.simpleName, "error >> $error")
                }
        return userModelMutableLiveData
    }

    fun getPostsByUserId(userId: String, fromCache: Boolean): LiveData<List<PostModel>> {
        val postMutableLiveData = MutableLiveData<List<PostModel>>()
        val postModelList = ArrayList<PostModel>()
        val source: Source
        if (fromCache) {
            source = Source.CACHE
        } else {
            source = Source.SERVER
        }
        firestoreDB.collection(POST_DETAILS_TABLE).whereEqualTo("user_Id", userId)
                .orderBy("created_at", Query.Direction.DESCENDING).get()
                .addOnSuccessListener { data ->
                    for (qs in data) {
                        postModelList.add(qs.toObject(PostModel::class.java!!))
                    }
                    postMutableLiveData.postValue(postModelList)
                }
                .addOnFailureListener { error ->
                    postMutableLiveData.postValue(null)
                    Log.e(this.javaClass.getSimpleName(), "Error >> ", error)
                }
        return postMutableLiveData
    }

    fun addPost(postModel: PostModel): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()
        val mediaModel = postModel.mediaModel
        val commentModel = postModel.latestComment
        val userModel = postModel.postOwner
        postModel.mediaModel = null
        postModel.latestComment = null
        postModel.postOwner = null
        firestoreDB.collection(POST_DETAILS_TABLE).document(postModel.id)
                .set(postModel)
                .addOnSuccessListener { success ->
                    isSuccessful.postValue(true)
                    firestoreDB.collection(MEDIA_DETAILS_TABLE).document(mediaModel.id).set(mediaModel)
                    firestoreDB.collection(COMMENT_DETAILS_TABLE).document(commentModel.id).set(commentModel)
                    firestoreDB.collection(USER_DETAILS_TABLE).document(userModel.id).set(userModel)
                }
                .addOnFailureListener { error ->
                    isSuccessful.postValue(false)
                    Log.e(this.javaClass.getSimpleName(), "Error >> ", error)
                }

        return isSuccessful
    }

    fun getMedia(postId: String): LiveData<MediaModel> {
        val mediaModelMutableLiveData = MutableLiveData<MediaModel>()
        firestoreDB.collection(MEDIA_DETAILS_TABLE).whereEqualTo("post_id", postId).get()
                .addOnSuccessListener { data ->
                    if(!data.documents.isEmpty()){
                        mediaModelMutableLiveData.postValue(data.documents[0]
                                .toObject(MediaModel::class.java))
                    }
                }
                .addOnFailureListener { error ->
                    mediaModelMutableLiveData.postValue(null)
                    Log.e(this.javaClass.getSimpleName(), "Error >> ", error)
                }
        return mediaModelMutableLiveData
    }

    fun getLatestComments(postId: String): LiveData<CommentModel> {
        val commentModelMutableLiveData = MutableLiveData<CommentModel>()
        firestoreDB.collection(COMMENT_DETAILS_TABLE).whereEqualTo("post_id", postId)
                .orderBy("created_at", Query.Direction.DESCENDING).get()
                .addOnSuccessListener { data -> commentModelMutableLiveData.postValue(data.documents[0].toObject(CommentModel::class.java!!)) }
                .addOnFailureListener { error ->
                    commentModelMutableLiveData.postValue(null)
                    Log.e(this.javaClass.simpleName, "Error >> ", error)
                }
        return commentModelMutableLiveData
    }

    fun getAllComments(postId: String): LiveData<List<CommentModel>> {
        val commentModelMutableLiveData = MutableLiveData<List<CommentModel>>()
        val commentModelsList = ArrayList<CommentModel>()
        firestoreDB.collection(COMMENT_DETAILS_TABLE).whereEqualTo("post_id", postId)
                .orderBy("created_at", Query.Direction.DESCENDING).get()
                .addOnSuccessListener { data ->
                    for (qs in data) {
                        commentModelsList.add(qs.toObject(CommentModel::class.java!!))
                    }
                    commentModelMutableLiveData.postValue(commentModelsList)
                }
                .addOnFailureListener { error ->
                    commentModelMutableLiveData.postValue(null)
                    Log.e(this.javaClass.simpleName, "Error >> ", error)
                }
        return commentModelMutableLiveData
    }

    fun getCommentUpdates(postId: String): LiveData<List<CommentModel>> {
        val commentMutableLivedata = MutableLiveData<List<CommentModel>>()
        val commentModelsList = ArrayList<CommentModel>()
        firestoreDB.collection(COMMENT_DETAILS_TABLE).whereEqualTo("post_id", postId)
                .orderBy("created_at", Query.Direction.ASCENDING)
                .addSnapshotListener { querySnapshots, e ->
                    if (querySnapshots != null) {
                        for (qs in querySnapshots) {
                            commentModelsList.add(qs.toObject(CommentModel::class.java!!))
                        }

                        commentMutableLivedata.postValue(filterOutDuplicates(commentModelsList))
                    } else {
                        commentMutableLivedata.postValue(null)
                    }
                    if (e != null) {
                        Log.e(this.javaClass.getSimpleName(), " Error >> ", e)
                    }

                }
        return commentMutableLivedata
    }

    fun addComments(commentModel: CommentModel): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()
        if (commentModel.comment_owner != null) {
            commentModel.comment_owner = null
        }
        firestoreDB.collection(COMMENT_DETAILS_TABLE).document(commentModel.id).set(commentModel)
                .addOnSuccessListener { success -> isSuccessful.postValue(true) }
                .addOnFailureListener { error ->
                    isSuccessful.postValue(false)
                    Log.e(this.javaClass.getSimpleName(), "Error >> ", error)
                }
        return isSuccessful
    }

    fun getUserById(userId: String): LiveData<UserModel> {
        val userModelMutableLiveData = MutableLiveData<UserModel>()
        firestoreDB.collection(USER_DETAILS_TABLE).whereEqualTo("id", userId).get()
                .addOnSuccessListener { data ->
                    if (data != null && !data.documents.isEmpty()) {
                        userModelMutableLiveData.postValue(data.documents[0].toObject(UserModel::class.java!!))
                    }
                }
                .addOnFailureListener { error ->
                    userModelMutableLiveData.postValue(null)
                    Log.e(this.javaClass.getSimpleName(), "Error >> ", error)
                }
        return userModelMutableLiveData
    }

    fun getAllChatsUpdates(userId: String): LiveData<List<ChatsModel>> {
        val chatsMutableLivedata = MutableLiveData<List<ChatsModel>>()
        val chatsModelList = ArrayList<ChatsModel>()
        firestoreDB.collection(CHATS_DETAILS_TABLE)
                .whereEqualTo("otherUserId", userId)
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .addSnapshotListener { data, error ->
                    firestoreDB.collection(CHATS_DETAILS_TABLE).whereEqualTo("myUserId", userId)
                            .orderBy("createdAt", Query.Direction.ASCENDING)
                            .addSnapshotListener { data1, error1 ->
                                data?.forEach {
                                    chatsModelList.add(it.toObject(ChatsModel::class.java))
                                }
                                data1?.forEach {
                                    chatsModelList.add(it.toObject(ChatsModel::class.java))
                                }
                                if (error1 != null) {
                                    Log.e(this.javaClass.simpleName, "Error >> ", error1)
                                }

                                if((data == null) and (data1 == null)){
                                    chatsMutableLivedata.postValue(null)
                                }
                                if((error != null) and (error1 != null)){
                                    chatsMutableLivedata.postValue(null)
                                    Log.e(this.javaClass.simpleName, "Error1 >> $error1 and Error >> $error")
                                }
                                chatsMutableLivedata.postValue(chatsModelList)
                            }
                }

        return chatsMutableLivedata
    }

    fun addChats(chatsModel: ChatsModel): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()
        firestoreDB.collection(CHATS_DETAILS_TABLE).document(chatsModel.id).set(chatsModel)
                .addOnSuccessListener { isSuccessful.postValue(true) }
                .addOnFailureListener { error ->
                    isSuccessful.postValue(false)
                    Log.e(this.javaClass.simpleName, "Error >> ", error)
                }
        return isSuccessful
    }

    fun getChatByUsers(myUserId: String, otherUserId: String): LiveData<ChatsModel> {
        val chatsModelMutableLiveData = MutableLiveData<ChatsModel>()

        //get chats where current user and other user match
        firestoreDB.collection(CHATS_DETAILS_TABLE).whereEqualTo("myUserId", myUserId)
                .whereEqualTo("otherUserId", otherUserId).get()
                .addOnSuccessListener { data ->
                    if (data != null && !data.documents.isEmpty()) {
                        chatsModelMutableLiveData.postValue(data.documents[0].toObject(ChatsModel::class.java))
                    } else {
                        //this means no chat was found then alternate the id's and try to get chats
                        //where other user is saved as current user and current user is saved as
                        // other user
                        firestoreDB.collection(CHATS_DETAILS_TABLE).whereEqualTo("myUserId", otherUserId)
                                .whereEqualTo("otherUserId", myUserId).get()
                                .addOnSuccessListener { data2 ->
                                    if (data2 != null && !data2.documents.isEmpty()) {
                                        chatsModelMutableLiveData.postValue(data2.documents[0].toObject(ChatsModel::class.java))
                                    } else {
                                        chatsModelMutableLiveData.postValue(null)
                                    }
                                }
                                .addOnFailureListener { error ->
                                    chatsModelMutableLiveData.postValue(null)
                                    Log.e(this.javaClass.simpleName, "Error >> ", error)
                                }

                    }
                }
                .addOnFailureListener { error ->
                    chatsModelMutableLiveData.postValue(null)
                    Log.e(this.javaClass.simpleName, "Error >> ", error)
                }

        return chatsModelMutableLiveData
    }

    fun addMessage(messagesModel: MessagesModel): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()
        val ref = mFirebaseDatabase.getReference(MESSAGE_DETAILS_TABLE)
        ref.child(messagesModel.chatId).child(messagesModel.id).setValue(messagesModel)
                .addOnSuccessListener { data -> isSuccessful.postValue(true) }
                .addOnFailureListener { error ->
                    isSuccessful.postValue(false)
                    Log.e(javaClass.getSimpleName(), "Error >>", error)
                }
        return isSuccessful
    }

    fun getMessageUpdate(chatId: String): LiveData<MessagesModel>{
        val msgMutableLiveData = MutableLiveData<MessagesModel>()
        val ref = mFirebaseDatabase.getReference(MESSAGE_DETAILS_TABLE).child(chatId)
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val data = dataSnapshot.getValue(MessagesModel::class.java)
                println("data >>> $data")
                msgMutableLiveData.value = data
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
        return msgMutableLiveData
    }

    fun getMessages(chatId: String): LiveData<List<MessagesModel>> {
        val msgMutableLiveData = MutableLiveData<List<MessagesModel>>()
        val messagesModelList = ArrayList<MessagesModel>()
        val ref = mFirebaseDatabase.getReference(MESSAGE_DETAILS_TABLE).child(chatId)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.children.forEach {
                       val data = it.getValue<MessagesModel>(MessagesModel::class.java)
                        if(data != null)
                            messagesModelList.add(data)
                    }
                    msgMutableLiveData.postValue(filterOutDuplicatesMsg(messagesModelList))
                } else {
                    msgMutableLiveData.postValue(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                msgMutableLiveData.postValue(null)
                Log.e(javaClass.simpleName, " Error >> " + databaseError.message)
            }
        })
        return msgMutableLiveData
    }




    fun getLatestMessage(): LiveData<MessagesModel> {
        val msgMutableLiveData = MutableLiveData<MessagesModel>()
        val msgList = ArrayList<MessagesModel>()
        val ref = mFirebaseDatabase.getReference(MESSAGE_DETAILS_TABLE)
        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println("children count ${dataSnapshot.childrenCount}")
               dataSnapshot.children.forEach{

                   val snapshotValue = it.value as HashMap<*, *>
                   snapshotValue.forEach { it2 ->
                       val dataValue = it2.value as HashMap<*,*>
                       val messagesModel = MessagesModel()
                       messagesModel.chatId = dataValue["chatId"].toString()
                       messagesModel.createdAt = dataValue["createdAt"].toString()
                       messagesModel.text = dataValue["text"].toString()
                       messagesModel.userId = dataValue["userId"].toString()
                       messagesModel.id = dataValue["id"].toString()
                       msgList.add(messagesModel)
                   }
               }
                val finalList = filterOutDuplicatesMsg(msgList)
                msgMutableLiveData.postValue(finalList[finalList.size - 1])
            }

        })
        return msgMutableLiveData
    }

    //    public LiveData<List<UserModel>> getUsers(String searchText){
    //        final MutableLiveData<List<UserModel>> userMutableLiveData = new MutableLiveData<>();
    //    }

    private fun filterOutDuplicates(commentModelList: MutableList<CommentModel>): List<CommentModel> {
        val comments = TreeSet(CommentsComparator())
        comments.addAll(commentModelList)
        commentModelList.clear()
        commentModelList.addAll(comments)
        return commentModelList
    }

    private fun filterOutDuplicatesMsg(messagesModelList: MutableList<MessagesModel>): List<MessagesModel> {
        val messagesModels = TreeSet(MessagesComparator())
        messagesModels.addAll(messagesModelList)
        messagesModelList.clear()
        messagesModelList.addAll(messagesModels)
        messagesModelList.sortBy {DateTimeUtils.getDateFromString(it.createdAt)}
        return messagesModelList
    }

    companion object {
        private val BASE_URL = "http://teratour.herokuapp.com/"
        private val USER_DETAILS_TABLE = "USER_DETAILS"
        private val POST_DETAILS_TABLE = "POST_DETAILS"
        private val MEDIA_DETAILS_TABLE = "MEDIA_DETAILS"
        private val COMMENT_DETAILS_TABLE = "COMMENTS_DETAILS"
        private val CHATS_DETAILS_TABLE = "CHAT_DETAILS"
        private val MESSAGE_DETAILS_TABLE = "MESSAGE_DETAILS"
    }


    //Firebase end region
}

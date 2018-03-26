package com.example.mycomputer.igotafriend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My computer on 1/9/2018.
 */

//hello world
public class AccountInfo implements Serializable {
   // private String name;
    private String age;
    private String email;
    private List<Chat> chats;
    private String uid;
    private String originCity;
    private String dispalyName;
    private ProfileLocol profileLocol;
    private String photoUri;
    private Gender type;
    private SearchPreferernce searchPreferernce;

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public AccountInfo(String photoUri) {
        this.photoUri = photoUri;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setProfileLocol(ProfileLocol profileLocol) {
        this.profileLocol = profileLocol;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getEmail() {

        return email;
    }

    public String getUid() {
        return uid;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        chat.setKey(String.valueOf(chats.size() + 1));
        this.chats.add(chat);
    }

    public AccountInfo() {

        this.searchPreferernce = new SearchPreferernce();
        this.chats = new ArrayList<>();


    }

//    public AccountInfo(String uid) {
//        this.name=null;
//        this.age=null;
//        this.email=null;
//        this.uid=uid;
//        this.dispalyName=null;
//        this.profileLocol=null;
//        this.photoUri=null;
//        this.type=null;
//        this.searchPreferernce=null;
//    }

    public AccountInfo setSearchPreference(SearchPreferernce sp) {
        this.searchPreferernce = sp;
        return this;
    }

    public AccountInfo setProfileLocal(ProfileLocol pl) {
        this.profileLocol = pl;
        return this;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public void setSearchPreferernce(SearchPreferernce searchPreferernce) {
        this.searchPreferernce = searchPreferernce;
    }

    public SearchPreferernce getSearchPreferernce() {

        return searchPreferernce;
    }

    public String getPhotoUri() {

        return photoUri;
    }



    public void setAge(String age) {
        this.age = age;
    }

    public void setDispalyName(String userName) {
        dispalyName = userName;
    }

    public void setProfileLocol() {
        this.profileLocol = new ProfileLocol();
    }


    public String getAge() {
        return age;
    }

    public String getDispalyName() {
        return dispalyName;
    }

    public ProfileLocol getProfileLocol() {
        return profileLocol;
    }

    public Gender getType() {
        return type;
    }

    public void setType(Gender type) {
        this.type = type;
    }

    public Chat getChatByLOcal(String localUid) {
        for (Chat chat1 : chats) {
            if (chat1.getLocalUdi().equals(localUid)) {
                return chat1;
            }
        }

        return null;
    }
    public Chat getChatByViser(String VisetrlUid) {
        for (Chat chat1 : chats) {
            if (chat1.getVisterUdi().equals(VisetrlUid)) {
                return chat1;
            }
        }

        return null;
    }

    public boolean isChetExistWithLocal(String localUid) {
        for (Chat chat1 : chats) {
            if (chat1.getLocalUdi().equals(localUid)) {
                return true;
            }
        }

        return false;

    }
}


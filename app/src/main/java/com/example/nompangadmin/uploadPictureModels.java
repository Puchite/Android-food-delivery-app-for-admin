package com.example.nompangadmin;

public class uploadPictureModels {
    private String mName,mImageUri;

    public uploadPictureModels()
    {

    }

    public uploadPictureModels(String name, String imageUri)
    {
        if(name.trim().equals(""))
        {
            name = "NoName";
        }
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }
}

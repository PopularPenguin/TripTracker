package com.popularpenguin.triptracker.data;

import java.lang.System;

@androidx.room.Entity()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b$\b\u0007\u0018\u00002\u00020\u0001B[\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\t\u00a2\u0006\u0002\u0010\u0011B\u0005\u00a2\u0006\u0002\u0010\u0012J\u0006\u00102\u001a\u00020\u0005R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR \u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010!\"\u0004\b%\u0010#R\u001a\u0010&\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u001e\u0010+\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R \u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010!\"\u0004\b1\u0010#\u00a8\u00063"}, d2 = {"Lcom/popularpenguin/triptracker/data/Trip;", "", "id", "", "desc", "", "date", "Ljava/util/Date;", "points", "", "Lcom/google/android/gms/maps/model/LatLng;", "captionPhoto", "Landroid/net/Uri;", "photoMarkerList", "distance", "", "uriList", "(ILjava/lang/String;Ljava/util/Date;Ljava/util/List;Landroid/net/Uri;Ljava/util/List;DLjava/util/List;)V", "()V", "getCaptionPhoto", "()Landroid/net/Uri;", "setCaptionPhoto", "(Landroid/net/Uri;)V", "getDate", "()Ljava/util/Date;", "setDate", "(Ljava/util/Date;)V", "description", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "getPhotoMarkerList", "()Ljava/util/List;", "setPhotoMarkerList", "(Ljava/util/List;)V", "getPoints", "setPoints", "totalDistance", "getTotalDistance", "()D", "setTotalDistance", "(D)V", "uid", "getUid", "()I", "setUid", "(I)V", "getUriList", "setUriList", "getFormattedDate", "app_debug"})
public final class Trip {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private int uid;
    @org.jetbrains.annotations.NotNull()
    private java.util.Date date;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String description;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.google.android.gms.maps.model.LatLng> points;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri captionPhoto;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.google.android.gms.maps.model.LatLng> photoMarkerList;
    private double totalDistance;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<android.net.Uri> uriList;
    
    public final int getUid() {
        return 0;
    }
    
    public final void setUid(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getDate() {
        return null;
    }
    
    public final void setDate(@org.jetbrains.annotations.NotNull()
    java.util.Date p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final void setDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.google.android.gms.maps.model.LatLng> getPoints() {
        return null;
    }
    
    public final void setPoints(@org.jetbrains.annotations.NotNull()
    java.util.List<com.google.android.gms.maps.model.LatLng> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.net.Uri getCaptionPhoto() {
        return null;
    }
    
    public final void setCaptionPhoto(@org.jetbrains.annotations.Nullable()
    android.net.Uri p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.google.android.gms.maps.model.LatLng> getPhotoMarkerList() {
        return null;
    }
    
    public final void setPhotoMarkerList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.google.android.gms.maps.model.LatLng> p0) {
    }
    
    public final double getTotalDistance() {
        return 0.0;
    }
    
    public final void setTotalDistance(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<android.net.Uri> getUriList() {
        return null;
    }
    
    public final void setUriList(@org.jetbrains.annotations.NotNull()
    java.util.List<android.net.Uri> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDate() {
        return null;
    }
    
    public Trip() {
        super();
    }
    
    public Trip(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String desc, @org.jetbrains.annotations.NotNull()
    java.util.Date date, @org.jetbrains.annotations.NotNull()
    java.util.List<com.google.android.gms.maps.model.LatLng> points, @org.jetbrains.annotations.Nullable()
    android.net.Uri captionPhoto, @org.jetbrains.annotations.NotNull()
    java.util.List<com.google.android.gms.maps.model.LatLng> photoMarkerList, double distance, @org.jetbrains.annotations.NotNull()
    java.util.List<android.net.Uri> uriList) {
        super();
    }
}
package com.popularpenguin.triptracker.map;

import java.lang.System;

/**
 * Class to combine the map and location functions to draw a trip session
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\t\u0018\u0000 >2\u00020\u00012\u00020\u0002:\u0001>B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&J\u001a\u0010\'\u001a\u00020$2\u0006\u0010(\u001a\u00020&2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010&J\u000e\u0010*\u001a\u00020$2\u0006\u0010+\u001a\u00020&J\u000e\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020&J\u0010\u0010.\u001a\u00020$2\u0006\u0010/\u001a\u000200H\u0002J\b\u00101\u001a\u00020\u0007H\u0002J\u0006\u00102\u001a\u00020$J\u0018\u00103\u001a\u00020$2\u0006\u00104\u001a\u00020\u00152\u0006\u00105\u001a\u000206H\u0016J\u0010\u00107\u001a\u00020$2\u0006\u00108\u001a\u00020\u0017H\u0016J\u0006\u00109\u001a\u00020$J\u000e\u0010:\u001a\u00020$2\u0006\u0010;\u001a\u00020\tJ\b\u0010<\u001a\u00020$H\u0002J\u0006\u0010=\u001a\u00020$R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/popularpenguin/triptracker/map/TripTracker;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "Lcom/popularpenguin/triptracker/map/UserLocation$UserLocationListener;", "fragment", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/Fragment;)V", "distance", "", "infoTextView", "Landroid/widget/TextView;", "isFinished", "", "isMapReady", "isRefreshed", "isRunning", "jobList", "", "Lkotlinx/coroutines/Job;", "location", "Lcom/popularpenguin/triptracker/map/UserLocation;", "locationList", "Lcom/google/android/gms/maps/model/LatLng;", "map", "Lcom/google/android/gms/maps/GoogleMap;", "photoFile", "Ljava/io/File;", "getPhotoFile", "()Ljava/io/File;", "setPhotoFile", "(Ljava/io/File;)V", "photoMarkerList", "serviceConnection", "Landroid/content/ServiceConnection;", "uriList", "Landroid/net/Uri;", "addCameraListener", "", "cameraView", "Landroid/view/View;", "addControlListener", "controlView", "hintView", "addMapTypeListener", "typeView", "addZoomListener", "zoomView", "commitToDatabase", "dialogDescription", "", "computeTotalDistance", "onDestroy", "onLocationUpdated", "latLng", "zoom", "", "onMapReady", "googleMap", "onResume", "setInfoTextView", "view", "showSaveDialog", "storePhoto", "Companion", "app_debug"})
public final class TripTracker implements com.google.android.gms.maps.OnMapReadyCallback, com.popularpenguin.triptracker.map.UserLocation.UserLocationListener {
    private final java.util.List<kotlinx.coroutines.Job> jobList = null;
    private final com.popularpenguin.triptracker.map.UserLocation location = null;
    private final java.util.List<com.google.android.gms.maps.model.LatLng> locationList = null;
    private final android.content.ServiceConnection serviceConnection = null;
    private final java.util.List<com.google.android.gms.maps.model.LatLng> photoMarkerList = null;
    private final java.util.List<android.net.Uri> uriList = null;
    private com.google.android.gms.maps.GoogleMap map;
    private android.widget.TextView infoTextView;
    @org.jetbrains.annotations.NotNull()
    public java.io.File photoFile;
    private double distance;
    private boolean isMapReady;
    private boolean isRefreshed;
    private boolean isRunning;
    private boolean isFinished;
    private final androidx.fragment.app.Fragment fragment = null;
    public static final int REQUEST_PHOTO = 0;
    public static final com.popularpenguin.triptracker.map.TripTracker.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.io.File getPhotoFile() {
        return null;
    }
    
    public final void setPhotoFile(@org.jetbrains.annotations.NotNull()
    java.io.File p0) {
    }
    
    public final void onResume() {
    }
    
    public final void onDestroy() {
    }
    
    public final void addCameraListener(@org.jetbrains.annotations.NotNull()
    android.view.View cameraView) {
    }
    
    public final void addControlListener(@org.jetbrains.annotations.NotNull()
    android.view.View controlView, @org.jetbrains.annotations.Nullable()
    android.view.View hintView) {
    }
    
    public final void addZoomListener(@org.jetbrains.annotations.NotNull()
    android.view.View zoomView) {
    }
    
    public final void addMapTypeListener(@org.jetbrains.annotations.NotNull()
    android.view.View typeView) {
    }
    
    public final void setInfoTextView(@org.jetbrains.annotations.NotNull()
    android.widget.TextView view) {
    }
    
    public final void storePhoto() {
    }
    
    private final void showSaveDialog() {
    }
    
    private final void commitToDatabase(java.lang.String dialogDescription) {
    }
    
    private final double computeTotalDistance() {
        return 0.0;
    }
    
    @java.lang.Override()
    public void onLocationUpdated(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng latLng, float zoom) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap googleMap) {
    }
    
    public TripTracker(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/popularpenguin/triptracker/map/TripTracker$Companion;", "", "()V", "REQUEST_PHOTO", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
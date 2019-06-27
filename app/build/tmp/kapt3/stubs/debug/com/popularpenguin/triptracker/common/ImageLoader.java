package com.popularpenguin.triptracker.common;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J*\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eJ \u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001e\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014\u00a8\u0006\u0018"}, d2 = {"Lcom/popularpenguin/triptracker/common/ImageLoader;", "", "()V", "getRotation", "", "contentResolver", "Landroid/content/ContentResolver;", "photoUri", "Landroid/net/Uri;", "load", "", "view", "Landroid/widget/ImageView;", "center", "", "fit", "storeAsJpg", "context", "Landroid/content/Context;", "photoFile", "Ljava/io/File;", "bitmap", "Landroid/graphics/Bitmap;", "storePhoto", "app_debug"})
public final class ImageLoader {
    public static final com.popularpenguin.triptracker.common.ImageLoader INSTANCE = null;
    
    public final void load(@org.jetbrains.annotations.NotNull()
    android.net.Uri photoUri, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView view, boolean center, boolean fit) {
    }
    
    public final void storePhoto(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri photoUri, @org.jetbrains.annotations.NotNull()
    java.io.File photoFile) {
    }
    
    private final float getRotation(android.content.ContentResolver contentResolver, android.net.Uri photoUri) {
        return 0.0F;
    }
    
    private final void storeAsJpg(android.content.Context context, java.io.File photoFile, android.graphics.Bitmap bitmap) {
    }
    
    private ImageLoader() {
        super();
    }
}
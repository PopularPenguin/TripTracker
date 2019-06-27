package com.popularpenguin.triptracker.singletrip;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0002\u0013\u0014B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\r\u001a\u00020\tH\u0016J\u001c\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016J\u000e\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter$PhotoViewHolder;", "trip", "Lcom/popularpenguin/triptracker/data/Trip;", "handler", "Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter$OnClick;", "(Lcom/popularpenguin/triptracker/data/Trip;Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter$OnClick;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "removeItem", "OnClick", "PhotoViewHolder", "app_debug"})
public final class PhotoAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.popularpenguin.triptracker.singletrip.PhotoAdapter.PhotoViewHolder> {
    private final com.popularpenguin.triptracker.data.Trip trip = null;
    private final com.popularpenguin.triptracker.singletrip.PhotoAdapter.OnClick handler = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.popularpenguin.triptracker.singletrip.PhotoAdapter.PhotoViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.popularpenguin.triptracker.singletrip.PhotoAdapter.PhotoViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void removeItem(int position) {
    }
    
    public PhotoAdapter(@org.jetbrains.annotations.NotNull()
    com.popularpenguin.triptracker.data.Trip trip, @org.jetbrains.annotations.NotNull()
    com.popularpenguin.triptracker.singletrip.PhotoAdapter.OnClick handler) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J \u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2 = {"Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter$OnClick;", "", "onClick", "", "position", "", "onLongClick", "adapter", "Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter;", "trip", "Lcom/popularpenguin/triptracker/data/Trip;", "app_debug"})
    public static abstract interface OnClick {
        
        public abstract void onClick(int position);
        
        public abstract void onLongClick(@org.jetbrains.annotations.NotNull()
        com.popularpenguin.triptracker.singletrip.PhotoAdapter adapter, int position, @org.jetbrains.annotations.NotNull()
        com.popularpenguin.triptracker.data.Trip trip);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0005H\u0016\u00a8\u0006\u000f"}, d2 = {"Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter$PhotoViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "Landroid/view/View$OnLongClickListener;", "itemView", "Landroid/view/View;", "(Lcom/popularpenguin/triptracker/singletrip/PhotoAdapter;Landroid/view/View;)V", "bind", "", "photoUri", "Landroid/net/Uri;", "onClick", "view", "onLongClick", "", "app_debug"})
    public final class PhotoViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener, android.view.View.OnLongClickListener {
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        android.net.Uri photoUri) {
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
        }
        
        @java.lang.Override()
        public boolean onLongClick(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            return false;
        }
        
        public PhotoViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
    }
}
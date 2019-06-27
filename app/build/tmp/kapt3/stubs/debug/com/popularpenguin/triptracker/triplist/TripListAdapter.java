package com.popularpenguin.triptracker.triplist;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0002\u0014\u0015B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/popularpenguin/triptracker/triplist/TripListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/popularpenguin/triptracker/triplist/TripListAdapter$TripViewHolder;", "tripList", "", "Lcom/popularpenguin/triptracker/data/Trip;", "handler", "Lcom/popularpenguin/triptracker/triplist/TripListAdapter$OnClick;", "(Ljava/util/List;Lcom/popularpenguin/triptracker/triplist/TripListAdapter$OnClick;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "removeItem", "OnClick", "TripViewHolder", "app_debug"})
public final class TripListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.popularpenguin.triptracker.triplist.TripListAdapter.TripViewHolder> {
    private final java.util.List<com.popularpenguin.triptracker.data.Trip> tripList = null;
    private final com.popularpenguin.triptracker.triplist.TripListAdapter.OnClick handler = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.popularpenguin.triptracker.triplist.TripListAdapter.TripViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.popularpenguin.triptracker.triplist.TripListAdapter.TripViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void removeItem(int position) {
    }
    
    public TripListAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.popularpenguin.triptracker.data.Trip> tripList, @org.jetbrains.annotations.NotNull()
    com.popularpenguin.triptracker.triplist.TripListAdapter.OnClick handler) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J \u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH&\u00a8\u0006\f"}, d2 = {"Lcom/popularpenguin/triptracker/triplist/TripListAdapter$OnClick;", "", "onClick", "", "uid", "", "onLongClick", "adapter", "Lcom/popularpenguin/triptracker/triplist/TripListAdapter;", "position", "trip", "Lcom/popularpenguin/triptracker/data/Trip;", "app_debug"})
    public static abstract interface OnClick {
        
        public abstract void onClick(int uid);
        
        public abstract void onLongClick(@org.jetbrains.annotations.NotNull()
        com.popularpenguin.triptracker.triplist.TripListAdapter adapter, int position, @org.jetbrains.annotations.NotNull()
        com.popularpenguin.triptracker.data.Trip trip);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0005H\u0016\u00a8\u0006\u000f"}, d2 = {"Lcom/popularpenguin/triptracker/triplist/TripListAdapter$TripViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "Landroid/view/View$OnLongClickListener;", "itemView", "Landroid/view/View;", "(Lcom/popularpenguin/triptracker/triplist/TripListAdapter;Landroid/view/View;)V", "bind", "", "trip", "Lcom/popularpenguin/triptracker/data/Trip;", "onClick", "view", "onLongClick", "", "app_debug"})
    public final class TripViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener, android.view.View.OnLongClickListener {
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.popularpenguin.triptracker.data.Trip trip) {
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
        
        public TripViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
    }
}
package com.popularpenguin.triptracker.triplist;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 &2\u00020\u00012\u00020\u0002:\u0001&B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J&\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u000fH\u0016J \u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020 H\u0016J\u001a\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J&\u0010#\u001a\u00020\u000f2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010$\u001a\u00020%H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/popularpenguin/triptracker/triplist/TripListFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/popularpenguin/triptracker/triplist/TripListAdapter$OnClick;", "()V", "endDate", "", "jobList", "", "Lkotlinx/coroutines/Job;", "permissionValidator", "Lcom/popularpenguin/triptracker/common/PermissionValidator;", "showDatePickerListener", "Landroid/view/View$OnClickListener;", "startDate", "onClick", "", "uid", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onLongClick", "adapter", "Lcom/popularpenguin/triptracker/triplist/TripListAdapter;", "position", "trip", "Lcom/popularpenguin/triptracker/data/Trip;", "onViewCreated", "view", "setRecyclerView", "searchText", "", "Companion", "app_debug"})
public final class TripListFragment extends androidx.fragment.app.Fragment implements com.popularpenguin.triptracker.triplist.TripListAdapter.OnClick {
    private final java.util.List<kotlinx.coroutines.Job> jobList = null;
    private long startDate;
    private long endDate;
    private com.popularpenguin.triptracker.common.PermissionValidator permissionValidator;
    private final android.view.View.OnClickListener showDatePickerListener = null;
    public static final com.popularpenguin.triptracker.triplist.TripListFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setRecyclerView(long startDate, long endDate, java.lang.String searchText) {
    }
    
    @java.lang.Override()
    public void onClick(int uid) {
    }
    
    @java.lang.Override()
    public void onLongClick(@org.jetbrains.annotations.NotNull()
    com.popularpenguin.triptracker.triplist.TripListAdapter adapter, int position, @org.jetbrains.annotations.NotNull()
    com.popularpenguin.triptracker.data.Trip trip) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    public TripListFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/popularpenguin/triptracker/triplist/TripListFragment$Companion;", "", "()V", "newInstance", "Lcom/popularpenguin/triptracker/triplist/TripListFragment;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.popularpenguin.triptracker.triplist.TripListFragment newInstance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}
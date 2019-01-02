package com.itera.teratour.databinding;
import com.itera.teratour.R;
import com.itera.teratour.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ArViewBindingImpl extends ArViewBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new android.databinding.ViewDataBinding.IncludedLayouts(6);
        sIncludes.setIncludes(1, 
            new String[] {"target_info_layout"},
            new int[] {3},
            new int[] {R.layout.target_info_layout});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.unity_view, 4);
        sViewsWithIds.put(R.id.bottom_sheet, 5);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    @NonNull
    private final android.widget.TextView mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ArViewBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ArViewBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.support.v4.widget.NestedScrollView) bindings[5]
            , (com.itera.teratour.databinding.TargetInfoLayoutBinding) bindings[3]
            , (android.support.design.widget.CoordinatorLayout) bindings[0]
            , (android.widget.FrameLayout) bindings[4]
            );
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.screen.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        markerInfo.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (markerInfo.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.arViewModel == variableId) {
            setArViewModel((com.itera.teratour.viewmodel.ARViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setArViewModel(@Nullable com.itera.teratour.viewmodel.ARViewModel ArViewModel) {
        this.mArViewModel = ArViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.arViewModel);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable android.arch.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        markerInfo.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMarkerInfo((com.itera.teratour.databinding.TargetInfoLayoutBinding) object, fieldId);
            case 1 :
                return onChangeArViewModelNotify((android.arch.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMarkerInfo(com.itera.teratour.databinding.TargetInfoLayoutBinding MarkerInfo, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeArViewModelNotify(android.arch.lifecycle.MutableLiveData<java.lang.String> ArViewModelNotify, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.itera.teratour.viewmodel.ARViewModel arViewModel = mArViewModel;
        android.arch.lifecycle.MutableLiveData<java.lang.String> arViewModelNotify = null;
        java.lang.String arViewModelNotifyGetValue = null;

        if ((dirtyFlags & 0xeL) != 0) {



                if (arViewModel != null) {
                    // read arViewModel.notify
                    arViewModelNotify = arViewModel.notify;
                }
                updateLiveDataRegistration(1, arViewModelNotify);


                if (arViewModelNotify != null) {
                    // read arViewModel.notify.getValue()
                    arViewModelNotifyGetValue = arViewModelNotify.getValue();
                }
        }
        // batch finished
        if ((dirtyFlags & 0xeL) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, arViewModelNotifyGetValue);
        }
        executeBindingsOn(markerInfo);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): markerInfo
        flag 1 (0x2L): arViewModel.notify
        flag 2 (0x3L): arViewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}
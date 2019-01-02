package com.itera.teratour.databinding;
import com.itera.teratour.R;
import com.itera.teratour.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class TargetInfoLayoutBindingImpl extends TargetInfoLayoutBinding implements com.itera.teratour.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.card_image_layout, 4);
        sViewsWithIds.put(R.id.editText, 5);
        sViewsWithIds.put(R.id.card_text, 6);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public TargetInfoLayoutBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private TargetInfoLayoutBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.ImageView) bindings[1]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (android.widget.Button) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.cardImage.setTag(null);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.playback.setTag(null);
        this.targetName.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.itera.teratour.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.targetInfo == variableId) {
            setTargetInfo((com.itera.teratour.viewmodel.TargetInfoViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTargetInfo(@Nullable com.itera.teratour.viewmodel.TargetInfoViewModel TargetInfo) {
        this.mTargetInfo = TargetInfo;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.targetInfo);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeTargetInfoTargetImage((android.arch.lifecycle.LiveData<android.graphics.drawable.Drawable>) object, fieldId);
            case 1 :
                return onChangeTargetInfoTargetName((android.arch.lifecycle.LiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeTargetInfoTargetImage(android.arch.lifecycle.LiveData<android.graphics.drawable.Drawable> TargetInfoTargetImage, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeTargetInfoTargetName(android.arch.lifecycle.LiveData<java.lang.String> TargetInfoTargetName, int fieldId) {
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
        com.itera.teratour.viewmodel.TargetInfoViewModel targetInfo = mTargetInfo;
        android.arch.lifecycle.LiveData<android.graphics.drawable.Drawable> targetInfoTargetImage = null;
        boolean targetInfoTargetNameJavaLangObjectNull = false;
        java.lang.String targetInfoTargetNameGetValue = null;
        android.graphics.drawable.Drawable targetInfoTargetImageGetValue = null;
        android.arch.lifecycle.LiveData<java.lang.String> targetInfoTargetName = null;
        java.lang.String targetInfoTargetNameJavaLangObjectNullJavaLangStringNoTitleTargetInfoTargetName = null;

        if ((dirtyFlags & 0xfL) != 0) {


            if ((dirtyFlags & 0xdL) != 0) {

                    if (targetInfo != null) {
                        // read targetInfo.targetImage
                        targetInfoTargetImage = targetInfo.getTargetImage();
                    }
                    updateLiveDataRegistration(0, targetInfoTargetImage);


                    if (targetInfoTargetImage != null) {
                        // read targetInfo.targetImage.getValue()
                        targetInfoTargetImageGetValue = targetInfoTargetImage.getValue();
                    }
            }
            if ((dirtyFlags & 0xeL) != 0) {

                    if (targetInfo != null) {
                        // read targetInfo.targetName
                        targetInfoTargetName = targetInfo.getTargetName();
                    }
                    updateLiveDataRegistration(1, targetInfoTargetName);


                    if (targetInfoTargetName != null) {
                        // read targetInfo.targetName.getValue()
                        targetInfoTargetNameGetValue = targetInfoTargetName.getValue();
                    }


                    // read targetInfo.targetName.getValue() == null
                    targetInfoTargetNameJavaLangObjectNull = (targetInfoTargetNameGetValue) == (null);
                if((dirtyFlags & 0xeL) != 0) {
                    if(targetInfoTargetNameJavaLangObjectNull) {
                            dirtyFlags |= 0x20L;
                    }
                    else {
                            dirtyFlags |= 0x10L;
                    }
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0xeL) != 0) {

                // read targetInfo.targetName.getValue() == null ? "No Title" : targetInfo.targetName.getValue()
                targetInfoTargetNameJavaLangObjectNullJavaLangStringNoTitleTargetInfoTargetName = ((targetInfoTargetNameJavaLangObjectNull) ? ("No Title") : (targetInfoTargetNameGetValue));
        }
        // batch finished
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            android.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.cardImage, targetInfoTargetImageGetValue);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.playback.setOnClickListener(mCallback1);
        }
        if ((dirtyFlags & 0xeL) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.targetName, targetInfoTargetNameJavaLangObjectNullJavaLangStringNoTitleTargetInfoTargetName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // targetInfo
        com.itera.teratour.viewmodel.TargetInfoViewModel targetInfo = mTargetInfo;
        // targetInfo != null
        boolean targetInfoJavaLangObjectNull = false;



        targetInfoJavaLangObjectNull = (targetInfo) != (null);
        if (targetInfoJavaLangObjectNull) {



            targetInfo.DoSomething(callbackArg_0);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): targetInfo.targetImage
        flag 1 (0x2L): targetInfo.targetName
        flag 2 (0x3L): targetInfo
        flag 3 (0x4L): null
        flag 4 (0x5L): targetInfo.targetName.getValue() == null ? "No Title" : targetInfo.targetName.getValue()
        flag 5 (0x6L): targetInfo.targetName.getValue() == null ? "No Title" : targetInfo.targetName.getValue()
    flag mapping end*/
    //end
}
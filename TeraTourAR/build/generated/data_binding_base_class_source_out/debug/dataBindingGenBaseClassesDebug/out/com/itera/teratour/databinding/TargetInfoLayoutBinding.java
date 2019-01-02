package com.itera.teratour.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itera.teratour.viewmodel.TargetInfoViewModel;

public abstract class TargetInfoLayoutBinding extends ViewDataBinding {
  @NonNull
  public final ImageView cardImage;

  @NonNull
  public final RelativeLayout cardImageLayout;

  @NonNull
  public final TextView cardText;

  @NonNull
  public final TextView editText;

  @NonNull
  public final Button playback;

  @NonNull
  public final TextView targetName;

  @Bindable
  protected TargetInfoViewModel mTargetInfo;

  protected TargetInfoLayoutBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ImageView cardImage, RelativeLayout cardImageLayout, TextView cardText,
      TextView editText, Button playback, TextView targetName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardImage = cardImage;
    this.cardImageLayout = cardImageLayout;
    this.cardText = cardText;
    this.editText = editText;
    this.playback = playback;
    this.targetName = targetName;
  }

  public abstract void setTargetInfo(@Nullable TargetInfoViewModel targetInfo);

  @Nullable
  public TargetInfoViewModel getTargetInfo() {
    return mTargetInfo;
  }

  @NonNull
  public static TargetInfoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static TargetInfoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<TargetInfoLayoutBinding>inflate(inflater, com.itera.teratour.R.layout.target_info_layout, root, attachToRoot, component);
  }

  @NonNull
  public static TargetInfoLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static TargetInfoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<TargetInfoLayoutBinding>inflate(inflater, com.itera.teratour.R.layout.target_info_layout, null, false, component);
  }

  public static TargetInfoLayoutBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static TargetInfoLayoutBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (TargetInfoLayoutBinding)bind(component, view, com.itera.teratour.R.layout.target_info_layout);
  }
}

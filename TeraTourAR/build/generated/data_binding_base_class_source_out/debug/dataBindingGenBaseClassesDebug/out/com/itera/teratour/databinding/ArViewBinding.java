package com.itera.teratour.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.itera.teratour.viewmodel.ARViewModel;

public abstract class ArViewBinding extends ViewDataBinding {
  @NonNull
  public final NestedScrollView bottomSheet;

  @NonNull
  public final TargetInfoLayoutBinding markerInfo;

  @NonNull
  public final CoordinatorLayout screen;

  @NonNull
  public final FrameLayout unityView;

  @Bindable
  protected ARViewModel mArViewModel;

  protected ArViewBinding(DataBindingComponent _bindingComponent, View _root, int _localFieldCount,
      NestedScrollView bottomSheet, TargetInfoLayoutBinding markerInfo, CoordinatorLayout screen,
      FrameLayout unityView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomSheet = bottomSheet;
    this.markerInfo = markerInfo;
    setContainedBinding(this.markerInfo);;
    this.screen = screen;
    this.unityView = unityView;
  }

  public abstract void setArViewModel(@Nullable ARViewModel arViewModel);

  @Nullable
  public ARViewModel getArViewModel() {
    return mArViewModel;
  }

  @NonNull
  public static ArViewBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ArViewBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ArViewBinding>inflate(inflater, com.itera.teratour.R.layout.ar_view, root, attachToRoot, component);
  }

  @NonNull
  public static ArViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ArViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ArViewBinding>inflate(inflater, com.itera.teratour.R.layout.ar_view, null, false, component);
  }

  public static ArViewBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ArViewBinding bind(@NonNull View view, @Nullable DataBindingComponent component) {
    return (ArViewBinding)bind(component, view, com.itera.teratour.R.layout.ar_view);
  }
}

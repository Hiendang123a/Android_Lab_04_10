// Generated by view binder compiler. Do not edit!
package com.example.videoshort_firebase.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.videoshort_firebase.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SingleVideoRowBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView disfavcounts;

  @NonNull
  public final ImageView disfavorites;

  @NonNull
  public final TextView favcounts;

  @NonNull
  public final ImageView favorites;

  @NonNull
  public final ImageView imMore;

  @NonNull
  public final CircleImageView imPerson;

  @NonNull
  public final ImageView imShare;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final TextView textVideoDescription;

  @NonNull
  public final TextView textVideoTitle;

  @NonNull
  public final TextView tvEmail;

  @NonNull
  public final ProgressBar videoProgressBar;

  @NonNull
  public final VideoView videoView;

  private SingleVideoRowBinding(@NonNull ConstraintLayout rootView, @NonNull TextView disfavcounts,
      @NonNull ImageView disfavorites, @NonNull TextView favcounts, @NonNull ImageView favorites,
      @NonNull ImageView imMore, @NonNull CircleImageView imPerson, @NonNull ImageView imShare,
      @NonNull LinearLayout linearLayout, @NonNull TextView textVideoDescription,
      @NonNull TextView textVideoTitle, @NonNull TextView tvEmail,
      @NonNull ProgressBar videoProgressBar, @NonNull VideoView videoView) {
    this.rootView = rootView;
    this.disfavcounts = disfavcounts;
    this.disfavorites = disfavorites;
    this.favcounts = favcounts;
    this.favorites = favorites;
    this.imMore = imMore;
    this.imPerson = imPerson;
    this.imShare = imShare;
    this.linearLayout = linearLayout;
    this.textVideoDescription = textVideoDescription;
    this.textVideoTitle = textVideoTitle;
    this.tvEmail = tvEmail;
    this.videoProgressBar = videoProgressBar;
    this.videoView = videoView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SingleVideoRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SingleVideoRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.single_video_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SingleVideoRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.disfavcounts;
      TextView disfavcounts = ViewBindings.findChildViewById(rootView, id);
      if (disfavcounts == null) {
        break missingId;
      }

      id = R.id.disfavorites;
      ImageView disfavorites = ViewBindings.findChildViewById(rootView, id);
      if (disfavorites == null) {
        break missingId;
      }

      id = R.id.favcounts;
      TextView favcounts = ViewBindings.findChildViewById(rootView, id);
      if (favcounts == null) {
        break missingId;
      }

      id = R.id.favorites;
      ImageView favorites = ViewBindings.findChildViewById(rootView, id);
      if (favorites == null) {
        break missingId;
      }

      id = R.id.imMore;
      ImageView imMore = ViewBindings.findChildViewById(rootView, id);
      if (imMore == null) {
        break missingId;
      }

      id = R.id.imPerson;
      CircleImageView imPerson = ViewBindings.findChildViewById(rootView, id);
      if (imPerson == null) {
        break missingId;
      }

      id = R.id.imShare;
      ImageView imShare = ViewBindings.findChildViewById(rootView, id);
      if (imShare == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.textVideoDescription;
      TextView textVideoDescription = ViewBindings.findChildViewById(rootView, id);
      if (textVideoDescription == null) {
        break missingId;
      }

      id = R.id.textVideoTitle;
      TextView textVideoTitle = ViewBindings.findChildViewById(rootView, id);
      if (textVideoTitle == null) {
        break missingId;
      }

      id = R.id.tvEmail;
      TextView tvEmail = ViewBindings.findChildViewById(rootView, id);
      if (tvEmail == null) {
        break missingId;
      }

      id = R.id.videoProgressBar;
      ProgressBar videoProgressBar = ViewBindings.findChildViewById(rootView, id);
      if (videoProgressBar == null) {
        break missingId;
      }

      id = R.id.videoView;
      VideoView videoView = ViewBindings.findChildViewById(rootView, id);
      if (videoView == null) {
        break missingId;
      }

      return new SingleVideoRowBinding((ConstraintLayout) rootView, disfavcounts, disfavorites,
          favcounts, favorites, imMore, imPerson, imShare, linearLayout, textVideoDescription,
          textVideoTitle, tvEmail, videoProgressBar, videoView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

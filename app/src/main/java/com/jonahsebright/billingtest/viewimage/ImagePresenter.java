package com.jonahsebright.billingtest.viewimage;

import android.content.Context;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.unlock.Unlockable;

public class ImagePresenter implements Unlockable {

    private ImageViewModel imageViewModel;
    private Context context;

    public ImagePresenter(ImageViewModel imageViewModel, Context context) {
        this.imageViewModel = imageViewModel;
        this.context = context;
    }

    @Override
    public void unLocked() {
        imageViewModel.setImageFragmentModel(generateUnlockedModel(context));
    }

    @Override
    public void locked() {
        imageViewModel.setImageFragmentModel(generateLockedModel(context));
    }

    public ImageFragmentModel generateUnlockedModel(Context context) {
        return new ImageFragmentModel(R.drawable.ic_baseline_image_24, context.getString(R.string.info_image_unlocked));
    }

    public ImageFragmentModel generateLockedModel(Context context) {
        return new ImageFragmentModel(R.drawable.ic_baseline_lock_24, context.getString(R.string.info_image_locked));
    }
}

package com.jonahsebright.billingtest.viewimage;

import android.content.Context;

import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.unlock.Unlockable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ImagePresenterTest {

    @Mock
    Context context = mock(Context.class);
    private ImagePresenter imagePresenter;

    @BeforeEach
    void setUp() {
        when(context.getString(R.string.info_image_unlocked))
                .thenReturn("You unlocked this image!");
        when(context.getString(R.string.info_image_locked))
                .thenReturn("Image locked! Please subscribe to see the image");

        imagePresenter = new ImagePresenter(null, context);
    }

    @Test
    public void implementsUnlockable() throws Exception {
        assertThat(imagePresenter).isInstanceOf(Unlockable.class);
    }

    @Test
    public void createdCorrectImageFragmentModelWhenUnlocked() throws Exception {
        assertThat(imagePresenter.generateUnlockedModel(context))
                .usingRecursiveComparison()
                .isEqualTo(new ImageFragmentModel(R.drawable.ic_baseline_image_24, context.getString(R.string.info_image_unlocked)));
    }

    @Test
    public void createdCorrectImageFragmentModelWhenLocked() throws Exception {
        assertThat(imagePresenter.generateLockedModel(context))
                .usingRecursiveComparison()
                .isEqualTo(new ImageFragmentModel(R.drawable.ic_baseline_lock_24, context.getString(R.string.info_image_locked)));
    }
}
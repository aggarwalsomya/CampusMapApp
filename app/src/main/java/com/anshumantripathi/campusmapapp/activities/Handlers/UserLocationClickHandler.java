package com.anshumantripathi.campusmapapp.activities.Handlers;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.anshumantripathi.campusmapapp.R;
import com.anshumantripathi.campusmapapp.model.Constants;
import com.anshumantripathi.campusmapapp.model.Coordinates;
import com.anshumantripathi.campusmapapp.util.LocationContext;
import com.anshumantripathi.campusmapapp.util.UserLocationDrawUtils;
import com.anshumantripathi.campusmapapp.util.UserLocationFinderHelper;

public class UserLocationClickHandler implements View.OnClickListener {
    private AppCompatActivity appActivity;
    private LocationContext ctx;
    public UserLocationClickHandler (
            AppCompatActivity activity,
            LocationContext ctx
    ) {
        this.appActivity = activity;
        this.ctx = ctx;
    }

    @Override
    public void onClick(View v) {
        this.displayCurrentUserLocation(true);
    }

    public void displayCurrentUserLocation(boolean showErrorIfOutOfBoundary) {

        UserLocationDrawUtils.clearUserDots(appActivity,
                (FrameLayout) appActivity.findViewById(R.id.frameLayout));
        UserLocationFinderHelper.updateCurrentLocation(appActivity, ctx, showErrorIfOutOfBoundary);

        if (ctx.getCurrentLocation() != null) {

            Coordinates mapPixels = ctx.cd.convUtils.coorToPixels(ctx.getCurrentLocation());
            if (mapPixels.getLng() > (Constants.xPixelOffset + Constants.ImageSizeW) ||
                    mapPixels.getLat() > (Constants.yPixelOffset + Constants.ImageSizeH)) {
                if (showErrorIfOutOfBoundary) {
                    GenericToastManager.showGenericMsg(
                            appActivity.getBaseContext(),
                            "User is out of Campus."
                    );
                }
            } else {
                UserLocationDrawUtils.drawUserAtPixel(appActivity,
                        (FrameLayout) appActivity.findViewById(R.id.frameLayout),
                        (int) mapPixels.getLng(),
                        (int) mapPixels.getLat());
            }
        }
    }
}

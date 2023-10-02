package org.muffin.ads;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.unity3d.ads.*;

public class AdsActivity extends AppCompatActivity implements IUnityAdsInitializationListener {

    private static final String UNITY_ADS_TAG = "UnityAdsExample";
    private static final Boolean TEST_MODE = true;
    private static final String GAME_ID = "1234567";
    private static final String AD_UNIT_ID = "video";

    Activity mainActivity;

    public AdsActivity(Activity activity) {
        this.mainActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(UNITY_ADS_TAG, "OnCreate!");

        // Initialize the SDK:
        UnityAds.initialize(getApplicationContext(), GAME_ID, TEST_MODE, this);
    }

    private final IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {
            Log.i(UNITY_ADS_TAG, "onUnityAdsAdLoaded!");
            UnityAds.show(mainActivity, AD_UNIT_ID, new UnityAdsShowOptions(), showListener);
        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
            Log.e(UNITY_ADS_TAG, "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
        }
    };

    private final IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        @Override
        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
            Log.e(UNITY_ADS_TAG, "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
        }

        @Override
        public void onUnityAdsShowStart(String placementId) {
            Log.v(UNITY_ADS_TAG, "onUnityAdsShowStart: " + placementId);
        }

        @Override
        public void onUnityAdsShowClick(String placementId) {
            Log.v(UNITY_ADS_TAG, "onUnityAdsShowClick: " + placementId);
        }

        @Override
        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
            Log.v(UNITY_ADS_TAG, "onUnityAdsShowComplete: " + placementId);
        }
    };

    @Override
    public void onInitializationComplete() {
        Log.i(UNITY_ADS_TAG, "Init done!");
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
        Log.e(UNITY_ADS_TAG, "Unity Ads initialization failed with error: [" + error + "] " + message);
    }

    // Implement a function to load an interstitial ad. The ad will start to show after the ad has been loaded.
    public void DisplayInterstitialAd() {
        Log.i(UNITY_ADS_TAG, "DisplayInterstitialAd!");
        UnityAds.load(AD_UNIT_ID, loadListener);
    }
}

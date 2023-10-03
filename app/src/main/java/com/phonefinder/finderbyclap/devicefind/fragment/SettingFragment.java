package com.phonefinder.finderbyclap.devicefind.fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.R;
import com.phonefinder.finderbyclap.devicefind.databinding.FragmentSettingBinding;


public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;
    private String PREFS_NAME = "PREFS";
    private Uri selectedRingtoneUri;
    private AudioManager audioManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd0.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        String ringtoneUriStr = getPreference("ringtone_Name");
        if (!ringtoneUriStr.isEmpty()) {
            Uri lastSelectedRingtoneUri = Uri.parse(ringtoneUriStr);
            String ringtoneName = getRingtoneNameFromUri(lastSelectedRingtoneUri);
            binding.songItem.setText(ringtoneName);
            selectedRingtoneUri = lastSelectedRingtoneUri;
        } else {
            binding.songItem.setText("None");
            selectedRingtoneUri = null;
        }
        if(getPreference("flash").equals("YES"))
        {
            binding.flashSwitch.setChecked(true);
        }
        else
        {
            binding.flashSwitch.setChecked(false);
        }
        if(getPreference("vibration").equals("YES"))
        {
            binding.vibrationSwitch.setChecked(true);
        }
        else
        {
            binding.vibrationSwitch.setChecked(false);
        }

        if(getPreference("ring").equals("YES"))
        {
            binding.ringSwitch.setChecked(true);
        }
        else
        {
            binding.ringSwitch.setChecked(false);
        }
        binding.changeRingtone.setOnClickListener(v -> {
            AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                intent.putExtra("android.intent.extra.ringtone.TYPE", 2);
                intent.putExtra("android.intent.extra.ringtone.TITLE", getResources().getString(R.string.select_tone));
                intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", (Parcelable) null);
                startActivityForResult(intent, 5);
            });

        });



        binding.flashSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                setPreference("flash","YES");
            }
            else
            {
                setPreference("flash","NO");
            }
        });
        binding.vibrationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                setPreference("vibration","YES");
            }
            else
            {
                setPreference("vibration","NO");
            }
        });
        binding.ringSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                setPreference("ring","YES");
            }
            else
            {
                setPreference("ring","NO");
            }

        });

        AudioManager audioManager = (AudioManager) requireContext().getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        binding.volumeSeekbar.setMax(maxVolume);

        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        binding.volumeSeekbar.setProgress(currentVolume);

// Set a listener to handle volume changes
        binding.volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the media volume when the seekbar is moved by the user
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

                // Show a toast message for the final volume setting when the user stops interacting with the seek bar
                if (!fromUser) {
                    String volumeLevelText = "Volume Level: " + progress + "/" + maxVolume;
                    Toast.makeText(requireContext(), volumeLevelText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not used in this case
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Show a toast message for the final volume setting when the user stops interacting with the seek bar
                int finalVolume = seekBar.getProgress();
                String volumeLevelText = "Volume Level: " + finalVolume + "/" + maxVolume;
                Toast.makeText(requireContext(), volumeLevelText, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5 && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null) {
                selectedRingtoneUri = uri;
                String ringtoneName = getRingtoneNameFromUri(uri);
                binding.songItem.setText(ringtoneName);

                // Save the selected ringtone URI in SharedPreferences
                setPreference("ringtone_Name", uri.toString());
                setPreference("ring", "YES"); // Enable ringtone when a ringtone is selected
            } else {
                selectedRingtoneUri = null;
                binding.songItem.setText("None");

                // Clear the saved ringtone URI in SharedPreferences if none selected
                setPreference("ringtone_Name", "");
                setPreference("ring", "NO"); // Disable ringtone if none selected
            }
        }
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5 && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null) {
                selectedRingtoneUri = uri;
                String ringtoneName = getRingtoneNameFromUri(uri);
                binding.songItem.setText(ringtoneName);
                setPreference("ringtone_Name", uri.toString());
                setPreference("ring", "YES"); // Enable ringtone when a ringtone is selected
            } else {
                setPreference("ringtone_Name", ""); // Clear ringtone URI if none selected
                setPreference("ring", "NO"); // Disable ringtone if none selected
            }
        }
    }*/

    // Helper method to get the ringtone name from the URI
    private String getRingtoneNameFromUri(Uri uri) {
        Ringtone ringtone = RingtoneManager.getRingtone(requireActivity(), uri);
        if (ringtone != null) {
            return ringtone.getTitle(requireActivity());
        }
        return "";
    }
    public boolean setPreference(String key, String value) {
        SharedPreferences settings = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getPreference(String key) {
        SharedPreferences settings = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return settings.getString(key, "true");
    }



}
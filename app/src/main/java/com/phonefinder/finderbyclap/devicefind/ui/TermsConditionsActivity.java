package com.phonefinder.finderbyclap.devicefind.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.phonefinder.finderbyclap.devicefind.databinding.ActivityTermsConditionsBinding;

public class TermsConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTermsConditionsBinding binding = ActivityTermsConditionsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        String termsConditions = "Posting rules you should be taking care of before you start interaction on our website/application:\n" +
                "\n Assuring you carry the rights and eligibility to post any form of content you are about to share. We never permit nudism on application/website.\n" +
                "\n We won't allow posting of sexually explicit content, sexually explicit text, images, or audio content depicting extreme sexual acts such as acts of posting Content that drives traffic to pornographic online services. Please note that we may make exceptions based on artistic, educational, historical, documentary, or scientific nature, or where there are other considerable benefits to the public at large.\n" +
                "\n We don't permit encouragement or applaud towards terrorism, formed crime scenes or hate association groups on our application/website. Attempting to sell sexual services or weapons and harmful drugs are also not permitted. We will remove reasonable threats of severity, hate arousing content/speech and the intentionally pointing of private individuals. We do not permit attacks/abuse based on skin colour, native, sex, gender, gender identity, sexual orientation, religion, disability or disease.\n" +
                "\n Graphic violence is not allowed and we may remove videos or images of intense, graphic violence to make sure our application/website stays appropriate for everyone.\n" +
                "\n If anyone or any account is spotted doing the above restricted activities they can be booked and arrested for the same. If viewers spot any violations of the above rules they have the right to report the content to us by using the report feature given in our Find My Phone By Clapping.\n" +
                "\n" +
                "\n";
        binding.textView.setText(termsConditions);
    }
}
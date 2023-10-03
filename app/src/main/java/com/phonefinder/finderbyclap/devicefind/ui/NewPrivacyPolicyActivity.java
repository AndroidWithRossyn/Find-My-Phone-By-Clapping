package com.phonefinder.finderbyclap.devicefind.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.phonefinder.finderbyclap.devicefind.databinding.ActivityNewPrivacyPolicyBinding;

public class NewPrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewPrivacyPolicyBinding binding = ActivityNewPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String privacyPolicy = "This Privacy Policy outlines the practices and policies regarding the collection, use, and disclosure of personal information by applications developed by us. It is important for app developer's to provide clear and transparent information to users about how their personal data is handled. Here is a general framework for a privacy policy for Find My Phone By Clapping.\n" +
                "\n" +
                "1. Collection of Information: \n" +
                "Collection of information refers to the process by which an application gathers data from its users. This data can be categorized into two main types: personal information and non-personal information.\n" +
                "\n" +
                "a. Personal Information:\n" +
                "Personal information refers to data that can be used to identify or contact an individual. When we collecting personal information, it is important to inform users about the purpose for which the data is being collected and obtain their consent, particularly if it is sensitive information. Our App developer's should handle personal information with care and in compliance with applicable data protection laws.\n" +
                "\n" +
                "b. Non-Personal Information:\n" +
                "Non-personal information, also known as anonymous or aggregate data, refers to information that does not directly identify an individual. Non-personal information is generally used to improve the app's functionality, enhance user experiences, and analyze usage patterns. While it does not directly identify individuals, it is still important to inform users about the collection and use of such data and offer options for users to opt-out if desired.\n" +
                "\n" +
                "When describing the collection of information in a privacy policy, it is important to clearly state the types of information being collected (personal and non-personal), explain how the information is collected (through user input, device permissions, or other means), and disclose whether any third parties are involved in the data collection process. Providing transparency and obtaining user consent are key principles in handling and collecting information responsibly.\n" +
                "\n" +
                "2. Use of Information:\n" +
                "The purposes of information usage in an application can vary depending on the nature of the app and its features. The primary purpose of collecting user information is to deliver the core functionality of the application and enhance its performance. User data is often utilized to personalize and customize the user experience within the app. Collected data is frequently used for analytics and research purposes to improve the app's performance and user satisfaction. User information may be used for communication and support purposes & In some cases, user information may be utilized for marketing and promotional purposes.\n" +
                "It's important to explicitly communicate these purposes to users in the privacy policy to ensure transparency and obtain their consent. Additionally, the privacy policy should clarify any specific data processing activities that may be unique to the app or required by applicable laws or regulations.\n" +
                "\n" +
                "3. Data Storage and Security:\n" +
                "Data storage and security are crucial aspects of maintaining user privacy and protecting their information & the security measures implemented to protect user data from unauthorized access, loss, or misuse.\n" +
                "\n" +
                "4. User Choices and Control:\n" +
                "User choices and control are important aspects of privacy that allow individuals to manage their personal information. Consider the following application consent and permissions: \n" +
                "\n" +
                "-CAMERA\n" +
                "-INTERNET\n" +
                "-RECORD_AUDIO\n" +
                "-FOREGROUND_SERVICE\n" +
                "-VIBRATE\n" +
                "-FLASHLIGHT\n" +
                "-REQUEST_IGNORE_BATTERY_OPTIMIZATIONS\n" +
                "-SYSTEM_ALERT_WINDOW\n" +
                "-RECEIVE_BOOT_COMPLETED\n" +
                "-OPPO_COMPONENT_SAFE\n" +
                "-ACCESS_WIFI_STATE\n" +
                "\n" +
                "It is crucial to provide clear instructions and easily accessible mechanisms for users to exercise their choices and control over their personal information. Empowering users to manage their privacy preferences helps build trust and promotes transparency in the app's data practices.\n" +
                "\n" +
                "5. Children's Privacy:\n" +
                "Children's privacy is an important consideration when developing an application, particularly when the app is intended for use by children or when it involves the collection of personal information from children. Clearly define the age range or criteria that define a child according to applicable laws or regulations, such as the Children's Online Privacy Protection Act (COPPA) in the United States or the General Data Protection Regulation (GDPR) in the European Union. \n" +
                "It is essential to adhere to applicable laws and regulations when designing an app that targets children or collects personal information from them. By providing clear information and obtaining verifiable parental consent, the privacy of children can be safeguarded, promoting a safe and trusted environment for their digital interactions.\n" +
                "These policies aim to ensure the safety and privacy of children and provide a positive and suitable experience for families using apps and games.\n" +
                "\n" +
                "Changes to This Privacy Policy\n" +
                "Changes to privacy policies typically occur to reflect updates in data handling practices, legal requirements, or changes in the services provided by us.\n" +
                "\n" +
                "Contact Us\n" +
                "If you have concerns or questions about changes to a privacy policy, it's important to carefully review the updated policy and reach out to us directly for clarification.\n\n";

        binding.textView.setText(privacyPolicy);
    }
}
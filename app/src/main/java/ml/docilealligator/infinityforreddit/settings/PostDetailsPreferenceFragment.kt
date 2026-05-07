package ml.docilealligator.infinityforreddit.settings

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.Preference.SummaryProvider
import ml.docilealligator.infinityforreddit.R
import ml.docilealligator.infinityforreddit.customviews.preference.CustomFontPreferenceFragmentCompat
import ml.docilealligator.infinityforreddit.utils.SharedPreferencesUtils

class PostDetailsPreferenceFragment : CustomFontPreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val preferenceManager = getPreferenceManager()
        preferenceManager.setSharedPreferencesName(SharedPreferencesUtils.POST_DETAILS_SHARED_PREFERENCES_FILE)
        setPreferencesFromResource(R.xml.post_details_preferences, rootKey)

        val commentThreadContinuityCapacityEditTextPreference =
            findPreference<EditTextPreference?>(SharedPreferencesUtils.COMMENT_THREAD_CONTINUITY_CAPACITY)

        if (commentThreadContinuityCapacityEditTextPreference != null) {
            commentThreadContinuityCapacityEditTextPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    try {
                        ((newValue as String?)?.toInt() ?: -1) >= 0
                    } catch (_: NumberFormatException) {
                        false
                    }
                }
            commentThreadContinuityCapacityEditTextPreference.setSummaryProvider(
                SummaryProvider { _: Preference? ->
                    getString(
                        R.string.settings_comment_thread_continuity_capacity_summary,
                        commentThreadContinuityCapacityEditTextPreference.text
                    )
                }
            )
        }
    }
}
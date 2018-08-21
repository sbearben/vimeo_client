package uk.co.victoriajanedavis.vimeo_api_test.util;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Response;

public class VimeoApiServiceUtil {

    public static final String RESPONSE_OK = "ok";
    public static final String RESPONSE_UNAUTHORIZED = "unauthorized";
    public static final String RESPONSE_ERROR = "error";


    public static String basicAuthorizationHeader (String clientId, String clientSecret) {
        String rawString = clientId + ":" + clientSecret;
        return "basic " + Base64.encodeToString(rawString.getBytes(), Base64.NO_WRAP);
    }

    public static long generateIdFromUri (String uri) {
        String[] paths = uri.split("/");
        return Integer.valueOf(paths[paths.length-1]);
    }

    public static String formatSecondsToDuration (int seconds) {
        return String.format(Locale.getDefault(), "%02d:%02d", seconds/60, seconds%60);
    }

    public static String formatIntMetric (long value, String singularMetric, String pluralMetric) {
        if (value < 1000) {
            return String.format(Locale.getDefault(), "%d %s", value, ((value != 1) ? pluralMetric : singularMetric));
        }
        else if (value < 1000000) {
            return String.format(Locale.getDefault(), "%.1fk %s", ((double) value)/1000, pluralMetric);
        }
        else {
            return String.format(Locale.getDefault(), "%.1fm %s", ((double) value)/1000000, pluralMetric);
        }
    }

    public static Spanned formatForUserMetric (long value, String singularMetric, String pluralMetric) {
        StringBuilder sb = new StringBuilder(50);
        String[] lines = formatIntMetric(value, singularMetric, pluralMetric).split(" ");

        sb.append("<font color=#222222>");
        sb.append("<b>");
        sb.append(lines[0]);
        sb.append("</b>");
        sb.append("</font>");
        sb.append("<br/>");
        sb.append("<font color=#A3A3A3>");
        sb.append(lines[1]);
        sb.append("</font>");

        return Html.fromHtml(sb.toString());
    }

    private static String minutesToTimePassed (int minutes) {
        if (minutes < 60) {
            return String.format(Locale.getDefault(), "%d minute" + ((minutes != 1) ? "s" : "") + " ago", minutes);
        }
        else {
            return hoursToTimePassed(minutes/60);
        }
    }

    private static String hoursToTimePassed (int hours) {
        if (hours < 24) {
            return String.format(Locale.getDefault(), "%d hour" + ((hours != 1) ? "s" : "") + " ago", hours);
        }
        else {
            return daysToTimePassed(hours/24);
        }
    }

    private static String daysToTimePassed (int days) {
        if (days < 30) {
            return String.format(Locale.getDefault(), "%d day" + ((days != 1) ? "s" : "") + " ago", days);
        }
        else if (days < 365) {
            int months = days/30;
            return String.format(Locale.getDefault(), "%d month" + ((months != 1) ? "s" : "") + " ago", months);
        }
        else {
            int years = days/365;
            return String.format(Locale.getDefault(), "%d year" + ((years != 1) ? "s" : "") + " ago", years);
        }
    }

    private static int getDifferenceMinutes (Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (60 * 1000) + 1);
    }

    public static String dateCreatedToTimePassed (Date dateCreated) {
        return minutesToTimePassed (getDifferenceMinutes(dateCreated, new Date()));
    }

    public static String formatVideoAgeAndPlays (Long plays, Date dateCreated) {
        if (plays == null) {
            return dateCreatedToTimePassed(dateCreated);
        }
        else {
            return String.format(Locale.getDefault(), "%s ⋅ %s", formatIntMetric(plays, "play", "plays"),
                    dateCreatedToTimePassed(dateCreated));
        }
    }

    public static String formatVideoCountAndFollowers (int videos, int followers) {
        return String.format(Locale.getDefault(), "%s ⋅ %s", formatIntMetric(videos, "video", "videos"),
                formatIntMetric(followers, "follower", "followers"));
    }

    public static String responseType (Response response) {
        switch (response.code()) {
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_ACCEPTED:
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
                return RESPONSE_OK;

            case HttpsURLConnection.HTTP_UNAUTHORIZED:
                return RESPONSE_UNAUTHORIZED;

            default:
                return  RESPONSE_ERROR;
        }
    }

    public static void hideOrDisplayTextViewIfNullString (TextView textView, String text) {
        textView.setText(text);
        textView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }
}

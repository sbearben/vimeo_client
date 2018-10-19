package uk.co.victoriajanedavis.vimeo_api_test;

import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

public class VimeoTextUtlTest {

    @Test
    public void generateIdFromUriTest1() {
        String uri = "/users/2184989";
        long id = VimeoTextUtil.generateIdFromUri(uri);

        assertEquals(2184989, id);
    }

    @Test
    public void generateIdFromUriTest2() {
        String uri = "/users/2184989/";
        long id = VimeoTextUtil.generateIdFromUri(uri);

        assertEquals(2184989, id);
    }

    @Test
    public void generateVideoIdFromCommentUriTest() {
        String uri = "/videos/286294839/comments";
        long id = VimeoTextUtil.generateVideoIdFromCommentUri(uri);

        assertEquals(286294839, id);
    }

    @Test
    public void formatSecondsToDurationTest() {
        assertEquals("01:40", VimeoTextUtil.formatSecondsToDuration(100));
        assertEquals("09:46", VimeoTextUtil.formatSecondsToDuration(586));
        assertEquals("00:15", VimeoTextUtil.formatSecondsToDuration(15));
    }

    @Test
    public void formatIntMetricTest1() {
        String metric = VimeoTextUtil.formatIntMetric(945, "like", "likes");
        assertEquals("945 likes", metric);

        metric = VimeoTextUtil.formatIntMetric(0, "like", "likes");
        assertEquals("0 likes", metric);

        metric = VimeoTextUtil.formatIntMetric(1, "like", "likes");
        assertEquals("1 like", metric);

        metric = VimeoTextUtil.formatIntMetric(999, "like", "likes");
        assertEquals("999 likes", metric);
    }

    @Test
    public void formatIntMetricTest2() {
        String metric = VimeoTextUtil.formatIntMetric(1000, "comment", "comments");
        assertEquals("1.0k comments", metric);

        metric = VimeoTextUtil.formatIntMetric(462436, "comment", "comments");
        assertEquals("462.4k comments", metric);

        metric = VimeoTextUtil.formatIntMetric(462636, "comment", "comments");
        assertEquals("462.6k comments", metric);

        metric = VimeoTextUtil.formatIntMetric(462676, "comment", "comments");
        assertNotEquals("462.6k comments", metric);
    }

    @Test
    public void formatIntMetricTest3() {
        String metric = VimeoTextUtil.formatIntMetric(1434374, "share", "shares");
        assertEquals("1.4m shares", metric);

        metric = VimeoTextUtil.formatIntMetric(9456868, "share", "shares");
        assertEquals("9.5m shares", metric);
    }

    @Test
    public void formatVideoCountAndFollowers() {
        String str = VimeoTextUtil.formatVideoCountAndFollowers(23, 32523);
        assertEquals("23 videos ⋅ 32.5k followers", str);

        str = VimeoTextUtil.formatVideoCountAndFollowers(1, 1);
        assertEquals("1 video ⋅ 1 follower", str);
    }

}

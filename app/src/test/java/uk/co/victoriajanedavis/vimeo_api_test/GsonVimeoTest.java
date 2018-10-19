package uk.co.victoriajanedavis.vimeo_api_test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoConnection;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoMetadataVideo;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoPictureSizes;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoPictures;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoUser;
import uk.co.victoriajanedavis.vimeo_api_test.data.model.VimeoVideo;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoMetadataUserDeserializer;
import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoMetadataVideoDeserializer;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

public class GsonVimeoTest {

    private static final String TAG = "GsonVimeoTest";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private Gson mGson;


    @Before
    public void setup() {
        mGson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .registerTypeHierarchyAdapter(VimeoMetadataUser.class, new VimeoMetadataUserDeserializer())
                .registerTypeHierarchyAdapter(VimeoMetadataVideo.class, new VimeoMetadataVideoDeserializer())
                .create();
    }

    @Test
    public void vimeoUserWorks() {
        String json =
                "{\"uri\": \"/users/2184989\",\n" +
                        "    \"name\": \"Kevin McGloughlin\",\n" +
                        "    \"link\": \"https://vimeo.com/kevinmcgloughlin\",\n" +
                        "    \"location\": \"Sligo,Ireland\",\n" +
                        "    \"bio\": \"I'm here to share my imagination with you.\\n\\nkmcgloughlin@gmail.com\\n\\nhttps://www.kevinmcgloughlin.com/\\n\\nhttps://www.instagram.com/kevinmcgloughlin_gram/\",\n" +
                        "    \"created_time\": \"2009-08-18T23:44:12+00:00\",\n" +
                        "    \"pictures\": {\n" +
                        "        \"uri\": \"/users/2184989/pictures/6128293\",\n" +
                        "        \"active\": true,\n" +
                        "        \"type\": \"custom\",\n" +
                        "        \"sizes\": [\n" +
                        "            {\n" +
                        "                \"width\": 30,\n" +
                        "                \"height\": 30,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_30x30\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 75,\n" +
                        "                \"height\": 75,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_75x75\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 100,\n" +
                        "                \"height\": 100,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_100x100\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 300,\n" +
                        "                \"height\": 300,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_300x300\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 72,\n" +
                        "                \"height\": 72,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_72x72\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 144,\n" +
                        "                \"height\": 144,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_144x144\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 216,\n" +
                        "                \"height\": 216,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_216x216\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 288,\n" +
                        "                \"height\": 288,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_288x288\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"width\": 360,\n" +
                        "                \"height\": 360,\n" +
                        "                \"link\": \"https://i.vimeocdn.com/portrait/6128293_360x360\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"resource_key\": \"6e51e5488212981cefed00026eb966d31416c8ec\"\n" +
                        "    },\n" +
                        "    \"websites\": [\n" +
                        "        {\n" +
                        "            \"name\": \"Instagram\",\n" +
                        "            \"link\": \"https://www.instagram.com/kevinmcgloughlin_gram/\",\n" +
                        "            \"description\": null\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"name\": \"Website\",\n" +
                        "            \"link\": \"https://www.kevinmcgloughlin.com/\",\n" +
                        "            \"description\": \"Works by Kevin McGloughlin\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"metadata\": {\n" +
                        "        \"connections\": {\n" +
                        "            \"albums\": {\n" +
                        "                \"uri\": \"/users/2184989/albums\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 0\n" +
                        "            },\n" +
                        "            \"appearances\": {\n" +
                        "                \"uri\": \"/users/2184989/appearances\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 4\n" +
                        "            },\n" +
                        "            \"categories\": {\n" +
                        "                \"uri\": \"/users/2184989/categories\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 0\n" +
                        "            },\n" +
                        "            \"channels\": {\n" +
                        "                \"uri\": \"/users/2184989/channels\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 23\n" +
                        "            },\n" +
                        "            \"feed\": {\n" +
                        "                \"uri\": \"/users/2184989/feed\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ]\n" +
                        "            },\n" +
                        "            \"followers\": {\n" +
                        "                \"uri\": \"/users/2184989/followers\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 4817\n" +
                        "            },\n" +
                        "            \"following\": {\n" +
                        "                \"uri\": \"/users/2184989/following\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 177\n" +
                        "            },\n" +
                        "            \"groups\": {\n" +
                        "                \"uri\": \"/users/2184989/groups\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 39\n" +
                        "            },\n" +
                        "            \"likes\": {\n" +
                        "                \"uri\": \"/users/2184989/likes\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 731\n" +
                        "            },\n" +
                        "            \"moderated_channels\": {\n" +
                        "                \"uri\": \"/users/2184989/channels?filter=moderated\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 0\n" +
                        "            },\n" +
                        "            \"portfolios\": {\n" +
                        "                \"uri\": \"/users/2184989/portfolios\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 0\n" +
                        "            },\n" +
                        "            \"videos\": {\n" +
                        "                \"uri\": \"/users/2184989/videos\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 31\n" +
                        "            },\n" +
                        "            \"watchlater\": {\n" +
                        "                \"uri\": \"/users/2184989/watchlater\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 40\n" +
                        "            },\n" +
                        "            \"shared\": {\n" +
                        "                \"uri\": \"/users/2184989/shared/videos\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\"\n" +
                        "                ],\n" +
                        "                \"total\": 74\n" +
                        "            },\n" +
                        "            \"pictures\": {\n" +
                        "                \"uri\": \"/users/2184989/pictures\",\n" +
                        "                \"options\": [\n" +
                        "                    \"GET\",\n" +
                        "                    \"POST\"\n" +
                        "                ],\n" +
                        "                \"total\": 1\n" +
                        "            }\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"resource_key\": \"509b5e4a47498888c436f20e7e9571850fface5d\",\n" +
                        "    \"account\": \"pro\"\n" +
                        "}";

        VimeoUser vimeoUser = mGson.fromJson(json, VimeoUser.class);

        assertEquals(vimeoUser.getUri(), "/users/2184989");
        assertEquals(vimeoUser.getName(), "Kevin McGloughlin");
        assertEquals(vimeoUser.getLocation(), "Sligo,Ireland");
        assertEquals(vimeoUser.getBio(), "I'm here to share my imagination with you.\n\nkmcgloughlin@gmail.com\n\nhttps://www.kevinmcgloughlin.com/\n\nhttps://www.instagram.com/kevinmcgloughlin_gram/");

        VimeoPictures userPictures = vimeoUser.getPictures();
        assertEquals(userPictures.getUri(), "/users/2184989/pictures/6128293");

        VimeoPictureSizes pictureSize = userPictures.getSizesList().get(0);
        assertEquals(pictureSize.getWidth(), 30);
        assertEquals(pictureSize.getHeight(), 30);
        assertEquals(pictureSize.getLink(), "https://i.vimeocdn.com/portrait/6128293_30x30");

        VimeoConnection followersConnection = vimeoUser.getMetadata().getFollowersConnection();
        assertEquals(followersConnection.getUri(), "/users/2184989/followers");
        assertEquals(followersConnection.getTotal(), 4817);

        VimeoConnection followingConnection = vimeoUser.getMetadata().getFollowingConnection();
        assertEquals(followingConnection.getUri(), "/users/2184989/following");
        assertEquals(followingConnection.getTotal(), 177);

        VimeoConnection likesConnection = vimeoUser.getMetadata().getLikesConnection();
        assertEquals(likesConnection.getUri(), "/users/2184989/likes");
        assertEquals(likesConnection.getTotal(), 731);
    }

    @Test
    public void vimeoVideoWorks() {
        String json = "{\n" +
                "    \"uri\": \"/videos/279643508\",\n" +
                "    \"name\": \"Talos - D.O.A.M. (Death Of A Muse) Pt III\",\n" +
                "    \"description\": \"Directed by Kevin McGloughlin\\n\\nMy aspiration for 'DOAM' was to blur the lines of distance and scale, representing our human interrelationship amidst varying environments.\\n\\nhttps://www.instagram.com/kevinmcgloughlin_gram/\\n\\nTaken from 'Then There Was War' EP, included on the deluxe edition of 'Wild Alee', out now: https://talos.lnk.to/WildAleeDLXID\\n\\nFollow Talos: \\nSpotify: http://sptfy.com/Tfv \\nApple: http://apple.co/2dOro0X \\nFacebook: http://www.facebook.com/thisistalos\\nTwitter: http://www.twitter.com/thisistalos \\nInstagram: http://www.instagram.com/thisistalos \\nWebsite: http://www.thisistalos.com\",\n" +
                "    \"link\": \"https://vimeo.com/kevinmcgloughlin/doam\",\n" +
                "    \"duration\": 165,\n" +
                "    \"width\": 1920,\n" +
                "    \"language\": null,\n" +
                "    \"height\": 1080,\n" +
                "    \"embed\": {\n" +
                "        \"html\": \"<iframe src=\\\"https://player.vimeo.com/video/279643508?badge=0&autopause=0&player_id=0&app_id=130147\\\" width=\\\"1920\\\" height=\\\"1080\\\" frameborder=\\\"0\\\" title=\\\"Talos - D.O.A.M. (Death Of A Muse) Pt III\\\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>\",\n" +
                "        \"badges\": {\n" +
                "            \"hdr\": false,\n" +
                "            \"live\": {\n" +
                "                \"streaming\": false,\n" +
                "                \"archived\": false\n" +
                "            },\n" +
                "            \"staff_pick\": {\n" +
                "                \"normal\": true,\n" +
                "                \"best_of_the_month\": false,\n" +
                "                \"best_of_the_year\": false,\n" +
                "                \"premiere\": false\n" +
                "            },\n" +
                "            \"vod\": false,\n" +
                "            \"weekend_challenge\": false\n" +
                "        }\n" +
                "    },\n" +
                "    \"created_time\": \"2018-07-12T11:59:10+00:00\",\n" +
                "    \"modified_time\": \"2018-07-18T02:30:40+00:00\",\n" +
                "    \"release_time\": \"2018-07-12T11:59:10+00:00\",\n" +
                "    \"content_rating\": [\n" +
                "        \"safe\"\n" +
                "    ],\n" +
                "    \"license\": null,\n" +
                "    \"privacy\": {\n" +
                "        \"view\": \"anybody\",\n" +
                "        \"embed\": \"public\",\n" +
                "        \"download\": false,\n" +
                "        \"add\": true,\n" +
                "        \"comments\": \"anybody\"\n" +
                "    },\n" +
                "    \"pictures\": {\n" +
                "        \"uri\": \"/videos/279643508/pictures/712875795\",\n" +
                "        \"active\": true,\n" +
                "        \"type\": \"custom\",\n" +
                "        \"sizes\": [\n" +
                "            {\n" +
                "                \"width\": 100,\n" +
                "                \"height\": 75,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_100x75.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_100x75.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"width\": 200,\n" +
                "                \"height\": 150,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_200x150.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_200x150.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"width\": 295,\n" +
                "                \"height\": 166,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_295x166.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_295x166.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"width\": 640,\n" +
                "                \"height\": 360,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_640x360.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_640x360.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"width\": 960,\n" +
                "                \"height\": 540,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_960x540.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_960x540.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"width\": 1280,\n" +
                "                \"height\": 720,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_1280x720.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_1280x720.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"width\": 1920,\n" +
                "                \"height\": 1080,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_1920x1080.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_1920x1080.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"width\": 1280,\n" +
                "                \"height\": 720,\n" +
                "                \"link\": \"https://i.vimeocdn.com/video/712875795_1280x720.jpg?r=pad\",\n" +
                "                \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_1280x720.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"resource_key\": \"eee7516575222a24cfafbbf5472f77f2e2b05c92\"\n" +
                "    },\n" +
                "    \"tags\": [\n" +
                "        {\n" +
                "            \"uri\": \"/tags/sligo\",\n" +
                "            \"name\": \"sligo\",\n" +
                "            \"tag\": \"sligo\",\n" +
                "            \"canonical\": \"sligo\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/sligo/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 807\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"60f7668c0984781d71d66803cc6dac18f63357db\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/landscape\",\n" +
                "            \"name\": \"landscape\",\n" +
                "            \"tag\": \"landscape\",\n" +
                "            \"canonical\": \"landscape\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/landscape/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 42951\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"cb0e0ba20546282d05a5bdce140c059b61a4d036\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/rural\",\n" +
                "            \"name\": \"rural\",\n" +
                "            \"tag\": \"rural\",\n" +
                "            \"canonical\": \"rural\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/rural/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 8849\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"78403788f1f02289c35c4975f10b7a2db42d7aa2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/mavic\",\n" +
                "            \"name\": \"mavic\",\n" +
                "            \"tag\": \"mavic\",\n" +
                "            \"canonical\": \"mavic\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/mavic/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 7477\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"16b9faf3aca7ba43b0951f62400284e77e03ec93\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/drone\",\n" +
                "            \"name\": \"drone\",\n" +
                "            \"tag\": \"drone\",\n" +
                "            \"canonical\": \"drone\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/drone/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 156174\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"96cfdceff35323052e0100e196807559a1be5540\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/dji\",\n" +
                "            \"name\": \"dji\",\n" +
                "            \"tag\": \"dji\",\n" +
                "            \"canonical\": \"dji\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/dji/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 70128\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"f4db13787ffcf8fe658bc49fcbe179e9e193af9a\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/scenery\",\n" +
                "            \"name\": \"scenery\",\n" +
                "            \"tag\": \"scenery\",\n" +
                "            \"canonical\": \"scenery\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/scenery/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 6858\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"e347145ef2f766a80f63d14258d06ebe30acd87d\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/experimental\",\n" +
                "            \"name\": \"experimental\",\n" +
                "            \"tag\": \"experimental\",\n" +
                "            \"canonical\": \"experimental\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/experimental/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 151688\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"5c7fc37d5b8fa045223907d78c67e927baf135b3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/talos\",\n" +
                "            \"name\": \"talos\",\n" +
                "            \"tag\": \"talos\",\n" +
                "            \"canonical\": \"talos\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/talos/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 67\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"4a79d258bc37af377506631b3e8688b9de8aad92\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/space\",\n" +
                "            \"name\": \"space\",\n" +
                "            \"tag\": \"space\",\n" +
                "            \"canonical\": \"space\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/space/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 58983\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"518b0c1b03debf64f68d10474a6a5714234fe8e7\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/feelgoodlost\",\n" +
                "            \"name\": \"feelgoodlost\",\n" +
                "            \"tag\": \"feelgoodlost\",\n" +
                "            \"canonical\": \"feelgoodlost\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/feelgoodlost/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 38\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"84148e01eb586ca9c75a9bbfbd4dd58fb0522945\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/perception\",\n" +
                "            \"name\": \"perception\",\n" +
                "            \"tag\": \"perception\",\n" +
                "            \"canonical\": \"perception\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/perception/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 3935\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"1cfc88ab8db59eedea8faedb7bdaf888a0ba3d64\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/tags/scale\",\n" +
                "            \"name\": \"scale\",\n" +
                "            \"tag\": \"scale\",\n" +
                "            \"canonical\": \"scale\",\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/tags/scale/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 3745\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"a1a1626e003870d4885d203f02c5c75a73b433d3\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"stats\": {\n" +
                "        \"plays\": null\n" +
                "    },\n" +
                "    \"categories\": [\n" +
                "        {\n" +
                "            \"uri\": \"/categories/experimental\",\n" +
                "            \"name\": \"Experimental\",\n" +
                "            \"link\": \"https://vimeo.com/categories/experimental\",\n" +
                "            \"top_level\": true,\n" +
                "            \"pictures\": {\n" +
                "                \"uri\": \"/videos/279643508/pictures/712875795\",\n" +
                "                \"active\": true,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 75,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_100x75.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_100x75.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 200,\n" +
                "                        \"height\": 150,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_200x150.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_200x150.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 295,\n" +
                "                        \"height\": 166,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_295x166.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_295x166.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 640,\n" +
                "                        \"height\": 360,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_640x360.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_640x360.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 960,\n" +
                "                        \"height\": 540,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_960x540.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_960x540.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1280,\n" +
                "                        \"height\": 720,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_1280x720.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_1280x720.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1920,\n" +
                "                        \"height\": 1080,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_1920x1080.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_1920x1080.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"eee7516575222a24cfafbbf5472f77f2e2b05c92\"\n" +
                "            },\n" +
                "            \"last_video_featured_time\": \"2018-07-17T21:30:19+00:00\",\n" +
                "            \"parent\": null,\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"channels\": {\n" +
                "                        \"uri\": \"/categories/experimental/channels\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 22730\n" +
                "                    },\n" +
                "                    \"groups\": {\n" +
                "                        \"uri\": \"/categories/experimental/groups\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 5117\n" +
                "                    },\n" +
                "                    \"users\": {\n" +
                "                        \"uri\": \"/categories/experimental/users\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 949830\n" +
                "                    },\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/categories/experimental/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 420236\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"subcategories\": [\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/experimental/subcategories/freshideas\",\n" +
                "                    \"name\": \"Fresh ideas\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/experimental/freshideas/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/experimental/subcategories/processing\",\n" +
                "                    \"name\": \"Generative Art & Processing\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/experimental/processing/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/experimental/subcategories/supercuts\",\n" +
                "                    \"name\": \"Remix & Supercuts\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/experimental/supercuts/videos\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"icon\": {\n" +
                "                \"uri\": \"/categories/experimental/icon\",\n" +
                "                \"active\": false,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 20,\n" +
                "                        \"height\": 20,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=20&h=20&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 40,\n" +
                "                        \"height\": 40,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=40&h=40&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 60,\n" +
                "                        \"height\": 60,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=60&h=60&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 80,\n" +
                "                        \"height\": 80,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=80&h=80&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 100,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=100&h=100&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 120,\n" +
                "                        \"height\": 120,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=120&h=120&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 140,\n" +
                "                        \"height\": 140,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=140&h=140&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 160,\n" +
                "                        \"height\": 160,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=160&h=160&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 180,\n" +
                "                        \"height\": 180,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_experimental_64.png%3FDEV%3Fv%3D2&w=180&h=180&r=pad&f=png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"7b3256ccba8ec8b0e591b8b4abe8f62429fabce7\"\n" +
                "            },\n" +
                "            \"resource_key\": \"9504902091a3dd31d140740831ffe1d6b5f489b1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/categories/travel\",\n" +
                "            \"name\": \"Travel\",\n" +
                "            \"link\": \"https://vimeo.com/categories/travel\",\n" +
                "            \"top_level\": true,\n" +
                "            \"pictures\": {\n" +
                "                \"uri\": \"/videos/212122024/pictures/628169251\",\n" +
                "                \"active\": true,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 75,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/628169251_100x75.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F628169251_100x75.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 200,\n" +
                "                        \"height\": 150,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/628169251_200x150.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F628169251_200x150.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 295,\n" +
                "                        \"height\": 166,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/628169251_295x166.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F628169251_295x166.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 640,\n" +
                "                        \"height\": 360,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/628169251_640x360.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F628169251_640x360.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 960,\n" +
                "                        \"height\": 540,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/628169251_960x540.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F628169251_960x540.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1280,\n" +
                "                        \"height\": 720,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/628169251_1280x720.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F628169251_1280x720.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1920,\n" +
                "                        \"height\": 1080,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/628169251_1920x1080.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F628169251_1920x1080.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"42f862097b061c747184518a137d4c08d72478c1\"\n" +
                "            },\n" +
                "            \"last_video_featured_time\": \"2018-07-17T21:40:52+00:00\",\n" +
                "            \"parent\": null,\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"channels\": {\n" +
                "                        \"uri\": \"/categories/travel/channels\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 30780\n" +
                "                    },\n" +
                "                    \"groups\": {\n" +
                "                        \"uri\": \"/categories/travel/groups\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 6120\n" +
                "                    },\n" +
                "                    \"users\": {\n" +
                "                        \"uri\": \"/categories/travel/users\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 1187357\n" +
                "                    },\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/categories/travel/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 273661\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"subcategories\": [\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/africa\",\n" +
                "                    \"name\": \"Africa\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/africa/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/antarctica\",\n" +
                "                    \"name\": \"Antarctica\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/antarctica/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/asia\",\n" +
                "                    \"name\": \"Asia\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/asia/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/australasia\",\n" +
                "                    \"name\": \"Australasia\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/australasia/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/europe\",\n" +
                "                    \"name\": \"Europe\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/europe/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/northamerica\",\n" +
                "                    \"name\": \"North America\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/northamerica/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/southamerica\",\n" +
                "                    \"name\": \"South America\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/southamerica/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/travel/subcategories/space\",\n" +
                "                    \"name\": \"Space!\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/travel/space/videos\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"icon\": {\n" +
                "                \"uri\": \"/categories/travel/icon\",\n" +
                "                \"active\": false,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 20,\n" +
                "                        \"height\": 20,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=20&h=20&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 40,\n" +
                "                        \"height\": 40,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=40&h=40&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 60,\n" +
                "                        \"height\": 60,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=60&h=60&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 80,\n" +
                "                        \"height\": 80,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=80&h=80&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 100,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=100&h=100&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 120,\n" +
                "                        \"height\": 120,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=120&h=120&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 140,\n" +
                "                        \"height\": 140,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=140&h=140&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 160,\n" +
                "                        \"height\": 160,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=160&h=160&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 180,\n" +
                "                        \"height\": 180,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_travel_64.png%3FDEV%3Fv%3D2&w=180&h=180&r=pad&f=png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"917b55fc3191093c1868527ecf6010e1b3cb5efd\"\n" +
                "            },\n" +
                "            \"resource_key\": \"9884bb14a11d9d80337387d4ae21d90e09bee867\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/categories/animation\",\n" +
                "            \"name\": \"Animation\",\n" +
                "            \"link\": \"https://vimeo.com/categories/animation\",\n" +
                "            \"top_level\": true,\n" +
                "            \"pictures\": {\n" +
                "                \"uri\": \"/videos/279813552/pictures/713133825\",\n" +
                "                \"active\": true,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 75,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/713133825_100x75.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F713133825_100x75.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 200,\n" +
                "                        \"height\": 150,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/713133825_200x150.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F713133825_200x150.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 295,\n" +
                "                        \"height\": 166,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/713133825_295x166.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F713133825_295x166.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 640,\n" +
                "                        \"height\": 360,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/713133825_640x360.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F713133825_640x360.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 960,\n" +
                "                        \"height\": 540,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/713133825_960x540.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F713133825_960x540.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1280,\n" +
                "                        \"height\": 720,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/713133825_1280x720.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F713133825_1280x720.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1920,\n" +
                "                        \"height\": 1080,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/713133825_1920x1080.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F713133825_1920x1080.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"a021fc8fd5014518bbb0d3d9f0cc2ab7e04579b2\"\n" +
                "            },\n" +
                "            \"last_video_featured_time\": \"2018-07-17T21:39:34+00:00\",\n" +
                "            \"parent\": null,\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"channels\": {\n" +
                "                        \"uri\": \"/categories/animation/channels\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 35014\n" +
                "                    },\n" +
                "                    \"groups\": {\n" +
                "                        \"uri\": \"/categories/animation/groups\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 8708\n" +
                "                    },\n" +
                "                    \"users\": {\n" +
                "                        \"uri\": \"/categories/animation/users\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 1379072\n" +
                "                    },\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/categories/animation/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 601644\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"subcategories\": [\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/animation/subcategories/2d\",\n" +
                "                    \"name\": \"2D\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/animation/2d/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/animation/subcategories/3d\",\n" +
                "                    \"name\": \"3D/CG\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/animation/3d/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/animation/subcategories/mograph\",\n" +
                "                    \"name\": \"Mograph\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/animation/mograph/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/animation/subcategories/projectionmapping\",\n" +
                "                    \"name\": \"Projection Mapping\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/animation/projectionmapping/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/animation/subcategories/stopmotion\",\n" +
                "                    \"name\": \"Stop Frame\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/animation/stopmotion/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/animation/subcategories/vfx\",\n" +
                "                    \"name\": \"VFX\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/animation/vfx/videos\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"icon\": {\n" +
                "                \"uri\": \"/categories/animation/icon\",\n" +
                "                \"active\": false,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 20,\n" +
                "                        \"height\": 20,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=20&h=20&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 40,\n" +
                "                        \"height\": 40,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=40&h=40&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 60,\n" +
                "                        \"height\": 60,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=60&h=60&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 80,\n" +
                "                        \"height\": 80,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=80&h=80&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 100,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=100&h=100&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 120,\n" +
                "                        \"height\": 120,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=120&h=120&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 140,\n" +
                "                        \"height\": 140,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=140&h=140&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 160,\n" +
                "                        \"height\": 160,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=160&h=160&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 180,\n" +
                "                        \"height\": 180,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_animation_64.png%3FDEV%3Fv%3D2&w=180&h=180&r=pad&f=png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"70bd0c02f145b5d90fa4d2e45bf63c1f0c0ffac9\"\n" +
                "            },\n" +
                "            \"resource_key\": \"a622babbdfc1bd91a993b011a47b5a02330f5812\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/categories/experimental/subcategories/freshideas\",\n" +
                "            \"name\": \"Fresh ideas\",\n" +
                "            \"link\": \"https://vimeo.com/categories/experimental/freshideas/videos\",\n" +
                "            \"top_level\": false,\n" +
                "            \"pictures\": {\n" +
                "                \"uri\": \"/videos/279643508/pictures/712875795\",\n" +
                "                \"active\": true,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 75,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_100x75.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_100x75.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 200,\n" +
                "                        \"height\": 150,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_200x150.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_200x150.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 295,\n" +
                "                        \"height\": 166,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_295x166.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_295x166.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 640,\n" +
                "                        \"height\": 360,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_640x360.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_640x360.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 960,\n" +
                "                        \"height\": 540,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_960x540.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_960x540.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1280,\n" +
                "                        \"height\": 720,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_1280x720.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_1280x720.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1920,\n" +
                "                        \"height\": 1080,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/712875795_1920x1080.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F712875795_1920x1080.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"eee7516575222a24cfafbbf5472f77f2e2b05c92\"\n" +
                "            },\n" +
                "            \"last_video_featured_time\": \"2018-07-17T21:30:20+00:00\",\n" +
                "            \"parent\": {\n" +
                "                \"uri\": \"/categories/experimental\",\n" +
                "                \"name\": \"Experimental\",\n" +
                "                \"link\": \"https://vimeo.com/categories/experimental\"\n" +
                "            },\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"channels\": {\n" +
                "                        \"uri\": \"/categories/experimental/subcategories/freshideas/channels\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 13\n" +
                "                    },\n" +
                "                    \"groups\": {\n" +
                "                        \"uri\": \"/categories/experimental/subcategories/freshideas/groups\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 5\n" +
                "                    },\n" +
                "                    \"users\": {\n" +
                "                        \"uri\": \"/categories/experimental/subcategories/freshideas/users\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 6474\n" +
                "                    },\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/categories/experimental/subcategories/freshideas/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 48781\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"resource_key\": \"6a63d65b5dc2690d7efe52857e490adbe310324b\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"uri\": \"/categories/personal\",\n" +
                "            \"name\": \"Personal\",\n" +
                "            \"link\": \"https://vimeo.com/categories/personal\",\n" +
                "            \"top_level\": true,\n" +
                "            \"pictures\": {\n" +
                "                \"uri\": \"/videos/168422600/pictures/572931372\",\n" +
                "                \"active\": true,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 75,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/572931372_100x75.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F572931372_100x75.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 200,\n" +
                "                        \"height\": 150,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/572931372_200x150.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F572931372_200x150.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 295,\n" +
                "                        \"height\": 166,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/572931372_295x166.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F572931372_295x166.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 640,\n" +
                "                        \"height\": 360,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/572931372_640x360.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F572931372_640x360.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 960,\n" +
                "                        \"height\": 540,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/572931372_960x540.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F572931372_960x540.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1280,\n" +
                "                        \"height\": 720,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/572931372_1280x720.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F572931372_1280x720.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 1920,\n" +
                "                        \"height\": 1080,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/video/572931372_1920x1080.jpg?r=pad\",\n" +
                "                        \"link_with_play_button\": \"https://i.vimeocdn.com/filter/overlay?src0=https%3A%2F%2Fi.vimeocdn.com%2Fvideo%2F572931372_1920x1080.jpg&src1=http%3A%2F%2Ff.vimeocdn.com%2Fp%2Fimages%2Fcrawler_play.png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"37caee761c82f805b22f196d4389c16b85e38e73\"\n" +
                "            },\n" +
                "            \"last_video_featured_time\": \"2017-04-10T00:55:59+00:00\",\n" +
                "            \"parent\": null,\n" +
                "            \"metadata\": {\n" +
                "                \"connections\": {\n" +
                "                    \"channels\": {\n" +
                "                        \"uri\": \"/categories/personal/channels\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 47365\n" +
                "                    },\n" +
                "                    \"groups\": {\n" +
                "                        \"uri\": \"/categories/personal/groups\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 11382\n" +
                "                    },\n" +
                "                    \"users\": {\n" +
                "                        \"uri\": \"/categories/personal/users\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 1136153\n" +
                "                    },\n" +
                "                    \"videos\": {\n" +
                "                        \"uri\": \"/categories/personal/videos\",\n" +
                "                        \"options\": [\n" +
                "                            \"GET\"\n" +
                "                        ],\n" +
                "                        \"total\": 162346\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"subcategories\": [\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/personal/subcategories/cameo\",\n" +
                "                    \"name\": \"Cameo\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/personal/cameo/videos\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"uri\": \"/categories/personal/subcategories/stories\",\n" +
                "                    \"name\": \"Personal Stories\",\n" +
                "                    \"link\": \"https://vimeo.com/categories/personal/stories/videos\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"icon\": {\n" +
                "                \"uri\": \"/categories/personal/icon\",\n" +
                "                \"active\": false,\n" +
                "                \"type\": \"custom\",\n" +
                "                \"sizes\": [\n" +
                "                    {\n" +
                "                        \"width\": 20,\n" +
                "                        \"height\": 20,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=20&h=20&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 40,\n" +
                "                        \"height\": 40,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=40&h=40&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 60,\n" +
                "                        \"height\": 60,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=60&h=60&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 80,\n" +
                "                        \"height\": 80,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=80&h=80&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 100,\n" +
                "                        \"height\": 100,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=100&h=100&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 120,\n" +
                "                        \"height\": 120,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=120&h=120&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 140,\n" +
                "                        \"height\": 140,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=140&h=140&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 160,\n" +
                "                        \"height\": 160,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=160&h=160&r=pad&f=png\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"width\": 180,\n" +
                "                        \"height\": 180,\n" +
                "                        \"link\": \"https://i.vimeocdn.com/grab?s=https%3A%2F%2Ff.vimeocdn.com%2Fimages_v6%2Fcategories%2Firis_icon_personal_64.png%3FDEV%3Fv%3D2&w=180&h=180&r=pad&f=png\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"resource_key\": \"70560bfbf564a01613741ee161610dbb3b897aec\"\n" +
                "            },\n" +
                "            \"resource_key\": \"d8707722dbe8a1f0c41d4afa0b91c0622bd3eb8b\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"metadata\": {\n" +
                "        \"connections\": {\n" +
                "            \"comments\": {\n" +
                "                \"uri\": \"/videos/279643508/comments\",\n" +
                "                \"options\": [\n" +
                "                    \"GET\"\n" +
                "                ],\n" +
                "                \"total\": 12\n" +
                "            },\n" +
                "            \"credits\": {\n" +
                "                \"uri\": \"/videos/279643508/credits\",\n" +
                "                \"options\": [\n" +
                "                    \"GET\",\n" +
                "                    \"POST\"\n" +
                "                ],\n" +
                "                \"total\": 1\n" +
                "            },\n" +
                "            \"likes\": {\n" +
                "                \"uri\": \"/videos/279643508/likes\",\n" +
                "                \"options\": [\n" +
                "                    \"GET\"\n" +
                "                ],\n" +
                "                \"total\": 199\n" +
                "            },\n" +
                "            \"pictures\": {\n" +
                "                \"uri\": \"/videos/279643508/pictures\",\n" +
                "                \"options\": [\n" +
                "                    \"GET\",\n" +
                "                    \"POST\"\n" +
                "                ],\n" +
                "                \"total\": 1\n" +
                "            },\n" +
                "            \"texttracks\": {\n" +
                "                \"uri\": \"/videos/279643508/texttracks\",\n" +
                "                \"options\": [\n" +
                "                    \"GET\",\n" +
                "                    \"POST\"\n" +
                "                ],\n" +
                "                \"total\": 0\n" +
                "            },\n" +
                "            \"related\": null,\n" +
                "            \"recommendations\": {\n" +
                "                \"uri\": \"/videos/279643508/recommendations\",\n" +
                "                \"options\": [\n" +
                "                    \"GET\"\n" +
                "                ]\n" +
                "            }\n" +
                "        },\n" +
                "        \"interactions\": {\n" +
                "            \"report\": {\n" +
                "                \"uri\": \"/videos/279643508/report\",\n" +
                "                \"options\": [\n" +
                "                    \"POST\"\n" +
                "                ],\n" +
                "                \"reason\": [\n" +
                "                    \"pornographic\",\n" +
                "                    \"harassment\",\n" +
                "                    \"advertisement\",\n" +
                "                    \"ripoff\",\n" +
                "                    \"incorrect rating\",\n" +
                "                    \"spam\"\n" +
                "                ]\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"user\": {\n" +
                "        \"uri\": \"/users/2184989\",\n" +
                "        \"name\": \"Kevin McGloughlin\",\n" +
                "        \"link\": \"https://vimeo.com/kevinmcgloughlin\",\n" +
                "        \"location\": \"Sligo,Ireland\",\n" +
                "        \"bio\": \"I'm here to share my imagination with you.\\n\\nkmcgloughlin@gmail.com\\n\\nhttps://www.kevinmcgloughlin.com/\\n\\nhttps://www.instagram.com/kevinmcgloughlin_gram/\",\n" +
                "        \"created_time\": \"2009-08-18T23:44:12+00:00\",\n" +
                "        \"pictures\": {\n" +
                "            \"uri\": \"/users/2184989/pictures/6128293\",\n" +
                "            \"active\": true,\n" +
                "            \"type\": \"custom\",\n" +
                "            \"sizes\": [\n" +
                "                {\n" +
                "                    \"width\": 30,\n" +
                "                    \"height\": 30,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_30x30\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 75,\n" +
                "                    \"height\": 75,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_75x75\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 100,\n" +
                "                    \"height\": 100,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_100x100\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 300,\n" +
                "                    \"height\": 300,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_300x300\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 72,\n" +
                "                    \"height\": 72,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_72x72\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 144,\n" +
                "                    \"height\": 144,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_144x144\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 216,\n" +
                "                    \"height\": 216,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_216x216\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 288,\n" +
                "                    \"height\": 288,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_288x288\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"width\": 360,\n" +
                "                    \"height\": 360,\n" +
                "                    \"link\": \"https://i.vimeocdn.com/portrait/6128293_360x360\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"resource_key\": \"6e51e5488212981cefed00026eb966d31416c8ec\"\n" +
                "        },\n" +
                "        \"websites\": [\n" +
                "            {\n" +
                "                \"name\": \"Instagram\",\n" +
                "                \"link\": \"https://www.instagram.com/kevinmcgloughlin_gram/\",\n" +
                "                \"description\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Website\",\n" +
                "                \"link\": \"https://www.kevinmcgloughlin.com/\",\n" +
                "                \"description\": \"Works by Kevin McGloughlin\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"metadata\": {\n" +
                "            \"connections\": {\n" +
                "                \"albums\": {\n" +
                "                    \"uri\": \"/users/2184989/albums\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 0\n" +
                "                },\n" +
                "                \"appearances\": {\n" +
                "                    \"uri\": \"/users/2184989/appearances\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 4\n" +
                "                },\n" +
                "                \"categories\": {\n" +
                "                    \"uri\": \"/users/2184989/categories\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 0\n" +
                "                },\n" +
                "                \"channels\": {\n" +
                "                    \"uri\": \"/users/2184989/channels\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 23\n" +
                "                },\n" +
                "                \"feed\": {\n" +
                "                    \"uri\": \"/users/2184989/feed\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ]\n" +
                "                },\n" +
                "                \"followers\": {\n" +
                "                    \"uri\": \"/users/2184989/followers\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 4740\n" +
                "                },\n" +
                "                \"following\": {\n" +
                "                    \"uri\": \"/users/2184989/following\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 177\n" +
                "                },\n" +
                "                \"groups\": {\n" +
                "                    \"uri\": \"/users/2184989/groups\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 39\n" +
                "                },\n" +
                "                \"likes\": {\n" +
                "                    \"uri\": \"/users/2184989/likes\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 730\n" +
                "                },\n" +
                "                \"moderated_channels\": {\n" +
                "                    \"uri\": \"/users/2184989/channels?filter=moderated\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 0\n" +
                "                },\n" +
                "                \"portfolios\": {\n" +
                "                    \"uri\": \"/users/2184989/portfolios\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 0\n" +
                "                },\n" +
                "                \"videos\": {\n" +
                "                    \"uri\": \"/users/2184989/videos\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 31\n" +
                "                },\n" +
                "                \"watchlater\": {\n" +
                "                    \"uri\": \"/users/2184989/watchlater\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 40\n" +
                "                },\n" +
                "                \"shared\": {\n" +
                "                    \"uri\": \"/users/2184989/shared/videos\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\"\n" +
                "                    ],\n" +
                "                    \"total\": 74\n" +
                "                },\n" +
                "                \"pictures\": {\n" +
                "                    \"uri\": \"/users/2184989/pictures\",\n" +
                "                    \"options\": [\n" +
                "                        \"GET\",\n" +
                "                        \"POST\"\n" +
                "                    ],\n" +
                "                    \"total\": 1\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"resource_key\": \"509b5e4a47498888c436f20e7e9571850fface5d\",\n" +
                "        \"account\": \"pro\"\n" +
                "    },\n" +
                "    \"app\": {\n" +
                "        \"name\": \"Parallel Uploader\",\n" +
                "        \"uri\": \"/apps/87099\"\n" +
                "    },\n" +
                "    \"status\": \"available\",\n" +
                "    \"resource_key\": \"3d8c9a18687b30faee2073e7ab57b19d26a5633b\",\n" +
                "    \"upload\": null,\n" +
                "    \"transcode\": null\n" +
                "}";

        VimeoVideo vimeoVideo = mGson.fromJson(json, VimeoVideo.class);

        assertEquals(VimeoTextUtil.generateIdFromUri(vimeoVideo.getUri()), 279643508);
        assertEquals(vimeoVideo.getUri(), "/videos/279643508");
        assertEquals(vimeoVideo.getName(), "Talos - D.O.A.M. (Death Of A Muse) Pt III");
        assertEquals(vimeoVideo.getDescription(), "Directed by Kevin McGloughlin\n\nMy aspiration for 'DOAM' was to blur the lines of distance and scale, representing our human interrelationship amidst varying environments.\n\nhttps://www.instagram.com/kevinmcgloughlin_gram/\n\nTaken from 'Then There Was War' EP, included on the deluxe edition of 'Wild Alee', out now: https://talos.lnk.to/WildAleeDLXID\n\nFollow Talos: \nSpotify: http://sptfy.com/Tfv \nApple: http://apple.co/2dOro0X \nFacebook: http://www.facebook.com/thisistalos\nTwitter: http://www.twitter.com/thisistalos \nInstagram: http://www.instagram.com/thisistalos \nWebsite: http://www.thisistalos.com");
        assertEquals(vimeoVideo.getDurationSeconds(), 165);
        try {
            assertEquals(vimeoVideo.getCreatedTime(), new SimpleDateFormat(DATE_FORMAT).parse("2018-07-12T11:59:10+00:00"));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        VimeoConnection commentsConnection = vimeoVideo.getMetadata().getCommentsConnection();
        assertEquals(commentsConnection.getUri(), "/videos/279643508/comments");
        assertEquals(commentsConnection.getTotal(), 12);

        VimeoConnection likesConnection1 = vimeoVideo.getMetadata().getLikesConnection();
        assertEquals(likesConnection1.getUri(), "/videos/279643508/likes");
        assertEquals(likesConnection1.getTotal(), 199);

        VimeoConnection recommendationsConnection = vimeoVideo.getMetadata().getRecommendationsConnection();
        assertEquals(recommendationsConnection.getUri(), "/videos/279643508/recommendations");
    }
}

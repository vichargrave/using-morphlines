package io.github.vichargrave.morphlineparser;

import com.google.common.collect.ListMultimap;
import org.junit.Test;
import org.kitesdk.morphline.api.Record;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonParserTest {

    @Test
    public void testJsonLineParser() throws IOException {
        String lineToParse = "{\"text\":\"sample tweet one\",\"retweet_count\":0,\"in_reply_to_user_id\":null,\"retweeted\":false,\"truncated\":false,\"source\":\"href=\\\"http:\\/\\/sample.com\\\"\",\"id_str\":\"1234567891\",\"entities\":{\"user_mentions\":[],\"hashtags\":[],\"urls\":[]},\"in_reply_to_status_id\":null,\"place\":null,\"in_reply_to_status_id_str\":null,\"coordinates\":null,\"created_at\":\"Wed Sep 05 01:01:01 +0000 1985\",\"in_reply_to_screen_name\":null,\"favorited\":false,\"in_reply_to_user_id_str\":null,\"user\":{\"default_profile_image\":false,\"friends_count\":111,\"profile_background_color\":\"3C0C29\",\"location\":\"Palo Alto\",\"is_translator\":false,\"profile_background_tile\":true,\"favourites_count\":11,\"verified\":false,\"profile_sidebar_fill_color\":\"efefef\",\"follow_request_sent\":null,\"contributors_enabled\":false,\"description\":\"desc1\",\"profile_sidebar_border_color\":\"eeeeee\",\"profile_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_images\\/1\\/normal.jpg\",\"id_str\":\"1111111\",\"listed_count\":1,\"lang\":\"en\",\"screen_name\":\"fake_user1\",\"show_all_inline_media\":false,\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_images\\/1111111\\/normal.jpg\",\"default_profile\":false,\"statuses_count\":11111,\"created_at\":\"Thu Apr 07 11:04:54 +0000 1985\",\"profile_text_color\":\"333333\",\"followers_count\":111,\"protected\":false,\"following\":null,\"notifications\":null,\"profile_background_image_url\":\"http:\\/\\/a0.twimg.com\\/images\\/themes\\/theme1\\/bg.gif\",\"time_zone\":null,\"url\":null,\"name\":\"vichargrave\",\"geo_enabled\":false,\"profile_link_color\":\"009999\",\"id\":1111112,\"profile_background_image_url_https\":\"https:\\/\\/si0.twimg.com\\/images\\/themes\\/theme1\\/bg.gif\",\"utc_offset\":null},\"id\":11111112,\"contributors\":null,\"geo\":null}\n";

        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "json");
        List<Record> records = parser.parse(lineToParse);
        if (records.size() > 0) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
            assertEquals("id", 11111112, out.get("id").get(0));
            assertEquals("id_str", "1234567891", out.get("id_str").get(0));
            assertEquals("text", "sample tweet one", out.get("text").get(0));
            assertEquals("created_at", "Wed Sep 05 01:01:01 +0000 1985", out.get("created_at").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("source", "href=\"http://sample.com\"", out.get("source").get(0));
            assertEquals("retweeted", false, out.get("retweeted").get(0));
            assertEquals("favorited", false, out.get("favorited").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("user_friends_count", 111, out.get("user_friends_count").get(0));
            assertEquals("user_favourites_count", 11, out.get("user_favourites_count").get(0));
            assertEquals("user_location", "Palo Alto", out.get("user_location").get(0));
            assertEquals("user_description", "desc1", out.get("user_description").get(0));
            assertEquals("user_statuses_count", 11111, out.get("user_statuses_count").get(0));
            assertEquals("user_followers_count", 111, out.get("user_followers_count").get(0));
            assertEquals("user_name", "vichargrave", out.get("user_name").get(0));
            assertEquals("user_screen_name", "fake_user1", out.get("user_screen_name").get(0));
        }
        else {
            System.out.println("No parsed records produced");
        }
    }

    @Test
    public void testJsonFileParser() throws IOException {
        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf", "json");
        File fileToParse = new File("../data/tweets.json");
        List<Record> records = parser.parse(fileToParse);
        if (records.size() > 0) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
            assertEquals("id", 11111112, out.get("id").get(0));
            assertEquals("id_str", "1234567891", out.get("id_str").get(0));
            assertEquals("text", "sample tweet one", out.get("text").get(0));
            assertEquals("created_at", "Wed Sep 05 01:01:01 +0000 1985", out.get("created_at").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("source", "href=\"http://sample.com\"", out.get("source").get(0));
            assertEquals("retweeted", false, out.get("retweeted").get(0));
            assertEquals("favorited", false, out.get("favorited").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("user_friends_count", 111, out.get("user_friends_count").get(0));
            assertEquals("user_favourites_count", 11, out.get("user_favourites_count").get(0));
            assertEquals("user_location", "Palo Alto", out.get("user_location").get(0));
            assertEquals("user_description", "desc1", out.get("user_description").get(0));
            assertEquals("user_statuses_count", 11111, out.get("user_statuses_count").get(0));
            assertEquals("user_followers_count", 111, out.get("user_followers_count").get(0));
            assertEquals("user_name", "vichargrave", out.get("user_name").get(0));
            assertEquals("user_screen_name", "fake_user1", out.get("user_screen_name").get(0));
        }
        else {
            System.out.println("No parsed records produced");
        }
    }

    @Test
    public void testJsonLineParserNoMorphlineId() throws IOException {
        String lineToParse = "{\"text\":\"sample tweet one\",\"retweet_count\":0,\"in_reply_to_user_id\":null,\"retweeted\":false,\"truncated\":false,\"source\":\"href=\\\"http:\\/\\/sample.com\\\"\",\"id_str\":\"1234567891\",\"entities\":{\"user_mentions\":[],\"hashtags\":[],\"urls\":[]},\"in_reply_to_status_id\":null,\"place\":null,\"in_reply_to_status_id_str\":null,\"coordinates\":null,\"created_at\":\"Wed Sep 05 01:01:01 +0000 1985\",\"in_reply_to_screen_name\":null,\"favorited\":false,\"in_reply_to_user_id_str\":null,\"user\":{\"default_profile_image\":false,\"friends_count\":111,\"profile_background_color\":\"3C0C29\",\"location\":\"Palo Alto\",\"is_translator\":false,\"profile_background_tile\":true,\"favourites_count\":11,\"verified\":false,\"profile_sidebar_fill_color\":\"efefef\",\"follow_request_sent\":null,\"contributors_enabled\":false,\"description\":\"desc1\",\"profile_sidebar_border_color\":\"eeeeee\",\"profile_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_images\\/1\\/normal.jpg\",\"id_str\":\"1111111\",\"listed_count\":1,\"lang\":\"en\",\"screen_name\":\"fake_user1\",\"show_all_inline_media\":false,\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_images\\/1111111\\/normal.jpg\",\"default_profile\":false,\"statuses_count\":11111,\"created_at\":\"Thu Apr 07 11:04:54 +0000 1985\",\"profile_text_color\":\"333333\",\"followers_count\":111,\"protected\":false,\"following\":null,\"notifications\":null,\"profile_background_image_url\":\"http:\\/\\/a0.twimg.com\\/images\\/themes\\/theme1\\/bg.gif\",\"time_zone\":null,\"url\":null,\"name\":\"vichargrave\",\"geo_enabled\":false,\"profile_link_color\":\"009999\",\"id\":1111112,\"profile_background_image_url_https\":\"https:\\/\\/si0.twimg.com\\/images\\/themes\\/theme1\\/bg.gif\",\"utc_offset\":null},\"id\":11111112,\"contributors\":null,\"geo\":null}\n";

        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf");
        List<Record> records = parser.parse(lineToParse);
        if (records.size() > 0) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
            assertEquals("id", 11111112, out.get("id").get(0));
            assertEquals("id_str", "1234567891", out.get("id_str").get(0));
            assertEquals("text", "sample tweet one", out.get("text").get(0));
            assertEquals("created_at", "Wed Sep 05 01:01:01 +0000 1985", out.get("created_at").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("source", "href=\"http://sample.com\"", out.get("source").get(0));
            assertEquals("retweeted", false, out.get("retweeted").get(0));
            assertEquals("favorited", false, out.get("favorited").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("user_friends_count", 111, out.get("user_friends_count").get(0));
            assertEquals("user_favourites_count", 11, out.get("user_favourites_count").get(0));
            assertEquals("user_location", "Palo Alto", out.get("user_location").get(0));
            assertEquals("user_description", "desc1", out.get("user_description").get(0));
            assertEquals("user_statuses_count", 11111, out.get("user_statuses_count").get(0));
            assertEquals("user_followers_count", 111, out.get("user_followers_count").get(0));
            assertEquals("user_name", "vichargrave", out.get("user_name").get(0));
            assertEquals("user_screen_name", "fake_user1", out.get("user_screen_name").get(0));
        }
        else {
            System.out.println("No parsed records produced");
        }
    }

    @Test
    public void testJsonFileParserNoMorphlineId() throws IOException {
        MorphlineParser parser = new MorphlineParser("../conf/parsers.conf");
        File fileToParse = new File("../data/tweets.json");
        List<Record> records = parser.parse(fileToParse);
        if (records.size() > 0) {
            ListMultimap out = records.get(0).getFields();
            System.out.println(out);
            assertEquals("id", 11111112, out.get("id").get(0));
            assertEquals("id_str", "1234567891", out.get("id_str").get(0));
            assertEquals("text", "sample tweet one", out.get("text").get(0));
            assertEquals("created_at", "Wed Sep 05 01:01:01 +0000 1985", out.get("created_at").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("source", "href=\"http://sample.com\"", out.get("source").get(0));
            assertEquals("retweeted", false, out.get("retweeted").get(0));
            assertEquals("favorited", false, out.get("favorited").get(0));
            assertEquals("retweet_count", 0, out.get("retweet_count").get(0));
            assertEquals("user_friends_count", 111, out.get("user_friends_count").get(0));
            assertEquals("user_favourites_count", 11, out.get("user_favourites_count").get(0));
            assertEquals("user_location", "Palo Alto", out.get("user_location").get(0));
            assertEquals("user_description", "desc1", out.get("user_description").get(0));
            assertEquals("user_statuses_count", 11111, out.get("user_statuses_count").get(0));
            assertEquals("user_followers_count", 111, out.get("user_followers_count").get(0));
            assertEquals("user_name", "vichargrave", out.get("user_name").get(0));
            assertEquals("user_screen_name", "fake_user1", out.get("user_screen_name").get(0));
        }
        else {
            System.out.println("No parsed records produced");
        }
    }
}
